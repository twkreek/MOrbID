package com.bobandthomas.Morbid.molecule.data;

import com.bobandthomas.Morbid.utils.BoxType;
import com.bobandthomas.Morbid.utils.LineSegment;




import com.bobandthomas.Morbid.utils.CLoadableItem;

public class CopyOfCubeArray extends CLoadableItem{

	public CopyOfCubeArray(LineSegment scale, int resX, int resY, int resZ) {
	}

    public enum DRAWPLANE { planexy, planeyz, planexz};
  	private double[] m_hData;
    private double min, max; 

  	public double getMin() {
		return min;
	}
	public double getMax() {
		return max;
	}
	public double getSqMin() {
		return min*min;
	}
	public double getSqMax() {
		return max*max;
	}
	public int sideX, sideY, sideZ, sideXY;
  	public int currentSlice;
  	public DRAWPLANE currentPlane;

		private	BoxType bounds;
		public void SetBounds(BoxType box) { bounds = box; }
		public void ScaleBounds(double scale) { bounds = bounds.Scale(scale); }
		public double X(double i) { return (bounds.size().x * i) /sideX + bounds.getMin().x;} 
		public double Y(double i) { return (bounds.size().y * i) /sideY + bounds.getMin().y;}
		public double Z(double i) { return (bounds.size().z * i) /sideZ + bounds.getMin().z;}
		public double X(int i) { return (bounds.size().x * i) /sideX + bounds.getMin().x;} 
		public double Y(int i) { return (bounds.size().y * i) /sideY + bounds.getMin().y;}
		public double Z(int i) { return (bounds.size().z * i) /sideZ + bounds.getMin().z;}
  		public CopyOfCubeArray With(int slice) { currentSlice = slice; return this;}
  		public CopyOfCubeArray Plane(DRAWPLANE plane) { currentPlane = plane; return this; }
  		public double Slice(int i, int j)  // returns the double value based on the currentPlane and currentSlice
        { return (currentPlane == DRAWPLANE.planexy ? XY(i, j) : (currentPlane == DRAWPLANE.planeyz ? YZ(i, j) : ZX(i, j))); }
  		
  		public int Side0() // returns the length of the side the slice is in (considers currentPlane)
        { return (currentPlane == DRAWPLANE.planexy ? sideZ : (currentPlane == DRAWPLANE.planeyz ? sideX : sideY)); }
   					 			 
  		public int Side1()	// returns the length of the first side of the slice (considers currentPlane)
        { return (currentPlane == DRAWPLANE.planexy ? sideX : (currentPlane == DRAWPLANE.planeyz ? sideY : sideZ)); }
 					
  		public int Side2()	// returns the length of the Second
        { return (currentPlane == DRAWPLANE.planexy ? sideY : (currentPlane == DRAWPLANE.planeyz ? sideZ : sideX)); }
  		
  		public double XYZ(int i, int j, int k)
			{ return m_hData[i*sideXY + j*sideX + k]; }
  		public double XY(int i, int j) { return XYZ(i, j, currentSlice); }
  		public double YZ(int i, int j) { return XYZ(currentSlice, i, j); }
  		public double ZX(int i, int j) { return XYZ(j, currentSlice, i); }
  		
  		public double XYZSq(int i, int j, int k) { double f; f = XYZ(i,j,k); return f*f; }
  		public double XYSq(int i, int j) { return XYZSq(i, j, currentSlice); }
  		public double YZSq(int i, int j) { return XYZSq(currentSlice, i, j); }
  		public double ZXSq(int i, int j) { return XYZSq(j, currentSlice, i); }

		public void Set(int i, int j, int k, double f) 
        {
			if (f < min) min = f;
			if (f > max) max = f;
            m_hData[i*sideXY + j*sideX + k] = f; 
        }

        public void Resize(int x, int y, int z)
        {
            sideX = x;
            sideY = y;
            sideZ = z;
            sideXY = x*y;
            m_hData = new double[x*y*z];
        }
		protected void ResetMinMax()
		{
			min =100000;
			max =-100000;
		}
    }

