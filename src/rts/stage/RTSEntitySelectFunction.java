/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.stage;

import com.jme3.input.event.MouseButtonEvent;
import com.jme3.input.event.MouseMotionEvent;
import rts.core.entity.RTSSpatialEntity;
import rts.gameplay.RTSGamePlayManager;
import rts.ui.EntityActionUI;
import rts.ui.EntityInfoUI;
import sg.atom.ui.GameGUIManager;
import sg.atom.entity.Entity;
import sg.atom.stage.SelectManager;
import sg.atom.stage.select.function.SingleSelectFunction;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class RTSEntitySelectFunction extends SingleSelectFunction {

    public RTSEntitySelectFunction(SelectManager manager) {
        super(manager);
    }

    @Override
    public void mouseMove(MouseMotionEvent evt) {
    }

    @Override
    public void mouseButton(MouseButtonEvent evt) {
        if (evt.getButtonIndex() == firstButtonIndex && !evt.isPressed()) {
            funcSelect();
        }
    }

    @Override
    public void doSelectSingleEntity(Entity entity) {
        RTSGamePlayManager.getDefaultInstance().displayEntityInfo(entity);
    }
}
