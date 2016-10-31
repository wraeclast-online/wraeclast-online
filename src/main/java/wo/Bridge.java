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

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import wo.trade.*;

import javax.swing.*;
import java.util.List;

/**
 * This is what js can use to callback
 */
public class Bridge {
    @Getter
    private final JFrame stage;
    private final TradeService tradeService = new TradeService();

    public Bridge(JFrame stage) {
        this.stage = stage;
    }

    public void setSize(int w, int h) {
        stage.setSize(w, h);
    }

    public void exit() {
        Platform.exit();
    }

    public void copyToClipboard(String str) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(str);
        clipboard.setContent(content);
    }

    public TradeParams newTradeParams() {
        return new TradeParams();
    }

    /**
     * SAMPLE OUTPUT:
        {
          "tradeItems" : [{
             "id" : "0",
             "buyout" : "1 fusing",
             "name" : "Life Leech Support",
             "ign" : "Панночка",
             "corrupted" : false,
             "identified" : true,
             "rarity" : "gem",
             "dataHash" : "",
             "socketsRaw" : "",
             "quality" : "0",
             "physDmgRangeAtMaxQuality" : "",
             "physDmgAtMaxQuality" : "",
             "eleDmgRange" : "",
             "attackSpeed" : "",
             "dmgAtMaxQuality" : "",
             "crit" : "",
             "level" : "1",
             "eleDmg" : "",
             "armourAtMaxQuality" : "",
             "evasionAtMaxQuality" : "",
             "energyShieldAtMaxQuality" : "",
             "block" : "",
             "mapQuantity" : "",
             "league" : "Essence",
             "seller" : "Gaerovich",
             "thread" : "",
             "sellerid" : "None",
             "online" : "Online",
             "imageUrl" : "https://web.poecdn.com/image/Art/2DItems/Gems/Support/LifeLeech.png?scale\u003d1\u0026w\u003d1\u0026h\u003d1\u0026v\u003da286b7cb68bee2319a14a80c1e4bcf9c3",
             "explicitMods" : []
             }]
        }
     */
    public String searchTradeItems(TradeParams params) {
        List<TradeItem> tradeItems = tradeService.searchTradeItems(params);
        SearchTradeResult searchTradeResult = new SearchTradeResult();
        searchTradeResult.tradeItems = tradeItems;
        Gson gson = new Gson();
        String json = gson.toJson(searchTradeResult);
        return json;
    }

    public static class SearchTradeResult {
        private List<TradeItem> tradeItems;
    }
}