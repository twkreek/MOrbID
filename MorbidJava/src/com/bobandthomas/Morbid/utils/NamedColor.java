package com.bobandthomas.Morbid.utils;

import java.awt.Color;

import javax.vecmath.Color3f;

import com.bobandthomas.Morbid.molecule.MoleculeProperty;

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
	static IPropertyDescriptor propertyDescriptor = new PropertyDescriptorList<MoleculeProperty>(){

		@Override
		public void initialize() {
			addPropertyDescriptor(0, "Name", String.class, false);
			addPropertyDescriptor(1, "ID", Number.class, true);
			addPropertyDescriptor(2, "Color", ColorQuad.class, true);

			
		}

	};
	IPropertyAccessor access = new PropertyAccessor(propertyDescriptor){
		@Override
		public Object getProperty(int index) {
			switch (index){
			case 0: return  NamedColor.this.getName();
			case 1: return  NamedColor.this.getID();
			case 2: return  NamedColor.this.getColor();
			}
			return null;
		}
	
		@Override
		public void setProperty(int index, Object value) {
			switch (index){
			case 0:  NamedColor.this.setName((String) value); return;
			case 1:  NamedColor.this.setID((Long) value); return;
			case 2:  NamedColor.this.Set((ColorQuad) value); return;
			case 3:	 return;
			}
			
		}

	};
	
	// {{ IAccessorDelegates
	public void addPropertyDescriptor(int i, String n, @SuppressWarnings("rawtypes") Class c, boolean e) {
		access.addPropertyDescriptor(i, n, c, e);
	}

	public Object getProperty(int index) {
		return access.getProperty(index);
	}

	public int getPropertyCount() {
		return access.getPropertyCount();
	}

	public void setProperty(int index, Object value) {
		access.setProperty(index, value);
	}

	public int getPropertyIndex(String name) {
		return access.getPropertyIndex(name);
	}

	public Class<?> getPropertyClass(int index) {
		return access.getPropertyClass(index);
	}

	public String getPropertyName(int index) {
		return access.getPropertyName(index);
	}

	public boolean isPropertyEditable(int index) {
		return access.isPropertyEditable(index);
	}

	public Object getProperty(String name) {
		return access.getProperty(name);
	}

	public void setProperty(String name, Object value) {
		access.setProperty(name, value);
	}

	@Override
	public void addProperty(String name, Object value) {
		access.addProperty(name,  value);
		
	}
	// }}

}
