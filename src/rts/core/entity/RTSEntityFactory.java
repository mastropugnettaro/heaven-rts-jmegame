/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.core.entity;

import com.jme3.scene.Spatial;
import rts.gameplay.RTSGamePlayManager;
import rts.RTSStageManager;
import rts.RTSWorldManager;
import rts.gameplay.base.Building;
import rts.gameplay.manager.BuildingManager;
import rts.gameplay.base.Unit;
import rts.gameplay.manager.UnitManager;
import sg.atom.entity.EntityFactory;
import sg.atom.stage.select.control.CircleSelectMark;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class RTSEntityFactory extends EntityFactory {

    public RTSEntityFactory(RTSEntityManager entityManager, RTSStageManager stageManager) {
        super(entityManager, stageManager);
    }

    public void createEntity(RTSSpatialEntity se) {
        //Spatial orginal = new Geometry("Box");
        String setype = se.type;
        //System.out.println(" " + setype);

        Spatial orginalModel = ((RTSWorldManager) worldManager).getPreloadModels().get(setype);
        Spatial model = orginalModel.clone();

        model.setName(se.name);
        se.setSpatial(model);

        model.addControl(new RTSSpatialEntityControl((RTSWorldManager) worldManager, se));
        model.addControl(new CircleSelectMark((RTSWorldManager) worldManager));

        if (Unit.class.isAssignableFrom(se.getClass())) {
            UnitManager unitManager = stageManager.getGamePlayManager(RTSGamePlayManager.class).getUnitManager();
            unitManager.createUnitControl(model);

        } else if (Building.class.isAssignableFrom(se.getClass())) {
            BuildingManager buildingManager = stageManager.getGamePlayManager(RTSGamePlayManager.class).getBuildingManager();
            buildingManager.createBuildingControl(model);
        }

    }
}
