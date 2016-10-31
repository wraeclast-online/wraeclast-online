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

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import wo.Bridge;

import java.util.List;

/**
 * Created 10/31/2016.
 */
@Log4j2
public class TradeServiceTest {

    @Test
    public void test_search_tabula() throws Exception {
        TradeService tradeService = new TradeService();
        TradeParams params = new TradeParams();
        params.setName("Tabula Rasa Simple Robe");

        List<TradeItem> items = tradeService.searchTradeItems(params);

        items.forEach(log::info);
    }

    @Test
    public void test_added_chaos_dmg() throws Exception {
        TradeService tradeService = new TradeService();
        TradeParams params = new TradeParams();
        params.setName("Added Chaos Damage");
        params.setType("Gem");

        List<TradeItem> items = tradeService.searchTradeItems(params);

        items.forEach(log::info);
    }
}
