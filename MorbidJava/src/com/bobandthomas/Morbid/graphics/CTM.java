package com.bobandthomas.Morbid.graphics;

import com.bobandthomas.Morbid.utils.Point3D;

public class CTM {


	    	boolean identFlag;
			double[][] ctm = new double[4][4];
			//These return new matrices and don't affect the original
//			CTM invert();
//			CTM transpose();
			//These return references to the affected original
//			CTM transposeInPlace();
//			CTM invertInPlace();
//			CTM normalizeInPlace();
			double coord(int i, int j) { return ctm[i][j]; }
			
	        double /*Coord*/ XForm(Point3D p, int axis) 
	        { 
	        	return (p).x * ctm[axis][0] + (p).y * ctm[axis][1] + (p).z *ctm[axis][2];
	        }

			static double det2x2(double a, double b, double c, double d)
			{
				double ans;
				ans = a*d - b*c;
				return ans;
			}

			static double det3x3(double a1, double a2, double a3,
								double b1, double b2, double b3,
								double c1, double c2, double c3)
			{
	 			double ans;
			 	
	 			ans = a1 * det2x2( b2, b3, c2, c3)
	 				- b1 * det2x2( a2, a3, c2, c3)
	 				+ c1 * det2x2( a2, a3, b2, b3);
	 			return ans;

			}
//
			public CTM()
			{
				ctm = new double[4][4];
				identity();
			}

			boolean isIdent()
			{
				return identFlag;
			}
			void Copy( CTM in)
			{
				ctm = new double[4][4];

				int i,j;

				for (i=0; i<4; i++) for (j=0; j<4; j++)
					ctm[i][j] = in.ctm[i][j];

				identFlag = in.identFlag;
			} 

			CTM( CTM in)
			{
				ctm = new double[4][4];

				int i,j;

				for (i=0; i<4; i++) for (j=0; j<4; j++)
					ctm[i][j] = in.ctm[i][j];

				identFlag = in.identFlag;
			} 

			CTM transpose()
			{

				CTM c = new CTM();
				int i, j;
				for (i = 0; i < 4; i ++)
					for (j = 0; j < 4; j++)
						c.ctm[i][j] = ctm[j][i];
				return c;
			}
			public CTM rotateAboutAxis(Point3D a, double angle)
			{   
				double s = Math.sin(angle);
				double c = Math.cos(angle);
				double t = 1.0 - c;
				CTM R = new CTM();
				R.ctm[0][0] = t * a.x * a.x + c;
				R.ctm[1][0] = t * a.x * a.y - s * a.z;
				R.ctm[2][0] = t * a.x * a.z + s * a.y;
				
				R.ctm[0][1] = t * a.x * a.y + s * a.z;
				R.ctm[1][1] = t * a.y * a.y + c; 
				R.ctm[2][1] = t * a.y * a.z - s * a.x;
				
				R.ctm[0][2] = t * a.x * a.z - s * a.y;
				R.ctm[1][2] = t * a.y * a.z + s * a.x;
				R.ctm[2][2] = t * a.z * a.z + c;
				
				identFlag = false;
				this.Copy(this.Mul(R));
				return this;

			}

			CTM transposeInPlace()
			{

				CTM c = new CTM();
				int i, j;
				for (i = 0; i < 4; i ++)
					for (j = 0; j < 4; j++)
						c.ctm[i][j] = ctm[j][i];
				this.Copy(c);
				return this;
			}

			CTM normalizeInPlace()
			{

				double factor;
				int i, j;
				for (i = 0; i < 4; i ++)
				{
					factor = 0.0f;
					for (j = 0; j < 4; j++)
						factor += ctm[i][j]*ctm[i][j];
					factor = 1.0f/Math.sqrt(factor);
					for (j = 0; j < 4; j++)
						ctm[i][j] /= factor;
					
				}
				for (i = 0; i < 4; i ++)
				{
					factor = 0.0f;
					for (j = 0; j < 4; j++)
						factor += ctm[j][i]*ctm[j][i];
					factor = 1.0/Math.sqrt( factor);
					for (j = 0; j < 4; j++)
						ctm[j][i] /= factor;
					
				}
				return this;
			}

			CTM invertInPlace()
			{

				int i, j;
				double det;
				CTM temp;
				  
				temp = adjoint();
				det = temp.determinant();
				if (det != 1.00)
				{
				 	i = 1;
				}
				if (Math.abs(det) < 1e-32)
					return this;
				
				for (i=0; i < 4; i ++)
					for (j = 0; j < 4; j++)
						  temp.ctm[i][j] = temp.ctm[i][j] / det;
						  
				this.Copy(temp);

				return this;
			}

