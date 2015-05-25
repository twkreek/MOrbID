package com.bobandthomas.Morbid.graphics.renderers;

import java.io.IOException;
import java.io.PrintWriter;

import com.bobandthomas.Morbid.graphics.ArrowGob;
import com.bobandthomas.Morbid.graphics.CTM;
import com.bobandthomas.Morbid.graphics.CircleGob;
import com.bobandthomas.Morbid.graphics.CylinderGob;
import com.bobandthomas.Morbid.graphics.Gob;
import com.bobandthomas.Morbid.graphics.GobIndexed;
import com.bobandthomas.Morbid.graphics.GobList;
import com.bobandthomas.Morbid.graphics.GobListSet;
import com.bobandthomas.Morbid.graphics.GobPoly;
import com.bobandthomas.Morbid.graphics.GobVector;
import com.bobandthomas.Morbid.graphics.LabelGob;
import com.bobandthomas.Morbid.graphics.LabeledCircleGob;
import com.bobandthomas.Morbid.graphics.LightSource;
import com.bobandthomas.Morbid.graphics.LightSourceList;
import com.bobandthomas.Morbid.graphics.Material;
import com.bobandthomas.Morbid.graphics.MaterialList;
import com.bobandthomas.Morbid.graphics.SphereGob;
import com.bobandthomas.Morbid.graphics.StringGob;
import com.bobandthomas.Morbid.graphics.Vertex;
import com.bobandthomas.Morbid.graphics.GobPoly.GobPolyType;
import com.bobandthomas.Morbid.graphics.renderers.Port.PortCapabilities;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.Point3D;

public class RendererPOV extends Renderer {
	PrintWriter povFile;
	String renFile = new String();

	public RendererPOV() {
	}


	void EOL() throws IOException
	{
			povFile.write("\n");
	}
	 void WriteColor(ColorQuad color) throws IOException
	{
		povFile.format("colour rgb<%f, %f %f>\n", color.x/256.0, color.y/256.0, color.z/256.0);
	}
	String colorString(ColorQuad color)
	{
		String s = String.format("colour rgb<%f, %f %f>\n", color.x/256.0, color.y/256.0, color.z/256.0);
		return s;
	}
	String colorStringNamed(ColorQuad color, String name)
	{
		String s = String.format("%s {colour rgb<%f, %f %f>}\n", name, color.x/256.0, color.y/256.0, color.z/256.0);
		return s;
	}
	 void WriteColorFilter(ColorQuad color, double filter) throws IOException
	{
			String s = String.format("colour rgbf<%f, %f %f, %f>\n", color.x/256.0, color.y/256.0, color.z/256.0, 1.0-filter);
			povFile.write(s);
	}
	 String pointString(Point3D point)
	 {
		 return String.format("<%f,%f,%f>", point.x, point.y, point.z);
	 }
	 String twoPointString(String name, Point3D x1, Point3D x2, Material mat)
	 {
		return String.format("%s { %s , %s} %s", name, pointString(x1), pointString(x2), (mat == null)? "" : mat  );
	 }
	void WritePos(Point3D point) throws IOException
	{
		povFile.write(String.format("<%f,%f,%f>", point.x, point.y, point.z));
	}

	void WriteMaterialDefinition(Material mat) throws IOException
	{
		povFile.write("material {\n   texture {\n    pigment {");
		WriteColorFilter(mat.getColor(), mat.getAlpha());
		povFile.write(String.format("} finish { \n specular %f\n diffuse %f\n ambient %f\n reflection %d \n } \n } \n }\n",
				mat.getKSpecular(), mat.getkDiffuse(), mat.getAlpha(), mat.getSpecularity()));
	}

	void WriteMaterial(Gob gob) throws IOException
	{
		Material m = gob.getMaterial();
		if (m == null)
		{
			m = MaterialList.getOne().get(0);
		}
		String name;
		name = m.getName();
		if (name == null) name = String.format("material_%d", gob.getID());
		name.replace(' ', '_');
		povFile.write(String.format("material { %s\n texture { \n", name));
		if (gob.getColor()!= null)
		{
			povFile.write(String.format(" pigment { %s } \n", colorString(gob.getColor())));
		}
		povFile.write("}}\n");
	}

