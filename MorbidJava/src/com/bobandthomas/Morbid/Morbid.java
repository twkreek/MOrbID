package com.bobandthomas.Morbid;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JApplet;
import javax.swing.JOptionPane;

import com.bobandthomas.Morbid.Gadget.Scene;
import com.bobandthomas.Morbid.graphics.renderers.PortJava3D;
import com.bobandthomas.Morbid.graphics.renderers.RendererJava3D;
import com.bobandthomas.Morbid.molecule.Molecule;
import com.bobandthomas.Morbid.molecule.reader.FileReaderMopac13;
import com.bobandthomas.Morbid.molecule.reader.FileReaderPCModel;
import com.bobandthomas.Morbid.molecule.reader.FileReaderPDB;
import com.bobandthomas.Morbid.molecule.reader.FileReaderSDF;
import com.bobandthomas.Morbid.molecule.reader.MoleculeFileReader;
import com.bobandthomas.Morbid.wrapper.MorbidBufferedReader;
import com.bobandthomas.Morbid.wrapper.ResourceMgr;
import com.bobandthomas.Morbid.UI.ControlPanelSideBar;
import com.bobandthomas.Morbid.UI.HudPanel;
import com.bobandthomas.Morbid.UI.Logger;
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

	//	add(new MorbidMenus(), "North");
		setJMenuBar(new MorbidMenus());
		
		// create canvas and gadget panel
		port = new PortJava3D(); //3D Canvas is in the port.
		add ("Center", port.canvas);
		scene = new Scene();

		gadgetPanel = new ControlPanelSideBar(scene);
		add("West", gadgetPanel);
		
		// Make the renderer
		
		RendererJava3D renderer;
		renderer = new RendererJava3D();
		scene.SetRenderer(renderer);
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
	private Molecule readFile(String name, MorbidBufferedReader br, MoleculeFileReader file)
	{
		Molecule m = new Molecule();
		file.init(name, m, br); 
		file.Read();
		try {
			br.close();
		} catch (IOException e) {
			Logger.addMessage(this, e);
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
//		findExamples();
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
				"testPCM:test.pcm",
				"BenzeneF13:benzene.f13"
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
				m = readFile(spl[0], ResourceMgr.getResourceFile("samples/"+ spl[1]), new FileReaderPDB());
			if (file[1].toLowerCase().equals("pcm"))
				m = readFile(spl[0], ResourceMgr.getResourceFile("samples/"+ spl[1]), new FileReaderPCModel());
			if (file[1].toLowerCase().equals("sdf"))
				m = readFile(spl[0], ResourceMgr.getResourceFile("samples/"+ spl[1]), new FileReaderSDF());
			if (file[1].toLowerCase().equals("f13"))
				m = readFile(spl[0], ResourceMgr.getResourceFile("samples/"+ spl[1]), new FileReaderMopac13());

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
		Logger.addMessage(m,m.getEmpirical().getFormula());
		scene.setPauseRender(true);
		scene.SetMolecule(m);

		gadgetPanel.makeDefaultGadgets(m);
		gadgetPanel.makeDefaultPanels();
		scene.setPauseRender(false);
		scene.SetPort(port);
		scene.Render();
			
	}
	
}
