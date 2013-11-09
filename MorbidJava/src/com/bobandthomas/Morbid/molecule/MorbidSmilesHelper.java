package com.bobandthomas.Morbid.molecule;

import java.util.HashMap;
import java.util.Stack;

public class MorbidSmilesHelper {

	Stack<Atom> atomStack;	
	HashMap<Atom, Boolean> visited;
	int depth = 0;
	Substructure list;

	public MorbidSmilesHelper(Substructure a) {
		list = a;
		atomStack = new Stack<Atom>();
		visited = new HashMap<Atom,Boolean>();
		depth = 0;
	}
	
	public void printSmiles()
	{
		for (Atom a : atomStack)
		{
			System.out.print(a.getAtomType().getName() + " ");
		}
		System.out.println();
	}

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
