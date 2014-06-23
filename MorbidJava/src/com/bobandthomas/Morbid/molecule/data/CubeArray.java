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
package com.bobandthomas.Morbid.molecule.data;

import com.bobandthomas.Morbid.utils.BoundingBox;
import com.bobandthomas.Morbid.utils.MinMax;
import com.bobandthomas.Morbid.utils.Point3D;

import toxi.geom.Vec3D;
import toxi.volume.VolumetricSpaceArray;







import com.bobandthomas.Morbid.utils.CLoadableItem;

// TODO: Auto-generated Javadoc
/**
 * The Class CubeArray.
 * 
 * @author Thomas Kreek
 */
public class CubeArray extends CLoadableItem {
	
	/** The scaler. */
	toxi.geom.Vec3D scaler = new toxi.geom.Vec3D(1.0f, 1.0f, 1.0f);
	
	/** The side xy. */
	public int sideX, sideY, sideZ, sideXY;
	
	/** The size. */
	public int size;
    
    /**
	 * Gets the size.
	 * 
	 * @return the size
	 */
    public int getSize() {
		return size;
	}

	/**
	 * Sets the size.
	 * 
	 * @param size
	 *            the new size
	 */
	public void setSize(int size) {
		this.size = size;
		markDirty();
	}
	
	/** The minmax. */
	public MinMax minmax = new MinMax();
    

	/**
	 * Instantiates a new cube array.
	 * 
	 * @param scale
	 *            the scale
	 * @param res
	 *            the res
	 */
	public CubeArray(Point3D scale, int res) {
		size = res;
		this.sideX = res;
		this.sideY = res;
		this.sideZ = res;
		bounds = BoundingBox.UnitBox();
		scaler = new toxi.geom.Vec3D((float)scale.x, (float)scale.y, (float)scale.z);
//		volume = new VolumetricSpaceArray(scaler , sideX, sideY, sideZ);
	}
	
	/**
	 * Instantiates a new cube array.
	 * 
	 * @param sides
	 *            the sides
	 */
	public CubeArray( int sides)
	{
		this(new Point3D(1.0,1.0,1.0), sides);
		}
	
	/**
	 * Instantiates a new cube array.
	 */
	public CubeArray()
	{
		this(10);
	}
	
	/** The volume. */
	VolumetricSpaceArray volume;
	
	/**
	 * Gets the volume.
	 * 
	 * @return the volume
	 */
	public VolumetricSpaceArray getVolume()
	{
		return volume;
	}
	
    /**
	 * The Enum DRAWPLANE.
	 * 
	 * @author Thomas Kreek
	 */
    public enum DRAWPLANE { /** The planexy. */
 planexy, /** The planeyz. */
 planeyz, /** The planexz. */
 planexz};


    /**
	 * Gets the min max.
	 * 
	 * @return the min max
	 */
    public MinMax getMinMax()
    {
    	return minmax;
    }
  	
	  /**
		 * Gets the min.
		 * 
		 * @return the min
		 */
	  public double getMin() {
		return minmax.min;
	}
	
	/**
	 * Gets the max.
	 * 
	 * @return the max
	 */
	public double getMax() {
		return minmax.max;
	}
	
	/**
	 * Gets the sq min.
	 * 
	 * @return the sq min
	 */
	public double getSqMin() {
		return minmax.min*minmax.min;
	}
	
	/**
	 * Gets the sq max.
	 * 
	 * @return the sq max
	 */
	public double getSqMax() {
		return minmax.max*minmax.max;
	}

   	/** The current slice. */
	   public int currentSlice;
 
  	/** The current plane. */
	  public DRAWPLANE currentPlane;

		/** The bounds. */
		private	BoundingBox bounds = new BoundingBox();
		
		/**
		 * Sets the bounds.
		 * 
		 * @param box
		 *            the box
		 */
		public void SetBounds(BoundingBox box) { 
			bounds = box; 
			scaler = new Vec3D((float) bounds.size().x, (float) bounds.size().y, (float) bounds.size().z);
			markDirty();
		}
		