			CTM invert()
			{

				int i, j;
				double det;
				CTM temp;
				
				temp = adjoint();
				det = temp.determinant();
				if (Math.abs(det) < 1e-32)
					return this;
				
				for (i=0; i < 4; i ++)
					for (j = 0; j < 4; j++)
						  temp.ctm[i][j] = temp.ctm[i][j] / det;

				return temp;
			}

			double /*Coord*/ determinant()
			{
				double ans;
				double a1, a2, a3, a4, b1, b2, b3, b4, c1, c2, c3, c4, d1, d2, d3, d4;

			 	a1 = ctm[0][0]; a2 = ctm[1][0]; a3 = ctm[2][0]; a4 = ctm[3][0];
				b1 = ctm[0][1]; b2 = ctm[1][1]; b3 = ctm[2][1]; b4 = ctm[3][1];
				c1 = ctm[0][2]; c2 = ctm[1][2]; c3 = ctm[2][2]; c4 = ctm[3][2];
				d1 = ctm[0][3]; d2 = ctm[1][3]; d3 = ctm[2][3]; d4 = ctm[3][3];

				ans = a1 * det3x3( b2, b3, b4, c2, c3, c4, d2, d3, d4)
					- b1 * det3x3( a2, a3, a4, c2, c3, c4, d2, d3, d4)
					+ c1 * det3x3( a2, a3, a4, b2, b3, b4, d2, d3, d4)
					- d1 * det3x3( a2, a3, a4, b2, b3, b4, c2, c3, c4);
				return ans;
			}
			CTM adjoint()
			{
			 	double a1,a2,a3,a4, b1,b2,b3,b4;
			 	double c1,c2,c3,c4, d1,d2,d3,d4;
			 	CTM out = new CTM();
			 	
			 	a1 = ctm[0][0]; a2 = ctm[1][0]; a3 = ctm[2][0]; a4 = ctm[3][0];
				b1 = ctm[0][1]; b2 = ctm[1][1]; b3 = ctm[2][1]; b4 = ctm[3][1];
				c1 = ctm[0][2]; c2 = ctm[1][2]; c3 = ctm[2][2]; c4 = ctm[3][2];
				d1 = ctm[0][3]; d2 = ctm[1][3]; d3 = ctm[2][3]; d4 = ctm[3][3];
			 	
			 	out.ctm[0][0] =		det3x3( b2, b3, b4, c2, c3, c4, d2, d3, d4);
			 	out.ctm[1][0] = -	det3x3( a2, a3, a4, c2, c3, c4, d2, d3, d4);
			 	out.ctm[2][0] =		det3x3( a2, a3, a4, b2, b3, b4, d2, d3, d4);
			 	out.ctm[3][0] = -	det3x3( a2, a3, a4, b2, b3, b4, c2, c3, c4);
			 	
			 	out.ctm[0][1] =	-	det3x3( b1, b3, b4, c1, c3, c4, d1, d3, d4);
			 	out.ctm[1][1] =  	det3x3( a1, a3, a4, c1, c3, c4, d1, d3, d4);
			 	out.ctm[2][1] =	-	det3x3( a1, a3, a4, b1, b3, b4, d1, d3, d4);
			 	out.ctm[3][1] = 	det3x3( a1, a3, a4, b1, b3, b4, c1, c3, c4);

			  	out.ctm[0][2] =		det3x3( b1, b2, b4, c1, c2, c4, d1, d2, d4);
			 	out.ctm[1][2] = -	det3x3( a1, a2, a4, c1, c2, c4, d1, d2, d4);
			 	out.ctm[2][2] =		det3x3( a1, a2, a4, b1, b2, b4, d1, d2, d4);
			 	out.ctm[3][2] = -	det3x3( a1, a2, a4, b1, b2, b4, c1, c2, c4);
			 	
			 	out.ctm[0][3] =	-	det3x3( b1, b2, b3, c1, c2, c3, d1, d2, d3);
			 	out.ctm[1][3] =  	det3x3( a1, a2, a3, c1, c2, c3, d1, d2, d3);
			 	out.ctm[2][3] =	-	det3x3( a1, a2, a3, b1, b2, b3, d1, d2, d3);
			 	out.ctm[3][3] = 	det3x3( a1, a2, a3, b1, b2, b3, c1, c2, c3); 

			 	return out;

			}
			public CTM Mul(CTM inCTM)
			{   
				int i, j, k;
				CTM newctm = new CTM();
				
				for (i=0; i < 4; i ++)
					for (j=0; j < 4; j ++)
					{
						newctm.ctm[i][j] = 0.0f;
						for (k=0; k < 4; k ++)
							newctm.ctm[i][j] += ctm[i][k] * inCTM.ctm[k][j];
					}
						
				newctm.identFlag = identFlag && inCTM.identFlag;
				return newctm;   
			}

