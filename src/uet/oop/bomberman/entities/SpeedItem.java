package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.InputManager;
import uet.oop.bomberman.graphics.Sprite;

public class SpeedItem extends Entity{
    public SpeedItem(int xUnit, int yUnit) {
        super(xUnit, yUnit, Sprite.powerup_speed.getFxImage());
    }

    @Override
    public void update(InputManager input, double time) {

    }
}
