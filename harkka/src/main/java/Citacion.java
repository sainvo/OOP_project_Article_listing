import java.sql.*;
import java.util.ArrayList;

/** @Sanna Volanen  
 * class that creates a citacion 
 * EXCLUDE???
*/

public class Citacion  extends Article implements Listable{
    public String quote;
    public Article article;
    private int quoteId = 0;
    public Map <K,V> allQuotes = new Map <K,V>();

    //CONSTRUCTOR
    public Citacion(String quote, Article article){
        quote = this.quote;
        article = this.article;
        quoteId++;
    }
    //GETTERS
    public String getQuote(){
        return quote;
    }
    public Article getArticle(){
        return article;
    }
    public Map<K,V> getAllQuotes(){
        return allQuotes;
    }

    //SETTERS

    //METHODS
    public boolean addQuote(Citacion cit){
        allQuotes.add(cit);
    }
    // mitä verrataan???
    public boolean compareTo(Citacion cit){
        quoteId a = this.quoteId ;
        if ((a.equals(b.getQuoteId()))){
            return false;
        }else{
            return true;
        }
    }
}