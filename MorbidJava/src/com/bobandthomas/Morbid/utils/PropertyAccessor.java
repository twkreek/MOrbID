package com.bobandthomas.Morbid.utils;


public abstract class PropertyAccessor implements IPropertyAccessor {

	IPropertyDescriptor descriptor;

	public int getPropertyCount() {
		return descriptor.getPropertyCount();
	}


	public int getIndex(String name) {
		return descriptor.getIndex(name);
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
		return getProperty(descriptor.getIndex(name));
	};

	@Override
	public void setProperty(String name, Object value) {
		setProperty(descriptor.getIndex(name), value);

	}


}
