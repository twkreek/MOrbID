package com.bobandthomas.Morbid.utils;


public abstract class PropertyAccessor implements IPropertyAccessor {

	IPropertyDescriptor descriptor;

	public void addPropertyDescriptor(int i, String n, @SuppressWarnings("rawtypes") Class c, boolean e) {
		descriptor.addPropertyDescriptor(i, n, c, e);
	}


	public int getPropertyCount() {
		return descriptor.getPropertyCount();
	}


	public int getPropertyIndex(String name) {
		return descriptor.getPropertyIndex(name);
	}


	public PropertyAccessor(IPropertyDescriptor desc) {
		descriptor = desc;
	}


	@Override
	public Class<?> getPropertyClass(int index) {
		return descriptor.getPropertyClass(index);
	}


	@Override
	public String getPropertyName(int index) {
		return descriptor.getPropertyName(index);
	}


	@Override
	public boolean isPropertyEditable(int index) {
		return descriptor.isPropertyEditable(index);
	}


	@Override
	public Object getProperty(String name)
	{
		return getProperty(descriptor.getPropertyIndex(name));
	};

	@Override
	public void setProperty(String name, Object value) {
		setProperty(descriptor.getPropertyIndex(name), value);

	}
	
	@Override
	public void addProperty(String name, Object value) {
		descriptor.addPropertyDescriptor(descriptor.getPropertyCount(), name, value.getClass(), true);
		setProperty(name, value);
	
	}


}
