package com.bobandthomas.Morbid.utils;

import java.util.EventListener;

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
