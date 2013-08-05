/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import rts.RTSGame;
import rts.RTSGameGUIManager;
import rts.RTSGameStateManager;
import rts.RTSStageManager;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class InGameState extends AbstractAppState {

    RTSGameGUIManager gameGUIManager;
    RTSStageManager stageManager;
    private RTSGame app;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        this.app = (RTSGame) app;
        this.gameGUIManager = this.app.getGameGUIManager();
        this.stageManager = this.app.getStageManager();
        
        if (gameGUIManager==null){
            
        }
        setEnabled(true);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        if (enabled) {
            //stageManager.initStage();
            stageManager.goInGame();
            gameGUIManager.goInGame();
        }
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);

        // units
        // building
        //miniView
        gameGUIManager.simpleUpdate(tpf);
        stageManager.simpleUpdate(tpf);
    }
}
