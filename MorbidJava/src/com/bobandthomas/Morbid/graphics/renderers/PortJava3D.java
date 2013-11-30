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
		

	}

	@Override
	void Release() {
		

	}

	@Override
	void Initialize() {
		

	}

	@Override
	void Clear() {
		

	}

	@Override
	public void SwapBuffers() {
		

	}

	@Override
	void BackgroundColor(ColorQuad cq) {
		

	}

	@Override
	void FillColor(ColorQuad cq) {
		

	}

	@Override
	void FrameColor(ColorQuad cq) {
		

	}

	@Override
	void TextColor(ColorQuad cq) {
		

	}

	@Override
	void MoveTo(Point3D p) {
		

	}

	@Override
	void LineTo(Point3D p) {
		

	}

	@Override
	void Vector(Point3D p1, Point3D p2) {
		

	}

	@Override
	void Circle(Point3D c, Point3D r) {
		

	}

	@Override
	void DrawPoint(Point3D p) {
		

	}

	@Override
	void DrawPoint(Point3D p, ColorQuad cq) {
		

	}

	@Override
	ColorQuad GetPoint(Point3D p) {
		
		return null;
	}

	@Override
	void Text(Point3D p, String string) {
		

	}

	@Override
	void Polygon(Point3DList plist) {
		

	}


	@Override
	public void componentHidden(ComponentEvent arg0) {
		
	}


	@Override
	public void componentMoved(ComponentEvent arg0) {
		
		
	}


	@Override
	public void componentResized(ComponentEvent arg0) {
		
		Dimension size = canvas.getSize();
		BoxType box = new BoxType(0, 0, size.width, size.height);
		this.SetScreenBounds(box);
	}


	@Override
	public void componentShown(ComponentEvent arg0) {
		
		
	}

}
