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
package com.bobandthomas.Morbid.utils;

// TODO: Auto-generated Javadoc
/**
 * The Class Dihedral.
 * 
 * @author Thomas Kreek
 */
public class Dihedral {

	/** The p4. */
	Point3D p1, p2, p3, p4;
	
	/** The v23. */
	Vector3D v12, v23;
	
	/** The v34. */
	Vector3D v34;
	
	
	/**
	 * Instantiates a new dihedral.
	 * 
	 * @param p1
	 *            the p1
	 * @param p2
	 *            the p2
	 * @param p3
	 *            the p3
	 * @param p4
	 *            the p4
	 */
	public Dihedral(Point3D p1, Point3D p2, Point3D p3, Point3D p4) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.p4 = p4;
		v12 = new Vector3D(p1.Sub(p2));
		v34 = new Vector3D(p3.Sub(p4));
		v23 = new Vector3D(p2.Sub(p3));
		
	}
	
      /**
		 * Dihedral.
		 * 
		 * @return the double
		 */
      public double dihedral(){
 
        // p1-p2-p3-p4 and we need dihedral around the p2-p3 bond
        // this is the angle between two planes: (p1 p2 p3) and (p2 p3 p4)
        Vector3D n1 = new Vector3D(v12.Cross( v23 )); // perpendicular to (p1 p2 p3) plane
        Vector3D n2 = new Vector3D(v34.Cross( v23 )); // perpendicular to (p2 p3 p4) plane

        double da = n1.angle( n2 ); // angle between two vectors perpendicular
                                    // to (p1 p2 p3) and (p2 p3 p4) planes,
                                    // respectively.
                                    // angle between the two vectors is equal
                                    // to the angle between the two planes

        return n1.Dot( v34 ) < 0 ? -da : da;

    }
    
    /**
	 * Angle123.
	 * 
	 * @return the double
	 */
    public double angle123()
    {
    	return v12.angle(v23);
    }
    
    /**
	 * Angle234.
	 * 
	 * @return the double
	 */
    public double angle234()
    {
    	return v23.angle(v34);
    }

}        
