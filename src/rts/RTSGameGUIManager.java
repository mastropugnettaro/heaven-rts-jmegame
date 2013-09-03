package rts;

import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.texture.Texture2D;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import java.util.ArrayList;
import java.util.HashMap;
import rts.gameplay.RTSGamePlayManager;
import rts.gameplay.RTSPlayer;
import rts.gameplay.base.Unit;
import rts.ui.EndGameUI;
import rts.ui.HealthBar;
import rts.ui.InGameScreenUI;
import rts.ui.MainMenuScreenUI;
import rts.ui.MapUI;
import rts.ui.MiniView;
import sg.atom.ui.GameGUIManager;
import sg.atom.ui.common.UILoadingScreenController;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class RTSGameGUIManager extends GameGUIManager {

    private MiniView unitMiniView;
    private ImageRenderer imageRenderer, mapRenderer;
    private ArrayList<HealthBar> healthBars = new ArrayList<HealthBar>();
    private HashMap<String, JmeCursor> cursors = new HashMap<String, JmeCursor>();
    private Texture2D mapTex;
    private MapUI mapUI;

    public RTSGameGUIManager(RTSGame app) {
        super(app);

    }

    @Override
    public void initCursors() {
        /*
         cursors.put("move", (JmeCursor) assetManager.loadAsset("Textures/Cursors/TRONmove.ani"));
         cursors.put("busy", (JmeCursor) assetManager.loadAsset("Textures/Cursors/TRONbusy.ani"));
         cursors.put("normal", (JmeCursor) assetManager.loadAsset("Textures/Cursors/TRONnormal.ani"));
         cursors.put("precision", (JmeCursor) assetManager.loadAsset("Textures/Cursors/TRONprecision.ani"));
         inputManager.setMouseCursor(cursors.get("normal"));
         inputManager.setCursorVisible(true);
         */
    }

    @Override
    public void initGUI() {
        super.initGUI();
        setupKeys();
    }

    public void setupKeys() {
        inputManager.addMapping("ToogleNifty",
                new KeyTrigger(KeyInput.KEY_F7));
        inputManager.addListener(actionListener, "ToogleNifty");
    }
    /**
     * Use ActionListener to respond to pressed/released inputs (key presses,
     * mouse clicks)
     */
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean pressed, float tpf) {
            if (name.equals("ToogleNifty") && pressed) {
                if (enabled) {
                    System.out.println("Detach Nifty");
                    detachNifty();
                } else {
                    System.out.println("attachNifty ");
                    attachNifty();
                }
            }
        }
    };

    public void goInGame() {
        this.stageManager = app.getStageManager();

        goToScreen("InGameScreen");
        setupMap();

        unitMiniView = new MiniView(assetManager, app.getRenderManager(), inputManager);
        unitMiniView.setupViewport();
        imageRenderer = getRenderer(ImageRenderer.class, "InGameScreen/fix/mainPanel/miniView/miniAvatar/miniAvatarImage");
        setNiftyImage(unitMiniView.getTexture(), "unitMiniViewImage", imageRenderer);

    }

    @Override
    public void setupCommonScreens() {
        nifty.registerScreenController(new InGameScreenUI(this),
                new MainMenuScreenUI(this),
                new EndGameUI(this),
                new UILoadingScreenController(this));
        //
        NiftyScreenPath mainMenuXML = new NiftyScreenPath("Interface/MainMenu/MainMenu.xml");
        NiftyScreenPath createHostXML = new NiftyScreenPath("Interface/MainMenu/CreateHost.xml");
        NiftyScreenPath inGameXML = new NiftyScreenPath("Interface/InGame/Ingame.xml");
        NiftyScreenPath loadGameXML = new NiftyScreenPath("Interface/MainMenu/Loading.xml");
        NiftyScreenPath optionXML = new NiftyScreenPath("Interface/MainMenu/Options/Options.xml");
        commonScreens.put("MainMenuScreen", mainMenuXML);
        commonScreens.put("CreateSingleGameScreen", createHostXML);
        commonScreens.put("InGameScreen", inGameXML);
        commonScreens.put("loadingScreen", loadGameXML);
        commonScreens.put("Options", optionXML);

        //nifty.addXml("Interface/InGame/Ingame.xml");
        //nifty.addXml("Interface/MainMenu/Options/Options.xml");
        //nifty.addXml("Interface/MainMenu/CreateHost.xml");
        //nifty.addXml("Interface/InGame/EndGameScreen.xml");
        //nifty.addXml("Interface/MainMenu/Loading.xml");
    }

    void setupMap() {
        mapUI = new MapUI(assetManager);
        mapUI.initMap();
        mapTex = mapUI.getTexture();
        mapRenderer = getRenderer(ImageRenderer.class, "InGameScreen/fix/mainPanel/bottom/mapPanel/mapImage");
        setNiftyImage(mapTex, "mapTex", mapRenderer);

    }

    void setupMainMenu() {
        //setupFilters();
        createHealthBar();
    }

    public void simpleUpdate(float tpf) {
        if (enabled) {
            // Mini View
            unitMiniView.captureScreen(tpf);
            //
            // Healthbar
         /*
             for (HealthBar hb : healthBars) {
             hb.updateBar(app.getCamera(), 10f);
             }
             */
            mapUI.simpleUpdate(tpf);
        }
    }

    private void changeButtonImage(String buttonId, String type) {
    }

    void createHealthBar() {
        RTSPlayer currentPlayer = (RTSPlayer) ((RTSStageManager) app.getStageManager()).getGamePlayManager(RTSGamePlayManager.class).getCurrentPlayer();

        for (Unit unit : currentPlayer.getCountry().getUnits()) {
            HealthBar hb = new HealthBar(unit, this);
            app.getGuiNode().attachChild(hb);
            healthBars.add(hb);
        }
    }
}
