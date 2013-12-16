package com.bobandthomas.Morbid.molecule;

import com.bobandthomas.Morbid.utils.CLoadableItem;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.IPropertyAccessor;
import com.bobandthomas.Morbid.utils.IPropertyDescriptor;
import com.bobandthomas.Morbid.utils.PropertyAccessor;
import com.bobandthomas.Morbid.utils.PropertyDescriptorList;

/**
 * @author Thomas Kreek A single visual representation of a substructure. there
 *         may be up to 1 per substructure per gadget instance, plus 1 default
 *         per substructure
 */
public class SubstructureRep extends CLoadableItem implements IPropertyAccessor {
	boolean visible;
	ColorQuad color;
	boolean transparent;
	double alpha;
	Substructure substructure;

	public SubstructureRep(Substructure s, boolean v, ColorQuad c) {
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


	static IPropertyDescriptor propertyDescriptor = new PropertyDescriptorList<SubstructureRep>() {

		@Override
		public void initialize() {
			addPropertyDescriptor(0, "Name", String.class, false);
			addPropertyDescriptor(1, "Visible", Boolean.class, true);
			addPropertyDescriptor(2, "Color", ColorQuad.class, true);
		}
	};

	PropertyAccessor accessor = new PropertyAccessor(propertyDescriptor) {
		@Override
		public Object getProperty(int index) {
			switch (index) {
			case 0:
				return getName();
			case 1:
				return isVisible();
			case 2:
				return getColor();
			}
			return null;
		}

		@Override
		public void setProperty(int index, Object value) {
			switch (index) {
			case 1:
				setVisible((boolean) value);
				return;
			case 2:
				setColor((ColorQuad) value);
				return;
			}

		}
	};
    // {{ IAccessorDelegates
	public void addPropertyDescriptor(int i, String n, @SuppressWarnings("rawtypes") Class c,
			boolean e) {
		accessor.addPropertyDescriptor(i, n, c, e);
	}

	public void addProperty(String name, Object value) {
		accessor.addProperty(name, value);
	}

	public Object getProperty(int index) {
		return accessor.getProperty(index);
	}

	public int getPropertyCount() {
		return accessor.getPropertyCount();
	}

	public void setProperty(int index, Object value) {
		accessor.setProperty(index, value);
	}

	public int getPropertyIndex(String name) {
		return accessor.getPropertyIndex(name);
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
	// }} IAccessorDelegates

}
