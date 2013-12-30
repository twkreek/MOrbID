package com.bobandthomas.Morbid.molecule.data;

import com.bobandthomas.Morbid.molecule.AtomType;
import com.bobandthomas.Morbid.molecule.MolecularOrbitalSet;
import com.bobandthomas.Morbid.molecule.MolecularOrbitalSet.AtomicOrbital;
import com.bobandthomas.Morbid.molecule.Molecule;
import com.bobandthomas.Morbid.molecule.data.control.SpatialDataControl;
import com.bobandthomas.Morbid.molecule.data.control.SpatialDataMOControl;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.Vector3D;

public class SpatialDataMO extends SpatialData {

	int whichMO;
	int slice;

	public SpatialDataMO(Molecule mol) {
		super(mol);
		whichMO = 2;
		setName("MO");
	}

	public int getWhichMO() {
		return whichMO;
	}

	public void setWhichMO(int whichMO) {
		this.whichMO = whichMO;
		markDirty();
	}

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

	public void CalculateAllNone() {
		slice = 0;

		for (slice = 0; slice < sideX; slice++) {
			calcslice();
		}
	}

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

	@Override
	public SpatialDataControl getControlPanel() {
		return new SpatialDataMOControl(this, getName());
	}
}
