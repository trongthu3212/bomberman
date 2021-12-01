package uet.oop.bomberman.entities;

import uet.oop.bomberman.InputManager;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.SpritePlayer;

import java.util.Arrays;

public class Balloom extends Entity {
    private SpritePlayer balloomMoveLeftSprite;
    private SpritePlayer balloomMoveRightSprite;
    private SpritePlayer balloomDeadSprite;

    private static final double DURATION = 0.100;
    private static final int VELOCITY = 1;

    public Balloom(Map map, int x, int y) {
        super(map, x, y, FLAG_FLAME_EATABLE | FLAG_PLAYER_HARDBLOCK, Sprite.balloom_left1.getFxImage());

        balloomMoveLeftSprite = new SpritePlayer(
                Arrays.asList(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3),
                DURATION);

        balloomMoveRightSprite = new SpritePlayer(
                Arrays.asList(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3),
                DURATION);
        balloomMoveRightSprite = new SpritePlayer(
                Arrays.asList(Sprite.balloom_dead), DURATION);
    }

    @Override
    public void update(InputManager manager, double time) {
    }
}
