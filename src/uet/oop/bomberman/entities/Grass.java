package uet.oop.bomberman.entities;

import uet.oop.bomberman.InputManager;
import uet.oop.bomberman.graphics.Sprite;

public class Grass extends Entity {

    public Grass(int x, int y) {
        super(x, y, Sprite.grass.getFxImage());
    }

    @Override
    public void update(InputManager manager, double time) {
    }
}
