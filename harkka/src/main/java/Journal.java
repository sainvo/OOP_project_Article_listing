//package com.sainvo.oop.harkka.java;

//import java.io.File;
//import java.net.URL;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
import java.util.Scanner;

//import javax.swing.JFileChooser;
//import javax.swing.filechooser.FileNameExtensionFilter;

/*
 * @author Sanna Volanen
 * @version 1.0
 */
 
public class Journal extends Source{
	/*
	 * subclass of Source defining an article
	 * @param articleID - running with affect on the integer part of the double
	 * @.pre true
	 * @.post articleID > 0
	 */
    
	protected String name;
	protected Integer issue;
	protected Integer volume;
	protected String journalID;
	
	//CONSTRUCTORS

	public Journal(SourceType st) {
		super(st);
          Scanner sc = new Scanner(System.in);
          System.out.println("Give journal's name:");
          this.name = sc.nextLine();
          System.out.println("Give number of the volume:");
           this.volume = sc.nextInt();
          System.out.println("Give issue number:");
		Integer issue = sc.nextInt();
		sc.close();
          //Double bookID = (double) super.getIdNumber();
          this.journalID = "J_" + Double.toString((double) super.getIdNumber());
          super.setSourceInformation("Journal Id", journalID);
          super.setSourceInformation("Volume", volume);
		super.setSourceInformation("Issue", issue);
		//setSourceInformation();
	}
	/* 
	public Article(String title, String author, Integer published, SourceMedia media) {
		super(title, media);
		this.articleID = (double) super.getIdNumber();
		this.author = author;
		this.year = published;
		setSourceInformation();
	}
    	public Article(String title, String author, Integer published, SourceMedia media, String url){
		super(title,media, published);
		this.articleID = (double) super.getIdNumber();
		this.author = author;
		setSourceInformation();
    }
    */
    //SETTERS
	
/* 
	public void setSourceInformation() {
		specs.put("ID", Double.toString(articleID));
		specs.put("TITLE", super.getTitle());
		specs.put("AUTHOR(S)", author);
		specs.put("YEAR", Integer.toString(super.getYear()));
	}
	*/
	public void setJournal(String name) {
		this.name = name;
	}
	//GETTERS
    	public String getID() {
		return this.journalID;
    }
}
