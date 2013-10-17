package com.bobandthomas.Morbid.molecule;

import com.bobandthomas.Morbid.graphics.Material;
import com.bobandthomas.Morbid.graphics.MaterialSet;
import com.bobandthomas.Morbid.utils.CLoadableItem;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.wrapper.CSVFileReader;

public class AtomType extends CLoadableItem
{
		double zS;
		double zP;
		double zD;
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
		//ErrorCode WriteItem(String data);
	};


