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
package com.bobandthomas.Morbid.molecule;

import com.bobandthomas.Morbid.utils.CLoadableItem;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.IPropertyAccessor;
import com.bobandthomas.Morbid.utils.IPropertyDescriptor;
import com.bobandthomas.Morbid.utils.IPropertyDescriptorList;
import com.bobandthomas.Morbid.utils.PropertyAccessor;
import com.bobandthomas.Morbid.utils.MPropertyDescriptorList;

// TODO: Auto-generated Javadoc
/**
 * The Class SubstructureRep.
 * 
 * @author Thomas Kreek A single visual representation of a substructure. there
 *         may be up to 1 per substructure per gadget instance, plus 1 default
 *         per substructure
 */
public class SubstructureRep extends CLoadableItem implements IPropertyAccessor {
	
	/** The visible. */
	boolean visible;
	
	/** The color. */
	ColorQuad color;
	
	/** The transparent. */
	boolean transparent;
	
	/** The alpha. */
	double alpha;
	
	/** The substructure. */
	Substructure substructure;

	/**
	 * Instantiates a new substructure rep.
	 * 
	 * @param s
	 *            the s
	 * @param v
	 *            the v
	 * @param c
	 *            the c
	 */
	public SubstructureRep(Substructure s, boolean v, ColorQuad c) {
		substructure = s;
		visible = v;
		color = c;
	}

	/**
	 * Checks if is visible.
	 * 
	 * @return true, if is visible
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Sets the visible.
	 * 
	 * @param visible
	 *            the new visible
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * Gets the color.
	 * 
	 * @return the color
	 */
	public ColorQuad getColor() {
		return color;
	}

	/**
	 * Sets the color.
	 * 
	 * @param color
	 *            the new color
	 */
	public void setColor(ColorQuad color) {
		this.color = color;
	}

	/**
	 * Gets the substructure.
	 * 
	 * @return the substructure
	 */
	public Substructure getSubstructure() {
		return substructure;
	}

	/**
	 * Sets the substructure.
	 * 
	 * @param substructure
	 *            the new substructure
	 */
	public void setSubstructure(Substructure substructure) {
		this.substructure = substructure;
	}


	/** The property descriptor. */
	static IPropertyDescriptorList propertyDescriptor = new MPropertyDescriptorList() {

		@Override
		public void initialize() {
			addPropertyDescriptor(0, "Name", String.class, false);
			addPropertyDescriptor(1, "Visible", Boolean.class, true);
			addPropertyDescriptor(2, "Color", ColorQuad.class, true);
		}
	};

	/** The accessor. */
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

	/* (non-Javadoc)
     * @see com.bobandthomas.Morbid.utils.IPropertyAccessor#getProperty(java.lang.String)
     */
    public Object getProperty(String name) {
		return accessor.getProperty(name);
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IPropertyAccessor#setProperty(java.lang.String, java.lang.Object)
	 */
	public void setProperty(String name, Object value) {
		accessor.setProperty(name, value);
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IPropertyAccessor#getProperty(int)
	 */
	public Object getProperty(int index) {
		return accessor.getProperty(index);
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IPropertyAccessor#setProperty(int, java.lang.Object)
	 */
	public void setProperty(int index, Object value) {
		accessor.setProperty(index, value);
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IPropertyAccessor#getProperty(com.bobandthomas.Morbid.utils.IPropertyDescriptor)
	 */
	public Object getProperty(IPropertyDescriptor desc) {
		return accessor.getProperty(desc);
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IPropertyAccessor#setProperty(com.bobandthomas.Morbid.utils.IPropertyDescriptor, java.lang.Object)
	 */
	public void setProperty(IPropertyDescriptor desc, Object value) {
		accessor.setProperty(desc, value);
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IPropertyAccessor#getDescriptors()
	 */
	public IPropertyDescriptorList getDescriptors() {
		return accessor.getDescriptors();
	}
	
	// }} IAccessorDelegates

}
