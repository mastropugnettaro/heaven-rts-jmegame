/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.gameplay.base;

import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingSphere;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import rts.gameplay.ai.BuildingAI;
import rts.gameplay.building.TroopInfo;
import rts.gameplay.units.Fighter;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class Building extends RTSUnitBase {

    // Require building
    ArrayList<Building> requiredBuiling;
    ArrayList<TroopInfo> troopQueue = new ArrayList<TroopInfo>();
    Vector3f gatherPoint;
    public int troopNum = 0;

    ArrayList <SkillUp> upgradeList;
    enum BuildingType {

        FighterBuilding, MainBuilding, GatherBuilding, UnitBuilding
    };

    public Building(String name, String type) {
        super(name, type);
    }

    public Building(String name, String type, Spatial spatial) {
        super(name, type, spatial);
    }

    public Building(Long id, String name, String type) {
        super(id, name, type);
    }
      

    public Vector3f getGatherPoint() {
        return gatherPoint;
    }

    public ArrayList<TroopInfo> getTroopQueue() {
        return troopQueue;
    }

    BuildingAI getAIController() {
        return getSpatial().getControl(BuildingAI.class);
    }

    public void upgradeToNextLevel(float tpf) {
    }

    public void cancelTroop() {
    }

    public void makingTroop(float tpf) {
        // 
        //System.out.println("Making troop");

        if (!getTroopQueue().isEmpty()) {
            TroopInfo firstTroop = getTroopQueue().get(0);
            firstTroop.remainTime = firstTroop.remainTime - tpf;
            if (firstTroop.remainTime < 0) {
                // remove 
                float rad = 1;
                Spatial model = getSpatial();
                if (model.getWorldBound() instanceof BoundingBox) {
                    rad = ((BoundingBox) model.getWorldBound()).getXExtent();
                    //System.out.println(" BoundingBox: " + size);
                } else if (model.getWorldBound() instanceof BoundingSphere) {
                    rad = ((BoundingSphere) model.getWorldBound()).getRadius();
                    //System.out.println(" BoundingSphere: " + size);
                }
                float angle = FastMath.TWO_PI / 8 * troopNum;
                Quaternion q = new Quaternion().fromAngleAxis(angle, new Vector3f(0, 1, 0));
                Vector3f rotVec = q.mult(new Vector3f(1, 0, 0).mult(rad + 2f));
                targetPos = getMapPosition().add(rotVec).clone();

                System.out.println(" One Troop out");
                getTroopQueue().remove(firstTroop);
                Unit newUnit = firstTroop.type;
                newUnit.setMapPosition(targetPos);
                troopNum++;

                getEntityManager().addEntityToWorld(newUnit);

            }

        }
    }

    public void queueTroop() {
        TroopInfo ti = new TroopInfo();
        ti.type = new Fighter("Fighter1");
        ti.totalTime = 5;
        ti.remainTime = 5;
        ti.order = 1;
        getTroopQueue().add(ti);
        System.out.println(" Queue a troop");
    }
}
