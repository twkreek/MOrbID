package com.bobandthomas.Morbid.wrapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceMgr {
	
	static ResourceMgr r = new ResourceMgr();
	ClassLoader cl;
	public static MorbidBufferedReader getResourceFile(String name)
	{
		InputStream stream = r.getClass().getClassLoader().getResourceAsStream(name);
		
		MorbidBufferedReader reader = new MorbidBufferedReader(stream);
		
		return reader;
	}

	public static CSVFileReader getResourceCSV(String name) throws IOException
	{
		InputStream stream = r.getClass().getClassLoader().getResourceAsStream(name);
		
		CSVFileReader reader = new CSVFileReader(new InputStreamReader(stream),true);
		
		return reader;
	}

	public ResourceMgr() {
		// TODO Auto-generated constructor stub
	}

}
