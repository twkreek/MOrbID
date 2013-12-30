package com.bobandthomas.Morbid.molecule;

import com.bobandthomas.Morbid.graphics.MaterialList;

public enum Element
{
		
		H  ("H",1,1.18808,0,0,1,0,0,0.5,"Hydrogen"),
		He  ("He",2,-1,-1,0,1,0,0,0,"Black"),
		Li  ("Li",3,0.70238,0.70238,0,2,2,0,1.52,"LiteGray"),
		Be  ("Be",4,1.00421,1.00421,0,2,2,0,1.1,"LiteGray"),
		B  ("B",5,1.61171,1.55539,0,2,2,0,0.88,"Green"),
		C  ("C",6,1.80867,1.68512,0,2,2,0,0.77,"Carbon"),
		N  ("N",7,2.31541,2.15794,0,2,2,0,0.7,"Blue"),
		O  ("O",8,3.10803,2.52404,0,2,2,0,0.66,"Red"),
		F  ("F",9,3.77008,2.49467,0,2,2,0,0.64,"Yellow"),
		Ne  ("Ne",10,-1,-1,0,2,2,0,0,"Black"),
		Na  ("Na",11,-1,-1,0,3,3,0,1.86,"LiteGray"),
		Mg  ("Mg",12,-1,-1,0,3,3,0,1.6,"LiteGray"),
		Al  ("Al",13,1.51659,1.30635,1,3,3,0,1.43,"LiteGray"),
		Si  ("Si",14,1.8307,1.28495,1,3,3,0,1.17,"LiteGray"),
		P  ("P",15,1.98128,1.87515,1,3,3,0,1.1,"Yellow"),
		S  ("S",16,2.36652,1.66726,1,3,3,0,1.04,"Yellow"),
		Cl  ("Cl",17,3.63138,2.0768,1,3,3,0,0.99,"Green"),
		Ar  ("Ar",18,-1,-1,-1,3,3,0,0,"Black"),
		K  ("K",19,-1,-1,-1,4,4,3,2.31,"LiteGray"),
		Ca  ("Ca",20,-1,-1,-1,4,4,3,1.97,"LiteGray"),
		Sc  ("Sc",21,-1,-1,-1,4,4,3,1.6,"LiteGray"),
		Ti  ("Ti",22,-1,-1,-1,4,4,3,1.46,"LiteGray"),
		V  ("V",23,-1,-1,-1,4,4,3,1.31,"LiteGray"),
		Cr  ("Cr",24,-1,-1,-1,4,4,3,1.25,"Chrome"),
		Mn  ("Mn",25,-1,-1,-1,4,4,3,1.29,"LiteGray"),
		Fe  ("Fe",26,-1,-1,-1,4,4,3,1.26,"LiteGray"),
		Co  ("Co",27,-1,-1,-1,4,4,4,1.26,"LiteGray"),
		Ni  ("Ni",28,-1,-1,-1,4,4,4,1.24,"Chrome"),
		Cu  ("Cu",29,-1,-1,-1,4,4,4,1.28,"LiteGray"),
		Zn  ("Zn",30,1.9543,1.37237,1,4,4,4,1.33,"LiteGray"),
		Ga  ("Ga",31,-1,-1,1,4,4,4,1.22,"LiteGray"),
		Ge  ("Ge",32,-1,-1,1,4,4,4,1.22,"LiteGray"),
		LP  ("LP",255,0,0,0,0,0,0,0.2,"LiteBlue");
//ID,,Name,,ATNo,Zs,Zp,Zd,N1,N2,N3,radius,,material,
		public String name;
		public int typeid;
		double zS;
		double zP;
		double zD;
		double radius;
		int ns;
		int np;
		int nd;
		
		public int AtomicNumber;
		public String materialName;		
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