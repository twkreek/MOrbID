package com.bobandthomas.Morbid.molecule;

import java.util.ArrayList;

public class MoleculePropertyList extends ArrayList<MoleculeProperty> {

	public MoleculePropertyList() {
	}
	public void Add(String a, String b, String c)
	{
			MoleculeProperty m = new MoleculeProperty(a,b,c);
			add( m);
	}

}
