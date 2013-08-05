/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.gameplay.base;

import com.jme3.scene.Spatial;
import java.util.ArrayList;
import rts.core.entity.RTSEntityFunction;
import rts.core.entity.RTSSpatialEntity;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class Resource extends RTSSpatialEntity {

    public enum ResourceTypes {

        Energy, GasMine, Wood, Food, Gem
    }
    boolean canHarvest;
    boolean harvesting;
    RTSCost each;
    RTSCost total;

    public Resource(String type) {
        this("Resource", type);
    }

    public Resource(ResourceTypes type) {
        this("Resource", type.name());
    }

    public Resource(String name, String type) {
        super(name, type);
    }

    public Resource(Long id, String name, String type, Spatial spatial) {
        super(id, name, type, spatial);
    }

    public Resource(Long id, ResourceTypes type) {
        super(id, "Resource", type.name());
        this.canHarvest = true;
        this.harvesting = false;
    }
}
