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


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

// TODO: Auto-generated Javadoc
/**
 * The Class CLoadableSet. Provides the following funcionalities 1) creates a
 * list of items of type T and supports List<T> functionality 2) provides
 * optional lookup by Item name (from getName()) (off by default) 3) provides
 * optional parenting of child Items by default, so Items can refer to parent
 * and find siblings
 * 
 * @author Thomas Kreek
 * @param <T>
 *            the generic type, must be derived from CLoadableItem
 */
public class CLoadableSet <T extends CLoadableItem> extends CLoadableItem implements List <T>, IChangeNotifier
{
	
	/** The set. */
	private ArrayList <T> set;
	
	/** The name map. */
	HashMap<String, T> nameMap;
	
	/** whether we can search elements by name;. */
	private boolean useByName = false; 
	/** this Determines if an element added to this set should have the parent set to "this".
	 *  This should be set to false if this is only keeping referenes 
	*/

	boolean reParent = true;  
	
	
	/**
	 * Instantiates a new c loadable set.
	 */
	public CLoadableSet() {
		set = new ArrayList <T>();
		nameMap = new HashMap<String, T> ();
		this.useByName = false;
		this.reParent = true;
	}

	/**
	 * Checks if name lookup is enabled.
	 * 
	 * @return true, if by name is enabled
	 */
	public boolean isUseByName() {
		return useByName;
	}
	
	/**
	 * sets whether Items can be looked up by name.
	 * 
	 * @param useByName
	 *            the new whether we can search elements by name;
	 */
	public void setUseByName(boolean useByName) {
		this.useByName = useByName;
	}
	
	/**
	 * Checks if Items are told this Set is their parent.
	 * 
	 * @return true, if reparenting
	 */
	public boolean isReParent() {
		return reParent;
	}

	/**
	 * Sets if Items are told this Set is their parent.
	 * 
	 * @param reParent
	 *            whether to reparent
	 */
	public void setReParent(boolean reParent) {
		this.reParent = reParent;
	}

	/**
	 * Gets the Item by name.
	 * 
	 * @param s
	 *            the s
	 * @return the by name
	 */
	public T getByName(String s)
	{
		return nameMap.get(s);
	}
	
	/**
	 * Gets the by name set.
	 * 
	 * @param arg1
	 *            the arg1
	 * @return the by name set
	 * 
	 *         public Set<?> getByNameSet() { return nameMap.entrySet(); }
	 */
	
	/**
	 * Adds the to map.
	 * 
	 * @param arg1
	 *            the arg1
	 */
	private void addToMap(T arg1)
	{
		if (useByName)
		{
			nameMap.put(arg1.getName(), arg1);
		}
	}
	
	// {{ Delegates for List<T>
	
	/* (non-Javadoc)
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	@Override
	public void add(int arg0, T arg1) {
		set.add(arg0, arg1);
		addToMap(arg1);
		if (reParent) arg1.setParentSet(this);
	}
	
	/* (non-Javadoc)
	 * @see java.util.List#add(java.lang.Object)
	 */
	@Override
	public boolean add(T arg0) {
		addToMap(arg0);
		if (reParent) arg0.setParentSet(this);
		return set.add(arg0);
	}
	
	/* (non-Javadoc)
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends T> arg0) {
		if (useByName)
		{
			for (T e: arg0) {
				addToMap(e);
			}
		}
		if (reParent) for (T e: arg0) e.setParentSet(this);
		return set.addAll(arg0);
	}
	
	/* (non-Javadoc)
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	@Override
	public boolean addAll(int arg0, Collection<? extends T> arg1) {
		if (useByName)
		{
			for (T e: arg1) {
				addToMap(e);
			}
		}
		if (reParent) for (T e: arg1) e.setParentSet(this);
		return set.addAll(arg0, arg1);
	}
	
	/* (non-Javadoc)
	 * @see java.util.List#clear()
	 */
	@Override
	public void clear() {
		set.clear();
	}
	
	/* (non-Javadoc)
	 * @see java.util.List#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object arg0) {
		return set.contains(arg0);
	}
	
	/* (non-Javadoc)
	 * @see java.util.List#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection<?> arg0) {
		return set.containsAll(arg0);
	}
	
	/**
	 * Ensure capacity.
	 * 
	 * @param arg0
	 *            the arg0
	 */
	public void ensureCapacity(int arg0) {
		set.ensureCapacity(arg0);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0) {
		return set.equals(arg0);
	}
	
	/* (non-Javadoc)
	 * @see java.util.List#get(int)
	 */
	@Override
	public T get(int arg0) {
		return set.get(arg0);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return set.hashCode();
	}
	
	/* (non-Javadoc)
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	@Override
	public int indexOf(Object arg0) {
		return set.indexOf(arg0);
	}
	
	/* (non-Javadoc)
	 * @see java.util.List#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return set.isEmpty();
	}
	
	/* (non-Javadoc)
	 * @see java.util.List#iterator()
	 */
	@Override
	public Iterator<T> iterator() {
		return set.iterator();
	}
	
	/* (non-Javadoc)
	 * @see java.util.List#lastIndexOf(java.lang.Object)
	 */
	@Override
	public int lastIndexOf(Object arg0) {
		return set.lastIndexOf(arg0);
	}
	
	/* (non-Javadoc)
	 * @see java.util.List#listIterator()
	 */
	@Override
	public ListIterator<T> listIterator() {
		return set.listIterator();
	}
	
	/* (non-Javadoc)
	 * @see java.util.List#listIterator(int)
	 */
	@Override
	public ListIterator<T> listIterator(int arg0) {
		return set.listIterator(arg0);
	}
	
	/* (non-Javadoc)
	 * @see java.util.List#remove(int)
	 */
	@Override
	public T remove(int arg0) {
		return set.remove(arg0);
	}
	//@Override
	/**
	 * Removes the list.
	 * 
	 * @param arg0
	 *            the arg0
	 * @return the t
	 */
	public T removeList(Object arg0) {
		set.remove(arg0);
		return nameMap.remove(arg0);
	}
	
	/* (non-Javadoc)
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection<?> arg0) {
		return set.removeAll(arg0);
	}
	
	/* (non-Javadoc)
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(Collection<?> arg0) {
		return set.retainAll(arg0);
	}
	
	/* (non-Javadoc)
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	@Override
	public T set(int arg0, T arg1) {
		return set.set(arg0, arg1);
	}
	
	/* (non-Javadoc)
	 * @see java.util.List#size()
	 */
	@Override
	public int size() {
		return set.size();
	}
	
	/* (non-Javadoc)
	 * @see java.util.List#subList(int, int)
	 */
	@Override
	public List<T> subList(int arg0, int arg1) {
		return set.subList(arg0, arg1);
	}
	
	/* (non-Javadoc)
	 * @see java.util.List#toArray()
	 */
	@Override
	public Object[] toArray() {
		return set.toArray();
	}
	
	/* (non-Javadoc)
	 * @see java.util.List#toArray(java.lang.Object[])
	 */
	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] arg0) {
		return set.toArray(arg0);
	}
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.CLoadableItem#toString()
	 */
	@Override
	public String toString() {
		return set.toString();
	}
	
	/**
	 * Trim to size.
	 */
	public void trimToSize() {
		set.trimToSize();
	}

	/* (non-Javadoc)
	 * @see java.util.List#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object o) {
		return set.remove(o);
	}
	// }}

}
