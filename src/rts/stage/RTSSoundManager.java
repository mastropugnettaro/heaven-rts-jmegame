/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.stage;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import sg.atom.core.AtomMain;
import sg.atom.stage.SoundManager;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class RTSSoundManager extends SoundManager {

    private AudioNode audio_gun;
    private AudioNode audio_nature;
    private AssetManager assetManager;
    private Node rootNode;
    private AudioNode audioThemeMusic;
    private AudioNode audioTalk;

    public RTSSoundManager(AtomMain app) {
        super(app);
    }


    /**
     * We create two audio nodes.
     */
    public void initAudio() {
        /* gun shot sound is to be triggered by a mouse click. */
        audio_gun = new AudioNode(assetManager, "Sound/Effects/Gun.wav", false);
        audio_gun.setLooping(false);
        audio_gun.setVolume(2);
        rootNode.attachChild(audio_gun);

        /* nature sound - keeps playing in a loop. */
        audio_nature = new AudioNode(assetManager, "Sound/Environment/Nature.ogg", false);
        audio_nature.setLooping(true);  // activate continuous playing
        audio_nature.setPositional(false);
        audio_nature.setLocalTranslation(Vector3f.ZERO.clone());
        audio_nature.setVolume(3);

        audioThemeMusic = new AudioNode(assetManager, "Sounds/Music/Inaria.ogg", false);
        audioThemeMusic.setLooping(true);
        audioThemeMusic.setPositional(false);
        audioThemeMusic.setVolume(3);
        
        audioTalk = new AudioNode(assetManager, "Sounds/Talk/Attention.wav", false);
        audioTalk.setLooping(false);
        audioTalk.setPositional(false);
        audioTalk.setVolume(3);
    }

    public void attachAudio() {
        rootNode.attachChild(audio_gun);
        rootNode.attachChild(audio_nature);
        rootNode.attachChild(audioThemeMusic);
        audio_nature.play(); // play continuously!
        //audioThemeMusic.play();
        
    }

    public void mainMenu() {
    }

    public void inGame() {
    }

    public void winGame() {
    }

    public void talk() {
        audioTalk.play();
    }
}
