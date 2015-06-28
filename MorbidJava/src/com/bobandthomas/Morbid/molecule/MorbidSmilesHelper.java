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

import java.util.HashMap;
import java.util.Stack;

// TODO: Auto-generated Javadoc
/**
 * The Class MorbidSmilesHelper.
 * 
 * @author Thomas Kreek
 */
public class MorbidSmilesHelper {

	/** The atom stack. */
	Stack<Atom> atomStack;	
	
	/** The visited. */
	HashMap<Atom, Boolean> visited;
	
	/** The depth. */
	int depth = 0;
	
	/** The list. */
	Substructure list;

	/**
	 * Instantiates a new morbid smiles helper.
	 * 
	 * @param a
	 *            the a
	 */
	public MorbidSmilesHelper(Substructure a) {
		list = a;
		atomStack = new Stack<Atom>();
		visited = new HashMap<Atom,Boolean>();
		depth = 0;
	}
	
	/**
	 * Prints the smiles.
	 */
	public void printSmiles()
	{
		for (Atom a : atomStack)
		{
			System.out.print(a.getAtomType().getName() + " ");
		}
		System.out.println();
	}

	/**
	 * Calc smiles.
	 * 
	 * @param root
	 *            the root
	 */
	public void calcSmiles(Atom root) {
		if (root == null)
			return;
		if (!list.contains(root))
			return;
		depth++;
/*		for (int i = 0; i<depth; i++)
			System.out.print("  ");
		System.out.println(root.getName() + root.getID());
*/
		atomStack.push(root);
		visited.put(root, true);
		for (Atom n : root.getBonded()) {
			if (visited.get(n) == null)
				calcSmiles(n);
		}
		depth--;
	}

}
