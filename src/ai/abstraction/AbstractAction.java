/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.abstraction;

import mrts.GameState;
import mrts.UnitAction;
import mrts.units.Unit;

/**
 *
 * @author santi
 */
public abstract class AbstractAction {
    
    Unit unit;
    
    public AbstractAction(Unit a_unit) {
        unit = a_unit;
    }
    
    public abstract boolean completed(GameState pgs);
    public abstract UnitAction execute(GameState pgs);
}
