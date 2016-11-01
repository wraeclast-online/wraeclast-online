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

import javafx.application.Platform;
import lombok.extern.log4j.Log4j2;
import org.aeonbits.owner.ConfigFactory;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import wo.trade.Util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;

/**
 * Created 10/31/2016.
 */
@Log4j2
public class SystemTray {

    private final Browser browser;
    private final MainConfig config;

    public SystemTray(MainConfig config, Browser browser) {
        this.browser = browser;
        this.config = config;
    }

    void addAppToTray() {
        try {
            // ensure awt toolkit is initialized.
            java.awt.Toolkit.getDefaultToolkit();

            // app requires system tray support, just exit if there is no support.
            if (!java.awt.SystemTray.isSupported()) {
                log.error("No system tray support, application exiting.");
                platformExitAndUnregisterHook();
            }

            // set up a system tray icon.
            java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();
            URL imageLoc = new URL(config.systemTrayIconUrl());
            java.awt.Image image = ImageIO.read(imageLoc);
            java.awt.TrayIcon trayIcon = new java.awt.TrayIcon(image);

            // if the user double-clicks on the tray icon, show the wo.main app stage.
//            trayIcon.addActionListener(event -> Platform.runLater(mainApplication::toggleBrowserVisibility));

            // if the user selects the default menu item (which includes the app name),
            // show the wo.main app stage.
            java.awt.MenuItem openItem = new java.awt.MenuItem("Wraeclast Online Website");
            openItem.addActionListener(event -> Platform.runLater(Util::openWebsite));

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
                String browserUrl = JOptionPane.showInputDialog("Enter URL");
                browser.setUrl(browserUrl);
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
            log.error("Unable to init system tray", e);
        }
    }

    private void platformExitAndUnregisterHook() {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e1) {
            log.error(e1);
        }
        Platform.exit();
        System.exit(0);
    }
}
