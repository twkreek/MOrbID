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

import com.bobandthomas.Morbid.utils.CLoadableItem;

// TODO: Auto-generated Javadoc
/**
 * The Class Hybridization.
 * 
 * @author Thomas Kreek
 */
public class Hybridization extends CLoadableItem {
	
	/**
	 * The Enum Geometry.
	 * 
	 * @author Thomas Kreek
	 */
	enum Geometry
	{
		
		/** The single. */
		SINGLE,
		
		/** The linear. */
		LINEAR,
		
		/** The trigonalplanar. */
		TRIGONALPLANAR,
		
		/** The tetrahedral. */
		TETRAHEDRAL,
		
		/** The squareplanar. */
		SQUAREPLANAR,
		
		/** The trigonalbipyramidal. */
		TRIGONALBIPYRAMIDAL,
		
		/** The octahedral. */
		OCTAHEDRAL;
	}
	
	/**
	 * The Enum HybridType.
	 * 
	 * @author Thomas Kreek
	 */
	enum HybridType
	{
		
		/** The s. */
		S (Geometry.SINGLE, 1, 0),
		
		/** The sp. */
		SP (Geometry.LINEAR, 2, 0 ),
		
		/** The S p2. */
		SP2 (Geometry.TRIGONALPLANAR, 3, 0 ),
		
		/** The S p3. */
		SP3 (Geometry.TETRAHEDRAL, 4,1),
		
		/** The S p2 d. */
		SP2D (Geometry.SQUAREPLANAR, 4,2),
		
		/** The S p3 d. */
		SP3D(Geometry.TRIGONALBIPYRAMIDAL, 5, 2),
		
		/** The S p3 d2. */
		SP3D2(Geometry.OCTAHEDRAL, 6, 2);
		
		/** The hydrogen count. */
		int hydrogenCount;
		
		/** The lone pair count. */
		int lonePairCount;
		
		/**
		 * Instantiates a new hybrid type.
		 * 
		 * @param geom
		 *            the geom
		 * @param hydrogenCount
		 *            the hydrogen count
		 * @param lonePairCount
		 *            the lone pair count
		 */
		HybridType(Geometry geom, int hydrogenCount, int lonePairCount)
		{
			this.hydrogenCount = hydrogenCount;
			this.lonePairCount = lonePairCount;
		}
	};
	
	/** The at. */
	AtomType at;
	
	/** The hybrid. */
	HybridType hybrid;	
	
	/**
	 * Instantiates a new hybridization.
	 * 
	 * @param a
	 *            the a
	 * @param h
	 *            the h
	 */
	Hybridization(AtomType a, HybridType h)
	{
		at = a;
		hybrid = h;
	}

}
