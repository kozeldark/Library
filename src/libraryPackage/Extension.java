package libraryPackage;

import java.util.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/** Class : extend the rented the book in the library
 * 
 * function : check if the book is available to extend, modify the data in rent.txt file(rented date 2 weeks)
 * 
 * @author JHL
 *
 */
public class Extension {
	static String bookname;
	static String book_num = "";
	static String userId;
	static String rentFile = "rent.txt";
	static String bookFile;
	static String userFile;
	static int line_count;
	static String lib_name;
	static int errorMessage = 0;

	static String due_date;


	/* available() - check the book is rented, if the book is reserved or extended before, more extension is impossible */
	static boolean available() throws IOException {

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
		int found = 0; // if found = 1, not = 0
		line_count = 0; // for position in rent.txt file
		while (line != null) {
			line = inputrent.readLine();
			rent_splited = line.split("\t");
			
			// read a line and split by "\t" - [1]사용자 아이디,[2]책 번호,[4]반납예정일, [5]연장 유무
			// find the book -> check extension status
			if (rent_splited[1].equals(userId) && rent_splited[2].equals(book_num)) {
				lib_name = rent_splited[6];
				
				if (rent_splited[5].equals("0")) {
					//never extended - check if the book is reserved
					if (isReserved(rent_splited[0])) {
						errorMessage = 1;
						System.out.println("예약된 책이므로 연장할 수 없습니다.");
						return false; //reserved, not extension
					}
					else {
						System.out.println("연장이 가능합니다.");
						due_date = rent_splited[4];
						return true;
					}
				} else {
					//not extension
					errorMessage = 2;
					System.out.println("더 이상 연장할 수 없습니다.");
					return false;
				}
			}
			line_count++;
		}
		errorMessage = 3;
		System.out.println("해당 정보를 찾을 수 없습니다.");
		return false;
	}
	
	private static boolean isReserved(String rentNum) throws IOException {
		/* file open to compare a rent list */
		BufferedReader inputStream = null;
		try {
			inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(rentFile), "utf-8"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/* check book with book_num, lib_name -> the rent_number distributed whether the book is rented or reserved */
		String temp = "";
		String[] splited = null;
		while (temp != null) {
			temp = inputStream.readLine();
			if (temp == null) break; //EOF Exception
			splited = temp.split("\t");
			if (splited[2].equals(book_num) && splited[6].equals(lib_name) && !splited[0].equals(rentNum)) return true; //reserved
		}
		//didn't find the wanted sentence
		
		return false;
	}
}
