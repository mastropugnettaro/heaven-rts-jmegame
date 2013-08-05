/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.ui;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.ui.Picture;
import rts.RTSGameGUIManager;
import rts.gameplay.base.Unit;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class HealthBar extends Node {

    Picture p1, p2;
    private Unit unit;
    int maxWidth = 50;
    int barHeight = 8;
    private Material blackMat;
    private final Material greenMat;

    public HealthBar(Unit unit, RTSGameGUIManager gameGUIManager) {
        super("heathbar" + unit.name);
        AssetManager assetManager = gameGUIManager.getAssetManager();
        this.unit = unit;
        p1 = new Picture("Background");
        p2 = new Picture("Bar");

        blackMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        blackMat.setColor("Color", ColorRGBA.Black);

        p1.setMaterial(blackMat);
        p1.setLocalTranslation(0, 0, 0);
        p1.setWidth(maxWidth);
        p1.setHeight(barHeight);

        greenMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        greenMat.setColor("Color", ColorRGBA.Green);

        p2.setMaterial(greenMat);
        p2.setLocalTranslation(0, 0, 1);
        p2.setWidth((float) maxWidth * 0.8f);
        p2.setHeight((float) barHeight * 0.9f);
        attachChild(p1);
        attachChild(p2);
        //add();

    }

    public void updateBar(Camera cam, float yUp) {
        int maxValue = unit.maxHP;
        int value = unit.hp;

        float x = unit.getMapPosition().x;
        float z = unit.getMapPosition().z;
        float y = unit.getMapPosition().y + yUp;
        Vector3f screenPos = cam.getScreenCoordinates(new Vector3f(x, y, z));
        setLocalTranslation(screenPos);
        //p1.setLocalScale(Vector3f.UNIT_XYZ);
        //p2.setLocalScale(Vector3f.UNIT_XYZ);
    }
}
