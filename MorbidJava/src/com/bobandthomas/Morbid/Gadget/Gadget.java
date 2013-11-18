package com.bobandthomas.Morbid.Gadget;

import java.util.ArrayList;

import com.bobandthomas.Morbid.Gadget.Scene.LayerPosition;
import com.bobandthomas.Morbid.graphics.GobList;
import com.bobandthomas.Morbid.graphics.Material;
import com.bobandthomas.Morbid.graphics.SphereGob;
import com.bobandthomas.Morbid.molecule.Atom;
import com.bobandthomas.Morbid.molecule.Substructure;
import com.bobandthomas.Morbid.molecule.AtomType;
import com.bobandthomas.Morbid.molecule.Molecule;
import com.bobandthomas.Morbid.molecule.SubstructureRepList;
import com.bobandthomas.Morbid.molecule.SubstructureSet;
import com.bobandthomas.Morbid.utils.*;

public abstract class Gadget extends CLoadableItem {

	public enum ColorBy {
		TYPE(0, "Atom Type"), CHARGE(1, "Charge"), MONOCHROME(2,"Monochrome"),SUBSTRUCTURE(3, "Substructure"),SPATIALDATA(4,"Spatial Data");
		int id;
		String name;
		ColorBy(int i, String n) {
			id = i;
			name = n;
		}

		private static ColorBy[] values = null;

		public static ColorBy fromInt(int i) {
			if (ColorBy.values == null) {
				ColorBy.values = ColorBy.values();
			}
			return ColorBy.values[i];
		}
	};
	
		public class ColorOption
		{
			ColorBy colorBy;
			String name;
			SubstructureSet substructure;
			SubstructureRepList reps;
			ColorOption(ColorBy cb)
			{
				colorBy = cb;
				name = cb.name;
				substructure = null;
			}
			ColorOption( SubstructureSet s)
			{
				colorBy = ColorBy.SUBSTRUCTURE;
				substructure = s;
				name = s.getName();
				reps = s.getDefaultRep();
			}
			public String toString()
			{
				return name;
			}
		}
		ArrayList<ColorOption> colorOptionList = null;
		
		public ArrayList<ColorOption> getColorOptions(boolean reset)
		{
			if (colorOptionList != null && !reset)
				return colorOptionList;
			colorOptionList = new ArrayList<ColorOption>();
			for (ColorBy cb : ColorBy.values())
			{
				switch (cb)
				{
					case SUBSTRUCTURE:
						for (SubstructureSet s: molecule.getSubstructures())
						{
							ColorOption co = new ColorOption(s);
							colorOptionList.add(co);
						}
					break;
				default:
					colorOptionList.add(new ColorOption(cb));
					break;
						
				}
			}
			
			return colorOptionList;
		}
		
		public void setCurrentColorOption( int index)
		{
			currentColorOption = colorOptionList.get(index);
			markDirty();
		}
		
	
		private ColorOption currentColorOption;
		boolean rotate;
		boolean visible;
		double alpha;
		boolean transparent;
		boolean m_bFirst;
		LayerPosition layer;
//		int position;
		Material mat; // current material during a working session;
	
		ColorQuad baseColor;
		ColorQuad plusColor;
		ColorQuad minusColor;
		ColorQuad colors[];
		
		MinMax chargeRange;

		

		Material baseMaterial;
		Material plusMaterial;
		Material minusMaterial;
		SubstructureRepList substructureFilterList = null;
		Substructure currentFilterSubstructure = null;
		
		boolean substructureFilter;


		Scene scene = null;
		Molecule molecule = null;
		

		public abstract String getGadgetType();
		

		public LayerPosition getLayer() {
			return layer;
		}
		
		public void sceneAdded(Scene s)
		{
			scene = s;
			molecule = scene.GetMolecule();
		}
		public void sceneChanged(Scene scene)
		{
			//most Gadgets don't care.
		}

		public boolean isSubstructureFilter() {
			return substructureFilter;
		}

		public void setSubstructureFilter(boolean substructureFilter) {
			this.substructureFilter = substructureFilter;
			markDirty();
		}
		public SubstructureRepList getSubstructureFilterList() {
			return substructureFilterList;
		}

		public void setSubstructureFilterList(SubstructureRepList substructureFilterList) {
			this.substructureFilterList = substructureFilterList;
			markDirty();
		}
		
		public boolean isAtomVisible(Atom a)
		{
			if (!isSubstructureFilter()) return true;
			if (substructureFilterList == null) return true;
			return substructureFilterList.isVisible(a);

		}


