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
package com.bobandthomas.Morbid.UI;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.TableCellEditor;

import com.bobandthomas.Morbid.graphics.CTM;
import com.bobandthomas.Morbid.graphics.Gob;
import com.bobandthomas.Morbid.graphics.GobList;
import com.bobandthomas.Morbid.graphics.GobListSet;
import com.bobandthomas.Morbid.graphics.LightSource;
import com.bobandthomas.Morbid.graphics.LightSourceList;
import com.bobandthomas.Morbid.graphics.Material;
import com.bobandthomas.Morbid.graphics.Material.MaterialChangeEvent;
import com.bobandthomas.Morbid.graphics.Material.MaterialChangeListener;
import com.bobandthomas.Morbid.graphics.SphereGob;
import com.bobandthomas.Morbid.graphics.renderers.JPanelPort;
import com.bobandthomas.Morbid.graphics.renderers.ZRender;
import com.bobandthomas.Morbid.utils.BoundingBox;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.Point3D;

// TODO: Auto-generated Javadoc
/**
 * The Class MaterialTableCell.
 * 
 * @author Thomas Kreek
 */
public class MaterialTableCell extends MorbidTableCell {
	
	/** The current material. */
	Material currentMaterial;
	
	/**
	 * The Class MaterialPreview.
	 * 
	 * @author Thomas Kreek
	 */
	public class MaterialPreview extends JPanelPort implements MaterialChangeListener
	{
		
		/** The renderer. */
		ZRender renderer = new ZRender();
		
		/** The gl. */
		GobList gl = new GobList();
		
		/** The gls. */
		GobListSet gls = new GobListSet();
		
		/** The light sources. */
		LightSourceList lightSources = new LightSourceList();
		
		/** The sphere. */
		SphereGob sphere;
		
		/**
		 * Instantiates a new material preview.
		 */
		public MaterialPreview()
		{
			currentMaterial.registerListener((MaterialChangeListener)this);
			/** construct scene objects for preview */
			sphere = new SphereGob(new Point3D(0.2, 0.2, -0.3), 0.65);
			sphere.setMaterial(currentMaterial);
			gl.add(sphere);
			sphere = new SphereGob(new Point3D(-0.3, -0.3, 0.4), 0.35);
			sphere.setMaterial(currentMaterial);
			gl.add(sphere);
			gls.add(gl);
			/** set up port */
			renderer.SetPort(this);
			/** set up light sources */
			LightSource ls;
			ls = new LightSource(new ColorQuad(255, 200, 150));
			ls.setName("Pinkish Light");
			lightSources.add(ls);
			ls = new LightSource(new ColorQuad(150, 200, 255), new Point3D(20.0f,
					-10.0f, 40.0f));
			ls.setName("Bluish light");
			lightSources.add(ls);
			lightSources.registerListener(this);
			

			BoundingBox box = new BoundingBox();
			box.setMin(new Point3D(-1,-1,-1));
			box.setMax(new Point3D(1,1,1));
			renderer.SetWorldBox(box);
			renderer.DoRender(gls, lightSources, new CTM());
			SwapBuffers();

			
		}
		
		/**
		 * Redraw.
		 */
		public void redraw()
		{
			for (Gob g : gl)
				g.setMaterial(currentMaterial);
			renderer.DoRender(gls, lightSources, new CTM());
			SwapBuffers();
			
		}
		
		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.utils.IMorbidListener#handleEvent(com.bobandthomas.Morbid.utils.MorbidEvent)
		 */
		@Override
		public void handleEvent(MaterialChangeEvent event) {
			redraw();
			
		}
		
		
	}
	
	/**
	 * The Class MaterialEditor.
	 * 
	 * @author Thomas Kreek
	 */
	public class MaterialEditor extends MorbidPanel
	{
		
		/** The preview. */
		MaterialPreview preview;
		
		/** The color button. */
		JButton colorButton;
		
		/** The color chooser. */
		JColorChooser colorChooser;
		
		/** The dialog. */
		JDialog dialog;
		
		/**
		 * Instantiates a new material editor.
		 * 
		 * @param currentMaterial
		 *            the current material
		 */
		MaterialEditor(Material currentMaterial)
		{
			super("Material");
			preview = new MaterialTableCell.MaterialPreview();
			createLabel(currentMaterial.getName());
			colorButton = createButton("Color");
			colorButton.setBackground(currentMaterial.getColor().getJColor());;
			createSlider("Ambient", 0, 100, (int) (currentMaterial.getkAmbient()*100), true); 
			createSlider("Diffuse", 0, 100, (int) (currentMaterial.getkDiffuse()*100), true); 
			createSlider("Emission", 0, 100, (int) (currentMaterial.getkEmission()*100), true); 
			createSlider("Specular", 0, 100, (int) (currentMaterial.getKSpecular()*100), true);
			createCheckbox("useSpecular", currentMaterial.isUseSpecularity());
			createSlider("Shininess", 1, 128, currentMaterial.getSpecularity(), true);
			createSlider("Alpha", 0,100, (int) (currentMaterial.getAlpha()*100), true);
			
		}	
		
