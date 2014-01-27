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

public abstract class Port extends CLoadableItem implements IMorbidNotifier<Port.PortChangeEvent, Port.PortChangeListener> {
	public enum PortCapabilities
	{
		VECTOR, BITMAP, THREE_D, SERIALIZED, DOUBLEBUFFER, MONOCHROME
	};
		static class PortChangeEvent extends MorbidEvent {
			public enum PortChangeEventType
			{
				RESIZED, CLOSED, MOVED, CLEARED;
			}; 
			public PortChangeEventType type;
			public PortChangeEvent(IChangeNotifier item) {
				super(item);
				speciallistener = PortChangeListener.class;
			}
		}
		interface PortChangeListener extends IMorbidListener<PortChangeEvent> {
		}
		MorbidNotifier<PortChangeEvent, PortChangeListener> portNotifier = new MorbidNotifier<PortChangeEvent, PortChangeListener>(this);

		public void fireEvent(PortChangeEvent event) {
			portNotifier.fireEvent(event);
		}
		public void registerListener(PortChangeListener listener) {
			portNotifier.registerListener(listener);
		}
		public void unRegisterListener(PortChangeListener listener) {
			portNotifier.unRegisterListener(listener);
		}

		BoundingBox screenBounds;
		public Port()
		{
			screenBounds = new BoundingBox();
			
		}
		public abstract boolean isCapableOf(PortCapabilities cap);
		protected void SetScreenBounds(BoundingBox bt)
		{
			screenBounds = bt;
		}
		public BoundingBox GetScreenBounds()
		{
			return screenBounds;
		}


		abstract void SetParent(Port p);
		abstract void Release();

		boolean Monochrome;

		abstract void Initialize();
		public abstract void Clear();
// doublebuffer routines
		public boolean DoubleBuffer;
//		PortBuffer Buffer;
		public abstract void SwapBuffers();
// color setting routines
		public abstract void BackgroundColor(ColorQuad cq);
		public abstract void FillColor(ColorQuad cq);
		public abstract void FrameColor(ColorQuad cq);
		public abstract void TextColor(ColorQuad cq);
// Vector primitives - valid for PortCapabilities.VECTOR
		public abstract void MoveTo( Point3D p);
		public abstract void LineTo( Point3D p);
		public abstract void Vector( Point3D p1, Point3D p2);
		public abstract void Circle( Point3D c, Point3D r);
		public abstract void Text(Point3D p, String string);
		public abstract void Polygon(Point3DList plist);

// Bitmap primitives - valid for PortCapabilities.BITMAP	
		public abstract void DrawPoint(Point3D p);
		public abstract void DrawPoint(Point3D p, ColorQuad cq);
		public abstract ColorQuad GetPoint(Point3D p);
		
};