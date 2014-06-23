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
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

// TODO: Auto-generated Javadoc
/**
 * The Class URLReader.
 * 
 * @author Thomas Kreek
 */
public class URLReader {
	
	/** The error status. */
	String errorStatus;
	
	/** The one. */
	static URLReader one = new URLReader();
	
	/**
	 * Gets the.
	 * 
	 * @return the URL reader
	 */
	public static URLReader get() { return one; }
	
	/**
	 * Gets the error status.
	 * 
	 * @return the error status
	 */
	public String getErrorStatus(){ return errorStatus; }

	/**
	 * Instantiates a new URL reader.
	 */
	private URLReader() {
	}
	
	/**
	 * Gets the from url.
	 * 
	 * @param url
	 *            the url
	 * @return the from url
	 */
	public BufferedReader getFromURL(String url)
	{
		URL fileSource = null;
		try {
			fileSource = new URL(url);
			return getFromURL(fileSource);
		} catch (MalformedURLException e) {
			errorStatus = e.getMessage();
			e.printStackTrace();
			return null;
		}

	}
	
	/**
	 * Gets the from url.
	 * 
	 * @param fileSource
	 *            the file source
	 * @return the from url
	 */
	public BufferedReader getFromURL(URL fileSource)
	{
		BufferedReader r = null;
		try {
			URLConnection conn = fileSource.openConnection();
			InputStreamReader ir = new InputStreamReader(conn.getInputStream());
			r = new BufferedReader(ir);
			
		} catch (MalformedURLException e) {
			errorStatus = e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			errorStatus = e.getMessage();
			e.printStackTrace();
		}
		return r;
		
	}
	

}
