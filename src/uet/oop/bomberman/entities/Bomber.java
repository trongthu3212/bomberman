package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.InputManager;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.SpritePlayer;

import java.util.Arrays;
import java.util.List;

public class Bomber extends Entity {
    int bomberSpeed = 2;

    private SpritePlayer moveUpSprite;
    private SpritePlayer moveDownSprite;
    private SpritePlayer moveLeftSprite;
    private SpritePlayer moveRightSprite;
    private SpritePlayer deadSprite;

    private static final double DURATION = 0.100;

    public Bomber(Map map, int x, int y) {
        super(map, x, y, FLAG_ENEMY_EATABLE, Sprite.player_down.getFxImage());

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

        map.registerForUpdating(this);
    }

    @Override
    public void update(InputManager input, double time) {
        int stepX = 0;
        int stepY = 0;

        if (input.isKeyPressed(KeyCode.LEFT)) {
            img = moveLeftSprite.playFrame(time);
            stepX = -bomberSpeed;
        } else if (input.isKeyPressed(KeyCode.RIGHT)) {
            img = moveRightSprite.playFrame(time);
            stepX = bomberSpeed;
        } else if (input.isKeyPressed(KeyCode.UP)) {
            img = moveUpSprite.playFrame(time);
            stepY = -bomberSpeed;
        } else if (input.isKeyPressed(KeyCode.DOWN)) {
            img = moveDownSprite.playFrame(time);
            stepY = bomberSpeed;
        }

        boolean shouldMove = true;

        if ((stepX != 0) || (stepY != 0)) {
            x += stepX;
            y += stepY;

            List<Entity> entities = map.getEntitiesWithFlags(FLAG_PLAYER_HARDBLOCK);
            for (Entity entity: entities) {
                if (entity.intersects(this)) {
                    shouldMove = false;
                    break;
                }
            }

            if (!shouldMove) {
                x -= stepX;
                y -= stepY;
            }
        }
    }
}
