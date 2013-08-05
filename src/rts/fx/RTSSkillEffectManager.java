package rts.fx;

import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import rts.RTSWorldManager;

/**
 *
 * @author cuong.nguyenmanh2
 */

/*
 * Effect Manager employe Builder Pattern
 */
public class RTSSkillEffectManager {

    private static RTSSkillEffectManager _defaultInstance;
    Material lineMat;

    public static RTSSkillEffectManager getDefault() {
        if (_defaultInstance == null) {
            _defaultInstance = new RTSSkillEffectManager();
        }
        return _defaultInstance;
    }
    public static int defaultBulletNum = 80;

    ParticleEmitter getFire(String type, int numParticles) {
        return new ParticleEmitter(type, ParticleMesh.Type.Point, numParticles);
    }

    public Material getLineMat() {
        if (lineMat == null) {
            Material greenMat = new Material(RTSWorldManager.getDefault().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
            greenMat.setColor("Color", ColorRGBA.Green);
            greenMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Additive);
            greenMat.getAdditionalRenderState().setDepthWrite(false);
            greenMat.getAdditionalRenderState().setDepthTest(false);

            lineMat = greenMat;
        }
        return lineMat;
    }
}
