package uet.oop.bomberman.entities;

import uet.oop.bomberman.InputManager;
import uet.oop.bomberman.graphics.Sprite;

public class BombItem extends Entity {

    public BombItem(int xUnit, int yUnit) {
        super(xUnit, yUnit, Sprite.powerup_bombs.getFxImage());
    }

    @Override
    public void update(InputManager input, double time) {

    }
}
