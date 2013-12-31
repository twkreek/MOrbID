package com.bobandthomas.Morbid;

import java.awt.BorderLayout;

import javax.swing.JApplet;
import javax.swing.JOptionPane;

import com.bobandthomas.Morbid.Gadget.Scene;
import com.bobandthomas.Morbid.graphics.renderers.PortJava3D;
import com.bobandthomas.Morbid.graphics.renderers.RendererJava3D;
import com.bobandthomas.Morbid.molecule.Molecule;
import com.bobandthomas.Morbid.molecule.reader.MoleculeFileReaderManager;
import com.bobandthomas.Morbid.wrapper.Logger;
import com.bobandthomas.Morbid.wrapper.ResourceMgr;
import com.bobandthomas.Morbid.UI.ControlPanelSideBar;
import com.bobandthomas.Morbid.UI.MorbidMenus;

public class Morbid extends JApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8227285965421826153L;
	@SuppressWarnings("unused")
	private static final String s ="2$2K@)8JgJ#?h~<";
	PortJava3D port;
	Scene scene;
	ControlPanelSideBar gadgetPanel;
	public Morbid()
	{
		super();
		gadgetPanel = null;
	}
	@Override
	public void init()
	{
		// set up default applet presentation
		setSize(800,600);
		setJMenuBar(new MorbidMenus(this));
		setLayout(new BorderLayout());

				
	}
	public Scene getScene() {return scene;}
	public ControlPanelSideBar getGadgetPanel() { return gadgetPanel; }
	public void openFile()
	{
		//create some molecule
		Molecule m;
		m=this.showSampleSelection();
		m.CenterAtoms();
		Logger.addMessage(m,m.getEmpirical().getFormula());
		createScene(m);

	}
	
	public Scene createScene(Molecule m)
	{
		scene = new Scene();
		// Make the renderer
		
		if (port != null)
		{
			this.remove(port.canvas);
		}
		// create canvas and gadget panel
		port = new PortJava3D(); //3D Canvas is in the port.
		
		add ("Center", port.canvas);

		RendererJava3D renderer;
		renderer = new RendererJava3D();
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
	boolean started = false;
	@Override
	public void start()
	{
		if (started) return;
		started = true;
		openFile();
			
	}
	
}
