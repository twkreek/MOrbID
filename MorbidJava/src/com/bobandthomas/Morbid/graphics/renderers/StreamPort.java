package com.bobandthomas.Morbid.graphics.renderers;

import java.io.PrintStream;

import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.Point3DList;

public class StreamPort extends Port {
	private PrintStream outStream;

	public StreamPort( PrintStream outstream) {
		outStream = outstream;
	}
	
	PrintStream Stream() { return outStream;}

	@Override
	public boolean isCapableOf(PortCapabilities cap) {
		if (cap == PortCapabilities.SERIALIZED)
			return true;
		return false;
	}

	@Override
	void SetParent(Port p) {
		// TODO Auto-generated method stub

	}

	@Override
	void Close() {
		// TODO Auto-generated method stub

	}

	@Override
	void Open() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public void SwapBuffers() {
		// TODO Auto-generated method stub

	}

	@Override
	public void BackgroundColor(ColorQuad cq) {
		// TODO Auto-generated method stub

	}

	@Override
	public void FillColor(ColorQuad cq) {
		// TODO Auto-generated method stub

	}

	@Override
	public void FrameColor(ColorQuad cq) {
		// TODO Auto-generated method stub

	}

	@Override
	public void TextColor(ColorQuad cq) {
		// TODO Auto-generated method stub

	}

	@Override
	public void MoveTo(Point3D p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void LineTo(Point3D p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Vector(Point3D p1, Point3D p2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Circle(Point3D c, Point3D r) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Text(Point3D p, String string) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Polygon(Point3DList plist) {
		// TODO Auto-generated method stub

	}

	@Override
	public void DrawPoint(Point3D p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void DrawPoint(Point3D p, ColorQuad cq) {
		// TODO Auto-generated method stub

	}

	@Override
	public ColorQuad GetPoint(Point3D p) {
		// TODO Auto-generated method stub
		return null;
	}

}
