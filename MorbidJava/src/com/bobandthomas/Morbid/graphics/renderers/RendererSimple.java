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

import com.bobandthomas.Morbid.Scene.LayerPosition;
import com.bobandthomas.Morbid.graphics.ArrowGob;

import java.util.Comparator;

import com.bobandthomas.Morbid.graphics.CTM;
import com.bobandthomas.Morbid.graphics.CircleGob;
import com.bobandthomas.Morbid.graphics.CylinderGob;
import com.bobandthomas.Morbid.graphics.Gob;
import com.bobandthomas.Morbid.graphics.GobIndexed;
import com.bobandthomas.Morbid.graphics.GobList;
import com.bobandthomas.Morbid.graphics.GobListSet;
import com.bobandthomas.Morbid.graphics.GobPoly;
import com.bobandthomas.Morbid.graphics.GobPoly.GobPolyType;
import com.bobandthomas.Morbid.graphics.GobVector;
import com.bobandthomas.Morbid.graphics.LabelGob;
import com.bobandthomas.Morbid.graphics.LabeledCircleGob;
import com.bobandthomas.Morbid.graphics.LightSourceList;
import com.bobandthomas.Morbid.graphics.Material;
import com.bobandthomas.Morbid.graphics.SphereGob;
import com.bobandthomas.Morbid.graphics.StringGob;
import com.bobandthomas.Morbid.graphics.renderers.Renderer;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.Point3DList;
import com.bobandthomas.Morbid.utils.StaticColorQuad;
import com.bobandthomas.Morbid.utils.Vector3D;

// TODO: Auto-generated Javadoc
/**
 * The Class RendererSimple.
 * 
 * @author Thomas Kreek
 */
public class RendererSimple extends Renderer {
		
		/** The m_b shade lines. */
		boolean m_bShadeLines;
		
		/** The m_n shade steps. */
		int m_nShadeSteps;
		
		/** The m_b split poly. */
		boolean  m_bSplitPoly;
//		PropAlias(bool, ShadeLines, m_bShadeLines);
//		PropAlias(int, ShadeSteps, m_nShadeSteps);
//		PropAlias(bool, SplitPolygons, m_bSplitPoly);
		/**
 * Instantiates a new renderer simple.
 */
RendererSimple()
		{
			lm.setDoDepthCue(true);
			lm.setDoScatterAlpha(false);
			m_nShadeSteps = 3;
			m_bShadeLines = true;
			m_bSplitPoly = false;
		}

		/**
		 * The Class depth.
		 * 
		 * @author Thomas Kreek
		 */
		class depth implements Comparator<Gob>
		{
			
