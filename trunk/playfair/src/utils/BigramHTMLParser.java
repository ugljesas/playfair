package utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class BigramHTMLParser {

	public static String convertStreamToString(java.io.InputStream is) {
		Scanner s = new Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
	public static Map<String,Double> getFrequencies() throws FileNotFoundException {
		Map<String,Double> frequencies = new HashMap<String, Double>();
		BufferedInputStream in = new BufferedInputStream(new FileInputStream("digrams.txt"));
		String allText = convertStreamToString(in);
		Pattern findTitles = Pattern.compile("title=\"(.*?);");
		Matcher m = findTitles.matcher(allText);
		while (m.find()) {
		    String s = m.group(1);
		    String key = s.substring(0, 2);
		    Double value = Double.parseDouble(s.substring(4, 9))/100;
		    frequencies.put(key,value);
		}
		return frequencies;
	}

}
