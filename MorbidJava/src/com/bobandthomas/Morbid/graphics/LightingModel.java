package com.bobandthomas.Morbid.graphics;

import com.bobandthomas.Morbid.utils.BoundingBox;
import com.bobandthomas.Morbid.utils.CLoadableItem;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.StaticColorQuad;
import com.bobandthomas.Morbid.utils.Vector3D;

public class LightingModel extends CLoadableItem {

		boolean DoLighting;
		boolean DoScatterAlpha;;
		boolean DoSpecularity;
		boolean  DoDiffuse;
		boolean  LightFrontBack;
		boolean  DoDepthCue;
		ColorQuad   Background;
		float  DepthCueMin;
		float  DepthCueRate;
		BoundingBox   BoundingBox;
		
		ColorQuad DepthCue(Point3D p, ColorQuad cq) { if (DoDepthCue) return CalcDepthCue(p, cq); else return cq; }

		public LightingModel()
		{
			DoLighting = true;
			DoScatterAlpha = true;
			DoSpecularity = true;
			DoDiffuse = true;
			LightFrontBack = false;
			DoDepthCue = false;
			DepthCueMin = 0.1f;
			DepthCueRate  = 1.00f;
		}
		ColorQuad CalcDepthCue(Point3D pos, ColorQuad cq)
		{
			ColorQuad color;
			double z = pos.z;
			double fraction = (z - BoundingBox.min.z) * DepthCueRate / (BoundingBox.max.z - BoundingBox.min.z + DepthCueMin);
			if (fraction < 0.0)
				fraction = 0.0;
			if (fraction > 1.0)
				fraction = 1.0;
			color = cq.BlendRGB(StaticColorQuad.Black.cq(), fraction);
			return color;
		}
		ColorQuad LightPoint(Material m, LightSourceList lightList, Point3D pos, Point3D N1)
		{
			int i;

			if (!DoLighting)
				return DepthCue(pos, m.getColor());

			ColorQuad pColor = StaticColorQuad.Black.cq();
			Vector3D N = new Vector3D(N1);
			N.Normalize();
			int nLights = lightList.size();
			if (nLights == 0)
			{
				return pColor;
			}

			for (i=0 ; i < nLights; i++)
			{    
				LightSource L = lightList.get(i);

				// ambient contribution
				ColorQuad ambient = L.color.multiply(L.ambient);
				pColor = pColor.plus(ambient.multiply(m.kAmbient));
				
				double d = L.pos.Dot(N);
				if (d > 1.0)
					d = 1.0;
				if (d < 0.0)
					if (LightFrontBack)
						d = Math.abs(d);
					else
						d = 0.0;

				// diffuse contribution
				if (DoDiffuse)
				{
				    ColorQuad l = L.color.multiply(m.getColor().multiply(m.kDiffuse)).multiply(d);
					pColor = pColor.plus(l);
				}

				// specular contribution
				if (m.useSpecularity && DoSpecularity)
				{
					Vector3D V = new Vector3D(0.0f, 0.0f, -1.0f);
					Vector3D ntemp = N.Scale(2*d).Sub(L.getLocation());
					double cosalpha =  /*( N.Scale(2*d).Sub(L.pos))*/ntemp.Dot(V);
					//TODO verify new formula for cosalpha
					if (cosalpha < 0)
						cosalpha = 0.0f - cosalpha;

					if (cosalpha > 1.0)
						cosalpha = 1.0;
					else
						cosalpha = (float) Math.pow(cosalpha, m.specularity);
						/* I = Iaka + IpKd L dot N */
					cosalpha = cosalpha * m.kSpecularity;
					ColorQuad specular = L.color.multiply(cosalpha);
					pColor = pColor.plus(specular);
				}
			}
			pColor = pColor.multiply(1/ (nLights *1.0f));
			pColor = pColor.plus(m.getColor().multiply(m.kEmission));
			pColor.Clamp(0.999f );

			return DepthCue(pos, pColor);	
		}


}
