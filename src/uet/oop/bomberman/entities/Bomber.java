package uet.oop.bomberman.entities;

import javafx.scene.input.KeyCode;
import uet.oop.bomberman.InputManager;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.SpritePlayer;

import java.util.Arrays;
import java.util.List;

public class Bomber extends Entity {
    List<List<Entity>> stillObjects;
    int bomberSpeed = 5;

    private SpritePlayer moveUpSprite;
    private SpritePlayer moveDownSprite;
    private SpritePlayer moveLeftSprite;
    private SpritePlayer moveRightSprite;
    private SpritePlayer deadSprite;

    private static final double DURATION = 0.100;

    public Bomber(int x, int y, List<List<Entity>> stillObjects) {
        super(x, y, Sprite.player_down.getFxImage());

        moveUpSprite = new SpritePlayer(Arrays.asList(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2),
                DURATION);
        moveDownSprite = new SpritePlayer(Arrays.asList(Sprite.player_down, Sprite.player_down_1,
                Sprite.player_down_2), DURATION);
        moveLeftSprite = new SpritePlayer(Arrays.asList(Sprite.player_left, Sprite.player_left_1,
                Sprite.player_left_2), DURATION);
        moveRightSprite = new SpritePlayer(Arrays.asList(Sprite.player_right, Sprite.player_right_1,
                Sprite.player_right_2), DURATION);
        deadSprite = new SpritePlayer(Arrays.asList(Sprite.player_dead1, Sprite.player_dead2,
                Sprite.player_dead3), DURATION);

        this.stillObjects = stillObjects;
    }

    @Override
    public void update(InputManager input, double time) {
        if (input.isKeyPressed(KeyCode.LEFT)) {
            img = moveLeftSprite.playFrame(time);
            x -= bomberSpeed;
            if (!canMove()) x += bomberSpeed;
        } else if (input.isKeyPressed(KeyCode.RIGHT)) {
            img = moveRightSprite.playFrame(time);
            x += bomberSpeed;
            if (!canMove()) x -= bomberSpeed;
        } else if (input.isKeyPressed(KeyCode.UP)) {
            img = moveUpSprite.playFrame(time);
            y -= bomberSpeed;
            if (!canMove()) y += bomberSpeed;
        } else if (input.isKeyPressed(KeyCode.DOWN)) {
            img = moveDownSprite.playFrame(time);
            y += bomberSpeed;
            if (!canMove()) y -= bomberSpeed;
        }
    }

    public boolean canMove() {
        for (List<Entity> entityList : stillObjects) {
            for (Entity entity : entityList) {
                if (entity instanceof Wall && this.intersects(entity)) {
                    return false;
                }
            }
        }
        return true;
    }
}
