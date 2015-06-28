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
package com.bobandthomas.Morbid.molecule.data;

import com.bobandthomas.Morbid.molecule.AtomType;
import com.bobandthomas.Morbid.molecule.MolecularOrbitalSet;
import com.bobandthomas.Morbid.molecule.MolecularOrbitalSet.AtomicOrbital;
import com.bobandthomas.Morbid.molecule.Molecule;
import com.bobandthomas.Morbid.molecule.data.control.SpatialDataControl;
import com.bobandthomas.Morbid.molecule.data.control.SpatialDataMOControl;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.Vector3D;

// TODO: Auto-generated Javadoc
/**
 * The Class SpatialDataMO.
 * 
 * @author Thomas Kreek
 */
public class SpatialDataMO extends SpatialData {

	/** The which mo. */
	int whichMO;
	
	/** The slice. */
	int slice;

	/**
	 * Instantiates a new spatial data mo.
	 * 
	 * @param mol
	 *            the mol
	 */
	public SpatialDataMO(Molecule mol) {
		super(mol);
		whichMO = 2;
		setName("MO");
	}

	/**
	 * Gets the which mo.
	 * 
	 * @return the which mo
	 */
	public int getWhichMO() {
		return whichMO;
	}

	/**
	 * Sets the which mo.
	 * 
	 * @param whichMO
	 *            the new which mo
	 */
	public void setWhichMO(int whichMO) {
		this.whichMO = whichMO;
		markDirty();
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.molecule.data.SpatialData#Calculate(com.bobandthomas.Morbid.utils.Point3D)
	 */
	@Override
	public double Calculate(Point3D p) {
		double tempvalue = 0, coeff, dx, dy, dz, rad;
		Point3D pos;
		MolecularOrbitalSet mo = molecule.getMo();

		for (int n = 0; n < molecule.getMo().nOrbitals; n++) {
			AtomicOrbital ao = mo.ao[n];
			coeff = mo.coefficient(n, whichMO);
			if (Math.abs(coeff) < 0.01f)
				continue;
			pos = ao.atom.Position();

			Vector3D diff = p.Sub(pos).Scale(1/SlaterOrbitals.a0);
			dx = diff.x;
			dy = diff.y;
			dz = diff.z;
			rad = diff.Length();
			if (rad > 3) continue;

			// Do the angular component
			switch (ao.L) {
			case 0: /* S orbital */
				tempvalue = SlaterOrbitals.YS(dx, dy, dz, rad);
				break;
			case 1: /* P orbital */
				tempvalue = SlaterOrbitals.YP(ao.Ml, diff, rad);
				break;
			case 2: /* D orbital */
				switch (ao.Ml) {
				case -2: // dx2y2:
					tempvalue = SlaterOrbitals.YDx2y2(dx, dy, dz, rad);
					break;
				case -1: // dz2:
					tempvalue = SlaterOrbitals.YDz2(dx, dy, dz, rad);
					break;
				case 0: // dxy:
					tempvalue = SlaterOrbitals.YDxy(dx, dy, dz, rad);
					break;
				case 1: // dyz:
					tempvalue = SlaterOrbitals.YDyz(dx, dy, dz, rad);
					break;
				case 2: // dxz:
					tempvalue = SlaterOrbitals.YDxz(dx, dy, dz, rad);
				}
				break;
			default:
				;

			} /* end switch */

			// Do the radial component of the orbital
			if (rad > 1e-15) {
				switch (ao.N) {
				case 1: /* 1 orbital */
					tempvalue *= coeff * SlaterOrbitals.R1STO(ao.zeta, rad);
					break;
				case 2:
					tempvalue *= coeff * SlaterOrbitals.R2STO(ao.zeta, rad);
					break;
				case 3:
					if (ao.contracted > 0)
						tempvalue *= coeff
								* SlaterOrbitals.contracted_R3(ao.zeta,
										ao.zeta2[0], ao.zeta2[1], ao.zeta2[2],
										rad);
					else
						tempvalue *= coeff * SlaterOrbitals.R3STO(ao.zeta, rad);
					break;
				case 4:
					tempvalue *= coeff * SlaterOrbitals.R4STO(ao.zeta, rad);
				}
			}
		}
		return tempvalue;

	}

	/**
	 * Calculate all none.
	 */
	public void CalculateAllNone() {
		slice = 0;

		for (slice = 0; slice < sideX; slice++) {
			calcslice();
		}
	}

	/**
	 * Calcslice.
	 */
	void calcslice() {
		int i, j, k, n;
		@SuppressWarnings("unused")
		double tempvalue = 0, x, y, z, coeff, zeta, dx, dy, dz, rad;
		AtomType aType;
		Point3D pos;
		MolecularOrbitalSet mo = molecule.getMo();

		i = slice;
		if (slice > sideX)
			return;

		// initialize this slice.
		for (j = 0; j < sideY; j++)
			for (k = 0; k < sideZ; k++)
				this.Set(i, j, k, 0.0);

		// step through by atoms. do all the atom lookup at once.
		for (n = 0; n < molecule.getMo().nOrbitals; n++) {
			AtomicOrbital ao = mo.ao[n];
			if (Math.abs(mo.coefficient(n, whichMO)) < 0.01f)
				continue;
			coeff = mo.coefficient(n, whichMO);
			aType = ao.atom.getAtomType();
			pos = ao.atom.Position();
			ResetMinMax();

			for (j = 0; j < sideY; j++) {
				for (k = 0; k < sideZ; k++) {
					x = X(i);
					y = Y(j);
					z = Z(k);
					dx = (x - pos.x) / SlaterOrbitals.a0;
					dy = (y - pos.y) / SlaterOrbitals.a0;
					dz = (z - pos.z) / SlaterOrbitals.a0;
					rad = SlaterOrbitals.r(dx, dy, dz);

					// Do the angular component
					switch (ao.L) {
					case 0: /* S orbital */
						zeta = aType.zS;

						tempvalue = SlaterOrbitals.YS(dx, dy, dz, rad);
						break;
					case 1: /* P orbital */
						zeta = aType.zP;

						switch (ao.Ml) {
						case -1: // px
							tempvalue = SlaterOrbitals.YPx(dx, dy, dz, rad);
							break;
						case 0: // py
							tempvalue = SlaterOrbitals.YPy(dx, dy, dz, rad);
							break;
						case 1: // pz
							tempvalue = SlaterOrbitals.YPz(dx, dy, dz, rad);
						}
						break;
					case 2: /* D orbital */
						zeta = aType.zD;

						switch (ao.Ml) {
						case -2: // dx2y2:
							tempvalue = SlaterOrbitals.YDx2y2(dx, dy, dz, rad);
							break;
						case -1: // dz2:
							tempvalue = SlaterOrbitals.YDz2(dx, dy, dz, rad);
							break;
						case 0: // dxy:
							tempvalue = SlaterOrbitals.YDxy(dx, dy, dz, rad);
							break;
						case 1: // dyz:
							tempvalue = SlaterOrbitals.YDyz(dx, dy, dz, rad);
							break;
						case 2: // dxz:
							tempvalue = SlaterOrbitals.YDxz(dx, dy, dz, rad);
						}
						break;
					default:
						;

					} /* end switch */

					// Do the radial component of the orbital
					if (rad > 1e-15) {
						switch (ao.N) {
						case 1: /* 1 orbital */
							tempvalue *= coeff
									* SlaterOrbitals.R1STO(ao.zeta, rad);
							break;
						case 2:
							tempvalue *= coeff
									* SlaterOrbitals.R2STO(ao.zeta, rad);
							break;
						case 3:
							if (ao.contracted > 0)
								tempvalue *= coeff
										* SlaterOrbitals.contracted_R3(ao.zeta,
												ao.zeta2[0], ao.zeta2[1],
												ao.zeta2[2], rad);
							else
								tempvalue *= coeff
										* SlaterOrbitals.R3STO(ao.zeta, rad);
							break;
						case 4:
							tempvalue *= coeff
									* SlaterOrbitals.R4STO(ao.zeta, rad);
						}
					}
					this.Add(i, j, k, tempvalue);
				}

			}
		}
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.molecule.data.SpatialData#getControlPanel()
	 */
	@Override
	public SpatialDataControl getControlPanel() {
		return new SpatialDataMOControl(this, getName());
	}
}
