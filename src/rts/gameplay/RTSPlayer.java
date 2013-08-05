/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.gameplay;

import rts.gameplay.base.Country;
import rts.gameplay.league.RTSGameMatch;
import rts.gameplay.league.RTSGamePolicy;
import rts.gameplay.league.RTSPlayerTeam;
import sg.atom.core.StageManager;
import sg.atom.gameplay.player.Player;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class RTSPlayer extends Player {

    public enum PlayerStatus {
    }
    PlayerStatus playerStatus;
    String name;
    String nick;
    int level;
    int exp;
    int score;
    int status;
    public boolean isAI;
    String avatar;
    Country country;
    RTSPlayerTeam team;
    RTSGameMatch currentMatch;
    RTSGamePolicy playerPolicies;

    public RTSPlayer(StageManager stageManager, String name) {
        super(stageManager, name);
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
        country.setPlayer(this);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
