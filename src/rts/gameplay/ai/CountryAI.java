package rts.gameplay.ai;

import rts.gameplay.RTSGamePlayManager;
import rts.gameplay.base.Country;
import rts.gameplay.base.RTSUnitBase;
import rts.world.RTSGameWorldView;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class CountryAI {
    /* Goal driven architect */

    Country country;

    public CountryAI(Country country) {
        this.country = country;
    }

    public void update(float tpf) {
        // assume we have updated view into the world's map (fog of war) of the country
        RTSGameWorldView worldView = RTSGamePlayManager.getDefaultInstance().getWorldView(this.country.getPlayer());

        // do something with the worldView
        
        decideNextCountryAction();
    }

    private void decideNextCountryAction() {
        listAllActionAvailable();
        calculateCostOfPresumeAction();
    }

    private void calculateCostOfPresumeAction() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void listAllActionAvailable() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    

}
