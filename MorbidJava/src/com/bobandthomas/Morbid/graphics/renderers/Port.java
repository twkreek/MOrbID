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
package com.bobandthomas.Morbid.graphics.renderers;

import com.bobandthomas.Morbid.utils.BoundingBox;
import com.bobandthomas.Morbid.utils.CLoadableItem;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.IChangeNotifier;
import com.bobandthomas.Morbid.utils.IMorbidListener;
import com.bobandthomas.Morbid.utils.IMorbidNotifier;
import com.bobandthomas.Morbid.utils.MorbidEvent;
import com.bobandthomas.Morbid.utils.MorbidNotifier;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.Point3DList;

// TODO: Auto-generated Javadoc
/**
 * The Class Port.
 * 
 * @author Thomas Kreek
 */
public abstract class Port extends CLoadableItem implements IMorbidNotifier<Port.PortChangeEvent, Port.PortChangeListener> {
	
	/**
	 * The Enum PortCapabilities.
	 * 
	 * @author Thomas Kreek
	 */
	public enum PortCapabilities
	{
		
		/** vector based port.  */
		VECTOR, 
 /** bitmap based */
 BITMAP, 
 /**  client 3D based. i.e. GL This implies view transformation matrix is in the port so we don't pre apply it. */
 THREE_D, 
 /** port data is serialized - stream or file based must use open/close */
 SERIALIZED, 
 /** doublebuffer support. */
 DOUBLEBUFFER, 
 /** monochrome only */
 MONOCHROME
	};
		
		/**
		 * The Class PortChangeEvent.
		 * 
		 * @author Thomas Kreek
		 */
		static class PortChangeEvent extends MorbidEvent {
			
			/**
			 * The Enum PortChangeEventType.
			 * 
			 * @author Thomas Kreek
			 */
			public enum PortChangeEventType
			{
				
				/** The resized. */
				RESIZED, 
 /** The closed. */
 CLOSED, 
 /** The moved. */
 MOVED, 
 /** The cleared. */
 CLEARED;
			}; 
			
			/** The type. */
			public PortChangeEventType type;
			
			/**
			 * Instantiates a new port change event.
			 * 
			 * @param item
			 *            the item
			 */
			public PortChangeEvent(IChangeNotifier item) {
				super(item);
				speciallistener = PortChangeListener.class;
			}
		}
		
		/**
		 * The listener interface for receiving portChange events. The class
		 * that is interested in processing a portChange event implements this
		 * interface, and the object created with that class is registered with
		 * a component using the component's
		 * <code>addPortChangeListener<code> method. When
		 * the portChange event occurs, that object's appropriate
		 * method is invoked.
		 * 
		 * @see PortChangeEvent
		 */
		interface PortChangeListener extends IMorbidListener<PortChangeEvent> {
		}
		
		/** The port notifier. */
		MorbidNotifier<PortChangeEvent, PortChangeListener> portNotifier = new MorbidNotifier<PortChangeEvent, PortChangeListener>(this);

		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.utils.IMorbidNotifier#fireEvent(com.bobandthomas.Morbid.utils.MorbidEvent)
		 */
		public void fireEvent(PortChangeEvent event) {
			portNotifier.fireEvent(event);
		}
		
		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.utils.IMorbidNotifier#registerListener(com.bobandthomas.Morbid.utils.IMorbidListener)
		 */
		public void registerListener(PortChangeListener listener) {
			portNotifier.registerListener(listener);
		}
		
		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.utils.IMorbidNotifier#unRegisterListener(com.bobandthomas.Morbid.utils.IMorbidListener)
		 */
		public void unRegisterListener(PortChangeListener listener) {
			portNotifier.unRegisterListener(listener);
		}

		/** The screen bounds. */
		BoundingBox screenBounds;
		
		/**
		 * Instantiates a new port.
		 */
		public Port()
		{
			screenBounds = new BoundingBox();
			
		}
		
		/**
		 * Checks if is capable of.
		 * 
		 * @param cap
		 *            the cap
		 * @return true, if is capable of
		 */
		public abstract boolean isCapableOf(PortCapabilities cap);
		
		/**
		 * Sets the screen bounds.
		 * 
		 * @param bt
		 *            the bt
		 */
		protected void SetScreenBounds(BoundingBox bt)
		{
			screenBounds = bt;
		}
		
		/**
		 * Gets the screen bounds.
		 * 
		 * @return the bounding box
		 */
		public BoundingBox GetScreenBounds()
		{
			return screenBounds;
		}


		/**
		 * Sets the parent.
		 * 
		 * @param p
		 *            the p
		 */
		abstract void SetParent(Port p);
		
		/**
		 *  Called at the end of a render cycle to release resources or close files
		 */
		abstract void Close();

		/** The Monochrome. */
		boolean Monochrome;

		/**
		 * Initialize the port for an individual render cycle. such as opening files, allocating bitmaps
		 */
		abstract void Open();
		
		/**
		 * Clear the port to blank
		 */
		public abstract void Clear();
// doublebuffer routines
		/** The Double buffer. */
public boolean DoubleBuffer;
//		PortBuffer Buffer;
		/**
 * Swap buffers for ports that support double buffering
 */
public abstract void SwapBuffers();
// color setting routines
		/**
 * Background color.
 * 
 * @param cq
 *            the cq
 */
public abstract void BackgroundColor(ColorQuad cq);
		
		/**
		 * Fill color.
		 * 
		 * @param cq
		 *            the cq
		 */
		public abstract void FillColor(ColorQuad cq);
		
		/**
		 * Frame color.
		 * 
		 * @param cq
		 *            the cq
		 */
		public abstract void FrameColor(ColorQuad cq);
		
		/**
		 * Text color.
		 * 
		 * @param cq
		 *            the cq
		 */
		public abstract void TextColor(ColorQuad cq);
// Vector primitives - valid for PortCapabilities.VECTOR
		/**
 * Move to.
 * 
 * @param p
 *            the p
 */
public abstract void MoveTo( Point3D p);
		
		/**
		 * Line to.
		 * 
		 * @param p
		 *            the p
		 */
		public abstract void LineTo( Point3D p);
		
		/**
		 * Vector.
		 * 
		 * @param p1
		 *            the p1
		 * @param p2
		 *            the p2
		 */
		public abstract void Vector( Point3D p1, Point3D p2);
		
		/**
		 * Circle.
		 * 
		 * @param c
		 *            the c
		 * @param r
		 *            the r
		 */
		public abstract void Circle( Point3D c, Point3D r);
		
		/**
		 * Text.
		 * 
		 * @param p
		 *            the p
		 * @param string
		 *            the string
		 */
		public abstract void Text(Point3D p, String string);
		
		/**
		 * Polygon.
		 * 
		 * @param plist
		 *            the plist
		 */
		public abstract void Polygon(Point3DList plist);

// Bitmap primitives - valid for PortCapabilities.BITMAP	
		/**
 * Draw point.
 * 
 * @param p
 *            the p
 */
public abstract void DrawPoint(Point3D p);
		
		/**
		 * Draw point.
		 * 
		 * @param p
		 *            the p
		 * @param cq
		 *            the cq
		 */
		public abstract void DrawPoint(Point3D p, ColorQuad cq);
		
		/**
		 * Gets the point.
		 * 
		 * @param p
		 *            the p
		 * @return the color quad
		 */
		public abstract ColorQuad GetPoint(Point3D p);
		
};