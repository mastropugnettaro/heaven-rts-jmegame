/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.fx.filters;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.post.Filter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.texture.Texture;

/**
 *
 * @author kwando
 */
public class BadTVFilter extends Filter {

    private Texture noiseTex;
    private Texture randText;
    private float distortionFreq;
    private float distortionScale;
    private float distortionRoll;
    private float interference;
    private float frameLimit;
    private float frameShape;
    private float frameSharpness;
    private float brightness;

    protected void initFilter(AssetManager assetManager, RenderManager renderManager, ViewPort vp, int w, int h) {
        material = new Material(assetManager, "MatDefs/BadTV/BadTV.j3md");
    }

    @Override
    protected Material getMaterial() {
        material.setFloat("DistortionFreq", distortionFreq);
        material.setFloat("DistortionScale", distortionScale);
        material.setFloat("DistortionRoll", distortionRoll);
        material.setFloat("Interference", interference);
        material.setFloat("FrameLimit", frameLimit);
        material.setFloat("FrameShape", frameShape);
        material.setFloat("FrameSharpness", frameSharpness);
        material.setFloat("Brightness", brightness);
        material.setTexture("Noise", noiseTex);
        material.setTexture("Rand", randText);
        return material;
    }

    public Texture getNoiseTex() {
        return noiseTex;
    }

    public void setNoiseTex(Texture noiseTex) {
        this.noiseTex = noiseTex;
    }

    public Texture getRandText() {
        return randText;
    }

    public void setRandText(Texture randText) {
        this.randText = randText;
    }

    public float getDistortionFreq() {
        return distortionFreq;
    }

    public void setDistortionFreq(float distortionFreq) {
        this.distortionFreq = distortionFreq;
    }

    public float getDistortionScale() {
        return distortionScale;
    }

    public void setDistortionScale(float distortionScale) {
        this.distortionScale = distortionScale;
    }

    public float getDistortionRoll() {
        return distortionRoll;
    }

    public void setDistortionRoll(float distortionRoll) {
        this.distortionRoll = distortionRoll;
    }

    public float getInterference() {
        return interference;
    }

    public void setInterference(float interference) {
        this.interference = interference;
    }

    public float getFrameLimit() {
        return frameLimit;
    }

    public void setFrameLimit(float frameLimit) {
        this.frameLimit = frameLimit;
    }

    public float getFrameShape() {
        return frameShape;
    }

    public void setFrameShape(float frameShape) {
        this.frameShape = frameShape;
    }

    public float getFrameSharpness() {
        return frameSharpness;
    }

    public void setFrameSharpness(float frameSharpness) {
        this.frameSharpness = frameSharpness;
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }
    
    
}