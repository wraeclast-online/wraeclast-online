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

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBefore;

@Log4j2
public class Util {

	public static String removeThoseDamnWhiteSpace(String s) {
		s = StringUtils.deleteWhitespace(s);
		StringBuilder sb = new StringBuilder();
		char[] charArray = s.toCharArray();
		for (char c : charArray) {
			if (!Character.isSpaceChar(c)) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String regexMatch(String regex, String s, int group) {
		if (s != null) {
			List<String> matches = regexMatches(regex, s, group, Pattern.CASE_INSENSITIVE);
			if (!matches.isEmpty()) {
				return matches.get(0);
			}
		}
		return null;
	}
	
	public static List<String> regexMatches(String regex, String s, int group) {
		return regexMatches(regex, s, group, Pattern.CASE_INSENSITIVE);
	}

	public static List<String> regexMatches(String regex, String s, int group, int flags) {
		List<String> allMatches = new ArrayList<String>();
		Matcher m = Pattern.compile(regex, flags).matcher(s);
		while (m.find()) {
			allMatches.add(m.group(group));
		}
		return allMatches;
	}

	public static String encodeQueryParm(String queryParam) {
		String key = substringBefore(queryParam, "=");
		String value = substringAfter(queryParam, "=");
		try {
			value = URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return key + "=" + value;
	}

//	public static void overwriteFile(String fileName, String contents) throws IOException {
//		File file = new File(fileName);
//		overwriteFile(file, contents);
//	}

//	public static void overwriteFile(File file, String contents) throws IOException {
//		FileUtils.writeStringToFile(file, contents, "UTF-8", false);
//	}
//
//	public static String toJsonPretty(Object obj) {
//		Gson gson = new GsonBuilder().setPrettyPrinting().create();
//		return gson.toJson(obj);
//	}
//
//	public static List<String> loadSearchList(String filename) {
//		List<String> lines;
//		try {
//			lines = FileUtils.readLines(new File(filename));
//			lines = lines.stream().filter(s -> isNotBlank(s) && !s.startsWith(";")).collect(toList());
//			return lines;
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
//	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}


	public static void openUrlViaBrowser(String url) {
		String s = url;
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(new URI(s));
			} catch (Exception e) {
				log.error("Error on opening browser, address: " + s + ": " + e.getMessage(), e);
			}
		} else {
			log.error(("Launch browser failed, please manually visit: " + s));
		}
	}

	public static void copyToClipboard(String str) {
		Clipboard clipboard = Clipboard.getSystemClipboard();
		final ClipboardContent content = new ClipboardContent();
		content.putString(str);
		clipboard.setContent(content);
	}

	public static void openWebsite() {
		openUrlViaBrowser("https://wraeclast-online.github.io/");
	}
}
