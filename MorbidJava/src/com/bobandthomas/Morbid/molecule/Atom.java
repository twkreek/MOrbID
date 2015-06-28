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

import java.util.ArrayList;

import com.bobandthomas.Morbid.utils.CLoadableItem;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.IChangeNotifier;
import com.bobandthomas.Morbid.utils.IPropertyAccessor;
import com.bobandthomas.Morbid.utils.IPropertyDescriptor;
import com.bobandthomas.Morbid.utils.IPropertyDescriptorList;
import com.bobandthomas.Morbid.utils.IPropertySetter;
import com.bobandthomas.Morbid.utils.ISelectable;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.PropertyAccessor;
import com.bobandthomas.Morbid.utils.MPropertyDescriptorList;
import com.bobandthomas.Morbid.utils.Selectable;


/**
 * The Class Atom
 * represents a single atom.
 * has, at minimum, type and location.
 * 
 * @author Thomas Kreek
 */
public class Atom extends CLoadableItem implements IPropertyAccessor, ISelectable, IChangeNotifier
{
	
	/** The pos. */
	public Point3D pos;
	
	/** The bonded. */
	private ArrayList<Atom> bonded = new ArrayList<Atom>();
	
	/** The bonds. */
	private ArrayList<Bond> bonds = new ArrayList<Bond>();
	
	/** The charge. */
	private double charge;
	
	/** The hybrid. */
	private Hybridization hybrid;
	
	/** The sub type. */
	private int subType;
	
	/** The atom type. */
	AtomType atomType;
	
	/** The marker. */
	String marker;
	
	/**
	 * Instantiates a new atom.
	 */
	public Atom()
	{
		charge = 0.0f;
		setSubType(0);
		pos = new Point3D();
	}
	
	/**
	 * Instantiates a new atom.
	 * 
	 * @param at
	 *            the at
	 */
	public Atom(AtomType at) {

		setAtomType(at);
		setSubType(0);
		pos = new Point3D();
	}
	
	/**
	 * Instantiates a new atom.
	 * 
	 * @param inName
	 *            the in name
	 */
	public Atom(String inName)
	{
		
		setAtomType(AtomTypeList.Get(inName));
		setSubType(0);
		pos = new Point3D();
	}
	
	/**
	 * Instantiates a new atom.
	 * 
	 * @param AtNo
	 *            the at no
	 */
	public Atom(int AtNo)
	{
		
		setAtomType(AtomTypeList.Get(AtNo));
		setSubType(0);
		pos = new Point3D();
	}
	
	/**
	 * Adds the bond.
	 * 
	 * @param b
	 *            the b
	 */
	public void addBond(Bond b)
	{
		bonds.add(b);
		bonded.add(b.getAtom(this));
	}
	
	/**
	 * Gets the atomic number.
	 * 
	 * @return the atomic number
	 */
	public int getAtomicNumber() {
		return atomType.GetAtomicNumber();
	}
	
	/**
	 * Gets the atom type.
	 * 
	 * @return the atom type
	 */
	public AtomType getAtomType() { return atomType; }
	
	/**
	 * Gets the bonded.
	 * 
	 * @return the bonded
	 */
	public Atom[] getBonded()
	{
		return bonded.toArray(new Atom[0]);
		
	}
	
	/**
	 * Gets the bonds.
	 * 
	 * @return the bonds
	 */
	public Bond[] getBonds()
	{
		return bonds.toArray(new Bond[0]);
	}
	
	/**
	 * Gets the charge.
	 * 
	 * @return the charge
	 */
	public double getCharge() { return charge; }
	
	/**
	 * Gets the hybrid.
	 * 
	 * @return the hybrid
	 */
	public Hybridization getHybrid() {
		return hybrid;
	}
	
	/**
	 * Gets the sub type.
	 * 
	 * @return the sub type
	 */
	public int getSubType() {
		return subType;
	}
	
	/**
	 * Checks if is a.
	 * 
	 * @param type
	 *            the type
	 * @return true, if is a
	 */
	public boolean isA(Element type)
	{
		return getAtomType().isA(type);
	}
	
	/**
	 * Position.
	 * 
	 * @return the point3 d
	 */
	public Point3D Position() { return pos; }
	
	/**
	 * Gets the attached list.
	 * 
	 * @return the attached list
	 */
	public String getAttachedList()
	{
		String s = "";
		for (Atom a: bonded)
		{
			s+=  a.getAtomType().getName() + a.getID() + ",";
		}
		return s;
	}
	
