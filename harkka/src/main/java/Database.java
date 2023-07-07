//package com.sainvo.oop.harkka.java;

import java.io.*;
//import java.nio.DoubleBuffer;
/*
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
*/
//import java.net.URI;
//import java.nio.file.FileSystem;
//import java.nio.file.Files;
//import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.nio.file.WatchEvent.Kind;
//import java.nio.file.WatchEvent.Modifier;
//import java.nio.file.WatchKey;
//import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
//import java.io.StringWriter;
 

import org.w3c.dom.*;
//import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;
import java.io.StringWriter;
 
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/** @Sanna Volanen
 * 
 * creates a new DBpath as xml file
 * if there is no DB already or if the user wants to create a new one 
 *
 * functions for DB actions: searches, adding, deleting, creating a folder and csv file
 */
public class Database {
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L; //if extends File
	protected String name;
	//default saving location Path
	private Path defaultPath = Paths.get(System.getProperty("user.home") + "/Desktop/Databases");
	//created from default Path
	private File defaultFolder;
	// chosen folder
	public File DBfolder;
	private final File DBfolder_root;
	public Path DBPath;
	public File resourceFolder;
	public File sourcesFile;
	//public File quotesFile;
	/*
	 * xml file
	 * created at above folder based on user given name
	 */
	public File DBfile;
	protected Path DBpath;
	public Map<Double, ArrayList<String>> allQuotes = new HashMap<Double, ArrayList<String>>();

	DocumentBuilderFactory factory;
	DocumentBuilder builder;
	Document DBdoc;

	//CONSTRUCTOR
	//creating a new xml file at default location or given folder
	public Database(String name) {
		Scanner sc = new Scanner(System.in);
		System.out.println(
				"Choose a folder where your new DBfile will be saved. If you don't choose any, a default folder is created on your Desktop.");
		this.DBfolder_root = chooseFolder();
		if (DBfolder_root != null && DBfolder_root.toString().length() > 0) {
			this.name = name;
			//File DBfolder = createNewSubfolder(name);
			this.DBfolder =createNewSubfolder(name,DBfolder_root);
			this.DBpath = DBfolder.toPath();
			this.resourceFolder = createNewSubfolder(name + "_resources", DBfolder);
			
			// Doc facto
			docFactory();
			this.sourcesFile = XMLCreator( name + "_sources");
			//System.out.println("New xml "+name+" created at "+ DBfolder.toString());
			//this.quotesFile = DocCreator(builder, name+"_quotes");
			//addDBToList(this);
			System.out.println("A new DBpath was created at " + DBpath.toString());
		}
	//	System.out.println(DBlist.keySet());
		sc.close();
	}

	public File getDBfolder() {
		return DBfolder;
	}

	public File getDBfile() {
		return DBfile;
	}

	public File getDBfile(String name) {
		File temp = null;
		if (DBfile.getName().contains(name)) {
			temp = DBfile;
		} else {
			System.out.println("No such database found");
		}
		return temp;
	}

	public File getResourceFolder() {
		return resourceFolder;
	}

	//METHODS

