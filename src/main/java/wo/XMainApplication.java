///*
// *     This file is part of wraelclast-online.
// *
// *     wraelclast-online is free software: you can redistribute it and/or modify
// *     it under the terms of the GNU General Public License as published by
// *     the Free Software Foundation, either version 3 of the License, or
// *     (at your option) any later version.
// *
// *     wraelclast-online is distributed in the hope that it will be useful,
// *     but WITHOUT ANY WARRANTY; without even the implied warranty of
// *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// *     GNU General Public License for more details.
// *
// *     You should have received a copy of the GNU General Public License
// *     along with wraelclast-online.  If not, see <http://www.gnu.org/licenses/>.
// */
//
//package wo;
//
//import javafx.application.Application;
//import javafx.application.Platform;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//import javafx.stage.StageStyle;
//import lombok.Setter;
//import lombok.extern.log4j.Log4j2;
//import org.aeonbits.owner.ConfigFactory;
//import org.jnativehook.GlobalScreen;
//import wo.jna.JnaHelper;
//import wo.nativehook.NativeHook;
//
///**
// * Created 10/31/2016.
// */
//@Log4j2
//public class MainController extends Application {
//    private Stage stage;
//    private Browser browser;
//    private MainConfig mainConfig = ConfigFactory.create(MainConfig.class);
//
//    @Setter
//    private String browserUrl = mainConfig.homeUrl();
//    NativeHook nativeHook = new NativeHook();
//
//    @Override
//    public void start(Stage primaryStage) {
//        this.stage = primaryStage;
//        Bridge bridge = new Bridge(stage);
//        browser = new Browser(bridge, nativeHook);
//
//        primaryStage.setTitle("Hello World!");
//        primaryStage.initStyle(StageStyle.UNDECORATED);
//        primaryStage.setAlwaysOnTop(true);
//
//        Scene scene = new Scene(browser, 650, 500);
//        primaryStage.setScene(scene);
//
//        SystemTray st = new SystemTray(this);
//        st.addAppToTray();
//
//        nativeHook.onKeyEsc(this::toggleBrowserVisibility);
//        nativeHook.onKeyTilde(this::toggleBrowserVisibility);
//        GlobalScreen.addNativeKeyListener(nativeHook);
//    }
//
//    void toggleBrowserVisibility() {
//        Platform.runLater( () -> {
//            if (JnaHelper.getActiveWindowTitle().equals(mainConfig.poeClientWindowTitle())) {
//                if (stage.isShowing()) {
//                    stage.hide();
//                } else {
//                    stage.show();
//                    browser.webEngine.load(browserUrl);
//                }
//            }
//        });
//    }
//
//}