		/**
		 * Gets the dialog.
		 * 
		 * @return the dialog
		 */
		JDialog getDialog()
		{
			JDialog dlg = new JDialog(null, Dialog.ModalityType.APPLICATION_MODAL);
			dlg.setSize(new Dimension(500, 500));
			dlg.setLayout(new FlowLayout());
			dlg.add(this);
			dlg.add(preview.getPanel());
			preview.getPanel().setPreferredSize(new Dimension(200, 200));
			preview.redraw();
			return dlg;
		}
		
		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.UI.MorbidPanel#changeValue(java.lang.String, java.lang.Integer)
		 */
		@Override
		public void changeValue(String label, Integer value) {
			if (label.equals("Color"))
			{

				colorChooser = new JColorChooser();
				dialog = JColorChooser.createDialog(colorButton, "Diffuse color for Material", true, // modal
						colorChooser, this, // OK button handler
						null); // no CANCEL button handler
				dialog.setVisible(true);
				preview.redraw();
				return;
			}	
			if (label.equals("Diffuse"))
			{
				currentMaterial.setkDiffuse(value/100.0);
				preview.redraw();
				return;
			}
			if (label.equals("Emission"))
			{
				currentMaterial.setkEmission(value/100.0);
				preview.redraw();
				return;
			}
			if (label.equals("Ambient"))
			{
				currentMaterial.setkAmbient(value/100.0);
				preview.redraw();
				return;
			}
			if (label.equals("Specular"))
			{
				currentMaterial.setKSpecular(value/100.0);
				preview.redraw();
				return;
			}
			if (label.equals("useSpecular"))
			{
				currentMaterial.setUseSpecularity(value == 1);
				preview.redraw();
				return;
			}
			if (label.equals("Diffuse"))
			{
				currentMaterial.setkDiffuse(value/100.0);
				preview.redraw();
				return;
			}
			if (label.equals("Alpha"))
			{
				currentMaterial.setAlpha(value/100.0);
				preview.redraw();
				return;
			}
			if (label.equals("Shininess"))
			{
				currentMaterial.setSpecularity(value);
				preview.redraw();
				return;
			}

		}
		
		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.UI.MorbidPanel#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			if ("OK".equals(e.getActionCommand()))
			{
				currentMaterial.setColor(new ColorQuad(colorChooser.getColor()));
				colorButton.setBackground(currentMaterial.getColor().getJColor());;
				return;
			}
			super.actionPerformed(e);
		}
	}

	/**
	 * The Class MaterialTableCellEditor.
	 * 
	 * @author Thomas Kreek
	 */
	public class MaterialTableCellEditor extends MCellEditor implements TableCellEditor,
			ActionListener {
		
		/** The button. */
		JButton button;
		
		/** The mat editor. */
		MaterialEditor matEditor;
		
		/** The dialog. */
		JDialog dialog;
		
		/** The Constant EDIT. */
		protected static final String EDIT = "edit";

		/**
		 * Instantiates a new material table cell editor.
		 */
		public MaterialTableCellEditor() {
			button = new JButton();
			button.setActionCommand(EDIT);
			button.addActionListener(this);
			button.setBorderPainted(false);

		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			if (EDIT.equals(e.getActionCommand())) {
				// The user has clicked the cell, so
				// bring up the dialog.
				button.setBackground(currentMaterial.getColor().getJColor());
				// Set up the dialog that the button brings up.
				matEditor = new MaterialEditor(currentMaterial);
				dialog = matEditor.getDialog();
				dialog.setVisible(true);

				fireEditingStopped(); // Make the renderer reappear.

			} else { // User pressed dialog's "OK" button.
//				currentMaterial.setColor(new ColorQuad(colorChooser.getColor()));
			}
		}

		// Implement the one CellEditor method that AbstractCellEditor doesn't.
		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.UI.MorbidTableCell.MCellEditor#getCellEditorValue()
		 */
		public Object getCellEditorValue() {
			return currentMaterial;
		}

		// Implement the one method defined by TableCellEditor.
		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.UI.MorbidTableCell.MCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
		 */
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			currentMaterial = (Material) value;
			return button;
		}
	}

	/**
	 * The Class MaterialCellRenderer.
	 * 
	 * @author Thomas Kreek
	 */
	class MaterialCellRenderer extends MCellRenderer {
		
		/** The label. */
		JLabel label;
		
		/** The unselected border. */
		Border unselectedBorder = null;
		
		/** The selected border. */
		Border selectedBorder = null;

		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.UI.MorbidTableCell.MCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
		 */
		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			currentMaterial = (Material) value;
			label = new JLabel();
			label.setOpaque(true);
			label.setBackground(currentMaterial.getColor().getJColor());
			if (isSelected) {
				if (selectedBorder == null) {
					selectedBorder = BorderFactory.createMatteBorder(2, 5, 2,
							5, table.getSelectionBackground());
				}
				label.setBorder(selectedBorder);
			} else {
				if (unselectedBorder == null) {
					unselectedBorder = BorderFactory.createMatteBorder(2, 5, 2,
							5, table.getBackground());
				}
				label.setBorder(unselectedBorder);
			}
			return label;
		}
	}


	/**
	 * Instantiates a new material table cell.
	 */
	public MaterialTableCell() {
		super();
		editor = new MaterialTableCellEditor();
		renderer = new MaterialCellRenderer();
	}

}
