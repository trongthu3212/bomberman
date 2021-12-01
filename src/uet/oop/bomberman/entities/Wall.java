package uet.oop.bomberman.entities;

import uet.oop.bomberman.InputManager;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.graphics.Sprite;

public class Wall extends Entity {

    public Wall(Map map, int x, int y) {
        super(map, x, y, FLAG_PLAYER_HARDBLOCK, Sprite.wall.getFxImage());
    }

    @Override
    public void update(InputManager manager, double time) {

    }
}
