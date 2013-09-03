package rts;

import rts.core.entity.RTSEntityFactory;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.effect.ParticleEmitter;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.BatchNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.SceneGraphVisitorAdapter;
import com.jme3.scene.Spatial;
import com.jme3.scene.debug.Arrow;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Sphere;
import com.jme3.shadow.PssmShadowRenderer;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.texture.Texture;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import rts.core.entity.RTSEntityManager;
import rts.core.entity.RTSSpatialEntity;
import rts.gameplay.base.Building;
import rts.gameplay.base.Country;
import rts.world.map.Map;
import rts.gameplay.manager.UnitManager;
import rts.world.RTSGameWorldView;
import sg.atom.entity.SpatialEntity;
import sg.atom.gameplay.GameLevel;
import sg.atom.stage.WorldManager;
import sg.atom.world.MaterialManager;
import sg.atom.world.TerrainManager;
import sg.atom.world.WorldSettings;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class RTSWorldManager extends WorldManager {

    protected BitmapText hintText;
    PointLight pl;
    Geometry lightMdl;
    private HashMap<String, Spatial> preloadModels;
    private HashMap<String, String> preloadModelsPath;
    UnitManager unitManager;
    private Geometry marker;
    private Geometry markerNormal;
    private Spatial gizmo;
    private boolean show3DCussor = false;
    float cursorHeight = 2f;
    float cursorHeightAngle = 0;
    float cursorHeightAngleInc = 5;
    private Geometry gizmo2;
    private boolean batchEnable = true;
    Node entitiesNode;
    boolean toogleTerrain = false;
    boolean toogleShaddow = false;
    private PssmShadowRenderer pssm;
    private static RTSWorldManager _defaultInstance;

    public RTSWorldManager(RTSGame app, RTSStageManager stageManager) {
        super(app, new Node("_worldNode"));
        if (batchEnable) {
            entitiesNode = new BatchNode("_worldNode");
        } else {
            entitiesNode = worldNode;
        }
        this.stageManager = stageManager;

    }

    public static RTSWorldManager getDefault() {
        return _defaultInstance;
    }

    // FIXME: DEFAULT NOT SINGLETON
    // INIT
    @Override
    public void initWorld(GameLevel level, WorldSettings settings) {
        super.initWorld(level, settings);
        Logger.getLogger(RTSWorldManager.class.getName()).log(Level.INFO, "Init world");

        this.materialManager = new MaterialManager(this);
        this.terrainManager = new TerrainManager(this);

        this._defaultInstance = this;
        // app.pauseGameLoop();
    }
    // LOAD

    @Override
    public void loadWorld() {
        /*
         if (this.worldSettings.useLevel) {
         loadLevel(currentLevel);
         terrainManager.loadTerrain();
         }
         */
        terrainManager.createSampleTerrain(65, 513);
        this.terrain = terrainManager.getTerrain();
        //terrainManager.setupKeys(stageManager.getInputManager());
        setupLight();
        createMarker();
    }

    @Override
    public void configWorld() {
        /*
         if (worldSettings.useTerrainLOD) {
         terrainManager.configTerrain();
         }
         */
        setupKeys();
    }

    void setupKeys() {
        stageManager.getInputManager().addMapping("ToogleTerrain", new KeyTrigger(KeyInput.KEY_F6));
        stageManager.getInputManager().addListener(actionListener, "ToogleTerrain");
        stageManager.getInputManager().addMapping("ToogleShaddow", new KeyTrigger(KeyInput.KEY_F9));
        stageManager.getInputManager().addListener(actionListener, "ToogleShaddow");
    }
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean pressed, float tpf) {
            if (name.equals("ToogleTerrain") && pressed) {
                doToogleTerrain();
            } else if (name.equals("ToogleShaddow") && pressed) {
                doToogleShaddow();;
            }
        }
    };

    void doToogleTerrain() {
        if (terrain != null) {
            toogleTerrain = !toogleTerrain;
            if (toogleTerrain) {
                terrain.removeFromParent();
            } else {
                rootNode.attachChild(terrain);
            }
        }
    }

    void doToogleShaddow() {
        if (pssm != null) {
            toogleShaddow = !toogleShaddow;
            if (toogleShaddow) {
                app.getViewPort().removeProcessor(pssm);
            } else {
                app.getViewPort().addProcessor(pssm);
            }
        }
    }

    void preloadAssets(Map map) {
        String path = "";
        /*
         for (UnitTypes type : UnitManager.UnitTypes.values()) {
         String name = type.name();
         preloadModels.put(name, assetManager.loadModel(path + name));
         }
         * 
         */

        preloadModelsPath = new HashMap<String, String>(10);
        preloadModels = new HashMap<String, Spatial>(10);

        String assetRootPath = "Models/FuristicStyle/";
        preloadModelsPath.put("MainBuilding", assetRootPath +"Buildings/Protos/MainHouse/MainHouse.j3o");
        preloadModelsPath.put("FighterBuidling", assetRootPath +"Buildings/Protos/TroopHouse/TroopHouse.j3o");
        preloadModelsPath.put("Energy", assetRootPath +"Resources/Energy/Energy.j3o");
        preloadModelsPath.put("GasMine", assetRootPath +"Resources/Energy/GasMine.j3o");
        //preloadModelsPath.put("Warrior", rootPath +"Units/Warior/Warior.j3o");
        preloadModelsPath.put("Warrior", assetRootPath +"Units/Human/Mech/Mech1/Mech.j3o");
        //preloadModelsPath.put("Warrior", rootPath +"Units/Nomad/Nomad.j3o");

        for (Entry<String, String> entry : preloadModelsPath.entrySet()) {
            Spatial orginalSpatial = assetManager.loadModel(entry.getValue());

            // Save orginal Spatial data into a List!
            orginalSpatial.setShadowMode(ShadowMode.Cast);
            preloadModels.put(entry.getKey(), orginalSpatial);
        }
    }

    public void buildWorld(Map map) {
        preloadAssets(map);

    }
    // ATTACH

    @Override
    public void attachWorld() {
        /*
         for (SpatialEntity entity : entityManager.getAllSpatialEntities()) {
         loadAndAttachSpatialEntity(entity);
         placeEntityOnTerrain(entity);
         }
         */
//        SpatialEntity se = entityManager.getEntitiesByClass(Building.class).get(0);
        //selectManager.select(se);
        terrainManager.attachTerrain();
        if (batchEnable) {
            worldNode.attachChild(entitiesNode);
        }
        rootNode.attachChild(worldNode);

    }

    public void attachCountry(final Country country) {
        for (SpatialEntity e : getEntityManager().getAllSpatialEntitiesByGroup(country.getName())) {
            RTSSpatialEntity entity = (RTSSpatialEntity) e;
            loadAndAttachSpatialEntity(entity);
            placeEntityOnTerrain(entity);

            // set color
            entity.getSpatial().breadthFirstTraversal(new SceneGraphVisitorAdapter() {
                @Override
                public void visit(Geometry geom) {
                    super.visit(geom);
                    if (geom.getMaterial() != null) {
                        if (geom.getMaterial().getParam("Diffuse") != null) {
                            geom.getMaterial().setColor("Diffuse", country.getColor());
                        }
                    }

                }

                @Override
                public void visit(Node geom) {
                    super.visit(geom);

                }
            });

        }
    }
    // CONFIG

    public void batchWorld() {
        if (batchEnable) {
            //((BatchNode) entitiesNode).batch();
        }
    }

    public void reposAllEntities() {
        for (SpatialEntity e : getEntityManager().getAllSpatialEntities()) {
            RTSSpatialEntity entity = (RTSSpatialEntity) e;
            placeEntityOnTerrain(entity);
        }
    }

    public Vector3f getTerrainPos(RTSSpatialEntity se) {
        float x = se.getMapPosition().x;
        float z = se.getMapPosition().z;
        float y = terrainManager.getHeight(new Vector2f(x, z));// - 100;

        return new Vector3f(x, y, z);
    }

    public void placeEntityOnTerrain(RTSSpatialEntity se) {
        Vector3f pos = getTerrainPos(se);
        if (se instanceof Building) {
            // flating the terrain
            terrainManager.adjustLevelHeight(se.getMapPosition().clone(), 10, pos.y);
        } else {
        }

        se.getMapPosition().setY(pos.y);
        Spatial model = se.getSpatial();
        model.setLocalTranslation(pos);
        //model.updateGeometricState();
        //model.updateModelBound();
        //
    }

    public void loadAndAttachSpatialEntity(RTSSpatialEntity se) {
        getEntityManager().getEntityFactory(RTSEntityFactory.class).createEntity(se);
        entitiesNode.attachChild(se.getSpatial());
    }

    void setupLight() {

        DirectionalLight light = new DirectionalLight();
        light.setDirection((new Vector3f(-0.5f, -1f, -0.5f)).normalize());
        worldNode.addLight(light);
        AmbientLight ambLight = new AmbientLight();
        ambLight.setColor(new ColorRGBA(1f, 1f, 0.8f, 0.2f));
        worldNode.addLight(ambLight);

        /**
         * Advanced shadows for uneven surfaces
         */
        pssm = new PssmShadowRenderer(assetManager, 1024, 3);
        pssm.setDirection(new Vector3f(-.5f, -.8f, -.5f).normalizeLocal());
        app.getViewPort().addProcessor(pssm);

    }

    @Override
    public void simpleUpdate(float tpf) {

        if (show3DCussor) {
            Vector3f intersection = getWorldIntersection();
            TerrainQuad terrain = terrainManager.getTerrain();

            if (terrainManager.raiseTerrain) {

                if (intersection != null) {
                    terrainManager.adjustHeight(intersection, 64, tpf * 60);
                    reposAllEntities();
                }
            } else if (terrainManager.lowerTerrain) {
                if (intersection != null) {
                    terrainManager.adjustHeight(intersection, 64, -tpf * 60);
                    reposAllEntities();
                }
            }
            updateMark(terrain, intersection, tpf);
        }
    }

    public void setShow3DCussor(boolean show3DCussor) {
        this.show3DCussor = show3DCussor;
        if (show3DCussor) {
            gizmo.setCullHint(Spatial.CullHint.Inherit);
        } else {
            cursorHeightAngle = 0;
            gizmo.setCullHint(Spatial.CullHint.Always);
        }
    }

    void updateMark(TerrainQuad terrain, Vector3f intersection, float tpf) {

        if (terrain != null && intersection != null) {
            float h = terrain.getHeight(new Vector2f(intersection.x, intersection.z));
            Vector3f tl = terrain.getWorldTranslation();
            //Logger.getLogger("WorldManager").warning("Update Mark!");
            //marker.setLocalTranslation(tl.add(new Vector3f(intersection.x, h, intersection.z)));
            //markerNormal.setLocalTranslation(tl.add(new Vector3f(intersection.x, h, intersection.z)));

            if (cursorHeightAngle < FastMath.PI) {
                cursorHeightAngle += tpf * cursorHeightAngleInc;
            } else {
                cursorHeightAngle = 0;
            }
            float height = h + cursorHeight * FastMath.sin(cursorHeightAngle);
            gizmo.setLocalTranslation(tl.add(new Vector3f(intersection.x, height, intersection.z)));
            gizmo2.setLocalTranslation(tl.add(new Vector3f(intersection.x, h, intersection.z)));
            gizmo2.setLocalScale(1 - FastMath.sin(cursorHeightAngle) / 2);
            //Vector3f normal = terrain.getNormal(new Vector2f(intersection.x, intersection.z));
            //((Arrow) markerNormal.getMesh()).setArrowExtent(normal);
        }

    }

    private Vector3f getWorldIntersection() {
        Camera cam = app.getCamera();

        Vector2f mousePos = app.getInputManager().getCursorPosition();
        TerrainQuad terrain = terrainManager.getTerrain();

        Vector3f origin = cam.getWorldCoordinates(mousePos, 0.0f);
        Vector3f direction = cam.getWorldCoordinates(mousePos, 0.3f);
        //Vector3f direction = cam.getWorldCoordinates(new Vector2f(settings.getWidth() / 2, settings.getHeight() / 2), 0.3f);
        //Vector3f direction = cam.getWorldCoordinates(new Vector2f(settings.getWidth() / 2, settings.getHeight() / 2), 0.3f);
        direction.subtractLocal(origin).normalizeLocal();

        Ray ray = new Ray(origin, direction);

        //Logger.getLogger(RTSWorldManager.class.getName()).info("Shoot !");
        CollisionResults results = new CollisionResults();
        int numCollisions = terrain.collideWith(ray, results);
        if (numCollisions > 0) {
            CollisionResult hit = results.getClosestCollision();
            return hit.getContactPoint();
        }
        return null;
    }

    private void createMarker() {
        // collision marker
        Sphere sphere = new Sphere(8, 8, 0.5f);
        marker = new Geometry("Marker");
        marker.setMesh(sphere);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", new ColorRGBA(251f / 255f, 130f / 255f, 0f, 0.6f));
        mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);

        marker.setMaterial(mat);
        super.worldNode.attachChild(marker);


        // surface normal marker
        Arrow arrow = new Arrow(new Vector3f(0, 1, 0));
        markerNormal = new Geometry("MarkerNormal");
        markerNormal.setMesh(arrow);
        markerNormal.setMaterial(mat);
        worldNode.attachChild(markerNormal);

        gizmo = (Spatial) assetManager.loadModel("Models/gizmo/arrow.j3o");
        worldNode.attachChild(gizmo);

        gizmo2 = new Geometry("Gizmo2", new Cylinder(3, 20, 8, 0.01f, true));
        Material clickedMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture tex = assetManager.loadTexture("Textures/Cursors/Clicked.png");
        clickedMat.setTexture("ColorMap", tex);
        gizmo2.setMaterial(clickedMat);
        Quaternion q = new Quaternion();
        q.lookAt(Vector3f.UNIT_Y, Vector3f.UNIT_X);
        gizmo2.setLocalRotation(q);
        worldNode.attachChild(gizmo2);
        //clickedMat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
        gizmo2.setQueueBucket(RenderQueue.Bucket.Transparent);
        clickedMat.getAdditionalRenderState().setBlendMode(BlendMode.AlphaAdditive);
        clickedMat.getAdditionalRenderState().setDepthTest(false);

    }

    void disableParticle() {
        worldNode.depthFirstTraversal(new SceneGraphVisitor() {
            public void visit(Spatial spatial) {
                if (spatial instanceof ParticleEmitter) {
                    ((ParticleEmitter) spatial).setEnabled(false);
                }
            }
        });
    }

    public Vector3f getCursor3dPos() {
        return getWorldIntersection();
    }

    public HashMap<String, Spatial> getPreloadModels() {
        return preloadModels;
    }

    private RTSEntityManager getEntityManager() {
        return (RTSEntityManager) stageManager.getEntityManager();
    }

    public RTSGameWorldView getFullWorldView() {
        return new RTSGameWorldView(this);
    }
}
