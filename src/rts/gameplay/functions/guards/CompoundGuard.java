/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.gameplay.functions.guards;

import com.continuent.tungsten.fsm.core.Entity;
import com.continuent.tungsten.fsm.core.Event;
import com.continuent.tungsten.fsm.core.Guard;
import com.continuent.tungsten.fsm.core.State;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class CompoundGuard implements Guard {
    String type = "And";
    ArrayList<Guard> guardList = new ArrayList<Guard>();

    public CompoundGuard() {
    }

    public CompoundGuard(Guard... g) {
        Collections.addAll(guardList, g);
    }

    public void addGuard(Guard... g) {
        Collections.addAll(guardList, g);
    }

    public boolean accept(Event event, Entity entity, State state) {
        if (type.equalsIgnoreCase("And")) {
            for (Guard g : guardList) {
                if (!g.accept(event, entity, state)) {
                    return false;
                }
            }
            return true;
        } else if (type.equalsIgnoreCase("Or")) {
            for (Guard g : guardList) {
                if (g.accept(event, entity, state)) {
                    return true;
                }
            }
            return false;
        } else if (type.equalsIgnoreCase("Possitive")) {
            return false;
        } else if (type.equalsIgnoreCase("Negative")) {
            return false;
        }
        return true;
    }
    
}
