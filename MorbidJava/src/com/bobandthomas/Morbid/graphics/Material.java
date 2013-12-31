package com.bobandthomas.Morbid.graphics;
import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;

import com.bobandthomas.Morbid.utils.CLoadableItem;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.IPropertyAccessor;
import com.bobandthomas.Morbid.utils.IPropertyDescriptor;
import com.bobandthomas.Morbid.utils.IPropertyDescriptorList;
import com.bobandthomas.Morbid.utils.IPropertySetter;
import com.bobandthomas.Morbid.utils.PropertyAccessor;
import com.bobandthomas.Morbid.utils.MPropertyDescriptorList;
import com.bobandthomas.Morbid.utils.StaticColorQuad;
import com.bobandthomas.Morbid.wrapper.CSVFileReader;

public class Material extends CLoadableItem implements IPropertyAccessor
{ 		
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
		
		private ColorQuad color; // the base color of the material
	 
		double kDiffuse; //diffuse light coefficient
		double kAmbient; //ambient light coefficient
		double kSpecular; //specular reflection coefficient (specular light color defaults to white)
		double kEmission;	//emission
		double alpha; //percent transmission for trnasparent objects. (alpha)
		int shininess; // specularity coeffiecient.
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
		public double getKSpecular() {
			return kSpecular;
		}
		public void setKSpecular(double kSpecularity) {
			this.kSpecular = kSpecularity;
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
			return shininess;
		}
		public void setSpecularity(int specularity) {
			this.shininess = specularity;
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
			kSpecular = 0.4f;
			kEmission = 0.5f;
			shininess = 10;
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
			kSpecular = mat.kSpecular;
			shininess = mat.shininess;
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
		public ColorQuad getColor() {
			return color;
		}
		public void setColor(ColorQuad diffuse) {
			this.color = diffuse;
		}

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
			
			

};
