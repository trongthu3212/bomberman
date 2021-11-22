package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Bomber extends Entity {
    List<String> input;
    List<Entity> stillObjects;
    int bomberSpeed = 5;

    public Image[] frames_up = new Image[3];
    public Image[] frames_down = new Image[3];
    public Image[] frames_left = new Image[3];
    public Image[] frames_right = new Image[3];
    public Image[] frames_dead = new Image[3];
    public double duration = 0.100;


    public Bomber(int x, int y, Image img, List<String> input, List<Entity> stillObjects) {
        super(x, y, img);
        frames_up[0] = Sprite.player_up.getFxImage();
        frames_up[1] = Sprite.player_up_1.getFxImage();
        frames_up[2] = Sprite.player_up_2.getFxImage();

        frames_down[0] = Sprite.player_down.getFxImage();
        frames_down[1] = Sprite.player_down_1.getFxImage();
        frames_down[2] = Sprite.player_down_2.getFxImage();

        frames_left[0] = Sprite.player_left.getFxImage();
        frames_left[1] = Sprite.player_left_1.getFxImage();
        frames_left[2] = Sprite.player_left_2.getFxImage();

        frames_right[0] = Sprite.player_right.getFxImage();
        frames_right[1] = Sprite.player_right_1.getFxImage();
        frames_right[2] = Sprite.player_right_2.getFxImage();

        frames_dead[0] = Sprite.player_dead1.getFxImage();
        frames_dead[1] = Sprite.player_dead2.getFxImage();
        frames_dead[2] = Sprite.player_dead3.getFxImage();

        this.input = input;
        this.stillObjects = stillObjects;
    }

    @Override
    public void update(double time) {
        moving(time);
    }

    public void moving(double time) {
        if (input.contains("LEFT")) {
            img = getFrame(time, frames_left);
            x -= bomberSpeed;
            if (!canMove()) x += bomberSpeed;
        }
        else if (input.contains("RIGHT")) {
            img = getFrame(time, frames_right);
            x += bomberSpeed;
            if (!canMove()) x -= bomberSpeed;
        }
        else if (input.contains("UP")) {
            img = getFrame(time, frames_up);
            y -= bomberSpeed;
            if (!canMove()) y += bomberSpeed;
        }
        else if (input.contains("DOWN")) {
            img = getFrame(time, frames_down);
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

    public Image getFrame(double time, Image[] frames) {
        int index = (int) ((time % (frames.length * duration)) / duration);
        return frames[index];
    }
}
