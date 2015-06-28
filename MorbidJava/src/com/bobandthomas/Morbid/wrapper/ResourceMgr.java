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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

// TODO: Auto-generated Javadoc
/**
 * The Class ResourceMgr.
 * 
 * @author Thomas Kreek
 */
public class ResourceMgr {
	
	/** The r. */
	static ResourceMgr r = new ResourceMgr();
	
	/** The cl. */
	ClassLoader cl;
	
	/**
	 * Gets the resource file.
	 * 
	 * @param name
	 *            the name
	 * @return the resource file
	 */
	public static MorbidBufferedReader getResourceFile(String name)
	{
		InputStream stream = r.getClass().getClassLoader().getResourceAsStream(name);
		
		MorbidBufferedReader reader = new MorbidBufferedReader(stream);
		
		return reader;
	}

	/**
	 * Gets the resource csv.
	 * 
	 * @param name
	 *            the name
	 * @return the resource csv
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static CSVFileReader getResourceCSV(String name) throws IOException
	{
		InputStream stream = r.getClass().getClassLoader().getResourceAsStream(name);
		
		CSVFileReader reader = new CSVFileReader(new InputStreamReader(stream),true);
		
		return reader;
	}

	/**
	 * Instantiates a new resource mgr.
	 */
	public ResourceMgr() {
	}

}
