package uet.oop.bomberman.sound;

import javafx.scene.media.Media;
import uet.oop.bomberman.entities.Bomber;

import java.net.URISyntaxException;

public class SoundEffect {
    public static Media BOMB_EXPLODE_SOUND;
    public static Media WALK_SOUND;
    public static Media POWERUP_SOUND;
    public static Media PUT_BOMB_SOUND;

    static {
        try {
            BOMB_EXPLODE_SOUND = new Media(SoundEffect.class.getResource(
                    "/sound/sound_explode.wav").toURI().toString());
            WALK_SOUND = new Media(Bomber.class.getResource("/sound/sound_walk.wav").
                    toURI().toString());
            POWERUP_SOUND = new Media(Bomber.class.getResource("/sound/sound_powerup.wav").toURI().toString());
            PUT_BOMB_SOUND = new Media(Bomber.class.getResource("/sound/sound_putbomb.wav").toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
