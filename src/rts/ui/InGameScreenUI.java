/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.ui;

import com.jme3.math.Vector3f;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import rts.RTSGameGUIManager;
import rts.RTSStageManager;
import rts.stage.RTSSoundManager;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class InGameScreenUI implements ScreenController {

    RTSGameGUIManager gameGUIManager;
    private Element energyText, gasText;
    private Element mapElement;
    private Screen screen;
    private Element entityActionUI;
    RTSStageManager stageManager;

    public InGameScreenUI() {
    }

    public InGameScreenUI(RTSGameGUIManager gameGUIManager) {
        this.gameGUIManager = gameGUIManager;

    }

    public void onClick(String buttonId, String actionName) {
        System.out.println(" Clicked " + buttonId);
        //gameGUIManager.goToScreen("EndGameScreen");

    }

    public void onActionClick(String buttonId, String actionName) {
        System.out.println(" Clicked " + buttonId);
        //gameGUIManager.goToScreen("EndGameScreen");
        stageManager.doEntityAction(actionName);
    }

    void createUI(Nifty nifty, Screen screen) {
    }

    public void onStartScreen() {
        System.out.println("onStartScreen");
    }

    public void onEndScreen() {
        System.out.println("onEndScreen");
    }

    public void bind(Nifty nifty, Screen screen) {
        stageManager = (RTSStageManager) this.gameGUIManager.getApp().getStageManager();
        this.screen = screen;
        createUI(nifty, screen);
        this.energyText = screen.findElementByName("country_energy");
        this.gasText = screen.findElementByName("country_gas");
        energyText.getRenderer(TextRenderer.class).setText("100");
        gasText.getRenderer(TextRenderer.class).setText("200");
        this.mapElement = screen.findElementByName("mapImage");
        entityActionUI = screen.findElementByName("entityAction");
    }

    public void doTalk() {
        ((RTSSoundManager) this.gameGUIManager.getSoundManager()).talk();
    }

    public void doMapClick(int mx, int my) {

        // go to XY
        mx = mx - mapElement.getX();
        my = my - mapElement.getY();
        int mh = mapElement.getHeight();
        int mw = mapElement.getWidth();
        float sx = 1024 / mw;
        float sy = stageManager.getStartPos().y;
        float sz = 1024 / mh;

        float cx = 512;
        float cz = 512;
        Vector3f newMapPos = new Vector3f(mx * sx - cx, sy, my * sz - cz);
        System.out.println(" Mouse Map : " + mx + " " + my);
        stageManager.getRtsCam().setCenter(newMapPos);
    }
}
