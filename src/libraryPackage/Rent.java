package libraryPackage;

import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;

import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;

/** Class : rent the book in the library
 * 
 * function : check if the book is available to rent, book_count++ in user.txt file, add data in rent.txt file
 * 
 * @author JHL
 *
 */
public class Rent {
	static String bookname;
	static String book_num;
	static String userId;
	static String rentFile = "rent.txt";
	static String bookFile; //
	static String userFile = "user.txt";
	static int line_count;
	
	static int errorMenu = 0;
	static String lib_name;
	
	static String due_date;
	
	private static void library() {
		/* function : modify book file name(var: bookname) 
		 * before each class, modify books file name as library file name
		 */
		bookFile = lib_name + "books.txt";

	}
	static boolean available() throws IOException {
		/**
		 * available() :check the number of books user rented in user.txt, rent list in rent.txt file
		 */
		String[] userSplit;
		String temp = "";

		/* file open to check user list */
		BufferedReader inputStream = null;
		try {
			inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(userFile), "utf-8"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		/* file open to compare a rent list */
		BufferedReader inputrent = null;
		try {
			inputrent = new BufferedReader(new InputStreamReader(new FileInputStream(rentFile), "utf-8"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		String line = ""; // read lines
		String[] rent_splited = null;
		line_count = 0; //count the line in rent.txt file - order when writing new data
		int found = 0; // founded = 1, not = 0 
		/* if not founded, rent is available */
		do {
			line = inputrent.readLine(); // throws IOException
			if (line == null)
				break; // EOF Exception
			rent_splited = line.split("\t");
			
			if (rent_splited[2].equals(book_num) && rent_splited[6].equals(lib_name)) {
				// find the book then compare
				found = 1;
			}
			
			line_count++;
		} while (line != null);
		inputrent.close();

		if (found == 0) {
			// rent proceed
			System.out.println("현재 대여 가능한 책입니다.");
			do {
				temp = inputStream.readLine(); // userfile throws IOException
				if (temp == null)
					break; // EOF Exception

				userSplit = temp.split("/");
				if (userSplit[0].equals(userId)) { //find user information
					int bookCount;
					bookCount = Integer.valueOf(userSplit[7]);

					/* Exception if bookCount has 3 or more */
					if (bookCount < 3)
						return true;
					else {
						errorMenu = 1;
						System.out.println("대여 가능한 책 수를 초과했습니다. 관리자에게 문의하세요.");
						return false;
					}

				}
				
			} while (temp != null);
			inputStream.close();
			System.out.println("회원 정보에 등록되어 있지 않습니다. 관리자에게 문의하세요.");
			return false;

		} else {
			errorMenu = 2;
			System.out.println("이미 대여중인 책입니다.");
			return false;

		}
	}

}
