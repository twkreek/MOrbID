package com.bobandthomas.Morbid.wrapper;

import java.util.ArrayList;
import java.util.HashMap;

import com.bobandthomas.Morbid.utils.CLoadableItem;
import com.bobandthomas.Morbid.utils.IPropertyAccessor;

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
	protected class Message 
	{
		CLoadableItem obj;
		String message;
		MessageLevel level;
		public Message(CLoadableItem obj, String message, MessageLevel level) {
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
			String s = obj.getName()+" says:\n" + message + " as " + level.toString();
			return s;
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
		get().createMessage(null, e.toString(), MessageLevel.ERROR);
		e.printStackTrace();
	}
	public static void addMessage(CLoadableItem o, Exception e)
	{
		get().createMessage(o, e.toString(), MessageLevel.ERROR);
		e.printStackTrace();
	}
	public void createMessage(CLoadableItem o, String message, MessageLevel ml)
	{
		Message m = new Logger.Message(o,message,ml);
		messages.add(m);	
		MessageList mlist;
		if ((mlist = messageMap.get(o)) == null)
		{
			mlist = new MessageList();
			messageMap.put(o, mlist);
		}
		mlist.add(m);
		reportNext();
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
			System.out.println(messages.get(lastReported).report());
		}
	}
}
