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

public class JPanelPort extends Port implements ComponentListener {
	JPanel panel;
	BufferedImage buffer;
	ColorQuad currentColor;
	ColorQuad backgroundColor;
	Graphics2D graphics;
	

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
	void createOffscreenBuffer(int width, int height)
	{
		if (graphics != null)
			graphics.dispose();
		buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    graphics = buffer.createGraphics();
	    Clear();
 		
	}
	public JPanel getPanel()
	{
		return panel;
	}
	@Override
	public boolean isCapableOf(PortCapabilities cap) {
		if (cap.equals(PortCapabilities.BITMAP))
			return true;
		if (cap.equals(PortCapabilities.DOUBLEBUFFER))
			return true;
		return false;
	}

	@Override
	void SetParent(Port p) {
		// TODO Auto-generated method stub

	}

	@Override
	void Release() {
		graphics.dispose();
		buffer = null;
	}

	@Override
	void Initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Clear() {
//	        g.setRenderingHints(renderingHints);
	       graphics.setColor(backgroundColor.get());
	       graphics.fillRect(0, 0, panel.getWidth(), panel.getHeight());

	}

	@Override
	public void SwapBuffers() {
		this.panel.invalidate();
		this.panel.repaint();
	}

	@Override
	public void BackgroundColor(ColorQuad cq) {
		backgroundColor = cq;
	}

	@Override
	public void FillColor(ColorQuad cq) {
		currentColor = cq;
	}

	@Override
	public void FrameColor(ColorQuad cq) {
		currentColor = cq;
	}

	@Override
	public void TextColor(ColorQuad cq) {
		currentColor = cq;
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
		graphics.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);

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
		buffer.setRGB((int) p.x, (int) p.y, currentColor.getJColor().getRGB());		
	}

	@Override
	public void DrawPoint(Point3D p, ColorQuad cq) {
		buffer.setRGB((int) p.x, (int) p.y, cq.getJColor().getRGB());

	}

	@Override
	public ColorQuad GetPoint(Point3D p) {
		Color c = new Color(buffer.getRGB((int) p.x, (int)p.y));
		return new ColorQuad(c);
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

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
