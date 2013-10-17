package com.bobandthomas.Morbid.graphics;

import com.bobandthomas.Morbid.utils.BoxType;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.Point3DList;

public abstract class Port {

		BoxType screenBounds;
		public Port()
		{
			screenBounds = new BoxType();
			
		}
		protected void SetScreenBounds(BoxType bt)
		{
			screenBounds = bt;
		}
		BoxType GetScreenBounds()
		{
			return screenBounds;
		}


		abstract void SetParent(Port p);
		abstract void Release();

		boolean Monochrome;

//		abstract void Copy(MCCIBitmap bm);
		abstract void Initialize();
		abstract void Clear();
// doublebuffer routines
		public boolean DoubleBuffer;
//		PortBuffer Buffer;
		public abstract void SwapBuffers();
// color setting routines
		abstract void BackgroundColor(ColorQuad cq);
		abstract void FillColor(ColorQuad cq);
		abstract void FrameColor(ColorQuad cq);
		abstract void TextColor(ColorQuad cq);
// primitives
		abstract void MoveTo( Point3D p);
		abstract void LineTo( Point3D p);
		abstract void Vector( Point3D p1, Point3D p2);
		abstract void Circle( Point3D c, Point3D r);
		abstract void DrawPoint(Point3D p);
		abstract void DrawPoint(Point3D p, ColorQuad cq);
		abstract ColorQuad GetPoint(Point3D p);
		abstract void Text(Point3D p, String string);
		abstract void Polygon(Point3DList plist);

};