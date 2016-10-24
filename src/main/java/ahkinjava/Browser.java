package ahkinjava;

import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.input.Clipboard;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.media.MediaPlayer;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

class Browser extends Region {
 
    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();
     
    public Browser() {

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
                        System.out.println("clipboard: " + clipboard.getString());
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
    private Node createSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }
 
    @Override protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
    }
 
    @Override protected double computePrefWidth(double height) {
        return 750;
    }
 
    @Override protected double computePrefHeight(double width) {
        return 500;
    }
}