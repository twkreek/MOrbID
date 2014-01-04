package com.bobandthomas.Morbid.utils;

import java.util.EventListener;
import java.util.HashSet;

import com.bobandthomas.Morbid.wrapper.Logger;

/**
 * The Class ChangeNotifier.
 * Default implementation of the IChangeNotifier interface.
 * 
 * @author Thomas Kreek 
 */
public  class ChangeNotifier implements IChangeNotifier, EventListener {
	IChangeNotifier parent;

	/** The listeners. list of all the objects that want notifications when i change */
	HashSet<IChangeNotifier> listeners; 
	
	/** The notifiers. the list of all items that i am listening to. */
	HashSet<IChangeNotifier> notifiers; 
	
	/**
	 * Instantiates a new change notifier.
	 */
	public ChangeNotifier(IChangeNotifier parent) {
		this.parent = parent;
		listeners = null;
	}

	boolean logThis = true;
	public void logThis(boolean logMe)
	{
		logThis = logMe;
	}
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IChangeNotifier#getNotifyList()
	 */
	@Override
	public IChangeNotifier[] getNotifyList() {
		if (listeners == null) return null;
		return listeners.toArray( new IChangeNotifier[listeners.size()]);
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IChangeNotifier#registerListener(com.bobandthomas.Morbid.utils.IChangeNotifier)
	 */
	@Override
	public void registerListener(IChangeNotifier listener) {
		if (listener == this) return;
		if (listeners == null)
			listeners = new HashSet<IChangeNotifier>(5);
		listeners.add(listener);
		listener.registerNotifier(this);
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IChangeNotifier#notifyChange(com.bobandthomas.Morbid.utils.MorbidEvent)
	 */
	@Override
	public void notifyChange(MorbidEvent event) {
		// do not respond to an event i sent myself.
		if (! event.getSource().equals(parent))
		{
			MorbidEvent newEvent = parent.handleNotify(event);
			if (newEvent == null) return;
			newEvent.handledBy(parent);

		}
		if (listeners == null) 
		{
//			Logger.addMessage(event);
			return;
		}

		for (IChangeNotifier cn : listeners)
		{
			cn.notifyChange(event);
		}
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IChangeNotifier#notifyChange()
	 */
	@Override
	public void notifyChange() {
		notifyChange(new MorbidEvent(this));
		
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IChangeNotifier#unRegisterListener(com.bobandthomas.Morbid.utils.IChangeNotifier)
	 */
	@Override
	public void unRegisterListener(IChangeNotifier listener) {
		if (listeners == null) return;
		if (listener == null) return;
		listeners.remove(listener);
		
	}
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IChangeNotifier#registerNotifier(com.bobandthomas.Morbid.utils.IChangeNotifier)
	 */
	public void registerNotifier(IChangeNotifier notifier)
	{
		if (notifiers == null)
			notifiers = new HashSet<IChangeNotifier>(5);
		notifiers.add(notifier);
	
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IChangeNotifier#unRegisterFromAll()
	 */
	@Override
	public void unRegisterFromAll() {
		if (notifiers == null) return;

		for (IChangeNotifier cn : notifiers)
		{
			cn.unRegisterListener(this);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IChangeNotifier#handleNotify(com.bobandthomas.Morbid.utils.MorbidEvent)
	 */
	@Override
	public MorbidEvent handleNotify(MorbidEvent source) {
		return source;
	}

}
