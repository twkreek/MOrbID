package com.bobandthomas.Morbid.graphics;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Group;
import javax.media.j3d.Node;
import javax.media.j3d.PointArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Point3d;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3f;

import com.bobandthomas.Morbid.graphics.GobPoly.GobPolyType;
import com.bobandthomas.Morbid.utils.Point3D;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class RendererJava3D extends Renderer {
	private BranchGroup rootBranch;
	private TransformGroup objTrans; // add primitives to this
	private SimpleUniverse universe; //owned by the port.
	private boolean lightingAdded = false;
	private BranchGroup currentBG = null;
	private Hashtable<String, BranchGroup> bgMap = new Hashtable<String, BranchGroup>();
	private Transform3D lookat;
	
	private void makeGroupWritable(Group g)
	{
		g.setCapability(BranchGroup.ALLOW_DETACH);
		g.setCapability(Group.ALLOW_CHILDREN_READ);
		g.setCapability(Group.ALLOW_CHILDREN_WRITE);
		g.setCapability(Group.ALLOW_CHILDREN_EXTEND);
		g.setCapability(Node.ALLOW_BOUNDS_READ);
		g.setCapability(Node.ALLOW_BOUNDS_WRITE);		
	}

	public RendererJava3D() {
		rootBranch = new BranchGroup();
		rootBranch.setName("rootBranch");
		makeGroupWritable(rootBranch);

		objTrans = new TransformGroup();
		objTrans.setName("objTrans");
		makeGroupWritable(objTrans);

		Transform3D lookat = new Transform3D();
		lookat.set(0.15);
		objTrans.setTransform(lookat);
		
		setupMouse();
		rootBranch.addChild(objTrans);
	}


	public BranchGroup getBranchGroup(String name) {
		BranchGroup branchGroup;
		branchGroup = bgMap.get(name);
		if (branchGroup == null) {
			branchGroup = new BranchGroup();
			branchGroup.setName(name);
			makeGroupWritable(branchGroup);

			objTrans.addChild(branchGroup);
			bgMap.put(name, branchGroup);
		}
		return branchGroup;
	}
	public void setCurrentBranchGroup(GobList gl) {
		currentBG = getBranchGroup(gl.getName());
	}
	private void printNodeLabel(String name, int depth)
	{
		String label="";
		for (int i=0; i< depth; i++)
			label += "      ";
		label +="|_______" + name;
		System.out.println(label);
	}
	@SuppressWarnings("unused")
	private void printGraphTree(Group n, int depth)
	{
		if (n == null) return;
		printNodeLabel(n.getClass().getName() + "-" + n.getName(), depth);
		Enumeration e = n.getAllChildren();
		if (e== null) return;
		while(e.hasMoreElements())
		{
			Object next = e.nextElement();
			if 
			(Group.class.isAssignableFrom(next.getClass()))
			{
				printGraphTree((Group)next, depth+1);
			}
			else
			{
				printNodeLabel(next.getClass().toString(), depth);
			}
		}
	}

	@Override
	public void SetPort(Port p) {
		super.SetPort(p);
		if (p == null)
		{
			return;
		}

		PortJava3D j3p = (PortJava3D) p;
		universe = j3p.universe;
		
		lookat = new Transform3D();
		Rescale();
		
		if (Scale.x >0.0000000001)
			lookat.set(Scale.x/400);
		else
			lookat.set(0.15*zoom);
		objTrans.setTransform(lookat);
		
		universe.getViewingPlatform().setNominalViewingTransform();
	//	universe.addBranchGraph(rootBranch);
	}
	
	private void setupLights(LightSourceList LSList)
	{
		if (lightingAdded) return;
		BranchGroup lights;
			//BranchGroup lights = getBranchGroup("Lights Branch");
			lights = rootBranch;
			lights.detach();
			BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0,
					0.0), 100.0);
			for (LightSource ls : LSList) {
				DirectionalLight light = new DirectionalLight(ls.color.Cf(),
						ls.location.getVec3f());

				light.setInfluencingBounds(bounds);

				lights.addChild(light);
				ls.setRenderedLight(light);
			}
			AmbientLight ambient = new AmbientLight(new Color3f(1.0f, 1.0f,
					1.0f));
			ambient.setInfluencingBounds(bounds);
			lights.addChild(ambient);
			lightingAdded = true;
			universe.addBranchGraph(rootBranch);

	}

	@Override
	public void DoRender(GobListSet goblists, LightSourceList lsList,
			CTM totalCTM) {
		setupLights(lsList);
		for (GobList gl : goblists) {
			if (!gl.isDirty())
				continue;
			setCurrentBranchGroup(gl); //always returns the parent.
			// detach brach group from the objTrans
			currentBG.detach();
		    currentBG.removeAllChildren();
			Dispatch(gl);
			gl.markClean();
			objTrans.addChild(currentBG);
			universe.getViewingPlatform().setNominalViewingTransform();
			
		//	printGraphTree(rootBranch, 1); //debugging
			
			
		}

	}

	private void setupMouse() {
		MouseRotate behavior = new MouseRotate();
		behavior.setTransformGroup(objTrans);
		objTrans.addChild(behavior);
		BoundingSphere sbounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
				1000.0);
		behavior.setSchedulingBounds(sbounds);
		
        MouseZoom behavior2 = new MouseZoom();
        behavior2.setTransformGroup(objTrans);
        objTrans.addChild(behavior2);
		behavior2.setSchedulingBounds(sbounds);
		
		MouseTranslate behavior3 = new MouseTranslate();
		behavior3.setTransformGroup(objTrans);
        objTrans.addChild(behavior3);
        behavior3.setSchedulingBounds(sbounds);
		
		objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	}

	private Appearance getAppearance(Material m) {
		// ColoringAttributes ca = new ColoringAttributes(m.diffuse.Cf(),
		// ColoringAttributes.NICEST);
		javax.media.j3d.Material J3DMaterial = new javax.media.j3d.Material();
		J3DMaterial.setDiffuseColor(m.getColor().Cf());
		J3DMaterial.setAmbientColor((m.getColor().multiply(m.kAmbient)).Cf());
		J3DMaterial.setShininess(m.specularity);
		Appearance app = new Appearance();
		if (m.useFilter) {
			TransparencyAttributes ta = new TransparencyAttributes();
			ta.setTransparencyMode(TransparencyAttributes.BLENDED);
			ta.setTransparency((float) m.alpha);
			app.setTransparencyAttributes(ta);
		}

		app.setMaterial(J3DMaterial);

		return app;
	}

	private Transform3D getRotationTransform(GobVector g) {
		// creates the transform matrix to rotate the object to vector in g

		Vector3f unitV = g.getUnitVector().getVec3f();
		Vector3f unitY = new Vector3f(0.0f, 1.0f, 0.0f);

		Vector3f cross = new Vector3f();
		cross.cross(unitV, unitY);
		float dot = unitV.dot(unitY);

		Quat4d q = new Quat4d(cross.x, cross.y, cross.z, -(1 + dot));
		q.normalize();
		Transform3D t = new Transform3D();
		t.set(q);
		return t;
	}

	@Override
	void Arrow(ArrowGob g) {
		// TODO Auto-generated method stub
		CylinderGob cyl = new CylinderGob(g.center(), g.EndPoint, 0.01f);
		cyl.setMaterial(g.getMaterial());
		Cylinder(cyl);
		
//		Cone = new Cone
	}

	@Override
	void Circle(CircleGob g) {
		// TODO Auto-generated method stub

	}

	@Override
	void Cylinder(CylinderGob g) {

		float length = g.getLength();

		Cylinder cyl = new Cylinder(g.getRadius(), length);

		cyl.setAppearance(getAppearance(currentMaterial));

		TransformGroup tg = new TransformGroup();
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		Transform3D transform = new Transform3D();
		transform.set(getRotationTransform(g));

		transform.setTranslation(g.getCenter().getVec3f());

		tg.setTransform(transform);
		tg.addChild(cyl);
		currentBG.addChild(tg);
	}

	@Override
	void Indexed(GobIndexed g) {
		// TODO Auto-generated method stub

	}

	@Override
	void Label(LabelGob g) {
		// TODO Auto-generated method stub

	}

	@Override
	void LabeledCircle(LabeledCircleGob g) {
		// TODO Auto-generated method stub

	}

	@Override
	void Poly(GobPoly g) {
		
		GeometryArray array;
		int size = g.points.size();
		int flags = GeometryArray.COORDINATES;
		if (g.hasColors)
			flags |= GeometryArray.COLOR_4;
		if (g.hasNormals)
			flags |= GeometryArray.NORMALS;
		if (g.GetPolyType() == GobPolyType.Points)
			array = new PointArray(size, flags);
		else
			array = new TriangleArray(size, flags);
		Point3d points[] = new Point3d[size];
		
		
		for (int i = 0; i< size; i++)
		{
			points[i]=g.points.get(i);
		}
		array.setCoordinates(0,points);
		if (g.hasColors)
		{
			Color4f colors[] = new Color4f[size];
			for (int i = 0; i< size; i++)
			{
				colors[i]=g.colors.get(i);
			}

			array.setColors(0,colors);
		}
		if (g.hasNormals)
		{
			Vector3f normals[] = new Vector3f[size];
			for (int i = 0; i< size; i++)
			{
				Point3D p = g.normals.get(i);
				normals[i]=p.getVec3f();
			}

			array.setNormals(0,normals);
				
		}
		Shape3D shape = new Shape3D(array, getAppearance(currentMaterial));
		
		currentBG.addChild(shape);

	}

	@Override
	void Sphere(SphereGob g) {
		
		int tessalations;
		if (g.getLOD() < 0.01) tessalations = 200;
		else tessalations = (int) (200/g.getLOD());
		Sphere sphere = new Sphere((float) g.r, Primitive.GENERATE_NORMALS, tessalations);
		sphere.setName(g.getName());

		sphere.setAppearance(getAppearance(currentMaterial));

		TransformGroup tg = new TransformGroup();
		Transform3D transform = new Transform3D();
		Vector3f vector = g.center().getVec3f();
		transform.setTranslation(vector);
		tg.setTransform(transform);
		tg.addChild(sphere);
		currentBG.addChild(tg);
	}

	@Override
	void String(StringGob g) {
		// TODO Auto-generated method stub

	}

	@Override
	void Vector(GobVector g) {
		// TODO Auto-generated method stub

	}

}