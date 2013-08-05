/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.stage;

import sg.atom.core.GameGUIManager;
import sg.atom.core.StageManager;
import sg.atom.stage.SelectManager;
import sg.atom.stage.WorldManager;
import sg.atom.stage.select.HoverFunction;
import sg.atom.stage.select.SelectFunction;
import sg.atom.stage.select.function.SwitchableSelectFunction;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class RTSSelectManager extends SelectManager {

    SelectFunction unitSelect;
    private SwitchableSelectFunction switchableSelectFunc;

    public RTSSelectManager(GameGUIManager gameGUIManager, StageManager stageManager, WorldManager worldManager) {
        super(gameGUIManager, stageManager, worldManager);
    }

    @Override
    public void init() {
        super.init();
        unitSelect = new RTSEntitySelectFunction(this);
        unitSelect.init();
        switchableSelectFunc = new SwitchableSelectFunction(this);
        switchableSelectFunc.init();
        switchableSelectFunc.setSingleSelectFunc(unitSelect);
        
        setHoverFunction(new HoverFunction(this));
        setSelectFunction(switchableSelectFunc);
        setEnable(true);

    }

    public void setCurrentFunction(String func) {
        if (func.equals("SelectUnit")) {
            setSelectFunction(unitSelect);
        } else if (func.equals("SelectMulti")) {
            setSelectFunction(switchableSelectFunc);
        } else {
            setSelectFunction(null);
        }
    }
}
