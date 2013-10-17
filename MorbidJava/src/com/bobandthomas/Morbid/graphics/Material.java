package com.bobandthomas.Morbid.graphics;
import com.bobandthomas.Morbid.utils.CLoadableItem;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.StaticColorQuad;

public class Material extends CLoadableItem
{ 		
		public enum StaticMaterial 
		{
			White ("White",1,255,255,255,0.4,0.5,0.7,1,0,10,1),
			Blue ("Blue",1,0,51,255,0.4,0.13,0.7,1,0,4,1),
			Green ("Green",1,0,255,0,0.4,0.5,0.5,1,0,10,1),
			Cyan ("Cyan",1,0,255,255,0.4,0.5,0.5,1,0,10,1),
			Red ("Red",1,255,0,0,0.4,0.13,0.5,1,0,10,1),
			Yellow ("Yellow",1,255,255,0,0.4,0.5,0.5,1,0,10,1),
			Magenta ("Magenta",1,255,0,255,0.4,0.5,0.5,1,0,10,1),
			LiteGray ("LiteGray",1,192,192,192,0.4,0.5,0.5,1,0,10,1),
			LiteBlue ("LiteBlue",1,100,100,255,0.4,0.5,0.5,1,0,10,1),
			LiteRed ("LiteRed",1,255,151,151,0.4,0.1,0.7,1,0,10,1),
			Black ("Black",1,0,0,0,0.8,0.14,0.5,1,0,10,1),
			YellowGreen ("YellowGreen",1,192,255,0,0.4,0.5,0.5,1,0,10,1),
			LiteGreen ("LiteGreen",1,100,255,100,0.4,0.5,0.5,1,0,10,1),
			LiteYellow ("LiteYellow",1,255,255,100,0.4,0.5,0.5,1,0,10,1),
			DarkGray ("DarkGray",1,128,128,128,0.4,0.5,0.5,1,0,10,1),
			Carbon ("Carbon",1,48,48,48,0.8,0.1,1,1,0,50,1),
			Hydrogen ("Hydrogen",1,204,201,207,0.4,0.5,0.7,0.7,1,10,0),
			Chrome ("Chrome",1,197,207,190,0.4,0.45,0.49,1,0,86,1),
			Transparent ("Transparent",1,0,0,0,0.8,0.7,0.7,0.49,1,10,1);
			String Name;
			double kd; 
			ColorQuad diffuse; 
			double ka;
			double ks;
			double ke;
			double filter;
			int specularity;
			boolean useSpecularity;
			boolean useFilter;
			
			StaticMaterial(String name, int kd, int r, int g, int b, double ka, double ks, double ke, double filter, int useFilter, int specularity, int useSpecularity)
			{
				Name = name;
				this.kd = kd;
				diffuse = new ColorQuad(r,g,b);
				this.ka = ka;
				this.ks = ks;
				this.ke = ke;
				this.filter = filter;
				this.useFilter = useFilter == 1;
				this.specularity = specularity;
				this.useSpecularity = useSpecularity == 1;
				
			}
			Material getMaterial()
			{
				Material mat = new Material();
				mat.setName(Name);
				mat.kDiffuse = kd;
				mat.setColor(new ColorQuad(diffuse));
				mat.kAmbient = ka;
				mat.kSpecularity = ks;
				mat.kEmission = ke;
				mat.alpha = filter;
				mat.useFilter = useFilter;
				mat.specularity = specularity;
				mat.useSpecularity = useSpecularity;
				return mat;
				
			}
		}

		private ColorQuad color; // the base color of the material
	 
