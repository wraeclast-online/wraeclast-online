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
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.time.LocalDate.now;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.split;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBefore;
import static org.apache.commons.lang3.StringUtils.substringBetween;
import static org.apache.commons.lang3.StringUtils.trim;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

/**
 *
 * @author thirdy
 */
public class SearchPageScraper {

	private static final String regex_horizontal_whitespace = "(^\\h*)|(\\h*$)";
	private String page;

	public SearchPageScraper(String page) {
		this.page = page;
//		try {
//			Util.overwriteFile("results.htm", page);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	public List<TradeItem> parse() {
		List<TradeItem> tradeItems = new LinkedList<>();
		Document doc = Jsoup.parse(page, "UTF-8");
		
		Element content = doc.getElementById("content");

		Elements items = null;
		if (content == null) {
			items = doc.getElementsByClass("item");
		} else {
			items = content.getElementsByClass("item");
		}

		for (Element element : items) {
			
			TradeItem item = new TradeItem();

			item.id = element.attr("id");
			item.id = StringUtils.remove(item.id, "item-container-");
			item.seller = element.attr("data-seller");
			item.thread = element.attr("data-thread");
			item.sellerid = element.attr("data-sellerid");
			item.buyout = element.attr("data-buyout");
			item.ign = element.attr("data-ign");
			item.league = element.attr("data-league");
			item.name = element.attr("data-name");
			item.corrupted = element.getElementsByClass("corrupted").size() > 0;
			item.identified = element.getElementsByClass("item-unid").size() == 0;
			

//			System.out.println(String.format("Now parsing item id %s name %s", item.id, item.name));
			
			Element sockElem = element.getElementsByClass("sockets-raw").get(0);
			item.socketsRaw = sockElem.text();
			
			Elements accntAgeElement = element.getElementsByAttributeValue("title", "account age and highest level");
			if (accntAgeElement != null && !accntAgeElement.isEmpty()) {
				item.ageAndHighLvl = accntAgeElement.get(0).text();
			}
			
			// ----- Requirements ----- //
			Element reqElem = element.getElementsByClass("requirements").get(0);
			List<TextNode> reqNodes = reqElem.textNodes();
			for (TextNode reqNode : reqNodes) {
				// sample [ Level:&nbsp;37 ,  Strength:&nbsp;42 ,  Intelligence:&nbsp;42 ] 
				String req = StringUtils.trimToEmpty(reqNode.getWholeText());
				req = req.replaceAll(regex_horizontal_whitespace,"");
				req = Util.removeThoseDamnWhiteSpace(req);
				String separator = ":";
				String reqType = trim(substringBefore(req, separator));
				switch(reqType) {
				case "Level":
					item.reqLvl = trim(substringAfter(req, separator));
					break;
				case "Strength":
					item.reqStr = trim(substringAfter(req, separator)); 
					break;
				case "Intelligence":
					item.reqInt = trim(substringAfter(req, separator)); 
					break;
				case "Dexterity":
					item.reqDex = trim(substringAfter(req, separator)); 
					break;
				}
			}
			item.mapQuantity = element.getElementsByAttributeValue("data-name", "mapq").stream()
					.findFirst()
					.map(n -> n.text())
					.map(s -> substringAfter(s, "Item quantity:"))
					.map(s -> StringUtils.removePattern(s, "[^\\d]"))
					.orElse("")
					.replaceAll(regex_horizontal_whitespace,"").trim();
			
			// ----- Rarity by checking the item name link class ----- //
			// itemframe0 - normal
			// itemframe1 - magic
			// itemframe2 - rare
			// itemframe3 - unique
			// itemframe4 - gems
			// itemframe5 - currency
			// itemframe6 - divination card
			String itemframeStr = element.getElementsByClass("title").stream()
					.findFirst()
					.map(n -> n.attr("class")).orElse(null);
			itemframeStr = Util.regexMatch("itemframe(\\d)", itemframeStr, 1);
			if (itemframeStr != null) {
				int frame = Integer.parseInt(itemframeStr);
				item.rarity = Rarity.valueOf(frame);
			} else {
				item.rarity = Rarity.unknown;
			}
			
			// ----- Verify ----- //
			item.dataHash = element.getElementsByAttributeValue("onclick", "verify_modern(this)").stream()
					.findFirst()
					.map(n -> n.attr("data-hash"))
					.orElse("").trim();

			// ----- Mods ----- //
			Elements itemModsElements = element.getElementsByClass("item-mods");
			if (itemModsElements != null && itemModsElements.size() > 0) {
				Element itemMods = itemModsElements.get(0);
				if (itemMods.getElementsByClass("bullet-item").size() != 0) {
					Element bulletItem = itemMods.getElementsByClass("bullet-item").get(0);
					Elements ulMods = bulletItem.getElementsByTag("ul");
					if (ulMods.size() == 2) {
						// implicit mod
						Elements implicitLIs = ulMods.get(0).getElementsByTag("li");
						Element implicitLi = implicitLIs.last();
						Mod impMod = new Mod(implicitLi.attr("data-name"), implicitLi.attr("data-value"));
						item.implicitMod = impMod;
					}
					int indexOfExplicitMods = ulMods.size() - 1;
					Elements modsLi = ulMods.get(indexOfExplicitMods).getElementsByTag("li");
					for (Element modLi : modsLi) {
						// explicit mods
						Mod mod = new Mod(modLi.attr("data-name"), modLi.attr("data-value"));
						item.explicitMods.add(mod);
					}
				}
			}
			
			// ----- Properties ----- //
			// this is the third column data (the first col is the image, second is the mods, reqs)
			item.quality = element.getElementsByAttributeValue("data-name", "q").get(0).text().replaceAll(regex_horizontal_whitespace,"").trim();
			item.physDmgRangeAtMaxQuality = element.getElementsByAttributeValue("data-name", "quality_pd").get(0).text().replaceAll(regex_horizontal_whitespace,"").trim();
			item.eleDmgRange = element.getElementsByAttributeValue("data-name", "ed").get(0).text().replaceAll(regex_horizontal_whitespace,"").trim();
			item.attackSpeed = element.getElementsByAttributeValue("data-name", "aps").get(0).text().replaceAll(regex_horizontal_whitespace,"").trim();
			item.dmgAtMaxQuality = element.getElementsByAttributeValue("data-name", "quality_dps").get(0).text().replaceAll(regex_horizontal_whitespace,"").trim();
			item.physDmgAtMaxQuality = element.getElementsByAttributeValue("data-name", "quality_pdps").get(0).text().replaceAll(regex_horizontal_whitespace,"").trim();
			item.eleDmg = element.getElementsByAttributeValue("data-name", "edps").get(0).text().replaceAll(regex_horizontal_whitespace,"").trim();
			item.armourAtMaxQuality = element.getElementsByAttributeValue("data-name", "quality_armour").get(0).text().replaceAll(regex_horizontal_whitespace,"").trim();
			item.evasionAtMaxQuality = element.getElementsByAttributeValue("data-name", "quality_evasion").get(0).text().replaceAll(regex_horizontal_whitespace,"").trim();
			item.energyShieldAtMaxQuality = element.getElementsByAttributeValue("data-name", "quality_shield").get(0).text().replaceAll(regex_horizontal_whitespace,"").trim();
			item.block = element.getElementsByAttributeValue("data-name", "block").get(0).text().replaceAll(regex_horizontal_whitespace,"").trim();
			item.crit = element.getElementsByAttributeValue("data-name", "crit").get(0).text().replaceAll(regex_horizontal_whitespace,"").trim();
			item.level = element.getElementsByAttributeValue("data-name", "level").get(0).text().replaceAll(regex_horizontal_whitespace,"").trim();
			item.imageUrl = element.getElementsByAttributeValue("alt", "Item icon").get(0).attr("src");
			item.stackSize = asList(split(trimToEmpty(item.imageUrl), '&')).stream()
					.filter(t -> t.startsWith("stackSize="))
					.findFirst().map(s -> substringAfter(s, "=")).orElse(null);
			
			Elements onlineSpans = element.getElementsMatchingText("online");
			if (!onlineSpans.isEmpty()) {
				item.online="Online";
			} else {
				item.online="";
			}
			
			tradeItems.add(item);
		}
//		System.out.println("DONE --- Items");

		return tradeItems;
	}

}
