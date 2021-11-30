package uet.oop.bomberman.entities;

import uet.oop.bomberman.InputManager;
import uet.oop.bomberman.graphics.Sprite;

public class FlameItem extends Entity{
    public FlameItem(int xUnit, int yUnit) {
        super(xUnit, yUnit, Sprite.powerup_flames.getFxImage());
    }

    @Override
    public void update(InputManager input, double time) {

    }
}
