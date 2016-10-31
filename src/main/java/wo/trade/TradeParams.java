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

import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created 10/31/2016.
 */
@Setter
public class TradeParams {

    private String league = "Essence";
    private String type = "";
    private String base = "";
    private String name = "";
    private String dmg_min = "";
    private String dmg_max = "";
    private String aps_min = "";
    private String aps_max = "";
    private String crit_min = "";
    private String crit_max = "";
    private String dps_min = "";
    private String dps_max = "";
    private String edps_min = "";
    private String edps_max = "";
    private String pdps_min = "";
    private String pdps_max = "";
    private String armour_min = "";
    private String armour_max = "";
    private String evasion_min = "";
    private String evasion_max = "";
    private String shield_min = "";
    private String shield_max = "";
    private String block_min = "";
    private String block_max = "";
    private String sockets_min = "";
    private String sockets_max = "";
    private String link_min = "";
    private String link_max = "";
    private String sockets_r = "";
    private String sockets_g = "";
    private String sockets_b = "";
    private String sockets_w = "";
    private String linked_r = "";
    private String linked_g = "";
    private String linked_b = "";
    private String linked_w = "";
    private String rlevel_min = "";
    private String rlevel_max = "";
    private String rstr_min = "";
    private String rstr_max = "";
    private String rdex_min = "";
    private String rdex_max = "";
    private String rint_min = "";
    private String rint_max = "";
    private String mod_name = "";
    private String mod_min = "";
    private String mod_max = "";
    private String group_type = "And";
    private String group_min = "";
    private String group_max = "";
    private String group_count = "1";
    private String q_min = "";
    private String q_max = "";
    private String level_min = "";
    private String level_max = "";
    private String ilvl_min = "";
    private String ilvl_max = "";
    private String rarity = "";
    private String seller = "";
    private String thread = "";
    private String identified = "";
    private String corrupted = "";
    private String online = "x";
    private String buyout = "";
    private String altart = "";
    private String capquality = "x";
    private String buyout_min = "";
    private String buyout_max = "";
    private String buyout_currency = "";
    private String crafted = "";
    private String enchanted = "";

    @Override
    public String toString() {
        return asMap()
                .entrySet()
                .stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("&"));
    }

    private Map<String, String> asMap() {
        Map<String, String> map = new HashMap<>();
        map.put("league", league);
        map.put("type", type);
        map.put("base", base);
        map.put("name", name);
        map.put("dmg_min", dmg_min);
        map.put("dmg_max", dmg_max);
        map.put("aps_min", aps_min);
        map.put("aps_max", aps_max);
        map.put("crit_min", crit_min);
        map.put("crit_max", crit_max);
        map.put("dps_min", dps_min);
        map.put("dps_max", dps_max);
        map.put("edps_min", edps_min);
        map.put("edps_max", edps_max);
        map.put("pdps_min", pdps_min);
        map.put("pdps_max", pdps_max);
        map.put("armour_min", armour_min);
        map.put("armour_max", armour_max);
        map.put("evasion_min", evasion_min);
        map.put("evasion_max", evasion_max);
        map.put("shield_min", shield_min);
        map.put("shield_max", shield_max);
        map.put("block_min", block_min);
        map.put("block_max", block_max);
        map.put("sockets_min", sockets_min);
        map.put("sockets_max", sockets_max);
        map.put("link_min", link_min);
        map.put("link_max", link_max);
        map.put("sockets_r", sockets_r);
        map.put("sockets_g", sockets_g);
        map.put("sockets_b", sockets_b);
        map.put("sockets_w", sockets_w);
        map.put("linked_r", linked_r);
        map.put("linked_g", linked_g);
        map.put("linked_b", linked_b);
        map.put("linked_w", linked_w);
        map.put("rlevel_min", rlevel_min);
        map.put("rlevel_max", rlevel_max);
        map.put("rstr_min", rstr_min);
        map.put("rstr_max", rstr_max);
        map.put("rdex_min", rdex_min);
        map.put("rdex_max", rdex_max);
        map.put("rint_min", rint_min);
        map.put("rint_max", rint_max);
        map.put("mod_name", mod_name);
        map.put("mod_min", mod_min);
        map.put("mod_max", mod_max);
        map.put("group_type", group_type);
        map.put("group_min", group_min);
        map.put("group_max", group_max);
        map.put("group_count", group_count);
        map.put("q_min", q_min);
        map.put("q_max", q_max);
        map.put("level_min", level_min);
        map.put("level_max", level_max);
        map.put("ilvl_min", ilvl_min);
        map.put("ilvl_max", ilvl_max);
        map.put("rarity", rarity);
        map.put("seller", seller);
        map.put("thread", thread);
        map.put("identified", identified);
        map.put("corrupted", corrupted);
        map.put("online", online);
        map.put("buyout", buyout);
        map.put("altart", altart);
        map.put("capquality", capquality);
        map.put("buyout_min", buyout_min);
        map.put("buyout_max", buyout_max);
        map.put("buyout_currency", buyout_currency);
        map.put("crafted", crafted);
        map.put("enchanted", enchanted);
        return map;
    }
}
