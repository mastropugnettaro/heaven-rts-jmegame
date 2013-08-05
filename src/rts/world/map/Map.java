/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.world.map;

import rts.gameplay.base.Resource;
import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.Random;
import rts.core.entity.RTSEntityManager;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class Map {

    //public ArrayList<Building> buildings = new ArrayList<Building>(40);
    //public ArrayList<Unit> units = new ArrayList<Unit>(40);
    public ArrayList<Resource> resources = new ArrayList<Resource>(40);
    public String name;
    public float height;
    public float width;

    public Map(String name) {
        this.name = name;
        this.height = 200;
        this.width = 200;
    }
    Random random = new Random();

    public void createRandomMap(RTSEntityManager entityManager) {
        System.out.println(" Create random map");

        for (int i = 0; i <= 10; i++) {
            Resource.ResourceTypes type = Resource.ResourceTypes.values()[i % 2];
            //System.out.println(" " + type.name());
            Resource resource = new Resource(type.name());
            resource.setMapPosition(randomVec3(20, 200));
            resources.add(resource);
        }
    }

    Vector3f randomVec3(float min, float max) {
        float limit = min - max;
        float x = min + random.nextFloat() * limit;
        float y = min + random.nextFloat() * limit;
        float z = min + random.nextFloat() * limit;
        return new Vector3f(x, 0, z);
    }
}
