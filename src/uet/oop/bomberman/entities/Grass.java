package uet.oop.bomberman.entities;

import uet.oop.bomberman.InputManager;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.graphics.Sprite;

public class Grass extends Entity {

    public Grass(Map map, int x, int y) {
        super(map, x, y, 0, Sprite.grass.getFxImage());
    }

    @Override
    public void update(InputManager manager, double time) {
    }
}
