package uet.oop.bomberman.entities;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import uet.oop.bomberman.InputManager;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;

public class Portal extends Entity {


    public Portal(Map map, int xUnit, int yUnit) {
        super(map, xUnit, yUnit, 0, Sprite.portal.getFxImage());
        map.registerForUpdating(this);
    }

    @Override
    public void update(InputManager input, double time) {
        List<Entity> enemies = map.getEntitiesWithFlags(FLAG_ENEMY);
        if (enemies.isEmpty()) {
            Point2D intersect = map.getBomberEntity().getIntersectSize(this);
            if (intersect != null && ((intersect.getX() >= Entity.SIZE / 4) || (intersect.getY() >= Entity.SIZE / 4))) {
                map.setGameStateVictory();
            }
        }
    }
}
