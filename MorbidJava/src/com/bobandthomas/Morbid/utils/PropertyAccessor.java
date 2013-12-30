package com.bobandthomas.Morbid.utils;

public abstract class PropertyAccessor implements IPropertyAccessor {

	IPropertyDescriptorList descriptorList;
	Object parent;

	public IPropertyDescriptorList getDescriptors()
	{
		return descriptorList;
	}
	public IPropertyDescriptor getDescriptor(int index) {
		return descriptorList.getDescriptor(index);
	}

	public void addPropertyDescriptor(int i, String n, Class<?> c, boolean e,
			IPropertySetter ips) {
		descriptorList.addPropertyDescriptor(i, n, c, e, ips);
	}

	public void addPropertyDescriptor(int i, String n,
			@SuppressWarnings("rawtypes") Class c, boolean e) {
		descriptorList.addPropertyDescriptor(i, n, c, e);
	}

	public int getPropertyCount() {
		return descriptorList.getPropertyCount();
	}

	public int getPropertyIndex(String name) {
		return descriptorList.getPropertyIndex(name);
	}

	public PropertyAccessor(Object parent, IPropertyDescriptorList desc) {
		descriptorList = desc;
		this.parent = parent;
	}

	public Object get(Object parent, int index) {
		IPropertySetter setter;
		if ((setter = descriptorList.getDescriptor(index).getSetter()) == null)
			return null;
		return setter.get(parent);
	}

	public boolean set(Object parent, int index, Object value) {
		IPropertySetter setter;
		if ((setter = descriptorList.getDescriptor(index).getSetter()) == null)
			return false;
		setter.set(parent, value);
		return true;
	}

	@Override
	public Object getProperty(String name) {
		IPropertyDescriptor descriptor = descriptorList.getDescriptor(name);
		if (descriptor.getSetter() != null) {
			Object obj = descriptor.getSetter().get(parent);
			if (obj != null)
				return obj;
		}
		return getProperty(descriptorList.getDescriptor(name));
	};

	@Override
	public void setProperty(String name, Object value) {
		IPropertyDescriptor descriptor = descriptorList.getDescriptor(name);
		if (descriptor.getSetter() != null) {
			if (descriptor.getSetter().set(parent, value))
				return;

		}
		setProperty(descriptorList.getDescriptor(name), value);

	}

	@Override
	public Object getProperty(int index) {
		IPropertyDescriptor descriptor = descriptorList.getDescriptor(index);
		if (descriptor.getSetter() != null) {
			Object obj = descriptor.getSetter().get(parent);
			if (obj != null)
				return obj;
		}
		return getProperty(descriptorList.getDescriptor(index));
	}

	@Override
	public void setProperty(int index, Object value) {
		IPropertyDescriptor descriptor = descriptorList.getDescriptor(index);
		if (descriptor.getSetter() != null) {
			if (descriptor.getSetter().set(parent, value))
				return;

		}
		setProperty(descriptorList.getDescriptor(index), value);
	}

}
