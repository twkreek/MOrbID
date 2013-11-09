package com.bobandthomas.Morbid.graphics.renderers;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import com.bobandthomas.Morbid.utils.BoxType;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.Point3DList;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.Canvas3D;
public class PortJava3D extends Port implements ComponentListener{
	public Canvas3D canvas;
	SimpleUniverse universe;
	
	public PortJava3D()
	{
		super();
		canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		universe = new SimpleUniverse(canvas);
		canvas.addComponentListener(this);
	}


	@Override
	void SetParent(Port p) {
		// TODO Auto-generated method stub

	}

	@Override
	void Release() {
		// TODO Auto-generated method stub

	}

	@Override
	void Initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	void Clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public void SwapBuffers() {
		// TODO Auto-generated method stub

	}

	@Override
	void BackgroundColor(ColorQuad cq) {
		// TODO Auto-generated method stub

	}

	@Override
	void FillColor(ColorQuad cq) {
		// TODO Auto-generated method stub

	}

	@Override
	void FrameColor(ColorQuad cq) {
		// TODO Auto-generated method stub

	}

	@Override
	void TextColor(ColorQuad cq) {
		// TODO Auto-generated method stub

	}

	@Override
	void MoveTo(Point3D p) {
		// TODO Auto-generated method stub

	}

	@Override
	void LineTo(Point3D p) {
		// TODO Auto-generated method stub

	}

	@Override
	void Vector(Point3D p1, Point3D p2) {
		// TODO Auto-generated method stub

	}

	@Override
	void Circle(Point3D c, Point3D r) {
		// TODO Auto-generated method stub

	}

	@Override
	void DrawPoint(Point3D p) {
		// TODO Auto-generated method stub

	}

	@Override
	void DrawPoint(Point3D p, ColorQuad cq) {
		// TODO Auto-generated method stub

	}

	@Override
	ColorQuad GetPoint(Point3D p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	void Text(Point3D p, String string) {
		// TODO Auto-generated method stub

	}

	@Override
	void Polygon(Point3DList plist) {
		// TODO Auto-generated method stub

	}


	@Override
	public void componentHidden(ComponentEvent arg0) {
		
	}


	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void componentResized(ComponentEvent arg0) {
		
		Dimension size = canvas.getSize();
		BoxType box = new BoxType(0, 0, size.width, size.height);
		this.SetScreenBounds(box);
	}


	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
