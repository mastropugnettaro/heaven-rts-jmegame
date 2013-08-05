/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.gameplay.base;

import com.jme3.scene.Spatial;
import java.util.ArrayList;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class Unit extends RTSUnitBase {

    ArrayList<Skill> skill;
    public int energy;
    public int intel;
    public int maxHP;
    // movement 
    float speed;
    float veclocity;
    float initSpeed;
    float initVec;
    Resource carryingResource;

    public Unit(String name, String type) {
        super(name, type);
    }

    public Unit(Long id, String name, String type, Spatial spatial) {
        super(id, name, type, spatial);
    }

    public Unit(Long id, String name, String type) {
        super(id, name, type);
    }
}
