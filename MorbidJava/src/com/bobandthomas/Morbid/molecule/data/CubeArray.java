package com.bobandthomas.Morbid.molecule.data;

import com.bobandthomas.Morbid.utils.BoxType;
import com.bobandthomas.Morbid.utils.MinMax;
import com.bobandthomas.Morbid.utils.Point3D;

import toxi.geom.Vec3D;
import toxi.volume.VolumetricSpaceArray;







import com.bobandthomas.Morbid.utils.CLoadableItem;

public class CubeArray extends CLoadableItem {
	
	toxi.geom.Vec3D scaler = new toxi.geom.Vec3D(1.0f, 1.0f, 1.0f);
	public int sideX, sideY, sideZ, sideXY;
    public MinMax minmax = new MinMax();
    

	public CubeArray(Point3D scale, int resX, int resY, int resZ) {
		this.sideX = resX;
		this.sideY = resY;
		this.sideZ = resZ;
		bounds = BoxType.UnitBox();
		scaler = new toxi.geom.Vec3D((float)scale.x, (float)scale.y, (float)scale.z);
		volume = new VolumetricSpaceArray(scaler , resX, resY, resZ);
	}
	
	public CubeArray( int sides)
	{
		this(new Point3D(1.0,1.0,1.0), sides, sides, sides);
		markDirty();
	}
	public CubeArray()
	{
		this(10);
		markDirty();
	}
	VolumetricSpaceArray volume;
	public VolumetricSpaceArray getVolume()
	{
		return volume;
	}
	
    public enum DRAWPLANE { planexy, planeyz, planexz};


    public MinMax getMinMax()
    {
    	return minmax;
    }
  	public double getMin() {
		return minmax.min;
	}
	public double getMax() {
		return minmax.max;
	}
	public double getSqMin() {
		return minmax.min*minmax.min;
	}
	public double getSqMax() {
		return minmax.max*minmax.max;
	}

   	public int currentSlice;
 
  	public DRAWPLANE currentPlane;

		private	BoxType bounds = new BoxType();
		public void SetBounds(BoxType box) { 
			bounds = box; 
			scaler = new Vec3D((float) bounds.size().x, (float) bounds.size().y, (float) bounds.size().z);
			volume.setScale(scaler);
		}
		public void ScaleBounds(double scale) { bounds = bounds.Scale(scale); }
		public double X(double i) { return (bounds.size().x * i) /sideX + bounds.getMin().x;} 
		public double Y(double i) { return (bounds.size().y * i) /sideY + bounds.getMin().y;}
		public double Z(double i) { return (bounds.size().z * i) /sideZ + bounds.getMin().z;}
		public double X(int i) { return (bounds.size().x * i) /sideX + bounds.getMin().x;} 
		public double Y(int i) { return (bounds.size().y * i) /sideY + bounds.getMin().y;}
		public double Z(int i) { return (bounds.size().z * i) /sideZ + bounds.getMin().z;}
  		public CubeArray With(int slice) { currentSlice = slice; return this;}
  		public CubeArray Plane(DRAWPLANE plane) { currentPlane = plane; return this; }
  		public double Slice(int i, int j)  // returns the double value based on the currentPlane and currentSlice
        { return (currentPlane == DRAWPLANE.planexy ? XY(i, j) : (currentPlane == DRAWPLANE.planeyz ? YZ(i, j) : ZX(i, j))); }
  		
  		public int Side0() // returns the length of the side the slice is in (considers currentPlane)
        { return (currentPlane == DRAWPLANE.planexy ? sideZ : (currentPlane == DRAWPLANE.planeyz ? sideX : sideY)); }
   					 			 
  		public int Side1()	// returns the length of the first side of the slice (considers currentPlane)
        { return (currentPlane == DRAWPLANE.planexy ? sideX : (currentPlane == DRAWPLANE.planeyz ? sideY : sideZ)); }
 					
  		public int Side2()	// returns the length of the Second
        { return (currentPlane == DRAWPLANE.planexy ? sideY : (currentPlane == DRAWPLANE.planeyz ? sideZ : sideX)); }
  		
  		public double XYZ(int i, int j, int k)
			{ return volume.getVoxelAt(i,j,k);}
  		public double XY(int i, int j) { return XYZ(i, j, currentSlice); }
  		public double YZ(int i, int j) { return XYZ(currentSlice, i, j); }
  		public double ZX(int i, int j) { return XYZ(j, currentSlice, i); }
  		
  		public double XYZSq(int i, int j, int k) { double f; f = XYZ(i,j,k); return f*f; }
  		public double XYSq(int i, int j) { return XYZSq(i, j, currentSlice); }
  		public double YZSq(int i, int j) { return XYZSq(currentSlice, i, j); }
  		public double ZXSq(int i, int j) { return XYZSq(j, currentSlice, i); }


		public void Set(int i, int j, int k, double f) 
        {
			minmax.addValue(f);
            volume.setVoxelAt(i, j, k, (float) f);  
        }
		
		public double getClosestValue(Point3D p)
		{
			int xIndex = (int) Math.floor((p.x - bounds.getMin().x)/(bounds.getMax().x-bounds.getMin().x) * sideX);
			int yIndex = (int) Math.floor((p.y - bounds.getMin().y)/(bounds.getMax().y-bounds.getMin().y) * sideY);
			int zIndex = (int) Math.floor((p.z - bounds.getMin().z)/(bounds.getMax().z-bounds.getMin().z) * sideZ);
			return XYZ(xIndex, yIndex, zIndex);
		}

        public void Resize(int x, int y, int z)
        {
            sideX = x;
            sideY = y;
            sideZ = z;
            sideXY = x*y;
            volume = new VolumetricSpaceArray(scaler, x,y,z);
            markDirty();
        }
		protected void ResetMinMax()
		{
			minmax.reset();
		}
    }

