/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.gameplay.units;

import rts.core.entity.RTSEntityFunction;
import rts.gameplay.ai.UnitAI;
import rts.gameplay.base.Unit;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class Fighter extends Unit {

    public Fighter(String name) {
        super(name, "Warrior");

        functionMap.put("Move", new RTSEntityFunction("Move", "Move", "Move a unit", "Interface/InGame/images/parts/button1.png"));
        functionMap.put("Attack", new RTSEntityFunction("Attack", "Attack", "Attack a unit", "Interface/InGame/images/parts/button2.png"));
        functionMap.put("Defense", new RTSEntityFunction("Defense", "Defense", "Defense a unit", "Interface/InGame/images/parts/button3.png"));
        functionMap.put("Cancel", new RTSEntityFunction("Cancel", "Cancel", "Defense a unit", "Interface/InGame/images/parts/button1.png"));
        functionMap.put("Fly", new RTSEntityFunction("Fly", "Fly", "Defense a unit", "Interface/InGame/images/parts/button2.png"));
    }

    @Override
    public void doAction(String actionName) {
        super.doAction(actionName);

        if (actionName.equals("Attack")) {
            UnitAI ai = this.getSpatial().getControl(UnitAI.class);
            ai.fire();
        }
    }
}
