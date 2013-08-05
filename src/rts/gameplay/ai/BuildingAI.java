/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.gameplay.ai;

import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingSphere;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import rts.gameplay.RTSGamePlayManager;
import rts.RTSWorldManager;
import rts.core.entity.RTSSpatialEntityControl;
import rts.gameplay.base.Building;
import rts.gameplay.manager.BuildingManager;
import rts.gameplay.building.TroopInfo;
import rts.gameplay.base.Unit;
import rts.gameplay.units.Fighter;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class BuildingAI extends AbstractControl {
    // Simple state machine

    String defaultState = "Standing";
    String currentState;
    String inEvent;
    
    boolean isActive;
    Vector3f targetPos;
    private RTSSpatialEntityControl spatialEntityControl;
    private Building thisEntity;
    RTSGamePlayManager gamePlayManager;
    RTSWorldManager worldManager;

    public BuildingAI(String currentState, RTSGamePlayManager gamePlayManager, BuildingManager buildingManager) {
        this.currentState = currentState;
        this.gamePlayManager = gamePlayManager;
        this.worldManager = (RTSWorldManager) gamePlayManager.getWorldManager();
    }

    @Override
    protected void controlUpdate(float tpf) {
        updateAI(tpf);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    public Control cloneForSpatial(Spatial spatial) {
        return this;
    }

    private void updateAI(float tpf) {
        if (currentState.equals("Idle")) {
            if (inEvent != null) {
                if (inEvent.equals("Troop")) {
                    changeState("Troop");
                    thisEntity.makingTroop(tpf);
                    //changeState("Moving");

                } else if (inEvent.equals("Upgrade")) {
                    changeState("Upgrade");
                    thisEntity.upgradeToNextLevel(tpf);
                    //changeState("Moving");

                } else {
                    // Continue Stop
                    // Do nothing
                }
            }
        } else if (currentState.equals("Troop")) {
            if (inEvent != null) {
                if (inEvent.equals("Cancel")) {
                    changeState("Idle");
                    thisEntity.cancelTroop();
                }
            } else {
                // Continue making troop
                thisEntity.makingTroop(tpf);
            }
        } else if (currentState.equals("Upgrade")) {
            if (inEvent != null) {
                if (inEvent.equals("Cancel")) {
                    changeState("Idle");
                    cancelUpgrade();
                }
            } else {
                // Continue making troop
                thisEntity.upgradeToNextLevel(tpf);
            }
        }
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        try {
            // Animation Control
            /*
             animControl = SceneGraphHelper.travelDownFindFirstControl(spatial, AnimControl.class);
             animChannel = animControl.createChannel();
             animChannel.setAnim("Standingaround");
            
             */
        } catch (NullPointerException ex) {
            throw new IllegalStateException("You have to add this Control after Animcontrol");
        }

        try {
            spatialEntityControl = spatial.getControl(RTSSpatialEntityControl.class);
            this.thisEntity = (Building) spatialEntityControl.getEntity();
            initAIVar();
        } catch (NullPointerException ex) {
            throw new IllegalStateException("You have to add this Control after SpatialEntityControl");
        }
    }

    private void changeState(String otherState) {
        currentState = otherState;
    }



    private void cancelUpgrade() {
    }

    private void initAIVar() {
        changeState("Troop");
        targetPos = new Vector3f();

    }

    public void setTargetPos(Vector3f targetPos) {
        
    }
}
