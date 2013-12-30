package com.bobandthomas.Morbid.molecule;

import com.bobandthomas.Morbid.graphics.Material;
import com.bobandthomas.Morbid.graphics.MaterialList;
import com.bobandthomas.Morbid.utils.CLoadableItem;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.IPropertyAccessor;
import com.bobandthomas.Morbid.utils.IPropertyDescriptor;
import com.bobandthomas.Morbid.utils.IPropertyDescriptorList;
import com.bobandthomas.Morbid.utils.IPropertySetter;
import com.bobandthomas.Morbid.utils.PropertyAccessor;
import com.bobandthomas.Morbid.utils.MPropertyDescriptorList;
import com.bobandthomas.Morbid.wrapper.CSVFileReader;

public class AtomType extends CLoadableItem implements IPropertyAccessor
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
			mat = MaterialList.getOne().getByName(materialName);
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
			mat = MaterialList.getOne().getByName(tokens[9]);
			color = mat.getColor();

		}

		static IPropertyDescriptorList propertyDescriptor = new MPropertyDescriptorList(){

			@Override
			public void initialize() {
				addPropertyDescriptor(0, "Atomic Number", Integer.class, false, new IPropertySetter(){

					@Override
					public Object get(Object obj) {
						return ((AtomType) obj).getID();
					}

					@Override
					public boolean set(Object obj, Object value) {
						return true;
					}
					
				});
				addPropertyDescriptor(1, "Name", String.class, false, new IPropertySetter(){
					
					@Override
					public Object get(Object obj) {
						return ((AtomType) obj).getName();
					}
					
					@Override
					public boolean set(Object obj, Object value) {
						((AtomType) obj).setName((String) value);
						return true;
					}
					
				});
				addPropertyDescriptor(2, "NS", Integer.class, false, new IPropertySetter(){
					
					@Override
					public Object get(Object obj) {
						return ((AtomType) obj).ns;
					}
					
					@Override
					public boolean set(Object obj, Object value) {
						return true;
					}
					
				});
				addPropertyDescriptor(3, "NP", Integer.class, false, new IPropertySetter(){
					
					@Override
					public Object get(Object obj) {
						return ((AtomType) obj).np;
					}
					
					@Override
					public boolean set(Object obj, Object value) {
						return true;
					}
					
				});
				addPropertyDescriptor(4, "ND", Integer.class, false, new IPropertySetter(){
					
					@Override
					public Object get(Object obj) {
						return ((AtomType) obj).nd;
					}
					
					@Override
					public boolean set(Object obj, Object value) {
						return true;
					}
					
				});
				
				addPropertyDescriptor(5, "Color", ColorQuad.class, false, new IPropertySetter(){
					
					@Override
					public Object get(Object obj) {
						return ((AtomType) obj).color;
					}
					
					@Override
					public boolean set(Object obj, Object value) {
						return true;
					}
					
				});
				addPropertyDescriptor(6, "Material", Material.class, false, new IPropertySetter(){
					
					@Override
					public Object get(Object obj) {
						return ((AtomType) obj).color;
					}
					
					@Override
					public boolean set(Object obj, Object value) {
						return true;
					}
					
				});
			}

		};
		IPropertyAccessor access = new PropertyAccessor(this, propertyDescriptor){
			@Override
			public Object getProperty(IPropertyDescriptor ipd) {
				return null;
			}
		
			@Override
			public void setProperty(IPropertyDescriptor ipd, Object value) {
				switch (ipd.getIndex()){
					case 0:	 return;
				}
				
			}

		};
		// {{ IAccessorDelegates

		public Object getProperty(String name) {
			return access.getProperty(name);
		}


		public void setProperty(String name, Object value) {
			access.setProperty(name, value);
		}


		public Object getProperty(int index) {
			return access.getProperty(index);
		}


		public void setProperty(int index, Object value) {
			access.setProperty(index, value);
		}


		public Object getProperty(IPropertyDescriptor desc) {
			return access.getProperty(desc);
		}


		public void setProperty(IPropertyDescriptor desc, Object value) {
			access.setProperty(desc, value);
		}


		public IPropertyDescriptorList getDescriptors() {
			return access.getDescriptors();
		}
		


		// }}
		

}



