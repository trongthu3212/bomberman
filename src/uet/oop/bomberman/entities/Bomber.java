package uet.oop.bomberman.entities;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.media.Media;
import javafx.scene.input.KeyCode;
import javafx.scene.media.MediaPlayer;
import uet.oop.bomberman.InputManager;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.SpritePlayer;
import uet.oop.bomberman.sound.SoundEffect;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class Bomber extends Entity {
    int bomberSpeed = 2;

    private SpritePlayer moveUpSprite;
    private SpritePlayer moveDownSprite;
    private SpritePlayer moveLeftSprite;
    private SpritePlayer moveRightSprite;
    private SpritePlayer deadSprite;


    private int lastStepX;
    private int lastStepY;

    private int bombLeft;
    private int lastBombPutUnitX;
    private int lastBombPutUnitY;
    private int bombLength;
    private double lastBombSpawnTime = 0;
    private MediaPlayer walkSoundPlayer;
    private MediaPlayer powerUpSoundPlayer;
    private MediaPlayer putBombSoundPlayer;
    private double lastWalkSoundTime = 0;

    private static final double DURATION = 0.100;
    private static final double BOMB_SPAWN_COOLDOWN = 0.300;
    private static final double DEAD_FLAME_TIME = 0.400;
    private static final double WALK_SOUND_DURATION = 1.000;
    private double timeStartDead = 0;

    public Bomber(Map map, int x, int y) throws URISyntaxException {
        super(map, x, y, FLAG_ENEMY_EATABLE | FLAG_FLAME_EATABLE | FLAG_ENEMY_HARDBLOCK, Sprite.player_down.getFxImage());

        bombLeft = 1;
        bombLength = 1;

        lastBombPutUnitX = -1;
        lastBombPutUnitY = -1;

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

        walkSoundPlayer = new MediaPlayer(SoundEffect.WALK_SOUND);
        powerUpSoundPlayer = new MediaPlayer(SoundEffect.POWERUP_SOUND);
        putBombSoundPlayer = new MediaPlayer(SoundEffect.PUT_BOMB_SOUND);

        map.registerForUpdating(this);
    }

    public void trySpawnBomb(double currentTime) {
        if ((bombLeft <= 0) || (currentTime - lastBombSpawnTime < BOMB_SPAWN_COOLDOWN)) {
            return;
        }

        // Round up the unit to spawn depending on how many percentage of the tile we are sitting on
        int unitSpawnBombX = (x + ((x % Entity.SIZE >= 24) ? Entity.SIZE - 1 : 0)) / Entity.SIZE;
        int unitSpawnBombY = (y + ((y % Entity.SIZE >= 24) ? Entity.SIZE - 1 : 0)) / Entity.SIZE;

        if ((lastBombPutUnitX > 0) && (lastBombPutUnitY > 0)) {
            if ((lastBombPutUnitX == unitSpawnBombX) && (lastBombPutUnitY == unitSpawnBombY)) {
                // Can't place bomb at the same place
                return;
            }
        }

        lastBombPutUnitX = unitSpawnBombX;
        lastBombPutUnitY = unitSpawnBombY;

        bombLeft--;
        lastBombSpawnTime = currentTime;

        putBombSoundPlayer.stop();
        putBombSoundPlayer.play();

        map.spawnEntity(new Bomb(map, this, unitSpawnBombX, unitSpawnBombY, bombLength));
    }

    public void bombExploded() {
        bombLeft++;
    }

    @Override
    public void update(InputManager input, double time) {
        if (timeStartDead != 0) {
            if (time - timeStartDead > DEAD_FLAME_TIME) {
                map.despawnEntity(this);
            } else {
                img = deadSprite.playFrame(time);
            }
            return;
        }

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

        if (input.isKeyPressed(KeyCode.SPACE)) {
            trySpawnBomb(time);
        }

        boolean shouldMove = true;

        if ((stepX != 0) || (stepY != 0)) {
            // Try smooth movement on turn
            if ((stepX != 0) && (lastStepY != 0)) {
                if (y % Entity.SIZE >= 24) {
                    stepY = (y + Entity.SIZE - 1) / Entity.SIZE * Entity.SIZE - y;
                } else if ((y % Entity.SIZE <= 8)) {
                    stepY = y / Entity.SIZE * Entity.SIZE - y;
                }
            } else if ((stepY != 0) && (lastStepX != 0)) {
                if (x % Entity.SIZE >= 24) {
                    stepX = (x + Entity.SIZE - 1) / Entity.SIZE * Entity.SIZE - x;
                } else if ((x % Entity.SIZE <= 8)) {
                    stepX = x / Entity.SIZE * Entity.SIZE - x;
                }
            }

            x += stepX;
            y += stepY;

            List<Entity> entities = map.getEntitiesWithFlags(FLAG_PLAYER_HARDBLOCK | FLAG_PLAYER_EATABLE);
            for (Entity entity : entities) {
                Point2D intersectSize = entity.getIntersectSize(this);
                if (intersectSize != null) {
                    boolean meetPowerup = false;
                    if (entity instanceof SpeedItem) {
                        bomberSpeed += 1;
                        map.despawnEntity(entity);
                        meetPowerup = true;
                    }
                    if (entity instanceof BombItem) {
                        bombLeft += 1;
                        map.despawnEntity(entity);
                        meetPowerup = true;
                    }
                    if (entity instanceof FlameItem) {
                        bombLength += 1;
                        map.despawnEntity(entity);
                        meetPowerup = true;
                    }
                    if (meetPowerup) {
                        powerUpSoundPlayer.stop();
                        powerUpSoundPlayer.play();

                        continue;
                    }
                    // Perform smooth edge move
                    if ((intersectSize.getX() > 4) || (intersectSize.getY() > 4)) {
                        shouldMove = false;
                        break;
                    }
                }
            }

            if (!shouldMove) {
                x -= stepX;
                y -= stepY;
            } else {
                if (time - lastWalkSoundTime > WALK_SOUND_DURATION / bomberSpeed) {
                    lastWalkSoundTime = time;

                    walkSoundPlayer.stop();
                    walkSoundPlayer.play();
                }

                lastStepY = stepY;
                lastStepX = stepX;
            }
        }
    }

    public void dead(double time) {
        timeStartDead = time;
    }
}
