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

import javax.vecmath.Point3d;

// TODO: Auto-generated Javadoc
/**
 * The Class SlaterOrbitals.
 * 
 * @author Thomas Kreek
 */
public class SlaterOrbitals {

	/**
	 * Instantiates a new slater orbitals.
	 */
	public SlaterOrbitals() {
	}
		
		/** The Pi. */
		static float Pi 		=3.14159265359f;
		
		/** The sqrt pi. */
		static float sqrtPi 	=1.7724539f;
		
		/** The sqrt2. */
		static float sqrt2  	=1.4142135f;
		
		/** The sqrt3. */
		static float sqrt3  	=1.7320508f;
		
		/** The sqrt5. */
		static float sqrt5  	=2.23260679f;
		
		/** The sqrt7. */
		static float sqrt7  	=2.6457513f;
		
		/** The a0. */
		static float a0 	   	=0.529177f;
		
		/**
		 * R.
		 * 
		 * @param x
		 *            the x
		 * @param y
		 *            the y
		 * @param z
		 *            the z
		 * @return the double
		 */
		static double r(double x, double y, double z) { return Math.sqrt( (x)*(x)+ (y)*(y) +(z)*(z) );}
		
		/**
		 * Exp.
		 * 
		 * @param r
		 *            the r
		 * @return the double
		 */
		static double exp(double r) { return Math.exp(r); }
		
		/**
		 * R1 s.
		 * 
		 * @param rad
		 *            the rad
		 * @return the double
		 */
		static double  /*SlaterOrbitals */R1S(double rad) {
				double value;

				value = 2*exp(-rad);
				return value;
		}

		/**
		 * Ys.
		 * 
		 * @param x
		 *            the x
		 * @param y
		 *            the y
		 * @param z
		 *            the z
		 * @param rad
		 *            the rad
		 * @return the double
		 */
		static double  /*SlaterOrbitals */YS(double x, double y, double z, double rad) {
				double value;

				value = 1/(2*sqrtPi);
				return value;
		}

		/**
		 * R2 s.
		 * 
		 * @param rad
		 *            the rad
		 * @return the double
		 */
		static double  /*SlaterOrbitals */R2S(double rad) {
				double value;

				value = 1/(2*sqrt2)*(rad-2)*exp(-rad/2);
				return value;
		}

		/**
		 * R2 p.
		 * 
		 * @param rad
		 *            the rad
		 * @return the double
		 */
		static double  /*SlaterOrbitals */R2P (double rad){
				double value;

				value = 1/(2*sqrt2*sqrt3)*rad*exp(-rad/2);
				return value;
		}

		/**
		 * Yp.
		 * 
		 * @param L
		 *            the l
		 * @param p
		 *            the p
		 * @param rad
		 *            the rad
		 * @return the double
		 */
		static double YP(int L, Point3d p, double rad)
		{
			switch (L)
			{
			case -1: return YPx(p.x, p.y, p.z, rad);
			case 0: return YPy(p.x, p.y, p.z, rad);
			case 1: return YPz(p.x, p.y, p.z, rad);
			}
			return 0;
		}
		
		/**
		 * Y px.
		 * 
		 * @param x
		 *            the x
		 * @param y
		 *            the y
		 * @param z
		 *            the z
		 * @param rad
		 *            the rad
		 * @return the double
		 */
		static double  /*SlaterOrbitals */YPx(double x, double y, double z, double rad) {
				if (rad < 1.0e-15) return 0.0F;
				return sqrt3*(x/rad)/2*sqrtPi;
		}

		/**
		 * Y py.
		 * 
		 * @param x
		 *            the x
		 * @param y
		 *            the y
		 * @param z
		 *            the z
		 * @param rad
		 *            the rad
		 * @return the double
		 */
		static double  /*SlaterOrbitals */YPy(double x, double y, double z,double rad) {
				double value;

				if (rad < 1.0e-15) return 0.0F;
				value = sqrt3*(y/rad)/2*sqrtPi;
				return value;
		}

