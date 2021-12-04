package uet.oop.bomberman.entities;

import uet.oop.bomberman.InputManager;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.SpritePlayer;

import java.util.Arrays;

public class Brick extends Entity {

    private SpritePlayer brickDeadSprite;
    private double timeStartDead = 0;

    private static final double DURATION = 0.1000;
    private static final double DEAD_FLAME_TIME = 0.2000;

    public Brick(Map map, int x, int y) {
        super(map, x, y, FLAG_PLAYER_HARDBLOCK | FLAG_ENEMY_HARDBLOCK | FLAG_FLAME_EATABLE, Sprite.brick.getFxImage());

        brickDeadSprite = new SpritePlayer(
                Arrays.asList(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2),
                DURATION);

        map.registerForUpdating(this);
    }

    @Override
    public void update(InputManager input, double time) {
        if (timeStartDead != 0) {
            if (time - timeStartDead > DEAD_FLAME_TIME) {
                map.despawnEntity(this);
            } else {
                img = brickDeadSprite.playFrame(time);
            }
        }
    }

    public void dead(double time) {
        timeStartDead = time;
    }
}
