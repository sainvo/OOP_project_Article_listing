//import java.util.HashMap;
import java.util.Scanner;

/*
 * @author Sanna Volanen
 * @version 1.0
 */
public class Book extends Source {
     /*
     * 
     * @param pages - no of pages in the book
     * @param edition - book's edition number
     * @ publisher - name of the publishing house or entity
     * @.pre true
     * @.post pages, edition > 0; published != ""
     */

     protected Integer pages;
     protected Integer edition = 0;
     protected String publisher;
     private String bookID;

     //CONSTRUCTOR
     public Book(SourceType t) {
          super(t);
          Scanner sc = new Scanner(System.in);
          System.out.println("Give publisher's name:");
          String pub = sc.nextLine();
          System.out.println("Give number of pages:");
          Integer pages = sc.nextInt();
          System.out.println("Give edition number:");
          Integer ed = sc.nextInt();
          sc.close();
          this.publisher = pub;
          this.edition = ed;
          this.pages = pages;
          //Double bookID = (double) super.getIdNumber();
          this.bookID = "B_" + Double.toString((double) super.getIdNumber());
          super.setSourceInformation("Book Id", bookID);
          super.setSourceInformation("Publisher", pub);
          super.setSourceInformation("Edition", edition);
          super.setSourceInformation("Pages", pages);
     }

     public String getBookId(){
          return bookID;
     }
}
