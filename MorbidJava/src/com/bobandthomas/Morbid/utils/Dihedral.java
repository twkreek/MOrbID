package com.bobandthomas.Morbid.utils;

public class Dihedral {

	Point3D p1, p2, p3, p4;
	Vector3D v12, v23;
	Vector3D v34;
	
	
	public Dihedral(Point3D p1, Point3D p2, Point3D p3, Point3D p4) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.p4 = p4;
		v12 = new Vector3D(p1.Sub(p2));
		v34 = new Vector3D(p3.Sub(p4));
		v23 = new Vector3D(p2.Sub(p3));
		
	}
	
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
    public double angle123()
    {
    	return v12.angle(v23);
    }
    public double angle234()
    {
    	return v23.angle(v34);
    }

}        
