/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.core.entity;

import sg.atom.entity.SpatialEntity;
import sg.atom.entity.SpatialEntityControl;
import sg.atom.stage.WorldManager;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class RTSSpatialEntityControl extends SpatialEntityControl {

    public RTSSpatialEntityControl(WorldManager worldManager, SpatialEntity spatialEntity) {
        super(worldManager, spatialEntity);
    }
}
