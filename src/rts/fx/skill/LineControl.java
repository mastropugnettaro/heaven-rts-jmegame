/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.fx.skill;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.shape.Line;
import rts.RTSWorldManager;
import rts.fx.RTSSkillEffectManager;
import rts.gameplay.base.RTSUnitBase;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class LineControl extends AbstractControl {

    public static float DEFAULT_LINE_WIDTH = 4;
    Vector3f endPos = new Vector3f();
    Vector3f startPos = new Vector3f();
    private RTSUnitBase startEntity;
    private RTSUnitBase targetEntity;
    Line line;
    private Geometry lineGeo;

    public void setTarget(Vector3f endPos) {
        this.endPos.set(endPos);
    }

    public void setStart(RTSUnitBase thisEntity) {
        this.startEntity = thisEntity;
    }

    @Override
    protected void controlUpdate(float tpf) {
        this.endPos.set(endPos);
        this.startPos.set(startEntity.getSpatial().getLocalTranslation());

        //this.endPos = new Vector3f(0, 0, 10);
        //this.startPos = new Vector3f(20, 20, 10);
        if (lineGeo != null) {
            this.lineGeo.removeFromParent();
        }
        // redraw
        this.line = new Line(this.startPos, this.endPos);
        this.line.setLineWidth(DEFAULT_LINE_WIDTH);
        this.lineGeo = new Geometry("Line", line);
        this.lineGeo.setMaterial(RTSSkillEffectManager.getDefault().getLineMat());

        //((Node) this.spatial).attachChild(lineGeo);
        RTSWorldManager.getDefault().getWorldNode().attachChild(lineGeo);

    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //rm.renderGeometry();
    }
}
