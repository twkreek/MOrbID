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

import java.util.EventListener;

// TODO: Auto-generated Javadoc
/**
 * The Interface IChangeNotifier.
 * The Notifier/Listener pattern for Morbid.  Allows all implementing objects to keep a list of 
 * listener objects, and objects that notify this objects.
 * 
 * @author Thomas Kreek The Interface IChangeNotifier.
 */
public interface IChangeNotifier extends EventListener {
	
	/**
	 * Gets the notify list.
	 * 
	 * @return the notify list
	 */
	public IChangeNotifier [] getNotifyList();
	
	/**
	 * Register listener.
	 * Adds a new listener to the current object.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void registerListener(IChangeNotifier listener);
	
	/**
	 * Un register listener.
	 * Removes the given listener from the current object.
	 * @param listener
	 *            the listener
	 */
	public void unRegisterListener(IChangeNotifier listener);
	
	/**
	 * Un register from all.
	 * Reomves this object from all notification chains.  
	 * 
	 * Always call this when discontinuing use of an object
	 * to prevent memory leaks.  
	 */
	public void unRegisterFromAll();
	
	/**
	 * Register notifier.
	 * Called for the reflexive mapping of objects that listen to this.
	 * @param notifier
	 *            the notifier
	 */
	public void registerNotifier(IChangeNotifier notifier);
	
	/**
	 * Notify change.
	 * The default simple notify call.  This notifies "this" that a change has occured
	 * by defulat this will create a full notification with a message and notify listeners
	 */
	public void notifyChange(); // to notify self of change, and invoke the notification mechanism;
	
	/**
	 * Notify change.
	 * This given an event, this function will handle the event, and decide
	 * whether to pass it on to the listeners.
	 * 
	 * @param source
	 *            the source - a MorbidEvent describing the nature of the change
	 */
	public void notifyChange(MorbidEvent source);
	
	/**
	 * Handle notify.
	 * The handler function for a notification.  Default implementation simply 
	 * returns the source. classes may override this handler to act on it and 
	 * modify the source, if approriate.
	 * @param source
	 *            the source - an event describing the change.
	 * @return the morbid event
	 */
	public abstract MorbidEvent handleNotify(MorbidEvent source);
}
