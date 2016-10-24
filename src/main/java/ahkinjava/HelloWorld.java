package ahkinjava;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.Clipboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import netscape.javascript.JSObject;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.SwingDispatchService;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloWorld extends Application {
    public static void main(String[] args) {

        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        Platform.setImplicitExit(false);
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        Browser browser = new Browser();

        primaryStage.setTitle("Hello World!");
        primaryStage.initStyle(StageStyle.UNDECORATED);
//        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setAlwaysOnTop(true);

        Scene scene = new Scene(browser,448, 246, Color.web("#666970"));

        browser.browser.setOnMousePressed(event -> {
            System.out.println("setOnMousePressed");
            if (altPressed) {
                xOffset = primaryStage.getX() - event.getScreenX();
                yOffset = primaryStage.getY() - event.getScreenY();
            }
        });
        browser.browser.setOnMouseDragged(event -> {
            System.out.println("setOnMouseDragged");
            if (altPressed) {
                primaryStage.setX(event.getScreenX() + xOffset);
                primaryStage.setY(event.getScreenY() + yOffset);
            }
        });
        primaryStage.setScene(scene);
//        primaryStage.show();
        GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
            @Override
            public void nativeKeyPressed(NativeKeyEvent e) {
                if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
                    try {
                        GlobalScreen.unregisterNativeHook();
                    } catch (NativeHookException e1) {
                        e1.printStackTrace();
                    }
                    Platform.exit();
                }

                if (isCtrl(e)) {
                    ctrlPressed = true;
                }

                if (isAlt(e)) {
                    altPressed = true;
                }

                if (ctrlPressed && e.getKeyCode() == NativeKeyEvent.VC_C) {
                    System.out.println("COPY");

                    Platform.runLater( () -> {
                        System.out.println("primaryStage.isShowing() " + primaryStage.isShowing());
                        if (primaryStage.isShowing()) {
                            primaryStage.hide();
                        } else {
                            primaryStage.show();
                            browser.webEngine.load("https://poe-trademacro.github.io/awesome.html");
                        }
                    });
                }
            }

            @Override
            public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

            }
            private boolean isCtrl(NativeKeyEvent e) {
                return e.getKeyCode() == NativeKeyEvent.VC_CONTROL_L || e.getKeyCode() == NativeKeyEvent.VC_CONTROL_R;
            }
            private boolean isAlt(NativeKeyEvent e) {
                return e.getKeyCode() == NativeKeyEvent.VC_ALT_L || e.getKeyCode() == NativeKeyEvent.VC_ALT_R;
            }

            @Override
            public void nativeKeyReleased(NativeKeyEvent e) {
                if (isCtrl(e)) {
                    ctrlPressed = false;
                }
                if (isAlt(e)) {
                    altPressed = false;
                }
            }
        });
    }

    private boolean ctrlPressed = false;
    private boolean altPressed = false;
    private double xOffset = 0;
    private double yOffset = 0;
}

