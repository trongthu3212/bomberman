package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Music;

import java.awt.*;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {

    private GraphicsContext gc;
    private Canvas canvas;
    private Stage stage;
    private Alert noMoreLevelAlert;

    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    private InputManager inputManager;
    private MediaPlayer themePlayer;
    private MediaPlayer victorySoundPlayer;

    private Map map = new Map(1);
    private int nextMapLevel = 1;
    private double themeGameDoneTimeStart = 0.0;

    public BombermanGame() throws FileNotFoundException, URISyntaxException {
    }

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    private void remakeGameStage() {
        canvas = new Canvas(Sprite.SCALED_SIZE * map.getColumns(), Sprite.SCALED_SIZE * map.getRows());
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);
        inputManager = new InputManager(scene);

        // Them scene vao stage
        stage.setScene(scene);
        stage.setTitle("Bomberman - Level " + nextMapLevel);
        stage.centerOnScreen();
        stage.show();
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        remakeGameStage();

        themePlayer = new MediaPlayer(Music.STAGE_THEME_MUSIC);
        themePlayer.play();

        victorySoundPlayer = new MediaPlayer(Music.STAGE_VICTORY_MUSIC);

        final long startNanoTime = System.nanoTime();
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                double time = (currentNanoTime - startNanoTime) / 1000000000.0;

                render();

                try {
                    update(time);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void update(double time) throws FileNotFoundException, URISyntaxException {
        if (noMoreLevelAlert != null) {
            return;
        }
        if (map.getGameState() != Map.GAME_STATE_PENDING) {
            if (map.getGameState() == Map.GAME_STATE_VICTORY) {
                if (themeGameDoneTimeStart == 0) {
                    themePlayer.stop();

                    themeGameDoneTimeStart = time;
                    victorySoundPlayer.play();
                } else if (time - themeGameDoneTimeStart > 4.5) {
                    nextMapLevel++;

                    try {
                        map = new Map(nextMapLevel);
                    } catch (Exception exception) {
                        noMoreLevelAlert = new Alert(Alert.AlertType.INFORMATION, "Bạn đã chơi hết các bàn!",
                                ButtonType.OK);

                        noMoreLevelAlert.show();
                        stage.close();

                        return;
                    }

                    themePlayer.play();
                    remakeGameStage();
                }
            } else {
                themePlayer.stop();
                themePlayer.play();

                map = new Map(nextMapLevel);
            }
        } else {
            map.update(inputManager, time);
        }
    }

    public void render() {
        gc.setFill(Color.web("#50a000"));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        map.render(gc);
    }

}