	void WriteMaterialGradient(Gob gob, ColorQuad start, ColorQuad finish, Point3D vec) throws IOException
	{
		Material m = gob.getMaterial();
		if (m == null)
		{
			m = MaterialList.getOne().get(0);
		}
		String name;
		name = m.getName();
		name.replace(' ', '_');
		povFile.format("material { %s\n texture { \n", name);
		if (gob.getColor()!= null)
		{
			povFile.write(" pigment { gradient ");
			WritePos(vec); 
			povFile.write(" color_map { [ 0.0 ");
				WriteColorFilter(start, m.getAlpha());
			povFile.write(" ] [ ");
			povFile.write(String.format("%f ",vec.getVector().Length()));
				WriteColorFilter(finish, m.getAlpha());
			povFile.write(" ] } }");
		}
		povFile.write("}}\n");
	}
	void WriteLabel (String label, Point3D  pos, ColorQuad color)
	{
//TODO - label
	}

	public void DoRender(GobListSet goblist, LightSourceList lights, CTM inViewCTM)
	{
//		povFile << "#include \"Morbid.inc\"\n";
//		povFile << "#include \"chars.inc\"";

		
		try {
			if (this.port.isCapableOf(PortCapabilities.SERIALIZED))
			{
				povFile = new PrintWriter(((StreamPort) port).Stream());
			}
			
//			povFile.write("#VRML V2.0 utf8\r\n");
	
		povFile.write(String.format("camera {\n location < 0, 0, %f > \nsky <0, -1, 0> \n look_at <0, 0, 0> \n}\n ", 
			Math.max(worldBox.size().x, worldBox.size().y) * 1.25) );

		if (bgColor != null)
		{
			povFile.write(colorStringNamed(bgColor,"background"));
		}
		

		povFile.write("// Material List \n"); 
		for (Material m : MaterialList.getOne())
		{
			String name;
			name = m.getName().replace('_', '_');
			povFile.write(String.format("#declare %s = ", name));
			WriteMaterialDefinition(m); EOL();
		}

		povFile.write("// Other declarations\n#declare LineWidth = 0.01;\n");

		povFile.write("// LightSources\n");
		for (LightSource ls: lights)
		{ 
		  povFile.write(String.format("light_source { // %s \n", ls.getName()));
		  WritePos((ls.getLocation().Scale(1000))); EOL();
		  WriteColor(ls.color); EOL();
		  povFile.write("}\n"); 
		}
		for (GobList g: goblist)
		{
			povFile.format("// GobList %s\n", g.getName());
			Dispatch(g);
		}
		povFile.close();
		}
		catch(IOException e)
		{
			
		}
	}


