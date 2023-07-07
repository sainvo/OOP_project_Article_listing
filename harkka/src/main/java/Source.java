//package com.sainvo.oop.harkka.java;
/*
 * base class for all sources
 */

import java.util.ArrayList;
//import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
//import javafx.util.Pair;
//import java.io.IOException;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;

public class Source {
	/**
	 * Generic source
	 * 
	 */
	private Integer counter = 0;
	protected boolean cited = false;
	
	protected final Integer ID;
	public SourceType type;
	public final SourceMedia media; 
     protected HashMap <String,String> info = new HashMap<String,String>();
	protected HashMap <String,ArrayList<String>> articles = new HashMap<String,ArrayList<String>>();

	public Source() {
		this.ID = this.counter++;
		this.media = SourceMedia.PRINT;
	}
	public Source(SourceType t){
		this.ID = this.counter++;
		this.media = SourceMedia.PRINT;
		this.type = t;
	}

	public void setTitle() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Give source title:");
		String title = sc.nextLine();
		sc.close();
		setSourceInformation("title", title);
	}
	
	public void setAuthors() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Give source authors:");
		String authors = sc.nextLine();
		sc.close();
		setSourceInformation("authors", authors);
	}
	
	public void setYear() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Give year of publication:");
		String year = sc.nextLine();
		sc.close();
		setSourceInformation("year", year);
	}

	public void setMedia() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Is source media print or digital:");
		String media = sc.nextLine();
		sc.close();
		setSourceInformation("media", media.toUpperCase());
	}

	public void setType() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Choose source type: JOURNAL, BOOK:");
		String type = sc.nextLine();
		sc.close();
		switch (type.toUpperCase()) {
			case "JOURNAL":
				this.type = SourceType.JOURNAL;
			case "BOOK":
				this.type = SourceType.BOOK;
		}
		//setSourceInformation("type", type);
		/* 
		switch (type.toUpperCase()) {
			case "ARTICLE":
				Book newBook = this.new Book(sc, SourceType.ARTICLE);
				this = newBook;
		}*/
	}
	
	public void setCited(boolean value) {
		this.cited = value;
    }
	
	public void setSourceInformation(String name, Object o) {
		if (!info.containsKey(name)) {
			info.put(name, o.toString());
		} else {
			info.replace(name, info.get(name), o.toString());
		}
    };
    
    public Integer getIdNumber(){
        return ID;
    }
    public String getType(){
    		return type.toString();
    }

	public HashMap<String, String> getSourceInformation() {
		return info;
	}
	/* 

	public void addArticle(String ID){
		Article a = new Article();
		String A_info = "title :"+a.title+", authors: "+a.authors;
		if (articles.isEmpty()){
			articles.put(ID,new ArrayList<>(Arrays.asList(A_info)));
		}else if (articles.containsKey(ID)){
			ArrayList<String> old = articles.get(ID);
			ArrayList<String> newList = old.add(A_info);
			articles.put(ID,);
			}
	};
		

	public class Article {
		protected String title;
		protected String authors;
	
		public Article() {
			super();
			Scanner sc = new Scanner(System.in);
			System.out.println("Give source title:");
			//String title = sc.nextLine();
			this.title = sc.nextLine();
			System.out.println("Give author(s):");
			//String authors = sc.nextLine();
			this.authors = sc.nextLine();
			sc.close();
		}
	}
	*/

}
	