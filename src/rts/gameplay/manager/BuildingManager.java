/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.gameplay.manager;

import com.jme3.scene.Spatial;
import java.util.ArrayList;
import rts.gameplay.RTSGamePlayManager;
import rts.RTSWorldManager;
import rts.gameplay.ai.BuildingAI;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class BuildingManager {

    RTSGamePlayManager gamePlayManager;
    RTSWorldManager worldManager;

    public enum UnitTypes {

        A, B, C
    }
    ArrayList<String> typeNames;

    public BuildingManager(RTSGamePlayManager gamePlayManager) {
        this.gamePlayManager = gamePlayManager;
        this.worldManager = (RTSWorldManager) gamePlayManager.getWorldManager();
    }

    public void createBuildingControl(Spatial model) {
        model.addControl(new BuildingAI("Standing", gamePlayManager, this));
    }
}
