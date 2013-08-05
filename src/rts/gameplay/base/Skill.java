package rts.gameplay.base;

import rts.core.entity.RTSEntityFunction;

/**
 *
 * @author cuong.nguyenmanh2
 */
public abstract class Skill extends RTSGameAction{


    public enum SkillRange {
    }

    public enum SkillType {
    }
    
    SkillUp upgrade;
    String picture;
    public abstract RTSEntityFunction getFunction();

    public void execute() {
        ((RTSUnitBase)owner).country.paidCost(this.cost);
    }

    public void affect(Object target) {
    }


}
