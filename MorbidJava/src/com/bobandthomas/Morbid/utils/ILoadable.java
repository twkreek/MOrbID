package com.bobandthomas.Morbid.utils;

public interface ILoadable {
	
	public boolean isDirty();
	public void markDirty();
	public void markClean();

	public String getName();
	public void setName(String n);
	
	public long ID();
	public void setID(long id);
	
	public void Use();
	public void Unuse();
	public boolean InUse();


}
