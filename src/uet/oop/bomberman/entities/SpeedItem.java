package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.InputManager;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.graphics.Sprite;

public class SpeedItem extends Entity{
    public SpeedItem(Map map, int xUnit, int yUnit) {
        super(map, xUnit, yUnit, FLAG_PLAYER_EATABLE | FLAG_FLAME_EATABLE, Sprite.powerup_speed.getFxImage());
    }

    @Override
    public void update(InputManager input, double time) {

    }
}
