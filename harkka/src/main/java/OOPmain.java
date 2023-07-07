//package com.sainvo.oop.harkka.java;

//import java.io.File;
import java.util.Scanner;
import java.util.HashMap;
import java.io.StringWriter;
import java.io.PrintWriter;

/*
 * @author Sanna Volanen 
 * */

public class OOPmain {
	public static HashMap<String, Database> DBlist = new HashMap<String, Database>();

	public static void main(String[] args) {
		//Database current ;
		boolean isNew = false;
		String dbName = "";
		Integer prompt = -1;
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to Simple Database for Thesis Resources!");
		while (prompt < 0) {
			System.out.println(
					"Would you like to \n 1) create a new database,\n 2) amend an existing one or \n 3) exit?");
			String input = sc.nextLine();

			//sc.close();
			prompt = Integer.parseInt(input);

		}
		//Scanner sc_inner = new Scanner(System.in);
		//System.out.println(prompt);
		do {

			switch (prompt) {
				case 1:
					//Database temp;
					isNew = true;
					System.out.println("Give a name to your database:");
					dbName = sc.nextLine();
					sc.nextLine();
					break;
				//sc = new Scanner(System.in);

				//sc.close();
				//sc_inner.nextLine();

				//prompt = 1;
				case 2:
					System.out.println("Give the name of your database:");
					dbName = sc.nextLine();
					sc.nextLine();
					break;
					// need to check if exists
					// load from the doc

				//sc = new Scanner(System.in);
				//current = new Database(dbName);
				//UI(prompt, sc, current);
				//sc.close();
				//sc_inner.close();
				//Database db = new Database(existing);
				//prompt = 2;
				case 3:
					System.out.println("Exiting...");
					sc.close();
					System.exit(0);
			}
			
			try {
				Database current = new Database(dbName);
				if (current != null) {
					System.out.println("Database " + dbName + " created succesfully\n");
					addDBToList(dbName, current);
					//Scanner sc1 = new Scanner(System.in);
					UI(current, isNew);
				}
			} catch (NullPointerException npe) {
				System.out.println("Error in db creation.\n\n");
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				npe.printStackTrace(pw);
				System.out.print(sw.toString());
				sc.close();
				System.exit(1);
			} 
			/* 
			current = new Database(dbName);
			System.out.println("Database " + dbName + " created succesfully\n");
					addDBToList(dbName, current);
					//Scanner sc1 = new Scanner(System.in);
					UI(current, isNew);
			*/
		} while (prompt != 0);
		/* 
		sc.nextLine();
		sc.close();
		if (prompt != 3) {
			sc = new Scanner(System.in);
			current = new Database(dbName);
			UI(prompt, sc, current);
		}
		*/
		/* 
		//create or start a database
		Database articles = new Database("articles");
		//System.out.println(articles);
		//create an article
		//eArticle test0 = new eArticle(Database.chooseFileFromLocal().toString());
		eArticle test1 = new eArticle("testiartikkeli","Tommi Tutkija", 2015, "https://arxiv.org/pdf/1912.12333.pdf");
		System.out.println(test1.getSourceInformation());
		System.out.println(articles.getDBfile());
		articles.addSourceToDB(test1, articles.getDBfile());
		articles.printDBfile(articles.getDBfile());
		System.out.println("Source title is now "+ test1.getTitle()+ ".");
		System.out.println("Give a new name if you want to reset this title:");
		test1.setTitle(sc.nextLine());
		sc.close();
		//add a quote
		//find quotes
		//list the articles/quotes
		//output the reference list (different formatting from above article listing)
		//delete and article??
		*/

	}

	public static HashMap<String, Database> getDBlist() {
		return DBlist;
	}

	private static void addDBToList(String name, Database db) {
		DBlist.put(name, db);
		System.out.println(DBlist.keySet());
	}
	/**
	 * 
	 * @param db 
	 * @param isNew
	 * @return
	 */
	private static Integer UI(Database db, boolean isNew) {
		//Integer lastInput = prompt;
		Integer userInput = -1;
		Scanner scan = new Scanner(System.in);

		if (isNew) {
			// new BD, no entries yet
			System.out.println("Would you like to \n 0: exit \n 1: add a new source?");
		} else {
			System.out.println("Would you like to \n 0: exit \n 1: add a new source\n 2: edit an existing entry?");
		}
		userInput = scan.nextInt();
		scan.nextLine();
		//lastInput = userInput;
		
		switch (userInput) {

			case 1:
				Source newSource;
				System.out.println(
						"Give the type of the source media:\n Choices:\n" + SourceMedia.values().toString());
				String media = scan.nextLine();
				if (media.equals(SourceMedia.DIGITAL.toString())) {
					System.out.println("Give source url:");
					String url = scan.nextLine();
					newSource = new eSource(url);

				} else {
					newSource = new Source();
				}
				newSource.setType();
				if (newSource.getType().toString() == "BOOK") {
					newSource = new Book(SourceType.BOOK);
					newSource.setAuthors();
				} else {
					newSource = new Journal(SourceType.JOURNAL);
				}
				newSource.setTitle();
				newSource.setYear();

			case 2:
				System.out.println(
						"Editing source information is a future feature and not available at this time.");

				//System.out.println("Exiting...");

				//System.exit(0);
		}
		if (userInput == 0) {
			scan.close();
		}
		return userInput;
		
	};
}
