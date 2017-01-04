package com.bobandthomas.Morbid.graphics.renderers;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import toxi.geom.Quaternion;
import toxi.geom.Vec3D;

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
import com.bobandthomas.Morbid.graphics.renderers.Port.PortCapabilities;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.Vector3D;

public class RendererX3D extends Renderer {
	Document doc;
	Element sceneNode;
    DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder icBuilder;

	public RendererX3D() {
	 	}

	@Override
	public void DoRender(GobListSet goblist, LightSourceList LSList,
			CTM totalCTM) {

		if (!isDirty()) return;
        try {
            icBuilder = icFactory.newDocumentBuilder();
            doc = icBuilder.newDocument();
            Element x3d = doc.createElement("X3D");
            x3d.setAttribute("profile", "Interactive");
            x3d.setAttribute("version", "3.2");
	        x3d.setAttribute("xmlns:xsd","http://www.w3.org/2001/XMLSchema-instance");
            x3d.setAttribute("xsd:noNamespaceSchemaLocation","http://www.web3d.org/specifications/x3d-3.2.xsd" );
            doc.appendChild(x3d);
            sceneNode = doc.createElement("Scene");
            x3d.appendChild(sceneNode);
 
 
        } catch (Exception e) {
            e.printStackTrace();
        }

		
		for (Material m : MaterialList.getOne())
		{
			sceneNode.appendChild(MakeMaterial(m));
		}

		for (LightSource ls: LSList)
		{ 
			MakeLight(ls);
		}
		for (GobList g: goblist)
		{
			Dispatch(g);
		}
        // output DOM XML to console
        Transformer transformer;
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        DOMSource source = new DOMSource(doc);
			if (this.port.isCapableOf(PortCapabilities.SERIALIZED))
	        {
		        StreamResult console = new StreamResult(((StreamPort) port).Stream());
		        transformer.transform(source, console);
	        }
	        else
	        	System.out.println("no stream port in RenderX3D");
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		markClean();
	}
	private String num(double f)
	{
		return String.format("%5.3f", f);
	}
	private Element MakeMaterial(Material m)
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
		 
//			Element proto = doc.createElement("ProtoDeclare");
//			proto.setAttribute("name", "Material_"+ m.getName());
//			Element protoBody = doc.createElement("ProtoBody");
	        Element mat = doc.createElement("Material");
	        mat.setAttribute("DEF", "Material_"+ m.getName());
	        mat.setAttribute("diffuseColor", m.getDiffuseColor().getSpacedString());
	        mat.setAttribute("shininess", num(m.getKSpecular()));
	        mat.setAttribute("ambientIntensity", num(m.getkAmbient()));
	        mat.setAttribute("specularColor", m.getSpecularColor().getSpacedString());
	        mat.setAttribute("transparency", num(1-m.getAlpha()));
//	        mat.setAttribute("emissiveColor", m.getEmissionColor().getSpacedString());
	        mat.setAttribute("ambientIntensity", num(m.getkAmbient()));
	        
//	        protoBody.appendChild(mat);
//	        proto.appendChild(protoBody);
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
		Element light = doc.createElement("PointLight");
		light.setAttribute("DEF", "Light_" + l.getName());
		light.setAttribute("color", l.color.getSpacedString());
		light.setAttribute("location", l.getLocation().getSpacedString());
		sceneNode.appendChild(light);
		
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
	private Element translate(Element el, Point3D p)
	{
		Element trans = doc.createElement("Transform");
		trans.setAttribute("translation", p.getSpacedString());
		if (el!=null)
			trans.appendChild(el);
		return trans;		
	}
	@SuppressWarnings("unused")
	private Element rotate(Element el, GobVector v)
	{
		Element rot = doc.createElement("Transform");
		rot.setAttribute("rotation", getRotationTransform(v));
		if (el!=null)
			rot.appendChild(el);
		return rot;
	}
	private Element appearance(Gob g, boolean useUse)
	{
		Element appearance = doc.createElement("Appearance");
		Element material = doc.createElement("Material");
		if (useUse)
			material.setAttribute("USE", "Material_"+g.getMaterial().getName());
		else
			appearance.appendChild(MakeMaterial(g.getMaterial()));
		appearance.appendChild(material);
		return appearance;
		
	}
	
	@Override
	void Cylinder(CylinderGob g) {
		Element trans = doc.createElement("Transform");
		trans.setAttribute("rotation", getRotationTransform(g));
		trans.setAttribute("translation", g.getCenter().getSpacedString());
		Element shape = doc.createElement("Shape");
		Element sphere = doc.createElement("Cylinder");
		sphere.setAttribute("radius", num(g.getRadius()));
		sphere.setAttribute("height", num(g.getLength()));
		shape.appendChild(sphere);
		shape.appendChild(appearance(g, true));
		trans.appendChild(shape);
		sceneNode.appendChild(trans);

	}

	@Override
	void Sphere(SphereGob g) {
		Element shape = doc.createElement("Shape");
		Element sphere = doc.createElement("Sphere");
		sphere.setAttribute("radius", num(g.getRadius()));

		shape.appendChild(sphere);
		shape.appendChild(appearance(g, true));
		sceneNode.appendChild(translate(shape, g.center()));
		

	}

}
