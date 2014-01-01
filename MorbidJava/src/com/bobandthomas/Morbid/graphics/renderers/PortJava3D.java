package com.bobandthomas.Morbid.graphics.renderers;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import com.bobandthomas.Morbid.utils.BoundingBox;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.Point3DList;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.Canvas3D;
public class PortJava3D extends Port implements ComponentListener{
	public Canvas3D canvas;
	SimpleUniverse universe;
	RendererJava3D renderer;
	
	
	public PortJava3D()
	{
		super();
		canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration())  {
	        private static final long serialVersionUID = 7144426579917281131L;

	        public void postRender()
	        {
	        	if (renderer!= null)
	        		renderer.postRender(getGraphics2D());
	        }
	    };
		universe = new SimpleUniverse(canvas);
		canvas.addComponentListener(this);
	}

	public void setRenderer(RendererJava3D rend)
	{
		this.renderer = rend;
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
	public void Clear() {
		

	}

	@Override
	public void SwapBuffers() {
		

	}

	@Override
	public void BackgroundColor(ColorQuad cq) {
		

	}

	@Override
	public void FillColor(ColorQuad cq) {
		

	}

	@Override
	public void FrameColor(ColorQuad cq) {
		

	}

	@Override
	public void TextColor(ColorQuad cq) {
		

	}

	@Override
	public void MoveTo(Point3D p) {
		

	}

	@Override
	public void LineTo(Point3D p) {
		

	}

	@Override
	public void Vector(Point3D p1, Point3D p2) {
		

	}

	@Override
	public void Circle(Point3D c, Point3D r) {
		

	}

	@Override
	public void DrawPoint(Point3D p) {
		

	}

	@Override
	public void DrawPoint(Point3D p, ColorQuad cq) {
		

	}

	@Override
	public ColorQuad GetPoint(Point3D p) {
		
		return null;
	}

	@Override
	public void Text(Point3D p, String string) {
		

	}

	@Override
	public void Polygon(Point3DList plist) {
		

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
		BoundingBox box = new BoundingBox(0, 0, size.width, size.height);
		this.SetScreenBounds(box);
	}


	@Override
	public void componentShown(ComponentEvent arg0) {
		
		
	}

	@Override
	public boolean isCapableOf(PortCapabilities cap) {
		if (cap == PortCapabilities.THREE_D)
			return true;
		return false;
	}

}
