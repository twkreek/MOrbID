package com.bobandthomas.Morbid.utils;
import com.bobandthomas.Morbid.utils.MorbidEvent;

public class Selectable implements ISelectable {
	private boolean selected;
	private CLoadableItem item;
	public Selectable(CLoadableItem item) {
		selected = false;
		this.item = item;
	}

	@Override
	public void setSelected(boolean selected) {
		setSelected(null, selected);

	}

	@Override
	public boolean isSelected() {
		return selected;
	}

	@Override
	public void setSelected(IChangeNotifier target, boolean selected) {
		if (this.selected == selected) return;
		MorbidEvent event = new MorbidEvent(item);
		event.setTarget(target);
		
		event.setChangeField("selected", this.selected, selected);
		
		this.selected = selected;
		if (item.getClass().isAssignableFrom(IChangeNotifier.class))
		((IChangeNotifier) item).notifyChange(event);
		
	}

}
