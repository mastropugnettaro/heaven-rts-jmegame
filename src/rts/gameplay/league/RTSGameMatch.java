/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.gameplay.league;

import rts.gameplay.config.RTSMatchSetting;

/**
 *
 * @author hungcuong
 */
public class RTSGameMatch {

    RTSMatchSetting matchSetting;

    public RTSGameMatch(RTSMatchSetting matchSetting) {
        this.matchSetting = matchSetting;
    }

    public RTSMatchSetting getMatchSetting() {
        return matchSetting;
    }
}
