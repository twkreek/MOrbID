package com.bobandthomas.Morbid.molecule;

import com.bobandthomas.Morbid.utils.CLoadableItem;
import com.bobandthomas.Morbid.utils.ColorQuad;

/**
 * @author Thomas Kreek
 *	A single visual representation of a substructure.
 *  there may be up to 1 per substructure per gadget instance, 
 *  plus 1 default per substructure
 */
public class SubstructureRep extends CLoadableItem
{
	boolean visible;
	ColorQuad color;
	boolean transparent;
	double alpha;
	Substructure substructure;
	public SubstructureRep(Substructure s, boolean v, ColorQuad c)
	{
		substructure = s;
		visible = v;
		color = c;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public ColorQuad getColor() {
		return color;
	}
	public void setColor(ColorQuad color) {
		this.color = color;
	}
	public Substructure getSubstructure() {
		return substructure;
	}
	public void setSubstructure(Substructure substructure) {
		this.substructure = substructure;
	}
}