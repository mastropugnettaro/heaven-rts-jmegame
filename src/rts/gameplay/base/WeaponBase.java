package rts.gameplay.base;

import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.control.GhostControl;
import com.jme3.scene.Spatial;
import rts.gameplay.RTSGamePlayManager;
import sg.atom.logic.trigger.PhysicTrigger;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class WeaponBase {

    public static int STARTED = 0;
    String name;
    String modelPath;
    int damage;
    boolean enable = false;
    boolean allowDirect = false;
    RTSUnitBase target;
    RTSUnitBase owner;
    RTSGamePlayManager gamePlay;
    Spatial model;
    PhysicTrigger trigger;
    int status;

    public WeaponBase(String name, String modelPath) {
        this.name = name;
        this.modelPath = modelPath;
    }

    public void load() {
        // load and add it by EntityFactory
    }

    public void active() {
        // add Control
        //model.addControl(trigger);
        //trigger.addListener(this);
    }

    public void start() {
        // fly to target
    }

    public void hit() {
        // get notification by physic trigger
        // tell gameplay to decrease the point of opponent
        //RTSGamePlayeManager.getDefault().
    }

    public void missed() {
    }

    public void end() {
    }

    public void enter(Spatial sp) {
    }

    public void enter(GhostControl gc) {
    }

    public void enter(PhysicsCollisionObject gc) {
    }
}
