/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.stage;

import com.jme3.math.ColorRGBA;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.ColorOverlayFilter;
import sg.atom.core.StageManager;
import sg.atom.fx.ScreenEffectManager;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class RTSScreenEffectManager extends ScreenEffectManager {

    public RTSScreenEffectManager(StageManager stageManager) {
        super(stageManager);
    }
    private FilterPostProcessor fpp;

    public void setupFilters() {
        fpp = new FilterPostProcessor(assetManager);
        // fpp.setNumSamples(4);
        fpp.addFilter(new ColorOverlayFilter(ColorRGBA.DarkGray));
        //fpp.addFilter(new RadialBlurFilter());
        //fade=new FadeFilter(1.0f);
        //fpp.addFilter(fade);
        stageManager.getApp().getViewPort().addProcessor(fpp);
    }
}
