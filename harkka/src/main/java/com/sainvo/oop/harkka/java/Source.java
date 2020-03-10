package com.sainvo.oop.harkka.java;
/*
 * base class for all sources
 * param name - String identifying the source
 * @.pre true
 * @.post Id > 0.0, name != null
 */

import java.util.HashMap;
import java.util.Scanner;

public class Source{
    protected Integer Id = 0;
    protected String title;
    protected SourceMedia media;
    public HashMap <String,String> specs = new HashMap<String,String>();

    
    public Source(Article a) {
    	this.Id = (int)a.getArticleID();
    	this.title = a.getTitle();
    	this.media = a.getMedia();
    }
    public Source(String name) {
    	this.Id++;
    	this.title = name;
    	setMedia(SourceMedia.PRINT);
    	setSourceInformation();
    }
    public Source(String name, SourceMedia media) {
    	this.Id++;
    	this.title = name;
    	this.media =media;
    	setSourceInformation();
    }
    public void setTitle() {
    	Scanner sc = new Scanner(System.in);
    	System.out.println("Give source title:");
    	this.title = sc.nextLine();
    	sc.close();
    }
    public void setMedia(SourceMedia m) {
    	this.media = m;
    }
    private void setSourceInformation() {
		specs.put("ID", Integer.toString(Id));
		specs.put("TITLE", title);
		specs.put("MEDIA", media.toString());
	}
    public Integer getIdNumber(){
        return Id;
    }
    public String getName(){
    	return title;
    }
    public SourceMedia getMedia() {
    	return media;
    }
    public HashMap<String, String> getSourceInformation(){
    	return specs;
	} 
    
}