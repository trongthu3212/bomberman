package uet.oop.bomberman.entities;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.InputManager;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    protected Image img;

    protected Map map;

    //Day bitflags cho biet nhanh cac thuoc tinh cua Entity.
    protected int flags;

    public static final int FLAG_PLAYER_HARDBLOCK = 1 << 1;
    public static final int FLAG_ENEMY_HARDBLOCK = 1 << 2;
    public static final int FLAG_PLAYER_EATABLE = 1 << 3;
    public static final int FLAG_ENEMY_EATABLE = 1 << 4;
    public static final int FLAG_FLAME_EATABLE = 1 << 5;

    public static final int SIZE = Sprite.SCALED_SIZE;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị trong lưới sang tọa độ trong canvas
    //Tọa độ trong canvas sẽ là tọa độ di chuyển chính và dùng để kiểm tra va chạm.
    public Entity(Map map, int xUnit, int yUnit, int flags, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit  * Sprite.SCALED_SIZE;
        this.img = img;
        this.map = map;
        this.flags = flags;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y, Entity.SIZE, Entity.SIZE);
    }

    public abstract void update(InputManager input, double time);

    public Rectangle2D getBoundary() {
        return new Rectangle2D(x, y, Entity.SIZE, Entity.SIZE);
    }

    public Point2D getIntersectSize(Entity s) {
        Rectangle2D a = getBoundary();
        Rectangle2D b = s.getBoundary();

        double dx = Math.min(a.getMaxX(), b.getMaxX()) - Math.max(a.getMinX(), b.getMinX());
        double dy = Math.min(a.getMaxY(), b.getMaxY()) - Math.max(a.getMinY(), b.getMinY());
        if ((dx > 0) && (dy > 0)) {
            return new Point2D(dx, dy);
        }

        return null;
    }

    public int getFlags() {
        return flags;
    }
}
