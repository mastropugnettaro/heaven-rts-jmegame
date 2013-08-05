/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.gameplay.functions.guards;

import com.continuent.tungsten.fsm.core.Entity;
import com.continuent.tungsten.fsm.core.Event;
import com.continuent.tungsten.fsm.core.Guard;
import com.continuent.tungsten.fsm.core.State;
import rts.gameplay.base.Country;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class EntitySelectGuard implements Guard {

    String filterType;
    String className;
    String gameCondition;
    float distance;
    Country country;

    public EntitySelectGuard(String filterType) {
        this.filterType = filterType;
    }

    public EntitySelectGuard(String filterType, String className) {
        this.filterType = filterType;
        this.className = className;
    }

    public EntitySelectGuard(String filterType, String className, String gameCondition) {
        this.filterType = filterType;
        this.className = className;
        this.gameCondition = gameCondition;

    }

    public EntitySelectGuard(String filterType, String className, String gameCondition, float distance) {
        this.filterType = filterType;
        this.className = className;
        this.gameCondition = gameCondition;
        this.distance = distance;
    }

    public EntitySelectGuard(String filterType, String className, String gameCondition, float distance, Country country) {
        this.filterType = filterType;
        this.className = className;
        this.gameCondition = gameCondition;
        this.distance = distance;
        this.country = country;
    }

    public boolean accept(Event event, Entity entity, State state) {
        if (filterType.equals("Null")) {
            return true;
        } else if (filterType.equals("NotAny")) {
            return true;
        } else if (filterType.equals("Any")) {
            return false;
        } else if (filterType.equals("Single")) {
            return true;
        } else if (filterType.equals("SingleInClass")) {
            return true;
        }

        if (gameCondition.equals("CurrentPlayer")) {
        } else if (gameCondition.equals("NotCurrentPlayer")) {
        } else if (gameCondition.equals("InTeam")) {
        } else if (gameCondition.equals("EnemyTeam")) {
        }
        return true;
    }
}
