package com.bobandthomas.Morbid.Gadget;


import com.bobandthomas.Morbid.Gadget.GadgetSpatialData;
import com.bobandthomas.Morbid.graphics.GobList;
import com.bobandthomas.Morbid.graphics.GobPoly;
import com.bobandthomas.Morbid.graphics.GobPoly.GobPolyType;
import com.bobandthomas.Morbid.molecule.data.CubeArray.DRAWPLANE;
import com.bobandthomas.Morbid.utils.Point3D;

public class GadgetContour2 extends GadgetSpatialData {

	double contourValue;

	@Override
	void Draw(GobList gl) {
		// TODO Auto-generated method stub

	}
	void drawone(double /*Coord*/ pctthresh, DRAWPLANE plane) {
		int n;

	 	if (squared)
			threshold = pctthresh * (getSd().getSqMax()-getSd().getSqMin());
		else
			threshold = pctthresh * (getSd().getMax() - getSd().getMin());
		//getSd().Plane(plane);
		
		for (n=0; n<getSd().Side0(); n++)
		{
			drawoneslice(threshold,n,plane, squared);
		}

	}

	void drawoneslice(double /*Coord*/ threshold, int n, DRAWPLANE plane,boolean square)
	{
		double /*Coord*/ scratch = 0;

		getSd().Plane(plane);
		currentPlane = plane;
		getSd().With(n);
		switch (plane) {
		case planexy:
			cntour(getSd().Z(n),threshold,scratch);
			break;
		case planeyz:
			cntour(getSd().X(n),threshold,scratch);
			break;                                      
		case planexz:
			cntour(getSd().Y(n),threshold,scratch);
		}
	 
	 }



	/*Translated by FOR_C, v2.3, on 02/11/92 at 20:32:08 */
	/*FOR_C Options SET: no=dr pf=cndraw.h - prototypes */
	
	double A(int i, int j) { return getSd().Slice(i, j) - contourValue;}
	void cntour(double /*Coord*/ ipz, double /*Coord*/ value, double /*Coord*/ r11)
	{
	contourValue = value;

	int mdim = getSd().Side0(), imax = getSd().Side1(), jmax = getSd().Side2();
	boolean b[] = new boolean[imax*jmax];
	boolean c[][] = new boolean[2][imax];
	int  i, imax1, j, jmax1, k;
	double /*Coord*/ aa;
	double ab;

	    /************************************************************************
	     *
	     * GENERAL CONTOUR-DRAWING ROUTINE.
	     *
	     *  ON INPUT:
	     *      A      = 2-D ARRAY OF POINTS DEFINING HEIGHT IN MAP-SPACE.
	     *      MDIM   = SIZE OF FIRST DIMENSION OF A.
	     *      IMAX   = LENGTH OF ONE SIDE OF ARRAY WITHIN A.
	     *      JMAX   = LENGTH OF THE OTHER SIDE OF THE ARRAY WITHIN A.
	     *      PZ     = VALUE OF Z DIRECTION IF 3-D PLOTS ARE BEING DRAWN.
	     *      VALUE  = VALUE OF CONTOUR.
	     *      I1,I2,I3 = 1,2,3 IF AN ORTHOSCOPIC PLOT IS WANTED, I.E. IF THE
	     *               VIEW IS ALONG Z, AND THE PLOT IS IN X AND Y, THIS IS THE
	     *               'NORMAL' PLOT. THIS OPTION HAS NEVER BEEN EXCERCISED
	     *               FULLY.
	     *  ON EXIT R11= LENGTH OF CONTOUR. THIS IS THE TOTAL LENGTH OF ALL LINES
	     *               MAKING UP THE CONTOUR, OPEN AND CLOSED.
		 ************************************************************************ */

	    /*  SET UP STRING FOR LATER ANNOTATION OF CONTOURS.
	     */

	    jmax1 = jmax - 1;
	    imax1 = imax - 1;
	    for( i = 0; i < imax; i++ )
	        for( j = 0; j < jmax1; j++ )
	        {
	            aa = A(j,i);
	            ab = A(j+1,i);

	            /*  IF A CONTOUR  PASSES CLOSE TO AN ELEMENT OF A, THE CORRESPONDING
	             *  ELEMENT OF LOGICAL ARRAY B IS SET.
	             */
	            b[imax*(j) + i] = (aa*ab) < 0;
	        }
	    
	    k = 0;
	    for( j = 0; j<jmax ; j += jmax1)
	    {
	        ab = A(j,0);
	        for( i = 0; i < imax1; i++ )
	        {
	            aa = ab;
	            ab = A(j,i+1);

	            /*  THE ARRAY C HAS THE SAME FUNCTION AS B, BUT ON THE BOUNDARY OF THE
	             *  AREA.
	             */
	            c[k][i] = (aa*ab)< 0;
	        }
	        k = 1;
	     }

	    /*   DRAW UNCLOSED CONTOURS (IE STARTING ON A BOUNDARY).
	     */
	    for( i = 1; i <= imax1; i++ ){
	        if( c[0][i - 1] )
	            cndraw(i, 1, 1, b, c, ipz, mdim, value );
	        if( c[1][i - 1] )
	            cndraw(i + 1, jmax, 3,  b, c, ipz, mdim, value );
	        }

	    /*  DRAW INTERIOR CONTOURS PASSING CLOSE TO BOUNDARY POINTS.
	     */
	    for( j = 1; j <= jmax1; j++ ){
	        if( b[(j - 1)*imax] )
	            cndraw(1, j + 1, 4, b, c, ipz, mdim, value );
	        if( b[j*imax - 1] )
	            cndraw(imax, j, 2, b, c, ipz, mdim, value );
	        }


	    /*  DRAW OTHER INTERIOR CONTOURS.
	     */
	    for( i = 2; i <= imax1; i++ ){
	        for( j = 1; j <= jmax1; j++ ){
	            if( b[(j - 1)*imax + i - 1] )
	                cndraw(i, j, 2, b, c, ipz, mdim, value );
	            }
	        }


	    return;
	} /* end of function */


