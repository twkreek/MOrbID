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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.Point3DList;
import com.bobandthomas.Morbid.utils.StaticColorQuad;

// TODO: Auto-generated Javadoc
/**
 * The Class JPanelPort.
 * 
 * @author Thomas Kreek
 */
public class JPanelPort extends Port implements ComponentListener {
	
	/** The panel. */
	JPanel panel;
	
	/** The buffer. */
	BufferedImage buffer;
	
	/** The current color. */
	ColorQuad currentColor;
	
	/** The background color. */
	ColorQuad backgroundColor;
	
	/** The graphics. */
	Graphics2D graphics;
	

	/**
	 * Instantiates a new j panel port.
	 */
	public JPanelPort() {
		panel = new JPanel()
		{
			public void paint (Graphics g)
			{
			    // copy buffered image to the screen
			    g.drawImage (buffer, 0, 0, this);
			    g.setColor(getBackground());
			    g.drawRect(10, 10, 50, 50);
			    

			} 
		};
		panel.setPreferredSize(new Dimension(100, 100));
		panel.addComponentListener(this);	
		panel.setBorder(new BevelBorder(BevelBorder.RAISED));
		panel.setVisible(true);
		screenBounds.setMin(new Point3D(0,0,0));
		screenBounds.setMax(new Point3D(100,100,100));
		
		currentColor = StaticColorQuad.White.cq();
		backgroundColor = StaticColorQuad.Magenta.cq();
		createOffscreenBuffer(100, 100);
		panel.repaint();
	}
	
	/**
	 * Creates a offscreen buffer.
	 * 
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 */
	void createOffscreenBuffer(int width, int height)
	{
		if (graphics != null)
			graphics.dispose();
		buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    graphics = buffer.createGraphics();
	    Clear();
 		
	}
	
	/**
	 * Gets the panel.
	 * 
	 * @return the panel
	 */
	public JPanel getPanel()
	{
		return panel;
	}
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#isCapableOf(com.bobandthomas.Morbid.graphics.renderers.Port.PortCapabilities)
	 */
	@Override
	public boolean isCapableOf(PortCapabilities cap) {
		if (cap.equals(PortCapabilities.BITMAP))
			return true;
		if (cap.equals(PortCapabilities.DOUBLEBUFFER))
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#SetParent(com.bobandthomas.Morbid.graphics.renderers.Port)
	 */
	@Override
	void SetParent(Port p) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#Release()
	 */
	@Override
	void Release() {
		graphics.dispose();
		buffer = null;
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#Initialize()
	 */
	@Override
	void Initialize() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#Clear()
	 */
	@Override
	public void Clear() {
//	        g.setRenderingHints(renderingHints);
	       graphics.setColor(backgroundColor.get());
	       graphics.fillRect(0, 0, panel.getWidth(), panel.getHeight());

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#SwapBuffers()
	 */
	@Override
	public void SwapBuffers() {
		this.panel.invalidate();
		this.panel.repaint();
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#BackgroundColor(com.bobandthomas.Morbid.utils.ColorQuad)
	 */
	@Override
	public void BackgroundColor(ColorQuad cq) {
		backgroundColor = cq;
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#FillColor(com.bobandthomas.Morbid.utils.ColorQuad)
	 */
	@Override
	public void FillColor(ColorQuad cq) {
		currentColor = cq;
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#FrameColor(com.bobandthomas.Morbid.utils.ColorQuad)
	 */
	@Override
	public void FrameColor(ColorQuad cq) {
		currentColor = cq;
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#TextColor(com.bobandthomas.Morbid.utils.ColorQuad)
	 */
	@Override
	public void TextColor(ColorQuad cq) {
		currentColor = cq;
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#MoveTo(com.bobandthomas.Morbid.utils.Point3D)
	 */
	@Override
	public void MoveTo(Point3D p) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#LineTo(com.bobandthomas.Morbid.utils.Point3D)
	 */
	@Override
	public void LineTo(Point3D p) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#Vector(com.bobandthomas.Morbid.utils.Point3D, com.bobandthomas.Morbid.utils.Point3D)
	 */
	@Override
	public void Vector(Point3D p1, Point3D p2) {
		graphics.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#Circle(com.bobandthomas.Morbid.utils.Point3D, com.bobandthomas.Morbid.utils.Point3D)
	 */
	@Override
	public void Circle(Point3D c, Point3D r) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#Text(com.bobandthomas.Morbid.utils.Point3D, java.lang.String)
	 */
	@Override
	public void Text(Point3D p, String string) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#Polygon(com.bobandthomas.Morbid.utils.Point3DList)
	 */
	@Override
	public void Polygon(Point3DList plist) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#DrawPoint(com.bobandthomas.Morbid.utils.Point3D)
	 */
	@Override
	public void DrawPoint(Point3D p) {
		buffer.setRGB((int) p.x, (int) p.y, currentColor.getJColor().getRGB());		
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#DrawPoint(com.bobandthomas.Morbid.utils.Point3D, com.bobandthomas.Morbid.utils.ColorQuad)
	 */
	@Override
	public void DrawPoint(Point3D p, ColorQuad cq) {
		buffer.setRGB((int) p.x, (int) p.y, cq.getJColor().getRGB());

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Port#GetPoint(com.bobandthomas.Morbid.utils.Point3D)
	 */
	@Override
	public ColorQuad GetPoint(Point3D p) {
		Color c = new Color(buffer.getRGB((int) p.x, (int)p.y));
		return new ColorQuad(c);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ComponentListener#componentHidden(java.awt.event.ComponentEvent)
	 */
	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ComponentListener#componentMoved(java.awt.event.ComponentEvent)
	 */
	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ComponentListener#componentResized(java.awt.event.ComponentEvent)
	 */
	@Override
	public void componentResized(ComponentEvent arg0) {
		Dimension dim;
		dim = panel.getSize();
		if (dim.height <= 0 || dim.width <= 0)
			return;
		screenBounds.setMin(new Point3D(0, 0, 0));
		screenBounds.setMax(new Point3D(dim.width, dim.height, 0));
		createOffscreenBuffer(dim.width, dim.height);
		this.fireEvent(new PortChangeEvent(this));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ComponentListener#componentShown(java.awt.event.ComponentEvent)
	 */
	@Override
	public void componentShown(ComponentEvent arg0) {
		Dimension dim;
		dim = panel.getSize();
		if (dim.height <= 0 || dim.width <= 0)
			return;
		screenBounds.setMin(new Point3D(0, 0, 0));
		screenBounds.setMax(new Point3D(dim.width, dim.height, 0));
		createOffscreenBuffer(dim.width, dim.height);
		this.fireEvent(new PortChangeEvent(this));
		
	}

}
