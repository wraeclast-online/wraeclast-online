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
import javafx.embed.swing.JFXPanel;
import lombok.extern.log4j.Log4j2;
import org.aeonbits.owner.ConfigFactory;
import org.jnativehook.GlobalScreen;
import wo.javafx.UndecoratedUtilityWindow;
import wo.nativehook.NativeHook;
import wo.nativehook.NativeHookHelper;

import javax.swing.*;
import java.util.Properties;

/**
 * Created 10/31/2016.
 */
@Log4j2
public class Main {

    public static void main(String[] args) {
        Properties cmdArgs = new Properties();
        if (args.length > 0) cmdArgs.put("home.url", args[0]);

        MainConfig mainConfig = ConfigFactory.create(MainConfig.class, cmdArgs);
        log.info("Config - Home URL - {}", mainConfig.homeUrl());

        NativeHookHelper.initAndRegisterHook();

        Platform.setImplicitExit(false);

//        Application.launch(MainController.class, args);

        SwingUtilities.invokeLater(() -> {
            // initialize JavaFX, this hack was found here:
            // http://stackoverflow.com/questions/11273773/javafx-2-1-toolkit-not-initialized
            JFXPanel jfxPanel = new JFXPanel();
            Platform.runLater(() -> {
                UndecoratedUtilityWindow hostWindow = new UndecoratedUtilityWindow();
                Bridge bridge = new Bridge(hostWindow);

                NativeHook nativeHook = new NativeHook();
                GlobalScreen.addNativeKeyListener(nativeHook);

                Browser browser = new Browser(bridge, nativeHook);
                hostWindow.setScene(browser);

                nativeHook.onKeyEsc(hostWindow::hideFrame);
                nativeHook.onKeyV(() -> {
                    if (hostWindow.isPoEActive()) {
                        Platform.runLater(browser::toggleVisibility);
                    }
                });

            });
        });
    }

}