		/**
		 * Scale bounds.
		 * 
		 * @param scale
		 *            the scale
		 */
		public void ScaleBounds(double scale) { bounds = bounds.Scale(scale); }
		
		/**
		 * X.
		 * 
		 * @param i
		 *            the i
		 * @return the double
		 */
		public double X(double i) { return (bounds.size().x * i) /sideX + bounds.getMin().x;} 
		
		/**
		 * Y.
		 * 
		 * @param i
		 *            the i
		 * @return the double
		 */
		public double Y(double i) { return (bounds.size().y * i) /sideY + bounds.getMin().y;}
		
		/**
		 * Z.
		 * 
		 * @param i
		 *            the i
		 * @return the double
		 */
		public double Z(double i) { return (bounds.size().z * i) /sideZ + bounds.getMin().z;}
		
		/**
		 * X.
		 * 
		 * @param i
		 *            the i
		 * @return the double
		 */
		public double X(int i) { return (bounds.size().x * i) /sideX + bounds.getMin().x;} 
		
		/**
		 * Y.
		 * 
		 * @param i
		 *            the i
		 * @return the double
		 */
		public double Y(int i) { return (bounds.size().y * i) /sideY + bounds.getMin().y;}
		
		/**
		 * Z.
		 * 
		 * @param i
		 *            the i
		 * @return the double
		 */
		public double Z(int i) { return (bounds.size().z * i) /sideZ + bounds.getMin().z;}
  		
		  /**
			 * With.
			 * 
			 * @param slice
			 *            the slice
			 * @return the cube array
			 */
		  public CubeArray With(int slice) { currentSlice = slice; return this;}
  		
		  /**
			 * Plane.
			 * 
			 * @param plane
			 *            the plane
			 * @return the cube array
			 */
		  public CubeArray Plane(DRAWPLANE plane) { currentPlane = plane; return this; }
  		
		  /**
			 * Slice.
			 * 
			 * @param i
			 *            the i
			 * @param j
			 *            the j
			 * @return the double
			 */
		  public double Slice(int i, int j)  // returns the double value based on the currentPlane and currentSlice
        { return (currentPlane == DRAWPLANE.planexy ? XY(i, j) : (currentPlane == DRAWPLANE.planeyz ? YZ(i, j) : ZX(i, j))); }
  		
  		/**
			 * Side0.
			 * 
			 * @return the int
			 */
		  public int Side0() // returns the length of the side the slice is in (considers currentPlane)
        { return (currentPlane == DRAWPLANE.planexy ? sideZ : (currentPlane == DRAWPLANE.planeyz ? sideX : sideY)); }
   					 			 
  		/**
			 * Side1.
			 * 
			 * @return the int
			 */
		  public int Side1()	// returns the length of the first side of the slice (considers currentPlane)
        { return (currentPlane == DRAWPLANE.planexy ? sideX : (currentPlane == DRAWPLANE.planeyz ? sideY : sideZ)); }
 					
  		/**
			 * Side2.
			 * 
			 * @return the int
			 */
		  public int Side2()	// returns the length of the Second
        { return (currentPlane == DRAWPLANE.planexy ? sideY : (currentPlane == DRAWPLANE.planeyz ? sideZ : sideX)); }
  		
  		/**
			 * Xyz.
			 * 
			 * @param i
			 *            the i
			 * @param j
			 *            the j
			 * @param k
			 *            the k
			 * @return the double
			 */
		  public double XYZ(int i, int j, int k)
			{ return volume.getVoxelAt(i,j,k);}
  		
		  /**
			 * Xy.
			 * 
			 * @param i
			 *            the i
			 * @param j
			 *            the j
			 * @return the double
			 */
		  public double XY(int i, int j) { return XYZ(i, j, currentSlice); }
  		
