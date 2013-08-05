/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.gameplay.manager;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.List;
import rts.gameplay.RTSGamePlayManager;
import rts.RTSWorldManager;
import rts.gameplay.ai.UnitAI;
import rts.core.entity.RTSSpatialEntity;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class UnitManager {

    RTSGamePlayManager gamePlayManager;

    public void createUnitControl(Spatial model) {
        model.addControl(new UnitAI("Standing", gamePlayManager, this));
    }

    public enum UnitTypes {

        A, B, C
    }
    ArrayList<String> typeNames;

    public UnitManager(RTSGamePlayManager gamePlayManager) {
        this.gamePlayManager = gamePlayManager;
    }

    public List<String> getTypeNames() {
        return typeNames;
    }

    public void doAction(RTSSpatialEntity selectedEntity, String actionName) {

        UnitAI aiControl = selectedEntity.getControl(UnitAI.class);
        if (aiControl != null) {
            System.out.println(" " + aiControl);
            if (actionName.equals("move")) {
                aiControl.setInEvent("Move");
            } else {
                aiControl.setInEvent("Stop");
            }
        }
    }

    public void moveUnitTo(RTSSpatialEntity selectedEntity, Vector3f targetPos) {
        UnitAI aiControl = selectedEntity.getControl(UnitAI.class);
        if (aiControl != null) {
            aiControl.setTargetPos(targetPos);
            aiControl.setInEvent("Move");
        }
    }
}
