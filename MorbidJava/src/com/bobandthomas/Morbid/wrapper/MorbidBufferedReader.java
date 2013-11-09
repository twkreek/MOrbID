package com.bobandthomas.Morbid.wrapper;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MorbidBufferedReader {
	boolean binary;
	BufferedReader br = null;
	DataInputStream dis = null;
	InputStream stream = null;


	public MorbidBufferedReader(InputStream stream)
	{
		this.stream = stream;
	}
	
	public void setReaderBinary(boolean binary)
	{
		
		this.binary = binary;
		if (binary)
			dis = new DataInputStream(stream);
		else
			br = new BufferedReader(new InputStreamReader(stream));
	}

	public final double readDouble() throws IOException {
		long bits = dis.readLong();
		bits = Long.reverseBytes(bits);
		return Double.longBitsToDouble(bits);
	}

	public final float readFloat() throws IOException {
		return Float.intBitsToFloat(dis.readInt());
	}

	public String readLine() throws IOException {
		return br.readLine();
	}

	public final long readLong() throws IOException {
		return dis.readLong();
	}
	
	
	public final short readShort() throws IOException {
		return dis.readShort();
	}
	public final int readInt() throws IOException {
		return Integer.reverseBytes(dis.readInt());
	}
	public byte[] readBytes(int length) throws IOException
	{
		byte [] chars = new byte[length];
		dis.read(chars);
		return chars;
	}

	public void close() throws IOException
	{
		if (binary)
			dis.close();
		else
			br.close();
		
	}
	
	

}
