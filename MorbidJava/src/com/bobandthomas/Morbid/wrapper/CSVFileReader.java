package com.bobandthomas.Morbid.wrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

public class CSVFileReader extends BufferedReader {

	HashMap<String, Integer> nameMap = new HashMap<String, Integer>();
	ArrayList<String> fields;
	
	
	public CSVFileReader(Reader in, boolean hasHeaders) throws IOException {
		super(in);
		if (hasHeaders)
		{
			String headers = readLine();
			fields = customSplitSpecific(headers);
			int index = 0;
			for (String s : fields)
			{
				
				nameMap.put(s.trim(), index);
				index++;
			}
			nextLine();
		}
	}
	public boolean nextLine() throws IOException
	{
		String line = readLine();
		if (line == null)
			return false;
		fields = customSplitSpecific(line);
		return true;
	}
	public ArrayList<String> customSplitSpecific(String s)
	{
		ArrayList<String> words = new ArrayList<String>();
	    boolean notInsideComma = true;
	    int start =0;
	    for(int i=0; i<s.length()-1; i++)
	    {
	        if(s.charAt(i)==',' && notInsideComma)
	        {
	            words.add(s.substring(start,i));
	            start = i+1;                
	        }   
	        else if(s.charAt(i)=='"')
	        notInsideComma=!notInsideComma;
	    }
	    words.add(s.substring(start));
	    return words;
	}
	
	public String getString(String name)
	{
		return fields.get(nameMap.get(name));
	}
	public int getInteger(String name)
	{
		return Integer.parseInt(getString(name).trim());
	}
	public float getFloat(String name)
	{
		return Float.parseFloat(getString(name).trim());
	}


}
