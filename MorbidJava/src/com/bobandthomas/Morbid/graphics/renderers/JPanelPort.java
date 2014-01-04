package com.bobandthomas.Morbid.graphics.renderers;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

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
			public void update (Graphics g)
			{

			    // draw image on the screen
			    g.drawImage (buffer, 0, 0, this);

			} 
		};
		panel.addComponentListener(this);	
		/* start with a generic size */
		buffer = new BufferedImage(320,240,BufferedImage.TYPE_INT_RGB);  
		
		currentColor = StaticColorQuad.White.cq();
		backgroundColor = StaticColorQuad.Black.cq();
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
		// TODO Auto-generated method stub

	}

	@Override
	void Initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Clear() {
	      Graphics2D g = buffer.createGraphics();
//	        g.setRenderingHints(renderingHints);
	        g.setColor(backgroundColor.get());
	        g.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
	        g.dispose();

	}

	@Override
	public void SwapBuffers() {
		// TODO Auto-generated method stub

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
		buffer.setRGB((int) p.x, (int) p.y, currentColor.getJColor().getRGB());		
	}

	@Override
	public void DrawPoint(Point3D p, ColorQuad cq) {
		buffer.setRGB((int) p.x, (int) p.y, cq.getJColor().getRGB());

	}

	@Override
	public ColorQuad GetPoint(Point3D p) {
		// TODO Auto-generated method stub
		return null;
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
		screenBounds.setMin(new Point3D(0, 0, 0));
		screenBounds.setMax(new Point3D(dim.width, dim.height, 0));
		buffer = new BufferedImage(dim.width,dim.height,BufferedImage.TYPE_INT_RGB);  
		graphics = (Graphics2D) buffer.getGraphics();
		notifyChange(new PortChangeEvent(this));
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
