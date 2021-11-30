package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.InputManager;
import uet.oop.bomberman.graphics.Sprite;

public class Portal extends Entity {


    public Portal(int xUnit, int yUnit) {
        super(xUnit, yUnit, Sprite.portal.getFxImage());
    }

    @Override
    public void update(InputManager input, double time) {

    }
}
