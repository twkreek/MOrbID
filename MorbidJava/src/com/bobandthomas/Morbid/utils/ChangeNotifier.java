package com.bobandthomas.Morbid.utils;

import java.util.HashSet;

public abstract class ChangeNotifier implements IChangeNotifier {

	HashSet<IChangeNotifier> listeners; // list of all the objects that want notifications when i change
	HashSet<IChangeNotifier> notifiers; // the list of all items that i am listening to.
	
	public ChangeNotifier() {
		listeners = null;
	}

	@Override
	public IChangeNotifier[] getNotifyList() {
		if (listeners == null) return null;
		return listeners.toArray( new IChangeNotifier[listeners.size()]);
	}

	@Override
	public void registerListener(IChangeNotifier listener) {
		if (listener == this) return;
		if (listeners == null)
			listeners = new HashSet<IChangeNotifier>(5);
		listeners.add(listener);
		listener.registerNotifier(this);
	}

	@Override
	public void notifyChange(MorbidEvent source) {
		MorbidEvent newEvent = handleNotify(source);
		if (newEvent == null) return;
		if (listeners == null) return;

		for (IChangeNotifier cn : listeners)
		{
			cn.notifyChange(newEvent);
		}
	}

	@Override
	public void notifyChange() {
		notifyChange(new MorbidEvent(this));
		
	}

	@Override
	public void unRegisterListener(IChangeNotifier listener) {
		if (listeners == null) return;
		if (listener == null) return;
		listeners.remove(listener);
		
	}
	
	public void registerNotifier(IChangeNotifier notifier)
	{
		if (notifiers == null)
			notifiers = new HashSet<IChangeNotifier>(5);
		notifiers.add(notifier);
	
	}

	@Override
	public void unRegisterFromAll() {
		if (notifiers == null) return;

		for (IChangeNotifier cn : notifiers)
		{
			cn.unRegisterListener(this);
		}
		
	}

}
