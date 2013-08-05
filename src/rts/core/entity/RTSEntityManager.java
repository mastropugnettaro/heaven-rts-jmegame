/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.core.entity;

import rts.RTSStageManager;
import rts.RTSWorldManager;
import rts.world.map.Map;
import rts.gameplay.base.Resource;
import rts.gameplay.base.Unit;
import sg.atom.entity.EntityManager;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class RTSEntityManager extends EntityManager {

    static RTSEntityManager singleton;

    static RTSEntityManager getInstance() {
        return singleton;
    }

    public RTSEntityManager(RTSStageManager stageManager) {
        super(stageManager);
        this.entityFactory = new RTSEntityFactory(this, stageManager);
        singleton = this;
    }

    public void loadEntitesFromMap(Map map) {
        for (Resource resource : map.resources) {
            addEntity(resource);
        }
    }

    public void addEntityToWorld(Unit newUnit) {
        addEntity(newUnit);
        RTSWorldManager worldManager = (RTSWorldManager) stageManager.getWorldManager();
        worldManager.loadAndAttachSpatialEntity(newUnit);
        worldManager.placeEntityOnTerrain(newUnit);
    }
}
