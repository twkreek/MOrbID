package com.bobandthomas.Morbid.utils;

public interface IMorbidNotifier<T extends MorbidEvent, L extends IMorbidListener<T>>  {

	public void fireEvent(T event);

	public void registerListener(L listener);

	public void unRegisterListener(L listener);

}