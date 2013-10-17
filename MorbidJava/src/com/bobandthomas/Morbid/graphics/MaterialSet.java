package com.bobandthomas.Morbid.graphics;

import com.bobandthomas.Morbid.utils.CLoadableSet;

public class MaterialSet extends CLoadableSet<Material> {
	
	static MaterialSet staticSet;
	public MaterialSet()
	{	setUseByName(true);
		for (Material.StaticMaterial p : Material.StaticMaterial.values())
		{
			add(p.getMaterial());
		}
	}
	public static MaterialSet get()
	{
		if (staticSet == null) staticSet = new MaterialSet();
		return staticSet;
	}

}
