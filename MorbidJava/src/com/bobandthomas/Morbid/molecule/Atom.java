package com.bobandthomas.Morbid.molecule;

import java.util.ArrayList;

import com.bobandthomas.Morbid.utils.CLoadableItem;
import com.bobandthomas.Morbid.utils.Point3D;

public class Atom extends CLoadableItem
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
	
}




