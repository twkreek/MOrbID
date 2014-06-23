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

import com.bobandthomas.Morbid.graphics.ArrowGob;
import com.bobandthomas.Morbid.graphics.CTM;
import com.bobandthomas.Morbid.graphics.CircleGob;
import com.bobandthomas.Morbid.graphics.CylinderGob;
import com.bobandthomas.Morbid.graphics.GobIndexed;
import com.bobandthomas.Morbid.graphics.GobList;
import com.bobandthomas.Morbid.graphics.GobListSet;
import com.bobandthomas.Morbid.graphics.GobPoly;
import com.bobandthomas.Morbid.graphics.GobPoly.GobPolyType;
import com.bobandthomas.Morbid.graphics.GobVector;
import com.bobandthomas.Morbid.graphics.LabelGob;
import com.bobandthomas.Morbid.graphics.LabeledCircleGob;
import com.bobandthomas.Morbid.graphics.LightSourceList;
import com.bobandthomas.Morbid.graphics.SphereGob;
import com.bobandthomas.Morbid.graphics.StringGob;
import com.bobandthomas.Morbid.graphics.Vertex;
import com.bobandthomas.Morbid.graphics.VertexList;
import com.bobandthomas.Morbid.graphics.renderers.Renderer;
import com.bobandthomas.Morbid.utils.BoundingBox;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.Point3DList;
import com.bobandthomas.Morbid.utils.StaticColorQuad;
import com.bobandthomas.Morbid.utils.Vector3D;

// TODO: Auto-generated Javadoc
/**
 * The Class ZRender.
 * 
 * @author Thomas Kreek
 */
public class ZRender extends Renderer {

	/**
	 * The Enum ZBShadeType.
	 * 
	 * @author Thomas Kreek
	 */
	public enum ZBShadeType
	{
		
		/** The zb flat. */
		ZB_FLAT,
		
		/** The zb gouraud. */
		ZB_GOURAUD,
		
		/** The zb phong. */
		ZB_PHONG
	};

	/**
	 * The Class ZBuffer.
	 * 
	 * @author Thomas Kreek
	 */
	public  class ZBuffer
	{
		
		/** The x wid. */
		long xWid;
		
		/** The y hei. */
		long yHei;
		
		/** The max size. */
		int maxSize;
		
		/** The z. */
		int z[];
		
		/** The Z buffer offset. */
		double ZBufferOffset = -1;

		/**
		 * Instantiates a new z buffer.
		 */
		ZBuffer()
		{
			z = null;
			yHei = xWid = 0;
		}

		/**
		 * Clear.
		 */
		void Clear()
		{					
			if (z == null)
				return;
			for (int i = 0; i < z.length; i++)
				z[i] = 0;
		}

		/**
		 * Resize.
		 * 
		 * @param x
		 *            the x
		 * @param y
		 *            the y
		 */
		void Resize(int x, int y)
		{

			xWid = x;
			yHei = y;
			maxSize = x * y;
			z = new int[maxSize];

			Clear();
		}

		/**
		 * Z write.
		 * 
		 * @param p
		 *            the p
		 * @return true, if successful
		 */
		boolean ZWrite(Point3D p)
		{
			if (z==null)
				return false;
			if (p.x < 0 || p.y < 0)
				return false;
			if (p.x >= xWid || p.y >= yHei)
				return false;
			int index = (int ) ((yHei * p.x) + p.y);
			if (index >= maxSize)
				return false;
			double fz = p.z + ZBufferOffset;
			if (fz < 0.0f)
			{
				return true;
			}
			if (fz > 32000)
			{
				return true;
			}
			int newZ = (int) (p.z + ZBufferOffset);
			if ( newZ > z[index])
			{
				z[index] = newZ;
				return true;
			}
			else
				return false;
		}

		/**
		 * Z read.
		 * 
		 * @param p
		 *            the p
		 * @return the double
		 */
		double  ZRead(Point3D p)
		{
			if (z == null) 
				return 0;
			if (p.x < 0 || p.y < 0)
				return 0;
			int index = (int) ((yHei * p.x) + p.y);
			if (index >= maxSize)
				return 0;
			return (double) (z[index] - ZBufferOffset);	
		}
	};


