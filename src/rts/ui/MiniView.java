package rts.ui;

import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.texture.FrameBuffer;
import com.jme3.texture.Image;
import com.jme3.texture.Image.Format;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import rts.fx.filters.BadTVFilter;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class MiniView {

    private ViewPort offScreenView;
    private Texture2D texture;
    private Camera mainCamera;
    private Spatial scene;
    private RenderManager renderManager;
    InputManager inputManager;
    private AssetManager assetManager;
    private Spatial camNode;
    private Spatial bodyNode;
    private Spatial light1;

    public MiniView(AssetManager assetManager, RenderManager renderManager, InputManager inputManager) {

        this.inputManager = inputManager;
        this.assetManager = assetManager;
        this.renderManager = renderManager;
    }

    public void setupViewport() {
        this.mainCamera = new Camera(120, 120);
        mainCamera.setFrustumPerspective(25f, 1, 1f, 1000f);

        this.scene = createScene(assetManager);

        offScreenView = renderManager.createPreView("Offscreen View", mainCamera);
        //offScreenView = new ViewPort("Offscreen View", mainCamera);
        offScreenView.setClearFlags(true, true, true);
        texture = new Texture2D(mainCamera.getWidth(), mainCamera.getHeight(), Image.Format.RGB8);
        //texture.setMinFilter(Texture.MinFilter.Trilinear);
        FrameBuffer offScreenBuffer = new FrameBuffer(mainCamera.getWidth(), mainCamera.getHeight(), 0);
        offScreenBuffer.setDepthBuffer(Format.Depth);
        offScreenBuffer.setColorBuffer(Format.RGBA8);
        offScreenBuffer.setColorTexture(texture);

        offScreenView.setBackgroundColor(ColorRGBA.DarkGray);
        offScreenView.setOutputFrameBuffer(offScreenBuffer);
        scene.updateGeometricState();
        
        //setupFilter();
        offScreenView.attachScene(this.scene);
        

    }
    /**
     * Captured data will be available in {@link #getTexture()} and can be
     * applied to Picture. Other ways of using derived texture may result
     * unexpected behavior
     */
    float angle = 0;
    float speed = 1;

    public void captureScreen(float tpf) {
        //offScreenView.getCamera().setLocation(mainCamera.getLocation());
        angle += tpf * speed;
        //mainCamera.setRotation();

        //ColorRGBA color = new ColorRGBA();
        //color.interpolate(ColorRGBA.Red, ColorRGBA.Green, 40*tpf);
        //viewPort.setBackgroundColor(color);
        //offScreenView.setBackgroundColor(color);

        //renderManager.renderViewPort(offScreenView, tpf);
        Quaternion quad = new Quaternion();
        quad.fromAngleAxis(angle, Vector3f.UNIT_Y);
        Vector3f lightPos = light1.getLocalTranslation().clone();
        Vector3f vec1 = lightPos.subtract(bodyNode.getLocalTranslation());
        //light1.setLocalTranslation(quad.mult(vec1));

        scene.setLocalRotation(quad);
        scene.updateLogicalState(tpf);
        scene.updateGeometricState();
    }

    public Texture2D getTexture() {
        return texture;
    }

    private Spatial createScene(AssetManager assetManager) {
        Node rootNode = new Node("Offview root");
        Node model = (Node) assetManager.loadModel("Models/Style1/UnitDetail/Human/GirlCommander/model1.j3o");

        PointLight lamp = new PointLight();
        lamp.setPosition(new Vector3f(-10, -10, -10));
        lamp.setColor(ColorRGBA.White);

        /*
      
         Vector3f lightDir = new Vector3f(-0.8719428f, -0.46824604f, 0.14304268f);
         DirectionalLight dl = new DirectionalLight();
         dl.setColor(new ColorRGBA(1.0f, 0.92f, 0.75f, 1f));
         dl.setDirection(lightDir);
        
         Vector3f lightDir2 = new Vector3f(0.70518064f, 0.5902297f, -0.39287305f);
         DirectionalLight dl2 = new DirectionalLight();
         dl2.setColor(new ColorRGBA(0.7f, 0.85f, 1.0f, 1f));
         dl2.setDirection(lightDir2);
         rootNode.addLight(dl);
         rootNode.addLight(dl2);
         
         */
        //rootNode.addLight(lamp);

        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient);

        rootNode.attachChild(model);



        camNode = model.getChild("Camera");
        bodyNode = model.getChild("Helmet");
        light1 = model.getChild("Point.001");
        //ChaseCamera chaseCam = new ChaseCamera(mainCamera, bodyNode, inputManager);
        //chaseCam.setSmoothMotion(true);
        //chaseCam.setMaxDistance(100000);
        //chaseCam.setMinVerticalRotation(-FastMath.PI / 2);

        mainCamera.setLocation(camNode.getLocalTranslation());
        mainCamera.lookAt(bodyNode.getWorldTranslation(), Vector3f.UNIT_Y);

        //mainCamera.update();


        return rootNode;
    }
    /*
     @Override
     public void simpleInitApp() {
     setupViewport();
     }

     @Override
     public void simpleUpdate(float tpf) {
     super.simpleUpdate(tpf);
     captureScreen(tpf);
     }

     public static void main(String[] args) {
     MiniView app = new MiniView();
     app.start();
     }
     */

    public void setupFilter() {
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        BloomFilter bf = new BloomFilter(BloomFilter.GlowMode.Objects);
        bf.setBloomIntensity(2.0f);
        bf.setExposurePower(1.3f);
        fpp.addFilter(bf);


        BadTVFilter badTv = new BadTVFilter();
        fpp.addFilter(badTv);
        badTv.setFrameShape(0.26f);
        badTv.setFrameLimit(0.36f);
        badTv.setInterference(0.39f);
        badTv.setDistortionRoll(0.15f);
        badTv.setDistortionFreq(0f);
        badTv.setDistortionScale(0f);
        badTv.setFrameSharpness(8.4f);
        badTv.setBrightness(1.2f);
        Texture noiseTex = assetManager.loadTexture("Textures/NoiseRand/NoiseVolume.dds");
        Texture randTex = assetManager.loadTexture("Textures/NoiseRand/Random3D.dds");
        badTv.setNoiseTex(noiseTex);
        badTv.setRandText(randTex);
        offScreenView.addProcessor(fpp);
    }
}
