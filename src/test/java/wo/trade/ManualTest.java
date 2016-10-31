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

package wo.trade;

import lombok.extern.log4j.Log4j2;
import org.aeonbits.owner.ConfigFactory;
import wo.Main;
import wo.MainConfig;

import java.util.Properties;

/**
 * Created 11/1/2016.
 */
@Log4j2
public class ManualTest {
    public static void main(String[] args) {
        Properties cmdArgs = new Properties();
        String url = ManualTest.class.getClassLoader().getResource("akara.html").toString();
        cmdArgs.put("home.url", url);
        cmdArgs.put("poe.client.window.title", MainConfig.WINDOW_TITLE_MATCH_TESTING_MODE);

        MainConfig mainConfig = ConfigFactory.create(MainConfig.class, cmdArgs);
        log.info("Config - Home URL - {}", mainConfig.homeUrl());

        Main.start(mainConfig);
    }
}
