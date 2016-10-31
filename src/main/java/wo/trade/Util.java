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

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBefore;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

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
}
