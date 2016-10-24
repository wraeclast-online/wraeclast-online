package ahkinjava;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
        primaryStage.setTitle("Hello World!");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setAlwaysOnTop(true);
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });

        GlobalScreen.setEventDispatcher(new SwingDispatchService());
        GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
            @Override
            public void nativeKeyPressed(NativeKeyEvent e) {
                if (isCtrl(e)) {
                    ctrlPressed = true;
                }

                if (ctrlPressed && e.getKeyCode() == NativeKeyEvent.VC_C) {
                    System.out.println("COPY");

                    Platform.runLater( () -> {
                        System.out.println("primaryStage.isShowing() " + primaryStage.isShowing());
                        if (primaryStage.isShowing()) {
                            primaryStage.hide();
                        } else {
                            primaryStage.show();
                        }
                    } );
                }
            }

            @Override
            public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

            }
            private boolean isCtrl(NativeKeyEvent e) {
                return e.getKeyCode() == NativeKeyEvent.VC_CONTROL_L || e.getKeyCode() == NativeKeyEvent.VC_CONTROL_R;
            }

            private boolean ctrlPressed = false;
            @Override
            public void nativeKeyReleased(NativeKeyEvent e) {
                if (isCtrl(e)) {
                    ctrlPressed = false;
                }
            }
        });

//        primaryStage.focusedProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//
//            }
//        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);
        Scene scene = new Scene(root, 448, 246);
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
    }
}