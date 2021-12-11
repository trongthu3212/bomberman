package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.InputManager;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.SpritePlayer;

import java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;
import java.util.List;

public class RandomizedMoveEnemy extends Entity {
    protected SpritePlayer moveLeftSprite;
    protected SpritePlayer moveRightSprite;
    protected SpritePlayer deadSprite;

    private static final double DEAD_FLAME_TIME = 0.400;
    private int currentDirection;
    private double timeStartDead = 0;
    private double velocity = 1.0;
    private double initialVelocity = 1.0;
    private boolean randomizeVelocity = false;

    public RandomizedMoveEnemy(Map map, int x, int y, int extraFlags, double initialVelocity,
                               boolean shouldRandomizeVelocity) {
        super(map, x, y, FLAG_ENEMY | FLAG_FLAME_EATABLE | FLAG_PLAYER_HARDBLOCK | extraFlags,
                Sprite.balloom_left1.getFxImage());

        this.velocity = initialVelocity;
        this.initialVelocity = initialVelocity;
        this.randomizeVelocity = shouldRandomizeVelocity;

        currentDirection = ThreadLocalRandom.current().nextInt(0, 4);
        map.registerForUpdating(this);
    }

    @Override
    public void update(InputManager manager, double time) {
        if (timeStartDead != 0) {
            if (time - timeStartDead > DEAD_FLAME_TIME) {
                map.despawnEntity(this);
            } else {
                img = deadSprite.playFrame(time);
            }
            return;
        }

        List<Entity> entities = map.getEntitiesWithFlags(FLAG_ENEMY_HARDBLOCK);
        boolean sideMoveable[] = {true, true, true, true};
        boolean needChangeDir = false;

        double prevX = x;
        double prevY = y;

        Image previousImg = img;

        switch (currentDirection) {
            case 0:
                x -= velocity;
                img = moveRightSprite.playFrame(time);

                break;

            case 1:
                x += velocity;
                img = moveRightSprite.playFrame(time);
                break;

            case 2:
                y -= velocity;
                img = moveLeftSprite.playFrame(time);
                break;

            case 3:
                y += velocity;
                img = moveRightSprite.playFrame(time);
                break;

            default:
                break;
        }

        for (Entity entity : entities) {
            if (entity instanceof Balloom) {
                continue;
            }
            if (entity.getIntersectSize(this) != null) {
                if (entity instanceof Bomber) ((Bomber) entity).dead(time);

                if (!needChangeDir) {
                    needChangeDir = true;
                    x = prevX;
                    y = prevY;
                    img = previousImg;
                }

                if (sideMoveable[0]) {
                    x -= velocity;
                    if (entity.getIntersectSize(this) != null) {
                        sideMoveable[0] = false;
                    }
                    x += velocity;
                }

                if (sideMoveable[1]) {
                    x += velocity;
                    if (entity.getIntersectSize(this) != null) {
                        sideMoveable[1] = false;
                    }
                    x -= velocity;
                }

                if (sideMoveable[2]) {
                    y -= velocity;
                    if (entity.getIntersectSize(this) != null) {
                        sideMoveable[2] = false;
                    }
                    y += velocity;
                }

                if (sideMoveable[3]) {
                    y += velocity;
                    if (entity.getIntersectSize(this) != null) {
                        sideMoveable[3] = false;
                    }
                    y -= velocity;
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
                    if (randomizeVelocity) {
                        velocity = ThreadLocalRandom.current().nextInt(0, 2) + initialVelocity;
                    }
                    break;
                }
            }
        }
    }

    public void dead(double time) {
        timeStartDead = time;
    }
}
