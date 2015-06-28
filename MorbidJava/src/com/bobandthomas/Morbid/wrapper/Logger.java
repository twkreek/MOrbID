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
package com.bobandthomas.Morbid.wrapper;

import java.util.ArrayList;
import java.util.HashMap;

import com.bobandthomas.Morbid.utils.CLoadableItem;
import com.bobandthomas.Morbid.utils.MorbidEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class Logger.
 * 
 * @author Thomas Kreek
 */
public class Logger {
	
	/**
	 * The Enum MessageLevel.
	 * 
	 * @author Thomas Kreek
	 */
	public enum MessageLevel
	{
		
		/** The debug. */
		DEBUG ("Debug"),
		
		/** The information. */
		INFORMATION ("Information"),
		
		/** The warning. */
		WARNING ("Warning"),
		
		/** The error. */
		ERROR ("ERROR"),
		
		/** The fatal. */
		FATAL ("*FATAL*");
		
		/**
		 * Instantiates a new message level.
		 * 
		 * @param s
		 *            the s
		 */
		MessageLevel(String s)
		{
			
		}
	}
	
	/**
	 * The Class LoggerWrapper.
	 * 
	 * @author Thomas Kreek
	 */
	private static class LoggerWrapper extends CLoadableItem
	{
		
		/** The o. */
		public Object o;
		
		/**
		 * Instantiates a new logger wrapper.
		 * 
		 * @param object
		 *            the object
		 */
		LoggerWrapper(Object object)
		{
			o = object;
			setName(o.getClass().getSimpleName());
		}
	}
	
	/**
	 * The Class Message.
	 * 
	 * @author Thomas Kreek
	 */
	protected static class Message 
	{
		
		/** The obj. */
		Object obj;
		
		/** The message. */
		String message;
		
		/** The level. */
		MessageLevel level;
		
		/**
		 * Instantiates a new message.
		 */
		public Message()
		{
			obj = null;
			message = null;
		}
		
		/**
		 * Instantiates a new message.
		 * 
		 * @param obj
		 *            the obj
		 * @param message
		 *            the message
		 * @param level
		 *            the level
		 */
		public Message(Object obj, String message, MessageLevel level) {
			super();
			this.obj = obj;
			this.message = message;
			this.level = level;
		}
		
		/**
		 * Short report.
		 * 
		 * @return the string
		 */
		public String shortReport()
		{
			return message;
		}
		
		/**
		 * Report.
		 * 
		 * @return the string
		 */
		public String report()
		{
			String s = obj.getClass().getSimpleName() +" says:\n" + message /* + " as " + level.toString() */;
			return s;
		}
	}
	
	/**
	 * The Class EventMessage.
	 * 
	 * @author Thomas Kreek
	 */
	protected class EventMessage extends Message
	{
		
		/** The event. */
		MorbidEvent event;
		
		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.wrapper.Logger.Message#shortReport()
		 */
		@Override
		public String shortReport() {
			return event.toString();
		}

		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.wrapper.Logger.Message#report()
		 */
		@Override
		public String report() {
			return event.toString();
		}

		/**
		 * Instantiates a new event message.
		 * 
		 * @param ev
		 *            the ev
		 */
		public EventMessage(MorbidEvent ev)
		{
			event = ev;
		}
	}
	
	/** The last reported. */
	int lastReported;
	
	/**
	 * The Class MessageList.
	 * 
	 * @author Thomas Kreek
	 */
	class MessageList extends ArrayList<Message>
	{
	}
	
	/** The messages. */
	MessageList messages;
	
	/** The message map. */
	HashMap<Object, MessageList> messageMap;
	
	/** The cpi. */
	static Logger cpi = null;
	
	/**
	 * Gets the.
	 * 
	 * @return the logger
	 */
	static Logger get()
	{
		if (cpi == null)
			cpi = new Logger();
		return cpi;
	}

	/**
	 * Instantiates a new logger.
	 */
	public Logger() {
		lastReported = 0;
		messages = new MessageList();
		messageMap = new HashMap<Object,MessageList>();
		
	}

	/**
	 * Adds the message.
	 * 
	 * @param message
	 *            the message
	 */
	public static void addMessage(String message)
	{
		get().createMessage(null, message, MessageLevel.INFORMATION);
	}
	
	/**
	 * Adds the message.
	 * 
	 * @param o
	 *            the o
	 * @param message
	 *            the message
	 */
	public static void addMessage(CLoadableItem o, String message)
	{
		get().createMessage(o, message, MessageLevel.INFORMATION);
	}
	
	/**
	 * Adds the message.
	 * 
	 * @param o
	 *            the o
	 * @param message
	 *            the message
	 * @param ml
	 *            the ml
	 */
	public static void addMessage(CLoadableItem o, String message, MessageLevel ml)
	{
		get().createMessage(o,message,ml);
	}
	
	/**
	 * Adds the message.
	 * 
	 * @param o
	 *            the o
	 */
	public static void addMessage(CLoadableItem o)
	{
		get().createMessage(o, o.toString(), MessageLevel.INFORMATION);
	}
	
	/**
	 * Adds the message.
	 * 
	 * @param o
	 *            the o
	 * @param e
	 *            the e
	 */
	public static void addMessage(Object o, Exception e)
	{
		get().createMessage(new LoggerWrapper(o), e.toString(), MessageLevel.ERROR);
		e.printStackTrace();
	}
	
	/**
	 * Adds the message.
	 * 
	 * @param o
	 *            the o
	 * @param e
	 *            the e
	 */
	public static void addMessage(CLoadableItem o, Exception e)
	{
		get().createMessage(o, e.toString(), MessageLevel.ERROR);
		e.printStackTrace();
	}
	
	/**
	 * Adds the message.
	 * 
	 * @param event
	 *            the event
	 */
	public static void addMessage(MorbidEvent event)
	{
		get().createMessage(event);
	}
	
	/**
	 * Adds the message.
	 * 
	 * @param m
	 *            the m
	 */
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
	
	/**
	 * Creates a message.
	 * 
	 * @param o
	 *            the o
	 * @param message
	 *            the message
	 * @param ml
	 *            the ml
	 */
	public void createMessage(Object o, String message, MessageLevel ml)
	{
		Message m = new Logger.Message(o,message,ml);
		addMessage(m); 

	}
	
	/**
	 * Creates a message.
	 * 
	 * @param e
	 *            the e
	 */
	public void createMessage(MorbidEvent e)
	{
		Message m = new Logger.EventMessage(e);
		addMessage(m);

	}
	
	/**
	 * Report all.
	 */
	public void reportAll()
	{
		lastReported = -1;
		for (Message m : messages)
		{
			lastReported ++;
			System.out.println(m.report());
		}
	}
	
	/**
	 * Report next.
	 */
	public void reportNext()
	{
		for (; lastReported < messages.size(); lastReported++)
		{
			System.out.println("|" + messages.get(lastReported).report() + "|");
		}
	}
}