			/* (non-Javadoc)
			 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
			 */
			@Override
			public int compare(Gob arg0, Gob arg1) {
				return (int) (arg0.center().z - arg1.center().z);
			}
		};

		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.graphics.renderers.Renderer#DoRender(com.bobandthomas.Morbid.graphics.GobListSet, com.bobandthomas.Morbid.graphics.LightSourceList, com.bobandthomas.Morbid.graphics.CTM)
		 */
		public void DoRender(GobListSet  gobListSet, LightSourceList  lights, CTM  inViewCTM)
		{
			if (port == null)
				return ;
			CTM  ident = new CTM();
				port.BackgroundColor(bgColor);
			lm.setBoundingBox(port.GetScreenBounds());
			currentLights = lights;
			port.Clear();
			viewCTM = ident;
			if (gobListSet.get(0).getLayer() != LayerPosition.LayerModel)
			{
				for (GobList gl : gobListSet)
					Dispatch(gl);
			}
			viewCTM = inViewCTM;
			
			/**
			 * Certain gobs must be split to do an accurate depth sort
			 */
			/* 
			 * This section decomposes complex gobs into simple ones that can be depth sorted.
			 */
			/*
			for (i=0; i < gobListSet.get(LayerPosition.LayerModel.ordinal()).size(); i++)
			{
				depth dd;
				Gob gob = gobListSet.get(LayerPosition.LayerModel.ordinal()).get(i);
				
				if (m_bSplitPoly && gob.Type() == GobType.Poly)
				{
					GobPoly  gp = (GobPoly  )gob;
					switch (gp.GetPolyType())
					{
					case TMeshStrip:
						int i1;
						for (i1=2; i1 < gp.getVertices().size(); i1++)
						{
							GobPoly  newGP = new GobPoly();
							newGP.SetPolyType(GobPolyType.Triangles);
							newGP.setMaterial(gp.getMaterial());
							newGP.setColor(gp.getColor());
							int j;
							for (j=2; j >= 0; j--)
								newGP.AddVertex(gp.get(i1-j));
							removeableGobs.add(newGP);
						}
						break;
					case Connected:
					case Closed:
						{
							for (int i11 = 1; i11 < gp.getVertices().size(); i11++)
							{
								Vertex v1 = new Vertex(gp.get(i11-1));
								Vertex v2 = new Vertex(gp.get(i11));

								GobVector  vg = new GobVector(v1, v2);
								vg.setMaterial(gp.getMaterial());
								removeableGobs.add(vg);

							}
						}

						break;
					default:
					}
				}
				else
				{
					dd.z = viewCTM.XForm(dd.g.Center).z;
					dlist.add(dd);
				}
			}
			dlist.Sort();
			for (i=0; i < dlist.Count; i ++)
				temp.Add(dlist(i).g);
			Dispatch(temp);
			*/
			viewCTM = ident;
			for (GobList gl : gobListSet)
				Dispatch(gl);
			return;
		}


		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.graphics.renderers.Renderer#Vector(com.bobandthomas.Morbid.graphics.GobVector)
		 */
		public void Vector(GobVector  g)
		{
			boolean shade = false;
			Point3D start = ctm.XForm(g.getPoint());
			Point3D end = ctm.XForm(g.getEndPoint());
			ColorQuad endColor = g.getColor();
			ColorQuad startColor = g.getColor();
			ColorQuad currentColor = g.getColor();
			if (!(g.EndColor == g.getColor()) )
			{
				shade = true;
				endColor = g.EndColor;
			}
			startColor = lm.DepthCue(start, startColor);
			endColor = lm.DepthCue(end, endColor);


			port.FrameColor(startColor);
			if (m_bShadeLines && shade)
			{
				int i;
				currentColor = endColor;
				Vector3D inc = (end.Sub(start)).Scale(m_nShadeSteps);
				port.MoveTo(start);
				Point3D next = start;
				for (i=1; i <= m_nShadeSteps; i++)
				{
					next = next.Add(inc);
					currentColor = endColor.BlendHSV(startColor, (i-1.0f)/m_nShadeSteps);
					port.FrameColor(currentColor);
					port.LineTo(next);
				}

			}
			else if (shade)
			{
				currentColor = startColor.BlendHSV(endColor, 0.5);
				port.Vector(start, end);
			}
			else
				port.Vector(start, end);

		}

		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.graphics.renderers.Renderer#Arrow(com.bobandthomas.Morbid.graphics.ArrowGob)
		 */
		public void Arrow(ArrowGob  g)
		{
			Vector(g);

				port.FrameColor(g.getColor());
			
				Point3D p1 = ctm.XForm(g.getPoint());
				Point3D p2 = ctm.XForm(g.getEndPoint());
				float /*Coord*/ wingSize = 5.0f;
				
		        Vector3D outVector = (p2.Sub(p1)).Cross(p2.Sub(p2.Add(new Vector3D(0,0,1))));
		        						// Cross product gives vector 
		        						// in the plane perpendicular to arrow line at p2;
				port.Vector(p1,p2);

				Vector3D inLine = p2.Sub(p1);
		        inLine.Normalize();
		       	outVector.Normalize();
		        
		        Point3D wingTip = p2.Sub(inLine.Scale(wingSize).Add(outVector.Scale(wingSize)));
		        port.MoveTo(p2);
		        port.LineTo(wingTip);
		         
		        
		        wingTip = p2.Sub(inLine.Scale(wingSize).Add(outVector.Scale(wingSize)));
		        port.MoveTo(p2);
		        port.LineTo(wingTip);
		}

		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.graphics.renderers.Renderer#String(com.bobandthomas.Morbid.graphics.StringGob)
		 */
		public void String(StringGob  g)
		{
			port.TextColor(g.getColor());
		 	port.Text(ctm.XForm(g.getPoint()), g.getName());
			
		}

		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.graphics.renderers.Renderer#Label(com.bobandthomas.Morbid.graphics.LabelGob)
		 */
		public void Label(LabelGob g)
		{   
			port.TextColor(g.getColor());
		 	port.Text(ctm.XForm(g.getPoint()), g.getName());
		}

		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.graphics.renderers.Renderer#Circle(com.bobandthomas.Morbid.graphics.CircleGob)
		 */
		public void Circle(CircleGob g)
		{
			port.FillColor(g.getColor());
			port.FrameColor(StaticColorQuad.Black.cq());
			
			Point3D rad = Scale.Scale(zoom* g.r);
		 	port.Circle(ctm.XForm(g.getPoint()), rad);
		}
		
		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.graphics.renderers.Renderer#LabeledCircle(com.bobandthomas.Morbid.graphics.LabeledCircleGob)
		 */
		public void LabeledCircle(LabeledCircleGob  g)
		{
			Circle(g);
			port.TextColor(g.getColor().Inverse());
		 	port.Text(ctm.XForm(g.getPoint()), g.Label);
			

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
			Vector3D outVector = bondVector.Add(new Vector3D(0.0f, 0.0f, 1.0f));
			Vector3D crossVector = bondVector.Cross(outVector);
		   	crossVector.Normalize();
		   	double scaledR = g.getRadius() * Scale.x * zoom;
		    Point3DList  s = new Point3DList();
		    s.add(newStart.Add(crossVector.Scale(scaledR)));
		    s.add(newStart.Sub(crossVector.Scale(scaledR/100)));
		    s.add(newStart.Sub(crossVector.Scale(scaledR)));
		    s.add(newEnd.Add(crossVector.Scale(scaledR)));
		    s.add(newEnd.Add(crossVector.Scale(scaledR/100)));
		    s.add(newEnd.Sub(crossVector.Scale(scaledR)));
	    	port.FillColor(g.getColor());
			port.FrameColor(StaticColorQuad.Black.cq());
		    port.Polygon(s);
		    	
		}

		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.graphics.renderers.Renderer#Sphere(com.bobandthomas.Morbid.graphics.SphereGob)
		 */
		public void Sphere(SphereGob g)
		{   
			Point3D pos = ctm.XForm(g.getPoint());
			ColorQuad cq = g.getColor();
			cq = lm.DepthCue(pos, cq);
			port.FillColor(cq);
			port.FrameColor(StaticColorQuad.Black.cq());
			Point3D rad=Scale.Scale(zoom * g.getRadius());
			
		 	port.Circle(pos, rad);
		}



		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.graphics.renderers.Renderer#Poly(com.bobandthomas.Morbid.graphics.GobPoly)
		 */
		public void Poly(GobPoly g)
		{
			int i;
			if (g.getVertices().size() == 0)
				return;
			switch (g.GetPolyType())
			{
			case Connected:
			case Closed:
				{
					
					port.MoveTo(ctm.XForm(g.get(0)));
				
					for (i=1; i < g.size(); i++)
					{
						GobVector  vg = new GobVector(g.get(i-1), g.get(i));
						if (!g.isHasColors())
						{
							vg.setColor(currentMaterial.getColor());
						}
						Vector(vg);
					}
					if (g.GetPolyType() == GobPolyType.Closed)
						port.LineTo(ctm.XForm(g.get(0)));
				}
				break;
			case Lines:
				{
					for (i=0; i < g.size(); i+=2)
					{
						GobVector  vg = new GobVector(g.get(i), g.get(i+1));
						if (!g.isHasColors())
						{
							vg.setColor(currentMaterial.getColor());
						}
						Vector(vg);
					}
				}
				break;
			case Arrows:
				{
					for (i=0; i < g.size(); i+=2)
					{
						ArrowGob  vg = new ArrowGob(1,g.get(i), g.get(i+1));
						if (!g.isHasColors())
						{
							vg.setColor(currentMaterial.getColor());
						}
						Arrow(vg);
					}
				}
				break;
			case Points:
				for (i=0; i < g.size(); i+=2)
				{
					if (g.isHasColors())
						port.DrawPoint(ctm.XForm(g.get(i)), g.get(i).getColor());
					else
						port.DrawPoint(ctm.XForm(g.get(i)));
				}
				break;
			case Triangles:
			case TMeshStrip:
				{
					Point3DList  t = new Point3DList();
					for (int i1 = 0; i1 < 3; i1++)
						t.add(new Point3D());
					Point3D normal;

					Material  tempMat = new Material(currentMaterial);
					port.FrameColor(StaticColorQuad.Black.cq());
					ColorQuad cq;
					for (i = 0; i < g.size(); i++)
					{
						t.set(i % 3, ctm.XForm(g.get(i)));
						if (i >= 2)
						{
							if (currentMaterial!= null)
							{
								if (g.isHasColors())
								{
									ColorQuad fc = g.get(i).getColor().plus(g.get(i-1).getColor()).plus(g.get(i-2).getColor()).multiply(1/3);
									tempMat.setColor(fc);
								}
								Point3D center = t.get(0).Add(
										(Vector3D)(t.get(1).Add((Vector3D) t.get(2)))
										).Scale(1/3.0);
								if (g.isHasNormals())
								{
									normal = viewCTM.XForm(g.get(i).getNormal());
									cq = lm.LightPoint(tempMat, currentLights, center, normal);
								}
								else
								{
									cq = lm.LightPoint(tempMat, currentLights, center, t.get(0).getVector().Normalize());
								}
								port.FillColor(cq);
							}
								port.Polygon(t);
						}
					}
					break;
				}
			case TMeshFan:
				{
					Point3DList  t = new Point3DList();
					for (int i1 = 0; i1 < 3; i1++)
						t.add(new Point3D());
					Point3D normal;
					int nextIndex;
					int size = g.size();
				
					Material  tempMat = new Material(currentMaterial);
					port.FrameColor(StaticColorQuad.Black.cq());
					ColorQuad cq;
					t.set(0,ctm.XForm(g.get(0)));
					for (i = 1; i <= size; i++)
					{	
						t.set(1,t.get(2));
						if (i == size)
							nextIndex = 1;
						else
							nextIndex = i;
						t.set(2,ctm.XForm(g.get(nextIndex)));

						if (i >= 2)
						{
							if (currentMaterial!= null)
							{
								if (g.isHasColors())
								{
									ColorQuad fc = g.get(i).getColor().plus(g.get(i-1).getColor()).plus(g.get(i-2).getColor()).multiply(1/3);
									tempMat.setColor(fc);
								}
								Point3D center = t.get(0).Add(
										(Vector3D)(t.get(1).Add((Vector3D) t.get(2)))
										).Scale(1/3.0);
								if (g.isHasNormals())
								{
									normal = viewCTM.XForm(g.get(i).getNormal());
									cq = lm.LightPoint(tempMat, currentLights, center, normal);
								}
								else
								{
									cq = lm.LightPoint(tempMat, currentLights, center, t.get(0).getVector().Normalize());
								}
								port.FillColor(cq);
							}
								port.Polygon(t);
						}
					}
				}
				break;
			case Polygon:
				// {{
				/** Polygon behavior - not used.
				{
					
		int MAXPOLYGONSIZE = 10;
				    Point3DList  pt = new Point3DList();

					Point3D normal;
					int size = g.size();
				
					Material  tempMat = new Material(currentMaterial);
					port.FrameColor(StaticColorQuad.Black.cq());
					ColorQuad fc;
					ColorQuad cq = StaticColorQuad.LiteGray.cq();
					for (i = 0; i < size; i++)
					{	
						if (g.isHasColors())
							fc = fc + g.get(i);
						if (g.hasNormals)
							normal = normal + viewCTM.XForm(g.n(i));
						pt.Add(ctm.XForm(g.get(i)));
					}
					if (g.isHasColors())
					{
						fc = fc /(size*1.0f);
						tempMat.diffuse= fc;
					}
					if (g.hasNormals)
					{
						normal = normal /(size*1.0f);
						cq = lm.LightPoint(tempMat, currentLights, pt(0), normal);
					}
					else
					{
						cq = lm.LightPoint(tempMat, currentLights, pt(0), pt.Normal());
					}
					if (cq.IsColor())
						port.FillColor(cq);
					if (cq.IsColor())
						port.Polygon(pt);
				}*/
				// }}
			default:
				break;
			}
		}

		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.graphics.renderers.Renderer#Indexed(com.bobandthomas.Morbid.graphics.GobIndexed)
		 */
		void Indexed(GobIndexed g)
		{
		}
}


