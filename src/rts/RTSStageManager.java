/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts;

import com.jme3.input.FlyByCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.renderer.Camera;
import java.util.ArrayList;
import rts.cam.RTSCamera;
import rts.core.entity.RTSEntityManager;
import rts.core.entity.RTSSpatialEntity;
import rts.fx.filters.ScreenShadowFilter;
import rts.gameplay.RTSGamePlayManager;
import rts.stage.RTSSelectManager;
import rts.world.map.Map;
import sg.atom.core.lifecycle.ProcessInfo;
import sg.atom.entity.SpatialEntity;
import sg.atom.gameplay.GameLevel;
import sg.atom.stage.StageManager;
import sg.atom.world.WorldSettings;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class RTSStageManager extends StageManager {

    private Map currentMap;
    private RTSCamera rtsCam;
    ProcessInfo currentProcess = new ProcessInfo("None");
    private Vector3f startPos = new Vector3f(0, 130, -10);
    ScreenShadowFilter shadowFilter;

    public RTSStageManager(RTSGame app) {
        super(app);
    }

    void initCam() {
        inputManager.setCursorVisible(true);
        //flyCam.setMoveSpeed(50);
        //cam.setLocation(new Vector3f(0, 10, -10));
        //cam.lookAtDirection(new Vector3f(0, -1.5f, -1).normalizeLocal(), Vector3f.UNIT_Y);
        FlyByCamera flyCam = app.getFlyByCamera();
        Camera cam = app.getCamera();
        flyCam.setEnabled(false);
        inputManager.removeListener(flyCam);

        rtsCam = new RTSCamera(cam, rootNode);
        rtsCam.registerWithInput(inputManager);
        rtsCam.setCenter(startPos);
        rtsCam.setMaxSpeed(RTSCamera.Degree.SIDE, 60f, 0.4f);
        rtsCam.setMaxSpeed(RTSCamera.Degree.FWD, 60f, 0.4f);
        rtsCam.setMaxSpeed(RTSCamera.Degree.ROTATE, 5f, 0.4f);
        rtsCam.setMaxSpeed(RTSCamera.Degree.TILT, 5f, 0.4f);

        inputManager.setCursorVisible(true);
    }

    void initEffect() {
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        BloomFilter bf = new BloomFilter(BloomFilter.GlowMode.Objects);
        bf.setBloomIntensity(0.6f);
        bf.setExposurePower(0.8f);
        bf.setBlurScale(2f);
        fpp.addFilter(bf);
// For of War
        /*
         shadowFilter = new ScreenShadowFilter();
         shadowFilter.setNoiseTex(assetManager.loadTexture("Textures/NoiseRand/noiseTile.png"));
         shadowFilter.setMapTex(assetManager.loadTexture("Textures/NoiseRand/noiseFractal.png"));

         fpp.addFilter(shadowFilter);
         */
        app.getViewPort().addProcessor(fpp);
    }

    @Override
    public void initStage() {

        this.worldManager = new RTSWorldManager((RTSGame) app, this);
        this.entityManager = new RTSEntityManager(this);
        this.gameGUIManager = app.getGameGUIManager();
        this.gamePlayManager = new RTSGamePlayManager((RTSGame) app);

    }
    // ======== LOAD STAGE=============

    public void loadStage() {
        currentProcess.currentProgressName = "Settings";
        currentProcess.currentProgressPercent = 0.1f;
        loadSettings();
        currentProcess.currentProgressName = "Data";
        currentProcess.currentProgressPercent = 0.2f;
        loadData();
        currentProcess.currentProgressName = "Profile";
        currentProcess.currentProgressPercent = 0.3f;
        loadProfile();
        currentProcess.currentProgressName = "World";
        currentProcess.currentProgressPercent = 0.4f;

        // World 
        currentLevel = new GameLevel(gamePlayManager, worldManager, "Level1", "Scenes/Map1/Map1.j3o");
        currentMap = new Map("map1");
        currentMap.createRandomMap((RTSEntityManager) entityManager);
        getEntityManager().loadEntitesFromMap(currentMap);
        // Load all asset
        // units
        // building
        WorldSettings worldSettings = new WorldSettings();
        worldSettings.useTerrain = true;
        worldSettings.useTerrainLOD = true;
        worldSettings.useDayLight = true;

        System.out.println(" Loaded Entities from Map !");
        getWorldManager().initWorld(currentLevel, worldSettings);
        getWorldManager().loadWorld();
        getWorldManager().buildWorld(currentMap);

        currentProcess.currentProgressName = "Gameplay";
        currentProcess.currentProgressPercent = 0.8f;

        //soundManager.initAudio();
        System.out.println(" Done Load stage");
    }

    void setupWaterControl() {
        inputManager.addMapping("foam1", new KeyTrigger(KeyInput.KEY_1));
        inputManager.addMapping("foam2", new KeyTrigger(KeyInput.KEY_2));
        inputManager.addMapping("foam3", new KeyTrigger(KeyInput.KEY_3));
        inputManager.addListener(worldManager.getWaterManager(), "foam1", "foam2", "foam3");

    }

    void setupScreenEffectKeys() {

        inputManager.addMapping("toogleCCFilter", new KeyTrigger(KeyInput.KEY_L));
        inputManager.addMapping("toogleBloomFilter", new KeyTrigger(KeyInput.KEY_K));
        inputManager.addListener(screenEffectManager, "toogleCCFilter", "toogleBloomFilter");
    }

    void setupKeys() {
        // Short cut for MainMenu,Help
        //inputManager.addMapping("wireframe", new KeyTrigger(KeyInput.KEY_T));
    }

    @Override
    public void configStage() {
        //super.configStage();

        // Setup players
        // from Net?
        // Setup gameplay
        // Loading the map
        selectManager = new RTSSelectManager(this.gameGUIManager, this, getWorldManager());
        selectManager.init();
        worldManager.configWorld();
    }

    public void attachStage() {
        worldManager.attachWorld();
        //soundManager.attachAudio();
    }

    public void simpleUpdate(float tpf) {
        //Logger.getLogger(RTSStageManager.class.getName()).warning("Update Stage");
        worldManager.simpleUpdate(tpf);
        if (shadowFilter != null) {
            updateShadow();
        }
        //gameGUIManager.simpleUpdate(tpf);
    }

    public void updateShadow() {
        Vector2f mapSize = new Vector2f(512, 512);
        Vector2f viewPortSize = new Vector2f(100, 100);
        Vector3f currentPos = rtsCam.getPosition().clone();
        currentPos.z = -currentPos.z;
        Vector4f mapPos = new Vector4f(currentPos.x / mapSize.x,
                currentPos.z / mapSize.y,
                viewPortSize.x / mapSize.x, viewPortSize.y / mapSize.y);

        shadowFilter.setMapPos(mapPos);
    }

    public Map getMap() {
        return currentMap;
    }

    public void goInGame() {
        //worldManager.attachWorld();
        ((RTSWorldManager) worldManager).disableParticle();

        // init GUI
        initCam();
        initEffect();
        getGamePlayManager().initGamePlay();
        getGamePlayManager().startGame();
        selectManager.setupInputListener();
    }

    public void doEntityAction(String actionName) {
        ArrayList<SpatialEntity> selection = getSelectManager().getCurrentSelection();
        if (!selection.isEmpty()) {
            if (selection.size() > 1) {
            } else {
                RTSSpatialEntity entity = (RTSSpatialEntity) selection.get(0);
                entity.doAction(actionName);
            }
        }
    }

    public RTSCamera getRtsCam() {
        return rtsCam;
    }

    public Vector3f getStartPos() {
        return startPos;
    }

    @Override
    public RTSWorldManager getWorldManager() {
        return (RTSWorldManager) super.getWorldManager();
    }

    @Override
    public RTSGamePlayManager getGamePlayManager() {
        return (RTSGamePlayManager) super.getGamePlayManager();
    }

    @Override
    public RTSEntityManager getEntityManager() {
        return (RTSEntityManager) super.getEntityManager();
    }

    public void pauseGame() {
    }
}
