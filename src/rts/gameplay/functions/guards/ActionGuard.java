/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.gameplay.functions.guards;

import com.continuent.tungsten.fsm.core.Entity;
import com.continuent.tungsten.fsm.core.Event;
import com.continuent.tungsten.fsm.core.Guard;
import com.continuent.tungsten.fsm.core.State;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class ActionGuard implements Guard {

    public ActionGuard() {
    }

    public boolean accept(Event event, Entity entity, State state) {
        return false;
    }
    
}
