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
package com.bobandthomas.Morbid.utils;
import com.bobandthomas.Morbid.utils.MorbidEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class Selectable.
 * 
 * @author Thomas Kreek
 */
public class Selectable implements ISelectable {
	
	/** The selected. */
	private boolean selected;
	
	/** The item. */
	private CLoadableItem item;
	
	/**
	 * Instantiates a new selectable.
	 * 
	 * @param item
	 *            the item
	 */
	public Selectable(CLoadableItem item) {
		selected = false;
		this.item = item;
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.ISelectable#setSelected(boolean)
	 */
	@Override
	public void setSelected(boolean selected) {
		setSelected(null, selected);

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.ISelectable#isSelected()
	 */
	@Override
	public boolean isSelected() {
		return selected;
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.ISelectable#setSelected(com.bobandthomas.Morbid.utils.IChangeNotifier, boolean)
	 */
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
