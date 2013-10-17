package com.bobandthomas.Morbid;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.IOException;

import javax.swing.JApplet;
import javax.swing.JOptionPane;

import com.bobandthomas.Morbid.Gadget.Scene;
import com.bobandthomas.Morbid.graphics.PortJava3D;
import com.bobandthomas.Morbid.graphics.RendererJava3D;
import com.bobandthomas.Morbid.molecule.Molecule;
import com.bobandthomas.Morbid.molecule.reader.FileReaderPCModel;
import com.bobandthomas.Morbid.molecule.reader.FileReaderPDB;
import com.bobandthomas.Morbid.molecule.reader.FileReaderSDF;
import com.bobandthomas.Morbid.molecule.reader.MoleculeFileReader;
import com.bobandthomas.Morbid.wrapper.ResourceMgr;
import com.bobandthomas.Morbid.UI.ControlPanelSideBar;
import com.bobandthomas.Morbid.UI.HudPanel;
import com.bobandthomas.Morbid.UI.MorbidMenus;

public class Morbid extends JApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8227285965421826153L;
	PortJava3D port;
	Scene scene;
	Molecule m;
	ControlPanelSideBar gadgetPanel;
	public Morbid()
	{
		super();
	}
	@Override
	public void init()
	{
		// set up default applet presentation
		setSize(800,600);
		setLayout(new BorderLayout());
		setJMenuBar(new MorbidMenus());
		
		// create canvas and gadget panel
		port = new PortJava3D(); //3D Canvas is in the port.
		add ("Center", port.canvas);
		scene = new Scene();

		gadgetPanel = new ControlPanelSideBar(scene);
		add("West", gadgetPanel);
		
		setGlassPane(new HudPanel());
		
		
		// Make the renderer
		
		RendererJava3D renderer;
		renderer = new RendererJava3D();
		scene.SetRenderer(renderer);
	}
	
	private Molecule readFile(String name, BufferedReader br, MoleculeFileReader file)
	{
		Molecule m = new Molecule();
		file.init(name, m, br); 
		file.Read();
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return m;
		
	}
/*	private BufferedReader getReaderFromURL(String url)
	{
		BufferedReader br = ReaderManager.get().getFromURL(url);
		return br;
	}
*/
	
	private Molecule showSampleSelection()
	{
		Object [] list = {"Glucagon:1GCN.pdb", 
				"Actin:1ATN.pdb",
				"Oxygenated Hemoglobin:1GZX.pdb", 
				"HCA Fragment:HCA5.pcm", 
				"Horse Hemoglobin:2DHB.pdb", 
				"Ibuprofen:ibuprofen.sdf",
				"cetirizine:cetirizine.sdf",
				"albuterol:albuterol.sdf",
				"Diphenhydramine:diphenhydramine.sdf",
				"tinyMolecule:0.0",
				"testPCM:test.pcm"
			};
		String s = (String)JOptionPane.showInputDialog(
                this,
                "select a sample",
                "Customized Dialog",
                JOptionPane.PLAIN_MESSAGE,
                null,
                list,
                "");
		Molecule m = new Molecule();
		if (s != null && s.length() > 0)
		{
			String [] spl = s.split(":");
			String fileName = spl[1];
			String [] file= fileName.split("[.]");
			
			if (file[1].toLowerCase().equals("pdb"))
				m = readFile(spl[0], ResourceMgr.getResourceFile("data/"+ spl[1]), new FileReaderPDB());
			if (file[1].toLowerCase().equals("pcm"))
				m = readFile(spl[0], ResourceMgr.getResourceFile("data/"+ spl[1]), new FileReaderPCModel());
			if (file[1].toLowerCase().equals("sdf"))
				m = readFile(spl[0], ResourceMgr.getResourceFile("data/"+ spl[1]), new FileReaderSDF());
			if (file[1].equals("0"))
				m.makeTinyMolecule();
		}
		return m;
	}
	@Override
	public void start()
	{
		//create some molecule
		if (m!= null) return;
		m=this.showSampleSelection();
		m.CenterAtoms();
		System.out.println(m.empirical.getFormula());
		scene.setPauseRender(true);
		scene.SetMolecule(m);

		gadgetPanel.makeDefaultGadgets(m);
		gadgetPanel.makeDefaultPanels();
		scene.setPauseRender(false);
		scene.SetPort(port);
		scene.Render();
			
	}
	
}
