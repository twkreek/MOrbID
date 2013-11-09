package com.bobandthomas.Morbid.utils;

public interface IChangeNotifier {
	public IChangeNotifier [] getNotifyList();
	public void registerListener(IChangeNotifier listener);
	public void unRegisterListener(IChangeNotifier listener);
	public void unRegisterFromAll();
	public void registerNotifier(IChangeNotifier notifier);
	public void notifyChange(); // to notify self of change, and invoke the notification mechanism;
	public void notifyChange(MorbidEvent source);
	public abstract MorbidEvent handleNotify(MorbidEvent source);
}
