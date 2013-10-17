package com.bobandthomas.Morbid.utils;

/**
 * @author Thomas
 * Temporary base class for all readable elements - serializable or DB.
 */
public class CLoadableItem implements ILoadable {
	
	public CLoadableSet<?> parentSet;

	private String Name;
	private boolean m_bDirty;
	public int useCount;
	private long ID;
	
	public int getUseCount() {
		return useCount;
	}
	public void setUseCount(int useCount) {
		this.useCount = useCount;
	}
	public long getID() {
		return ID;
	}
	@Override
	public boolean isDirty() {return m_bDirty;}
	@Override
	public void markDirty() {
		m_bDirty = true;
		if (parentSet != null)
		{
			parentSet.notifyChange();
		}
	}
	@Override
	public void markClean() {
		m_bDirty = false;
	}
	

	public CLoadableItem() {
		// TODO Auto-generated constructor stub
		ID = 0;
		useCount = 0;
		m_bDirty = true;
	}
	@Override
	public String getName() {
		return Name;
	}
	@Override
	public void setName(String n) {
		Name = n;
		
	}
	@Override
	public long ID() {
		return ID;
	}
	@Override
	public void setID(long id) {
		ID = id;		
	}
	@Override
	public void Use() {
		useCount++;
		
	}
	@Override
	public void Unuse() {
		useCount--;
		
	}
	@Override
	public boolean InUse() {
		return useCount != 0;
	}
	public Object getParentSet() {
		return parentSet;
	}
	public void setParentSet(CLoadableSet<?> parentSet) {
		this.parentSet = parentSet;
	}

}
