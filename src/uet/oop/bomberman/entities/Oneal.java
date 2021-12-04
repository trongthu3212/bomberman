package uet.oop.bomberman.entities;

import uet.oop.bomberman.InputManager;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.SpritePlayer;

import java.util.Arrays;

public class Oneal extends Entity {

    private SpritePlayer onealMoveLeftSprite;
    private SpritePlayer onealMoveRightSprite;
    private SpritePlayer onealDeadSprite;

    private static final double DURATION = 0.100;
    private static final int VELOCITY = 1;
    private static final double DEAD_FLAME_TIME = 0.400;
    private double timeStartDead = 0;

    public Oneal(Map map, int x, int y) {
        super(map, x, y, FLAG_PLAYER_HARDBLOCK | FLAG_FLAME_EATABLE, Sprite.oneal_left1.getFxImage());

        onealMoveLeftSprite = new SpritePlayer(
                Arrays.asList(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3),
                DURATION);

        onealMoveRightSprite = new SpritePlayer(
                Arrays.asList(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3),
                DURATION);
        onealDeadSprite = new SpritePlayer(
                Arrays.asList(Sprite.oneal_dead), DURATION);

        map.registerForUpdating(this);
    }

    @Override
    public void update(InputManager input, double time) {
        if (timeStartDead != 0) {
            if (time - timeStartDead > DEAD_FLAME_TIME) {
                map.despawnEntity(this);
            } else {
                img = onealDeadSprite.playFrame(time);
            }
            return;
        }


    }

    public void dead(double time) {
        timeStartDead = time;
    }
}
