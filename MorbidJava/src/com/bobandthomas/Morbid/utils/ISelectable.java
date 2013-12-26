package com.bobandthomas.Morbid.utils;

public interface ISelectable {
	public void setSelected(boolean selected);
	public void setSelected(IChangeNotifier target, boolean selected);
	public boolean isSelected();

}
