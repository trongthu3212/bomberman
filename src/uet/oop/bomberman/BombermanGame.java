package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {

    private GraphicsContext gc;
    private Canvas canvas;

    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    private InputManager inputManager;

    private Map map = new Map(1);

    public BombermanGame() throws FileNotFoundException {
    }

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
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
        stage.show();

        final long startNanoTime = System.nanoTime();
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                double time = (currentNanoTime - startNanoTime) / 1000000000.0;

                render();
                update(time);
            }
        }.start();

        createMap();

    }

    public void createMap() {
        for (int i = 0; i < map.getRows(); i++) {
            for (int j = 0; j < map.getColumns(); j++) {
                if (map.entities.get(i).get(j) instanceof Grass || map.entities.get(i).get(j) instanceof Wall) {
                    stillObjects.add(map.entities.get(i).get(j));
                } else {
                    entities.add(map.entities.get(i).get(j));
                    stillObjects.add(new Grass(j, i));
                }
            }
        }
    }

    public void update(double time) {
        entities.forEach(entity -> entity.update(inputManager, time));
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }

}
