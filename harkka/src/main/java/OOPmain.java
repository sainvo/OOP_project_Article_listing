package com.sainvo.oop.harkka.java;

import java.io.File;
import java.util.Scanner;

/*
 * @author Sanna Volanen 
 * */

public class OOPmain{
    public static void main(String[] args){
        //create or start a database
    	Database articles = new Database("articles");
    	//System.out.println(articles);
        //create an article
    	//eArticle test0 = new eArticle(Database.chooseFileFromLocal().toString());
    	eArticle test1 = new eArticle("testiartikkeli","Tommi Tutkija", 2015, "https://arxiv.org/pdf/1912.12333.pdf");
    	System.out.println(test1.getSourceInformation());
    	//System.out.println(articles.getDBfile());
    	//articles.addSourceToDB(test0, articles.getDBfile());
    	//articles.printDBfile(articles.getDBfile());
    	//System.out.println("Source title is now "+ test1.getTitle()+ ".");
		//System.out.println("Give a new name if you want to reset this title:");
		//Scanner sc = new Scanner(System.in);
		//test1.setTitle(sc.nextLine());
		//sc.close();
        //add a quote
        //find quotes
        //list the articles/quotes
        //output the reference list (different formatting from above article listing)
        //delete and article??
    }
    
}