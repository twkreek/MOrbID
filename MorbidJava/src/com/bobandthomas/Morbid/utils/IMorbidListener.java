package com.bobandthomas.Morbid.utils;

public interface IMorbidListener<T extends MorbidEvent> {
	
	public void handleEvent(T event);
}
