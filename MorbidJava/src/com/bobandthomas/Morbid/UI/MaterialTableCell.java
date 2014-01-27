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

public class MaterialTableCell extends MorbidTableCell {
	Material currentMaterial;
	public class MaterialPreview extends JPanelPort implements MaterialChangeListener
	{
		
		ZRender renderer = new ZRender();
		GobList gl = new GobList();
		GobListSet gls = new GobListSet();
		LightSourceList lightSources = new LightSourceList();
		SphereGob sphere;
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
		public void redraw()
		{
			for (Gob g : gl)
				g.setMaterial(currentMaterial);
			renderer.DoRender(gls, lightSources, new CTM());
			SwapBuffers();
			
		}
		@Override
		public void handleEvent(MaterialChangeEvent event) {
			redraw();
			
		}
		
		
	}
	public class MaterialEditor extends MorbidPanel
	{
		MaterialPreview preview;
		JButton colorButton;
		JColorChooser colorChooser;
		JDialog dialog;
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

	public class MaterialTableCellEditor extends MCellEditor implements TableCellEditor,
			ActionListener {
		JButton button;
		MaterialEditor matEditor;
		JDialog dialog;
		protected static final String EDIT = "edit";

		public MaterialTableCellEditor() {
			button = new JButton();
			button.setActionCommand(EDIT);
			button.addActionListener(this);
			button.setBorderPainted(false);

		}

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
		public Object getCellEditorValue() {
			return currentMaterial;
		}

		// Implement the one method defined by TableCellEditor.
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			currentMaterial = (Material) value;
			return button;
		}
	}

	class MaterialCellRenderer extends MCellRenderer {
		JLabel label;
		Border unselectedBorder = null;
		Border selectedBorder = null;

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


	public MaterialTableCell() {
		super();
		editor = new MaterialTableCellEditor();
		renderer = new MaterialCellRenderer();
	}

}
