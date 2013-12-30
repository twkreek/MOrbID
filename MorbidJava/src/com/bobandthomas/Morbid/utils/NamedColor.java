package com.bobandthomas.Morbid.utils;

import java.awt.Color;

import javax.vecmath.Color3f;

public class NamedColor extends CLoadableItem implements IPropertyAccessor {
	
	ColorQuad color;

	public void Set(double r, double g, double b) {
		color.Set(r, g, b);
	}

	public void Set(int inR, int inG, int inB) {
		color.Set(inR, inG, inB);
	}
	public void Set(ColorQuad cq)
	{
		color.Set(cq.x, cq.y, cq.z);
	}

	public Color3f Cf() {
		return color.Cf();
	}

	public Color getJColor() {
		return color.getJColor();
	}

	public final void set(Color arg0) {
		color.set(arg0);
	}

	public final void set(float arg0, float arg1, float arg2, float arg3) {
		color.set(arg0, arg1, arg2, arg3);
	}

	public void setAlpha(double a) {
		color.setAlpha(a);
	}
	
	public ColorQuad getColor()
	{
		return color;
	}
	static IPropertyDescriptorList propertyDescriptor = new MPropertyDescriptorList(){

		@Override
		public void initialize() {
			addPropertyDescriptor(0, "Name", String.class, false);
			addPropertyDescriptor(1, "ID", Number.class, true);
			addPropertyDescriptor(2, "Color", ColorQuad.class, true);

			
		}

	};
	IPropertyAccessor access = new PropertyAccessor(this, propertyDescriptor){
		@Override
		public Object getProperty(IPropertyDescriptor ipd) {
			switch (ipd.getIndex()){
			case 0: return  NamedColor.this.getName();
			case 1: return  NamedColor.this.getID();
			case 2: return  NamedColor.this.getColor();
			}
			return null;
		}
	
		@Override
		public void setProperty(IPropertyDescriptor ipd, Object value) {
			switch (ipd.getIndex()){
			case 0:  NamedColor.this.setName((String) value); return;
			case 1:  NamedColor.this.setID((Long) value); return;
			case 2:  NamedColor.this.Set((ColorQuad) value); return;
			case 3:	 return;
			}
			
		}

	};

	// {{ IAccessorDelegates

	public Object getProperty(String name) {
		return access.getProperty(name);
	}

	public void setProperty(String name, Object value) {
		access.setProperty(name, value);
	}

	public Object getProperty(int index) {
		return access.getProperty(index);
	}

	public void setProperty(int index, Object value) {
		access.setProperty(index, value);
	}

	public Object getProperty(IPropertyDescriptor desc) {
		return access.getProperty(desc);
	}

	public void setProperty(IPropertyDescriptor desc, Object value) {
		access.setProperty(desc, value);
	}

	public IPropertyDescriptorList getDescriptors() {
		return access.getDescriptors();
	}
	

	// }}

}