		/**
		 * Y pz.
		 * 
		 * @param x
		 *            the x
		 * @param y
		 *            the y
		 * @param z
		 *            the z
		 * @param rad
		 *            the rad
		 * @return the double
		 */
		static double  /*SlaterOrbitals */YPz(double x, double y, double z, double rad) {
				if (rad < 1.0e-15) return 0.0F;
				return sqrt3*(z/rad)/2*sqrtPi;
		}

		/**
		 * R3 s.
		 * 
		 * @param rad
		 *            the rad
		 * @return the double
		 */
		static double  /*SlaterOrbitals */R3S(double rad) {
				double value;

				value = 2/(81*sqrt3)* (27-18*rad+ 2 * rad * rad)*exp(-rad/3);
				return value;
		}

		/**
		 * R3 p.
		 * 
		 * @param rad
		 *            the rad
		 * @return the double
		 */
		static double  /*SlaterOrbitals */R3P(double rad) {
				double  value;

				value = -4/(81*sqrt3*sqrt2)* (rad*rad - 6*rad)*exp(-rad/3);
				return value;
		}

		/**
		 * R3 d.
		 * 
		 * @param rad
		 *            the rad
		 * @return the double
		 */
		static double  /*SlaterOrbitals */R3D(double rad) {
				double value;

				value = 4/(81*sqrt3*sqrt2*sqrt5) *rad*rad *exp(-rad/3);
				return value;
		}
		
		/**
		 * R4 s.
		 * 
		 * @param rad
		 *            the rad
		 * @return the double
		 */
		static double  /*SlaterOrbitals */R4S(double rad) {
				double value;
				value = (192 - 144*rad + 24*rad*rad - rad*rad*rad) *exp(-rad/4)/768;
				return value;
		}
		
		/**
		 * R4 p.
		 * 
		 * @param rad
		 *            the rad
		 * @return the double
		 */
		static double  /*SlaterOrbitals */R4P(double rad) {
				double value;
				value = (80+20*rad+rad*rad)* exp(-rad/4)/(256*sqrt3*sqrt5);
				return value;
		}
		
		/**
		 * R4 d.
		 * 
		 * @param rad
		 *            the rad
		 * @return the double
		 */
		static double  /*SlaterOrbitals */R4D(double rad) {
				double value;
				value = (12-rad)*rad*rad*exp(-rad/4)/(768*sqrt5);
				return value;
		}

		/**
		 * Y dx2y2.
		 * 
		 * @param x
		 *            the x
		 * @param y
		 *            the y
		 * @param z
		 *            the z
		 * @param rad
		 *            the rad
		 * @return the double
		 */
		static double  /*SlaterOrbitals */YDx2y2(double x, double y, double z, double rad) {
				if (rad < 1.0e-15) return 0.0F;
				return sqrt3*sqrt5 * (x*x-y*y)/(rad*rad)/(4*sqrtPi);
		}

		/**
		 * Y dxz.
		 * 
		 * @param x
		 *            the x
		 * @param y
		 *            the y
		 * @param z
		 *            the z
		 * @param rad
		 *            the rad
		 * @return the double
		 */
		static double  /*SlaterOrbitals */YDxz(double x, double y, double z,double rad) {
				if (rad < 1.0e-15) return 0.0F;
				return sqrt2*sqrt3*sqrt5*(x*z/(rad*rad))/(2*sqrt2*sqrtPi);
		}


		/**
		 * Y dz2.
		 * 
		 * @param x
		 *            the x
		 * @param y
		 *            the y
		 * @param z
		 *            the z
		 * @param rad
		 *            the rad
		 * @return the double
		 */
		static double  /*SlaterOrbitals */YDz2(double x, double y, double z, double rad) {
				if (rad < 1.0e-15) return 0.0F;
				return sqrt5*(3*z*z-rad*rad)/(rad*rad) /(4*sqrtPi);
		}

