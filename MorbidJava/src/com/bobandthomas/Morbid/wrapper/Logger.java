package com.bobandthomas.Morbid.wrapper;

import java.util.ArrayList;
import java.util.HashMap;

import com.bobandthomas.Morbid.utils.CLoadableItem;
import com.bobandthomas.Morbid.utils.MorbidEvent;

public class Logger {
	public enum MessageLevel
	{
		DEBUG ("Debug"),
		INFORMATION ("Information"),
		WARNING ("Warning"),
		ERROR ("ERROR"),
		FATAL ("*FATAL*");
		MessageLevel(String s)
		{
			
		}
	}
	private static class LoggerWrapper extends CLoadableItem
	{
		public Object o;
		LoggerWrapper(Object object)
		{
			o = object;
			setName(o.getClass().getSimpleName());
		}
	}
	protected static class Message 
	{
		Object obj;
		String message;
		MessageLevel level;
		public Message()
		{
			obj = null;
			message = null;
		}
		public Message(Object obj, String message, MessageLevel level) {
			super();
			this.obj = obj;
			this.message = message;
			this.level = level;
		}
		public String shortReport()
		{
			return message;
		}
		public String report()
		{
			String s = obj.getClass().getSimpleName() +" says:\n" + message /* + " as " + level.toString() */;
			return s;
		}
	}
	protected class EventMessage extends Message
	{
		MorbidEvent event;
		@Override
		public String shortReport() {
			return event.toString();
		}

		@Override
		public String report() {
			return event.toString();
		}

		public EventMessage(MorbidEvent ev)
		{
			event = ev;
		}
	}
	int lastReported;
	class MessageList extends ArrayList<Message>
	{
	}
	MessageList messages;
	HashMap<Object, MessageList> messageMap;
	static Logger cpi = null;
	
	static Logger get()
	{
		if (cpi == null)
			cpi = new Logger();
		return cpi;
	}

	public Logger() {
		lastReported = 0;
		messages = new MessageList();
		messageMap = new HashMap<Object,MessageList>();
		
	}

	public static void addMessage(String message)
	{
		get().createMessage(null, message, MessageLevel.INFORMATION);
	}
	public static void addMessage(CLoadableItem o, String message)
	{
		get().createMessage(o, message, MessageLevel.INFORMATION);
	}
	public static void addMessage(CLoadableItem o, String message, MessageLevel ml)
	{
		get().createMessage(o,message,ml);
	}
	public static void addMessage(CLoadableItem o)
	{
		get().createMessage(o, o.toString(), MessageLevel.INFORMATION);
	}
	public static void addMessage(Object o, Exception e)
	{
		get().createMessage(new LoggerWrapper(o), e.toString(), MessageLevel.ERROR);
		e.printStackTrace();
	}
	public static void addMessage(CLoadableItem o, Exception e)
	{
		get().createMessage(o, e.toString(), MessageLevel.ERROR);
		e.printStackTrace();
	}
	public static void addMessage(MorbidEvent event)
	{
		get().createMessage(event);
	}
	private void addMessage(Message m)
	{
		messages.add(m);	
		MessageList mlist;
		if ((mlist = messageMap.get(m.obj)) == null)
		{
			mlist = new MessageList();
			messageMap.put(m.obj, mlist);
		}
		mlist.add(m);
		reportNext();		
	}
	public void createMessage(Object o, String message, MessageLevel ml)
	{
		Message m = new Logger.Message(o,message,ml);
		addMessage(m); 

	}
	public void createMessage(MorbidEvent e)
	{
		Message m = new Logger.EventMessage(e);
		addMessage(m);

	}
	public void reportAll()
	{
		lastReported = -1;
		for (Message m : messages)
		{
			lastReported ++;
			System.out.println(m.report());
		}
	}
	public void reportNext()
	{
		for (; lastReported < messages.size(); lastReported++)
		{
			System.out.println("|" + messages.get(lastReported).report() + "|");
		}
	}
}
