package utils;

import java.io.FileNotFoundException;
import java.util.Map;

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public Double getFrequency(String s)
	{
		if (!frequencies.containsKey(s)) throw new IllegalArgumentException("Konju");
		return frequencies.get(s);
	}
	public static void main(String[] args) {
		System.out.println(getInstance().frequencies);
	}

}
