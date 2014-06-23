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

import java.util.HashSet;

// TODO: Auto-generated Javadoc
/**
 * The Class MorbidNotifier.
 * 
 * @author Thomas Kreek
 * @param <T>
 *            the generic type
 * @param <L>
 *            the generic type
 */
public class MorbidNotifier<T extends MorbidEvent, L extends IMorbidListener<T>>  implements IMorbidNotifier<T, L> {

	/** The listeners. list of all the objects that want notifications when i change */
	HashSet<L> listeners; 
	
	/** The parent. */
	Object parent;

	/**
	 * Instantiates a new morbid notifier.
	 * 
	 * @param parent
	 *            the parent
	 */
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
