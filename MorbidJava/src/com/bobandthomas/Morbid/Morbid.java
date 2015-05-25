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
package com.bobandthomas.Morbid;

import java.awt.BorderLayout;

import javax.swing.JApplet;
import javax.swing.JOptionPane;

import com.bobandthomas.Morbid.Gadget.Scene;
import com.bobandthomas.Morbid.graphics.renderers.PortJava3D;
import com.bobandthomas.Morbid.graphics.renderers.Renderer;
import com.bobandthomas.Morbid.graphics.renderers.RendererJava3D;
import com.bobandthomas.Morbid.molecule.Molecule;
import com.bobandthomas.Morbid.molecule.reader.MoleculeFileReaderManager;
import com.bobandthomas.Morbid.wrapper.Logger;
import com.bobandthomas.Morbid.wrapper.ResourceMgr;
import com.bobandthomas.Morbid.UI.ControlPanelSideBar;
import com.bobandthomas.Morbid.UI.MorbidMenus;

/**
 * Morbid is the class that launches Morbid as an applet.  
 * 
 * @author Thomas Kreek
 */
public class Morbid extends JApplet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8227285965421826153L;
	
	/** The Constant s. */
	@SuppressWarnings("unused")
	private static final String s ="2$2K@)8JgJ#?h~<";
	
	/** The port. */
	PortJava3D port;
	
	/** The scene. */
	Scene scene;
	
	/** The gadget panel. */
	ControlPanelSideBar gadgetPanel;
	
	/**
	 * Instantiates a new morbid.
	 */
	public Morbid()
	{
		super();
		gadgetPanel = null;
	}
	
	/* (non-Javadoc)
	 * @see java.applet.Applet#init()
	 */
	@Override
	public void init()
	{
		// set up default applet presentation
		setSize(800,600);
		setJMenuBar(new MorbidMenus(this));
		setLayout(new BorderLayout());

				
	}
	
	/**
	 * Gets the scene.
	 * 
	 * @return the scene
	 */
	public Scene getScene() {return scene;}
	
	/**
	 * Gets the gadget panel.
	 * 
	 * @return the gadget panel
	 */
	public ControlPanelSideBar getGadgetPanel() { return gadgetPanel; }
	
	/**
	 * Open file - currently uses file content selected from showSampleSelection
	 */
	public void openFile()
	{
		//create some molecule
		Molecule m;
		m=this.showSampleSelection();
		m.CenterAtoms();
		Logger.addMessage(m,m.getEmpirical().getFormula());
		createScene(m);

	}
	
	/**
	 * Creates a scene for the application based on defuault gadgets and the molecule that is loaded.
	 * In this version, it uses the Java3D port and renderer, by default.
	 *
	 * 
	 * @param m
	 *            the m
	 * @return the scene
	 */
	public Scene createScene(Molecule m)
	{
		scene = new Scene();
		// Make the renderer
		
		if (port != null)
		{
			this.remove(port.canvas);
		}
		//TODO check for java3D support, and fallback to a universal port type and renderer, instead
		// create canvas and gadget panel
		port = new PortJava3D(); //3D Canvas is in the port.
		
		add ("Center", port.canvas);

		Renderer renderer;
		renderer = new RendererJava3D();
//		renderer = new RendererPOV();
//		renderer = new RendererX3D();

		scene.SetRenderer(renderer);
		
		scene.SetMolecule(m);
		
		if (gadgetPanel != null)
			remove(gadgetPanel);
	
		gadgetPanel = new ControlPanelSideBar(scene);
		add("West", gadgetPanel);

		scene.setPauseRender(true);

		gadgetPanel.makeDefaultGadgets();
		gadgetPanel.makeDefaultPanels();
		scene.setPauseRender(false);
		scene.SetPort(port);
		scene.Render();
		return scene;
	}

/*	void findExamples() {
		List<String> slikeList = new ArrayList<String>();

		URL urlToApplet = Morbid.class
				.getResource("/data");
		String[] parts = urlToApplet.toString().split("!");
		String jarURLString = parts[0].replace("jar:", "");
		System.out.println("Loading from " + jarURLString);

		URL jar;
		try {
			jar = new URL(jarURLString);

			URLConnection jarConnection = jar.openConnection();
			JarInputStream jis = new JarInputStream(
					jarConnection.getInputStream());

			JarEntry je = jis.getNextJarEntry();
			while (je != null) {
				System.out.println("Inspecting " + je);
					System.out.println("Adding " + je.getName());
					slikeList.add(je.getName());
				je = jis.getNextJarEntry();
			}
		} catch (MalformedURLException e) {
				Logger.addMessage(this, e);
		} catch (IOException e) {
				Logger.addMessage(this, e);
		}
	}*/


	/**
 * Show sample selection - a set of hard coded samples contained in the resources, just to make simple loading and demo.
 * 
 * @return the molecule read in from the selection file.
 */
private Molecule showSampleSelection()
	{
//		findExamples();
		Object [] list = {
				"Ibuprofen:ibuprofen.sdf",
				"Glucagon:1GCN.pdb", 
				"Actin:1ATN.pdb",
				"Oxygenated Hemoglobin:1GZX.pdb", 
				"HCA Fragment:HCA5.pcm", 
				"Horse Hemoglobin:2DHB.pdb", 
				"cetirizine:cetirizine.sdf",
				"albuterol:albuterol.sdf",
				"Diphenhydramine:diphenhydramine.sdf",
				"tinyMolecule:0.0",
				"testPCM:test.pcm",
				"BenzeneF13:benzene.f13"
			};
		String s = (String)JOptionPane.showInputDialog(
                this,
                "select a sample Molecule to display",
                "MOrbID - Open Sample",
                JOptionPane.PLAIN_MESSAGE,
                null,
                list,
                "");
		Molecule m = null;
		if (s != null && s.length() > 0)
		{
			String [] spl = s.split(":");
			String fileName = spl[1];
			String [] file= fileName.split("[.]");
			m = MoleculeFileReaderManager.readFile(spl[0], ResourceMgr.getResourceFile("samples/"+ spl[1]), file[1].toLowerCase());

			if (file[1].equals("0"))
				m.makeTinyMolecule();
		}
		
		return m;
	}
	
	/** is it started? */
	boolean started = false;
	
	/* (non-Javadoc)
	 * @see java.applet.Applet#start()
	 */
	@Override
	public void start()
	{
		if (started) return;
		started = true;
		openFile();
			
	}
	
}
