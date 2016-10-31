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
import javafx.concurrent.Worker;
import javafx.scene.input.Clipboard;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import lombok.Setter;
import netscape.javascript.JSObject;
import org.aeonbits.owner.ConfigFactory;
import wo.jna.JnaHelper;
import wo.nativehook.NativeHook;

import javax.swing.*;

class Browser extends StackPane {
 
    private final WebView webView = new WebView();
    private final WebEngine webEngine = webView.getEngine();
    private MainConfig config;

    private final NativeHook nativeHook;
    private final Bridge bridge;

    private double xOffset = 0;
    private double yOffset = 0;

    @Setter
    private String url;

    public Browser(MainConfig config, Bridge bridge, NativeHook nativeHook) {
        this.config = config;
        this.bridge = bridge;
        this.nativeHook = nativeHook;
        url = config.homeUrl();

        webEngine.getLoadWorker().stateProperty().addListener(
                (ov, oldState, newState) -> {
                    if (newState == Worker.State.SUCCEEDED) {
                        Clipboard clipboard = Clipboard.getSystemClipboard();
                        JSObject win = (JSObject) webEngine.executeScript("window");
                        win.call("onJavaLoadSucceed", bridge);
                        win.call("onPoEItemCopy", clipboard.getString());
                    }
                }
        );
        // disable annoying system sound on webView alert
        webEngine.setOnAlert(event -> event.consume());
        getChildren().add(webView);

        // draggable window on alt + mouse
        webView.setOnMousePressed(event -> {
            if (nativeHook.isAltPressed()) {
                xOffset = bridge.getStage().getX() - event.getScreenX();
                yOffset = bridge.getStage().getY() - event.getScreenY();
            }
        });
        webView.setOnMouseDragged(event -> {
            if (nativeHook.isAltPressed()) {
                double x = event.getScreenX() + xOffset;
                double y = event.getScreenY() + yOffset;
                bridge.getStage().setLocation((int) x, (int) y);
            }
        });
    }

    public void load(String url) {
        webEngine.load(url);
    }

    public void toggleVisibility() {
        JFrame stage = bridge.getStage();
        boolean visible = stage.isVisible();
        if (visible) {
            stage.setVisible(false);
        } else {
            stage.setVisible(true);
            if (webEngine.getLoadWorker().getState() == Worker.State.SUCCEEDED) {
                webEngine.reload();
            } else {
                load(url);
            }
        }
    }
}
