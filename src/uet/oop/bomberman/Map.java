package uet.oop.bomberman;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Map {
    public List<Entity> entities = new ArrayList<>();
    public List<Entity> registeredUpdateEntities = new ArrayList<>();

    private static int rows;
    private static int columns;

    public Map(int level) throws FileNotFoundException {
        String path = "res/levels/Level" + level + ".txt";
        Scanner scanner = new Scanner(new File(path));
        scanner.nextInt();
        rows = scanner.nextInt();
        columns = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < rows; i++) {
            String row = scanner.nextLine();
            for (int j = 0; j < columns; j++) {
                switch (row.charAt(j)) {
                    case '#':
                        entities.add(new Wall(this, j, i));
                        break;
                    case '*':
                        entities.add(new Brick(this, j, i));
                        break;
                    case 'x':
                        entities.add(new Portal(this, j, i));
                        break;
                    case 'p':
                        entities.add(new Bomber(this, j, i));
                        break;
                    case '1':
                        entities.add(new Balloom(this, j, i));
                        break;
                    case '2':
                        entities.add(new Oneal(this, j, i));
                        break;
                    case 'b':
                        entities.add(new BombItem(this, j, i));
                        break;
                    case 'f':
                        entities.add(new FlameItem(this, j, i));
                        break;
                    case 's':
                        entities.add(new SpeedItem(this, j, i));
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public void registerForUpdating(Entity entity) {
        if (!registeredUpdateEntities.contains(entity)) {
            registeredUpdateEntities.add(entity);
        }
    }

    public void update(InputManager manager, double time) {
        for (Entity entity: registeredUpdateEntities) {
            entity.update(manager, time);
        }
    }

    public List<Entity> getEntitiesWithFlags(int flagMask) {
        List<Entity> result = new ArrayList<>();

        for (Entity entity: entities) {
            if ((entity.getFlags() & flagMask) != 0) {
                result.add(entity);
            }
        }

        return result;
    }

    public void render(GraphicsContext context) {
        for (Entity entity: entities) {
            if (entity != null) {
                entity.render(context);
            }
        }
    }
}
