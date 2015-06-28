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
package com.bobandthomas.Morbid.graphics;

import java.io.IOException;

import com.bobandthomas.Morbid.utils.CLoadableTable;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.wrapper.CSVFileReader;
import com.bobandthomas.Morbid.wrapper.ResourceMgr;

// TODO: Auto-generated Javadoc
/**
 * The Class MaterialList.
 * 
 * @author Thomas Kreek
 */
public class MaterialList extends CLoadableTable<Material> {

	/**
	 * The Enum StaticMaterial.
	 * 
	 * @author Thomas Kreek
	 */
	public enum StaticMaterial {
		
		/** The White. */
		White("White", 1, 255, 255, 255, 0.4, 0.5, 0.7, 1, 0, 10, 1), 
		
		/** The Blue. */
		Blue(
				"Blue", 1, 0, 51, 255, 0.4, 0.13, 0.7, 1, 0, 4, 1), 
 /** The Green. */
 Green(
				"Green", 1, 0, 255, 0, 0.4, 0.5, 0.5, 1, 0, 10, 1), 
 /** The Cyan. */
 Cyan(
				"Cyan", 1, 0, 255, 255, 0.4, 0.5, 0.5, 1, 0, 10, 1), 
 /** The Red. */
 Red("Red",
				1, 255, 0, 0, 0.4, 0.13, 0.5, 1, 0, 10, 1), 
 /** The Yellow. */
 Yellow("Yellow", 1,
				255, 255, 0, 0.4, 0.5, 0.5, 1, 0, 10, 1), 
 /** The Magenta. */
 Magenta("Magenta", 1,
				255, 0, 255, 0.4, 0.5, 0.5, 1, 0, 10, 1), 
 /** The Lite gray. */
 LiteGray("LiteGray",
				1, 192, 192, 192, 0.4, 0.5, 0.5, 1, 0, 10, 1), 
 /** The Lite blue. */
 LiteBlue(
				"LiteBlue", 1, 100, 100, 255, 0.4, 0.5, 0.5, 1, 0, 10, 1), 
 /** The Lite red. */
 LiteRed(
				"LiteRed", 1, 255, 151, 151, 0.4, 0.1, 0.7, 1, 0, 10, 1), 
 /** The Black. */
 Black(
				"Black", 1, 0, 0, 0, 0.8, 0.14, 0.5, 1, 0, 10, 1), 
 /** The Yellow green. */
 YellowGreen(
				"YellowGreen", 1, 192, 255, 0, 0.4, 0.5, 0.5, 1, 0, 10, 1), 
 /** The Lite green. */
 LiteGreen(
				"LiteGreen", 1, 100, 255, 100, 0.4, 0.5, 0.5, 1, 0, 10, 1), 
 /** The Lite yellow. */
 LiteYellow(
				"LiteYellow", 1, 255, 255, 100, 0.4, 0.5, 0.5, 1, 0, 10, 1), 
 /** The Dark gray. */
 DarkGray(
				"DarkGray", 1, 128, 128, 128, 0.4, 0.5, 0.5, 1, 0, 10, 1), 
 /** The Carbon. */
 Carbon(
				"Carbon", 1, 48, 48, 48, 0.8, 0.1, 1, 1, 0, 50, 1), 
 /** The Hydrogen. */
 Hydrogen(
				"Hydrogen", 1, 204, 201, 207, 0.4, 0.5, 0.7, 0.7, 1, 10, 0), 
 /** The Chrome. */
 Chrome(
				"Chrome", 1, 197, 207, 190, 0.4, 0.45, 0.49, 1, 0, 86, 1), 
 /** The Transparent. */
 Transparent(
				"Transparent", 1, 0, 0, 0, 0.8, 0.7, 0.7, 0.49, 1, 10, 1);
		
		/** The Name. */
		String Name;
		
		/** The kd. */
		double kd;
		
		/** The diffuse. */
		ColorQuad diffuse;
		
		/** The ka. */
		double ka;
		
		/** The ks. */
		double ks;
		
		/** The ke. */
		double ke;
		
		/** The filter. */
		double filter;
		
		/** The specularity. */
		int specularity;
		
		/** The use specularity. */
		boolean useSpecularity;
		
		/** The use filter. */
		boolean useFilter;

		/**
		 * Instantiates a new static material.
		 * 
		 * @param name
		 *            the name
		 * @param kd
		 *            the kd
		 * @param r
		 *            the r
		 * @param g
		 *            the g
		 * @param b
		 *            the b
		 * @param ka
		 *            the ka
		 * @param ks
		 *            the ks
		 * @param ke
		 *            the ke
		 * @param filter
		 *            the filter
		 * @param useFilter
		 *            the use filter
		 * @param specularity
		 *            the specularity
		 * @param useSpecularity
		 *            the use specularity
		 */
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

		/**
		 * Gets the material.
		 * 
		 * @return the material
		 */
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

	/** The static set. */
	static MaterialList staticSet;

	/**
	 * Instantiates a new material list.
	 */
	private MaterialList() {
		setUseByName(true);
		init();
/*		for (StaticMaterial p : StaticMaterial.values()) {
			add(p.getMaterial());
		}
*/
	}

	/** The initialized. */
	private boolean initialized = false;
	
	/**
	 * Initializes the.
	 */
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
	
	/**
	 * Gets the one.
	 * 
	 * @return the one
	 */
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
