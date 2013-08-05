package rts.gameplay.functions;

import com.continuent.tungsten.fsm.core.Action;
import com.continuent.tungsten.fsm.core.State;
import com.continuent.tungsten.fsm.core.StateType;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class RTSGamePlayFunction extends State {

    public RTSGamePlayFunction(String name, StateType type) {
        super(name, type);
    }

    public RTSGamePlayFunction(String name, StateType type, Action entryAction, Action exitAction) {
        super(name, type, entryAction, exitAction);
    }

    public RTSGamePlayFunction(String name, StateType type, State parent, Action entryAction, Action exitAction) {
        super(name, type, parent, entryAction, exitAction);
    }

    public RTSGamePlayFunction(Enum<?> stateEnum, StateType type, State parent, Action entryAction, Action exitAction) {
        super(stateEnum, type, parent, entryAction, exitAction);
    }

    public RTSGamePlayFunction(Enum<?> stateEnum, StateType type) {
        super(stateEnum, type);
    }

    public RTSGamePlayFunction(String name, StateType type, State parent) {
        super(name, type, parent);
    }
}
