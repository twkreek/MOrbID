package com.bobandthomas.Morbid.molecule;

import com.bobandthomas.Morbid.utils.CLoadableItem;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.IPropertyAccessor;
import com.bobandthomas.Morbid.utils.IPropertyDescriptor;
import com.bobandthomas.Morbid.utils.IPropertyDescriptorList;
import com.bobandthomas.Morbid.utils.PropertyAccessor;
import com.bobandthomas.Morbid.utils.MPropertyDescriptorList;

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


	static IPropertyDescriptorList propertyDescriptor = new MPropertyDescriptorList() {

		@Override
		public void initialize() {
			addPropertyDescriptor(0, "Name", String.class, false);
			addPropertyDescriptor(1, "Visible", Boolean.class, true);
			addPropertyDescriptor(2, "Color", ColorQuad.class, true);
		}
	};

	IPropertyAccessor accessor = new PropertyAccessor(this, propertyDescriptor) {
		@Override
		public Object getProperty(IPropertyDescriptor ipd) {
			switch (ipd.getIndex()) {
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
		public void setProperty(IPropertyDescriptor ipd, Object value) {
			switch (ipd.getIndex()) {
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

	public Object getProperty(String name) {
		return accessor.getProperty(name);
	}

	public void setProperty(String name, Object value) {
		accessor.setProperty(name, value);
	}

	public Object getProperty(int index) {
		return accessor.getProperty(index);
	}

	public void setProperty(int index, Object value) {
		accessor.setProperty(index, value);
	}

	public Object getProperty(IPropertyDescriptor desc) {
		return accessor.getProperty(desc);
	}

	public void setProperty(IPropertyDescriptor desc, Object value) {
		accessor.setProperty(desc, value);
	}

	public IPropertyDescriptorList getDescriptors() {
		return accessor.getDescriptors();
	}
	
	// }} IAccessorDelegates

}
