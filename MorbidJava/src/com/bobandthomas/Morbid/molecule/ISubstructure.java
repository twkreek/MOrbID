package com.bobandthomas.Morbid.molecule;

import java.util.Iterator;
import java.util.ListIterator;

public interface ISubstructure{

	public Iterator<Atom> iterator();

	public ListIterator<Atom> listIterator();

	public Atom getByName(String s);

	public boolean add(Atom arg0);

	public boolean contains(Object arg0);

	public Atom get(int arg0);

	public int indexOf(Object arg0);

	public int size();

}
