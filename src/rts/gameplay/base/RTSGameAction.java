/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.gameplay.base;

import rts.core.entity.RTSSpatialEntity;

/**
 *
 * @author hungcuong
 */
public class RTSGameAction {

    public enum ActionStatus {

        Enable(0), Disable(1);
        int value;

        ActionStatus(int value) {
            this.value = value;

        }
    }
    ActionStatus status;
    String title;
    String description;
    RTSSpatialEntity owner;
    RTSCost cost;
    RTSCost earn;
    
    public RTSCost getCost() {
        return cost;
    }

    public RTSCost getEarn() {
        return earn;
    }
    public void getFutureEarn(Object presume) {
    }
}
