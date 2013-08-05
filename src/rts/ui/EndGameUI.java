/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.ui;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.texture.Image;
import com.jme3.texture.Texture2D;
import com.jme3.texture.image.ImageRaster;
import com.zero_separation.plugins.imagepainter.ImagePainter;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.ArrayList;
import rts.RTSGameGUIManager;
import sg.paint.CommonImagePainter;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class EndGameUI implements ScreenController {

    RTSGameGUIManager gameGUIManager;
    private Element energyText, gasText;
    private ImageRenderer graphRenderer;

    public EndGameUI() {
    }

    public EndGameUI(RTSGameGUIManager gameGUIManager) {
        this.gameGUIManager = gameGUIManager;

    }

    public void onClick(String buttonId) {
        System.out.println(" Clicked " + buttonId);
    }

    void createUI(Nifty nifty, Screen screen) {
    }

    public void onStartScreen() {
        System.out.println("onStartScreen");
    }

    public void onEndScreen() {
        System.out.println("onEndScreen");
    }

    public void bind(Nifty nifty, Screen screen) {
        //createUI(nifty, screen);

        initGraph();
        graphRenderer = gameGUIManager.getRenderer(ImageRenderer.class, "EndGameScreen/fix/main/rightPanel/List/row2/graph");
        gameGUIManager.setNiftyImage(texture, "graphTex", graphRenderer);

    }

    public void backToGame() {
        gameGUIManager.goToScreen("InGameScreen");
    }
    CommonImagePainter ip;
    Material mat;
    Texture2D orgTex;
    ImageRaster orgTexRaster;
    ArrayList<CountryGraph> countryGraphs = new ArrayList<CountryGraph>();
    Texture2D texture;

    class Point {

        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    class CountryGraph {

        ArrayList<Point> points = new ArrayList<Point>();
        String name;
        int id;

        public CountryGraph(int id, String name) {
            this.id = id;
            this.name = name;
            randomPoint();
        }

        private void randomPoint() {
            int x = 0, y = 30, xc;
            while (x < 510) {
                points.add(new Point(x, y));
                y = FastMath.nextRandomInt(0, 255);
                xc = FastMath.nextRandomInt(10, 20);
                x += xc;

            }
        }
    }

    void randomCountry(int num) {
        for (int i = 0; i < num; i++) {
            countryGraphs.add(new CountryGraph(i, "Player" + (i + 1)));
        }
    }

    public void initGraph() {

        ip = new CommonImagePainter(Image.Format.RGBA8, 512, 512);
        //ip.paintRect(10, 10, 50, 50, ColorRGBA.Blue, ImagePainter.BlendMode.SET);
        /*
         ip.line(10, 100, 100, 100, ColorRGBA.Blue, ImagePainter.BlendMode.MULTIPLY);
         ip.line(10, 80, 120, 100, ColorRGBA.Brown, ImagePainter.BlendMode.MULTIPLY);
         */

        randomCountry(4);
        //ip.wipe(ColorRGBA.Blue);
        texture = new Texture2D(ip.getImage());
        drawGraph();
    }

    void drawGraph() {
        //drawMapBg();
        for (CountryGraph graph : countryGraphs) {
            //ip.paintPixel(unit.x, unit.y, ColorRGBA.Blue, ImagePainter.BlendMode.SET);
            ColorRGBA color;
            switch (graph.id) {
                case 0:
                    color = ColorRGBA.Green;
                    break;
                case 1:
                    color = ColorRGBA.Blue;
                    break;
                case 2:
                    color = ColorRGBA.Magenta;
                    break;
                case 3:
                    color = ColorRGBA.Orange;
                    break;
                default:
                    color = ColorRGBA.Cyan;
            }


            //System.out.println(unit.id + " x: " + unit.x + " y: " + unit.y);
            //ip.paintRect(graph.x, graph.y, 4, 4, color, ImagePainter.BlendMode.SET);
            for (int i = 0; i < graph.points.size() - 1; i++) {
                Point p1 = graph.points.get(i);
                Point p2 = graph.points.get(i + 1);
                ip.line(p1.x, p1.y, p2.x, p2.y, color, ImagePainter.BlendMode.SET);
            }

        }
        //Texture2D texture = new Texture2D(ip.getImage());
        //mat.setTexture("ColorMap", texture);
    }

    public Texture2D getTexture() {
        return texture;
    }
}