	/**
	 * Radius.
	 * 
	 * @return the double
	 */
	public double Radius() { return atomType.radius;  }
	
	/**
	 * Sets the atom type.
	 * 
	 * @param value
	 *            the new atom type
	 */
	public void setAtomType(AtomType value) 
	{
		atomType = value; 
		setName(atomType.getName());
		charge = 0.0;
	}
	
	/**
	 * Sets the charge.
	 * 
	 * @param charge
	 *            the new charge
	 */
	public void setCharge(double charge) {
		this.charge = charge;
	}
	
	/**
	 * Sets the hybrid.
	 * 
	 * @param hybrid
	 *            the new hybrid
	 */
	public void setHybrid(Hybridization hybrid) {
		this.hybrid = hybrid;
	}
	
	/**
	 * Sets the position.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param z
	 *            the z
	 */
	public void setPosition(double x, double y, double z)
	{
	 pos = new Point3D(x,y,z);
	} 
	
	/**
	 * Sets the sub type.
	 * 
	 * @param subType
	 *            the new sub type
	 */
	public void setSubType(int subType) {
		this.subType = subType;
	}


	/**
	 * Nd.
	 * 
	 * @return the int
	 */
	int ND() { return atomType.nd; }
	
	/**
	 * Np.
	 * 
	 * @return the int
	 */
	int NP() {  return atomType.np; }
	
	/**
	 * Ns.
	 * 
	 * @return the int
	 */
	int NS() {  return atomType.ns; }
	
	/**
	 * X.
	 * 
	 * @return the double
	 */
	double x() {return pos.x;}
	
	/**
	 * Y.
	 * 
	 * @return the double
	 */
	double y() {return pos.y;}
	
	/**
	 * Z.
	 * 
	 * @return the double
	 */
	double z() {return pos.z;}
	
	// {{ ISelectable Delegates
	
	/** The selectable. */
	ISelectable selectable = new Selectable(this);
	
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.ISelectable#setSelected(boolean)
	 */
	public void setSelected(boolean selected) {
		selectable.setSelected(selected);
	}
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.ISelectable#setSelected(com.bobandthomas.Morbid.utils.IChangeNotifier, boolean)
	 */
	public void setSelected(IChangeNotifier source, boolean selected) {
		selectable.setSelected(source, selected);
	}
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.ISelectable#isSelected()
	 */
	public boolean isSelected() {
		return selectable.isSelected();
	}
	//}}

	/** The property descriptor. */
	static IPropertyDescriptorList propertyDescriptor = new MPropertyDescriptorList(){

		@Override
		public void initialize() {
			addPropertyDescriptor(0, "Index", Integer.class, false, new IPropertySetter()
			{
				@Override
				public Object get(Object obj) { return (int) ((Atom) obj).getID(); }

				@Override
				public boolean set(Object obj, Object value) {
					((Atom) obj).setID((long) value); return true;
				}
			});
			addPropertyDescriptor(1, "Name", String.class, false, new IPropertySetter()
			{
				@Override
				public Object get(Object obj) { return ((Atom) obj).getName(); }

				@Override
				public boolean set(Object obj, Object value) {
					((Atom) obj).setName((String) value); return true;
				}
			});
			addPropertyDescriptor(2, "Element", String.class, true, new IPropertySetter()
			{

				@Override
				public Object get(Object obj) {
					return ((Atom) obj).atomType.getName(); 
				}

				@Override
				public boolean set(Object obj, Object value) {
					//this can not be edited.
					return true;
				}

			});
			addPropertyDescriptor(3, "Charge", Float.class, false);
			addPropertyDescriptor(4, "Position", Point3D.class, false);
			addPropertyDescriptor(5, "Color", ColorQuad.class, false);
			addPropertyDescriptor(6, "Attached", String.class, false);
			addPropertyDescriptor(7, "Selected", Boolean.class, true);
			
		}

	};
	
	/** The access. */
	IPropertyAccessor access = new PropertyAccessor(this, propertyDescriptor){
	
		@Override
		public Object getProperty(IPropertyDescriptor desc) {
			switch (desc.getIndex()){
			case 3: return getCharge();
			case 4: return pos;
			case 5: return atomType.color;
			case 6: return getAttachedList();
			case 7: return isSelected();
			}
			return null;
		}

		@Override
		public void setProperty(IPropertyDescriptor desc, Object value) {
			switch (desc.getIndex()){
			case 0:	 return;
			case 7: setSelected((Boolean) value);
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




