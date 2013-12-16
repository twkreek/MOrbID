package com.bobandthomas.Morbid.molecule;

import com.bobandthomas.Morbid.graphics.Material;
import com.bobandthomas.Morbid.graphics.MaterialSet;
import com.bobandthomas.Morbid.utils.CLoadableItem;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.IPropertyAccessor;
import com.bobandthomas.Morbid.utils.IPropertyDescriptor;
import com.bobandthomas.Morbid.utils.PropertyAccessor;
import com.bobandthomas.Morbid.utils.PropertyDescriptorList;
import com.bobandthomas.Morbid.wrapper.CSVFileReader;

public class AtomType extends CLoadableItem
{
		public double zS;
		public double zP;
		public double zD;
		double radius;
		int ns;
		int np;
		int nd;
		double electronegativity;
		
		public ColorQuad color;
		public Material mat;
		String fullName;
		public AtomType()
		{
		
		}


		int GetAtomicNumber() { return (int) getID(); }
		void SetAtomicNumber(int atno) { setID(atno); }
		public boolean isA(Element type)
		{
			if (type.AtomicNumber == this.GetAtomicNumber())
				return true;
			return false;
		}
		void readHeader(String data)
		{
			
		}
		void readItem(CSVFileReader reader)
		{
			//Name,AtNo,zS,zP,zD,nS,nP,nD,radius,material,FullName,
			//Name	AtNo	zS	zP	zD	nS	nP	nD	radius	material	FullName	neg


			setName(reader.getString("Name"));
			SetAtomicNumber(reader.getInteger("AtNo"));
			zS = reader.getFloat("zS");
			zP = reader.getFloat("zP");
			zD = reader.getFloat("zD");
			ns = reader.getInteger("nS");
			np = reader.getInteger("nP");
			nd = reader.getInteger("nD");
			radius = reader.getFloat("radius");
			fullName = reader.getString("FullName");
			String materialName = reader.getString("material");
			mat = MaterialSet.get().getByName(materialName);
			electronegativity = reader.getFloat("neg");
			color = mat.getColor();

		}
		
		void ReadItem(String data)
		{
			String[] tokens = data.split(",");
			setName(tokens[0]);
			SetAtomicNumber(Integer.parseInt(tokens[1]));
			zS = Float.parseFloat(tokens[2]);
			zP = Float.parseFloat(tokens[3]);
			zD = Float.parseFloat(tokens[4]);
			ns = Integer.parseInt(tokens[5]);
			np = Integer.parseInt(tokens[6]);
			nd = Integer.parseInt(tokens[7]);
			radius = Float.parseFloat(tokens[8]);
			mat = MaterialSet.get().getByName(tokens[9]);
			color = mat.getColor();

		}

		static IPropertyDescriptor propertyDescriptor = new PropertyDescriptorList<MoleculeProperty>(){

			@Override
			public void initialize() {
				addPropertyDescriptor(0, "Atomic Number", Integer.class, false);
				addPropertyDescriptor(1, "Name", String.class, false);
				addPropertyDescriptor(2, "NS", Integer.class, false);
				addPropertyDescriptor(3, "NP", Integer.class, false);
				addPropertyDescriptor(4, "ND", Integer.class, false);
				
			}

		};
		IPropertyAccessor access = new PropertyAccessor(propertyDescriptor){
			@Override
			public Object getProperty(int index) {
				switch (index){
				case 0: return getID();
				case 1: return getName();
				case 2: return ns;
				case 3: return np;
				case 4: return nd;
				}
				return null;
			}
		
			@Override
			public void setProperty(int index, Object value) {
				switch (index){
					case 0:	 return;
				}
				
			}

		};
		
		// {{ IAccessorDelegates
		public void addPropertyDescriptor(int i, String n, @SuppressWarnings("rawtypes") Class c, boolean e) {
			access.addPropertyDescriptor(i, n, c, e);
		}

		public Object getProperty(int index) {
			return access.getProperty(index);
		}

		public int getPropertyCount() {
			return access.getPropertyCount();
		}

		public void setProperty(int index, Object value) {
			access.setProperty(index, value);
		}

		public int getPropertyIndex(String name) {
			return access.getPropertyIndex(name);
		}

		public Class<?> getPropertyClass(int index) {
			return access.getPropertyClass(index);
		}

		public String getPropertyName(int index) {
			return access.getPropertyName(index);
		}

		public boolean isPropertyEditable(int index) {
			return access.isPropertyEditable(index);
		}

		public Object getProperty(String name) {
			return access.getProperty(name);
		}

		public void setProperty(String name, Object value) {
			access.setProperty(name, value);
		}

		// }}
		

}



