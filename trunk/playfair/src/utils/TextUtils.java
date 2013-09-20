package utils;

import java.util.ArrayList;
import java.util.List;

public class TextUtils {

	public static String prepareKey (String key)
	{
		StringBuilder sb = new StringBuilder();
		key=key.toUpperCase();
		for (char c : key.toCharArray())
		{
			if (Character.isUpperCase(c))
			{
				sb.append(c);
			}
		}
		return sb.toString();
	}
	public static String prepareText(String s)
	{
		StringBuilder sb = new StringBuilder();
		s=s.toUpperCase();
		char previous=' ';
		boolean pair=false;
		for (char c : s.toCharArray())
		{
			if (Character.isUpperCase(c))
			{
				if (c==PlayfairConstants.EQUAL_CHAR2) c=PlayfairConstants.EQUAL_CHAR1;
				if (c==previous && pair)sb.append(PlayfairConstants.INSERT_BETWEEN_SAME);
				else pair=!pair;
				sb.append(c);
				previous=c;
			}
		}
		if (sb.length()%2!=0) sb.append(PlayfairConstants.INSERT_BETWEEN_SAME);
		return sb.toString();
	}
	public static List<String> getDigraphs(String s)
	{
		List<String> toReturn = new ArrayList<String>();
		s=prepareText(s);
		for (int i=0;i<s.length();i+=2) toReturn.add(s.substring(i, i+2));
		
		return toReturn;
	}
	public static void printPairs(String s)
	{
		if (s.length()%2!=0) throw new IllegalStateException("Length of string needs to be even");
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<s.length();i+=2) sb.append(s.substring(i, i+2)+" ");
		System.out.println(sb.toString());
	}
}
