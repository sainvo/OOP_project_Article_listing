package com.sainvo.oop.harkka.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/** @Sanna Volanen  
 * class that creates a citacion 
 * 
*/

public class Citation{
    public String quote;
    public Article root;
    private int quoteNumber = 0;
    //combining articleId and quoteNumber into a quoteID with representing integer part and decimal part, respectively.
    public final double quoteID; 
    private ArrayList<Citation> quotes = new ArrayList<Citation>();

    //CONSTRUCTOR
    public Citation(String quote, Article s){
    	this.root = s;
    	double tempId = s.getArticleID(); //the integer part
        if(addQuote()) {
        	quoteNumber++;
        	System.out.println("New quote added.");
        	tempId +=(double) (quoteNumber/10); // the decimal part
        }else {
        	System.out.println("Quote exists");
        }
        this.quote = quote;
        this.quoteID = tempId;
    }
   
    //GETTERS
    public String getQuote(){
        return quote;
    }
    public Article getSource(){
        return root;
    }
    public double getquoteID() {
    	return quoteID;
    }
    public ArrayList<Citation> getAllQuotes(){
        return quotes;
    }

    //METHODS
    public HashMap<String, String> getSourceInformation(){
    	HashMap <String,String> specs = new HashMap<String,String>();
		specs.put("SOURCE", Double.toString(root.getArticleID()));
		specs.put("ID", Double.toString(quoteID));
		specs.put("QUOTE", quote);
		return specs;
    }
    
    public boolean addQuote(){
    	boolean success = true;
    	if(!quotes.contains(this)) {
    		quotes.add(this);
    	}else {
    		success = false;
    	}
    	return success;
    }
    
    public boolean compareTo(Citation cit){
        if ((this.equals(cit))){
            return false;
        }else{
            return true;
        }
    }
}