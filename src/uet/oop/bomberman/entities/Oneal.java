package uet.oop.bomberman.entities;

import uet.oop.bomberman.InputManager;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.SpritePlayer;

import java.util.Arrays;

public class Oneal extends RandomizedMoveEnemy {
    private static final double DURATION = 0.100;

    public Oneal(Map map, int x, int y) {
        super(map, x, y, 0, 1.0, true);

        this.moveLeftSprite = new SpritePlayer(
                Arrays.asList(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3),
                DURATION);

        this.moveRightSprite = new SpritePlayer(
                Arrays.asList(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3),
                DURATION);

        this.deadSprite = new SpritePlayer(
                Arrays.asList(Sprite.oneal_dead), DURATION);
    }
}
