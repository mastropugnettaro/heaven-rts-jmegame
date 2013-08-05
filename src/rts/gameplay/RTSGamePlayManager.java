package rts.gameplay;

import com.jme3.animation.AnimControl;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import rts.RTSGame;
import rts.RTSStageManager;
import rts.RTSWorldManager;
import rts.core.entity.RTSEntityManager;
import rts.core.entity.RTSSpatialEntity;
import rts.gameplay.actions.RTSAction;
import rts.gameplay.ai.BuildingAI;
import rts.gameplay.ai.UnitAI;
import rts.gameplay.base.Country;
import rts.gameplay.config.RTSMatchSetting;
import rts.gameplay.functions.RTSGamePlayFunctions;
import rts.gameplay.league.RTSGameMatch;
import rts.gameplay.manager.BuildingManager;
import rts.gameplay.manager.UnitManager;
import rts.stage.RTSSelectManager;
import rts.ui.EntityActionUI;
import rts.ui.EntityInfoUI;
import rts.world.RTSGameWorldView;
import sg.atom.entity.Entity;
import sg.atom.entity.SpatialEntity;
import sg.atom.gameplay.player.Player;
import sg.atom.stage.GamePlayManager;
import sg.atom.world.SceneGraphHelper;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class RTSGamePlayManager extends GamePlayManager implements ActionListener {

    protected RTSSelectManager selectManager;
    protected RTSEntityManager entityManager;
    protected UnitManager unitManager;
    protected BuildingManager buildingManager;
    private RTSGamePlayFunctions functions;
    private boolean toogleAIState = true;
    // Not a Singleton!
    static RTSGamePlayManager defaultInstance;
    private RTSGamePlay rtsGamePlay;

    public RTSGamePlayManager(RTSGame app) {
        super(app);
        defaultInstance = this;
    }

    public void initGamePlay() {
        this.entityManager = (RTSEntityManager) stageManager.getEntityManager();
        this.unitManager = new UnitManager(this);
        this.buildingManager = new BuildingManager(this);
        this.selectManager = new RTSSelectManager(gameGUIManager, stageManager, getWorldManager());

        this.rtsGamePlay = new RTSGamePlay(this);
        rtsGamePlay.init();
        this.functions = new RTSGamePlayFunctions(this);
        functions.init();
        setupPlayers();
        setupKeys();

    }

    private void setupPlayers() {
        rtsGamePlay.setupPlayers();
    }

    public void startGame() {
        RTSMatchSetting matchSetting = new RTSMatchSetting();
        rtsGamePlay.startGame(new RTSGameMatch(matchSetting));

        for (RTSPlayer player : rtsGamePlay.getPlayers()) {
            Country c = player.country;
            getWorldManager().attachCountry(c);
        }
        // setup the world
        getWorldManager().batchWorld();
        Vector3f gameStartPos = new Vector3f(140, 130, 220);
        gameStartPos = RTSWorldManager.getDefault().getTerrainPos(((RTSPlayer) getMainPlayer()).getCountry().getBuildings().get(0));
        gameStartPos.addLocal(0, 100, 80);
        ((RTSStageManager) getStageManager()).getRtsCam().setCenter(gameStartPos);

        // setup gameplay funtions
        functions.initFSM();
    }

    public void pauseGame() {
        getStageManager().pauseGame();
    }

    public void checkWinLose() {
    }

    public void createGamePlayEvent() {
    }

    private void setupKeys() {
        // Short cut for Building and unit, Skill
        inputManager.addMapping("move", new KeyTrigger(KeyInput.KEY_H));
        inputManager.addMapping("stand", new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping("attack", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("build", new KeyTrigger(KeyInput.KEY_B));
        inputManager.addMapping("group", new KeyTrigger(KeyInput.KEY_G));
        inputManager.addMapping("DisableAI", new KeyTrigger(KeyInput.KEY_F8));
        // FOR MOUSE
        inputManager.addMapping("mouseLeft", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addMapping("mouseRight", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        inputManager.addListener(this, "mouseLeft", "mouseRight", "wireframe", "move", "stand");
        inputManager.addListener(this, "DisableAI");
    }

    public void doMouseFunction(String actionName, boolean keyPressed, float tpf) {
        Camera cam = stageManager.getCamera();
        functions.inputTriggered(actionName);
    }

    public void onAction(String actionName, boolean keyPressed, float tpf) {
        if (actionName.equals("DisableAI") && keyPressed) {
            doToogleEntitiesControls();
        }
        selectManager = (RTSSelectManager) stageManager.getSelectManager();
        if (selectManager != null) {
            if ((actionName.equals("mouseRight") || actionName.equals("mouseLeft")) && !keyPressed) {
                doMouseFunction(actionName, keyPressed, tpf);
            } else if ((actionName.equals("move") || actionName.equals("stand")) && keyPressed) {
                //controlUnitAction(actionName);
                functions.inputTriggered(actionName);
            }
        }
    }

    public void actionCompleted(RTSAction action) {
    }

    public void displayEntityInfo(Entity entity) {

        entity = (RTSSpatialEntity) entity;
        // display entity info
        EntityInfoUI info = gameGUIManager.getQuickUIController("infoDetail", EntityInfoUI.class);
        info.setInfo("Name:" + entity.type, entity.type, entity.name);

        // display entity actions
        EntityActionUI actions = gameGUIManager.getQuickUIController("actionDetail", EntityActionUI.class);
        if (((RTSSpatialEntity) entity).getFunctionMap() != null) {
            actions.setInfoButtons(((RTSSpatialEntity) entity).getFunctionMap());
        }
    }

    private void doToogleEntitiesControls() {
        toogleAIState = !toogleAIState;
        System.out.println("Toogle AI");
        for (SpatialEntity ent : entityManager.getAllSpatialEntities()) {

            RTSSpatialEntity entity = (RTSSpatialEntity) ent;


            if (entity.getSpatial() != null) {
                System.out.println("" + entity);

                UnitAI unitAI = SceneGraphHelper.travelDownFindFirstControl(entity.getSpatial(), UnitAI.class);
                BuildingAI buildingAI = SceneGraphHelper.travelDownFindFirstControl(entity.getSpatial(), BuildingAI.class);
                AnimControl animControl = SceneGraphHelper.travelDownFindFirstControl(entity.getSpatial(), AnimControl.class);
                if (unitAI != null) {
                    unitAI.setEnabled(toogleAIState);
                }
                if (buildingAI != null) {
                    buildingAI.setEnabled(toogleAIState);
                }
                if (animControl != null) {
                    animControl.setEnabled(toogleAIState);
                    System.out.println("Toogle AnimControl");
                }
            }
        }
    }

    public RTSStageManager getStageManager() {
        return (RTSStageManager) super.getStageManager();
    }

    public UnitManager getUnitManager() {
        return unitManager;
    }

    public RTSEntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public RTSWorldManager getWorldManager() {
        return (RTSWorldManager) super.getWorldManager();
    }

    public BuildingManager getBuildingManager() {
        return buildingManager;
    }

    public RTSSelectManager getSelectManager() {
        return selectManager;
    }

    public static RTSGamePlayManager getDefaultInstance() {
        return defaultInstance;
    }

    public RTSGameWorldView getWorldView(RTSPlayer player) {
        return getWorldManager().getFullWorldView();
    }

    public Player getMainPlayer() {
        return mainPlayer;
    }

    public void setMainPlayer(RTSPlayer player1) {
        mainPlayer = player1;
    }
}
