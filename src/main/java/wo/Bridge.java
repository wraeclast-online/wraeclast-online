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
import javafx.stage.Stage;

/**
 * This is what js can use to callback
 */
public class Bridge {
    private final Stage stage;

    public Bridge(Stage stage) {
        this.stage = stage;
    }

    public void setSize(int w, int h) {
        stage.getScene().getWindow().setWidth(w);
        stage.getScene().getWindow().setHeight(h);
    }

    public void exit() {
        Platform.exit();
    }
}