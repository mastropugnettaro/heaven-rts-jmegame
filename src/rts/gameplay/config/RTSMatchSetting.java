/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.gameplay.config;

import rts.gameplay.RTSGamePlay;
import java.util.WeakHashMap;
import rts.gameplay.RTSPlayer;

/**
 *
 * @author hungcuong
 */
public class RTSMatchSetting {

    WeakHashMap<RTSPlayer, RTSPlayerRole> roleMap;
    int numOfSlot;

    // Map
    //Victory Condition 
    public static enum VictoryCondition {

        Conquest
    }
    
    VictoryCondition victoryCondition;
    RTSGamePlay startAge;
    int populationLimit = 200;
}
