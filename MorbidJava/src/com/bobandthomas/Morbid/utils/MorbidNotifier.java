package com.bobandthomas.Morbid.utils;

import java.util.HashSet;

public class MorbidNotifier<T extends MorbidEvent, L extends IMorbidListener<T>>  implements IMorbidNotifier<T, L> {

	/** The listeners. list of all the objects that want notifications when i change */
	HashSet<L> listeners; 
	Object parent;

	public MorbidNotifier(Object parent) {
		this.parent = parent;
	}
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IChangeNotifierTemplate#fireEvent(T)
	 */
	@Override
	public void fireEvent(T event)
	{
		if (listeners == null) 
		{
			return;
		}

		for (L cn : listeners)
		{
			cn.handleEvent(event);
		}
		
	}
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IChangeNotifierTemplate#registerListener(L)
	 */
	@Override
	public void registerListener(L listener) {
		if (listener == this) return;
		if (listeners == null)
			listeners = new HashSet<L>(5);
		listeners.add(listener);
	}
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IChangeNotifierTemplate#unRegisterListener(L)
	 */
	@Override
	public void unRegisterListener(L listener) {
		if (listeners == null) return;
		if (listener == null) return;
		listeners.remove(listener);
		
	}

}
