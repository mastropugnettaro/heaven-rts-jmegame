package rts.gameplay.league;

import java.util.ArrayList;
import rts.gameplay.RTSPlayer;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class RTSPlayerTeam {

    ArrayList<RTSPlayer> players;
    private String name;
    String icon;
    String logo;
    int totalPoint;

    
    public RTSPlayerTeam(String name) {
        this.name = name;
        players = new ArrayList<RTSPlayer>();
    }

    public ArrayList<RTSPlayer> getPlayers() {
        return players;
    }
    
    
}
