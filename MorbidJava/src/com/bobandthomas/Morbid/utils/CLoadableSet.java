package com.bobandthomas.Morbid.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public class CLoadableSet <T extends CLoadableItem> extends CLoadableItem implements List <T>, ILoadableSet/* document level */
{
	ArrayList <T> set;
	HashMap<String, T> nameMap;
	boolean useByName = false; // whether we can search elements by name;
	boolean reParent = true;  // this Determines if an element added to this set should have the parent set to "this". This should be set to false if this is only keeping referenes
	
	
	public CLoadableSet() {
		set = new ArrayList <T>();
		nameMap = new HashMap<String, T> ();
		this.useByName = false;
		this.reParent = true;
	}

	public boolean isUseByName() {
		return useByName;
	}
	public void setUseByName(boolean useByName) {
		this.useByName = useByName;
	}
	public boolean isReParent() {
		return reParent;
	}

	public void setReParent(boolean reParent) {
		this.reParent = reParent;
	}

	public T getByName(String s)
	{
		return nameMap.get(s);
	}
	public Set getByNameSet()
	{
		return nameMap.entrySet();
	}
	
	void addToMap(T arg1)
	{
		if (useByName)
		{
			nameMap.put(arg1.getName(), arg1);
		}
	}
	@Override
	public void add(int arg0, T arg1) {
		set.add(arg0, arg1);
		addToMap(arg1);
		if (reParent) arg1.setParentSet(this);
	}
	@Override
	public boolean add(T arg0) {
		addToMap(arg0);
		if (reParent) arg0.setParentSet(this);
		return set.add(arg0);
	}
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
	@Override
	public void clear() {
		set.clear();
	}
	@Override
	public boolean contains(Object arg0) {
		return set.contains(arg0);
	}
	@Override
	public boolean containsAll(Collection<?> arg0) {
		return set.containsAll(arg0);
	}
	public void ensureCapacity(int arg0) {
		set.ensureCapacity(arg0);
	}
	@Override
	public boolean equals(Object arg0) {
		return set.equals(arg0);
	}
	@Override
	public T get(int arg0) {
		return set.get(arg0);
	}
	@Override
	public int hashCode() {
		return set.hashCode();
	}
	@Override
	public int indexOf(Object arg0) {
		return set.indexOf(arg0);
	}
	@Override
	public boolean isEmpty() {
		return set.isEmpty();
	}
	@Override
	public Iterator<T> iterator() {
		return set.iterator();
	}
	@Override
	public int lastIndexOf(Object arg0) {
		return set.lastIndexOf(arg0);
	}
	@Override
	public ListIterator<T> listIterator() {
		return set.listIterator();
	}
	@Override
	public ListIterator<T> listIterator(int arg0) {
		return set.listIterator(arg0);
	}
	@Override
	public T remove(int arg0) {
		return set.remove(arg0);
	}
	//@Override
	public T removeList(Object arg0) {
		set.remove(arg0);
		return nameMap.remove(arg0);
	}
	@Override
	public boolean removeAll(Collection<?> arg0) {
		return set.removeAll(arg0);
	}
	@Override
	public boolean retainAll(Collection<?> arg0) {
		return set.retainAll(arg0);
	}
	@Override
	public T set(int arg0, T arg1) {
		return set.set(arg0, arg1);
	}
	@Override
	public int size() {
		return set.size();
	}
	@Override
	public List<T> subList(int arg0, int arg1) {
		return set.subList(arg0, arg1);
	}
	@Override
	public Object[] toArray() {
		return set.toArray();
	}
	@Override
	public <T> T[] toArray(T[] arg0) {
		return set.toArray(arg0);
	}
	@Override
	public String toString() {
		return set.toString();
	}
	public void trimToSize() {
		set.trimToSize();
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

}
