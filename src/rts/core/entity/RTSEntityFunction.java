package rts.core.entity;

import rts.gameplay.base.RTSCost;
import rts.gameplay.actions.RTSAction;
import sg.atom.entity.EntityFunction;

/**
 *
 * @author hungcuong
 */
public class RTSEntityFunction extends EntityFunction {

    String imgPath;
    int status = -1;
    RTSCost cost;
    
    public RTSEntityFunction(String name, String title, String info, String imgPath) {
        super(name, title, info);
        this.imgPath = imgPath;

    }

    public String getImgPath() {
        return imgPath;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    public void activeFunction(){
        
    }
    
    public RTSAction createAction(){
        return null;
        
    }
}
