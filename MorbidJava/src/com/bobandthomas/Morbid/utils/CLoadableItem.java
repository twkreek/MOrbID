/*
 * 
	MOrbID - Molecular Orbital Interactive Display

MOrbID is Copyright (c) 1996-2014 by Thomas W. Kreek


Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

 */
package com.bobandthomas.Morbid.utils;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
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
	/**
	 * Id.
	 * 
	 * @return the long
	 */
	public long ID() {
		return ID;
	}

	/* 
	 * returns the Item's name
	*/
	/**
	 * Gets the name.
	 * 
	 * @return the name
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
	
	/**
	 * Checks if is dirty.
	 * 
	 * @return true, if is dirty
	 */
	public boolean isDirty() {return m_bDirty;}
	
	/* 
	 * Mark the item as clean (downstream events have been handled)
	 */
	/**
	 * Mark clean.
	 */
	public void markClean() {
		m_bDirty = false;
	}
	
	/* 
	 * mark the item as dirty.  says that the item has changed, and 
	 * subscribing objects must all complete before it can be clean.
	 */
	
	/**
	 * Mark dirty.
	 */
	public void markDirty() {
		m_bDirty = true;
		notifyChange(new MorbidEvent(this, "dirty"));
		if (parentSet != null)
		{
			parentSet.markDirty();
		}
	}
	
	/**
	 * Mark dirty.
	 * 
	 * @param event
	 *            the event
	 */
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

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setID(long id) {
		ID = id;		
	}
	
	/* 
	 * Sets the name of this Item
	 */
	/**
	 * Sets the name.
	 * 
	 * @param n
	 *            the new name
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
	/** The notifier. */
	protected ChangeNotifier notifier = new ChangeNotifier(this);

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IChangeNotifier#getNotifyList()
	 */
	public IChangeNotifier[] getNotifyList() {
		return notifier.getNotifyList();
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IChangeNotifier#registerListener(com.bobandthomas.Morbid.utils.IChangeNotifier)
	 */
	public void registerListener(IChangeNotifier listener) {
		notifier.registerListener(listener);
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IChangeNotifier#unRegisterListener(com.bobandthomas.Morbid.utils.IChangeNotifier)
	 */
	public void unRegisterListener(IChangeNotifier listener) {
		notifier.unRegisterListener(listener);
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IChangeNotifier#unRegisterFromAll()
	 */
	public void unRegisterFromAll() {
		notifier.unRegisterFromAll();
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IChangeNotifier#registerNotifier(com.bobandthomas.Morbid.utils.IChangeNotifier)
	 */
	public void registerNotifier(IChangeNotifier notifier) {
		notifier.registerNotifier(notifier);
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IChangeNotifier#notifyChange()
	 */
	public void notifyChange() {
		notifier.notifyChange();
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IChangeNotifier#notifyChange(com.bobandthomas.Morbid.utils.MorbidEvent)
	 */
	public void notifyChange(MorbidEvent source) {
		notifier.notifyChange(source);
	}
	// }}
}
