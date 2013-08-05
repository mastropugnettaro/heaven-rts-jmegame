/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.ui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.HoverEffectBuilder;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.Controller;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.xml.xpp3.Attributes;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import rts.core.entity.RTSEntityFunction;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class EntityActionUI implements Controller {

    int bx, by, bn;
    private Element buttonPanel;
    private Nifty nifty;
    private Screen screen;
    private Element element;
    HashMap<String, RTSEntityFunction> functionMap = new HashMap<String, RTSEntityFunction>();

    public ImageBuilder createImageButton(final String buttonId, final int bn, final String path, final String hintText, final String actionName) {
        return new ImageBuilder(buttonId) {
            {
                onHoverEffect(new HoverEffectBuilder("") {
                    {
                        /*
                         effectParameter("name", "imageSize");
                         effectParameter("startSize", "0.5");
                         effectParameter("endSize", "1.5");
                         * 
                         */

                        /*
                         effectParameter("name", "colorPulsate");
                         effectParameter("startColor", "#00000000");
                         effectParameter("endColor", "#00ffff44");
                         //effectParameter("neverStopRendering", "true");
                         effectParameter("oneShot", "true");
                         effectParameter("period", "4000");
                         */

                        effectParameter("name", "colorBar");
                        effectParameter("color", "#00ffff44");
                        effectParameter("post", "true");
                    }
                });

                onHoverEffect(new HoverEffectBuilder("customHint") {
                    {
                        effectParameter("startDelay", "2000");
                        effectParameter("targetElement", "hintPanel");
                        effectParameter("hintText", hintText);
                        effectParameter("longText", hintText + " -- " + hintText);
                    }
                });
                onHoverEffect(new HoverEffectBuilder("playSound") {
                    {
                        effectParameter("sound", "hover1");
                    }
                });
                interactOnMouseOver("changeButtonImage(" + buttonId + ",hover)");
                interactOnClick("onActionClick(" + buttonId + "," + actionName + ")");
                width("25%");
                height("100%");
                //filename("Interface/ingame/images/icon/button" + (bx * 4 + by) + ".png");
                //filename("Interface/InGame/images/parts/button" + (bn + 1) + ".png");
                filename(path);
            }
        };
    }

    public void setInfoButtons(HashMap<String, RTSEntityFunction> functionList) {

        if (this.functionMap != functionList) {
            // remove all buttons
            if (buttonPanel != null) {
                buttonPanel.markForRemoval();
            }
            this.functionMap = functionList;
            createButtons(nifty, screen, element);
            element.add(buttonPanel);
        }
    }

    public void createButtons(Nifty nifty, Screen screen, Element container) {
        PanelBuilder pb = new PanelBuilder("ButtonPanel") {
            {
                childLayoutVertical();
                final Iterator<RTSEntityFunction> iterator = functionMap.values().iterator();
                
                for (by = 0; by < 3; by++) {
                    panel(new PanelBuilder("Row" + by) {
                        {
                            width("100%");
                            height("33%");
                            childLayoutHorizontal();

                            for (bx = 0; bx < 4; bx++) {
                                bn = (by * 4 + bx);
                                if (iterator.hasNext()) {
                                    RTSEntityFunction ef = iterator.next();
                                    final String buttonId = "button_" + bx + "_" + by;
                                    String path = ef.getImgPath();
                                    image(createImageButton(buttonId, bn, path, ef.getInfo(), ef.getName()));
                                }
                            }
                        }
                    });
                }
            }
        };
        buttonPanel = pb.build(nifty, screen, container);

    }

    public void createHintLayer() {
        Element layer = new LayerBuilder("hintLayer") {
            {

                childLayoutAbsoluteInside();
                panel(new PanelBuilder("hintPanel") {
                    {
                        width("200px");
                        height("200px");
                        backgroundColor("#0009");
                        childLayoutVertical();
                        visible(false);
                        panel(new PanelBuilder("info") {
                            {
                                height("80px");
                                childLayoutHorizontal();

                                text(new TextBuilder("content") {
                                    {
                                        width("50%");
                                        text("Info");
                                        font("aurulent-sans-16.fnt");
                                        color("#ffff");
                                        alignCenter();
                                        valignCenter();
                                    }
                                });

                                image(new ImageBuilder() {
                                    {
                                        width("50%");
                                        filename("Interface/InGame/images/unitpics/aafus.png");
                                    }
                                });
                            }
                        });

                        panel(new PanelBuilder("panel2") {
                            {
                                childLayoutVertical();
                                text(new TextBuilder("longInfo") {
                                    {
                                        font("aurulent-sans-16.fnt");
                                        color("#ffff");
                                        wrap(true);
                                        text("Long info");
                                    }
                                });
                            }
                        });
                    }
                });
            }
        }.build(nifty, screen, screen.getRootElement());

        //screen.addLayerElement(layer);
    }

    public void createHint(Element parent) {
    }

    public void bind(Nifty nifty, Screen screen, Element element, Properties parameter, Attributes controlDefinitionAttributes) {
        //System.out.println(" Bind and ADD");
        //buttonPanel =createButtons(nifty, screen, element);
        //element.add(buttonPanel);
        this.nifty = nifty;
        this.screen = screen;
        this.element = element;
        nifty.registerEffect("customHint", "rts.ui.CustomHint");
        createHintLayer();
    }

    public void init(Properties parameter, Attributes controlDefinitionAttributes) {
    }

    public void onStartScreen() {
    }

    public void onFocus(boolean getFocus) {
    }

    public boolean inputEvent(NiftyInputEvent inputEvent) {
        return false;
    }

    public HashMap<String, RTSEntityFunction> getButtonToFunctionMap() {
        return functionMap;
    }
}
