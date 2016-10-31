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

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import static org.apache.commons.lang3.StringUtils.*;

/**
 * @author thirdy
 *
 */
@Getter
public class Mod {
    private String name;
    private String value;

    public Mod(String name, String value) {
        this.name = name;
        this.value = value;
    }

//    @Override
//    public String toString() {
////				return System.lineSeparator() + "Mod [name=" + name + ", value=" + value + "]";
//        if (forgottenMod != null) {
//            return forgottenMod;
//        }
//        return toStringDisplay();
//    }
//
//    /*  Convert the ff into human readable form:
//        #Socketed Gems are Supported by level # Increased Area of Effect
//        ##% increased Physical Damage
//        #+# to Strength
//        #+# to Accuracy Rating
//        #+# Mana Gained on Kill
//        #+# to Weapon range
//     */
//    public String toStringDisplay() {
//        String display = name;
//        if (startsWith(name, "#") || startsWith(name, "$")) {
//            display = removeStart(display, "#");
//            display = removeStart(display, "$");
//            String val = endsWith(value, ".0") ? StringUtils.substringBefore(value, ".0") : value;
//            display = replaceOnce(display, "#", val);
//        }
//        return display;
//    }
//
//    public String toUUID() {
//        return name + ":" + value;
//    }
}
