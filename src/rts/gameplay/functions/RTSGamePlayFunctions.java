package rts.gameplay.functions;

import rts.gameplay.functions.guards.EntitySelectGuard;
import rts.gameplay.functions.guards.EventNameGuard;
import rts.gameplay.functions.guards.ActionGuard;
import com.continuent.tungsten.fsm.core.Action;
import com.continuent.tungsten.fsm.core.Entity;
import com.continuent.tungsten.fsm.core.EntityAdapter;
import com.continuent.tungsten.fsm.core.Event;
import com.continuent.tungsten.fsm.core.FiniteStateException;
import com.continuent.tungsten.fsm.core.Guard;
import com.continuent.tungsten.fsm.core.PositiveGuard;
import com.continuent.tungsten.fsm.core.State;
import com.continuent.tungsten.fsm.core.StateChangeListener;
import com.continuent.tungsten.fsm.core.StateMachine;
import com.continuent.tungsten.fsm.core.StateTransitionMap;
import com.continuent.tungsten.fsm.core.StateType;
import com.continuent.tungsten.fsm.core.Transition;
import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import rts.RTSWorldManager;
import rts.core.entity.RTSEntityManager;
import rts.core.entity.RTSSpatialEntity;
import rts.gameplay.RTSGamePlayManager;
import rts.gameplay.base.Unit;
import rts.gameplay.manager.BuildingManager;
import rts.gameplay.manager.UnitManager;
import rts.stage.RTSSelectManager;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class RTSGamePlayFunctions {

    protected RTSGamePlayManager gamePlayManager;
    protected RTSSelectManager selectManager;
    protected RTSEntityManager entityManager;
    protected UnitManager unitManager;
    protected BuildingManager buildingManager;
    // FSM for function handling
    private StateTransitionMap stmap;
    private Action nullAction;
    private StateMachine sm;
    private Logger logger = Logger.getLogger(RTSGamePlayFunctions.class.getName());

    public RTSGamePlayFunctions(RTSGamePlayManager gamePlayManager) {
        this.gamePlayManager = gamePlayManager;
    }

    public void init() {
        this.selectManager = gamePlayManager.getSelectManager();
        this.entityManager = gamePlayManager.getEntityManager();
        this.unitManager = gamePlayManager.getUnitManager();
        this.buildingManager = gamePlayManager.getBuildingManager();

    }

    public void initFSM() {
        try {
            stmap = new StateTransitionMap();
            State normalState = new State("Normal", StateType.START);
            State selectUnitState = new State("SelectUnit", StateType.ACTIVE);
            State selectTargetState = new State("SelectTarget", StateType.ACTIVE);
            State selectActionState = new State("SelectAction", StateType.ACTIVE);
            State viewState = new State("ViewState", StateType.ACTIVE);
            State stopState = new State("Stop", StateType.END);

            stmap.addState(normalState);
            stmap.addState(selectUnitState);
            stmap.addState(selectActionState);
            stmap.addState(selectTargetState);
            stmap.addState(viewState);
            stmap.addState(stopState);

            // Guard - Conditions
            Guard positiveGuard = new PositiveGuard();
            Guard entitySelectGuard = new EntitySelectGuard("Any", "", "");
            Guard actionGuard = new ActionGuard();
            Guard defaultActionGuard = new ActionGuard();
            Guard targetGuard = new PositiveGuard();

            // Transition - Direction
            // From: Normal
            // --> End : if End event from the input
            stmap.addTransition(new Transition("Normal --> End", new EventNameGuard("Exit"), normalState, nullAction, stopState));
            // --> Normal : if Any Controlable Entity selected
            stmap.addTransition(new Transition("Normal --> Select unit", entitySelectGuard, normalState, nullAction, selectUnitState));
            //stmap.addTransition(new Transition("Normal --> Normal", positiveGuard, normalState, nullAction, normalState));
            // --> Normal : if Any Non-Controlable Entity selected
            stmap.addTransition(new Transition("Normal --> View", new Guard() {
                public boolean accept(Event event, Entity entity, State state) {
                    return true;
                }
            }, normalState, nullAction, viewState));

            // From:View
            stmap.addTransition(new Transition("Normal --> View", entitySelectGuard, viewState, nullAction, normalState));
            // From: Select Unit
            // --> Select Action : 
            stmap.addTransition(new Transition("Select Unit --> Select Action", actionGuard, selectUnitState, nullAction, selectActionState));
            // --> Select Target : 
            stmap.addTransition(new Transition("Select Unit --> Select Target", defaultActionGuard, selectUnitState, nullAction, selectTargetState));

            // From: Select Action
            // --> Select Target: if Valid Target selected
            stmap.addTransition(new Transition("Select Action --> Select Target", defaultActionGuard, selectActionState, nullAction, selectTargetState));
            // --> (Self) : invalid action
            stmap.addTransition(new Transition("Select Action --> (Self)", defaultActionGuard, selectActionState, nullAction, selectTargetState));

            // From: Select Target
            // --> Normal : if Action Complete mode
            stmap.addTransition(new Transition("Select Target --> Normal", targetGuard, selectTargetState, nullAction, selectActionState));
            // --> Action : if Action Continue mode
            stmap.addTransition(new Transition("Select Target --> Select Action", targetGuard, selectTargetState, nullAction, normalState));

            // FINISH
            stmap.build();

            sm = new StateMachine(stmap, new EntityAdapter(this));

            StateChangeListener listener = new StateChangeListener() {
                public void stateChanged(Entity entity, State oldState, State newState) {
                    RTSGamePlayFunctions gf = ((RTSGamePlayFunctions) ((EntityAdapter) entity).getEntity());
                    logger.info(gf.getClass().getName());
                    logger.info("State changed:  old=" + oldState.getName() + " new=" + newState.getName());
                }
            };

            sm.addListener(listener);

        } catch (FiniteStateException ex) {
            Logger.getLogger(RTSGamePlayFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void displayFSM() {
    }

    public void inputTriggered(String actionName) {

        try {
            sm.applyEvent(new RTSFunctionEvent(actionName));
        } catch (InterruptedException ex) {
            Logger.getLogger(RTSGamePlayFunctions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FiniteStateException ex) {
            Logger.getLogger(RTSGamePlayFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void update(float tpf) {
        System.out.println("Current state" + sm.getState().getBaseName());
    }

/*
    public static void main(String args[]) {
        RTSGamePlayFunctions f = new RTSGamePlayFunctions(null);
        f.initFSM();

        f.inputTriggered("Any");
        //f.update(1);
        f.inputTriggered("Any");
        //f.update(1);
        f.inputTriggered("Any");
        //f.update(1);
    }
    */
}
