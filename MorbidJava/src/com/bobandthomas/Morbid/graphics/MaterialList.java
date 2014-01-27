package com.bobandthomas.Morbid.graphics;

import java.io.IOException;

import com.bobandthomas.Morbid.utils.CLoadableTable;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.wrapper.CSVFileReader;
import com.bobandthomas.Morbid.wrapper.ResourceMgr;

public class MaterialList extends CLoadableTable<Material> {

	public enum StaticMaterial {
		White("White", 1, 255, 255, 255, 0.4, 0.5, 0.7, 1, 0, 10, 1), 
		Blue(
				"Blue", 1, 0, 51, 255, 0.4, 0.13, 0.7, 1, 0, 4, 1), Green(
				"Green", 1, 0, 255, 0, 0.4, 0.5, 0.5, 1, 0, 10, 1), Cyan(
				"Cyan", 1, 0, 255, 255, 0.4, 0.5, 0.5, 1, 0, 10, 1), Red("Red",
				1, 255, 0, 0, 0.4, 0.13, 0.5, 1, 0, 10, 1), Yellow("Yellow", 1,
				255, 255, 0, 0.4, 0.5, 0.5, 1, 0, 10, 1), Magenta("Magenta", 1,
				255, 0, 255, 0.4, 0.5, 0.5, 1, 0, 10, 1), LiteGray("LiteGray",
				1, 192, 192, 192, 0.4, 0.5, 0.5, 1, 0, 10, 1), LiteBlue(
				"LiteBlue", 1, 100, 100, 255, 0.4, 0.5, 0.5, 1, 0, 10, 1), LiteRed(
				"LiteRed", 1, 255, 151, 151, 0.4, 0.1, 0.7, 1, 0, 10, 1), Black(
				"Black", 1, 0, 0, 0, 0.8, 0.14, 0.5, 1, 0, 10, 1), YellowGreen(
				"YellowGreen", 1, 192, 255, 0, 0.4, 0.5, 0.5, 1, 0, 10, 1), LiteGreen(
				"LiteGreen", 1, 100, 255, 100, 0.4, 0.5, 0.5, 1, 0, 10, 1), LiteYellow(
				"LiteYellow", 1, 255, 255, 100, 0.4, 0.5, 0.5, 1, 0, 10, 1), DarkGray(
				"DarkGray", 1, 128, 128, 128, 0.4, 0.5, 0.5, 1, 0, 10, 1), Carbon(
				"Carbon", 1, 48, 48, 48, 0.8, 0.1, 1, 1, 0, 50, 1), Hydrogen(
				"Hydrogen", 1, 204, 201, 207, 0.4, 0.5, 0.7, 0.7, 1, 10, 0), Chrome(
				"Chrome", 1, 197, 207, 190, 0.4, 0.45, 0.49, 1, 0, 86, 1), Transparent(
				"Transparent", 1, 0, 0, 0, 0.8, 0.7, 0.7, 0.49, 1, 10, 1);
		String Name;
		double kd;
		ColorQuad diffuse;
		double ka;
		double ks;
		double ke;
		double filter;
		int specularity;
		boolean useSpecularity;
		boolean useFilter;

		StaticMaterial(String name, int kd, int r, int g, int b, double ka,
				double ks, double ke, double filter, int useFilter,
				int specularity, int useSpecularity) {
			Name = name;
			this.kd = kd;
			diffuse = new ColorQuad(r, g, b);
			this.ka = ka;
			this.ks = ks;
			this.ke = ke;
			this.filter = filter;
			this.useFilter = useFilter == 1;
			this.specularity = specularity;
			this.useSpecularity = useSpecularity == 1;

		}

		Material getMaterial() {
			Material mat = new Material();
			mat.setName(Name);
			mat.kDiffuse = kd;
			mat.setColor(new ColorQuad(diffuse));
			mat.kAmbient = ka;
			mat.kSpecular = ks;
			mat.kEmission = ke;
			mat.alpha = filter;
			mat.useFilter = useFilter;
			mat.shininess = specularity;
			mat.useSpecularity = useSpecularity;
			return mat;

		}

	}

	static MaterialList staticSet;

	private MaterialList() {
		setUseByName(true);
		init();
/*		for (StaticMaterial p : StaticMaterial.values()) {
			add(p.getMaterial());
		}
*/
	}

	private boolean initialized = false;
	private void init()
	{
		if (initialized) return;
		
		try {
			CSVFileReader br = ResourceMgr.getResourceCSV("data/Material.csv");
			
			do
			{
				//TODO reading materials produces different results in carbons.
				Material mat = new Material();
				mat.readItem(br);
//				add(mat);
			} while (br.nextLine());
//			initialized = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (!initialized)
		{
			// if read fails. use static enum
			for (StaticMaterial att : StaticMaterial.values())
			{
			
				Material mat = att.getMaterial();
				add(mat);
			}
		}
		initialized = true;
	}
	public static MaterialList getOne() {
		if (staticSet == null)
		{
			staticSet = new MaterialList();
			staticSet.init();
		}
		return staticSet;
	}
	
/*	public void test ()
	{
		Object o = new Material();
	    try {
	        BeanInfo bi = Introspector.getBeanInfo(Material.class);
	        PropertyDescriptor[] pds = bi.getPropertyDescriptors();
	        for (int i=0; i<pds.length; i++) {
	            // Get property name
	            String propName = pds[i].getName();

	           // Get the value of prop1
	           Expression expr = new Expression(o, "Name", new Object[0]);
//	           expr.execute();
//	           String s = (String)expr.getValue();
	    }
	    // class, prop1, prop2, PROP3
	} catch (java.beans.IntrospectionException e) {
	}
	}
	*/

}
