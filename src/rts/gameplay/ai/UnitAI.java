/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.gameplay.ai;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import rts.gameplay.RTSGamePlayManager;
import rts.RTSWorldManager;
import rts.core.entity.RTSSpatialEntity;
import rts.core.entity.RTSSpatialEntityControl;
import rts.fx.skill.LineControl;
import rts.gameplay.base.Country;
import rts.gameplay.base.RTSUnitBase;
import rts.gameplay.manager.UnitManager;
import sg.atom.world.SceneGraphHelper;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class UnitAI extends AbstractControl implements AnimEventListener {
    // simple state machine
    // decision tree

    String defaultState = "Standing";
    String currentState;
    String inEvent;
    boolean isActive;
    // 
    RTSUnitBase thisEntity;
    RTSSpatialEntity target;
    // Position
    //Vector3f currentPos;
    float speed = 50;
    Vector3f targetPos;
    // Helper Control
    AnimControl animControl;
    AnimChannel animChannel;
    RTSSpatialEntityControl spatialEntityControl;
    RTSGamePlayManager gamePlayManager;
    RTSWorldManager worldManager;
    private Country country;
    private float lastTpf;

    /*
     public UnitAI(SpatialEntity thisEntity, String currentState, boolean isActive) {
     this.currentState = currentState;
     this.isActive = isActive;
     this.thisEntity = thisEntity;
     }
     */
    public UnitAI(String currentState, RTSGamePlayManager gamePlayManager, UnitManager unitManager) {
        this.currentState = currentState;
        this.gamePlayManager = gamePlayManager;
        this.worldManager = (RTSWorldManager) gamePlayManager.getWorldManager();
    }

    public void setInEvent(String eventName) {
        inEvent = eventName;
    }

    public void changeState(String otherState) {
        currentState = otherState;
    }

    public void updateAI(float tpf) {
        lastTpf = tpf;
        if (currentState.equals("Standing")) {
            if (inEvent != null) {
                if (inEvent.equals("Move")) {
                    //changeState("Standing");
                    changeState("Moving");
                    playAniIfNot("Walk");
                    goToPos(targetPos, tpf);
                } else {
                    // Continue Stop
                    // Do nothing
                }
            }
        } else if (currentState.equals("Moving")) {
            if (inEvent != null) {
                if (inEvent.equals("Stop")) {
                    changeState("Standing");
                    playAniIfNot("Stand");
                    stop();
                } else {
                    // Continue moving
                    goToPos(targetPos, tpf);
                }
            }
        }
    }

    // Helper entity Funtions
    private void goToPos(Vector3f targetPos, float tpf) {
        //playAniIfNot("Run");
        boolean reach = false;
        Vector3f currentPos = thisEntity.getMapPosition();
        float distance = currentPos.distance(targetPos);
        System.out.println("(*) MOVE         Current:" + currentPos + " Target : " + targetPos + " Dis : " + distance);
        Vector3f newPos = new Vector3f();
        if (speed * tpf <= distance) {
            newPos = currentPos.clone().interpolate(targetPos, speed * tpf / distance);
            thisEntity.getSpatial().lookAt(newPos, Vector3f.UNIT_Y);
        } else {
            newPos.set(targetPos);
            reach = true;
        }
        //thisEntity.setCurrentPos(newPos);
        thisEntity.setMapPosition(newPos);
        worldManager.placeEntityOnTerrain(thisEntity);
        //Quaternion rot = new Quaternion().fromAxes(Vector3f.UNIT_Z, Vector3f.UNIT_Y, targetPos.subtract(currentPos));


        if (reach) {
            inEvent = "Stop";
        }
        //changeState("Standing");
    }

    private void stop() {
        //playAni("Stand");
    }

    private void playAni(String animName) {
        if (animChannel != null) {
            animChannel.setAnim(animName, 0.2f);
        }
    }

    private void playAniIfNot(String animName) {
        if (animChannel != null) {
            if (!animChannel.getAnimationName().equals(animName)) {
                animChannel.setAnim(animName, 0.2f);
            }
        }
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);

        try {
            // Animation Control
            animControl = SceneGraphHelper.travelDownFindFirstControl(spatial, AnimControl.class);
            animChannel = animControl.createChannel();
            animChannel.setAnim("Stand");
        } catch (NullPointerException ex) {
            throw new IllegalStateException("You have to add this Control after Animcontrol");
        }

        try {
            spatialEntityControl = spatial.getControl(RTSSpatialEntityControl.class);
            this.thisEntity = (RTSUnitBase) spatialEntityControl.getEntity();
            this.country = thisEntity.getCountry();
            initAIVar();
        } catch (NullPointerException ex) {
            throw new IllegalStateException("You have to add this Control after SpatialEntityControl");
        }
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

    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        // finish Anim
    }

    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
    }

    public void setTargetPos(Vector3f targetPos) {
        this.targetPos.set(targetPos);
    }

    private void initAIVar() {
        changeState("Standing");
        targetPos = new Vector3f();
    }

    /* WEAPON */
    public void attachWeapon() {
        // find the wepon node 
        // if null to root
        // attach
    }

    public void createWeaponEffect() {
        // skill
        // create effect via EffectManager
    }

    public void fireWeapon() {
    }

    public void fire() {
        // FIXME : Test Line
        RTSUnitBase enemy = country.findNearestEnemy(this.thisEntity);
        if (enemy != null) {
        } else {
            // Shoot to the ground. Position
            Vector3f endPos = thisEntity.getSpatial().getLocalTranslation().clone();
            endPos.addLocal(new Vector3f(4, 0, 4));

            LineControl fireLine = new LineControl();
            fireLine.setTarget(endPos);
            fireLine.setStart(thisEntity);

            this.spatial.addControl(fireLine);
            System.out.println(" Fire !");
            inEvent = "Move";
            setTargetPos(endPos.clone().addLocal(-4, 0, -9));
        }
    }
}
