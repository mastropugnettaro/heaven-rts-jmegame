package rts.gameplay.base;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import rts.core.entity.RTSSpatialEntity;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class RTSUnitBase extends RTSSpatialEntity {

    protected Vector3f targetPos;
    public int hp;
    public int level;
    RTSCost cost;
    Country country;

    public RTSUnitBase(String name, String type) {
        super(name, type);
    }

    public RTSUnitBase(String name, String type, Spatial spatial) {
        super(name, type, spatial);
    }

    public RTSUnitBase(Long id, String name, String type, Spatial spatial) {
        super(id, name, type, spatial);
    }

    public RTSUnitBase(Long id, String name, String type) {
        super(id, name, type);
    }

    public void damaged(int damage) {
    }

    public void earn(int shotPoint) {
    }

    public Country getCountry() {
        return country;
    }

    public RTSCost getCost() {
        return cost;
    }

    public int getHp() {
        return hp;
    }

    public int getLevel() {
        return level;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setCost(RTSCost cost) {
        this.cost = cost;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