			Point3D XForm(Point3D p) 
			{
				if (isIdent()) return p;
				Point3D newPoint = new Point3D();
				
				newPoint.x = p.x * ctm[0][0] + p.y * ctm[0][1] + p.z *ctm[0][2] + ctm[0][3];
				newPoint.y = p.x * ctm[1][0] + p.y * ctm[1][1] + p.z *ctm[1][2] + ctm[1][3];
				newPoint.z = p.x * ctm[2][0] + p.y * ctm[2][1] + p.z *ctm[2][2] + ctm[2][3];
			    return newPoint;
			}



			Point3D Mul(Point3D p)
			{
				return XForm(p);
			}

			public CTM identity()
			{   
				int i, j;
				for (i=0; i<4; i++) for (j=0; j < 4; j++) ctm[i][j]=0.0f;
			    for (i=0; i<4; i++) ctm[i][i] = 1.0f;
			    identFlag = true;
			    return this;
			}

			CTM rotx(double a) {
			  int i;
			  double sinang,cosang,t;
			  sinang = Math.sin(a/57.2958f);
			  cosang = Math.cos(a/57.2958f);

			  for (i=0; i<4; i++) {
			    t = ctm[i][1];
			    ctm[i][1] = t*cosang - ctm[i][2]*sinang;
			    ctm[i][2] = t*sinang + ctm[i][2]*cosang;
			  }   
			  identFlag = false;
			   return this;
			}
			CTM roty(double a) {
			  int i;
			  double sinang,cosang,t;
			  sinang = Math.sin(a/57.2958f);
			  cosang = Math.cos(a/57.2958f);

			  for (i=0; i<4; i++) {
			    t = ctm[i][0];
			    ctm[i][0] = t*cosang + ctm[i][2]*sinang;
			    ctm[i][2] = - t*sinang + ctm[i][2]*cosang;
			  }
			  identFlag = false;
			  return this;
			}
			CTM rotz( double a) {
			  int i;
			  double sinang,cosang,t;
			  sinang =  Math.sin(a/57.2958f);
			  cosang =  Math.cos(a/57.2958f);

			  for (i=0; i<4; i++) {
			    t = ctm[i][0];
				ctm[i][0] = t*cosang - ctm[i][1]*sinang;
			    ctm[i][1] = t*sinang + ctm[i][1]*cosang;
			  }
			  identFlag = false;
			  return this;
			}

			public CTM scale( double zoom) {
			  int i;

			   for (i=0; i<4; i++) {
			    ctm[i][0] *=zoom;
			    ctm[i][1] *=zoom;
			    ctm[i][2] *=zoom;
			  }
			  identFlag = false;
			  return this;
			}

			CTM scale( double /*Coord*/ sx, double /*Coord*/ sy, double /*Coord*/ sz) {
			  int i;

			   for (i=0; i<4; i++) {
			    ctm[i][0] *=sx;
			    ctm[i][1] *=sy;
			    ctm[i][2] *=sz;
			  }
			  identFlag = false;
			  return this;
			}

			public CTM scale( Point3D s) {
			  int i;

			   for (i=0; i<4; i++) {
			    ctm[i][0] *=s.x;
			    ctm[i][1] *=s.y;
			    ctm[i][2] *=s.z;
			  }
			  identFlag = false;
			  return this;
			}

			CTM transl(double /*Coord*/ tx, double /*Coord*/ ty, double /*Coord*/ tz) {
			  ctm[0][3] += tx;
			  ctm[1][3] += ty;
			  ctm[2][3] += tz;
			  identFlag = false;
			  return this;
			}

			public CTM transl(Point3D p) {

			  ctm[0][3] += p.x;
			  ctm[1][3] += p.y;
			  ctm[2][3] += p.z;
			  identFlag = false;
			  return this;
			}
			
	
	}
