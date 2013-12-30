package com.bobandthomas.Morbid.utils;

import java.io.Serializable;

/**
 * The Class CLoadableItem.
 * 
 * @author Thomas Kreek 
 * Base class for all readable elements - serializable or
 *         DB. and all change notifiable items; Implements name, ID, and
 *         selectable
 */
public class CLoadableItem implements IChangeNotifier, Serializable
 {
	
	/** The parent set. Used when item is added to a reparenting CLoadableSet */
	public CLoadableSet<?> parentSet;
	
	/** The id. */
	private long ID;
	
	/** dirty flag for change notification. */
	private boolean m_bDirty;
	
	
	/** The Name. */
	private String Name;
	
	/**
	 * Instantiates a new CLoadabelItem.
	 */
	public CLoadableItem() {
		ID = 0;
		m_bDirty = true;

		
	}
	
	/**
	 * Checks if is type. Helper Function
	 * 
	 * @param c
	 *            the class to compare
	 * @return true, if is of same class as c
	 */
	public boolean isType(@SuppressWarnings("rawtypes") Class c)
	{
		return (this.getClass().equals(c));
	}
	
	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public long getID() {
		return ID;
	}
	
	/* 
	 * returns the ID
	 */
	public long ID() {
		return ID;
	}

	/* 
	 * returns the Item's name
	*/
	public String getName() {
		return Name;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override 
	public String toString()
	{
		return Name;
	}
	
	/**
	 * Gets the parent set.
	 * 
	 * @return the parent set
	 */
	public Object getParentSet() {
		return parentSet;
	}
	

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.ChangeNotifier#handleNotify(com.bobandthomas.Morbid.utils.MorbidEvent)
	 */
//	@Override
	public MorbidEvent handleNotify(MorbidEvent source) {
		return source;
		
	}
	
	/* 
	* identifies if object has been changed, since its event cascade hs been completed.
	 */
	
	public boolean isDirty() {return m_bDirty;}
	
	/* 
	 * Mark the item as clean (downstream events have been handled)
	 */
	public void markClean() {
		m_bDirty = false;
	}
	
	/* 
	 * mark the item as dirty.  says that the item has changed, and 
	 * subscribing objects must all complete before it can be clean.
	 */
	
	public void markDirty() {
		m_bDirty = true;
		notifyChange(new MorbidEvent(this, "dirty"));
		if (parentSet != null)
		{
			parentSet.markDirty();
		}
	}
	public void markDirty(MorbidEvent event) {
		m_bDirty = true;
		if (event != null) notifyChange(event);
		if (parentSet != null)
		{
			parentSet.markDirty(event);
		}
	}

	/* 
	 * Sets the context specific ID of this Item
	 */

	public void setID(long id) {
		ID = id;		
	}
	
	/* 
	 * Sets the name of this Item
	 */
	public void setName(String n) {
		Name = n;
		
	}
	
	/**
	 * Sets the parent set.
	 * A CLoadableItem can have only one parent
	 * This gets set when it is added to a CLoadableSet that has the reparent flag set
	 * @param parentSet
	 *            the new parent set
	 */
	public void setParentSet(CLoadableSet<?> parentSet) {
		if (parentSet != null)
			this.unRegisterListener(parentSet);
		this.parentSet = parentSet;
		this.registerListener(parentSet);
	}
	
	// {{ Delegate IChangeNotifier
	protected ChangeNotifier notifier = new ChangeNotifier(this);

	public IChangeNotifier[] getNotifyList() {
		return notifier.getNotifyList();
	}

	public void registerListener(IChangeNotifier listener) {
		notifier.registerListener(listener);
	}

	public void unRegisterListener(IChangeNotifier listener) {
		notifier.unRegisterListener(listener);
	}

	public void unRegisterFromAll() {
		notifier.unRegisterFromAll();
	}

	public void registerNotifier(IChangeNotifier notifier) {
		notifier.registerNotifier(notifier);
	}

	public void notifyChange() {
		notifier.notifyChange();
	}

	public void notifyChange(MorbidEvent source) {
		notifier.notifyChange(source);
	}
	// }}
}
