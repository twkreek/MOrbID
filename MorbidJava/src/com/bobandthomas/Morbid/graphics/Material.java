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
package com.bobandthomas.Morbid.graphics;
import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;

import com.bobandthomas.Morbid.utils.CLoadableItem;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.IChangeNotifier;
import com.bobandthomas.Morbid.utils.IMorbidListener;
import com.bobandthomas.Morbid.utils.IPropertyAccessor;
import com.bobandthomas.Morbid.utils.IPropertyDescriptor;
import com.bobandthomas.Morbid.utils.IPropertyDescriptorList;
import com.bobandthomas.Morbid.utils.IPropertySetter;
import com.bobandthomas.Morbid.utils.MorbidEvent;
import com.bobandthomas.Morbid.utils.MorbidNotifier;
import com.bobandthomas.Morbid.utils.PropertyAccessor;
import com.bobandthomas.Morbid.utils.MPropertyDescriptorList;
import com.bobandthomas.Morbid.utils.StaticColorQuad;
import com.bobandthomas.Morbid.wrapper.CSVFileReader;

// TODO: Auto-generated Javadoc
/**
 * The Class Material.
 * 
 * @author Thomas Kreek
 */
public class Material extends CLoadableItem implements IPropertyAccessor
{ 		

		/** The bean info. */
		public static SimpleBeanInfo beanInfo = new SimpleBeanInfo()
		{
			@Override
			public BeanDescriptor getBeanDescriptor() {
				return new BeanDescriptor(Material.class);
			}
			@Override
			public PropertyDescriptor[] getPropertyDescriptors()
			{
				ArrayList<PropertyDescriptor> list = new ArrayList<PropertyDescriptor>();
				try {
					list.add(new PropertyDescriptor("Name", String.class, "getName", "setName"));
					list.add(new PropertyDescriptor("ID", Long.class, "getID", "setID"));
					list.add(new PropertyDescriptor("Color", ColorQuad.class, "getColor", "setColor"));
				} catch (IntrospectionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return list.toArray(new PropertyDescriptor[list.size()]);
			}
		};
		
		/** The color. */
		private ColorQuad color; // the base color of the material
	 
		/** The k diffuse. */
		double kDiffuse; //diffuse light coefficient
		
		/** The k ambient. */
		double kAmbient; //ambient light coefficient
		
		/** The k specular. */
		double kSpecular; //specular reflection coefficient (specular light color defaults to white)
		
		/** The k emission. */
		double kEmission;	//emission
		
		/** The alpha. */
		double alpha; //percent transmission for trnasparent objects. (alpha)
		
		/** The shininess. */
		int shininess; // specularity coeffiecient.
		
		/** The use specularity. */
		boolean useSpecularity; 
		
		/** The use filter. */
		boolean useFilter;	

		
		/**
		 * Gets the k diffuse.
		 * 
		 * @return the k diffuse
		 */
		public double getkDiffuse() {
			return kDiffuse;
		}
		
		/**
		 * Gets the diffuse color.
		 * 
		 * @return the diffuse color
		 */
		public ColorQuad getDiffuseColor() {
			return new ColorQuad(color).multiply(kDiffuse);
		}
		
		/**
		 * Sets the k diffuse.
		 * 
		 * @param kDiffuse
		 *            the new k diffuse
		 */
		public void setkDiffuse(double kDiffuse) {
			this.kDiffuse = kDiffuse;
			markDirty();
		}
		
		/**
		 * Gets the k ambient.
		 * 
		 * @return the k ambient
		 */
		public double getkAmbient() {
			return kAmbient;
		}
		
		/**
		 * Sets the k ambient.
		 * 
		 * @param kAmbient
		 *            the new k ambient
		 */
		public void setkAmbient(double kAmbient) {
			this.kAmbient = kAmbient;
			markDirty();
		}
		
		/**
		 * Gets the k specular.
		 * 
		 * @return the k specular
		 */
		public double getKSpecular() {
			return kSpecular;
		}
		
		/**
		 * Sets the k specular.
		 * 
		 * @param kSpecularity
		 *            the new k specular
		 */
		public void setKSpecular(double kSpecularity) {
			this.kSpecular = kSpecularity;
			markDirty();
		}
		
		/**
		 * Gets the k emission.
		 * 
		 * @return the k emission
		 */
		public double getkEmission() {
			return kEmission;
		}
		
		/**
		 * Sets the k emission.
		 * 
		 * @param kEmission
		 *            the new k emission
		 */
		public void setkEmission(double kEmission) {
			this.kEmission = kEmission;
			markDirty();
		}
		
		/**
		 * Gets the alpha.
		 * 
		 * @return the alpha
		 */
		public double getAlpha() {
			return alpha;
		}
		
		/**
		 * Sets the alpha.
		 * 
		 * @param alpha
		 *            the new alpha
		 */
		public void setAlpha(double alpha) {
			this.alpha = alpha;
			setUseFilter(true);
			markDirty();
		}
		
		/**
		 * Gets the specularity.
		 * 
		 * @return the specularity
		 */
		public int getSpecularity() {
			return shininess;
		}
		
		/**
		 * Sets the specularity.
		 * 
		 * @param specularity
		 *            the new specularity
		 */
		public void setSpecularity(int specularity) {
			this.shininess = specularity;
			markDirty();
		}
		
		/**
		 * Checks if is use specularity.
		 * 
		 * @return true, if is use specularity
		 */
		public boolean isUseSpecularity() {
			return useSpecularity;
		}
		
		/**
		 * Sets the use specularity.
		 * 
		 * @param useSpecularity
		 *            the new use specularity
		 */
		public void setUseSpecularity(boolean useSpecularity) {
			this.useSpecularity = useSpecularity;
			markDirty();
		}
		
		/**
		 * Checks if is use filter.
		 * 
		 * @return true, if is use filter
		 */
		public boolean isUseFilter() {
			return useFilter;
		}
		
		/**
		 * Sets the use filter.
		 * 
		 * @param useFilter
		 *            the new use filter
		 */
		public void setUseFilter(boolean useFilter) {
			this.useFilter = useFilter;
			markDirty();
		}
		
	

		/**
		 * Initializes the.
		 */
		void Init()
		{
			kDiffuse = 0.5f;
			kAmbient = 0.5f;
			kSpecular = 0.4f;
			kEmission = 0.5f;
			shininess = 10;
			useSpecularity = true;
			alpha = 1.0f;
			useFilter = false;
			setColor(StaticColorQuad.White.cq());
		}
		
		/**
		 * Instantiates a new material.
		 */
		public Material()
		{
			Init();
		}

		/**
		 * Instantiates a new material.
		 * 
		 * @param cq
		 *            the cq
		 */
		public Material(ColorQuad cq)
		{
			Init();
			setColor(cq);
		}

		/**
		 * Instantiates a new material.
		 * 
		 * @param mat
		 *            the mat
		 */
		public Material(Material mat)
		{
			kAmbient = mat.kAmbient;
			kDiffuse = mat.kDiffuse;
			kEmission = mat.kEmission;
			setColor(mat.getColor());
			kSpecular = mat.kSpecular;
			shininess = mat.shininess;
			useSpecularity = mat.useSpecularity;
			alpha = mat.alpha;
			useFilter = mat.useFilter;
		}
		
		/**
		 * Write item.
		 * 
		 * @param str
		 *            the str
		 */
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
		
		/**
		 * Read item.
		 * 
		 * @param reader
		 *            the reader
		 */
		void readItem(CSVFileReader reader)
		{
			//Name,   kd,   r,g,b,      ka,     ks,    ke,    filter,useFilter,specularity,useSpec
			setName(reader.getString("Name"));
			kDiffuse = reader.getFloat("kd");
			setColor(new ColorQuad(reader.getFloat("r"), reader.getFloat("g"), reader.getFloat("b")));
			kAmbient = reader.getFloat("ka");
			kEmission = reader.getFloat("ke");
			kSpecular = reader.getFloat("ks");
			alpha = reader.getFloat("filter");
			useFilter = reader.getInteger("useFilter") == 1;
			shininess = reader.getInteger("specularity");
			useSpecularity = reader.getInteger("useSpec") == 1;
			
		}
		
		/**
		 * Gets the color.
		 * 
		 * @return the color
		 */
		public ColorQuad getColor() {
			return color;
		}
		
		/**
		 * Sets the color.
		 * 
		 * @param diffuse
		 *            the new color
		 */
		public void setColor(ColorQuad diffuse) {
			this.color = diffuse;
			markDirty();
		}

       /** The property descriptor. */
       static IPropertyDescriptorList propertyDescriptor = new MPropertyDescriptorList(){
				
				@Override
				public void initialize() {
					addPropertyDescriptor(0, "Name", String.class, false, new IPropertySetter(){
						
						@Override
						public Object get(Object obj) {
							return ((Material) obj).getName();
						}
						
						@Override
						public boolean set(Object obj, Object value) {
							((Material)obj).setName((String) value);
							return true;
						}
						
					});
					addPropertyDescriptor(1, "Color", ColorQuad.class, true, new IPropertySetter(){
						
						@Override
						public Object get(Object obj) {
							return ((Material) obj).getColor();
						}
						
						@Override
						public boolean set(Object obj, Object value) {
							((Material)obj).setColor((ColorQuad) value);
							return true;
						}
						
					});
					addPropertyDescriptor(2, "Ambient", Double.class, false, new IPropertySetter(){
						
						@Override
						public Object get(Object obj) {
							return ((Material) obj).getkAmbient();
						}
						
						@Override
						public boolean set(Object obj, Object value) {
							((Material)obj).setkAmbient((Double) value);
							return true;
						}
						
					});

				addPropertyDescriptor(3, "Diffuse", Double.class, false, new IPropertySetter(){
					
					@Override
					public Object get(Object obj) {
						return ((Material) obj).getkDiffuse();
					}
					
					@Override
					public boolean set(Object obj, Object value) {
						((Material)obj).setkDiffuse((Double) value);
						return true;
					}
					
				});

				addPropertyDescriptor(4, "Specular", Double.class, false, new IPropertySetter(){
					
					@Override
					public Object get(Object obj) {
						return ((Material) obj).getKSpecular();
					}
					
					@Override
					public boolean set(Object obj, Object value) {
						((Material)obj).setKSpecular((Double) value);
						return true;
					}
					
				});
				addPropertyDescriptor(5, "Emission", Double.class, false, new IPropertySetter(){
					
					@Override
					public Object get(Object obj) {
						return ((Material) obj).getkEmission();
					}
					
					@Override
					public boolean set(Object obj, Object value) {
						((Material)obj).setkEmission((Double) value);
						return true;
					}
					
				});
				addPropertyDescriptor(6, "Shininess", Integer.class, false, new IPropertySetter(){
					
					@Override
					public Object get(Object obj) {
						return ((Material) obj).getSpecularity();
					}
					
					@Override
					public boolean set(Object obj, Object value) {
						((Material)obj).setSpecularity((Integer) value);
						return true;
					}
					
				});
			}
				
			};
			
			/** The access. */
			IPropertyAccessor access = new PropertyAccessor(this, propertyDescriptor){
				@Override
				public Object getProperty(IPropertyDescriptor ipd) {
					switch (ipd.getIndex()){
					case 0: return  Material.this.getName();
					}
					return null;
				}
				
				@Override
				public void setProperty(IPropertyDescriptor ipd, Object value) {
					switch (ipd.getIndex()){
					case 0:  Material.this.setName((String) value); return;
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
		
		/**
			 * The Class MaterialChangeEvent.
			 * 
			 * @author Thomas Kreek
			 */
			public static class MaterialChangeEvent extends MorbidEvent {
			
			/**
			 * Instantiates a new material change event.
			 * 
			 * @param item
			 *            the item
			 */
			public MaterialChangeEvent(IChangeNotifier item) {
				super(item);
				speciallistener = MaterialChangeListener.class;
			}
		}

		/**
		 * The listener interface for receiving materialChange events. The class
		 * that is interested in processing a materialChange event implements
		 * this interface, and the object created with that class is registered
		 * with a component using the component's
		 * <code>addMaterialChangeListener<code> method. When
		 * the materialChange event occurs, that object's appropriate
		 * method is invoked.
		 * 
		 * @see MaterialChangeEvent
		 */
		public interface MaterialChangeListener extends
				IMorbidListener<MaterialChangeEvent> {
		}

		/** The Material notifier. */
		MorbidNotifier<MaterialChangeEvent, MaterialChangeListener> MaterialNotifier = new MorbidNotifier<MaterialChangeEvent, MaterialChangeListener>(
				this);

		/**
		 * Fire event.
		 * 
		 * @param event
		 *            the event
		 */
		public void fireEvent(MaterialChangeEvent event) {
			MaterialNotifier.fireEvent(event);
		}

		/**
		 * Register listener.
		 * 
		 * @param listener
		 *            the listener
		 */
		public void registerListener(MaterialChangeListener listener) {
			MaterialNotifier.registerListener(listener);
		}

		/**
		 * Un register listener.
		 * 
		 * @param listener
		 *            the listener
		 */
		public void unRegisterListener(MaterialChangeListener listener) {
			MaterialNotifier.unRegisterListener(listener);
		}
		
		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.utils.CLoadableItem#markDirty()
		 */
		@Override
		public void markDirty() {
			
			super.markDirty();
			fireEvent(new MaterialChangeEvent(this));
		}


};
