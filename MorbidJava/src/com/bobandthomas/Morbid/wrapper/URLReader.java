package com.bobandthomas.Morbid.wrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class URLReader {
	
	String errorStatus;
	static URLReader one = new URLReader();
	
	public static URLReader get() { return one; }
	
	public String getErrorStatus(){ return errorStatus; }

	private URLReader() {
		// TODO Auto-generated constructor stub
	}
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
