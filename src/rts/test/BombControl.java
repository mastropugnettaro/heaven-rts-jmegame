/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.test;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.objects.PhysicsGhostObject;
import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh.Type;
import com.jme3.effect.shapes.EmitterSphereShape;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.io.IOException;
import java.util.Iterator;

/**
 *
 * @author normenhansen
 */
public class BombControl extends RigidBodyControl implements PhysicsCollisionListener, PhysicsTickListener {

    private float explosionRadius = 10;
    private PhysicsGhostObject ghostObject;
    private Vector3f vector = new Vector3f();
    private Vector3f vector2 = new Vector3f();
    private float forceFactor = 1;
    private ParticleEmitter flame;
    private ParticleEmitter debris;
    private float fxTime = 0.5f;
    private float maxTime = 4f;
    private float curTime = -1.0f;
    private float timer;
    private float explosionSize = 1;
    private Node explosionEffectNode = new Node("FXNode");

    public BombControl(CollisionShape shape, float mass) {
        super(shape, mass);
        createGhostObject();
    }

    public BombControl(AssetManager manager, CollisionShape shape, float mass) {
        super(shape, mass);
        createGhostObject();
        prepareEffect(manager);
    }

    public void setPhysicsSpace(PhysicsSpace space) {
        super.setPhysicsSpace(space);
        if (space != null) {
            space.addCollisionListener(this);
        }
    }
    int COUNT_FACTOR = 1;
    float COUNT_FACTOR_F = 1f;

    private void prepareEffect(AssetManager assetManager) {
        createFlame(assetManager);
        createDebris(assetManager);
    }

    private void createFlame(AssetManager assetManager) {
        flame = new ParticleEmitter("Flame", Type.Triangle, 32 * COUNT_FACTOR);
        flame.setSelectRandomImage(true);
        flame.setStartColor(new ColorRGBA(1f, 0.4f, 0.05f, (float) (1f / COUNT_FACTOR_F)));
        flame.setEndColor(new ColorRGBA(.4f, .22f, .12f, 0f));
        flame.setStartSize(1.3f);
        flame.setEndSize(2f);
        flame.setShape(new EmitterSphereShape(Vector3f.ZERO, 1f));
        flame.setParticlesPerSec(10);
        flame.setGravity(0, -5, 0);
        flame.setLowLife(0.4f);
        flame.setHighLife(0.5f);
        flame.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 7, 0));
        flame.getParticleInfluencer().setVelocityVariation(1f);
        flame.setImagesX(2);
        flame.setImagesY(2);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/flame.png"));
        flame.setMaterial(mat);
        explosionEffectNode.attachChild(flame);
    }

    private void createDebris(AssetManager assetManager) {
        debris = new ParticleEmitter("Debris", Type.Triangle, 15 * COUNT_FACTOR);
        debris.setSelectRandomImage(true);
        debris.setRandomAngle(true);
        debris.setRotateSpeed(FastMath.TWO_PI * 4);
        debris.setStartColor(new ColorRGBA(1f, 0.59f, 0.28f, (float) (1.0f / COUNT_FACTOR_F)));
        debris.setEndColor(new ColorRGBA(.5f, 0.5f, 0.5f, 0f));
        debris.setStartSize(.2f);
        debris.setEndSize(.2f);

//        debris.setShape(new EmitterSphereShape(Vector3f.ZERO, .05f));
        debris.setParticlesPerSec(0);
        debris.setGravity(0, 12f, 0);
        debris.setLowLife(1.4f);
        debris.setHighLife(1.5f);
        debris.setInitialVelocity(new Vector3f(0, 15, 0));
        debris.setVelocityVariation(.60f);
        debris.setImagesX(3);
        debris.setImagesY(3);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/Debris.png"));
        debris.setMaterial(mat);
        explosionEffectNode.attachChild(debris);
    }

    protected void createGhostObject() {
        ghostObject = new PhysicsGhostObject(new SphereCollisionShape(explosionRadius));
    }

    public void collision(PhysicsCollisionEvent event) {
        if (space == null) {
            return;
        }
        if (event.getObjectA() == this || event.getObjectB() == this) {
            space.add(ghostObject);
            ghostObject.setPhysicsLocation(getPhysicsLocation(vector));
            space.addTickListener(this);
            if (explosionEffectNode != null && spatial.getParent() != null) {
                curTime = 0;
                explosionEffectNode.setLocalTranslation(spatial.getLocalTranslation());
                spatial.getParent().attachChild(explosionEffectNode);
                flame.emitAllParticles();
                debris.emitAllParticles();
            }
            space.remove(this);
            spatial.removeFromParent();
        }
    }

    public void prePhysicsTick(PhysicsSpace space, float f) {
        space.removeCollisionListener(this);
    }

    public void physicsTick(PhysicsSpace space, float f) {
        //get all overlapping objects and apply impulse to them
        for (Iterator<PhysicsCollisionObject> it = ghostObject.getOverlappingObjects().iterator(); it.hasNext();) {
            PhysicsCollisionObject physicsCollisionObject = it.next();
            if (physicsCollisionObject instanceof PhysicsRigidBody) {
                PhysicsRigidBody rBody = (PhysicsRigidBody) physicsCollisionObject;
                rBody.getPhysicsLocation(vector2);
                vector2.subtractLocal(vector);
                float force = explosionRadius - vector2.length();
                force *= forceFactor;
                force = force > 0 ? force : 0;
                vector2.normalizeLocal();
                vector2.multLocal(force);
                ((PhysicsRigidBody) physicsCollisionObject).applyImpulse(vector2, Vector3f.ZERO);
            }
        }
        space.removeTickListener(this);
        space.remove(ghostObject);
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        if (enabled) {
            timer += tpf;
            if (timer > maxTime) {
                if (spatial.getParent() != null) {
                    space.removeCollisionListener(this);
                    space.remove(this);
                    spatial.removeFromParent();
                }
            }
        }
        if (enabled && curTime >= 0) {
            curTime += tpf;
            if (curTime > fxTime) {
                curTime = -1;
                explosionEffectNode.removeFromParent();
            }
        }
    }

    /**
     * @return the explosionRadius
     */
    public float getExplosionRadius() {
        return explosionRadius;
    }

    /**
     * @param explosionRadius the explosionRadius to set
     */
    public void setExplosionRadius(float explosionRadius) {
        this.explosionRadius = explosionRadius;
        createGhostObject();
    }

    public float getForceFactor() {
        return forceFactor;
    }

    public void setForceFactor(float forceFactor) {
        this.forceFactor = forceFactor;
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        throw new UnsupportedOperationException("Reading not supported.");
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        throw new UnsupportedOperationException("Saving not supported.");
    }

    public void setExplosionSize(float explosionSize) {
        this.explosionSize = explosionSize;
        flame.setStartSize(1.3f * explosionSize);
        flame.setEndSize(2.5f * explosionSize);
        debris.setStartSize(0.3f * explosionSize);
        debris.setEndSize(0.5f * explosionSize);
    }
}
