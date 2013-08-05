/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts;

import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import rts.state.InGameState;
import rts.state.LoadingState;
import rts.state.MainMenuState;
import sg.atom.core.GameStateManager;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class RTSGameStateManager extends GameStateManager{

    private Class<? extends AppState> startupClass;


    public RTSGameStateManager(RTSGame app) {
        super(app);

    }

    public void goInGame() {
        LoadingState loadingState = stateManager.getState(LoadingState.class);
        boolean detached = stateManager.detach(loadingState);
        stateManager.attach(new InGameState());
    }

    @Override
    public void loadGame() {
        MainMenuState menuState = stateManager.getState(MainMenuState.class);
        boolean detached = stateManager.detach(menuState);
        stateManager.attach(new LoadingState());
    }
}
