/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.gameplay.building;

import rts.gameplay.base.Building;
import rts.core.entity.RTSEntityFunction;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class FighterBuidling extends Building {

    public FighterBuidling(String name) {
        super(name, "FighterBuidling");

        functionMap.put("Move",new RTSEntityFunction("Move", "Move", "Move a unit", "Interface/InGame/images/parts/button1.png"));
        functionMap.put("Acttack",new RTSEntityFunction("Acttack", "Acttack", "Acttack a unit", "Interface/InGame/images/parts/button2.png"));
        functionMap.put("Defense",new RTSEntityFunction("Defense", "Defense", "Defense a unit", "Interface/InGame/images/parts/button3.png"));
        functionMap.put("UnitA",new RTSEntityFunction("UnitA", "QueueUnitA", "Queue Unit A", "Interface/InGame/images/unitpics/aafus.png"));
        functionMap.put("UnitB",new RTSEntityFunction("UnitB", "QueueUnitB", "Queue Unit B", "Interface/InGame/images/unitpics/arm_jammer.png"));
    }
    
        @Override
    public void doAction(String actionName) {
        super.doAction(actionName);
        
        if (actionName.equals("UnitA")){
            queueTroop();
        }
    }
}
