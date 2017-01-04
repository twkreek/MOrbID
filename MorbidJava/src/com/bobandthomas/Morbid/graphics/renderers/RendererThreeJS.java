package com.bobandthomas.Morbid.graphics.renderers;

import java.io.PrintWriter;

import toxi.geom.Quaternion;
import toxi.geom.Vec3D;

import com.bobandthomas.Morbid.graphics.ArrowGob;
import com.bobandthomas.Morbid.graphics.CTM;
import com.bobandthomas.Morbid.graphics.CircleGob;
import com.bobandthomas.Morbid.graphics.CylinderGob;
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
import com.bobandthomas.Morbid.graphics.renderers.Port.PortCapabilities;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.Vector3D;

public class RendererThreeJS extends Renderer {

	PrintWriter print;
	public RendererThreeJS() {
	 	}
	private String getArray(String arrayName, String index)
	{
		return String.format("%s[\"%s\"]", arrayName, index);
	}

	@Override
	public void DoRender(GobListSet goblist, LightSourceList LSList,
			CTM totalCTM) {

		if (!isDirty()) return;
        try {
			if (this.port.isCapableOf(PortCapabilities.SERIALIZED))
	        {
		        print = new PrintWriter(((StreamPort) port).Stream());
		        print.println(); print.println("/* global THREE */\n//begin RenderThreeJS"); print.println();
	        }
	        else
	        	System.out.println("no stream port in RenderX3D");
        	
 
        } catch (Exception e) {
            e.printStackTrace();
        }
        print.println("var Morbid = new Object;");
		
		print.println("Morbid.Materials = new Object;\nMorbid.createMaterials = function() {");
		for (Material m : MaterialList.getOne())
		{
			print.print(getArray("Morbid.Materials", m.getName()));
			print.println(" = new " +MakeMaterial(m));
		}
		print.println("};");

		for (LightSource ls: LSList)
		{ 
			MakeLight(ls);
		}
		
		print.println();
		print.println("Morbid.Gob = new Object;\nMorbid.createGobs = function() {\n var geom;\n var mesh;\n");
		for (GobList g: goblist)
		{
			Dispatch(g);
		}
		print.println("};");
		markClean();
        print.println(); print.println("//end RenderThreeJS"); print.println();
		print.close();
		print = null;
	}
	private String prop(String field, String value)
	{
		return field + ": " + value;
	}
	@SuppressWarnings("unused")
	private String num(double f)
	{
		return String.format("%5.3f", f);
	}
	private String intNum(int in)
	{
		return String.format("%d", in);
	}
	@SuppressWarnings("unused")
	private String hexNum(int d)
	{
		return String.format("0x%08X", d);
	}
	private String hexNum(ColorQuad d)
	{
		return String.format("0x%08X", d.get().getRGB());
	}

	private String MakeMaterial(Material m)
	{
		/*
			Material : X3DMaterialNode { 
			  SFFloat [in,out] ambientIntensity 0.2         [0,1]
			  SFColor [in,out] diffuseColor     0.8 0.8 0.8 [0,1]
			  SFColor [in,out] emissiveColor    0 0 0       [0,1]
			  SFNode  [in,out] metadata         NULL        [X3DMetadataObject]
			  SFFloat [in,out] shininess        0.2         [0,1]
			  SFColor [in,out] specularColor    0 0 0       [0,1]
			  SFFloat [in,out] transparency     0           [0,1]
			}
		 */
		 
		String mat = "THREE.MeshPhongMaterial ( { ";
		
		mat += prop("color", hexNum(m.getColor())) + ", ";
		mat += prop("specular", hexNum(m.getSpecularColor())) + ", ";

		mat += prop("shading", "THREE.SmoothShading") + ", ";
		mat += prop("shininess", intNum(m.getSpecularity()));
		
		mat += "} );";
		return mat;
		
	        
	
	}
	private void MakeLight(LightSource l)
	{
		/*
			PointLight : X3DLightNode {
			  SFFloat [in,out] ambientIntensity 0     [0,1]
			  SFVec3f [in,out] attenuation      1 0 0 [0,)
			  SFColor [in,out] color            1 1 1 [0,1]
			  SFBool  [in,out] global           TRUE
			  SFFloat [in,out] intensity        1     [0,1]
			  SFVec3f [in,out] location         0 0 0 ()
			  SFNode  [in,out] metadata         NULL  [X3DMetadataObject]
			  SFBool  [in,out] on               TRUE
			  SFFloat [in,out] radius           100   [0,)
			}

		 */
		
	}

	@Override
	void Vector(GobVector g) {
		// TODO Auto-generated method stub

	}

	@Override
	void Arrow(ArrowGob g) {
		// TODO Auto-generated method stub

	}

	@Override
	void String(StringGob g) {
		// TODO Auto-generated method stub

	}

	@Override
	void Label(LabelGob g) {
		// TODO Auto-generated method stub

	}

	@Override
	void Circle(CircleGob g) {
		// TODO Auto-generated method stub

	}

	@Override
	void LabeledCircle(LabeledCircleGob g) {
		// TODO Auto-generated method stub

	}

	@Override
	void Poly(GobPoly g) {
		// TODO Auto-generated method stub

	}

	@Override
	void Indexed(GobIndexed g) {
		// TODO Auto-generated method stub

	}
	private String vector(Point3D v)
	{
		return String.format("%5.3f, %5.3f, %5.3f", v.x, v.y, v.z);
		
	}

	@SuppressWarnings("unused")
	private String getRotationTransform(GobVector g) {
		// creates the transform matrix to rotate the object to vector in g
		Vector3D  v = g.getUnitVector();
		Vec3D unitV = new Vec3D();
		unitV.set((float) v.x, (float) v.y, (float) v.z);
		Vec3D unitY = new Vec3D(0.0f, 1.0f, 0.0f);
		Quaternion q = Quaternion.getAlignmentQuat(unitV, unitY);
		float[] aa = q.toAxisAngle();

		String s = String.format("%5.3f %5.3f %5.3f %5.3f", aa[1], aa[2], aa[3], aa[0]);
		return s;
	}
	
	@Override
	void Cylinder(CylinderGob g) {
		print.printf("geom = new THREE.CylinderGeometry(%5.3f, %5.3f, %5.3f);\n",
				g.getRadius(),
				g.getRadius(),
				g.getLength()*2);

		
		print.printf("mesh = new THREE.Mesh(geom, Morbid.Materials[\"%s\"]);\n", g.getMaterial().getName());
		print.printf("mesh.position.set(%s);\n", vector(g.center()));
		print.printf("%s = mesh;\n", getArray("Morbid.Gob", Long.toString(g.getID())));		
	}

	@Override
	void Sphere(SphereGob g) {
		print.printf("mesh = new THREE.Mesh(new THREE.SphereGeometry(%5.3f), Morbid.Materials[\"%s\"]);\n",
				g.getRadius(), 
				g.getMaterial().getName());
		print.printf("mesh.position.set(%s);\n", vector(g.center()));
		print.printf("%s = mesh;\n", getArray("Morbid.Gob", Long.toString(g.getID())));		
		
	}

}
