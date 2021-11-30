package uet.oop.bomberman.entities;

import uet.oop.bomberman.InputManager;
import uet.oop.bomberman.graphics.Sprite;

public class Wall extends Entity {

    public Wall(int x, int y) {
        super(x, y, Sprite.wall.getFxImage());
    }

    @Override
    public void update(InputManager manager, double time) {

    }
}
