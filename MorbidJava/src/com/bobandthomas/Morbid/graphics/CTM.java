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
package com.bobandthomas.Morbid.graphics;

import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.Vector3D;

// TODO: Auto-generated Javadoc
/**
 * The Class CTM.
 * 
 * @author Thomas Kreek
 */
public class CTM {


	    	/** The ident flag. */
	    	boolean identFlag;
			
			/** The ctm. */
			double[][] ctm = new double[4][4];
			//These return new matrices and don't affect the original
//			CTM invert();
//			CTM transpose();
			//These return references to the affected original
//			CTM transposeInPlace();
//			CTM invertInPlace();
//			CTM normalizeInPlace();
			/**
			 * Coord.
			 * 
			 * @param i
			 *            the i
			 * @param j
			 *            the j
			 * @return the double
			 */
			double coord(int i, int j) { return ctm[i][j]; }
			
	        /**
			 * X form.
			 * 
			 * @param p
			 *            the p
			 * @param axis
			 *            the axis
			 * @return the double
			 */
        	double /*Coord*/ XForm(Point3D p, int axis) 
	        { 
	        	return (p).x * ctm[axis][0] + (p).y * ctm[axis][1] + (p).z *ctm[axis][2];
	        }

			/**
			 * Det2x2.
			 * 
			 * @param a
			 *            the a
			 * @param b
			 *            the b
			 * @param c
			 *            the c
			 * @param d
			 *            the d
			 * @return the double
			 */
			static double det2x2(double a, double b, double c, double d)
			{
				double ans;
				ans = a*d - b*c;
				return ans;
			}

			/**
			 * Det3x3.
			 * 
			 * @param a1
			 *            the a1
			 * @param a2
			 *            the a2
			 * @param a3
			 *            the a3
			 * @param b1
			 *            the b1
			 * @param b2
			 *            the b2
			 * @param b3
			 *            the b3
			 * @param c1
			 *            the c1
			 * @param c2
			 *            the c2
			 * @param c3
			 *            the c3
			 * @return the double
			 */
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
			/**
 * Instantiates a new ctm.
 */
public CTM()
			{
				ctm = new double[4][4];
				identity();
			}

			/**
			 * Checks if is ident.
			 * 
			 * @return true, if is ident
			 */
			boolean isIdent()
			{
				return identFlag;
			}
			
			/**
			 * Copy.
			 * 
			 * @param in
			 *            the in
			 */
			void Copy( CTM in)
			{
				ctm = new double[4][4];

				int i,j;

				for (i=0; i<4; i++) for (j=0; j<4; j++)
					ctm[i][j] = in.ctm[i][j];

				identFlag = in.identFlag;
			} 

			/**
			 * Instantiates a new ctm.
			 * 
			 * @param in
			 *            the in
			 */
			public CTM( CTM in)
			{
				ctm = new double[4][4];

				int i,j;

				for (i=0; i<4; i++) for (j=0; j<4; j++)
					ctm[i][j] = in.ctm[i][j];

				identFlag = in.identFlag;
			} 

			/**
			 * Transpose.
			 * 
			 * @return the ctm
			 */
			CTM transpose()
			{

				CTM c = new CTM();
				int i, j;
				for (i = 0; i < 4; i ++)
					for (j = 0; j < 4; j++)
						c.ctm[i][j] = ctm[j][i];
				return c;
			}
			
			/**
			 * Rotate about axis.
			 * 
			 * @param a
			 *            the a
			 * @param angle
			 *            the angle
			 * @return the ctm
			 */
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

			/**
			 * Transpose in place.
			 * 
			 * @return the ctm
			 */
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

			/**
			 * Normalize in place.
			 * 
			 * @return the ctm
			 */
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

			/**
			 * Invert in place.
			 * 
			 * @return the ctm
			 */
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

			/**
			 * Invert.
			 * 
			 * @return the ctm
			 */
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

			/**
			 * Determinant.
			 * 
			 * @return the double
			 */
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
			
			/**
			 * Adjoint.
			 * 
			 * @return the ctm
			 */
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
			
			/**
			 * Mul.
			 * 
			 * @param inCTM
			 *            the in ctm
			 * @return the ctm
			 */
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

