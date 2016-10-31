/*
 *     This file is part of wraelclast-online.
 *
 *     wraelclast-online is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     wraelclast-online is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with wraelclast-online.  If not, see <http://www.gnu.org/licenses/>.
 */

package wo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created 10/31/2016.
 */
public class MainApplication extends Application {
    private Stage stage;
    private Browser browser;

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        Bridge bridge = new Bridge(stage);
        browser = new Browser(bridge);

        primaryStage.setTitle("Hello World!");
        primaryStage.initStyle(StageStyle.UNDECORATED);
//        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setAlwaysOnTop(true);

//        Scene scene = new Scene(browser,448, 246, Color.web("#666970"));
        Scene scene = new Scene(browser,650, 500);

        browser.browser.setOnMousePressed(event -> {
            if (altPressed) {
                xOffset = primaryStage.getX() - event.getScreenX();
                yOffset = primaryStage.getY() - event.getScreenY();
            }
        });
        browser.browser.setOnMouseDragged(event -> {
            if (altPressed) {
                primaryStage.setX(event.getScreenX() + xOffset);
                primaryStage.setY(event.getScreenY() + yOffset);
            }
        });
        primaryStage.setScene(scene);
//        primaryStage.show();
        addAppToTray();

        GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
            @Override
            public void nativeKeyPressed(NativeKeyEvent e) {
                if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
                    Platform.runLater( () -> {
                        if (primaryStage.isShowing()) {
                            primaryStage.hide();
                        }
                    } );
                }

                if (isCtrl(e)) {
                    ctrlPressed = true;
                }

                if (isAlt(e)) {
                    altPressed = true;
                }

//                if (ctrlPressed && e.getKeyCode() == NativeKeyEvent.VC_C) {
                if (e.getKeyCode() == NativeKeyEvent.VC_V) {
                    System.out.println("COPY");
                    toggleBrowserVisibility();
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

    private void toggleBrowserVisibility() {
        Platform.runLater( () -> {
            System.out.println("primaryStage.isShowing() " + stage.isShowing());
            if (stage.isShowing()) {
                stage.hide();
            } else {
                stage.show();
                browser.webEngine.load(browserUrl);
            }
        });
    }

    private boolean ctrlPressed = false;
    private boolean altPressed = false;
    private double xOffset = 0;
    private double yOffset = 0;
    private static final String iconImageLoc =
            "https://raw.githubusercontent.com/PoE-TradeMacro/ahk-in-java/master/src/wo.main/resources/Portal_skill_icon.png";

    static String browserUrl = "https://poe-trademacro.github.io/awesome.html";


    /**
     * Sets up a system tray icon for the application.
     */
    private void addAppToTray() {
        try {
            // ensure awt toolkit is initialized.
            java.awt.Toolkit.getDefaultToolkit();

            // app requires system tray support, just exit if there is no support.
            if (!java.awt.SystemTray.isSupported()) {
                System.out.println("No system tray support, application exiting.");
                platformExitAndUnregisterHook();
            }

            // set up a system tray icon.
            java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();
            URL imageLoc = new URL(
                    iconImageLoc
            );
            java.awt.Image image = ImageIO.read(imageLoc);
            java.awt.TrayIcon trayIcon = new java.awt.TrayIcon(image);

            // if the user double-clicks on the tray icon, show the wo.main app stage.
            trayIcon.addActionListener(event -> Platform.runLater(this::toggleBrowserVisibility));

            // if the user selects the default menu item (which includes the app name),
            // show the wo.main app stage.
            java.awt.MenuItem openItem = new java.awt.MenuItem("Wraeclast Online (v)");
            openItem.addActionListener(event -> Platform.runLater(this::toggleBrowserVisibility));

            // the convention for tray icons seems to be to set the default icon for opening
            // the application stage in a bold font.
            java.awt.Font defaultFont = java.awt.Font.decode(null);
            java.awt.Font boldFont = defaultFont.deriveFont(java.awt.Font.BOLD);
            openItem.setFont(boldFont);

            // to really exit the application, the user must go to the system tray icon
            // and select the exit option, this will shutdown JavaFX and remove the
            // tray icon (removing the tray icon will also shut down AWT).
            java.awt.MenuItem exitItem = new java.awt.MenuItem("Exit");
            exitItem.addActionListener(event -> {
                platformExitAndUnregisterHook();
                tray.remove(trayIcon);
            });

            java.awt.MenuItem urlItem = new java.awt.MenuItem("Set URL");
            urlItem.addActionListener(event -> {
                String url = JOptionPane.showInputDialog("Enter URL");
                browserUrl = url;
            });

            // setup the popup menu for the application.
            final java.awt.PopupMenu popup = new java.awt.PopupMenu();
            popup.add(openItem);
            popup.add(urlItem);
            popup.addSeparator();
            popup.add(exitItem);
            trayIcon.setPopupMenu(popup);

            // add the application tray icon to the system tray.
            tray.add(trayIcon);
        } catch (java.awt.AWTException | IOException e) {
            System.out.println("Unable to init system tray");
            e.printStackTrace();
        }
    }

    private void platformExitAndUnregisterHook() {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e1) {
            e1.printStackTrace();
        }
        Platform.exit();
    }
}