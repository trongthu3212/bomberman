package uet.oop.bomberman.entities;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.InputManager;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.SpritePlayer;

import java.util.Arrays;
import java.util.List;

public class Explosion extends Entity {
    private SpritePlayer explodeSprite;
    private SpritePlayer explodeLastSprite;

    private double timeStart = 0;
    private int length;
    private int advX;
    private int advY;

    private Image explodeLastImg;

    private static final double DURATION = 0.2000;
    private static final double EXPLODE_TIME = 0.4000;

    public Explosion(Map map, int xUnit, int yUnit, int direction, int length) {
        super(map, xUnit, yUnit, 0, null);

        this.length = length;

        switch (direction) {
            case 0:
                this.explodeLastSprite = new SpritePlayer(Arrays.asList(Sprite.explosion_horizontal_left_last,
                        Sprite.explosion_horizontal_left_last1, Sprite.explosion_horizontal_left_last2), DURATION);

                this.explodeSprite = new SpritePlayer(Arrays.asList(Sprite.explosion_horizontal,
                        Sprite.explosion_horizontal1, Sprite.explosion_horizontal2), DURATION);

                this.advX = -1;
                this.advY = 0;

                break;

            case 1:
                this.explodeLastSprite = new SpritePlayer(Arrays.asList(Sprite.explosion_horizontal_right_last,
                        Sprite.explosion_horizontal_right_last1, Sprite.explosion_horizontal_right_last2), DURATION);

                this.explodeSprite = new SpritePlayer(Arrays.asList(Sprite.explosion_horizontal,
                        Sprite.explosion_horizontal1, Sprite.explosion_horizontal2), DURATION);

                this.advX = 1;
                this.advY = 0;

                break;

            case 2:
                this.explodeLastSprite = new SpritePlayer(Arrays.asList(Sprite.explosion_vertical_top_last,
                        Sprite.explosion_vertical_top_last1, Sprite.explosion_vertical_top_last2), DURATION);

                this.explodeSprite = new SpritePlayer(Arrays.asList(Sprite.explosion_vertical,
                        Sprite.explosion_vertical1, Sprite.explosion_vertical2), DURATION);

                this.advX = 0;
                this.advY = -1;

                break;

            case 3:
                this.explodeLastSprite = new SpritePlayer(Arrays.asList(Sprite.explosion_vertical_down_last,
                        Sprite.explosion_vertical_down_last1, Sprite.explosion_vertical_down_last2), DURATION);

                this.explodeSprite = new SpritePlayer(Arrays.asList(Sprite.explosion_vertical,
                        Sprite.explosion_vertical1, Sprite.explosion_vertical2), DURATION);

                this.advX = 0;
                this.advY = 1;

                break;
        }

        map.registerForUpdating(this);
    }

    @Override
    public void update(InputManager input, double time) {
        if (timeStart == 0) {
            timeStart = time;
        }

        List<Entity> eatable = map.getEntitiesWithFlags(FLAG_FLAME_EATABLE | FLAG_PLAYER_HARDBLOCK);

        if (advY == -1 || advX == -1) {
            //handle wall
            for (Entity toEat : eatable) {
                if (toEat instanceof Wall) {
                    while (toEat.getIntersectSize(this) != null) {
                        length--;
                    }
                }
            }
            //handle brick
            Entity brick = null;
            for (Entity toEat : eatable) {
                if (toEat instanceof Brick && toEat.getIntersectSize(this) != null) {
                    brick = toEat;
                }
            }
            if (brick != null) {
                while (brick.getIntersectSize(this) != null) {
                    length--;
                }
                ((Brick) brick).dead(time);
            }
            //handle rest
            for (Entity toEat : eatable) {
                if (toEat instanceof Bomb && toEat.getIntersectSize(this) != null) ((Bomb) toEat).dead();
                if (toEat instanceof Balloom && toEat.getIntersectSize(this) != null) ((Balloom) toEat).dead(time);
                if (toEat instanceof Oneal && toEat.getIntersectSize(this) != null) ((Oneal) toEat).dead(time);
                if (toEat instanceof Bomber && toEat.getIntersectSize(this) != null) ((Bomber) toEat).dead(time);
            }
        } else {
            //handle wall
            for (Entity toEat : eatable) {
                if (toEat instanceof Wall) {
                    while (toEat.getIntersectSize(this) != null) {
                        length--;
                    }
                }
            }
            //handle brick
            for (Entity toEat : eatable) {
                if (toEat.getIntersectSize(this) != null) {
                    if (toEat instanceof Brick) {
                        while (toEat.getIntersectSize(this) != null) {
                            length--;
                        }
                        ((Brick) toEat).dead(time);
                    }
                }
            }
            //handle brick
            for (Entity toEat : eatable) {
                if (toEat instanceof Bomb && toEat.getIntersectSize(this) != null) ((Bomb) toEat).dead();
                if (toEat instanceof Balloom && toEat.getIntersectSize(this) != null) ((Balloom) toEat).dead(time);
                if (toEat instanceof Oneal && toEat.getIntersectSize(this) != null) ((Oneal) toEat).dead(time);
                if (toEat instanceof Bomber && toEat.getIntersectSize(this) != null) ((Bomber) toEat).dead(time);
            }
        }

        if (time - timeStart > EXPLODE_TIME) {
            map.despawnEntity(this);
        } else {
            img = explodeSprite.playFrame(time);
            explodeLastImg = explodeLastSprite.playFrame(time);
        }

    }

    @Override
    public Rectangle2D getBoundary() {
        int sizeX = (advX != 0) ? length * Entity.SIZE : Entity.SIZE;
        int sizeY = (advY != 0) ? length * Entity.SIZE : Entity.SIZE;

        if ((advX < 0) || (advY < 0)) {
            return new Rectangle2D(x + (length - 1) * advX * Entity.SIZE, y + (length - 1) * advY * Entity.SIZE,
                    sizeX, sizeY);
        } else {
            return new Rectangle2D(x, y, sizeX, sizeY);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        for (int i = 0; i < length - 1; i++) {
            gc.drawImage(img, (x + i * advX * Entity.SIZE), (y + i * advY * Entity.SIZE));
        }

        gc.drawImage(explodeLastImg, (x + (length - 1) * advX * Entity.SIZE), (y + (length - 1) * advY * Entity.SIZE));
    }
}
