package uet.oop.bomberman.graphics;

import javafx.scene.image.Image;
import java.util.List;

public class SpritePlayer {
    private List<Sprite> spriteList;
    private double duration;

    public SpritePlayer(List<Sprite> spriteList, double duration) {
        this.spriteList = spriteList;
        this.duration = duration;
    }

    public Image playFrame(double time) {
        int index = (int) ((time % (spriteList.size() * duration)) / duration);
        return spriteList.get(index).getFxImage();
    }
}
