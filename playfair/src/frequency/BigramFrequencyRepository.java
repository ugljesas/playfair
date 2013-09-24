package frequency;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import utils.FrequencyParser;
import utils.PlayfairConstants;
import utils.TextUtils;

public class BigramFrequencyRepository implements FrequencyRepository{

	//private static final double MINIMUM_SCORE = -10;
	private static BigramFrequencyRepository instance;
	public static BigramFrequencyRepository getInstance() {
		if (instance==null) instance = new BigramFrequencyRepository();
		return instance;
	}
	private Map<String,Double> scores;
	private BigramFrequencyRepository ()
	{
		try {
			Map<String,Double> frequencies = FrequencyParser.getBigramFrequencies();
			
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
			
			scores = new HashMap<String, Double>();
			for (Map.Entry<String, Double> entry : frequencies.entrySet())
			{
				scores.put(entry.getKey(), Math.log(entry.getValue()));
			}
			System.out.println(scores);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		getInstance();
	}
	@Override
	public double getTextFitness(String text) {
		double result = 0;
		String preptext = TextUtils.prepareText(text);
		for (int i=0;i<preptext.length()-1;i++)
		{
			String bigram = preptext.substring(i, i+2);
			result += scores.get(bigram);
		}
		return result;
	}
	

}
