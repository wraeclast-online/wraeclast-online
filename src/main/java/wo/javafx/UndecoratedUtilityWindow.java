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

package wo.javafx;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javax.swing.JFrame;

import org.aeonbits.owner.ConfigFactory;
import wo.MainConfig;
import wo.jna.JnaHelper;

// https://www.namekdev.net/2016/03/javafx-taskbar-less-undecorated-window/
public class UndecoratedUtilityWindow extends JFrame {

    public static final String WINDOW_TITLE = "Wraeclast Online";
    private JFXPanel fxContainer;
    private MainConfig config;

	public UndecoratedUtilityWindow(MainConfig config) {
        this.config = config;
		setSize(650, 500);
		setType(Type.UTILITY);
		setUndecorated(true);
		setAlwaysOnTop(true);
        setTitle(WINDOW_TITLE);
//		defaultCloseOperation = EXIT_ON_CLOSE
	}

	public void setScene(Parent root) {
        Scene scene = new Scene(root, 650, 500);
        fxContainer = new JFXPanel();
        fxContainer.setScene(scene);
        getContentPane().add(fxContainer);
    }

    public void hideFrame() {
        if (isPoEActive()) {
            this.setVisible(false);
        }
    }

    public boolean isPoEActive() {
        String activeWindowTitle = JnaHelper.getActiveWindowTitle();
        boolean isPoEWindowFocused = activeWindowTitle.equals(config.poeClientWindowTitle())
                || config.poeClientWindowTitle().equals(MainConfig.WINDOW_TITLE_MATCH_TESTING_MODE);
        boolean isWoFocused = activeWindowTitle.equals(WINDOW_TITLE);
        return isPoEWindowFocused || isWoFocused;
    }
}