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
package com.bobandthomas.Morbid.molecule;

import java.util.Iterator;
import java.util.ListIterator;

// TODO: Auto-generated Javadoc
/**
 * The Interface ISubstructure.
 * 
 * @author Thomas Kreek
 */
public interface ISubstructure {

	/**
	 * Iterator.
	 * 
	 * @return the iterator
	 */
	public Iterator<Atom> iterator();

	/**
	 * List iterator.
	 * 
	 * @return the list iterator
	 */
	public ListIterator<Atom> listIterator();

	/**
	 * Gets the by name.
	 * 
	 * @param s
	 *            the s
	 * @return the by name
	 */
	public Atom getByName(String s);

	/**
	 * Adds the.
	 * 
	 * @param arg0
	 *            the arg0
	 * @return true, if successful
	 */
	public boolean add(Atom arg0);

	/**
	 * Contains.
	 * 
	 * @param arg0
	 *            the arg0
	 * @return true, if successful
	 */
	public boolean contains(Object arg0);

	/**
	 * Gets the.
	 * 
	 * @param arg0
	 *            the arg0
	 * @return the atom
	 */
	public Atom get(int arg0);

	/**
	 * Index of.
	 * 
	 * @param arg0
	 *            the arg0
	 * @return the int
	 */
	public int indexOf(Object arg0);
	
	/**
	 * Size.
	 * 
	 * @return the int
	 */
	public int size();

}
