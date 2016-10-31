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

import lombok.extern.log4j.Log4j2;
import org.aeonbits.owner.ConfigFactory;

/**
 * Created 10/31/2016.
 */
@Log4j2
public class Main {

    public static void main(String[] args) {
        MainConfig mainConfig = ConfigFactory.create(MainConfig.class);
        log.info("Config - Home URL - {}", mainConfig.homeUrl());
//        if (args.length > 0) browserUrl = args[0];
//        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
//        logger.setLevel(Level.WARNING);
//        try {
//            GlobalScreen.registerNativeHook();
//        }
//        catch (NativeHookException ex) {
//            System.err.println("There was a problem registering the native hook.");
//            System.err.println(ex.getMessage());
//            System.exit(1);
//        }
//
//        Platform.setImplicitExit(false);
//        launch(args);
    }
}