		  /**
			 * Yz.
			 * 
			 * @param i
			 *            the i
			 * @param j
			 *            the j
			 * @return the double
			 */
		  public double YZ(int i, int j) { return XYZ(currentSlice, i, j); }
  		
		  /**
			 * Zx.
			 * 
			 * @param i
			 *            the i
			 * @param j
			 *            the j
			 * @return the double
			 */
		  public double ZX(int i, int j) { return XYZ(j, currentSlice, i); }
  		
  		/**
			 * XYZ sq.
			 * 
			 * @param i
			 *            the i
			 * @param j
			 *            the j
			 * @param k
			 *            the k
			 * @return the double
			 */
		  public double XYZSq(int i, int j, int k) { double f; f = XYZ(i,j,k); return f*f; }
  		
		  /**
			 * XY sq.
			 * 
			 * @param i
			 *            the i
			 * @param j
			 *            the j
			 * @return the double
			 */
		  public double XYSq(int i, int j) { return XYZSq(i, j, currentSlice); }
  		
		  /**
			 * YZ sq.
			 * 
			 * @param i
			 *            the i
			 * @param j
			 *            the j
			 * @return the double
			 */
		  public double YZSq(int i, int j) { return XYZSq(currentSlice, i, j); }
  		
		  /**
			 * ZX sq.
			 * 
			 * @param i
			 *            the i
			 * @param j
			 *            the j
			 * @return the double
			 */
		  public double ZXSq(int i, int j) { return XYZSq(j, currentSlice, i); }


		/**
		 * Sets the.
		 * 
		 * @param i
		 *            the i
		 * @param j
		 *            the j
		 * @param k
		 *            the k
		 * @param f
		 *            the f
		 */
		public void Set(int i, int j, int k, double f) 
        {
			minmax.addValue(f);
            volume.setVoxelAt(i, j, k, (float) f);  
        }
		
		/**
		 * Adds the.
		 * 
		 * @param i
		 *            the i
		 * @param j
		 *            the j
		 * @param k
		 *            the k
		 * @param f
		 *            the f
		 */
		public void Add(int i, int j, int k, double f) 
        {
			minmax.addValue(f);
            volume.setVoxelAt(i, j, k, (float) (f+volume.getVoxelAt(i,j,k)));  
        }

		/**
		 * Gets the.
		 * 
		 * @param i
		 *            the i
		 * @param j
		 *            the j
		 * @param k
		 *            the k
		 * @return the double
		 */
		public double Get(int i, int j, int k) 
        {
	          return volume.getVoxelAt(i, j, k);  
        }
		
		/**
		 * Gets the point.
		 * 
		 * @param i
		 *            the i
		 * @param j
		 *            the j
		 * @param k
		 *            the k
		 * @return the point
		 */
		public Point3D getPoint(int i, int j, int k)
		{
			return new Point3D(X(i), Y(j), Z(k));
		}
		
		/**
		 * Gets the closest value.
		 * 
		 * @param p
		 *            the p
		 * @return the closest value
		 */
		public double getClosestValue(Point3D p)
		{
			int xIndex = (int) Math.floor((p.x - bounds.getMin().x)/(bounds.getMax().x-bounds.getMin().x) * sideX);
			int yIndex = (int) Math.floor((p.y - bounds.getMin().y)/(bounds.getMax().y-bounds.getMin().y) * sideY);
			int zIndex = (int) Math.floor((p.z - bounds.getMin().z)/(bounds.getMax().z-bounds.getMin().z) * sideZ);
			return XYZ(xIndex, yIndex, zIndex);
		}

        /**
		 * Resize.
		 */
        public void Resize()
        {
        	if (volume != null && size == sideX)
        		return;
            sideX = size;
            sideY = size;
            sideZ = size;
            sideXY = size * size;
            volume = new VolumetricSpaceArray(scaler, sideX, sideY, sideZ);
			volume.setScale(scaler);
			markDirty();
        }
		
		/**
		 * Reset min max.
		 */
		protected void ResetMinMax()
		{
			minmax.reset();
		}
    }

