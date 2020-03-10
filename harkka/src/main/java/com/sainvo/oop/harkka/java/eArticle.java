package com.sainvo.oop.harkka.java;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class eArticle extends Source implements Downloadable{
	protected String sourceUrl;
	public File resourceFolder;
	protected SourceMedia media;
	//private Source root;
	protected int year;
	protected String author;
	private File DBfile;
	HashMap <String,String> specs = new HashMap<String,String>();
	private double articleID;
	
	public eArticle(String name, String author, Integer published, String url) {
		super(name);
		this.articleID = (double) super.getIdNumber();
		this.year = published;
		this.author = author;
		setMedia(SourceMedia.DIGITAL);
		if(hasValidURL(url)) {
			this.sourceUrl = url;
		}
		setSourceInformation();
	}
	
    public eArticle(Article a){
    	super(a);
    	Map<String,String> transfer = a.getSourceInformation();
    	this.title = transfer.get("TITLE");
    	this.year = Integer.parseInt(transfer.get("YEAR"));
    	this.author = transfer.get("AUTHOR(S)");
    }
    public eArticle(String pathName) {
    	super(pathName);
    }

    private void setSourceInformation() {
    	this.specs = super.getSourceInformation();
		specs.put("ID", Double.toString(articleID));
		specs.put("TITLE", title);
		specs.put("AUTHOR(S)", author);
		specs.put("YEAR", Integer.toString(year));
		specs.put("URL", sourceUrl.toString());
	}
    
    public void setSourceUrl() {
    	String url = "";
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("Give source URL: ");
			url = sc.nextLine();
			try {
				URL sourceUrl = new URL(url);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		while(!(hasValidURL(sourceUrl)));
		this.sourceUrl = url;
		sc.close();
    }
			
    public String getSourceUrl(){
        return sourceUrl;
    }
    public void setTitle(String name) {
    	title = name;
    }
    public void setDBfile(File f) {
    	this.DBfile = f;
    }
    public String getTitle() {
    	return title;
    }
    public File getDBfile() {
    	return DBfile;
    }
    @Override
	public HashMap<String, String> getSourceInformation() {
		return specs;
	} 
    
	@Override
	public boolean hasValidURL(String u) {
		boolean isValid = false;
		try {
			URL url = new URL(u);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			int code = con.getResponseCode();
	        if(code == 404) {
	        	System.out.println("Invalid URL");
	        }else if(code == 200) {
	        	System.out.println("URL is valid");
	        	isValid = true;
	        }
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}return isValid;
	}
	@Override
	public boolean hasResourceFolder() {
		boolean exists = false;
		if(DBfile != null) { // checking if the object has been saved to a DB 
			Map<File, Database> databases = Database.getDBlist();
			if(databases.containsKey(DBfile)) {
				Database temp = databases.get(DBfile); 
				if(temp.getResourceFolder().exists()) {
					System.out.println("Resource folder exists: "+resourceFolder.toPath() );
					exists = true;
				}
				else{
					System.out.println("There is no database or the DBfolder doesn't exist");
				}
			}
		}return exists;
	}

	/*
	@Override
	public boolean downloadAndSave() {
		boolean isSaved = false;
		File loaded = null;
		
		}else {
		//download and save the source origin as HTML
			try {
				Object o = sourceUrl.getContent();
				if(o instanceof HTMLDocument)
				if(!fileAlreadySaved(temp)) {
					loaded = temp;
					try {
						Files.move(loaded.toPath(), resourceFolder.toPath());
					} catch (IOException e) {
						e.printStackTrace();
					}
					System.out.println("Resource downloaded");
				}else {
					temp.delete();
				}
			} catch (IOException e) {
				System.out.println("Source couldn't be downloaded");
				e.printStackTrace();
			}
		}
		return loaded;
	}
	
	public boolean fileAlreadySaved(File f) {
		File[] downloads = resourceFolder.listFiles();
		for(File file : downloads) {
			if(file.equals(f)) {
				return true;
			}
		}
		return false;
	}
	*/
	public static eArticle changeToDigi(Article a) {
		eArticle temp = null;
    	if(a.getMedia() ==SourceMedia.DIGITAL){
    	temp = new eArticle(a);
    	}return temp;
    }
	
	//creating an eArticle from already downloaded file
    public eArticle createFromLocalFile(File f){
		String localPathString = f.getPath();
		String name = localPathString.substring(localPathString.lastIndexOf('/')+1,localPathString.length() );
		System.out.println(name);
		eArticle article = new eArticle(localPathString);
		String targetFilepath = resourceFolder.toString();
		System.out.println(targetFilepath);
		if(targetFilepath != null) {
			try {
				Files.copy(f.toPath(), Path.of(targetFilepath,"/",name));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("New resource created and file moved to resource folder.");
		return article;
    }

	public File getResourceFolder() {
		// TODO Auto-generated method stub
		return resourceFolder;
	}

}
