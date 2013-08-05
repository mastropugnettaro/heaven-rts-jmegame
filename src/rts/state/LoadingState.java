/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.state;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import de.lessvoid.nifty.screen.Screen;
import java.util.concurrent.Callable;
import rts.RTSGame;
import rts.RTSGameGUIManager;
import rts.RTSGameStateManager;
import rts.RTSStageManager;
import sg.atom.state.LoadingAppState;
import sg.atom.ui.common.UILoadingScreenController;

/**
 * The LoadingState be executed when the Game is loaded. This State will
 * introduce some Task which run parallel with the orginal thread but the attach
 * part are in the render thread . It also run a progressbar and report the
 * loading percentage and infos.
 *
 * @author cuong.nguyenmanh2
 */
public class LoadingState extends LoadingAppState {

    private Node rootNode;
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private InputManager inputManager;
    private ViewPort viewPort;
    private UILoadingScreenController guiController;
    private RTSGameStateManager gameStateManager;
    private Screen loadingScreen;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (RTSGame) app; // can cast Application to something more specific

        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        this.inputManager = this.app.getInputManager();
        this.gameStateManager = (RTSGameStateManager) this.app.getGameStateManager();
        this.viewPort = this.app.getViewPort();

        if (this.app.getGameGUIManager() == null) {
            this.app.initGUI();
        }
        this.gameGUIManager = (RTSGameGUIManager) this.app.getGameGUIManager();
        if (this.app.getStageManager() == null) {
            this.app.initStage();
        }
        this.stageManager = (RTSStageManager) this.app.getStageManager();
        //System.out.println("initialize!");
        stageManager.initStage();
        setEnabled(true);

    }

    @Override
    public void setEnabled(boolean enabled) {
        // Pause and unpause
        super.setEnabled(enabled);
        if (enabled) {
            initPhase();
        } else {
            if (loadComplete) {
                //nextState();
            }

        }
    }

    @Override
    protected void initPhase() {
        gameGUIManager.loadScreen("loadingScreen");
        loadingScreen = gameGUIManager.getNifty().getScreen("loadingScreen");
        if (loadingScreen == null) {
            throw new RuntimeException("May be: You didn't add the LoadingScreen in XML yet!");
        } else {
            guiController = (UILoadingScreenController) loadingScreen.getScreenController();
        }
    }

    @Override
    protected void loadPhase() {
        stageManager.loadStage();
        System.out.println("Finish Loading !");
        stageManager.configStage();
        System.out.println("Finish Config !");
    }

    @Override
    protected void finishPhase() {
        stageManager.attachStage();
    }

    @Override
    protected void nextState() {
        gameStateManager.goInGame();
    }

    @Override
    protected void watchTask() {
        // Wait for the GUI controller to finish screen changing
        Screen currentScreen = gameGUIManager.getNifty().getCurrentScreen();
        if (guiController != null && currentScreen != null && !currentScreen.isNull()) {
            if (currentScreen.getScreenId().equals("loadingScreen")) {
                if (stageManager.getProcessInfo().getCurrentProgressName() != null) {
                    float currentProcess = stageManager.getProcessInfo().getCurrentProgressPercent();
                    if (oldPercent != currentProcess) {
                        guiController.setProgress(currentProcess, stageManager.getProcessInfo().getCurrentProgressName());
                        oldPercent = currentProcess;


                    }
                }
            }
        }
    }

    public void updateProgressBar(boolean hasError, String errorMsg) {
        // Wait for the GUI controller to finish screen changing
        if (guiController != null && gameGUIManager.getNifty().getCurrentScreen().getScreenId().equals("loadingScreen")) {
            if (hasError) {
                //
                guiController.setProgress(0, "Error ! Press Esc to back to main menu :" + errorMsg);
            } else {
                if (stageManager.getProcessInfo().getCurrentProgressName() != null) {
                    float currentProcess = stageManager.getProcessInfo().getCurrentProgressPercent();
                    if (oldPercent != currentProcess) {
                        guiController.setProgress(currentProcess, stageManager.getProcessInfo().getCurrentProgressName());
                        oldPercent = currentProcess;

                        System.out.println("Load :" + oldPercent);
                    }
                }
            }
        }
    }
}
