package com.bobandthomas.Morbid.utils;

/**
 * @author Thomas
 * Temporary base class for all readable elements - serializable or DB.
 * and all change notifiable items;
 */
public class CLoadableItem extends ChangeNotifier implements ILoadable, IChangeNotifier {
	
	public CLoadableSet<?> parentSet;

	private long ID;
	private boolean m_bDirty;
	private String Name;
	
	public CLoadableItem() {
		// TODO Auto-generated constructor stub
		ID = 0;
		m_bDirty = true;
		
	}
	public long getID() {
		return ID;
	}
	@Override
	public long ID() {
		return ID;
	}

	@Override
	public String getName() {
		return Name;
	}
	public Object getParentSet() {
		return parentSet;
	}
	

	@Override
	public MorbidEvent handleNotify(MorbidEvent source) {
		return source;
		
	}
	@Override
	public boolean isDirty() {return m_bDirty;}
	public void markClean() {
		m_bDirty = false;
	}
	@Override
	public void markDirty() {
		m_bDirty = true;
		if (parentSet != null)
		{
			parentSet.markDirty();
		}
		notifyChange(new MorbidEvent(this));
	}

	@Override
	public void setID(long id) {
		ID = id;		
	}
	@Override
	public void setName(String n) {
		Name = n;
		
	}
	public void setParentSet(CLoadableSet<?> parentSet) {
		this.parentSet = parentSet;
	}

}
