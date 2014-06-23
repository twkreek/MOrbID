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
// TODO: Auto-generated Javadoc

/**
 * The Class PortJava3D.
 * 
 * @author Thomas Kreek
 */
public class PortJava3D extends Port implements ComponentListener{
	
	/** The canvas. */
	public Canvas3D canvas;
	
	/** The universe. */
	SimpleUniverse universe;
	
	/** The renderer. */
	RendererJava3D renderer;
	
	
	/**
	 * Instantiates a new port java3 d.
	 */
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

	/**
	 * Sets the renderer.
	 * 
	 * @param rend
	 *            the new renderer
	 */
	public void setRenderer(RendererJava3D rend)
	{
		this.renderer = rend;
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#SetParent(com.bobandthomas.Morbid.graphics.renderers.Port)
	 */
	@Override
	void SetParent(Port p) {
		

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#Release()
	 */
	@Override
	void Release() {
		

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#Initialize()
	 */
	@Override
	void Initialize() {
		

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#Clear()
	 */
	@Override
	public void Clear() {
		

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#SwapBuffers()
	 */
	@Override
	public void SwapBuffers() {
		

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#BackgroundColor(com.bobandthomas.Morbid.utils.ColorQuad)
	 */
	@Override
	public void BackgroundColor(ColorQuad cq) {
		

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#FillColor(com.bobandthomas.Morbid.utils.ColorQuad)
	 */
	@Override
	public void FillColor(ColorQuad cq) {
		

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#FrameColor(com.bobandthomas.Morbid.utils.ColorQuad)
	 */
	@Override
	public void FrameColor(ColorQuad cq) {
		

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#TextColor(com.bobandthomas.Morbid.utils.ColorQuad)
	 */
	@Override
	public void TextColor(ColorQuad cq) {
		

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#MoveTo(com.bobandthomas.Morbid.utils.Point3D)
	 */
	@Override
	public void MoveTo(Point3D p) {
		

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#LineTo(com.bobandthomas.Morbid.utils.Point3D)
	 */
	@Override
	public void LineTo(Point3D p) {
		

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#Vector(com.bobandthomas.Morbid.utils.Point3D, com.bobandthomas.Morbid.utils.Point3D)
	 */
	@Override
	public void Vector(Point3D p1, Point3D p2) {
		

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#Circle(com.bobandthomas.Morbid.utils.Point3D, com.bobandthomas.Morbid.utils.Point3D)
	 */
	@Override
	public void Circle(Point3D c, Point3D r) {
		

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#DrawPoint(com.bobandthomas.Morbid.utils.Point3D)
	 */
	@Override
	public void DrawPoint(Point3D p) {
		

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#DrawPoint(com.bobandthomas.Morbid.utils.Point3D, com.bobandthomas.Morbid.utils.ColorQuad)
	 */
	@Override
	public void DrawPoint(Point3D p, ColorQuad cq) {
		

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#GetPoint(com.bobandthomas.Morbid.utils.Point3D)
	 */
	@Override
	public ColorQuad GetPoint(Point3D p) {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#Text(com.bobandthomas.Morbid.utils.Point3D, java.lang.String)
	 */
	@Override
	public void Text(Point3D p, String string) {
		

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#Polygon(com.bobandthomas.Morbid.utils.Point3DList)
	 */
	@Override
	public void Polygon(Point3DList plist) {
		

	}


	/* (non-Javadoc)
	 * @see java.awt.event.ComponentListener#componentHidden(java.awt.event.ComponentEvent)
	 */
	@Override
	public void componentHidden(ComponentEvent arg0) {
		
	}


	/* (non-Javadoc)
	 * @see java.awt.event.ComponentListener#componentMoved(java.awt.event.ComponentEvent)
	 */
	@Override
	public void componentMoved(ComponentEvent arg0) {
		
		
	}


	/* (non-Javadoc)
	 * @see java.awt.event.ComponentListener#componentResized(java.awt.event.ComponentEvent)
	 */
	@Override
	public void componentResized(ComponentEvent arg0) {
		
		Dimension size = canvas.getSize();
		BoundingBox box = new BoundingBox(0, 0, size.width, size.height);
		this.SetScreenBounds(box);
	}


	/* (non-Javadoc)
	 * @see java.awt.event.ComponentListener#componentShown(java.awt.event.ComponentEvent)
	 */
	@Override
	public void componentShown(ComponentEvent arg0) {
		
		
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#isCapableOf(com.bobandthomas.Morbid.graphics.renderers.Port.PortCapabilities)
	 */
	@Override
	public boolean isCapableOf(PortCapabilities cap) {
		if (cap == PortCapabilities.THREE_D)
			return true;
		return false;
	}

}
