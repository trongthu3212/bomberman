package uet.oop.bomberman.entities;

import uet.oop.bomberman.InputManager;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.graphics.Sprite;

public class BombItem extends Entity {

    public BombItem(Map map, int xUnit, int yUnit) {
        super(map, xUnit, yUnit, FLAG_FLAME_EATABLE | FLAG_PLAYER_HARDBLOCK, Sprite.powerup_bombs.getFxImage());
    }

    @Override
    public void update(InputManager input, double time) {

    }
}
