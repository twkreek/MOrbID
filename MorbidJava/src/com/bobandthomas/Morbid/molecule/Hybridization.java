package com.bobandthomas.Morbid.molecule;

import com.bobandthomas.Morbid.utils.CLoadableItem;

public class Hybridization extends CLoadableItem {
	enum Geometry
	{
		SINGLE,
		LINEAR,
		TRIGONALPLANAR,
		TETRAHEDRAL,
		SQUAREPLANAR,
		TRIGONALBIPYRAMIDAL,
		OCTAHEDRAL;
	}
	enum HybridType
	{
		S (Geometry.SINGLE, 1, 0),
		SP (Geometry.LINEAR, 2, 0 ),
		SP2 (Geometry.TRIGONALPLANAR, 3, 0 ),
		SP3 (Geometry.TETRAHEDRAL, 4,1),
		SP2D (Geometry.SQUAREPLANAR, 4,2),
		SP3D(Geometry.TRIGONALBIPYRAMIDAL, 5, 2),
		SP3D2(Geometry.OCTAHEDRAL, 6, 2);
		
		int hydrogenCount;
		int lonePairCount;
		HybridType(Geometry geom, int hydrogenCount, int lonePairCount)
		{
			this.hydrogenCount = hydrogenCount;
			this.lonePairCount = lonePairCount;
		}
	};
	AtomType at;
	HybridType hybrid;	
	
	Hybridization(AtomType a, HybridType h)
	{
		at = a;
		hybrid = h;
	}

}