		public void setLayer(LayerPosition layer) {
			this.layer = layer;
		}


		abstract void Draw(GobList gl);
		
		public void DrawGadget(GobList gl)
		{
			if (!fixGobList(gl)) return;
			gl.setGadget(this);
			chargeRange = molecule.Atoms().getChargeRange();
			if (!isVisible())
			{	//make a small, scratch sphere at the origin to use as replacement for the gadget's drawing.
				SphereGob s = new SphereGob(new Point3D(0,0,0), 0.0001);
				s.setLOD(10);
				gl.add(s);
				return;
			}
			else
				Draw(gl);
			gl.markDirty();
			markClean();
			
		}
		
		public boolean isVisible() { return visible;}
		public void setVisible(boolean show) 
		{ 
			visible = show;
			markDirty(); 
		}

//		int GetLayer() { return position; }
		

			
		Gadget()
		{
			setName(getGadgetType());
			markDirty();
			visible = true;
			rotate = true;
			layer = LayerPosition.LayerModel;
			SetColor(StaticColorQuad.LiteGray.cq(), StaticColorQuad.Red.cq(), StaticColorQuad.Blue.cq());
			baseMaterial = new Material(baseColor);
			plusMaterial = new Material(plusColor);
			minusMaterial = new Material(minusColor);
			alpha = 0.5;
			transparent = false;
			this.currentColorOption = new ColorOption(ColorBy.TYPE);
			colors = new ColorQuad[3];
			colors[0] = minusColor;
			colors[1] = baseColor;
			colors[2] = plusColor;
		}


		Scene GetScene()
		{
			return ((GadgetList)parentSet).GetScene();
		}

		public Molecule GetMolecule()
		{
		 	return GetScene().GetMolecule();
		}

		boolean fixGobList(GobList gobList)
		/*
		 * clears gobList and sets name.  returns true if draw should continue.
		 */
		{
		 	if (isDirty())
		 	{
		 		gobList.clear();
				gobList.setName(getGadgetType());
		 	}
		 	else
		 		return false;

		 	gobList.markDirty();

		 	if (!isVisible())
		 	{
		 		markClean();
		 		return false;
		 	}
		 	return true;
		} 

		void SetColor(ColorQuad base)
		{
			baseColor = base;
		}
		void SetColor(ColorQuad base, ColorQuad plus, ColorQuad minus)
		{
			this.baseColor = base;
			this.plusColor = plus;
			this.minusColor = minus;
		}


		public double getTransparency() {
			return alpha;
		}


		public void setTransparency(double transparency) {
			this.alpha = transparency;
			baseMaterial.setAlpha(alpha);
			markDirty();
		}


		public boolean isTransparent() {
			return transparent;
		}


		public void setTransparent(boolean useTransparency) {
			this.transparent = useTransparency;
			baseMaterial.setUseFilter(useTransparency);
			markDirty();
		}
		ColorQuad getFractionColor(MinMax range, double value)
		{
			return ColorQuad.multiBlend(colors, value);
		}
		
		ColorQuad getAtomColor(Atom a) {
			ColorQuad theColor;
			switch (currentColorOption.colorBy) {
			case CHARGE:
				double fraction = chargeRange.getFraction(a.getCharge());
				theColor = ColorQuad.multiBlend(colors, fraction);
				mat = new Material(baseMaterial);
				mat.setColor(theColor);
				break;
			case TYPE: {
				AtomType at = a.getAtomType();
				if (at != null) {
					theColor = at.color;
					mat = new Material(at.mat);
				} else {
					theColor = baseColor;
					mat = baseMaterial;
				}
			}
			break;
			case SUBSTRUCTURE:
			{
					if (currentColorOption.reps == null)
					{
						mat = baseMaterial;
						theColor = StaticColorQuad.LiteGray.cq();
					}
					else
					{
						mat = new Material(baseMaterial);
						theColor = currentColorOption.reps.getColor(a);
						mat.setColor(theColor);
					}
			}
			break;
			default:
				theColor = baseColor;
				mat = baseMaterial;

			}
			if (theColor == null)
			{
				theColor = StaticColorQuad.LiteGray.cq();
				mat = new Material(baseMaterial);
			}
			if (transparent)
				mat.setAlpha(alpha);
			return theColor;
		}


		public ColorOption getCurrentColorOption() {
			return currentColorOption;
		}


		public void setCurrentColorOption(ColorOption currentColorOption) {
			this.currentColorOption = currentColorOption;
			markDirty();
		}
};