		double kDiffuse; //diffuse
		double kAmbient; //ambient
		double kSpecularity; //specularity
		double kEmission;	//emission
		double alpha; //percent transmission for trnasparent objects. (alpha)
		int specularity; // specularity coeffiecient.
		boolean useSpecularity; 
		boolean useFilter;	

		
		public double getkDiffuse() {
			return kDiffuse;
		}
		public void setkDiffuse(double kDiffuse) {
			this.kDiffuse = kDiffuse;
		}
		public double getkAmbient() {
			return kAmbient;
		}
		public void setkAmbient(double kAmbient) {
			this.kAmbient = kAmbient;
		}
		public double getkSpecularity() {
			return kSpecularity;
		}
		public void setkSpecularity(double kSpecularity) {
			this.kSpecularity = kSpecularity;
		}
		public double getkEmission() {
			return kEmission;
		}
		public void setkEmission(double kEmission) {
			this.kEmission = kEmission;
		}
		public double getAlpha() {
			return alpha;
		}
		public void setAlpha(double alpha) {
			this.alpha = alpha;
			setUseFilter(true);
		}
		public int getSpecularity() {
			return specularity;
		}
		public void setSpecularity(int specularity) {
			this.specularity = specularity;
		}
		public boolean isUseSpecularity() {
			return useSpecularity;
		}
		public void setUseSpecularity(boolean useSpecularity) {
			this.useSpecularity = useSpecularity;
		}
		public boolean isUseFilter() {
			return useFilter;
		}
		public void setUseFilter(boolean useFilter) {
			this.useFilter = useFilter;
		}
	

		void Init()
		{
			kDiffuse = 0.5f;
			kAmbient = 0.5f;
			kSpecularity = 0.4f;
			kEmission = 0.5f;
			specularity = 10;
			useSpecularity = true;
			alpha = 1.0f;
			useFilter = false;
			setColor(StaticColorQuad.White.cq());
		}
		public Material()
		{
			Init();
		}

		public Material(ColorQuad cq)
		{
			Init();
			setColor(cq);
		}

		public Material(Material mat)
		{
			kAmbient = mat.kAmbient;
			kDiffuse = mat.kDiffuse;
			kEmission = mat.kEmission;
			setColor(mat.getColor());
			kSpecularity = mat.kSpecularity;
			specularity = mat.specularity;
			useSpecularity = mat.useSpecularity;
			alpha = mat.alpha;
			useFilter = mat.useFilter;
		}
		void  WriteItem(String str)
		{
			/*
			String s = new String();
			int length;
	
			length = sprintf_s(s, "\"%s\" %1d %3d %3d %3d %f %f %f %f %d %d %d\n",
				Name, diffuse.IsColor(), diffuse.R,diffuse.G,diffuse.B, ks, ka, kd, filter, useFilter, specularity, useSpecularity);
//			(m_pSet)->GetStream()->write(s, length);
 * 
 */
		}
		void ReadItem(String line)
		{
			/* TODO 
			array<Char> ^quotes = {'"'};
			array<String ^>^quoted = line->Split(quotes,StringSplitOptions::RemoveEmptyEntries);

			array<Char> ^seps = {' '};
			array<String ^> ^tokens = quoted[1]->Split(seps, StringSplitOptions::RemoveEmptyEntries);

			Name = quoted[0];
			bool isColor = Convert::ToInt32(tokens[0]) == 1;
			short r,g,b;
			r = Convert::ToInt16(tokens[1]);
			g = Convert::ToInt16(tokens[2]);
			b = Convert::ToInt16(tokens[3]);
			diffuse = ColorQuad(r,g,b);
			ks = Convert::ToSingle(tokens[4]);
			ka = Convert::ToSingle(tokens[5]);
			kd = Convert::ToSingle(tokens[6]);
			filter = Convert::ToSingle(tokens[7]);
			useFilter = Convert::ToInt32(tokens[8]) == 1;
			specularity = Convert::ToInt32(tokens[9]);
			useSpecularity = Convert::ToInt32(tokens[10]) == 1;
			return OKCODE;
		char s[500];
			char name[50];
			short r,g,b;
			short isColor;
			short temp;
			int length;

			// (MaterialList *)m_pSet)->GetStream()
			using (System::IO::StreamReader sr = File.OpenText())
		    {
		        m_sName = sr.ReadLine();
				
		        while ((input=sr.ReadLine())!=null) 
		        {
		            Console.WriteLine(input);
		        }
				
		        sr.Close();
		    }
			

			*stream >> isColor >> r >> g >> b;
			*stream >> ks >> ka >> kd;
			*stream >> filter >> temp;  useFilter = temp;
			*stream >> specularity >> temp; useSpecularity = temp;
			if (isColor)
				diffuse = ColorQuad (r,g,b);
			stream->getline(s, sizeof(s));
			*/
		}
		public ColorQuad getColor() {
			return color;
		}
		public void setColor(ColorQuad diffuse) {
			this.color = diffuse;
		}



};
