package uet.oop.bomberman;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;

public class InputManager {
    private List<KeyCode> keyCodes;

    public InputManager(Scene scene) {
        keyCodes = new ArrayList<KeyCode>();

        scene.setOnKeyPressed(
                e -> {
                    if (!keyCodes.contains(e.getCode()))
                        keyCodes.add(e.getCode());
                });

        scene.setOnKeyReleased(
                e -> {
                    KeyCode code = e.getCode();
                    keyCodes.remove(code);
                });
    }

    public boolean isKeyPressed(KeyCode key) {
        return keyCodes.contains(key);
    }
}
