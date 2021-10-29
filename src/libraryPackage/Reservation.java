package libraryPackage;

import java.io.*;
import java.util.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
/** Class : reserve the rented book
 * 
 * function : check the rent is available - book_count++ in user.txt file, add data in rent.txt file
 * 
 * @author JHL
 *
 */
public class Reservation {
	static String bookname;
	static String book_num;
	static String userId; 
	static String rentFile = "rent.txt";
	static String bookFile; // change variable value with library() function
	static String userFile = "userEncoding.txt";
	static int line_count;
	
	static String[] library = { "논현도서관", "도곡정보문화도서관", "삼성도서관", "세곡도서관", "역삼도서관"};
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
		 * available() : exists book in library, check rented book number in user.txt ,check rent list in rent.txt 
		 */
		String[] userSplit;
		String temp = "";

		/* file open to search a book */
		BufferedReader inputBook = null;
		try {
			inputBook = new BufferedReader(new InputStreamReader(new FileInputStream(bookFile), "utf-8"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		/* file open to check the book number rented */
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

		String line = "", bookLine = ""; // read lines
		String[] rent_splited = null, book_splited = null;
		line_count = 0; // data counted in rent.txt list - order for write new data
		int found_r = 0, found_b = 0; // founded = 1, not founded = 0 : reservation is okay when found_r == 1
		/**
		 * first, check if the book is in the library book list
		 */
		do {
			bookLine = inputBook.readLine();
			if (bookLine == null) break;
			book_splited = bookLine.split("/");
			if (book_splited[0].equals(book_num)) {
				// exist book in library
				found_b = 1;
			}

		} while (bookLine != null);
		inputBook.close();
		if (found_b != 1) {
			// no books in library
			System.out.println("해당 책은 도서관에 구비되어 있지 않습니다.");
			return false;
		} 
		else {
			// proceed to the next verification process (whether they are on the rent list - if the user's rent is not more than 3 copies)

			do {
				line = inputrent.readLine(); // throws IOException
				if (line == null)
					break; // EOF Exception
				rent_splited = line.split("\t");
				if (rent_splited[2].equals(book_num) && rent_splited[6].equals(lib_name)) {
					// find the book
					due_date = rent_splited[4];
					// then compare
					found_r = 1;
				}
				line_count++;
			} while (line != null);

			inputrent.close(); // when there is a book in rent.txt list( found = 1)

			// System.out.println("found : " + found);
			if (found_r == 0) {
				// rent proceed
				System.out.println("현재 대여 가능한 책이므로 예약할 수 없습니다.");
				return false;

			} 
			else {
				// 예약하기
				do {
					temp = inputStream.readLine(); // userfile throws IOException
					if (temp == null)
						break; // EOF Exception

					userSplit = temp.split("\t");
					if (userSplit[0].equals(userId)) { //find user information
						int bookCount;
						bookCount = Integer.valueOf(userSplit[7]);

						/* false when user want to rent book over 3 books*/
						if (bookCount < 3)
							return true;
						else {
							System.out.println("대여 가능한 책 수를 초과했습니다. 관리자에게 문의하세요.");
							return false;
						}
					}

				} while (temp != null);
				inputStream.close();
				System.out.println("회원 정보에 등록되어 있지 않습니다. 관리자에게 문의하세요.");
				return false;

			}
		}

	}
}
