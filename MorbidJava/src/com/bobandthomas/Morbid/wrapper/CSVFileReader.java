/*
 * 
	MOrbID - Molecular Orbital Interactive Display

MOrbID is Copyright (c) 1996-2014 by Thomas W. Kreek


Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

 */
package com.bobandthomas.Morbid.wrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class CSVFileReader.
 * 
 * @author Thomas Kreek
 */
public class CSVFileReader extends BufferedReader {

	/** The name map. */
	HashMap<String, Integer> nameMap = new HashMap<String, Integer>();
	
	/** The fields. */
	ArrayList<String> fields;
	
	
	/**
	 * Instantiates a new CSV file reader.
	 * 
	 * @param in
	 *            the in
	 * @param hasHeaders
	 *            the has headers
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
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
	
	/**
	 * Next line.
	 * 
	 * @return true, if successful
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public boolean nextLine() throws IOException
	{
		String line = readLine();
		if (line == null)
			return false;
		fields = customSplitSpecific(line);
		return true;
	}
	
	/**
	 * Custom split specific.
	 * 
	 * @param s
	 *            the s
	 * @return the array list
	 */
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
	
	/**
	 * Gets the string.
	 * 
	 * @param name
	 *            the name
	 * @return the string
	 */
	public String getString(String name)
	{
		return fields.get(nameMap.get(name));
	}
	
	/**
	 * Gets the integer.
	 * 
	 * @param name
	 *            the name
	 * @return the integer
	 */
	public int getInteger(String name)
	{
		return Integer.parseInt(getString(name).trim());
	}
	
	/**
	 * Gets the float.
	 * 
	 * @param name
	 *            the name
	 * @return the float
	 */
	public float getFloat(String name)
	{
		return Float.parseFloat(getString(name).trim());
	}


}
