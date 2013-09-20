package playfair;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import utils.PlayfairConstants;
import utils.TextUtils;

public class PlayfairKey {

	private char matrix [];
	
	private Map<Character, Integer> charToIndex;
	
	public PlayfairKey (String key)
	{
		key=TextUtils.prepareKey(key);
		Set<Character> available = new TreeSet<Character>();
		for (char i='A';i<='Z';i++) available.add(i);
		available.remove(PlayfairConstants.EQUAL_CHAR2);
		matrix = new char [25];
		int matrixInd = 0;
		for (char c : key.toCharArray())
		{
			if (available.contains(c))
			{
				available.remove(c);
				matrix[matrixInd++]=c;
			}
		}
		for (char c:available) matrix[matrixInd++]=c;
		charToIndex = new HashMap<Character, Integer>();
		populateCharToIndexMap();
	}
	public PlayfairKey (char matrix [])
	{
		if (matrix.length != 25) throw new IllegalArgumentException("The size of the matrix must be 25");
		for (char c:matrix) if (!Character.isUpperCase(c)) throw new IllegalArgumentException("Matrix may only contain uppercase alphabetic chars and can not contain char : " + PlayfairConstants.EQUAL_CHAR2);
		this.matrix=matrix;
		charToIndex = new HashMap<Character, Integer>();
		populateCharToIndexMap();
	}
	
	private void populateCharToIndexMap()
	{
		for (int i=0;i<25;i++)charToIndex.put(matrix[i], i);
	}
	
	private int getCharToIndex(char c)
	{
		return charToIndex.get(c);
	}
	
	//ecrypts digraph
	public String encrypt (String in)
	{
		StringBuilder sb = new StringBuilder();
		if (in.length()!=2) throw new IllegalArgumentException("Input string must be of length 2");
		char ac = in.charAt(0);
		char bc = in.charAt(1);
		int a = getCharToIndex(ac);
		int b = getCharToIndex(bc);
		if ((a/5) == (b/5)) 
		{
			int y = a/5;
			int ax = a%5;
			int bx = b%5;
			ax = (ax+1)%5;
			bx = (bx+1)%5;
			sb.append(matrix[y*5 + ax]);
			sb.append(matrix[y*5 + bx]);
			return sb.toString();
		}
		if ((a%5) ==(b%5))
		{
			int x = a%5;
			int ay = a/5;
			int by = b/5;
			ay = (ay+1)%5;
			by = (by+1)%5;
			sb.append(matrix[ay*5 + x]);
			sb.append(matrix[by*5 + x]);
			return sb.toString();
		}
		int ax = a%5;
		int ay = a/5;
		int bx = b%5;
		int by = b/5;
		sb.append(matrix[ay*5 + bx]);
		sb.append(matrix[by*5 + ax]);
		return sb.toString();
	}
	
	//decrypts digraph
	public String decrypt (String in)
	{
		if (in.length()!=2) throw new IllegalArgumentException("Input string must be of length 2");
		StringBuilder sb = new StringBuilder();
		if (in.length()!=2) throw new IllegalArgumentException("Input string must be of length 2");
		char ac = in.charAt(0);
		char bc = in.charAt(1);
		int a = getCharToIndex(ac);
		int b = getCharToIndex(bc);
		if ((a/5) == (b/5)) 
		{
			int y = a/5;
			int ax = a%5;
			int bx = b%5;
			ax--;
			if (ax<0) ax+=5;
			bx--;
			if (bx<0) bx+=5;
			sb.append(matrix[y*5 + ax]);
			sb.append(matrix[y*5 + bx]);
			return sb.toString();
		}
		if ((a%5) ==(b%5))
		{
			int x = a%5;
			int ay = a/5;
			int by = b/5;
			ay--;
			if (ay<0) ay+=5;
			by--;
			if (by<0) by+=5;
			sb.append(matrix[ay*5 + x]);
			sb.append(matrix[by*5 + x]);
			return sb.toString();
		}
		int ax = a%5;
		int ay = a/5;
		int bx = b%5;
		int by = b/5;
		sb.append(matrix[ay*5 + bx]);
		sb.append(matrix[by*5 + ax]);
		return sb.toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<5;i++)
		{
			for (int j=0;j<5;j++)
			{
				sb.append(matrix[i*5+j]+" ");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("\n");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
}
