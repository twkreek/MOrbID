package com.bobandthomas.Morbid.molecule;

import java.util.ArrayList;

import com.bobandthomas.Morbid.utils.CLoadableItem;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.IChangeNotifier;
import com.bobandthomas.Morbid.utils.IPropertyAccessor;
import com.bobandthomas.Morbid.utils.IPropertyDescriptor;
import com.bobandthomas.Morbid.utils.ISelectable;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.PropertyAccessor;
import com.bobandthomas.Morbid.utils.PropertyDescriptorList;
import com.bobandthomas.Morbid.utils.Selectable;

public class Atom extends CLoadableItem implements IPropertyAccessor, ISelectable, IChangeNotifier
{
	public Point3D pos;
	private ArrayList<Atom> bonded = new ArrayList<Atom>();
	private ArrayList<Bond> bonds = new ArrayList<Bond>();
	private double charge;
	private Hybridization hybrid;
	private int subType;
	AtomType atomType;
	String marker;
	public Atom()
	{
		charge = 0.0f;
		setSubType(0);
		pos = new Point3D();
	}
	public Atom(AtomType at) {

		setAtomType(at);
		setSubType(0);
		pos = new Point3D();
	}
	public Atom(String inName)
	{
		
		setAtomType(AtomTypeList.Get(inName));
		setSubType(0);
		pos = new Point3D();
	}
	public Atom(int AtNo)
	{
		
		setAtomType(AtomTypeList.Get(AtNo));
		setSubType(0);
		pos = new Point3D();
	}
	
	public void addBond(Bond b)
	{
		bonds.add(b);
		bonded.add(b.getAtom(this));
	}
	public int getAtomicNumber() {
		return atomType.GetAtomicNumber();
	}
	
	public AtomType getAtomType() { return atomType; }
	public Atom[] getBonded()
	{
		return bonded.toArray(new Atom[0]);
		
	}
	public Bond[] getBonds()
	{
		return bonds.toArray(new Bond[0]);
	}
	public double getCharge() { return charge; }
	public Hybridization getHybrid() {
		return hybrid;
	}
	
	public int getSubType() {
		return subType;
	}
	public boolean isA(Element type)
	{
		return getAtomType().isA(type);
	}
	public Point3D Position() { return pos; }
	public String getAttachedList()
	{
		String s = "";
		for (Atom a: bonded)
		{
			s+=  a.getAtomType().getName() + a.getID() + ",";
		}
		return s;
	}
	
	public double Radius() { return atomType.radius;  }
	public void setAtomType(AtomType value) 
	{
		atomType = value; 
		setName(atomType.getName());
		charge = 0.0;
	}
	public void setCharge(double charge) {
		this.charge = charge;
	}
	public void setHybrid(Hybridization hybrid) {
		this.hybrid = hybrid;
	}
	public void setPosition(double x, double y, double z)
	{
	 pos = new Point3D(x,y,z);
	} 
	public void setSubType(int subType) {
		this.subType = subType;
	}


	int ND() { return atomType.nd; }
	int NP() {  return atomType.np; }
	int NS() {  return atomType.ns; }
	double x() {return pos.x;}
	double y() {return pos.y;}
	double z() {return pos.z;}
	
	// {{ ISelectable Delegates
	
	ISelectable selectable = new Selectable(this);
	
	
	public void setSelected(boolean selected) {
		selectable.setSelected(selected);
	}
	public void setSelected(IChangeNotifier source, boolean selected) {
		selectable.setSelected(source, selected);
	}
	public boolean isSelected() {
		return selectable.isSelected();
	}
	//}}

	static IPropertyDescriptor propertyDescriptor = new PropertyDescriptorList<MoleculeProperty>(){

		@Override
		public void initialize() {
			addPropertyDescriptor(0, "Index", Integer.class, false);
			addPropertyDescriptor(1, "Name", String.class, false);
			addPropertyDescriptor(2, "Element", String.class, true);
			addPropertyDescriptor(3, "Charge", Float.class, false);
			addPropertyDescriptor(4, "Position", Point3D.class, false);
			addPropertyDescriptor(5, "Color", ColorQuad.class, false);
			addPropertyDescriptor(6, "Attached", String.class, false);
			addPropertyDescriptor(7, "Selected", Boolean.class, true);
			
		}

	};
	IPropertyAccessor access = new PropertyAccessor(propertyDescriptor){
		@Override
		public Object getProperty(int index) {
			switch (index){
			case 0: return getID();
			case 1: return getName();
			case 2: return atomType.getName();
			case 3: return getCharge();
			case 4: return pos;
			case 5: return atomType.color;
			case 6: return getAttachedList();
			case 7: return isSelected();
			}
			return null;
		}
	
		@Override
		public void setProperty(int index, Object value) {
			switch (index){
				case 0:	 return;
				case 7: setSelected((Boolean) value);
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

	@Override
	public void addProperty(String name, Object value) {
		access.addProperty(name,  value);
		
	}
	// }}
	
}




