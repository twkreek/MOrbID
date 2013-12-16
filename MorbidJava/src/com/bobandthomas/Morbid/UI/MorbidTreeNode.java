package com.bobandthomas.Morbid.UI;

import com.bobandthomas.Morbid.utils.CLoadableItem;
import javax.swing.tree.DefaultMutableTreeNode;
public class MorbidTreeNode extends DefaultMutableTreeNode
{
	CLoadableItem item;
	public MorbidTreeNode(CLoadableItem ls)
	{
		item = ls;
	}
	public String toString()
	{
		return item.getName();
	}
}
