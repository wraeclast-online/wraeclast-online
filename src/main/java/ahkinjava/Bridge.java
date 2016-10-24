package ahkinjava;

import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * This is what js can use to callback
 */
public class Bridge {
    private final Stage stage;

    public Bridge(Stage stage) {
        this.stage = stage;
    }

    public void setSize(int w, int h) {
        stage.getScene().getWindow().setWidth(w);
        stage.getScene().getWindow().setHeight(h);
    }

    public void exit() {
        Platform.exit();
    }
}