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

public class SubstructureSetChooser extends JDialog implements TreeSelectionListener {
	SubstructureMap map = null;
	JTree tree;
	SubstructureChooserType dialogType;
	
	public enum SubstructureChooserType
	{
		SUBSTRUCTURE_SET,
		SUBSTRUCTURE,
		SUBSTRUCTURE_EDIT_REP
	}
	class SubstructureListTreeNode extends MorbidTreeNode
	{

		SubstructureSet list;
		SubstructureRepList reps;
		public SubstructureListTreeNode(SubstructureSet ls, SubstructureRepList reps) {
			super(ls);
			list = ls;
			this.reps = reps;
			if (reps == null)
				this.reps =list.getDefaultRep();
		}	
	}
	class SubstructureTreeNode extends MorbidTreeNode
	{
		Substructure subs;
		SubstructureRep rep;

		public SubstructureTreeNode(Substructure ls, SubstructureRep rep) {
			super(ls);
			subs = ls;
			this.rep = rep;
			}
		
	}
	
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
	MorbidTreeNode createSubstructureNodes(Substructure al, SubstructureRep rep)
	{
		SubstructureTreeNode child = new SubstructureTreeNode(al, rep);
		return child;
	}
	
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

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		
	}

}
