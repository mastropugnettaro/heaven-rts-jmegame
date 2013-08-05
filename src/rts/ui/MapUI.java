/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.ui;

/**
 *
 * @author cuong.nguyenmanh2
 */
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.texture.Image;
import com.jme3.texture.Texture2D;
import com.jme3.texture.image.ImageRaster;
import com.zero_separation.plugins.imagepainter.ImagePainter;
import java.util.ArrayList;
import sg.paint.CommonImagePainter;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class MapUI {

    private final AssetManager assetManager;
    CommonImagePainter ip;
    Material mat;
    Texture2D orgTex;
    ImageRaster orgTexRaster;
    ArrayList<UnitPos> units = new ArrayList<UnitPos>();
    Texture2D texture;

    class UnitPos {

        int x;
        int y;
        int id;
        int tx;
        int ty;
        float speed;
        int side;

        public UnitPos(int x, int y, int id) {
            this.x = x;
            this.y = y;
            this.id = id;

        }

        void setTarget(int tx, int ty) {
            this.tx = tx;
            this.ty = ty;
        }
    }

    void randomUnit(int num) {

        for (int i = 0; i < num; i++) {
            int x = FastMath.nextRandomInt(0, 255);
            int y = FastMath.nextRandomInt(0, 255);

            int tx = FastMath.nextRandomInt(0, 255);
            int ty = FastMath.nextRandomInt(0, 255);
            int speed = FastMath.nextRandomInt(300, 450);
            UnitPos p = new UnitPos(x, y, i);
            p.setTarget(tx, ty);
            p.speed = speed;
            units.add(p);
        }
    }

    public MapUI(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    void createMapBg() {
        //orgTex = (Texture2D) assetManager.loadTexture("Interface/Logo/Monkey.png");
        orgTex = (Texture2D) assetManager.loadTexture("Textures/Maps/Map1.png");
        orgTexRaster = ImageRaster.create(orgTex.getImage());
    }

    void drawMapBg() {
        ip.paintStretchedImage(0, 0, 256, 256, orgTexRaster, ImagePainter.BlendMode.SET, 1f);

    }

    public void initMap() {

        ip = new CommonImagePainter(Image.Format.RGBA8, 256, 256);
        //ip.paintRect(10, 10, 50, 50, ColorRGBA.Blue, ImagePainter.BlendMode.SET);
        createMapBg();
        drawMapBg();
        /*
         ip.line(10, 100, 100, 100, ColorRGBA.Blue, ImagePainter.BlendMode.MULTIPLY);
         ip.line(10, 80, 120, 100, ColorRGBA.Brown, ImagePainter.BlendMode.MULTIPLY);
         */

        randomUnit(50);
        texture = new Texture2D(ip.getImage());

    }

    void updateUnits(float tpf) {
        //drawMapBg();
        for (UnitPos unit : units) {

            //ip.paintRect(unit.x, unit.y, 4, 4, ColorRGBA.Blue, ImagePainter.BlendMode.SET);
            //ip.paintSubImage(unit.x, unit.y, 4, 4, orgTexRaster, ImagePainter.BlendMode.SET, ColorRGBA.White, unit.x, unit.y);
            ip.paintStretchedSubImage(unit.x, unit.y, 4, 4, orgTexRaster, ImagePainter.BlendMode.SET, ColorRGBA.White, unit.x * 2, unit.y * 2, 8, 8);

            if ((FastMath.nextRandomInt(0, 20) < 3)
                    || ((unit.tx == unit.x) && (unit.ty == unit.y))) {
                // Change target
                int tx = FastMath.nextRandomInt(1, 255);
                int ty = FastMath.nextRandomInt(1, 255);
                //System.out.println(unit.id + " new target x: " + unit.tx + " y: " + unit.ty);
                unit.setTarget(tx, ty);
            }

            // Move to target by speed
            //unit.x = FastMath.floor()
            float dx = unit.tx - unit.x;
            float dy = unit.ty - unit.y;
            float dis = dx * dx + dy * dy;
            if (dis >= unit.speed) {
                unit.x = unit.x + (int) Math.round(dx * unit.speed * tpf / dis);
                unit.y = unit.y + (int) Math.round(dy * unit.speed * tpf / dis);
            } else {
                unit.x = unit.tx;
                unit.y = unit.ty;
            }


            //ip.paintPixel(unit.x, unit.y, ColorRGBA.Blue, ImagePainter.BlendMode.SET);
            ColorRGBA color;
            if (unit.id < 20) {
                color = ColorRGBA.Green;
            } else {
                color = ColorRGBA.Blue;
            }
            //System.out.println(unit.id + " x: " + unit.x + " y: " + unit.y);
            ip.paintRect(unit.x, unit.y, 4, 4, color, ImagePainter.BlendMode.SET);
        }
        //Texture2D texture = new Texture2D(ip.getImage());
        //mat.setTexture("ColorMap", texture);
    }

    public void simpleUpdate(float tpf) {
        //updateUnits(tpf);
    }

    public Texture2D getTexture() {
        return texture;
    }
}
