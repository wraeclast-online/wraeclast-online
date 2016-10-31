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

import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.time.LocalDate.now;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.apache.commons.lang3.StringUtils.*;

/**
 * Models one item in the search results
 */
@ToString
public class TradeItem {

    public String id; // the id in the search result html page
    public String buyout;
    public String name;
    public String ign;
    public boolean corrupted;
    public boolean identified;
    public Rarity rarity;

    public String dataHash; // use for verify

    public String socketsRaw;
    public String stackSize;

    public String quality;

    public String physDmgRangeAtMaxQuality;
    public String physDmgAtMaxQuality;
    public String eleDmgRange;
    public String attackSpeed;
    public String dmgAtMaxQuality;
    public String crit;
    public String level;
    public String eleDmg;

    public String armourAtMaxQuality;
    public String evasionAtMaxQuality;
    public String energyShieldAtMaxQuality;
    public String block;

    public String reqLvl;
    public String reqStr;
    public String reqInt;
    public String reqDex;
    public String mapQuantity;

    public String ageAndHighLvl;
    public String league;
    public String seller;
    public String thread;
    public String sellerid;
    public String threadUrl;
    public String online;

    public String imageUrl;

    public Mod implicitMod;
    public List<Mod> explicitMods = new ArrayList<>();

//    public List<Mod> getMods() {
//        List<Mod> mods = explicitMods.stream().collect(Collectors.toList());
//        if (implicitMod != null) {
//            mods.add(0, new Mod("--------------", null));
//            mods.add(0, implicitMod);
//        }
//        return mods;
//    }
//
//    public String wtb() {
//        if (wtb != null) {
//            return wtb;
//        }
//        return String.format(
//                "@%s Hi, WTB your \"%s\" listed for %s in %s league.",
//                ign, name, buyout, league);
//    }

//    public String toDisplay(String newLine) {
//        StringBuilder builder = new StringBuilder();
//        String _quality  = isNotBlank(quality) ? " " + quality + "%" : "";
//        String linksocks = isNotBlank(socketsRaw) ? " " + socketsRaw : "";
//        String strCorrupt = corrupted ? " Corrupted " : "";
//        strCorrupt += !identified ? " Unidentified " : "";
//        builder.append(format("[%s] %s%s%s", id, name, linksocks, _quality));
//        builder.append(format("%s -----%s------ ", newLine, strCorrupt));
//        builder.append(newLine);
//        if (implicitMod != null) {
//            builder.append(format("%s", implicitMod.toStringDisplay()));
//            builder.append(newLine + " ----------- " + newLine);
//        }
//        if (explicitMods.size() > 0) {
//            for (Mod mod : explicitMods) {
//                builder.append(format("%s", mod.toStringDisplay()));
//                builder.append(newLine);
//            }
//            builder.append("-----------" + newLine);
//        }
//        String _physDmg 	= isNotBlank(physDmgAtMaxQuality) ? ("pDPS " + physDmgAtMaxQuality) : "";
//        String _eleDmg 		= isNotBlank(eleDmg) 			  ? ("eDPS " + eleDmg) : "";
//        String _attackSpeed = isNotBlank(attackSpeed) 		  ? ("APS " + attackSpeed) : "";
//        String _crit 		= isNotBlank(crit) 				  ? ("Cc " + crit) : "";
//        String offense = format("%s %s %s %s", _physDmg, _eleDmg, _attackSpeed, _crit).trim();
//        offense = offense.isEmpty() ? "" : (offense + newLine);
//        builder.append(offense);
//
//        String _armour 		= isNotBlank(armourAtMaxQuality) 	? ("Ar " + armourAtMaxQuality) : "";
//        String _evasion 	= isNotBlank(evasionAtMaxQuality) 	? ("Ev " + evasionAtMaxQuality) : "";
//        String _energyShield = isNotBlank(energyShieldAtMaxQuality) ? ("Es " + energyShieldAtMaxQuality) : "";
//        String _block 		= isNotBlank(block) 				? ("Bk " + block) : "";
//        String defense = format("%s %s %s %s", _armour, _evasion, _energyShield, _block).trim();
//        defense = defense.isEmpty() ? "" : (defense + newLine);
//        builder.append(defense);
//
//        builder.append(format("%s IGN: %s", buyout, ign));
//        return builder.toString();
//    }


//    public String getBo() {
//        String str = buyout;
//        str = replace(str, "alteration", "alt");
//        str = replace(str, "fusing", "fuse");
//        str = replace(str, "jewellers", "jew");
//        str = replace(str, "exalted", "ex");
//        str = replace(str, "alchemy", "alch");
//        str = replace(str, "chaos", "ch");
//        str = replace(str, "chromatic", "chrm");
//        str = replace(str, "chance", "chan");
//        return str;
//    }
//
//    public String toShortDebugInfo() {
//        return String.format("id=%s name=%s account=%s thread=%s", id, name, seller, thread);
//    }

}
