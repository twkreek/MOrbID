package com.bobandthomas.Morbid.utils;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class MPropertyDescriptorList implements IPropertyDescriptorList
{

	ArrayList<IPropertyDescriptor> list = new ArrayList<IPropertyDescriptor>();
	HashMap<String, IPropertyDescriptor> nameIndex = new HashMap<String,IPropertyDescriptor>();
	public MPropertyDescriptorList()
	{
//		this.setUseByName(true);
		initialize();
	}
	
	@Override
	public int getPropertyCount() {return list.size(); }
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.UI.IPropertyAccess#getValue(java.lang.String)
	 */
	public abstract void initialize();
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.UI.IPropertyAccess#getType(int)
	 */
	public Class<?> getPropertyClass(int index)
	{
		return list.get((int) index).getClassType();
	}
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.UI.IPropertyAccess#getName(int)
	 */
	public String getPropertyName(int index)
	{
		return list.get(index).getName();
	}
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.UI.IPropertyAccess#isEditable(int)
	 */
	public boolean isPropertyEditable(int index)
	{
		return list.get(index).isEditable();
	}
	@Override
	public int getPropertyIndex(String name)
	{
		return (int) nameIndex.get(name).getIndex();
	}
	@Override
	public void addPropertyDescriptor(int i, String n, @SuppressWarnings("rawtypes") Class c,
			boolean e)
	{
		MPropertyDescriptor mtc = new MPropertyDescriptor(i, n,c,e);
		nameIndex.put(n, mtc);
		list.add(mtc);
	}	
	public void addPropertyDescriptor(int i, String n, @SuppressWarnings("rawtypes") Class c,
			boolean e, IPropertySetter ips)
	{
		MPropertyDescriptor mtc = new MPropertyDescriptor(i, n,c,e, ips);
		nameIndex.put(n, mtc);
		list.add(mtc);
	}	
	@Override
	public IPropertyDescriptor getDescriptor(int index) {
		return list.get((int) index);
	}
	
	@Override
	public IPropertyDescriptor getDescriptor(String name)
	{
		return nameIndex.get(name);
	}


}
