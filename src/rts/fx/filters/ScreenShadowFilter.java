/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.fx.filters;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector4f;
import com.jme3.post.Filter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.texture.Texture;

/**
 *
 * @author hungcuong
 */
public class ScreenShadowFilter extends Filter {

    private Texture noiseTex;
    private Texture mapTex;
    private Vector4f mapPos;

    @Override
    protected void initFilter(AssetManager assetManager, RenderManager renderManager, ViewPort vp, int w, int h) {
        material = new Material(assetManager, "MatDefs/ScreenShadow/ScreenShadow.j3md");
    }

    @Override
    protected Material getMaterial() {
        material.setVector4("MapPos", mapPos);
        material.setTexture("NoiseTile", noiseTex);
        material.setTexture("Map", mapTex);
        return material;
    }

    public Texture getNoiseTex() {
        return noiseTex;
    }

    public void setNoiseTex(Texture noiseTex) {
        this.noiseTex = noiseTex;
    }

    public Texture getMapTex() {
        return mapTex;
    }

    public void setMapTex(Texture mapTex) {
        this.mapTex = mapTex;
    }

    public Vector4f getMapPos() {
        return mapPos;
    }

    public void setMapPos(Vector4f mapPos) {
        this.mapPos = mapPos;
    }
}
