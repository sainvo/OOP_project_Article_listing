package com.sainvo.oop.harkka.java;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 * @author Sanna Volanen
 * @version 1.0
 */
 
public class Article extends Source{
	/*
	 * Abstract class that is inherited by all source types
	 * @param articleID - running number with affect on the integer part of the double
	 * @.pre true
	 * @.post articleID > 0
	 */
    protected double articleID;
    private SourceMedia sourceMedia;
    protected String title;
    protected String author;
    protected Integer year;
    HashMap <String,String> specs = new HashMap<String,String>();
	private File DBfile;

	
    public Article(String name, SourceMedia media){
    	super(name,media);
    }
    public Article(String name, String author, Integer published){
    	super(name);
    	this.articleID = (double) super.getIdNumber();
    	this.author = author;
    	this.year = published;
    	setSourceInformation();
    }
    public String getTitle() {
    	return title;
    }
    public void setDBfile(File f) {
    	this.DBfile = f;
    }
    private void setSourceInformation() {
    	this.specs = super.getSourceInformation();
		specs.put("ID", Double.toString(articleID));
		specs.put("TITLE", title);
		specs.put("AUTHOR(S)", author);
		specs.put("YEAR", Integer.toString(year));
	}
    public File getDBfile() {
    	return DBfile;
    }
    public double getArticleID() {
    	return articleID;
    }
    public SourceMedia getSourceMedia() {
    	return sourceMedia;
    }

    public HashMap<String, String> getSourceInformation(){
		return specs;
    }
}
