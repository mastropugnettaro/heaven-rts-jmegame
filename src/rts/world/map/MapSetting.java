/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.world.map;

/**
 *
 * @author hungcuong
 */
public class MapSetting {

    public static enum MapSize {

        Small(80), Normal(100), Huge(120), Gigantic(140);
        int size;

        MapSize(int v) {
            this.size = v;
        }
    }

    public static enum MapType {

        Continental, HillCountry, RiverSide, Coast, Island, Desert
    }
    int size;
    int type;
}
