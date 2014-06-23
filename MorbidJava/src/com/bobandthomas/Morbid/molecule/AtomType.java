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

// TODO: Auto-generated Javadoc
/**
 * The Class AtomType.
 * 
 * @author Thomas Kreek
 */
public class AtomType extends CLoadableItem implements IPropertyAccessor
{
		
		/** The z s. */
		public double zS;
		
		/** The z p. */
		public double zP;
		
		/** The z d. */
		public double zD;
		
		/** The radius. */
		double radius;
		
		/** The ns. */
		int ns;
		
		/** The np. */
		int np;
		
		/** The nd. */
		int nd;
		
		/** The electronegativity. */
		double electronegativity;
		
		/** The color. */
		public ColorQuad color;
		
		/** The mat. */
		public Material mat;
		
		/** The full name. */
		String fullName;
		
		/**
		 * Instantiates a new atom type.
		 */
		public AtomType()
		{
		
		}


		/**
		 * Gets the atomic number.
		 * 
		 * @return the int
		 */
		int GetAtomicNumber() { return (int) getID(); }
		
		/**
		 * Sets the atomic number.
		 * 
		 * @param atno
		 *            the atno
		 */
		void SetAtomicNumber(int atno) { setID(atno); }
		
		/**
		 * Checks if is a.
		 * 
		 * @param type
		 *            the type
		 * @return true, if is a
		 */
		public boolean isA(Element type)
		{
			if (type.AtomicNumber == this.GetAtomicNumber())
				return true;
			return false;
		}
		
		/**
		 * Read header.
		 * 
		 * @param data
		 *            the data
		 */
		void readHeader(String data)
		{
			
		}
		
		/**
		 * Read item.
		 * 
		 * @param reader
		 *            the reader
		 */
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
		
		/**
		 * Read item.
		 * 
		 * @param data
		 *            the data
		 */
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

		/** The property descriptor. */
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
				addPropertyDescriptor(6, "Material", Material.class, true, new IPropertySetter(){
					
					@Override
					public Object get(Object obj) {
						return ((AtomType) obj).mat;
					}
					
					@Override
					public boolean set(Object obj, Object value) {
						return true;
					}
					
				});
			}

		};
		
		/** The access. */
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

		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.utils.IPropertyAccessor#getProperty(java.lang.String)
		 */
		public Object getProperty(String name) {
			return access.getProperty(name);
		}


		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.utils.IPropertyAccessor#setProperty(java.lang.String, java.lang.Object)
		 */
		public void setProperty(String name, Object value) {
			access.setProperty(name, value);
		}


		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.utils.IPropertyAccessor#getProperty(int)
		 */
		public Object getProperty(int index) {
			return access.getProperty(index);
		}


		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.utils.IPropertyAccessor#setProperty(int, java.lang.Object)
		 */
		public void setProperty(int index, Object value) {
			access.setProperty(index, value);
		}


		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.utils.IPropertyAccessor#getProperty(com.bobandthomas.Morbid.utils.IPropertyDescriptor)
		 */
		public Object getProperty(IPropertyDescriptor desc) {
			return access.getProperty(desc);
		}


		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.utils.IPropertyAccessor#setProperty(com.bobandthomas.Morbid.utils.IPropertyDescriptor, java.lang.Object)
		 */
		public void setProperty(IPropertyDescriptor desc, Object value) {
			access.setProperty(desc, value);
		}


		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.utils.IPropertyAccessor#getDescriptors()
		 */
		public IPropertyDescriptorList getDescriptors() {
			return access.getDescriptors();
		}
		


		// }}
		

}



