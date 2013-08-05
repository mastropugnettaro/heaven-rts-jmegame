/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.cam;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.app.SimpleApplication;
import com.jme3.cinematic.Cinematic;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.Caps;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl;
import com.jme3.scene.shape.Box;
import com.jme3.shadow.PssmShadowRenderer;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class TestCameraControl extends SimpleApplication {

    Cinematic cinematic;
    private Node model;
    private Spatial camNode1;
    private FilterPostProcessor fpp;

    @Override
    public void simpleInitApp() {
        createScene();
    }

    void setupCinematic() {
        /*
        cinematic = new Cinematic(rootNode);
        CameraNode camNode = cinematic.bindCamera("cam1", cam);
        
        camNode.
        
         */
    }

    private void createScene() {

        model = (Node) assetManager.loadModel("Models/Box/boxAni.j3o");
        //model.setShadowMode(ShadowMode.CastAndReceive);
        rootNode.attachChild(model);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Cyan);
        camNode1 = model.getChild("Cube.001");
        Node mNode = (Node) model.getChild("Cube");
        camNode1.addControl(new CameraControl(cam, CameraControl.ControlDirection.SpatialToCamera));
        AnimControl control1 = camNode1.getControl(AnimControl.class);

        AnimChannel channel1 = control1.createChannel();

        AnimControl control2 = mNode.getControl(AnimControl.class);
        for (String name : control1.getAnimationNames()) {
            System.out.println(" " + name);
        }
        AnimChannel channel2 = control2.createChannel();

        channel1.setAnim("Cube.001Action");
        channel2.setAnim("CubeAction");

        //System.out.println(" " + channel2.getAnimationName());
        /*
        teapot.setMaterial(mat);
        teapot.setShadowMode(ShadowMode.CastAndReceive);
        rootNode.attachChild(teapot);
         */
        Material matSoil = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        matSoil.setBoolean("UseMaterialColors", true);
        matSoil.setColor("Ambient", ColorRGBA.Gray);
        matSoil.setColor("Diffuse", ColorRGBA.Green);
        matSoil.setColor("Specular", ColorRGBA.Black);

        Geometry soil = new Geometry("soil", new Box(new Vector3f(0, -6.0f, 0), 50, 1, 50));
        soil.setMaterial(matSoil);
        soil.setShadowMode(ShadowMode.Receive);
        rootNode.attachChild(soil);

        // Light
        DirectionalLight light = new DirectionalLight();
        light.setDirection(new Vector3f(0, -1, -1).normalizeLocal());
        light.setColor(ColorRGBA.White.mult(1.5f));
        rootNode.addLight(light);

        // Filter
        fpp = new FilterPostProcessor(assetManager);

        if (renderer.getCaps().contains(Caps.GLSL100)) {
            PssmShadowRenderer pssm = new PssmShadowRenderer(assetManager, 512, 1);
            pssm.setDirection(new Vector3f(0, -1, -1).normalizeLocal());
            pssm.setShadowIntensity(0.4f);
            viewPort.addProcessor(pssm);
            viewPort.addProcessor(fpp);
        }
    }

    public static void main(String[] args) {
        TestCameraControl app = new TestCameraControl();
        app.start();
    }
}
