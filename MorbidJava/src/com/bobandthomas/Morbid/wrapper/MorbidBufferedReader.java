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
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

// TODO: Auto-generated Javadoc
/**
 * The Class MorbidBufferedReader.
 * 
 * @author Thomas Kreek
 */
public class MorbidBufferedReader {
	
	/** The binary. */
	boolean binary;
	
	/** The br. */
	BufferedReader br = null;
	
	/** The dis. */
	DataInputStream dis = null;
	
	/** The stream. */
	InputStream stream = null;


	/**
	 * Instantiates a new morbid buffered reader.
	 * 
	 * @param stream
	 *            the stream
	 */
	public MorbidBufferedReader(InputStream stream)
	{
		this.stream = stream;
	}
	
	/**
	 * Sets the reader binary.
	 * 
	 * @param binary
	 *            the new reader binary
	 */
	public void setReaderBinary(boolean binary)
	{
		
		this.binary = binary;
		if (binary)
			dis = new DataInputStream(stream);
		else
			br = new BufferedReader(new InputStreamReader(stream));
	}

	/**
	 * Read double.
	 * 
	 * @return the double
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public final double readDouble() throws IOException {
		long bits = dis.readLong();
		bits = Long.reverseBytes(bits);
		return Double.longBitsToDouble(bits);
	}

	/**
	 * Read float.
	 * 
	 * @return the float
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public final float readFloat() throws IOException {
		return Float.intBitsToFloat(dis.readInt());
	}

	/**
	 * Read line.
	 * 
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String readLine() throws IOException {
		return br.readLine();
	}

	/**
	 * Read long.
	 * 
	 * @return the long
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public final long readLong() throws IOException {
		return dis.readLong();
	}
	
	
	/**
	 * Read short.
	 * 
	 * @return the short
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public final short readShort() throws IOException {
		return dis.readShort();
	}
	
	/**
	 * Read int.
	 * 
	 * @return the int
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public final int readInt() throws IOException {
		return Integer.reverseBytes(dis.readInt());
	}
	
	/**
	 * Read bytes.
	 * 
	 * @param length
	 *            the length
	 * @return the byte[]
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public byte[] readBytes(int length) throws IOException
	{
		byte [] chars = new byte[length];
		dis.read(chars);
		return chars;
	}

	/**
	 * Close.
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void close() throws IOException
	{
		if (binary)
			dis.close();
		else
			br.close();
		
	}
	
	

}
