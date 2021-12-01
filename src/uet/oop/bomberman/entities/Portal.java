package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.InputManager;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.graphics.Sprite;

public class Portal extends Entity {


    public Portal(Map map, int xUnit, int yUnit) {
        super(map, xUnit, yUnit, 0, Sprite.portal.getFxImage());
    }

    @Override
    public void update(InputManager input, double time) {

    }
}
