package com.bobandthomas.Morbid.molecule.data;

import javax.vecmath.Point3d;

public class SlaterOrbitals {

	public SlaterOrbitals() {
	}
		static float Pi 		=3.14159265359f;
		static float sqrtPi 	=1.7724539f;
		static float sqrt2  	=1.4142135f;
		static float sqrt3  	=1.7320508f;
		static float sqrt5  	=2.23260679f;
		static float sqrt7  	=2.6457513f;
		static float a0 	   	=0.529177f;
		static double r(double x, double y, double z) { return Math.sqrt( (x)*(x)+ (y)*(y) +(z)*(z) );}
		static double exp(double r) { return Math.exp(r); }
		
		static double  /*SlaterOrbitals */R1S(double rad) {
				double value;

				value = 2*exp(-rad);
				return value;
		}

		static double  /*SlaterOrbitals */YS(double x, double y, double z, double rad) {
				double value;

				value = 1/(2*sqrtPi);
				return value;
		}

		static double  /*SlaterOrbitals */R2S(double rad) {
				double value;

				value = 1/(2*sqrt2)*(rad-2)*exp(-rad/2);
				return value;
		}

		static double  /*SlaterOrbitals */R2P (double rad){
				double value;

				value = 1/(2*sqrt2*sqrt3)*rad*exp(-rad/2);
				return value;
		}

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
		static double  /*SlaterOrbitals */YPx(double x, double y, double z, double rad) {
				if (rad < 1.0e-15) return 0.0F;
				return sqrt3*(x/rad)/2*sqrtPi;
		}

		static double  /*SlaterOrbitals */YPy(double x, double y, double z,double rad) {
				double value;

				if (rad < 1.0e-15) return 0.0F;
				value = sqrt3*(y/rad)/2*sqrtPi;
				return value;
		}

		static double  /*SlaterOrbitals */YPz(double x, double y, double z, double rad) {
				if (rad < 1.0e-15) return 0.0F;
				return sqrt3*(z/rad)/2*sqrtPi;
		}

		static double  /*SlaterOrbitals */R3S(double rad) {
				double value;

				value = 2/(81*sqrt3)* (27-18*rad+ 2 * rad * rad)*exp(-rad/3);
				return value;
		}

		static double  /*SlaterOrbitals */R3P(double rad) {
				double  value;

				value = -4/(81*sqrt3*sqrt2)* (rad*rad - 6*rad)*exp(-rad/3);
				return value;
		}

		static double  /*SlaterOrbitals */R3D(double rad) {
				double value;

				value = 4/(81*sqrt3*sqrt2*sqrt5) *rad*rad *exp(-rad/3);
				return value;
		}
		static double  /*SlaterOrbitals */R4S(double rad) {
				double value;
				value = (192 - 144*rad + 24*rad*rad - rad*rad*rad) *exp(-rad/4)/768;
				return value;
		}
		static double  /*SlaterOrbitals */R4P(double rad) {
				double value;
				value = (80+20*rad+rad*rad)* exp(-rad/4)/(256*sqrt3*sqrt5);
				return value;
		}
		static double  /*SlaterOrbitals */R4D(double rad) {
				double value;
				value = (12-rad)*rad*rad*exp(-rad/4)/(768*sqrt5);
				return value;
		}

		static double  /*SlaterOrbitals */YDx2y2(double x, double y, double z, double rad) {
				if (rad < 1.0e-15) return 0.0F;
				return sqrt3*sqrt5 * (x*x-y*y)/(rad*rad)/(4*sqrtPi);
		}

		static double  /*SlaterOrbitals */YDxz(double x, double y, double z,double rad) {
				if (rad < 1.0e-15) return 0.0F;
				return sqrt2*sqrt3*sqrt5*(x*z/(rad*rad))/(2*sqrt2*sqrtPi);
		}


		static double  /*SlaterOrbitals */YDz2(double x, double y, double z, double rad) {
				if (rad < 1.0e-15) return 0.0F;
				return sqrt5*(3*z*z-rad*rad)/(rad*rad) /(4*sqrtPi);
		}

		static double  /*SlaterOrbitals */YDyz(double x, double y, double z,double rad) {
				if (rad < 1.0e-15) return 0.0F;
				return sqrt2*sqrt3*sqrt5*(y*z)/(rad*rad) /(2*sqrt2*sqrtPi);
		}

		static double  /*SlaterOrbitals */YDxy(double x, double y, double z, double rad) {
				if (rad < 1.0e-15) return 0.0F;
				return sqrt3*sqrt5*(x*y)/(rad*rad) /(2*sqrt2*sqrtPi);
		}
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
		static double  /*SlaterOrbitals */R1STO(double zeta, double rad) {
				double value;

				value = 2*zeta*Math.sqrt(zeta)*exp(-zeta*rad);
				return value;
		}

		static double  /*SlaterOrbitals */R2STO(double zeta, double rad) {
				double value;

				value = 2/sqrt3*zeta*zeta*Math.sqrt(zeta) * rad * exp(-zeta*rad);
				return value;
		}

		static double  /*SlaterOrbitals */R3STO(double zeta, double rad) {
				double value;

				value = 2*zeta*zeta*zeta*Math.sqrt(zeta)/(3*sqrt5) * sqrt2 * rad*rad * exp(-zeta*rad);
				return value;
		}
		static double  /*SlaterOrbitals */R4STO(double zeta, double rad) {
				double value;

				value = 2/(3*sqrt5*sqrt7) * 
					zeta*zeta*zeta*zeta*Math.sqrt(zeta) * 
					rad*rad*rad *exp(-zeta*rad);
				return value;
		}
		static double  /*SlaterOrbitals */contracted_R3 ( double zeta1, double zeta2, double coeff1, double coeff2, double rad) {
				double value;
				value = coeff1*R3STO(zeta1,rad) + coeff2*R3STO(zeta2,rad);
				return value;
		}

}
