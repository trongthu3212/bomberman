package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.InputManager;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.SpritePlayer;

import java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;
import java.util.List;

public class Balloom extends Entity {
    private SpritePlayer balloomMoveLeftSprite;
    private SpritePlayer balloomMoveRightSprite;
    private SpritePlayer balloomDeadSprite;

    private static final double DURATION = 0.200;
    private static final int VELOCITY = 1;
    private int currentDirection;

    public Balloom(Map map, int x, int y) {
        super(map, x, y, FLAG_FLAME_EATABLE | FLAG_PLAYER_HARDBLOCK, Sprite.balloom_left1.getFxImage());

        balloomMoveLeftSprite = new SpritePlayer(
                Arrays.asList(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3),
                DURATION);

        balloomMoveRightSprite = new SpritePlayer(
                Arrays.asList(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3),
                DURATION);

        balloomDeadSprite = new SpritePlayer(
                Arrays.asList(Sprite.balloom_dead), DURATION);

        currentDirection = ThreadLocalRandom.current().nextInt(0, 4);
        map.registerForUpdating(this);
    }

    @Override
    public void update(InputManager manager, double time) {
        List<Entity> entities = map.getEntitiesWithFlags(FLAG_ENEMY_HARDBLOCK);
        boolean sideMoveable[] = { true, true, true, true };
        boolean needChangeDir = false;

        int prevX = x;
        int prevY = y;

        Image previousImg = img;

        switch (currentDirection) {
            case 0:
                x -= VELOCITY;
                img = balloomMoveLeftSprite.playFrame(time);

                break;

            case 1:
                x += VELOCITY;
                img = balloomMoveRightSprite.playFrame(time);
                break;

            case 2:
                y -= VELOCITY;
                img = balloomMoveLeftSprite.playFrame(time);
                break;

            case 3:
                y += VELOCITY;
                img = balloomMoveRightSprite.playFrame(time);
                break;

            default:
                break;
        }

        for (Entity entity: entities) {
            if (entity instanceof Balloom) {
                continue;
            }
            if (entity.getIntersectSize(this) != null) {
                if (!needChangeDir) {
                    needChangeDir = true;
                    x = prevX;
                    y = prevY;
                    img = previousImg;
                }

                if (sideMoveable[0]) {
                    x -= VELOCITY;
                    if (entity.getIntersectSize(this) != null) {
                        sideMoveable[0] = false;
                    }
                    x += VELOCITY;
                }

                if (sideMoveable[1]) {
                    x += VELOCITY;
                    if (entity.getIntersectSize(this) != null) {
                        sideMoveable[1] = false;
                    }
                    x -= VELOCITY;
                }

                if (sideMoveable[2]) {
                    y -= VELOCITY;
                    if (entity.getIntersectSize(this) != null) {
                        sideMoveable[2] = false;
                    }
                    y += VELOCITY;
                }

                if (sideMoveable[3]) {
                    y += VELOCITY;
                    if (entity.getIntersectSize(this) != null) {
                        sideMoveable[3] = false;
                    }
                    y -= VELOCITY;
                }
            }
        }

        if (sideMoveable[0] || sideMoveable[1] || sideMoveable[2] || sideMoveable[3]) {
            int rangeBeg = 0;
            int rangeEnd = 4;
            if (!needChangeDir && (x % Entity.SIZE == 0) && (y % Entity.SIZE == 0)) {
                int keepMoving = ThreadLocalRandom.current().nextInt(0, 2);
                if (keepMoving != 1) {
                    if (currentDirection < 2 && (sideMoveable[2] || sideMoveable[3])) {
                        rangeBeg = 2;
                        rangeEnd = 4;

                        needChangeDir = true;
                    } else if (currentDirection >= 2 && (sideMoveable[0] || sideMoveable[1])) {
                        rangeBeg = 0;
                        rangeEnd = 2;

                        needChangeDir = true;
                    }
                }
            }

            while (needChangeDir) {
                currentDirection = ThreadLocalRandom.current().nextInt(rangeBeg, rangeEnd);
                if (sideMoveable[currentDirection]) {
                    break;
                }
            }
        }
    }
}