	public void Vector(GobVector vg) 
	{
		Point3D x1 = viewCTM.XForm(vg.center());
		Point3D x2 = viewCTM.XForm(vg.getEndPoint());
		
	

		try {
			povFile.write("cylinder {");
			WritePos(x1);
			povFile.write(",");
			WritePos(x2);
			povFile.write(", LineWidth ");
			WriteMaterial(vg);
			povFile.write("}\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	void Arrow(ArrowGob vg)
	{
		Point3D x1 = viewCTM.XForm(vg.center());
		Point3D x2 = viewCTM.XForm(vg.getEndPoint());
		Point3D arrowHead = x2.Sub((x2.Sub(x1).Scale(0.05)));

		Vector(vg);

		try {

			povFile.write("cone {");
			WritePos(x1);
			povFile.write(",");
			WritePos(arrowHead);
			povFile.write(", LineWidth ");
			WriteMaterial(vg);
			povFile.write("}\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	void String(StringGob sg){}
	void Label(LabelGob lg){}
	void Circle(CircleGob cg){}
	void LabeledCircle(LabeledCircleGob lcg){}
	void Poly(GobPoly pvg)
	{
		int i;
		Point3D from = new Point3D(0,0,0);
		GobVector vg = new GobVector(from, from);
//		ArrowGob ag = new ArrowGob(1,from,from);
		try {
		switch(pvg.GetPolyType())
		{
		case Connected:
		case Closed:
			{
				povFile.write("// Poly Lines \n");
				Vertex x1= null, x2 = null;
				x1 = viewCTM.XForm(pvg.getVertices().get(0));
				for (Vertex p: pvg.getVertices())
				{
					if (x1 == null)
					{
						x1 = viewCTM.XForm(p);
						break;
					}
					x2 = viewCTM.XForm(p);
					povFile.write("cylinder {");
					WritePos(x1);
					povFile.write(",");
					WritePos(x2);
					povFile.write(", LineWidth ");
					if (pvg.hasColors())
						WriteMaterialGradient(pvg, x1.getColor(), x2.getColor(), x2.Sub(x1));
					else
						WriteMaterial(pvg);
					povFile.write("}\n");

					x1 = x2;
				}
				if (pvg.GetPolyType() == GobPolyType.Closed)
				{
					x1 = viewCTM.XForm(pvg.getVertices().get(0));
					povFile.write(twoPointString("cylinder", x1, x2, pvg.getMaterial()));
				}
			}
			break;
		case Lines:
			for (i= 0; i < pvg.size(); i+=2)
			{	
				vg = new GobVector(pvg.get(i), pvg.get(i+1));
				Vector(vg);
			}
			break;
		case Points:
			{
				for (Vertex v: pvg.getVertices())
				{
					Point3D center = viewCTM.XForm(v);
					povFile.write( "sphere {");
						WritePos(center); 
						povFile.write(", 0.01");
						WriteMaterial(pvg);
					povFile.println("}");
					
				}
			}
			break;
		case Arrows:
//TODO VRML Arrows
/*			for (i= 0; i < pvg.points.GetSize(); i+=2)
			{	
				if (pvg.hasColors)
				{
					ag.SetColor(pvg.colors[i]);
					ag.SetEndColor(pvg.colors[i+1]);
				}
				ag.startPoint = pvg.points[i];
				ag.endPoint = pvg.points[i+1];
				Arrow(&ag);
			}
			break;
*/
		case TMeshStrip:
//TODO VRML TMeshStrips - may deprecate.
/*			{
				Point3D p[3], n[3];
				povFile << "// Triangle Mesh strip\n";
				for (i=0; i < pvg.points.GetSize(); i ++)
				{
					p[i%3] = viewCTM.XForm(pvg.points[i]);
					if (pvg.hasNormals)
						n[i%3] = viewCTM.XForm(pvg.n[i]);
					if (i > 2)
						if (pvg.hasNormals)
						{
							povFile << "smooth_triangle {";
							WritePos(p[0]);
							povFile << ",";
							WritePos(n[0]);
							povFile << ",";
							WritePos(p[1]);
							povFile << ",";
							WritePos(n[1]);
							povFile << ",";
							WritePos(p[2]);
							povFile << ",";
							WritePos(n[2]);

							if (pvg.hasColors)
							{
								Point3D vector = Point3D(0, 0, p[2].z - p[0].z);
								WriteMaterialGradient(pvg, pvg.colors[i-1], pvg.colors[i], vector);
							}
							else
								WriteMaterial(pvg);

							povFile << "}"; EOL	
						}
						else
						{
							povFile << "triangle {";
								WritePos(p[0]);
								povFile << ",";
								WritePos(p[1]);
								povFile << ",";
								WritePos(p[2]);

								WriteMaterial(pvg);
							povFile << "}"; EOL	
						}
				}
			}
			break;
		case GobPoly_TMeshFan:
			{
				Point3D p[3], n[3];
				p[0] = viewCTM.XForm(pvg.points[0]);
				if (pvg.hasNormals)
					n[0] = viewCTM.XForm(pvg.n[0]);

				int size = pvg.points.GetSize();
				int nextIndex;
				povFile << "// Triangle Mesh Fan " << size << "\n";
				for (i=1; i <= size; i ++)
				{
					p[1] = p[2];
					n[1] = n[2];
					if (i == size)
						nextIndex = 1;
					else
						nextIndex = i;
					p[2] = viewCTM.XForm(pvg.points[nextIndex]);
					if (pvg.hasNormals)
						n[2] = viewCTM.XForm(pvg.n[nextIndex]);
					if (i > 2)
						if (pvg.hasNormals)
						{
							povFile << "smooth_triangle {";
							WritePos(p[0]);
							povFile << ",";
							WritePos(n[0]);
							povFile << ",";
							WritePos(p[1]);
							povFile << ",";
							WritePos(n[1]);
							povFile << ",";
							WritePos(p[2]);
							povFile << ",";
							WritePos(n[2]);

							if (pvg.hasColors)
							{
								Point3D vector = Point3D(0, 0, p[2].z - p[0].z);
								WriteMaterialGradient(pvg, pvg.colors[nextIndex-1], pvg.colors[nextIndex], vector);
							}
							else
								WriteMaterial(pvg);

							povFile << "}"; EOL	
						}
						else
						{
							povFile << "triangle {";
								WritePos(p[0]);
								povFile << ",";
								WritePos(p[1]);
								povFile << ",";
								WritePos(p[2]);

								WriteMaterial(pvg);
							povFile << "}"; EOL	
						}
				}
				povFile << "//End of TMesh Fan \n";
			}
			break;
*/
			case Polygon:
//TODO VRML Polygon
/*			{
				Point3D p[3], n[3];
				p[0] = viewCTM.XForm(pvg.center/ pvg.points.GetSize());
				if (pvg.hasNormals)
					n[0] = viewCTM.XForm(pvg.n[0]);

				int size = pvg.points.GetSize();
				if (size < 3)
					return;
				int nextIndex;
				povFile << "// Polygon " << size << "\n";
				for (i=0; i <= size; i ++)
				{
					p[1] = p[2];
					n[1] = n[2];
					if (i == size)
						nextIndex = 0;
					else
						nextIndex = i;
					p[2] = viewCTM.XForm(pvg.points[nextIndex]);
					if (pvg.hasNormals)
						n[2] = viewCTM.XForm(pvg.n[nextIndex]);
					if (i >= 1)
						if (pvg.hasNormals)
						{
							povFile << "smooth_triangle {";
							WritePos(p[0]);
							povFile << ",";
							WritePos(n[0]);
							povFile << ",";
							WritePos(p[1]);
							povFile << ",";
							WritePos(n[1]);
							povFile << ",";
							WritePos(p[2]);
							povFile << ",";
							WritePos(n[2]);

							if (pvg.hasColors)
							{
								Point3D vector = Point3D(0, 0, p[2].z - p[0].z);
								WriteMaterialGradient(pvg, pvg.colors[nextIndex-1], pvg.colors[nextIndex], vector);
							}
							else
								WriteMaterial(pvg);

							povFile << "}"; EOL	
						}
						else
						{
							povFile << "triangle {";
								WritePos(p[0]);
								povFile << ",";
								WritePos(p[1]);
								povFile << ",";
								WritePos(p[2]);

								WriteMaterial(pvg);
							povFile << "}"; EOL	
						}
				}
				povFile << "//End of Polygon \n";
			}
			break;
*/
			case Triangles:
			{

				for (i=0; i < pvg.getVertices().size(); i +=3)
				{
					Vertex v1 = viewCTM.XForm(pvg.get(i));
					Vertex v2 = viewCTM.XForm(pvg.get(i+1));
					Vertex v3 = viewCTM.XForm(pvg.get(i+2));
					if (pvg.hasNormals())
					{
						povFile.format("smooth_triangle {%s, %s,  %s, %s, %s, %s ", 
								pointString(v1), pointString(v1.getNormal()), 
								pointString(v2), pointString(v2.getNormal()), 
								pointString(v3), pointString(v3.getNormal()));
						WriteMaterial(pvg);
						if (pvg.hasColors())
							WriteColor(v1.getColor());
						povFile.println("}");	
					}
					else
					{
						povFile.format("triangle {%s, %s,  %s ", 
								pointString(v1), pointString(v1.getNormal()), 
								pointString(v2), pointString(v2.getNormal()), 
								pointString(v3), pointString(v3.getNormal()));
						WriteMaterial(pvg);
						if (pvg.hasColors())
							WriteColor(v1.getColor());
						povFile.println("}");	
					}
				}
			}
			break;
		case Quads:
		case Bezier:
		case BSpline:
		case Nurbs:
			;
		case Segments:
			break;
		case TMeshFan:
			break;
		default:
			break;
		}
		} catch (IOException e)
		{
			
		}
		finally{
			
		}

	}
	void Indexed(GobIndexed gi){}
	void Cylinder(CylinderGob cy)
	{
		try {
			Point3D x1 = viewCTM.XForm(cy.getPoint());
			Point3D x2 = viewCTM.XForm(cy.getEndPoint());
			povFile.format("cylinder { %s, %s, %3.5f ", pointString(x1), pointString(x2), cy.getRadius() *zoom);
			WriteMaterial(cy);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		povFile.println( "}");

	}
	void Sphere(SphereGob sp)
	{
		Point3D center = viewCTM.XForm(sp.getPoint());
		try {
			povFile.println("sphere { <0,0,0>, 1");
			WriteMaterial(sp);
			povFile.format(" scale %5.3f\ntranslate %s \n}", sp.getRadius(), pointString(center));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			 
	}


}
