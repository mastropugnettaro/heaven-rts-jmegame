/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.ui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.HoverEffectBuilder;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.controls.Controller;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.xml.xpp3.Attributes;
import java.util.Properties;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class EntityInfoUI implements Controller {

    int bx, by, bn;
    private Element element, titleUI, buttons, infosUI, infoNameUI;

    
    public Element createUI(Nifty nifty, Screen screen, Element container) {
        return createButtons(nifty, screen, container);
    }

    public Element createButtons(Nifty nifty, Screen screen, Element container) {


        PanelBuilder pb = new PanelBuilder("ButtonPanel") {

            {
                childLayoutVertical();
                for (by = 0; by < 3; by++) {

                    panel(new PanelBuilder("Row" + by) {

                        {
                            width("100%");
                            height("33%");
                            childLayoutHorizontal();
                            for (bx = 0; bx < 4; bx++) {
                                bn = (by * 3 + bx);
                                if (bn < 3) {
                                    final String buttonId = "button_" + bx + "_" + by;
                                    image(new ImageBuilder(buttonId) {

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
                                            interactOnMouseOver("changeButtonImage(" + buttonId + ",hover)");
                                            interactOnClick("onClick(" + buttonId + ")");
                                            width("25%");
                                            height("100%");
                                            //filename("Interface/ingame/images/icon/button" + (bx * 4 + by) + ".png");
                                            filename("Interface/Icons/Units/Actions/tank1_action1.png");
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
            }
        };
        return pb.build(nifty, screen, container);

    }

    public void bind(Nifty nifty, Screen screen, Element element, Properties parameter, Attributes controlDefinitionAttributes) {
        this.element = element;
        this.buttons = element.findElementByName("EntityInfoButton");
        this.titleUI = element.findElementByName("EntityInfoTitle");
        this.infosUI = element.findElementByName("EntityInfo");
        this.infoNameUI = element.findElementByName("EntityInfoName");

        buttons.add(createButtons(nifty, screen, element));


    }

    public void init(Properties parameter, Attributes controlDefinitionAttributes) {
    }

    public void onStartScreen() {
        // Change the text
    }

    public void onFocus(boolean getFocus) {
    }

    public boolean inputEvent(NiftyInputEvent inputEvent) {
        return false;
    }

    public void setInfo(String title, String name, String infos) {
        titleUI.getRenderer(TextRenderer.class).setText(title);
        infoNameUI.getRenderer(TextRenderer.class).setText(name);
        infosUI.getRenderer(TextRenderer.class).setText(infos);
    }
}
