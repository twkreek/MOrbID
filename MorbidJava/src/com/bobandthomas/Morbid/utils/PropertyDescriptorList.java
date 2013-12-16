package com.bobandthomas.Morbid.utils;

import java.util.HashMap;

public abstract class PropertyDescriptorList<T extends IPropertyAccessor> extends CLoadableSet<PropertyDescriptor> implements IPropertyDescriptor
{
	HashMap<String, Integer> nameIndex = new HashMap<String,Integer>();
	public PropertyDescriptorList()
	{
		this.setUseByName(true);
		initialize();
	}
	
	@Override
	public int getPropertyCount() {return size(); }
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.UI.IPropertyAccess#getValue(java.lang.String)
	 */
	public abstract void initialize();
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.UI.IPropertyAccess#getType(int)
	 */
	public Class<?> getPropertyClass(int index)
	{
		return get(index).getClassType();
	}
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.UI.IPropertyAccess#getName(int)
	 */
	public String getPropertyName(int index)
	{
		return get(index).getName();
	}
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.UI.IPropertyAccess#isEditable(int)
	 */
	public boolean isPropertyEditable(int index)
	{
		return get(index).isEditable();
	}
	@Override
	public int getPropertyIndex(String name)
	{
		return nameIndex.get(name);
	}
	@Override
	public void addPropertyDescriptor(int i, String n, @SuppressWarnings("rawtypes") Class c,
			boolean e)
	{
		PropertyDescriptor mtc = new PropertyDescriptor(i, n,c,e);
		nameIndex.put(n, i);
		add(mtc);
	}	
}
