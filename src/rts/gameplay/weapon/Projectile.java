package rts.gameplay.weapon;

import rts.gameplay.base.RTSUnitBase;
import rts.gameplay.base.WeaponBase;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class Projectile extends WeaponBase {

    int shotPoint;
    int timeTravel;
    int timeLife;
    int energy;

    public Projectile(String name, String modelPath) {
        super(name, modelPath);
    }

    public void create() {
    }

    public void init() {
    }

    @Override
    public void start() {
    }

    public void update() {
        // entity update or ControlUpdate
    }

    @Override
    public void hit() {
        // who?
        RTSUnitBase hitTarget;
        // correct target
/*
         // direct
         target.damaged(damage);
         owner.earn(shotPoint);

         // indirect
         gamePlay.damageReport(owner, this, target);
         */
    }

    public void missed() {
    }
}