	private void docFactory() {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException parserError) {
			parserError.printStackTrace();
		}
		this.factory = dbFactory;
		this.builder = dBuilder;
	}
	
	//user choosing a folder from file system as saving location
	protected File chooseFolder() {
		File folder = null;
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new java.io.File("."));
		fc.setDialogTitle("Save DB_file.xml to chosen folder");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setAcceptAllFileFilterUsed(false);
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			folder = fc.getSelectedFile();
			System.out.println("Chosen folder for saving DBfiles is: " + folder.getPath()+"\n");
		} else if (returnVal == JFileChooser.CANCEL_OPTION) {
			if (defaultFolder != null) {
				System.out.println(" No folder selected. Default folder exists and selected instead.");
				folder = defaultFolder;
			} else {
				if (createDefFolder()) {
					folder = defaultFolder;
					System.out.println(
							"No folder selected. Creating a default folder and saving to default location"
									+ defaultFolder.getPath());
				}
			}
		}
		return folder;
	}

	//creates a new DB folder as saving location in the above default location 
	private boolean createDefFolder() {
		boolean created = false;
		File temp = new File(defaultPath.toString());
		if (!temp.isDirectory()) {
			created = temp.mkdirs();
			if (created) {
				this.defaultFolder = temp;
				System.out.println("Default folder created.");
			} else {
				System.out.println("No default saving location available");
			}
		}
		return created;
	}

	public File createNewSubfolder(String name, File rootFolder) {
		File newFolder = null;
		File temp = new File(rootFolder.toString() + "/" + name);
		if (!temp.isDirectory()) {
			if (temp.mkdirs()) {
				newFolder = temp;
				System.out.println("New subfolder " + temp.getName() + " created in DBfolder.");
			} else {
				System.out.println("No saving location available for downloads");
			}
		}
		return newFolder;
	}
	/*
	 * Creates a new XML file at DB folder
	 * @.pre DBfolder not null
	 * @.post DBfolder.contains("name.xml")
	 */

	protected File XMLCreator(String name) {
		File temp = new File(DBfolder.toString() + "/" + name + ".xml");
		try {
			DBdoc = builder.newDocument();

			// root element
			Element rootElement = DBdoc.createElement(name);
			DBdoc.appendChild(rootElement);
			/* 
			// supercars element
			Element supercar = doc.createElement("supercars");
			rootElement.appendChild(supercar);
			
			// setting attribute to element
			Attr attr = doc.createAttribute("company");
			attr.setValue("Ferrari");
			supercar.setAttributeNode(attr);
			
			// carname element
			Element carname = doc.createElement("carname");
			Attr attrType = doc.createAttribute("type");
			attrType.setValue("formula one");
			carname.setAttributeNode(attrType);
			carname.appendChild(doc.createTextNode("Ferrari 101"));
			supercar.appendChild(carname);
			
			Element carname1 = doc.createElement("carname");
			Attr attrType1 = doc.createAttribute("type");
			attrType1.setValue("sports");
			carname1.setAttributeNode(attrType1);
			carname1.appendChild(doc.createTextNode("Ferrari 202"));
			supercar.appendChild(carname1);
			*/
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(DBdoc);
			StreamResult result = new StreamResult(temp);
			transformer.transform(source, result);

			// Output to console for testing
			StreamResult consoleResult = new StreamResult(System.out);
			transformer.transform(source, consoleResult); //prints xml
		} catch (Exception e) {
			System.out.println("ERROR: DocCreator, printing StackTrace \n");
			e.printStackTrace();
		}
		return temp;

	}
	private static void jaxbObjectToXML(Source source) 
  {
      try
      {
        //Create JAXB Context
          JAXBContext jaxbContext = JAXBContext.newInstance(Source.class);
           
          //Create Marshaller
          Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
 
          //Required formatting??
          jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
 
         //Store XML to File
          File file = new File("employee.xml");
           
          //Writes XML file to file-system
          jaxbMarshaller.marshal(source, file); 
      } 
      catch (JAXBException e) 
      {
          e.printStackTrace();
      }
  }
	
	/**
	 * 
	 * @param doc the {DB_name}_sources xml as Document
	 * @param s Source object to be added
	 * @return edited doc containing the a new child Node for s
	 */
	protected File XMLEditor( Document doc, Source s) {
		//File temp = new File(DBfolder.toString() + "/" + name + ".xml");
		try {
			//Document doc = builder.newDocument();

			// 1st child Node
			Node rootElement = doc.getFirstChild(); //name of the db
			Element source = doc.createElement(s.getIdNumber().toString());
			rootElement.appendChild(source);
			

			/* 
			// supercars element
			Element supercar = doc.createElement("supercars");
			rootElement.appendChild(supercar);
			
			// setting attribute to element
			Attr attr = doc.createAttribute("company");
			attr.setValue("Ferrari");
			supercar.setAttributeNode(attr);
			
			// carname element
			Element carname = doc.createElement("carname");
			Attr attrType = doc.createAttribute("type");
			attrType.setValue("formula one");
			carname.setAttributeNode(attrType);
			carname.appendChild(doc.createTextNode("Ferrari 101"));
			supercar.appendChild(carname);
			
			Element carname1 = doc.createElement("carname");
			Attr attrType1 = doc.createAttribute("type");
			attrType1.setValue("sports");
			carname1.setAttributeNode(attrType1);
			carname1.appendChild(doc.createTextNode("Ferrari 202"));
			supercar.appendChild(carname1);
			*/
			// write the content into xml file
			/* 
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource dom = new DOMSource(doc);
			StreamResult result = new StreamResult(sourcesFile);
			transformer.transform(dom, result);

			// Output to console for testing
			StreamResult consoleResult = new StreamResult(System.out);
			transformer.transform(dom, consoleResult);
			*/
		} catch (Exception e) {
			System.out.println("ERROR: DocCreator, printing StackTrace \n");
			e.printStackTrace();
		}
		return doc;

	}

	protected void XMLPrinter(Document doc) {
		if (doc != null){
			
			//try {
			//	this.doc = builder.newDocument();

				// root element
			//	Element rootElement = doc.createElement(name);
			//doc.appendChild(rootElement);
			/* 
			// supercars element
			Element supercar = doc.createElement("supercars");
			rootElement.appendChild(supercar);
			
			// setting attribute to element
			Attr attr = doc.createAttribute("company");
			attr.setValue("Ferrari");
			supercar.setAttributeNode(attr);
			
			// carname element
			Element carname = doc.createElement("carname");
			Attr attrType = doc.createAttribute("type");
			attrType.setValue("formula one");
			carname.setAttributeNode(attrType);
			carname.appendChild(doc.createTextNode("Ferrari 101"));
			supercar.appendChild(carname);
			
			Element carname1 = doc.createElement("carname");
			Attr attrType1 = doc.createAttribute("type");
			attrType1.setValue("sports");
			carname1.setAttributeNode(attrType1);
			carname1.appendChild(doc.createTextNode("Ferrari 202"));
			supercar.appendChild(carname1);
			*/
			// write the content into xml file
		} else {
			File temp = 
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult();
			transformer.transform(source, result);

			// Output to console for testing
			StreamResult consoleResult = new StreamResult(System.out);
			transformer.transform(source, consoleResult);
		}
	}
	/* 
	protected Document XMLReader(File f) {
		//File temp = new File(DBfolder.toString() + "/" + dbName + ".xml");
		String name = f.getName().split(".")[0];
		try {
			StringBuilder xmlStringBuilder = new StringBuilder();
			xmlStringBuilder.append("<?xml version=\"1.0\"?> <"+name+"> </"+name+">");
			ByteArrayInputStream input = new ByteArrayInputStream(
   			xmlStringBuilder.toString().getBytes("UTF-8"));
			Document doc = builder.parse(input);
		}catch(){

		}*/
			/* 
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			//StreamResult result = new StreamResult(temp);
			//transformer.transform(source, result);

			// Output to console for testing
			StreamResult consoleResult = new StreamResult(System.out);
			transformer.transform(source, consoleResult);
		} catch (Exception e) {
			System.out.println("ERROR: DocPrinter, printing StackTrace \n");
			e.printStackTrace();
		}
		
	}*/
	public Document XMLreader(File inputFile) {
		if (inputFile != null) {
			try {
				StringBuilder xmlStringBuilder = new StringBuilder();
				//xmlStringBuilder.append("<?xml version="1.0"?> <source> <\source>?");
				ByteArrayInputStream input = new ByteArrayInputStream(
				xmlStringBuilder.toString().getBytes("UTF-8"));
			} catch (UnsupportedEncodingException u) {
				System.out.println("Cannot read database " + inputFile.toString() + ", wrong encoding");
				u.printStackTrace();
			}
			try {
				Document curr_doc = builder.parse(inputFile);
				curr_doc.getDocumentElement().normalize();
				Element root = curr_doc.getDocumentElement();
				System.out.println("Root element :" + curr_doc.getDocumentElement().getNodeName());
				NamedNodeMap attributes= root.getAttributes();
				NodeList nList = curr_doc.getElementsByTagName("source");
				for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				System.out.println("\nCurrent Element :" + nNode.getNodeName());
				
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					Object eEleClass = eElement.getClass();
					HashMap<String, String> specs = ((Source) eEleClass).getSourceInformation();
					System.out.println("Source no : " 
					+ eElement.getAttribute("ID"));
					System.out.println("Type : " 
					+ eElement
					.getElementsByTagName("Type")
					.item(0)
					.getTextContent());
					System.out.println("Media : " 
					+ eElement
					.getElementsByTagName("Media")
					.item(0)
					.getTextContent());
					for key in list(specs.keySet()):
					System.out.println("Nick Name : " 
					+ eElement
					.getElementsByTagName("nickname")
					.item(0)
					.getTextContent());
					System.out.println("Marks : " 
					+ eElement
					.getElementsByTagName("marks")
					.item(0)
					.getTextContent());
				}
	    		}
			} catch (SAXException sax) {
				//System.out.println("Cannot read database " + inputFile.toString() + ", wrong encoding");
				sax.printStackTrace();
			
			} catch (IOException io) {
				//System.out.println("Cannot read database " + inputFile.toString() + ", wrong encoding");
				io.printStackTrace();
			}
			
			
		} 
		else {
			System.out.println("Cannot read database, given file does not exist");
		}
		
	}
	private void setNewAttributeNode(Document doc, String name, Object value) {
		Attr attr = doc.createAttribute(name);
		if (value instanceof String) {
			attr.setValue(value.toString());
		}
		else if (value instanceof Integer || value instanceof Double) {
			attr.setValue(value.toString());
		}else if(value instanceof HashMap){
			List keys = value.keySet().toList();
		}
		
         	
		
	}

	//not altered
	public File chooseDBfile(HashMap<String, Database> databases) {
		File temp = null;
		//HashMap<String, Database> databases = Database.getDBlist();
		if (!databases.isEmpty()) {
			System.out.println("Choose which database file this source belongs to:");
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new java.io.File(System.getProperty("user.home") + "/Downloads"));
			fc.setDialogTitle("Choose DBfile");
			FileNameExtensionFilter filter = new FileNameExtensionFilter(null, "tsv", "csv");
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fc.setFileFilter(filter);
			int returnVal = fc.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				temp = fc.getSelectedFile();
				System.out.println("Chosen DBfile is: " + temp.getPath());
			} else if (returnVal == JFileChooser.CANCEL_OPTION) {
				System.out.println("No folder selected.");
			}
		} else {
			System.out.println("No database exists. Create a new database by giving a name:");
			Scanner sc = new Scanner(System.in);
			Database db = new Database(sc.nextLine());
			temp = db.getDBfile();
			sc.close();
		}
		return temp;
	}

	//adding a source to the DBfile
	public void addSourceToDB(Source s, File target) {
		HashMap<String, String> specs = s.getSourceInformation();
		System.out.println("Adding "+specs.get("name")+ " to the DB "+ this.DBfile);

	}
	
	
	
	/*
	public void printDBfile(File f) {
		ArrayList<String> data = readDBfile(f);
		for (String item : data) {
			System.out.println(item);
		}
	}
	
	public static File chooseFileFromLocal() {
	   	File temp = null;
	   	System.out.println("Choose a already downloaded pdf: ");
	   	JFileChooser fc = new JFileChooser();
	   	fc.setCurrentDirectory(new java.io.File(""));
	   	fc.setDialogTitle("Choose file");
	   	fc.setAcceptAllFileFilterUsed(true);
	   	FileNameExtensionFilter filter = new FileNameExtensionFilter(null, "pdf");
	   	fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
	   	fc.setFileFilter(filter);
	   	int returnVal = fc.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			temp = fc.getSelectedFile();
			System.out.println("Chosen resource file is: "+ temp.getPath());
		}else if(returnVal == JFileChooser.CANCEL_OPTION){
			System.out.println("No file selected.");
		}
		return temp;
	}
	 */
}