	void cndraw(int iz,int jz,int jdir,boolean[]  b, boolean[][] c, double /*Coord*/ ipz,int mdim, double /*Coord*/ value)
	{
	boolean ai, aj, ak;
	int i,iddx, iddy, idir, idx, idy,  ipen, j, k, m, number;
	double /*Coord*/ aa, ab, ac, ad, fa, factor, ipx, ipy;
	GobPoly pv = null;
	int idirx[]={1,0,-1,0,1,0};
	int idiry[]={0,1,0,-1,0,1};
	    /***********************************************************************
	     *
	     *   DRAW DRAWS CONTOURS, STARTING AT POINT IZ,JZ.
	     *   JDIR = STARTING DIRECTION, IF 1 THEN IN +X DIRECTION
	     *                                 2 THEN IN +Y DIRECTION
	     *                                 3 THEN IN -X DIRECTION
	     *                                 4 THEN IN -Y DIRECTION
	     *    A   = ARRAY TO BE PLOTTED
	     *    B,C = WORK SPACES OF SIZE SUITABLE TO HOLD 5000 AND 4000 LOGICAL
	     *          ELEMENTS.
	     *    iPZ  = VALUE OF THIRD DIMENSION. SET TO ZERO IF NOT WANTED.
	     *    I1,I2,I3 = ORDER OF CARTESIAN DIRECTIONS, X=1,Y=2,Z=3. FIRST TWO
	     *          ARE PLOTTED, SO TO DRAW THE X-Y PLOT USE 1,2,3.
	     *    MDIM= SIZE OF FIRST DIMENSION OF A.
		 *********************************************************************** */

	    idir = jdir;
	    ipen = 0;
	    i = iz;
	    j = jz;
	L_1:
	    aa = A(j - 1,i - 1);
	    ai = aa < 0.;
	    idx = idirx[idir - 1];
	    idy = idiry[idir - 1];
	    ab = A(j + idy - 1,i + idx - 1);
	    if (aa != ab)
	    	factor = aa/(aa - ab);
	    else
	    	factor = 10.0f;

		if (ipen==0)
		{   
			pv = new GobPoly();
			pv.SetPolyType(GobPolyType.Connected);
		}
		ipen ++;	
		switch (currentPlane)
		{
		case planexy: 
			{
				double /*Coord*/ aipx = i + factor*idx - 1;
				ipy = getSd().Y(aipx);
				aipx = j + factor*idy - 1;
	    		ipx = getSd().X(aipx);
				pv.AddPoint(new Point3D(ipx,ipy,ipz));
				break;
			}
			case planexz:
				ipy = getSd().X(i + factor*idx - 1);
	    		ipx = getSd().Z(j + factor*idy - 1);
				pv.AddPoint(new Point3D(ipy,ipz,ipx));
				break;
			case planeyz:
				ipy = getSd().Z(i + factor*idx - 1);
	    		ipx = getSd().Y(j + factor*idy - 1);
				pv.AddPoint(new Point3D(ipz,ipx,ipy));
		}
	//L_2:
	    if( idir/2*2 != idir )
	        goto L_5;
		if( idir != 2 )
	        goto L_6;
	    number = j;
	    m = 1;
	    goto L_7;
	L_6:
	    number = j - 1;
	    m = getSd().Side1();
	L_7:
	    k = (number - 1)*getSd().Side1() + i;
		if( !b[k - 1] )
		{
	        goto Exit_Normal;
		}
	    b[k - 1] = false;
		if( i == m )
		{
	         goto Exit_Normal;
		}
	    goto L_3;
	L_5:
	    if( j != 1 && j != getSd().Side2() )
			goto L_3;
	    number = idir*j;
	    if( number != 3 && number != getSd().Side2() )
	        goto L_3;
	    if( number == 3 )
	        c[0][i - 2] = false;
	    else
			c[1][i - 1] = false;
			
	        goto Exit_Normal;
	L_3:
//	    ipen = 2;
	    iddx = i + idirx[idir];
	    iddy = j + idiry[idir];
	    ac = A(iddy + idy - 1,iddx + idx - 1);
	    aj = ac < 0.;
	    ad = A(iddy - 1,iddx - 1);
	    ak = ad < 0.;
	    fa = 1;
	    if( aj && ak )
	        goto L_10;
	    if( !(aj || ak) )
	        goto L_11;
	    fa = 0.;
	    if( (ai || !ak) && (!ai || ak) )
	        goto L_4;
	    fa = aa*ac - ab*ad;
	    if( fa >= 0. )
	        goto L_4;
	L_15:
	    idir = idir + 1;
	    if( idir == 5 )
	        idir = 1;
	    goto L_1;
	L_4:
	    i = iddx;
	    j = iddy;
	    if( fa == 0 )
	        goto L_1;
	    i = i + idx;
	    j = j + idy;
	    idir = idir - 1;
	    if( idir == 0 )
	        idir = 4;
	    goto L_1;
	L_10:
	    if( ai )
	        goto L_4;
	    goto L_15;
	L_11:
	    if( ai )
	        goto L_15;
	    goto L_4;
	     
	Exit_Normal:
		if (ipen == 1) 
		{
			delete pv;	
		}
		else
		{   
			pv.Color = currentColor;
			gobList.Add(pv);
		}
		return;
	} /* end of function */

}
