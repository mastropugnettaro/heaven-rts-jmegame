/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.abstraction.pathfinding;

import mrts.GameState;
import mrts.UnitAction;
import mrts.units.Unit;

/**
 *
 * @author santi
 */
public abstract class PathFinding {
    public abstract UnitAction findPath(Unit start, int targetpos, GameState gs);
    public abstract UnitAction findPathToAdjacentPosition(Unit start, int targetpos, GameState gs);
}