	/** The m_n shade type. */
	ZBShadeType m_nShadeType;
	
	/** The z. */
	ZBuffer z;

	/*
		void FilledCirclePoints(Point3D p, Point3D center, double r, ColorQuad color);
		void SpherePoints(Point3D p, Point3D center, double r);
		void SphereScanLine(Point3D p, Point3D center, double r);
		//void DrawCylinderLine(Point3D start1, Point3D end1, Point3D center, double rad);
		void DrawPolygon(int sides, Point3DList p, Point3DList n, ColorQuadList cq, boolean smooth, boolean hasColors);
		void DrawPixel(Point3D p, ColorQuad c);	// These are in image precision and handle zbuffering if appropriate. 
		void DrawPixel(Point3D p, Point3D normal); // Normal and point are used for lighting calculation on current material
	 */


	/** The Shade type. */
	ZBShadeType ShadeType;


	/**
	 * Instantiates a new z render.
	 */
	public ZRender()
	{
		lm.setDoLighting(true);
		lm.setDoScatterAlpha(true);
		lm.setDoSpecularity(true);
		lm.setDoDiffuse(true);
		m_nShadeType = ZBShadeType.ZB_PHONG;
		z = new ZBuffer();
	}



	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Renderer#DoRender(com.bobandthomas.Morbid.graphics.GobListSet, com.bobandthomas.Morbid.graphics.LightSourceList, com.bobandthomas.Morbid.graphics.CTM)
	 */
	public void DoRender(GobListSet goblist, LightSourceList lights, CTM newviewCTM)
	{
		if (port == null)
			return;
		currentLights = lights;
		viewCTM = new CTM(newviewCTM);
		lm.setBoundingBox(port.GetScreenBounds());

		port.BackgroundColor(bgColor);
		port.Clear();
		z.Clear();
		for (GobList gl : goblist)
			Dispatch(gl);
	}
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Renderer#Resize()
	 */
	@Override
	public void Resize()
	{   
		super.Resize();
		Scale.set(portBox.getWidth(), portBox.getHeight(), 1);
		z.Resize((int) portBox.getWidth(), (int) portBox.getHeight());
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Renderer#SetPort(com.bobandthomas.Morbid.graphics.renderers.Port)
	 */
	public void SetPort(Port p)
	{
		super.SetPort(p);
	}

	/**
	 * Draw pixel.
	 * 
	 * @param p
	 *            the p
	 * @param normal
	 *            the normal
	 */
	void DrawPixel(Point3D p, Point3D normal)
	{
		ColorQuad cq = StaticColorQuad.Black.cq();
		if (currentMaterial == null)
			return;


		if (!z.ZWrite(p))
			return;


		cq = lm.LightPoint(currentMaterial, currentLights, p, normal);
		if (lm.isDoScatterAlpha() && currentMaterial.isUseFilter())
		{
			ColorQuad cq2 = port.GetPoint(p);
			cq = cq.BlendRGB(cq2, currentMaterial.getAlpha());
		}
		port.DrawPoint(p, cq);
	}

	/**
	 * Draw pixel.
	 * 
	 * @param p
	 *            the p
	 */
	void DrawPixel(Vertex p)
	{
		if (!z.ZWrite(p))
			return;
		ColorQuad c = lm.DepthCue(p, p.getColor());

		if (lm.isDoScatterAlpha() && currentMaterial.isUseFilter())
		{
			c = c.BlendRGB(port.GetPoint(p), currentMaterial.getAlpha());
		}
		port.DrawPoint(p,c);
	}

	/**
	 * Draw pixel.
	 * 
	 * @param p
	 *            the p
	 * @param c
	 *            the c
	 */
	void DrawPixel(Point3D p, ColorQuad c)
	{
		if (!z.ZWrite(p))
			return;
		c = lm.DepthCue(p, c);

		if (lm.isDoScatterAlpha() && currentMaterial.isUseFilter())
		{
			c = c.BlendRGB(port.GetPoint(p), currentMaterial.getAlpha());
		}
		port.DrawPoint(p,c);
	}


	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Renderer#Vector(com.bobandthomas.Morbid.graphics.GobVector)
	 */
	public void Vector(GobVector g)
	{
		Point3D start1, end1;
		boolean blend = false;
		start1 = ctm.XForm(g.getPoint());
		end1 = ctm.XForm(g.getEndPoint());
		ColorQuad startColor, endColor;
		endColor = startColor = g.getColor();
		if (!(g.EndColor == endColor) )
		{
			endColor = g.EndColor;
			blend = m_nShadeType != ZBShadeType.ZB_FLAT;
		}
		if (lm.isDoDepthCue() && m_nShadeType != ZBShadeType.ZB_FLAT)
		{
			startColor = lm.DepthCue(start1, startColor);
			endColor = lm.DepthCue(end1, endColor);
			blend = true;
		}
		int d, ax, ay, az, sx, sy, sz;
		Point3D dp = end1.Sub(start1);
		ax = (int) (Math.abs(dp.x) *3);
		ay = (int) (Math.abs(dp.y) *3);
		az = (int) (Math.abs(dp.z) *3);
		sx = (int) (dp.x / Math.abs(dp.x));
		sy = (int) (dp.y / Math.abs(dp.y));
		sz = (int) (dp.z / Math.abs(dp.z));
		if (sx == 0 && sy == 0) return;
		Point3D p = start1;
		if (ax > ay)
		{
			d = ay - (ax >> 1);
			for (;;)
			{
				double fraction = (p.x - start1.x) / dp.x;
				p.z = fraction * dp.z + start1.z; // was  = 0;
				// System.Console.WriteLine(end1.ToString() + p.ToString());
				if (blend)
					DrawPixel(p, endColor.BlendHSV(startColor, fraction)); 
				else
					DrawPixel(p, startColor);
				if ((int) p.x == (int) end1.x) return;
				if (d >=0)
				{
					p.y += sy;
					d -= ax;
				}
				p.x += sx;
				d += ay;
			} 
		}
		else
		{
			d = ax - (ay >> 1);
			for(;;)
			{
				double fraction = (p.y - start1.y) / dp.y;
				p.z = fraction * dp.z + start1.z; // was = 0;
				if (blend)
					DrawPixel(p, startColor.BlendHSV(endColor, fraction)); 
				else
					DrawPixel(p, startColor);
				if ((int) p.y == (int) end1.y) return;
				if ( d >= 0)
				{
					p.x += sx;
					d -= ay;
				}
				p.y += sy;
				d += ax;
			} 
		}
	}
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Renderer#Arrow(com.bobandthomas.Morbid.graphics.ArrowGob)
	 */
	public void Arrow(ArrowGob g)
	{
		Vector(g);
		Point3D p2 = g.getEndPoint();
		Point3D p1 = g.getPoint();
		double wingSize = 0.05f;
		Point3D outZ = new Point3D(p2);
		outZ.z += 1.0;
		Vector3D outVector = (p2.Sub(p1)).Cross(p2.Sub(outZ));
		// Cross product gives vector 
		// in the plane perp to arrow line at p2;
		Vector3D inLine = p2.Sub(p1);
		inLine.Normalize();
		outVector.Normalize();

		Point3D wingTip = p2.Sub( (inLine.Scale(wingSize).Add(outVector.Scale(wingSize))));
		GobVector vg = new GobVector(p2, wingTip);
		vg.setColor(g.EndColor);
		Vector (vg);
		vg.setEndPoint(new Vertex(p2.Sub((inLine.Scale(wingSize).Sub(outVector.Scale(wingSize))))));
		Vector(vg);
	}


	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Renderer#String(com.bobandthomas.Morbid.graphics.StringGob)
	 */
	public void String(StringGob sg)
	{

	}
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Renderer#Label(com.bobandthomas.Morbid.graphics.LabelGob)
	 */
	public void Label(LabelGob lg){}
	
	/** The first. */
	static boolean first = true;
	
	/** The zl. */
	static double[]zl = new double[101];
	
	/**
	 * Sphere scan line.
	 * 
	 * @param p
	 *            the p
	 * @param center
	 *            the center
	 * @param r
	 *            the r
	 */
	void SphereScanLine(Point3D p, Point3D center, double r)
	{
		if (first)
		{
			for (int j = 0; j < 100; j++)
			{
				zl[j] =  Math.sqrt(j/100.0);
			}
			first = false;
		}
		int xLeft = (int) (center.x - p.x);
		int xCenter = (int) center.x;
		int xRight = (int) (center.x + p.x);

		for (int i = xLeft; i < xRight; i++)
		{
			double z;

			Point3D b = center.Add(p.getVector());
			b.x = (double)i;

			Point3D n = (b.Sub(center.getVector())).Scale(1/r);
			double nz = n.x * n.x + n.y * n.y;
			if (nz > 1.0)
				z = n.z = 1.0;
			else
				z = n.z = zl[(int)((1.0 - nz) * 100)];
			z = z * r + center.z;
			b.z = z;
			DrawPixel(b, n);
			b.y = center.y - p.y;
			n.y = -n.y;
			DrawPixel(b, n);
		}
	}
	
	/**
	 * Sphere points.
	 * 
	 * @param p
	 *            the p
	 * @param center
	 *            the center
	 * @param r
	 *            the r
	 */
	void SpherePoints(Point3D p, Point3D center, double r)
	{
		// scanline from x left to x center, then from x center to x right

		SphereScanLine(p, center, r);

		Point3D pTransposed = new Point3D(p.y, p.x, p.z);
		SphereScanLine(pTransposed, center, r);
	}

	/**
	 * Filled circle points.
	 * 
	 * @param p
	 *            the p
	 * @param center
	 *            the center
	 * @param r
	 *            the r
	 * @param color
	 *            the color
	 */
	void FilledCirclePoints(Point3D p, Point3D center, double r, ColorQuad color)
	{
		double z = center.z;
		//Point3D a,b,c,d,e,f,g,h, pT, n;
		int i;
		Point3D a = p.Add(center.getVector());
		Point3D b = a;
		b.x = center.x - p.x;

		Point3D c = p.Add(center.getVector());
		c.y = center.y - p.y;

		for (i = (int) b.x; i < a.x; i++)
		{
			b.x = (double) i;
			DrawPixel(b, color);
			c.x = (double) i;
			DrawPixel(c, color);
		}

		Point3D pT = p;
		Point3D e = center.Add(pT.getVector());
		Point3D f = e;
		f.x = center.x - pT.x;

		Point3D g = center.Add(pT.getVector());
		g.y = center.y - pT.y;

		for (i = (int) f.x; i < e.x; i++)
		{
			f.x = (double) i;
			DrawPixel(f, color);
			g.x = (double) i;
			DrawPixel(g, color);
		}

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Renderer#Circle(com.bobandthomas.Morbid.graphics.CircleGob)
	 */
	public void Circle(CircleGob g)
	{

		Point3D pos = ctm.XForm(g.getPoint());
		Point3D rad = Scale.Scale(g.r);
		int x,y,d;
		x = 0;
		y = (int) rad.x;
		d = (int) (3 - 2 * rad.x);
		while (x < y)
		{
			FilledCirclePoints(new Point3D(x, y, 0), pos, rad.x, g.getColor());
			if (d < 0)
				d = d + 4 * x + 6;
			else
			{
				d = d + 4 * (x - y) + 10;
				y--;
			}
			x++;
		}
		if (x == y)
			FilledCirclePoints(new Point3D(x, y, 0), pos, rad.x, g.getColor());
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Renderer#LabeledCircle(com.bobandthomas.Morbid.graphics.LabeledCircleGob)
	 */
	public void LabeledCircle(LabeledCircleGob g)
	{
		Circle(g);
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Renderer#Poly(com.bobandthomas.Morbid.graphics.GobPoly)
	 */
	public void Poly(GobPoly pvg)
	{
		Point3D from = new Point3D();
		int i;
		GobVector vg = new GobVector(from, from);
		ArrowGob ag = new ArrowGob(1, from, from);

		statMat.setColor(pvg.getColor());
		vg.setColor(pvg.getColor());
		switch(pvg.GetPolyType())
		{
		case Lines:
			for (i= 0; i < pvg.size(); i+=2) 
			{	
				vg = new GobVector(pvg.get(i), pvg.get(i+1));
				Vector(vg);
			}
			break;
		case Arrows:
			for (i= 0; i < pvg.size(); i+=2)
			{	
				ag = new ArrowGob(pvg.get(i), pvg.get(i+1));
				Arrow(ag);
			}
			break;
		case Connected:
		case Closed:
			for (i= 1; i < pvg.size(); i++)
			{
				vg = new GobVector(pvg.get(i-1), pvg.get(i));
				Vector(vg);
			}
			if (pvg.GetPolyType() == GobPolyType.Closed)
			{
				vg = new GobVector(pvg.get(i-1), pvg.get(0));
				Vector(vg);
			}
			break;

		case TMeshStrip:
		{

			for (i = 2; i < pvg.size(); i++)
			{
				
				VertexList vl = new VertexList();
				vl.add(ctm.XForm(pvg.get(i-2)));
				vl.add(ctm.XForm(pvg.get(i-1)));
				vl.add(ctm.XForm(pvg.get(i)));
				DrawPolygon(3, vl, true, true);
			}
		}
		break;
		case TMeshFan:
		{
			VertexList n = new VertexList();
			n.add(new Vertex(new Point3D(1,0,0)));
			n.add(new Vertex());
			n.add(new Vertex());
			int size = pvg.size();
			int nextIndex;

			for (i = 1; i <= size; i++)
			{
				n.set(1, n.get(2));
				if (i == size)
					nextIndex = 1;
				else
					nextIndex = i;
				n.set(2,ctm.XForm(pvg.get(nextIndex)));

				if (i >= 2)
					DrawPolygon(3, n, true, true);
			}
		}
		break;
		case Polygon:
		{
		   /*  not implemented
			int size = pvg.points.Count;
			Point3DList n = new Point3DList(size);
			Point3DList t = new Point3DList(size);
			ColorQuadList cq = new ColorQuadList(size);

			for (i = 0; i < size; i++)
			{
				t[i] = ctm.XForm(pvg.points[i]);
				if (pvg.hasNormals)
					n[i] = viewCTM.XForm(pvg.n[i]);
				if (pvg.hasColors)
					cq[i] = pvg.colors[i];
			}
			DrawPolygon(size, t, n, cq, pvg.hasNormals, pvg.hasColors);
			*/
		}
		
		break;

		case Points:
			for (i= 0; i < pvg.size(); i++)
			{
				Vertex p = ctm.XForm(pvg.get(i));
				DrawPixel(p);
			}
		default:
			break;
		}

	}
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Renderer#Indexed(com.bobandthomas.Morbid.graphics.GobIndexed)
	 */
	public void Indexed(GobIndexed g){}
	
	/**
	 * Move scale.
	 * 
	 * @param in
	 *            the in
	 * @param vector
	 *            the vector
	 * @param scale
	 *            the scale
	 * @return the vertex
	 */
	private Vertex moveScale (Vertex in, Vector3D vector, double scale)
	{
		Vertex v = new Vertex(in);
		v.setPoint(in.Add(vector).Scale(scale));
		return v;
	}
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Renderer#Cylinder(com.bobandthomas.Morbid.graphics.CylinderGob)
	 */
	public void Cylinder(CylinderGob g)
	{
		Point3D newEnd = ctm.XForm(g.getEndPoint());
		Point3D newStart = ctm.XForm(g.getPoint());
		if (newEnd.z < newStart.z)
		{
			Point3D temp = newEnd;
			newEnd = newStart;
			newStart = temp;
		}
		Vector3D bondVector = newEnd.Sub(newStart);
		Vector3D outVector = new Vector3D(0.0f, 0.0f, 1.0f);
		Vector3D crossVector = bondVector.Cross(outVector);
		outVector.Normalize();
		crossVector.Normalize();
		double  scaledR = g.getRadius() * Scale.x * zoom;
		VertexList v = new VertexList();
		for (int i = 0; i < 4; i++)
		{
			v.add(new Vertex());
		}
		v.set(0, moveScale(new Vertex(newStart, g.getColor(), crossVector), crossVector, scaledR));
		v.set(1, moveScale(new Vertex(newStart, g.getColor(), outVector.Scale(0.5)), crossVector.invert(), scaledR));
		v.set(2, moveScale(new Vertex(newEnd, g.getColor(), outVector.Scale(0.5)), crossVector, scaledR));
		v.set(3, moveScale(new Vertex(newEnd, g.getColor(), crossVector), crossVector, scaledR));
		DrawPolygon(4, v, true, true );

		v.set(0, moveScale(new Vertex(newStart, g.getColor(), crossVector.invert()), crossVector.invert(), scaledR));
		v.set(0, moveScale(new Vertex(newEnd, g.getColor(), crossVector.invert()), crossVector.invert(), scaledR));
		DrawPolygon(4, v, true, true);
		return;		 
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.renderers.Renderer#Sphere(com.bobandthomas.Morbid.graphics.SphereGob)
	 */
	public void Sphere(SphereGob g)
	{
		Point3D pos = ctm.XForm(g.getPoint());
		Point3D rad = Scale.Scale(g.getRadius() * zoom);
		/*	Point3D point = pos-rad;
		if (point.x > portBox.Max.x || point.y > portBox.Max.y)
			return;
		point = pos + rad;
		if (point.x < portBox.Min.x || point.y < portBox.Min.y)
			return;
		 */
		int x,y,d;
		x = 0;
		y = (int) rad.x;
		d = 3 - 2 * (int) rad.x;
		while (x < y)
		{
			SpherePoints(new Point3D(x, y, 0), pos, rad.x);
			if (d < 0)
				d = d + 4 * x + 6;
			else
			{
				d = d + 4 * (x - y) + 10;
				y--;
			}
			x++;
		}
		if (x == y)
			SpherePoints(new Point3D(x, y, 0), pos, rad.x);
	}
	//#define INDEX(i) (((i) < sides - 1) ? ((i) + 1) : 0)
	/**
	 * Index.
	 * 
	 * @param i
	 *            the i
	 * @param sides
	 *            the sides
	 * @return the int
	 */
	private int INDEX(int i, int sides)
	{
		return (((i) < sides - 1) ? ((i) + 1) : 0);
	}
	
	/**
	 * Draw polygon.
	 * 
	 * @param sides
	 *            the sides
	 * @param vertices
	 *            the vertices
	 * @param smooth
	 *            the smooth
	 * @param useColors
	 *            the use colors
	 */
	void DrawPolygon(int sides, VertexList vertices, boolean smooth, boolean useColors)
	{
		int PolygonSizeLimit = 20;
		int i,j;
		int xline = 0;
		BoundingBox bounds = new BoundingBox();

		Point3DList intersectList = new Point3DList();	
		int	vecYMin[] = new int[PolygonSizeLimit];
		int vecYMax[] = new int[PolygonSizeLimit];
		boolean degenerate[] = new boolean[PolygonSizeLimit];
		VertexList pn;
		VertexList set = new VertexList();

		double  fraction, diff;
		double  Z, dZ;
		Point3D mV;
		Vector3D N, dN;
		Vector3D norm= new Vector3D(0,0,0);
		ColorQuad meanColor;
		if (!smooth)
		{

			Vector3D a = vertices.get(0).Sub(vertices.get(1));
			Vector3D b = vertices.get(2).Sub(vertices.get(1));

			norm = a.Cross(b);
			norm.Normalize();
		}
		else
		{
			for (i= 0; i < sides; i++)
				norm = norm.Add(vertices.get(i).getNormal());;
			norm = norm.Scale(1.0f * sides);
		}

		meanColor = statMat.getColor();
		if (useColors && m_nShadeType == ZBShadeType.ZB_FLAT)
		{
			ColorQuad fc = StaticColorQuad.Black.cq();
			for (i=0; i < sides; i++)
				fc.add(vertices.get(i).getColor());
			fc.scale(1.0f/sides);
			meanColor = fc;
		}
		if (m_nShadeType == ZBShadeType.ZB_FLAT)
		{
			statMat.setColor(meanColor);
			meanColor = lm.LightPoint(currentMaterial, currentLights, vertices.get(0) ,norm);
		}

		for (i = 0; i < sides; i++)
		{
			switch (m_nShadeType)
			{
			case ZB_FLAT:
				vertices.get(i).setNormal(norm);
				vertices.get(i).setColor(meanColor);;
				break;
			case ZB_GOURAUD:
				if (useColors)
					statMat.setColor(vertices.get(i).getColor());
				vertices.get(i).setColor(lm.LightPoint(currentMaterial, currentLights, vertices.get(i), vertices.get(i).getNormal()));
				break;
			case ZB_PHONG:
					vertices.get(i).setColor(statMat.getColor());;
			}
			set.add(vertices.get(i));
		}
		if (smooth)
			smooth = lm.isDoLighting() && m_nShadeType == ZBShadeType.ZB_PHONG;
		for (i=0; i < sides; i++)
		{
			Vertex p1 = vertices.get(i);

			bounds.addPoint(p1);
			// Bounding box

			// find y range for sides up to last side
			vecYMin[i] = Math.min(p1.iY(), vertices.get(INDEX(i, sides)).iY());
			vecYMax[i] = Math.max(p1.iY(), vertices.get(INDEX(i, sides)).iY());
			degenerate[i] = (p1.iY() == vertices.get(INDEX(i,sides)).iY());

		}
		// if out of screen bounds, don't bother
		if (bounds.min.x < 0 || bounds.min.y < 0)
			return;

		for (j = 0; j < sides; j++) // do the degenerate ones that are parallel to the scan line
		{
			if (!degenerate[j])
				continue;
			fraction = j * 1.0f / sides;
			i = INDEX(j, sides);

			Vertex pnc1;
			Vertex pnc2;
			if (set.get(j).x < set.get(i).x)
			{
				pnc1 = set.get(j);
				pnc2 = set.get(i);
			}
			else
			{
				pnc1 = set.get(i);
				pnc2 = set.get(j);
			}


			diff = pnc2.x - pnc1.x;
			dZ = (pnc2.z - pnc1.z)/diff;
			dN = new Vector3D();
			if (m_nShadeType == ZBShadeType.ZB_PHONG)
				dN = (pnc2.getNormal().Sub(pnc1.getNormal())).Scale(1/diff).getVector();
			N = pnc1.getNormal();
			Point3D theP = new Point3D((double) xline, pnc1.y, pnc1.z);
			switch (m_nShadeType)
			{
			case ZB_FLAT:
				for (xline = pnc1.iX(); xline < pnc2.iX(); xline++)
				{
					theP.x = (double) xline;
					DrawPixel(theP, meanColor);
					theP.z += dZ;
				}
				break;
			case ZB_GOURAUD:
				for (xline = pnc1.iX(); xline < pnc2.iX(); xline++)
				{
					theP.x = (double) xline;
					DrawPixel(theP, pnc1.getColor().BlendHSV(pnc2.getColor(), 1.0f - fraction));
					theP.z += dZ;
				}
				break;
			case ZB_PHONG:
				for (xline = pnc1.iX(); xline < pnc2.iX(); xline++)
				{
					theP.x = (double) xline;
					statMat.setColor(pnc1.getColor().BlendHSV(pnc2.getColor(), 1.0f - fraction));
					DrawPixel(theP, N);
					N = N.Add(dN);
					theP.z += dZ;
				}
			}

		}
		for (i = bounds.min.iY(); i < bounds.max.iY(); i++) // do all the other ones.
		{
			pn = new VertexList();
			int xCount = 0;
			for (j = 0; j < sides; j++)
			{
				if (degenerate[j])
					continue;
				if ((i >= vecYMin[j]) && (i < vecYMax[j])) // pick ones that cross the scan line
				{
					Vertex pnc1;
					Vertex pnc2;
					if (set.get(i).x > set.get(INDEX(j,sides)).x)
					{
						pnc1 = set.get(j);
						pnc2 = set.get(INDEX(j,sides));
					}
					else
					{
						pnc1 = set.get(INDEX(j,sides));
						pnc2 = set.get(j);
					}


					Vertex pnc;
					fraction = (i - pnc1.y)/(pnc2.y - pnc1.y);
					// calculate x and z points of the intersection
					if (fraction < 0.0)
						fraction = 0;
					if (fraction > 1.0)
						fraction = 1.0;
					pnc = new Vertex(pnc2);
					pnc.setPoint(pnc1.midPoint(pnc2, fraction));
					// calculate normal value at the intersection;
					if (m_nShadeType == ZBShadeType.ZB_PHONG)
						pnc.setNormal(pnc1.getNormal().Interpolate(pnc1.getNormal(), fraction));
					if (m_nShadeType != ZBShadeType.ZB_FLAT)
						pnc.setColor(pnc1.getColor().BlendHSV(pnc2.getColor(), 1.0f -fraction));
					pn.add(pnc);
					xCount++;	// stuff into the pt array
				}
				else
				{
					// out of bounds;
					int blabla = 0;
				}
			}
			if (xCount != 2)
			{
				continue; // somehow, there's a leak; only do the first fill.
			}
			if (xCount == 0)
				continue;
			int leftIndex = 0, rightIndex = 1;
			if (pn.get(0).x > pn.get(1).x)
			{
				leftIndex = 1;
				rightIndex = 0;
			}

				ColorQuad c1, c2;
				diff = (pn.get(rightIndex).x - pn.get(leftIndex).x);
				if (diff <= 1.0)
					continue;
				N = pn.get(leftIndex).getNormal();
				Z = pn.get(leftIndex).z;
				dZ = (pn.get(rightIndex).z - pn.get(leftIndex).z)/diff; // calculate slope of z along scanline
				if (m_nShadeType == ZBShadeType.ZB_PHONG)
					dN = (pn.get(rightIndex).getNormal().Interpolate(pn.get(leftIndex).getNormal(), 1/diff)); // calculate slope of Normal along scanline

				Point3D theP = new Point3D((double) xline, (double) i, pn.get(leftIndex).z);
				double x1, x2;
				x1 = pn.get(leftIndex).x;
				x2 = pn.get(rightIndex).x - 1;
				switch (m_nShadeType)
				{
				case ZB_FLAT:
					for (xline = (int) x1; xline < (int) x2; xline++)
					{
						theP.x = (double) xline;
						DrawPixel(theP, meanColor);
						theP.z += dZ;
					}
					break;
				case ZB_GOURAUD:
					c1 = pn.get(leftIndex).getColor();
					c2 = pn.get(rightIndex).getColor();
					for (xline = (int) x1; xline < (int) x2; xline++)
					{
						fraction = (xline - x1) / (diff);
						theP.x = (double) xline;
						DrawPixel(theP, c1.BlendHSV(c2, 1.0f - fraction));
						theP.z += dZ;
					}
					break;
				case ZB_PHONG:
					dN = (pn.get(rightIndex).getNormal().Interpolate(pn.get(leftIndex).getNormal(), 1/diff)); // calculate slope of Normal along scanline
					c1 = pn.get(leftIndex).getColor();
					c2 = pn.get(rightIndex).getColor();
					for (xline = (int) x1; xline < (int) x2; xline++)
					{
						fraction = (xline - x1) / (diff);
						theP.x = (double) xline;
						statMat.setColor(c1.BlendHSV(c2, 1.0f - fraction));
						DrawPixel(theP, N);
						theP.z += dZ;
						N = N.Add(dN);
					}
				}
		}
	}
}
