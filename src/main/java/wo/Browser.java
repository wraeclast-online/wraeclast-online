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

import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.scene.input.Clipboard;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

class Browser extends StackPane {
 
    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();
    private final Bridge bridge;

    public Browser(Bridge bridge) {
        this.bridge = bridge;

        //apply the styles
        getStyleClass().add("browser");
        // load the web page
//        webEngine.load("http://www.oracle.com/products/index.html");
//        webEngine.load("http://exiletra.de/");

        webEngine.getLoadWorker().stateProperty().addListener(
                (ov, oldState, newState) -> {
                    System.out.println("newState: " + newState);
                    if (newState == Worker.State.SUCCEEDED) {
                        System.out.println("newState == Worker.State.SUCCEEDED --- true");
                        Clipboard clipboard = Clipboard.getSystemClipboard();
                        JSObject win = (JSObject) webEngine.executeScript("window");
                        win.call("onJavaLoadSucceed", bridge);
                        win.call("onPoEItemCopy", clipboard.getString());
                    }
                }
        );
        //add the web view to the scene
        webEngine.setOnAlert(new EventHandler<WebEvent<String>>() {
            @Override
            public void handle(WebEvent<String> event) {
                event.consume();
            }
        });
        getChildren().add(browser);

    }

//    private Node createSpacer() {
//        Region spacer = new Region();
//        HBox.setHgrow(spacer, Priority.ALWAYS);
//        return spacer;
//    }
//
//    @Override protected void layoutChildren() {
//        double w = getWidth();
//        double h = getHeight();
//        layoutInArea(browser,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
//    }
//
//    @Override protected double computePrefWidth(double height) {
//        return 750;
//    }
//
//    @Override protected double computePrefHeight(double width) {
//        return 500;
//    }
}
