package rts;

import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import java.util.logging.Logger;
import rts.state.LoadingState;
import sg.atom.core.AtomMain;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class RTSGame extends AtomMain {

    @Override
    public void simpleInitApp() {
        this.stageManager = new RTSStageManager(this);
        this.gameGUIManager = new RTSGameGUIManager(this);
        this.gameStateManager = new RTSGameStateManager(this);
        //this.stageManager.initStage();
        this.gameGUIManager.initGUI();
        startup();
    }

    @Override
    public void startup() {
        //gameStateManager.setStartupState(MainMenuState.class);
        //gameStateManager.setStartupState(InGameState.class);
        gameStateManager.setStartupState(LoadingState.class);
        gameStateManager.startUp();
    }

    @Override
    public void simpleUpdate(float tpf) {
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    @Override
    public RTSStageManager getStageManager() {
        return (RTSStageManager) this.stageManager;
    }

    @Override
    public RTSGameGUIManager getGameGUIManager() {
        return (RTSGameGUIManager) this.gameGUIManager;
    }

    @Override
    public RTSGameStateManager getGameStateManager() {
        return (RTSGameStateManager) this.gameStateManager;
    }

    public static void main(String args[]) {

        //Logger.getLogger("").setLevel(java.util.logging.Level.OFF);
        //Logger.getLogger("de.lessvoid.nifty.Nifty").setLevel(java.util.logging.Level.OFF);

        RTSGame app = new RTSGame();
        AppSettings settings = new AppSettings(true);
        //settings.setFullscreen(true);
        //settings.setResolution(-1, -1); // current width/height
        settings.setResolution(1024, 768);
        app.setDisplayStatView(true);
        app.setShowSettings(false);
        app.setSettings(settings);
        app.start();
    }

    public void pauseGameLoop() {
        paused = true;
    }

    public void continueGameLoop() {
        paused = false;
    }
}
