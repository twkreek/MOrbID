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
package com.bobandthomas.Morbid.molecule.UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import com.bobandthomas.Morbid.UI.MorbidTreeNode;
import com.bobandthomas.Morbid.molecule.Substructure;
import com.bobandthomas.Morbid.molecule.SubstructureSet;
import com.bobandthomas.Morbid.molecule.SubstructureMap;
import com.bobandthomas.Morbid.molecule.SubstructureRep;
import com.bobandthomas.Morbid.molecule.SubstructureRepList;

// TODO: Auto-generated Javadoc
/**
 * The Class SubstructureSetChooser.
 * 
 * @author Thomas Kreek
 */
public class SubstructureSetChooser extends JDialog implements TreeSelectionListener {
	
	/** The map. */
	SubstructureMap map = null;
	
	/** The tree. */
	JTree tree;
	
	/** The dialog type. */
	SubstructureChooserType dialogType;
	
	/**
	 * The Enum SubstructureChooserType.
	 * 
	 * @author Thomas Kreek
	 */
	public enum SubstructureChooserType
	{
		
		/** The substructure set. */
		SUBSTRUCTURE_SET,
		
		/** The substructure. */
		SUBSTRUCTURE,
		
		/** The substructure edit rep. */
		SUBSTRUCTURE_EDIT_REP
	}
	
	/**
	 * The Class SubstructureListTreeNode.
	 * 
	 * @author Thomas Kreek
	 */
	class SubstructureListTreeNode extends MorbidTreeNode
	{

		/** The list. */
		SubstructureSet list;
		
		/** The reps. */
		SubstructureRepList reps;
		
		/**
		 * Instantiates a new substructure list tree node.
		 * 
		 * @param ls
		 *            the ls
		 * @param reps
		 *            the reps
		 */
		public SubstructureListTreeNode(SubstructureSet ls, SubstructureRepList reps) {
			super(ls);
			list = ls;
			this.reps = reps;
			if (reps == null)
				this.reps =list.getDefaultRep();
		}	
	}
	
	/**
	 * The Class SubstructureTreeNode.
	 * 
	 * @author Thomas Kreek
	 */
	class SubstructureTreeNode extends MorbidTreeNode
	{
		
		/** The subs. */
		Substructure subs;
		
		/** The rep. */
		SubstructureRep rep;

		/**
		 * Instantiates a new substructure tree node.
		 * 
		 * @param ls
		 *            the ls
		 * @param rep
		 *            the rep
		 */
		public SubstructureTreeNode(Substructure ls, SubstructureRep rep) {
			super(ls);
			subs = ls;
			this.rep = rep;
			}
		
	}
	
	/**
	 * Creates a substructure list nodes.
	 * 
	 * @param als
	 *            the als
	 * @param reps
	 *            the reps
	 * @return the morbid tree node
	 */
	MorbidTreeNode createSubstructureListNodes(SubstructureSet als, SubstructureRepList reps)
	{
		SubstructureListTreeNode child = new SubstructureListTreeNode(als, reps);
		if (dialogType != SubstructureChooserType.SUBSTRUCTURE_SET)
		{
			for (Substructure al: als)
				child.add(createSubstructureNodes(al, (reps == null)? null : reps.get(al)));
		}
		return child;
		
	}
	
	/**
	 * Creates a substructure nodes.
	 * 
	 * @param al
	 *            the al
	 * @param rep
	 *            the rep
	 * @return the morbid tree node
	 */
	MorbidTreeNode createSubstructureNodes(Substructure al, SubstructureRep rep)
	{
		SubstructureTreeNode child = new SubstructureTreeNode(al, rep);
		return child;
	}
	
	/**
	 * Instantiates a new substructure set chooser.
	 * 
	 * @param ssm
	 *            the ssm
	 * @param rep
	 *            the rep
	 */
	public SubstructureSetChooser(SubstructureMap ssm, SubstructureRepList rep)
	{
		map = ssm;
		
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(200, 200));
		
	    DefaultMutableTreeNode top =
	            new DefaultMutableTreeNode("Substructures");
	    for (SubstructureSet als: map)
	    	top.add(createSubstructureListNodes(als, (rep != null && rep.getParentSet() == als)? rep: null));
	    this.setMinimumSize(new Dimension(150,200));
	    
		tree = new JTree(top);
		if (dialogType == SubstructureChooserType.SUBSTRUCTURE_SET)
		{
		    tree.getSelectionModel().setSelectionMode
            			(TreeSelectionModel.SINGLE_TREE_SELECTION);
		}
 		tree.addTreeSelectionListener(this);
		add(tree, "Center");
		
		
	}
	
	/**
	 * Select substructure rep.
	 * 
	 * @param ssm
	 *            the ssm
	 * @return the substructure rep list
	 */
	public SubstructureRepList selectSubstructureRep(SubstructureMap ssm)
	{
		Object[] list = new Object[ssm.size()];
		int i=0;
		for (SubstructureSet als: ssm)
			list[i++] = als.getName() + " - " + als.getDescription();
		
		String s = (String)JOptionPane.showInputDialog(
                this,
                "select a SubstructureSet",
                "Customized Dialog",
                JOptionPane.PLAIN_MESSAGE,
                null,
                list,
                "");
		if (s == null || s.length() == 0) return null;
		String [] tokens = s.split(" - ");
		SubstructureSet ssl =  ssm.getByName(tokens[0]);
		if (ssl == null) return null;
		return ssl.getDefaultRep();
			

	}

	/* (non-Javadoc)
	 * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		
	}

}
