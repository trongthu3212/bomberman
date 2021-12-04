package uet.oop.bomberman.entities;

import uet.oop.bomberman.InputManager;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.SpritePlayer;

import java.util.Arrays;

public class Bomb extends Entity {
    private SpritePlayer bombSprite;
    private SpritePlayer bombExplodedSprite;
    private Bomber spawner;

    private double timeStart = 0;
    private int explodeLength;
    private boolean waitExplode = true;
    private boolean isDead = false;

    private static final double DURATION = 0.2000;
    private static final double BOMB_EXPLODE_TIME = 1.5000;
    private static final double BOMB_FLAME_TIME = 0.4000;

    public Bomb(Map map, Bomber spawner, int xUnit, int yUnit, int explodeLength) {
        super(map, xUnit, yUnit, FLAG_ENEMY_HARDBLOCK | FLAG_FLAME_EATABLE, null);

        this.spawner = spawner;

        this.bombSprite = new SpritePlayer(Arrays.asList(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2), DURATION);
        this.bombExplodedSprite = new SpritePlayer(Arrays.asList(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2),
                DURATION);
        this.explodeLength = explodeLength;
        map.registerForUpdating(this);
    }

    @Override
    public void update(InputManager input, double time) {
        if (isDead) {
            int xUnit = x / Entity.SIZE;
            int yUnit = y / Entity.SIZE;

            map.spawnEntity(new Explosion(map, xUnit - 1, yUnit, 0, explodeLength));
            map.spawnEntity(new Explosion(map, xUnit + 1, yUnit, 1, explodeLength));
            map.spawnEntity(new Explosion(map, xUnit, yUnit - 1, 2, explodeLength));
            map.spawnEntity(new Explosion(map, xUnit, yUnit + 1, 3, explodeLength));

            spawner.bombExploded();
            map.despawnEntity(this);

            return;
        }

        if (timeStart == 0) {
            timeStart = time;
        }

        if (waitExplode) {
            img = bombSprite.playFrame(time);

            // First time when the bomb is spawned, the player is intersects with the bomb
            // If the player moves away from the bomb, the bomb will become solid and player can't move on it no more.
            if (spawner.getIntersectSize(this) == null) {
                flags |= FLAG_PLAYER_HARDBLOCK;
            }
        } else {
            img = bombExplodedSprite.playFrame(time);
        }

        if (waitExplode && (time - timeStart >= BOMB_EXPLODE_TIME)) {
            timeStart = time;
            waitExplode = false;

            flags &= ~(FLAG_PLAYER_HARDBLOCK | FLAG_ENEMY_HARDBLOCK);

            int xUnit = x / Entity.SIZE;
            int yUnit = y / Entity.SIZE;

            map.spawnEntity(new Explosion(map, xUnit - 1, yUnit, 0, explodeLength));
            map.spawnEntity(new Explosion(map, xUnit + 1, yUnit, 1, explodeLength));
            map.spawnEntity(new Explosion(map, xUnit, yUnit - 1, 2, explodeLength));
            map.spawnEntity(new Explosion(map, xUnit, yUnit + 1, 3, explodeLength));
        } else if (!waitExplode && (time - timeStart >= BOMB_FLAME_TIME)) {
            spawner.bombExploded();
            map.despawnEntity(this);
        }
    }

    public void dead() {
        isDead = true;
    }
}
