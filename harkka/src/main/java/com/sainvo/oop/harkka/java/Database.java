package com.sainvo.oop.harkka.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
/** @Sanna Volanen
 * 
 * creates a new DBpath as csv file
 * if there is no DB already or if the user wants to create a new one 
 *
 * functions for DB actions: searches, adding, deleting, creating a folder and csv file
 */
public class Database{
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
	private final File DBfolder;
	public File resourceFolder;
	public File quotesFile;
	/*
	 * CSV file
	 * created at above folder based on user given name
	 */
	public File DBfile;
	protected Path DBpath;
	public static HashMap<File,Database> DBlist = new HashMap<File,Database>();
	public Map <Double,ArrayList<String>> allQuotes = new HashMap <Double,ArrayList<String>>();
	
//CONSTRUCTOR
	//creating a new csv file at default or chosen folder
	public Database(String name) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Choose a folder where your new DBfile will be saved. If you don't choose any, a default folder is created.");
		this.DBfolder = chooseFolder();
		if(DBfolder != null && DBfolder.toString().length() > 0) {
			this.name = name;
			this.DBfile = createCSVFile(name);
			this.DBpath = DBfile.toPath();
			this.resourceFolder = createNewSubfolder("Resources");
			this.quotesFile = createCSVFile("quotes");
			addDBToList();
			System.out.println("A new DBpath was created at "+ DBpath.toString());
		}
		System.out.println(DBlist.keySet());
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
		if(DBfile.getName().contains(name)) {
			temp = DBfile;
		}else {
			System.out.println("No such database found");
		}
		return temp;
	}
	public File getResourceFolder() {
		return resourceFolder;
	}
	public static HashMap<File, Database> getDBlist() {
		return DBlist;
	}
	
	
	//METHODS
	
	private void addDBToList() {
		DBlist.put(DBfile, this);
	} 
	
	//user choosing a folder from file system as saving location
	protected File chooseFolder() {
		File folder = null;
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new java.io.File("."));
		fc.setDialogTitle("Save DBfile to chosen folder");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setAcceptAllFileFilterUsed(false);
		int returnVal = fc.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			folder = fc.getSelectedFile();
			System.out.println("Chosen folder for saving DBfiles is: "+ folder.getPath());
		}else if(returnVal == JFileChooser.CANCEL_OPTION){
			if(defaultFolder != null) {
				System.out.println(" No folder selected. Default folder exists and selected instead.");
				folder = defaultFolder;
			}else{
				if(createDefFolder()) {
					folder = defaultFolder;
					System.out.println("No folder selected. Creating a default folder and saving to default location" + defaultFolder.getPath());
				}
			}
		}return folder;
	}
		
	//creates a new DB folder as saving location in the above default location 
	private boolean createDefFolder() {
		boolean created = false;
		File temp = new File(defaultPath.toString());
		if(!temp.isDirectory()) {
			 created = temp.mkdirs();
			 if(created) {
				 this.defaultFolder = temp;
				 System.out.println("Default folder created.");
			 }
			 else{
				 System.out.println("No default saving location available");
			 }
		}
		return created;
	}
	public File createNewSubfolder(String name) {
		File newFolder = null;
		File temp = new File(DBfolder.toString()+"/" + name);
		if(!temp.isDirectory()) {
			 if(temp.mkdirs()) {
				 newFolder = temp;
				 System.out.println("New subfolder "+ temp.getName() +" created in DBfolder.");
			 }
			 else{
				 System.out.println("No saving location available for downloads");
			 }
		}return newFolder;
	}
	/*
	 * Creates a new TSV file at DB folder
	 * @.pre DBfolder not null
	 * @.post DBfolder.contains("name.tsv")
	 */
	private File createCSVFile(String name) {	
		String tempPath = "";
		File temp = null;
		if(DBfolder == null) {
			 createDefFolder();
		}else {
			tempPath = DBfolder.toString() + "/" + name + ".tsv";
			System.out.println("New tsv file created at " + tempPath);
		}
		if(!(Path.of(tempPath).toFile().exists())) {
			if(name != "quotes") {
				try {
					temp = File.createTempFile(name, "tsv", DBfolder);
					temp.setReadable(true);
					temp.setWritable(true);
					temp.setExecutable(true);
					//initializing the tab separated values file with labels
					FileWriter csvWriter = new FileWriter(temp);
					csvWriter.append("MEDIA");
					csvWriter.append("\t");
					csvWriter.append("ID");
					csvWriter.append("\t");
					csvWriter.append("TITLE");
					csvWriter.append("\t");
					csvWriter.append("AUTHOR(S)");
					csvWriter.append("\t");
					csvWriter.append("PUBLISHED");
					csvWriter.append("\t");
					csvWriter.append("URL");
					csvWriter.append("\n");
					csvWriter.close();
					System.out.println("Created new csv file called "+ name);
				} catch (IOException e) {
					System.out.println("File could not be created.");
					e.printStackTrace();
				}
			}else {
				try {
					temp = File.createTempFile(name, "tsv", DBfolder);
					temp.setReadable(true);
					temp.setWritable(true);
					temp.setExecutable(true);
					//initializing the tab separated values file with labels
					FileWriter csvWriter = new FileWriter(temp);
					csvWriter.append("SOURCE");
					csvWriter.append("\t");
					csvWriter.append("QUOTES");
					csvWriter.append("\n");
					csvWriter.close();
					System.out.println("Created new csv file called "+ temp.getName());
				} catch (IOException e) {
					System.out.println("File could not be created.");
					e.printStackTrace();
				}
			}
		}else {
			System.out.println("File with this name "+ name + "already exists." );
		}return temp;
	}

	public File chooseDBfile() {
		   File temp = null;
		   HashMap<File,Database> databases= Database.getDBlist();
		   if(!databases.isEmpty()){
			   System.out.println("Choose which database file this source belongs to:");
			   JFileChooser fc = new JFileChooser();
			   fc.setCurrentDirectory(new java.io.File(System.getProperty("user.home") + "/Downloads"));
			   fc.setDialogTitle("Choose DBfile");
			   FileNameExtensionFilter filter = new FileNameExtensionFilter(null,"tsv","csv");
			   fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			   fc.setFileFilter(filter);
			   int returnVal = fc.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					temp = fc.getSelectedFile();
					System.out.println("Chosen DBfile is: "+ temp.getPath());
				}else if(returnVal == JFileChooser.CANCEL_OPTION){
					System.out.println("No folder selected.");
				}
			}
			else{
				 System.out.println("No database exists. Create a new database by giving a name:");
				 Scanner sc = new Scanner(System.in);
				 Database db = new Database(sc.nextLine());
				 temp = db.getDBfile();
				 sc.close();
			}return temp;
	   }
	//adding a source to the DBfile
	public void addSourceToDB(Object o, File target) {
		FileWriter csvWriter;
		HashMap<String,String> specs = null;
		if(o instanceof Article || o instanceof eArticle) {
			if(o instanceof Article) {
				Article a = ((Article) o);
				specs = a.getSourceInformation();
				//a.setDBfile(target);
			}else if(o instanceof eArticle) {
				eArticle e = ((eArticle) o);
				specs = e.getSourceInformation();
				//e.setDBfile(target);
			}
			try {
				csvWriter = new FileWriter(target);
				String[] labels = readDBfile(target).get(0).split("\t");
				for(String item: specs.values()) {
					for(String label: labels) {
						if(specs.get(item) == label) {
							csvWriter.append(item);
							csvWriter.append("\t");
						}else {
							csvWriter.append("no value");
							csvWriter.append("\t");
						}
					}
				}csvWriter.append("\n");
				csvWriter.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			} 
		}else if(o instanceof Citation) {
			specs = ((Citation) o).getSourceInformation();
			try {
				csvWriter = new FileWriter(target);
				String[] labels = readDBfile(target).get(0).split("\t");
				for(String item: specs.values()) {
					for(String label: labels) {
						if(specs.get(item) == label) {
							csvWriter.append(item);
							csvWriter.append("\t");
						}else {
							csvWriter.append("no value");
							csvWriter.append("\t");
						}
					}
				}csvWriter.append("\n");
				csvWriter.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			} 
		}
	}
	
	public ArrayList<String> readDBfile(File f) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			BufferedReader csvReader = new BufferedReader(new FileReader(f));
			while((csvReader.readLine() != null)) {
				String temp = csvReader.readLine();
				data.add(temp);
			}
			csvReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	public void printDBfile(File f) {
		ArrayList <String> data = readDBfile(f);
		for(String item : data) {
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
		
}