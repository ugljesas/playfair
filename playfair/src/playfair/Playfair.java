package playfair;

import java.util.List;

import utils.TextUtils;

public class Playfair {

	private PlayfairKey key;
	
	public Playfair (PlayfairKey key)
	{
		this.key=key;
	}
	public Playfair (String key)
	{
		this.key=new PlayfairKey(key);
	}
	
	public String encrypt(String in)
	{
		StringBuilder toReturn = new StringBuilder();
		List<String> digraphs = TextUtils.getDigraphs(in);
		for (String digraph:digraphs)
		{
			toReturn.append(key.encrypt(digraph));
		}
		return toReturn.toString();
	}
	
	public String decrypt(String in)
	{
		StringBuilder toReturn = new StringBuilder();
		List<String> digraphs = TextUtils.getDigraphs(in);
		for (String digraph:digraphs)
		{
			toReturn.append(key.decrypt(digraph));
		}
		return toReturn.toString();
	}

	public void setKey(PlayfairKey key) {
		this.key = key;
	}
}
