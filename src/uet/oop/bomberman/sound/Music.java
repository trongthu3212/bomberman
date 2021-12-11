package uet.oop.bomberman.sound;

import javafx.scene.media.Media;
import uet.oop.bomberman.entities.Bomber;

import java.net.URISyntaxException;

public class Music {
    public static Media STAGE_THEME_MUSIC;
    public static Media STAGE_VICTORY_MUSIC;

    static {
        try {
            STAGE_THEME_MUSIC = new Media(SoundEffect.class.getResource(
                    "/sound/sound_theme.mp3").toURI().toString());
            STAGE_VICTORY_MUSIC = new Media(SoundEffect.class.getResource(
                    "/sound/sound_victory.mp3").toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
