package libraryPackage;

import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
/** Class : return the rented book
 * 
 * function : book_count-- in user.txt file, delete data in rent.txt file
 * 
 * @author JHL
 *
 */

public class Return {
	static String bookname;
	static String book_num;
	static String userId; 
	static String rentFile = "rent.txt";
	static String bookFile;
	static String userFile = "user.txt";
	static int line_count;
	
	static void deleteLoan(int position) throws IOException, FileNotFoundException {
		File file = new File(rentFile);
		BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8")); // throws
		
		String tempLine;
		String dummy1 = "", dummy2 = "";
		
		// Move to position user want to delete and save to dummy
		for (int i = 0; i < position; i++) {
			tempLine = input.readLine(); //read a sentence
			dummy1 += (tempLine + "\n");
		}
		// Skip the data user want to delete
		input.readLine(); //position
		// Save in dummy after the position user want to delete
		
		String[] splited;
		String newLine = "";
		int num;
		while ((tempLine = input.readLine()) != null) {
			//tempLine number--
			splited = tempLine.split("\t");
			num = Integer.valueOf(splited[0]);
			num--;
			splited[0] = Integer.toString(num);
			newLine = "";
			/*Reconnect Broken Row (splited tempLine) */
			for (int i = 0; i < 6; i++) {
				newLine += (splited[i] + "\t");
			}
			newLine += (splited[6] + "\n");
			dummy2 += newLine;
		}
		input.close();
		
		PrintWriter output = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));// throws
		/* Before Modification*/
		output.print(dummy1);

		/* After Modification */
		output.print(dummy2);

		output.close();


		
	}


}
