package uet.oop.bomberman;

import uet.oop.bomberman.entities.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Map {
    public static List<List<Entity>> entities = new ArrayList<>();
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
            List<Entity> list = new ArrayList<>();
            for (int j = 0; j < columns; j++) {
                switch (row.charAt(j)) {
                    case '#':
                        list.add(new Wall(j, i));
                        break;
                    case '*':
                        list.add(new Brick(j, i));
                        break;
                    case 'x':
                        list.add(new Portal(j, i));
                        break;
                    case 'p':
                        list.add(new Bomber(j, i, entities));
                        break;
                    case '1':
                        list.add(new Balloom(j, i));
                        break;
                    case '2':
                        list.add(new Oneal(j, i));
                        break;
                    case 'b':
                        list.add(new BombItem(j, i));
                        break;
                    case 'f':
                        list.add(new FlameItem(j, i));
                        break;
                    case 's':
                        list.add(new SpeedItem(j, i));
                        break;
                    default:
                        list.add(new Grass(j, i));
                }
            }
            entities.add(list);
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}
