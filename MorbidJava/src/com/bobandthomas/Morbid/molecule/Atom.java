package com.bobandthomas.Morbid.molecule;

import java.util.ArrayList;

import com.bobandthomas.Morbid.utils.CLoadableItem;
import com.bobandthomas.Morbid.utils.Point3D;

public class Atom extends CLoadableItem
{
	private int subType;
	private double charge;
	AtomType atomType;
	String marker;
	public Point3D pos;
	private Hybridization hybrid;
	private ArrayList<Bond> bonds = new ArrayList<Bond>();
	private ArrayList<Atom> bonded = new ArrayList<Atom>();
	public void addBond(Bond b)
	{
		bonds.add(b);
		bonded.add(b.getAtom(this));
	}
	public Bond[] getBonds()
	{
		return bonds.toArray(new Bond[0]);
	}
	public Atom[] getBonded()
	{
		return bonded.toArray(new Atom[0]);
		
	}
	
	public Point3D Position() { return pos; }
	public double getCharge() { return charge; }
	
	public void setCharge(double charge) {
		this.charge = charge;
	}
	int NS() {  return atomType.ns; }
	int NP() {  return atomType.np; }
	int ND() { return atomType.nd; }
	public double Radius() { return atomType.radius;  }
	
	double x() {return pos.x;}
	double y() {return pos.y;}
	double z() {return pos.z;}
	
	public int getSubType() {
		return subType;
	}
	public void setSubType(int subType) {
		this.subType = subType;
	}
	public boolean isA(Element type)
	{
		return getAtomType().isA(type);
	}
	public void setPosition(double x, double y, double z)
	{
	 pos = new Point3D(x,y,z);
	}
	public AtomType getAtomType() { return atomType; } 
	public void setAtomType(AtomType value) 
	{
		atomType = value; 
		setName(atomType.getName());
		charge = 0.0;
	}


	public Atom()
	{
		charge = 0.0f;
		setSubType(0);
		pos = new Point3D();
	}
	public Atom(String inName)
	{
		
		setAtomType(AtomTypeList.Get(inName));
		setSubType(0);
		pos = new Point3D();
	}
	public Atom(AtomType at) {

		setAtomType(at);
		setSubType(0);
		pos = new Point3D();
	}
	public Hybridization getHybrid() {
		return hybrid;
	}
	public void setHybrid(Hybridization hybrid) {
		this.hybrid = hybrid;
	}
	public int getAtomicNumber() {
		return atomType.GetAtomicNumber();
	}
	
}




