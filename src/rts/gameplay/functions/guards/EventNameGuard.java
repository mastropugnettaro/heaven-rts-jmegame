/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.gameplay.functions.guards;

import com.continuent.tungsten.fsm.core.Entity;
import com.continuent.tungsten.fsm.core.Event;
import com.continuent.tungsten.fsm.core.Guard;
import com.continuent.tungsten.fsm.core.State;
import rts.gameplay.functions.RTSFunctionEvent;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class EventNameGuard implements Guard {
    String name;

    public EventNameGuard(String name) {
        this.name = name;
    }

    public boolean accept(Event event, Entity entity, State state) {
        RTSFunctionEvent rtsEvent = (RTSFunctionEvent) event;
        return rtsEvent.getData().equals(name);
    }
    
}
