package uet.oop.bomberman.entities;

import uet.oop.bomberman.InputManager;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.SpritePlayer;

import java.util.Arrays;

public class Brick extends Entity {

    private SpritePlayer brickDeadSprite;

    private static final double DURATION = 0.100;

    public Brick(Map map, int x, int y) {
        super(map, x, y, FLAG_PLAYER_HARDBLOCK | FLAG_FLAME_EATABLE,Sprite.brick.getFxImage());

        brickDeadSprite = new SpritePlayer(
                Arrays.asList(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2),
                DURATION);
    }

    @Override
    public void update(InputManager input, double time) {

    }
}
