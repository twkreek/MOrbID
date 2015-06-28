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
package com.bobandthomas.Morbid.molecule;

import com.bobandthomas.Morbid.graphics.MaterialList;

// TODO: Auto-generated Javadoc
/**
 * The Enum Element.
 * 
 * @author Thomas Kreek
 */
public enum Element
{
		
		/** The h. */
		H  ("H",1,1.18808,0,0,1,0,0,0.5,"Hydrogen"),
		
		/** The He. */
		He  ("He",2,-1,-1,0,1,0,0,0,"Black"),
		
		/** The Li. */
		Li  ("Li",3,0.70238,0.70238,0,2,2,0,1.52,"LiteGray"),
		
		/** The Be. */
		Be  ("Be",4,1.00421,1.00421,0,2,2,0,1.1,"LiteGray"),
		
		/** The b. */
		B  ("B",5,1.61171,1.55539,0,2,2,0,0.88,"Green"),
		
		/** The c. */
		C  ("C",6,1.80867,1.68512,0,2,2,0,0.77,"Carbon"),
		
		/** The n. */
		N  ("N",7,2.31541,2.15794,0,2,2,0,0.7,"Blue"),
		
		/** The o. */
		O  ("O",8,3.10803,2.52404,0,2,2,0,0.66,"Red"),
		
		/** The f. */
		F  ("F",9,3.77008,2.49467,0,2,2,0,0.64,"Yellow"),
		
		/** The Ne. */
		Ne  ("Ne",10,-1,-1,0,2,2,0,0,"Black"),
		
		/** The Na. */
		Na  ("Na",11,-1,-1,0,3,3,0,1.86,"LiteGray"),
		
		/** The Mg. */
		Mg  ("Mg",12,-1,-1,0,3,3,0,1.6,"LiteGray"),
		
		/** The Al. */
		Al  ("Al",13,1.51659,1.30635,1,3,3,0,1.43,"LiteGray"),
		
		/** The Si. */
		Si  ("Si",14,1.8307,1.28495,1,3,3,0,1.17,"LiteGray"),
		
		/** The p. */
		P  ("P",15,1.98128,1.87515,1,3,3,0,1.1,"Yellow"),
		
		/** The s. */
		S  ("S",16,2.36652,1.66726,1,3,3,0,1.04,"Yellow"),
		
		/** The Cl. */
		Cl  ("Cl",17,3.63138,2.0768,1,3,3,0,0.99,"Green"),
		
		/** The Ar. */
		Ar  ("Ar",18,-1,-1,-1,3,3,0,0,"Black"),
		
		/** The k. */
		K  ("K",19,-1,-1,-1,4,4,3,2.31,"LiteGray"),
		
		/** The Ca. */
		Ca  ("Ca",20,-1,-1,-1,4,4,3,1.97,"LiteGray"),
		
		/** The Sc. */
		Sc  ("Sc",21,-1,-1,-1,4,4,3,1.6,"LiteGray"),
		
		/** The Ti. */
		Ti  ("Ti",22,-1,-1,-1,4,4,3,1.46,"LiteGray"),
		
		/** The v. */
		V  ("V",23,-1,-1,-1,4,4,3,1.31,"LiteGray"),
		
		/** The Cr. */
		Cr  ("Cr",24,-1,-1,-1,4,4,3,1.25,"Chrome"),
		
		/** The Mn. */
		Mn  ("Mn",25,-1,-1,-1,4,4,3,1.29,"LiteGray"),
		
		/** The Fe. */
		Fe  ("Fe",26,-1,-1,-1,4,4,3,1.26,"LiteGray"),
		
		/** The Co. */
		Co  ("Co",27,-1,-1,-1,4,4,4,1.26,"LiteGray"),
		
		/** The Ni. */
		Ni  ("Ni",28,-1,-1,-1,4,4,4,1.24,"Chrome"),
		
		/** The Cu. */
		Cu  ("Cu",29,-1,-1,-1,4,4,4,1.28,"LiteGray"),
		
		/** The Zn. */
		Zn  ("Zn",30,1.9543,1.37237,1,4,4,4,1.33,"LiteGray"),
		
		/** The Ga. */
		Ga  ("Ga",31,-1,-1,1,4,4,4,1.22,"LiteGray"),
		
		/** The Ge. */
		Ge  ("Ge",32,-1,-1,1,4,4,4,1.22,"LiteGray"),
		
		/** The lp. */
		LP  ("LP",255,0,0,0,0,0,0,0.2,"LiteBlue");
//ID,,Name,,ATNo,Zs,Zp,Zd,N1,N2,N3,radius,,material,
		/** The name. */
public String name;
		
		/** The typeid. */
		public int typeid;
		
		/** The z s. */
		double zS;
		
		/** The z p. */
		double zP;
		
		/** The z d. */
		double zD;
		
		/** The radius. */
		double radius;
		
		/** The ns. */
		int ns;
		
		/** The np. */
		int np;
		
		/** The nd. */
		int nd;
		
		/** The Atomic number. */
		public int AtomicNumber;
		
		/** The material name. */
		public String materialName;		
		
		/**
		 * Instantiates a new element.
		 * 
		 * @param Name
		 *            the name
		 * @param atNo
		 *            the at no
		 * @param Zs
		 *            the zs
		 * @param Zp
		 *            the zp
		 * @param Zd
		 *            the zd
		 * @param N1
		 *            the n1
		 * @param N2
		 *            the n2
		 * @param N3
		 *            the n3
		 * @param rad
		 *            the rad
		 * @param material
		 *            the material
		 */
		Element(String Name, int atNo, double Zs, double Zp, double Zd, int N1, int N2, int N3, double rad, String material )
		{
			name = Name;
			typeid = atNo;
			AtomicNumber = atNo;
			zS = Zs;
			zP = Zp;
			zD = Zd;
			ns = N1;
			np = N2;
			nd = N3;
			radius = rad;
			materialName = material;
			
		}
		
		/**
		 * Gets the atom type.
		 * 
		 * @return the atom type
		 */
		AtomType getAtomType()
		{
			
			AtomType at = new AtomType();
			at.setName(name);
			at.SetAtomicNumber(typeid);
			at.setID(typeid);
			at.zS = zS;
			at.zP = zP;
			at.zD = zD;
			at.ns = ns;
			at.np = np;
			at.nd = nd;
			at.radius = radius;
			at.mat = MaterialList.getOne().getByName(materialName);
			at.color = at.mat.getColor();
			return at;
		}

}