package com.bobandthomas.Morbid.wrapper;

import com.bobandthomas.Morbid.utils.ILoadable;

import javax.swing.tree.DefaultMutableTreeNode;
public class MorbidTreeNode extends DefaultMutableTreeNode
{
	ILoadable item;
	public MorbidTreeNode(ILoadable ls)
	{
		item = ls;
	}
	public String toString()
	{
		return item.getName();
	}
}
