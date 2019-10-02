import java.util.Scanner;
import java.io.File;

public class Article extends Source implements Downloadable{
    private final Integer sourceId;
    private final String TYPE = "ARTICLE";
    private final String title;
    private final String author;
    private final Integer year;
    private String filePath;
    private String sourceUrl; 
    
//CONSTRUCTORS
    /* default constructor: only the ID number is set*/   
    public Article(){
        super(); 
        System.out.println("New, empty source has been created.");
        Scanner sc = new Scanner(System.in);
        setAuthor();
        setTitle();
        setSourceUrl();
        setFilePath();
        sc.close();
    }

    public Article(String title, String author){
        super();
        title = this.title;
        author = this.author;
        System.out.println("New article object has been created."+"\n"+
        "Set year of publication, source url and the file path.");
    }
    public Article(String url){
        super();
        sourceUrl = url;
    }

    //GETTERS
    public String getAuthor(){
        return author;
    }
    public String getTitle(){
        return title;
    }
    public int getPublicationYear(){
        return year;
    }
    public String getSourceUrl(){
        return sourceUrl;
    }
    public String getType(){
        return TYPE;
    }
    public String getFilePath(){
        return filePath;
    }
    public int getIdNumber(){
        return articleId;
    }
    //SETTERS
    
    /** Needed only if more types than one
     *  public void setType(){
       * System.out.println("Give source type. Valid option is ARTICLE(, BOOK, EBOOK, AUDIO, VIDEO)");
       Scanner sc = new Scanner(System.in);
       type = sc.nextLine();
       sc.close();
   }
   */
    public void setTitle(){
        System.out.println("Give the name of the article.");
        title = sc.nextLine();
        sc.close();
    }
    public void setAuthor(){
        System.out.println("Give author(s) of the article, f.ex Austin, J.");
        Scanner sc = new Scanner(System.in);
        sourceUrl = sc.nextLine();
        sc.close();
    }
    public void setYear(){

    }
    public void setSourceUrl(){
        System.out.println("Give source URL");
        Scanner sc = new Scanner(System.in);
        sourceUrl = sc.nextLine();
        sc.close();
    }
    public void setFilePath(){
        if(filePath == null){
            filePath = "C:\\Users\\OWNER\\Desktop\\OPINNOT\\OOPharkka\\Articles\\"+ this.title; 
        }
    }
    //METHODS
    //Adding file to the Articles folder based on url
    /* used to save directly when article located online and URL available
    /*downloads the article based on URL and creates the filepath

    //Creating an Article from saved file in Articles folder
    /* used when article already downloaded and no URL is saved or readily available*/

    /** tarpeeton? 
    Db-luokassa hakumetodit*/
    public toString showSourceInformation(){
        System.out.println("ID: " + getIdNumber() + ", Title: " +getTitle() + ", Author: " + 
        getAuthor() + ", Year: "+ getPublicationYear());
    }
}