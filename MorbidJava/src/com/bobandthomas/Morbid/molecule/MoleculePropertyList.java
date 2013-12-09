package com.bobandthomas.Morbid.molecule;

import com.bobandthomas.Morbid.utils.CLoadableTable;

public class MoleculePropertyList extends CLoadableTable<MoleculeProperty> {

	
	public MoleculePropertyList() {
		this.setUseByName(true);
	}
	
	public void Add(String name, String value, String units)
	{
		add(new MoleculeProperty(name, value, units));
	}


}