			/**
			 * X form.
			 * 
			 * @param p
			 *            the p
			 * @return the point3 d
			 */
			public Point3D XForm(Point3D p) 
			{
				if (isIdent()) return p;
				Point3D newPoint = new Point3D();
				
				newPoint.x = p.x * ctm[0][0] + p.y * ctm[0][1] + p.z *ctm[0][2] + ctm[0][3];
				newPoint.y = p.x * ctm[1][0] + p.y * ctm[1][1] + p.z *ctm[1][2] + ctm[1][3];
				newPoint.z = p.x * ctm[2][0] + p.y * ctm[2][1] + p.z *ctm[2][2] + ctm[2][3];
			    return newPoint;
			}

			/**
			 * X form.
			 * 
			 * @param v
			 *            the v
			 * @return the vertex
			 */
			public Vertex XForm(Vertex v) 
			{
				if (isIdent()) return v;
				Vertex newVertex = new Vertex();
				
				newVertex.x = v.x * ctm[0][0] + v.y * ctm[0][1] + v.z *ctm[0][2] + ctm[0][3];
				newVertex.y = v.x * ctm[1][0] + v.y * ctm[1][1] + v.z *ctm[1][2] + ctm[1][3];
				newVertex.z = v.x * ctm[2][0] + v.y * ctm[2][1] + v.z *ctm[2][2] + ctm[2][3];
				
				newVertex.setColor(v.getColor());
				if (v.hasNormal())
				{
					Vector3D n = v.getNormal();
					newVertex.x = n.x * ctm[0][0] + n.y * ctm[0][1] + n.z *ctm[0][2] + ctm[0][3];
					newVertex.y = n.x * ctm[1][0] + n.y * ctm[1][1] + n.z *ctm[1][2] + ctm[1][3];
					newVertex.z = n.x * ctm[2][0] + n.y * ctm[2][1] + n.z *ctm[2][2] + ctm[2][3];
					n.Normalize();
					newVertex.setNormal(n);
					
				}
			    return newVertex;
			}


			/**
			 * Mul.
			 * 
			 * @param p
			 *            the p
			 * @return the point3 d
			 */
			Point3D Mul(Point3D p)
			{
				return XForm(p);
			}

			/**
			 * Identity.
			 * 
			 * @return the ctm
			 */
			public CTM identity()
			{   
				int i, j;
				for (i=0; i<4; i++) for (j=0; j < 4; j++) ctm[i][j]=0.0f;
			    for (i=0; i<4; i++) ctm[i][i] = 1.0f;
			    identFlag = true;
			    return this;
			}

			/**
			 * Rotx.
			 * 
			 * @param a
			 *            the a
			 * @return the ctm
			 */
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
			
			/**
			 * Roty.
			 * 
			 * @param a
			 *            the a
			 * @return the ctm
			 */
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
			
			/**
			 * Rotz.
			 * 
			 * @param a
			 *            the a
			 * @return the ctm
			 */
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

			/**
			 * Scale.
			 * 
			 * @param zoom
			 *            the zoom
			 * @return the ctm
			 */
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

			/**
			 * Scale.
			 * 
			 * @param sx
			 *            the sx
			 * @param sy
			 *            the sy
			 * @param sz
			 *            the sz
			 * @return the ctm
			 */
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

			/**
			 * Scale.
			 * 
			 * @param s
			 *            the s
			 * @return the ctm
			 */
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

			/**
			 * Transl.
			 * 
			 * @param tx
			 *            the tx
			 * @param ty
			 *            the ty
			 * @param tz
			 *            the tz
			 * @return the ctm
			 */
			CTM transl(double /*Coord*/ tx, double /*Coord*/ ty, double /*Coord*/ tz) {
			  ctm[0][3] += tx;
			  ctm[1][3] += ty;
			  ctm[2][3] += tz;
			  identFlag = false;
			  return this;
			}

			/**
			 * Transl.
			 * 
			 * @param p
			 *            the p
			 * @return the ctm
			 */
			public CTM transl(Point3D p) {

			  ctm[0][3] += p.x;
			  ctm[1][3] += p.y;
			  ctm[2][3] += p.z;
			  identFlag = false;
			  return this;
			}
			
	
	}