		/**
		 * Y dyz.
		 * 
		 * @param x
		 *            the x
		 * @param y
		 *            the y
		 * @param z
		 *            the z
		 * @param rad
		 *            the rad
		 * @return the double
		 */
		static double  /*SlaterOrbitals */YDyz(double x, double y, double z,double rad) {
				if (rad < 1.0e-15) return 0.0F;
				return sqrt2*sqrt3*sqrt5*(y*z)/(rad*rad) /(2*sqrt2*sqrtPi);
		}

		/**
		 * Y dxy.
		 * 
		 * @param x
		 *            the x
		 * @param y
		 *            the y
		 * @param z
		 *            the z
		 * @param rad
		 *            the rad
		 * @return the double
		 */
		static double  /*SlaterOrbitals */YDxy(double x, double y, double z, double rad) {
				if (rad < 1.0e-15) return 0.0F;
				return sqrt3*sqrt5*(x*y)/(rad*rad) /(2*sqrt2*sqrtPi);
		}
		
		/**
		 * Sto.
		 * 
		 * @param n
		 *            the n
		 * @param zeta
		 *            the zeta
		 * @param rad
		 *            the rad
		 * @return the double
		 */
		static double  /*SlaterOrbitals */STO(int n, double zeta, double rad) {
				double value,temp1;
				int i;
				temp1 = 2*zeta;
				for (i=1; i<n; i++)
					temp1 *= 2*zeta;
				temp1 *= Math.sqrt(2*zeta);
				value = temp1;
				temp1 = 2*n;
				for (i=(2*n-1); i>0; i--) 
					temp1*= i;
				value *= temp1*Math.pow(rad,n-1)*exp(-zeta*rad);
				return value;
		}
		
		/**
		 * R1 sto.
		 * 
		 * @param zeta
		 *            the zeta
		 * @param rad
		 *            the rad
		 * @return the double
		 */
		static double  /*SlaterOrbitals */R1STO(double zeta, double rad) {
				double value;

				value = 2*zeta*Math.sqrt(zeta)*exp(-zeta*rad);
				return value;
		}

		/**
		 * R2 sto.
		 * 
		 * @param zeta
		 *            the zeta
		 * @param rad
		 *            the rad
		 * @return the double
		 */
		static double  /*SlaterOrbitals */R2STO(double zeta, double rad) {
				double value;

				value = 2/sqrt3*zeta*zeta*Math.sqrt(zeta) * rad * exp(-zeta*rad);
				return value;
		}

		/**
		 * R3 sto.
		 * 
		 * @param zeta
		 *            the zeta
		 * @param rad
		 *            the rad
		 * @return the double
		 */
		static double  /*SlaterOrbitals */R3STO(double zeta, double rad) {
				double value;

				value = 2*zeta*zeta*zeta*Math.sqrt(zeta)/(3*sqrt5) * sqrt2 * rad*rad * exp(-zeta*rad);
				return value;
		}
		
		/**
		 * R4 sto.
		 * 
		 * @param zeta
		 *            the zeta
		 * @param rad
		 *            the rad
		 * @return the double
		 */
		static double  /*SlaterOrbitals */R4STO(double zeta, double rad) {
				double value;

				value = 2/(3*sqrt5*sqrt7) * 
					zeta*zeta*zeta*zeta*Math.sqrt(zeta) * 
					rad*rad*rad *exp(-zeta*rad);
				return value;
		}
		
		/**
		 * Contracted_ r3.
		 * 
		 * @param zeta1
		 *            the zeta1
		 * @param zeta2
		 *            the zeta2
		 * @param coeff1
		 *            the coeff1
		 * @param coeff2
		 *            the coeff2
		 * @param rad
		 *            the rad
		 * @return the double
		 */
		static double  /*SlaterOrbitals */contracted_R3 ( double zeta1, double zeta2, double coeff1, double coeff2, double rad) {
				double value;
				value = coeff1*R3STO(zeta1,rad) + coeff2*R3STO(zeta2,rad);
				return value;
		}

}
