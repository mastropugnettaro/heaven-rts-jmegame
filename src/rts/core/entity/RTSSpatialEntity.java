package rts.core.entity;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.HashMap;
import rts.gameplay.actions.RTSAction;
import sg.atom.entity.SpatialEntity;

/**
 *
 * @author cuong.nguyenmanh2
 */
public abstract class RTSSpatialEntity extends SpatialEntity {

    ArrayList<RTSAction> actions = new ArrayList<RTSAction>();
    protected HashMap<String, RTSEntityFunction> functionMap = new HashMap<String, RTSEntityFunction>();
    Vector3f mapPosition = new Vector3f();

    public RTSSpatialEntity(String name, String type) {
        super(new Long(-1), name, type);
    }

    public RTSSpatialEntity(Long id, String name, String type) {
        super(id, name, type);
    }

    public RTSSpatialEntity(String name, String type, Spatial spatial) {
        this(new Long(-1), name, type, spatial);
    }

    public RTSSpatialEntity(Long id, String name, String type, Spatial spatial) {
        super(id, name, type);
    }

    public void addAction(RTSAction action) {
        actions.add(action);
    }

    public HashMap<String, RTSEntityFunction> getFunctionMap() {
        return functionMap;
    }

    // RTS Specific functions
    public RTSEntityManager getEntityManager() {
        return RTSEntityManager.getInstance();
    }

    public void doAction(String actionName) {
        System.out.println(" do Action " + actionName);
    }

    public Vector3f getMapPosition() {
        return mapPosition;
    }

    public void setMapPosition(Vector3f mapPosition) {
        this.mapPosition.set(mapPosition);
    }

    @Override
    public String toString() {
        return this.name +" Type: "+ this.type  +" Group: "+ this.group;
    }
}
