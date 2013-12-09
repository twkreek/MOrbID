package com.bobandthomas.Morbid.molecule;

import com.bobandthomas.Morbid.utils.CLoadableItem;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.IPropertyAccessor;
import com.bobandthomas.Morbid.utils.IPropertyDescriptor;
import com.bobandthomas.Morbid.utils.PropertyAccessor;
import com.bobandthomas.Morbid.utils.PropertyDescriptoList;

/**
 * @author Thomas Kreek
 *	A single visual representation of a substructure.
 *  there may be up to 1 per substructure per gadget instance, 
 *  plus 1 default per substructure
 */
public class SubstructureRep extends CLoadableItem implements IPropertyAccessor, IPropertyDescriptor
{
	boolean visible;
	ColorQuad color;
	boolean transparent;
	double alpha;
	Substructure substructure;
	static IPropertyDescriptor propertyDescriptor = new PropertyDescriptoList<SubstructureRep>(){

		@Override
		public void initialize() {
			add(0, "Name", String.class, false);
			add(1, "Visible", Boolean.class, true );
			add(2, "Color", ColorQuad.class, true);
		}
	};
	
	PropertyAccessor accessor = new PropertyAccessor(propertyDescriptor)
	{
		@Override
		public Object getProperty(int index) {
			switch(index)
			{
			case 0: return getName();
			case 1: return isVisible();
			case 2: return getColor();
			}
			return null;
		}
		@Override
		public void setProperty(int index, Object value) {
			switch(index)
			{
			case 1: 
				setVisible((boolean)value);
				return;
			case 2:
				setColor((ColorQuad) value);
				return;
			}
		
	}
	};
		public Object getProperty(int index) {
			return accessor.getProperty(index);
		}
		public int getPropertyCount() {
			return accessor.getPropertyCount();
		}
		public void setProperty(int index, Object value) {
			accessor.setProperty(index, value);
		}
		public int getIndex(String name) {
			return accessor.getIndex(name);
		}
		public Class<?> getPropertyClass(int index) {
			return accessor.getPropertyClass(index);
		}
		public String getPropertyName(int index) {
			return accessor.getPropertyName(index);
		}
		public boolean isPropertyEditable(int index) {
			return accessor.isPropertyEditable(index);
		}
		public Object getProperty(String name) {
			return accessor.getProperty(name);
		}
		public void setProperty(String name, Object value) {
			accessor.setProperty(name, value);
		}

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
