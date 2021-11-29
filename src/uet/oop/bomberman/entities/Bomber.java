package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import uet.oop.bomberman.InputManager;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.SpritePlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bomber extends Entity {
    List<Entity> stillObjects;
    int bomberSpeed = 5;

    public Image[] frames_up = new Image[3];
    public Image[] frames_down = new Image[3];
    public Image[] frames_left = new Image[3];
    public Image[] frames_right = new Image[3];
    public Image[] frames_dead = new Image[3];

    private SpritePlayer moveUpSprite;
    private SpritePlayer moveDownSprite;
    private SpritePlayer moveLeftSprite;
    private SpritePlayer moveRightSprite;

    private static final double DURATION = 0.100;

    public Bomber(int x, int y, List<Entity> stillObjects) {
        super(x, y, Sprite.player_down.getFxImage());

        moveUpSprite = new SpritePlayer(Arrays.asList(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2),
                DURATION);
        moveDownSprite = new SpritePlayer(Arrays.asList(Sprite.player_down, Sprite.player_down_1,
                Sprite.player_down_2), DURATION);
        moveLeftSprite = new SpritePlayer(Arrays.asList(Sprite.player_left, Sprite.player_left_1,
                Sprite.player_left_2), DURATION);
        moveRightSprite = new SpritePlayer(Arrays.asList(Sprite.player_right, Sprite.player_right_1,
                Sprite.player_right_2), DURATION);

        this.stillObjects = stillObjects;
    }

    @Override
    public void update(InputManager input, double time) {
        if (input.isKeyPressed(KeyCode.LEFT)) {
            img = moveLeftSprite.playFrame(time);
            x -= bomberSpeed;
            if (!canMove()) x += bomberSpeed;
        }
        else if (input.isKeyPressed(KeyCode.RIGHT)) {
            img = moveRightSprite.playFrame(time);
            x += bomberSpeed;
            if (!canMove()) x -= bomberSpeed;
        }
        else if (input.isKeyPressed(KeyCode.UP)) {
            img = moveUpSprite.playFrame(time);
            y -= bomberSpeed;
            if (!canMove()) y += bomberSpeed;
        }
        else if (input.isKeyPressed(KeyCode.DOWN)) {
            img = moveDownSprite.playFrame(time);
            y += bomberSpeed;
            if (!canMove()) y -= bomberSpeed;
        }
    }

    public boolean canMove() {
        for (Entity entity : stillObjects) {
            if (entity instanceof Wall && this.intersects(entity)) {
                return false;
            }
        }
        return true;
    }
}
