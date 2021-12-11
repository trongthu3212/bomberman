package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.InputManager;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.SpritePlayer;

import java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;
import java.util.List;

public class Balloom extends RandomizedMoveEnemy {
    private static final double DURATION = 0.200;

    public Balloom(Map map, int x, int y) {
        super(map, x, y, 0, 1.0, false);

        this.moveLeftSprite = new SpritePlayer(Arrays.asList(Sprite.balloom_left1,
                Sprite.balloom_left2, Sprite.balloom_left3), DURATION);

        this.moveRightSprite = new SpritePlayer(Arrays.asList(Sprite.balloom_right1, Sprite.balloom_right2,
                Sprite.balloom_right3), DURATION);

        this.deadSprite = new SpritePlayer(Arrays.asList(Sprite.balloom_dead), DURATION);
    }
}
