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

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Key;
import org.jnativehook.keyboard.NativeKeyEvent;

/**
 * Created 10/31/2016.
 */
@Config.Sources({
        "file:~/wraeclast-online.properties",
        "file:wraeclast-online.properties",
    "classpath:wraeclast-online.properties"
})
public interface MainConfig extends Config {
    String WINDOW_TITLE_MATCH_TESTING_MODE = "skip_restriction_to_poe_client_only";

    @Key("home.url")
    String homeUrl();

    @Key("systemtray.icon.url")
    String systemTrayIconUrl();

    @Key("poe.client.window.title")
    @DefaultValue("Path of Exile")
    String poeClientWindowTitle();

    @Key("hotkey.vc")
    @DefaultValue("" + NativeKeyEvent.VC_BACKQUOTE)
    int hotkeyVirtualCode();
}
