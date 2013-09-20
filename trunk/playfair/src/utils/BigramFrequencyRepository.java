package utils;

import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import playfair.Playfair;
import playfair.PlayfairKey;

public class BigramFrequencyRepository {
	private static BigramFrequencyRepository instance;
	public static BigramFrequencyRepository getInstance() {
		if (instance==null) instance = new BigramFrequencyRepository();
		return instance;
	}
	private Map<String,Double> frequencies;
	private BigramFrequencyRepository ()
	{
		try {
			frequencies = BigramHTMLParser.getFrequencies();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for (char i='A';i<='Z';i++)
		{
			if (i!=PlayfairConstants.EQUAL_CHAR2)
			{
				StringBuilder sb = new StringBuilder();
				sb.append(PlayfairConstants.EQUAL_CHAR2);
				sb.append(i);
				double toAdd = frequencies.get(sb.toString());
				frequencies.put(sb.toString(),0.0);
				sb= new StringBuilder();
				sb.append(PlayfairConstants.EQUAL_CHAR1);
				sb.append(i);
				double freq=frequencies.get(sb.toString());
				frequencies.put(sb.toString(), freq+toAdd);
				
				sb = new StringBuilder();
				sb.append(i);
				sb.append(PlayfairConstants.EQUAL_CHAR2);
				toAdd = frequencies.get(sb.toString());
				frequencies.put(sb.toString(),0.0);
				sb= new StringBuilder();
				sb.append(i);
				sb.append(PlayfairConstants.EQUAL_CHAR1);
				freq=frequencies.get(sb.toString());
				frequencies.put(sb.toString(), freq+toAdd);
			}
			else
			{
				StringBuilder sb = new StringBuilder();
				sb.append(PlayfairConstants.EQUAL_CHAR2);
				sb.append(PlayfairConstants.EQUAL_CHAR2);
				double toAdd = frequencies.get(sb.toString());
				frequencies.put(sb.toString(),0.0);
				sb= new StringBuilder();
				sb.append(PlayfairConstants.EQUAL_CHAR1);
				sb.append(PlayfairConstants.EQUAL_CHAR1);
				double freq=frequencies.get(sb.toString());
				frequencies.put(sb.toString(), freq+toAdd);
			}
				
		}
		for (char i='A';i<='Z';i++)
		{
			if (i!=PlayfairConstants.INSERT_BETWEEN_SAME)
			{
				StringBuilder sb = new StringBuilder();
				sb.append(i);
				sb.append(i);
				double toAdd=frequencies.get(sb.toString());
				
				sb= new StringBuilder();
				sb.append(i);
				sb.append(PlayfairConstants.INSERT_BETWEEN_SAME);
				double freq = frequencies.get(sb.toString());
				frequencies.put(sb.toString(), freq+toAdd);
				
				sb= new StringBuilder();
				sb.append(PlayfairConstants.INSERT_BETWEEN_SAME);
				sb.append(i);
				freq = frequencies.get(sb.toString());
				frequencies.put(sb.toString(), freq+toAdd);
			}
		}
		double sum=0;
		for (Double d: frequencies.values())sum+=d;
		for (Map.Entry<String, Double> entry:frequencies.entrySet())
		{
			entry.setValue(entry.getValue()/sum);
		}
	}
	
	public Double getFrequency(String s)
	{
		if (!frequencies.containsKey(s)) throw new IllegalArgumentException("Konju");
		return frequencies.get(s);
	}
	
	public double getTextFitness(String in)
	{
		List<String> digraphs = TextUtils.getDigraphs(in);
		Map<String,Integer> digraphCount = new HashMap<String, Integer> ();
		int total=0;
		for (String digraph : digraphs)
		{
			total++;
			if (!digraphCount.containsKey(digraph)) digraphCount.put(digraph, 1);
			else
			{
				int cc = digraphCount.get(digraph);
				digraphCount.put(digraph, cc+1);
			}
		}
		double result=0;
		for (Map.Entry<String, Double> entry : frequencies.entrySet())
		{
			double freq=0;
			if (digraphCount.containsKey(entry.getKey())) freq = digraphCount.get(entry.getKey());
			result+= entry.getValue() * freq;
		}
		return result;
	}
	public static void main(String[] args) {
		PlayfairKey key = new PlayfairKey("monarchy");
		System.out.println(key);
		Playfair playfair = new Playfair(key);
		String input = "This is playfair";
		System.out.println(BigramFrequencyRepository.getInstance().getTextFitness(input));
		TextUtils.printPairs(TextUtils.prepareText(input));
		String cipher = playfair.encrypt(input);
		TextUtils.printPairs(cipher);
		String decipher = playfair.decrypt(cipher);
		TextUtils.printPairs(decipher);
	}

}
