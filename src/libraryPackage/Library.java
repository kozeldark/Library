package libraryPackage;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import libraryPackage.FloydWarshall.liblocation;
import libraryPackage.FloydWarshall.userlocation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Library extends JFrame {

	private JPanel contentPane;
	private JTextField IDText;
	static JTextArea textPane;
	private JPasswordField PswText;
	private DefaultTableModel model;
	private DefaultTableModel rentModel;
	private JTable table;
	private static JTextField userNumTxt;
	private static JTextField userNameTxt;
	private static JTextField extensionTxt;
	private static JTextField birthTxt;
	private static JTextField phoneNumTxt;
	private static JTextField addressTxt;
	public static String[] info;
	private JTextField searchTxt;
	User user;
	private String bookFile;
	private String rentFile;
	private JTable rentTable;
	private JTextField book1;
	private JTextField book2;
	private JTextField book3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				try {
					Library frame = new Library();
					frame.setVisible(true);
					
		
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private int option() {
	  	 return JOptionPane.showConfirmDialog(
	  	            contentPane, 
	  	            "?????? ????????? ????????? ????????????.\n?????????????????????????", 
	  	            "Confirm", 
	  	            JOptionPane.YES_NO_OPTION 
	  	        );
	  }
	
	
	private String getBook() {
        return JOptionPane.showInputDialog(
            contentPane, // ????????? ????????? frame??? ????????????
            "??? ????????? ????????????:", // ????????? ?????? ?????????
            "Library", // ????????? ???
            JOptionPane.PLAIN_MESSAGE // ????????? ?????? ??????
        );
    }

	/**
	 * Create the frame.
	 */
	public Library() {
		setTitle("?????? ?????? ???, ??? ???"); //program name
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		JPanel menuPanel = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		/*///////////////////////////////////////////////////////////////////
		 * ???????????? ?????? ???-------------------------------------------------------
		////////////////////////////////////////////////////////////////// */
		
		JPanel distancePanel = new JPanel();
		distancePanel.setBounds(0, 0, 584, 361);
		contentPane.add(distancePanel);
		distancePanel.setLayout(null);
		distancePanel.setVisible(false);
		
		
		JButton prvDistance = new JButton("??????");
		prvDistance.setBounds(459, 21, 87, 31);
		distancePanel.add(prvDistance);
		prvDistance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				distancePanel.setVisible(false);
				menuPanel.setVisible(true);
			}
		});
		
		
		textPane = new JTextArea();
		textPane.setBounds(39, 74, 507, 222);
		distancePanel.add(textPane);
		
		JScrollPane scrollPane2 = new JScrollPane(textPane);
		scrollPane2.setBounds(39, 74, 507, 222);
		distancePanel.add(scrollPane2);
		
		// ??? ????????? ??????????????? ?????? ???????????? --------------------------------------
		JButton map = new JButton("????????? ???");
		map.setBounds(39, 23, 114, 31);
		distancePanel.add(map);
		map.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new mapMain();
				
			}
		});
		
		// ??? ????????? ?????? ??? ????????? ???????????? -----------------------------------------
		JButton list = new JButton("??? ??????");
		list.setBounds(171, 23, 114, 31);
		distancePanel.add(list);
		list.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new tableMain();
				
			}
		});
		
		book1 = new JTextField();
		book1.setBounds(39, 319, 116, 21);
		distancePanel.add(book1);
		book1.setColumns(10);
		
		book2 = new JTextField();
		book2.setBounds(171, 319, 116, 21);
		distancePanel.add(book2);
		book2.setColumns(10);
		
		book3 = new JTextField();
		book3.setBounds(304, 319, 116, 21);
		distancePanel.add(book3);
		book3.setColumns(10);
		
		
		// ???????????? (floydWarshall) ?????? ?????? ---------------------------------------------
		JButton searchDistance = new JButton("????????????");
		searchDistance.setBounds(449, 318, 97, 23);
		distancePanel.add(searchDistance);
		searchDistance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textPane.setText("");
				FloydWarshall.bookName1 = book1.getText();
				FloydWarshall.bookName2 = book2.getText();
				FloydWarshall.bookName3 = book3.getText();
				
				book1.setText("");
				book2.setText("");
				book3.setText("");
				
				try {
					userlocation userxy = new userlocation();
					liblocation libxy = new liblocation();
					
					BufferedReader inputStream = null;
					try {
						inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(FloydWarshall.libFile), "utf-8"));
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String line = inputStream.readLine();
					String[] arr = null;
					
					int i=0;
					while ((line = inputStream.readLine())!= null) 
					{
						arr = line.split(",");
						FloydWarshall.libname[i] = arr[0];	
						libxy.x[i] = Double.parseDouble(arr[1]);
						libxy.y[i] = Double.parseDouble(arr[2]);
						i++;
					}
					
					try {
						inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(FloydWarshall.userFile), "utf-8"));
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					//Rent rentclass = new Rent();
					int userid = Integer.valueOf(user.no);
					
					line = inputStream.readLine();
					String[] userarr = null;
					String userlo = null;
					String[] userlo2 = null;
					while ((line = inputStream.readLine())!= null) 
					{
						userarr = line.split("/");
						if(Integer.parseInt(userarr[0]) == userid)
						{	
							userlo = userarr[8];
						}
					}
					userlo2 = userlo.split(",");
					userxy.x = Double.parseDouble(userlo2[0]);
					userxy.y = Double.parseDouble(userlo2[1]);
					
					for(int j=0; j<libxy.x.length; j++)
					{
						FloydWarshall.distance[j] = Math.sqrt((Math.pow((userxy.x) - (libxy.x[j]),2) + (Math.pow((userxy.y) - (libxy.y[j]),2))));
					}

					
					FloydWarshall f = new FloydWarshall();
					
					f.floydalgorithm();
						
					
					
				/*	while(click.close)//?????? ????????? ???????????????
					{
						Scanner sc = new Scanner(System.in);
						
						if(i ==0)
							rentbook.set(0, ~~ );
						else
							rentbook.add(~~ );
							rentval.add(0);
								
						//??? ?????? ??? ???????????? ??????
						//????????? ????????? ??????
					}
				*/

					f.m5(0);
					f.print();
					
					
				}catch (IOException e1) {
					
				}
			}
		});
		
		
		
		
		/*///////////////////////////////////////////////////////////////////
		 * ??????/?????? ?????? ???-------------------------------------------------------
		////////////////////////////////////////////////////////////////// */	
		
		JPanel extensionPanel = new JPanel();
		extensionPanel.setBounds(0, 0, 584, 361);
		contentPane.add(extensionPanel);
		extensionPanel.setLayout(null);
		extensionPanel.setVisible(false);
		
		String rentHeader[] = {"?????? NO.", "?????? NO.", "????????????", "????????????"};
		String rentContents[][] = {};
		rentModel = new DefaultTableModel(rentContents, rentHeader) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		
		rentTable = new JTable(rentModel);
		rentTable.getTableHeader().setReorderingAllowed(false);
		extensionPanel.add(rentTable);
		
		JScrollPane scrollPane1 = new JScrollPane(rentTable);
		scrollPane1.setBounds(44, 71, 501, 130);
		extensionPanel.add(scrollPane1);
		
		JLabel myExtension = new JLabel("??? ?????? ??????");
		myExtension.setBounds(44, 46, 85, 15);
		extensionPanel.add(myExtension);
		
		JButton prvExtension = new JButton("??????");
		prvExtension.setBounds(459, 20, 86, 29);
		extensionPanel.add(prvExtension);
		prvExtension.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				extensionPanel.setVisible(false);
				menuPanel.setVisible(true);
			}
		});
		
		
		// ??? ?????? ?????? ??????---------------------------------------------
		JButton extensionButton = new JButton("??????");
		extensionButton.setBounds(113, 261, 97, 55);
		extensionPanel.add(extensionButton);
		extensionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
				Extension.book_num = getBook();
				Extension.userId = user.no;
				
				while(true) {
					if(Extension.book_num != "")
						break;
				}
				
				try {
					if (Extension.available() == true) {
						// Extension -due date(+14days), modify extension value (0, 1) in rent.txt

						/* Calculate return due date on extension */

						SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd"); // ?????? ?????? ??????
						Date date = formatter.parse(Extension.due_date);
						Calendar cal = Calendar.getInstance();
						cal.setTime(date); // ?????? ???????????????

						// cal.setTime(new Date());
						System.out.println("current : " + formatter.format(cal.getTime()));

						cal.add(Calendar.DATE, 14); // add 2 weeks
						System.out.println("after : " + formatter.format(cal.getTime()));
						String rTime = formatter.format(cal.getTime());
						Extension.due_date = rTime; // return date
						
						
						
						/* Modify rent.txt */
						File file = new File(rentFile);
						BufferedReader inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8")); // throws

						String tempLine;
						int position = Extension.line_count; // transformation into Integer
						String dummy1 = "", dummy2 = "";

						// Move to position user want to modify and save to dummy
						for (int i = 0; i < position; i++) {
							tempLine = inputStream.readLine(); // ???????????? ??????
							dummy1 += (tempLine + "\n");
						}
						//Rewrite a string to save to the rent.txt
						String modify = inputStream.readLine();
						String modified = ""; //????????? ??? ??????
						String[] newLine = modify.split("\t"); //\t???????????? ?????????
						newLine[4] = Extension.due_date;
						newLine[5] = "1";
						
						for (int i = 0; i < 6; i++) {
							modified += (newLine[i] + "\t");
						}
						modified += newLine[6];
						// Save in dummy after the position user want to modify
						while ((tempLine = inputStream.readLine()) != null) {
							dummy2 += (tempLine + "\n");
						}
						
						inputStream.close();

						
						PrintWriter outputStream = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));// throws
						/* Before Modification */
						outputStream.print(dummy1);

						/* Modification */
						outputStream.print(modified + "\n");

						/* After Modification */
						outputStream.print(dummy2);

						outputStream.close();
						
						rentModel.setNumRows(0);
						BufferedReader bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
						String line = "";
						while((line = bufReader.readLine()) != null) {
							String[] array = line.split("\t");
							String result = array[1] + "\t" + array[2] + "\t" + array[3] + "\t" + array[4];
							array = result.split("\t");
							if(user.no.equals(array[0]))
							rentModel.addRow(array);
							
						}
						JOptionPane.showMessageDialog(extensionPanel, "??? ?????? ????????? 2??? ?????????????????????.");
						bufReader.close();

					} else {
						if(Extension.errorMessage == 1) {
							JOptionPane.showMessageDialog(extensionPanel, "????????? ???????????? ????????? ??? ????????????.");
						}
						
						else if (Extension.errorMessage == 2) {
							JOptionPane.showMessageDialog(extensionPanel, "??? ?????? ????????? ??? ????????????.");
						}
						
						else if (Extension.errorMessage == 3) {
							JOptionPane.showMessageDialog(extensionPanel, "?????? ????????? ?????? ??? ????????????.");
						}
					}
				}catch (FileNotFoundException e1) {
					
				}catch (IOException e1) {
					System.out.println(e1);
				}catch (ParseException e1) {
					e1.printStackTrace();
				}
				
				
				
			}
		});
		
		// ??? ?????? ?????? ------------------------------------------------------------------------------
		// ????????? ??????????????? ?????? ?????? ???????????? ???????????? ?????? ?????? ????????? ??????
		JButton returnButton = new JButton("??????");
		returnButton.setBounds(380, 261, 97, 55);
		extensionPanel.add(returnButton);
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Return.book_num = getBook();
				Return.userId = user.no;
				String userFile = "user.txt";
				
				while(true) {
					if(Return.book_num != "")
						break;
				}
				
				try {
					
					BufferedReader inputRent = null;
					try {
						inputRent = new BufferedReader(new InputStreamReader(new FileInputStream(rentFile), "utf-8"));
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String line = ""; 
					String[] rent_splited = null;
					Return.line_count = 0; 
					
					int found = 0;
					do {
						line = inputRent.readLine(); // throws IOException
						if (line == null)
							break; 
						rent_splited = line.split("\t");
						if (rent_splited[2].equals(Return.book_num) && rent_splited[1].equals(Return.userId)/*&&rent_splited[6].equals(Return.lib_num)*/) {
							
							found = Return.line_count; 
						}
						Return.line_count++;
					} while (line != null);

					inputRent.close();
					
					if (found != 0) {
						
						Return.deleteLoan(found);
						
						/* 2. file open to update user list */
						BufferedReader inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(userFile), "utf-8"));
						String[] userSplit;
						String temp = "";
						String update = "";
						do {
							temp = inputStream.readLine(); // throws IOException
							if (temp == null)
								break; 

							userSplit = temp.split("/");
							if (userSplit[0].equals(Return.userId)) {
								/*
								 * for checking System.out.println(temp);
								 */
								int bookCount;
								bookCount = Integer.valueOf(userSplit[7]);
								/* bookCount-- */
								int bookNo = Integer.valueOf(user.extension);
								user.extension = String.valueOf(bookNo - 1);
								bookCount--;
								userSplit[7] = Integer.toString(bookCount);
								
								for (int i = 0; i < 8; i++) {
									update += userSplit[i];
									update += "/";
								}
								update += userSplit[8];
							}
						} while (temp != null);
						
						///////////////////////////////////////////////////////////////////
						/*Rewriting file */
						File file = new File(userFile);
						BufferedReader userInput = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8")); // throws

						String tempLine;
						int position = Integer.valueOf(Return.userId); //transformation into Integer
						String dummy1 = "", dummy2 = "";

						// Move to position user want to delete and save to dummy
						for (int i = 0; i < position; i++) {
							tempLine = userInput.readLine();
							dummy1 += (tempLine + "\n");
						}
						
						userInput.readLine();// Skip the data user want to delete
						while ((tempLine = userInput.readLine()) != null) {
							dummy2 += (tempLine + "\n");
						}
						// Save in dummy after the position user want to delete
						userInput.close();

						PrintWriter userOutput = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));// throws
						/* Before Modification*/
						userOutput.print(dummy1);

						/* Modification */
						userOutput.print(update + "\n");

						/* After Modification */
						userOutput.print(dummy2);

						userOutput.close();
						
						rentModel.setNumRows(0);
						BufferedReader bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(rentFile), "utf-8"));
						String line2 = "";
						while((line2 = bufReader.readLine()) != null) {
							String[] array = line2.split("\t");
							String result = array[1] + "\t" + array[2] + "\t" + array[3] + "\t" + array[4];
							array = result.split("\t");
							if(user.no.equals(array[0]))
							rentModel.addRow(array);
							
						}
						
						JOptionPane.showMessageDialog(extensionPanel, "?????? ?????? ???????????????.");
						
					}
					
				} catch(IOException e1) {
					
				}
			}
		});
		
		
		
		
		
		
		/*///////////////////////////////////////////////////////////////////
		 * ??? ????????? ?????? ???---------------------------------------------------------
		////////////////////////////////////////////////////////////////// */	
		
		//???????????? ????????? ????????? ????????? ???????????? ????????? ???????????? ??? ?????? ??????
		JPanel myPagePanel = new JPanel();
		myPagePanel.setBounds(0, 0, 584, 361);
		contentPane.add(myPagePanel);
		myPagePanel.setLayout(null);
		myPagePanel.setVisible(false);
		
		JLabel information = new JLabel("?????? ??????");
		information.setBounds(62, 31, 57, 15);
		myPagePanel.add(information);
		
		JPanel design = new JPanel();
		design.setBorder(new LineBorder(new Color(0, 153, 255)));
		design.setBounds(53, 56, 482, 223);
		myPagePanel.add(design);
		design.setLayout(null);
		
		JLabel userNum = new JLabel("????????????");
		userNum.setBounds(12, 10, 57, 15);
		design.add(userNum);
		
		userNumTxt = new JTextField();
		userNumTxt.setBounds(68, 7, 36, 21);
		design.add(userNumTxt);
		userNumTxt.setColumns(10);
		
		JLabel userName = new JLabel("??????");
		userName.setBounds(116, 10, 57, 15);
		design.add(userName);
		
		userNameTxt = new JTextField();
		userNameTxt.setBounds(167, 7, 116, 21);
		design.add(userNameTxt);
		userNameTxt.setColumns(10);
		
		JLabel userExtension = new JLabel("????????????");
		userExtension.setBounds(320, 10, 57, 15);
		design.add(userExtension);
		
		extensionTxt = new JTextField();
		extensionTxt.setBounds(383, 7, 57, 21);
		design.add(extensionTxt);
		extensionTxt.setColumns(10);
		
		JLabel birth = new JLabel("????????????");
		birth.setBounds(245, 58, 57, 15);
		design.add(birth);
		
		birthTxt = new JTextField();
		birthTxt.setBounds(315, 55, 125, 21);
		design.add(birthTxt);
		birthTxt.setColumns(10);
		
		JLabel phoneNum = new JLabel("????????????");
		phoneNum.setBounds(12, 58, 57, 15);
		design.add(phoneNum);
		
		phoneNumTxt = new JTextField();
		phoneNumTxt.setBounds(81, 55, 125, 21);
		design.add(phoneNumTxt);
		phoneNumTxt.setColumns(10);
		
		JLabel address = new JLabel("??????");
		address.setBounds(12, 114, 57, 15);
		design.add(address);
		
		addressTxt = new JTextField();
		addressTxt.setBounds(54, 111, 171, 21);
		design.add(addressTxt);
		addressTxt.setColumns(10);
		
		JButton prevMyPage = new JButton("??????");
		prevMyPage.setBounds(452, 10, 83, 23);
		myPagePanel.add(prevMyPage);
		prevMyPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myPagePanel.setVisible(false);
				menuPanel.setVisible(true);
			}
		});
		
		
		
		
		/*///////////////////////////////////////////////////////////////////
		 * ?????? ?????? ???---------------------------------------------------------
		////////////////////////////////////////////////////////////////// */
		JPanel searchPanel = new JPanel();
		searchPanel.setBounds(0, 0, 584, 361);
		contentPane.add(searchPanel);
		searchPanel.setLayout(null);
		
		String header[] = {"NO.", "?????????", "?????????", "?????????", "?????????"};
		String contents[][] = {};
		
		//????????? ?????? ?????? ?????????
		model = new DefaultTableModel(contents, header) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		
		// ???????????? ????????? ???????????? ???, ?????? ???????????? ?????? ?????? ---------------------------------------------------------------
		class MyMouseListener extends MouseAdapter {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					/* variable (??????????????? ???????????? ???) */
					Rent.lib_name = bookFile.replace("books.txt", "");
					String userFile = "user.txt";
					String rentFile = "rent.txt";
					
					int row = table.getSelectedRow();
					String userId = user.no;
					Rent.userId = user.no;
					Rent.bookname = (String) table.getValueAt(row, 1);
					Rent.book_num = (String) table.getValueAt(row, 0); 
					
					
					/* file open to search a book */
					try {
						BufferedReader inputStream = null;
						String[] userSplit;
						String temp = "";
						String update = "";
						try {
							inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(userFile), "utf-8"));
						} catch (FileNotFoundException e1) {
							
						}
						/* available == true : rent book */
						if (Rent.available() == true) {
							do {
								temp = inputStream.readLine(); // throws IOException
								if (temp == null)
									break; //EOF Exception

								userSplit = temp.split("/");
								if (userSplit[0].equals(userId)) {
									/*
									 * for checking System.out.println(temp);
									 */
									int bookCount;
									bookCount = Integer.valueOf(userSplit[7]);
									int result = Integer.valueOf(user.extension);
									user.extension = String.valueOf(result + 1);

									/* bookCount++, exception when rented book_num over 3 */

									bookCount++;
									/* file open to write renting information - update information */

									PrintWriter outputStream = null;
									try {
										outputStream = new PrintWriter(new FileOutputStream(rentFile, true));
									} catch (FileNotFoundException e1) {
										System.out.println("Error opening the file " + rentFile);
										e1.printStackTrace();
									}

									String newInfo = null;
									String date_rent, date_return, extension;
									String line = Integer.toString(Rent.line_count);

									Calendar cal = Calendar.getInstance(); // Calendar instance
									SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd"); //date formatting

									cal.setTime(new Date());
									System.out.println("current : " + formatter.format(cal.getTime()));
									String dTime = formatter.format(cal.getTime()); //transformation into String
									date_rent = dTime; // save date

									cal.add(Calendar.DATE, 14); //add 2weeks
									System.out.println("after : " + formatter.format(cal.getTime()));
									String rTime = formatter.format(cal.getTime());
									date_return = rTime; // Save scheduled return date

									extension = "0";
									newInfo = line + "\t" + userId + "\t" + Rent.book_num + "\t" + date_rent + "\t" + date_return
											+ "\t" + extension + "\t" + Rent.lib_name;
									/* for checking */
									System.out.println(newInfo);

									outputStream.println(newInfo);

									outputStream.close();

									userSplit[7] = Integer.toString(bookCount);
									/*Reconnect Broken Row (splited userSplit) */
									for (int i = 0; i < 8; i++) {
										update += userSplit[i];
										update += "/";
									}
									update += userSplit[8];
								}

							} while (temp != null);
							/* for checking */
							System.out.println("update : " + update);
							inputStream.close();
							///////////////////////////////////////////////////////////////////
							/* file open to update user list */
							File file = new File(userFile);
							BufferedReader userInput = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8")); // throws

							String tempLine;
							int position = Integer.valueOf(userId);//formatter Integer
							String dummy1 = "", dummy2 = "";

							// Move to position user want to modify and save to dummy
							for (int i = 0; i < position; i++) {
								tempLine = userInput.readLine();
								dummy1 += (tempLine + "\n");
							}
							// Skip the data user want to modify
							userInput.readLine();
							while ((tempLine = userInput.readLine()) != null) {
								dummy2 += (tempLine + "\n");
							}
							// Save in dummy after the position user want to modify
							userInput.close();

							PrintWriter userOutput = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));// throws
							/* Before Modification*/
							userOutput.print(dummy1);

							/* Modification*/
							userOutput.print(update + "\n");

							/* After Modification*/
							userOutput.print(dummy2);

							userOutput.close();
							JOptionPane.showMessageDialog(searchPanel, "?????? ??????????????? ?????? ???????????????.");
					}
						
					// ????????? ????????? ??? ??? ------------------------------------------------------------------------
					else {
						// ??? ???????????? ??????
						if(Rent.errorMenu == 1) {
							JOptionPane.showMessageDialog(searchPanel, "?????? ????????? ??? ?????? ??????????????????. ??????????????? ???????????????.");
						}	
						
						// ?????? ?????? ????????? ???????????? ??????
						else if(Rent.errorMenu == 2) {
							int reserve = option(); //????????? ?????? ????????? ?????????.
							
							if(reserve == JOptionPane.CLOSED_OPTION) {
								
							}
							
							else if(reserve == JOptionPane.YES_OPTION) {
								Reservation.bookFile = bookFile;				
								Reservation.lib_name = bookFile.replace("books.txt", "");
								Reservation.userId = user.no;
								Reservation.bookname = (String) table.getValueAt(row, 1);
								Reservation.book_num = (String) table.getValueAt(row, 0); 
								
								if (Reservation.available() == true) {
									/* file open to read user list - modify the rented book count - index[5] */
									temp = "";
									/* file open to search a book */
									inputStream = null;
									update = "";
									try {
										inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(userFile), "utf-8"));
									} catch (FileNotFoundException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									do {
										temp = inputStream.readLine(); // throws IOException
										if (temp == null)
											break; // EOF Exception

										userSplit = temp.split("/");
										if (userSplit[0].equals(userId)) {
											/*
											 * for checking System.out.println(temp);
											 */
											int bookCount;
											bookCount = Integer.valueOf(userSplit[7]);

											bookCount++;
											userSplit[7] = Integer.toString(bookCount);
											/* file open to write renting information - update information */

											PrintWriter outputStream = null;
											try {
												outputStream = new PrintWriter(new FileOutputStream(rentFile, true));
											} catch (FileNotFoundException e1) {
												System.out.println("Error opening the file " + rentFile);
												e1.printStackTrace();
											}

											String newInfo = null;
											String date_rent, date_return, extension;
											String line = Integer.toString(Reservation.line_count);

											SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd"); //date formatting
											Calendar cal = Calendar.getInstance(); // Calendar instance
											
											//due_date : Find and enter the earliest date of the rent
											Date date = formatter.parse(Reservation.due_date);
											cal.setTime(date); //Existing Return Date of rented book
									
											System.out.println("current : " + formatter.format(cal.getTime()));
											//String dTime = formatter.format(cal.getTime()); //transformation of String
											// ################????????????#############
											date_rent = Reservation.due_date; // Save date and time of loan (with earliest scheduled time)

											cal.add(Calendar.DATE, 14); //calculate 2 weeks
											System.out.println("after : " + formatter.format(cal.getTime()));
											String rTime = formatter.format(cal.getTime());
											date_return = rTime; // Save scheduled return date

											extension = "0";
											newInfo = line + "\t" + userId + "\t" + Reservation.book_num + "\t" + date_rent + "\t" + date_return + "\t"
													+ extension + "\t" + Reservation.lib_name;
											/* for checking */
											System.out.println(newInfo);

											outputStream.println(newInfo);

											outputStream.close();

											
											/*Reconnect Broken Row (splited userSplit) */
											for (int i = 0; i < 8; i++) {
												update += userSplit[i];
												update += "/";
											}
											update += userSplit[8];
										}

									} while (temp != null);
									/* for checking */
									System.out.println("update : " + update);
									inputStream.close();
									///////////////////////////////////////////////////////////////////
									/* file open to update user list */
									File file = new File(userFile);
									BufferedReader userInput = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8")); // throws

									String tempLine;
									int position = Integer.valueOf(userId); //transformation into integer
									String dummy1 = "", dummy2 = "";

									// Move to position user want to modify and save to dummy
									for (int i = 0; i < position; i++) {
										tempLine = userInput.readLine(); 
										dummy1 += (tempLine + "\n");
									}
									// Skip the data user want to modify
									userInput.readLine();
									while ((tempLine = userInput.readLine()) != null) {
										dummy2 += (tempLine + "\n");
									}
									// Save in dummy after the position user want to modify
									userInput.close();

									PrintWriter userOutput = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));// throws
									/* Before Modification*/
									userOutput.print(dummy1);

									/* Modification */
									userOutput.print(update + "\n");

									/* After Modification */
									userOutput.print(dummy2);

									userOutput.close();
								}
							}
						}
						
					}
					}catch(IOException e1) {
						System.out.println(e1);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				}
			}
			
		}
		
		table = new JTable(model);
		table.getTableHeader().setReorderingAllowed(false); //?????? ?????????
		table.addMouseListener(new MyMouseListener());
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 38, 584, 302);
		searchPanel.add(scrollPane);
		
		JButton prvSearch = new JButton("??????");
		prvSearch.setBounds(484, 10, 88, 23);
		searchPanel.add(prvSearch);
		prvSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchPanel.setVisible(false);
				menuPanel.setVisible(true);
			}
		});
		
		searchTxt = new JTextField();
		searchTxt.setBounds(75, 340, 380, 21);
		searchPanel.add(searchTxt);
		searchTxt.setColumns(10);
		
		
		// ????????? ????????? ??? ????????? ???????????? ?????? ----------------------------------------------------------------------------------
		JButton library1Button = new JButton("??????");
		library1Button.setBounds(10, 10, 60, 23);
		searchPanel.add(library1Button);
		library1Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				bookFile = "???????????????????????????books.txt";
				model.setNumRows(0);
				
				try {
					File file = new File(bookFile);
					
					BufferedReader bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
					String line = "";
					int i = 0;
					while((line = bufReader.readLine()) != null) {
						String[] array = line.split("/");
						if(i != 0)
							model.addRow(array);
						i++;
						
					}
					
					bufReader.close();
				}catch (FileNotFoundException e1) {
					
				}catch (IOException e1) {
					System.out.println(e1);
				}
				
			}
		});
		
		JButton library2Button = new JButton("??????");
		library2Button.setBounds(75, 10, 60, 23);
		searchPanel.add(library2Button);
		library2Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				bookFile = "???????????????books.txt";
				model.setNumRows(0);
				
				try {
					File file = new File(bookFile);
					
					BufferedReader bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
					String line = "";
					int i = 0;
					while((line = bufReader.readLine()) != null) {
						String[] array = line.split("/");
						if(i != 0)
							model.addRow(array);
						i++;
						
					}
					
					bufReader.close();
				}catch (FileNotFoundException e1) {
					
				}catch (IOException e1) {
					System.out.println(e1);
				}
				
			}
		});
		
		JButton library3Button = new JButton("??????");
		library3Button.setBounds(140, 10, 60, 23);
		searchPanel.add(library3Button);
		library3Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				bookFile = "???????????????books.txt";
				model.setNumRows(0);
				
				try {
					File file = new File(bookFile);
					
					BufferedReader bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
					String line = "";
					int i = 0;
					while((line = bufReader.readLine()) != null) {
						String[] array = line.split("/");
						if(i != 0)
							model.addRow(array);
						i++;
						
					}
					
					bufReader.close();
				}catch (FileNotFoundException e1) {
					
				}catch (IOException e1) {
					System.out.println(e1);
				}
				
			}
		});
		
		JButton library4Button = new JButton("??????");
		library4Button.setBounds(205, 10, 60, 23);
		searchPanel.add(library4Button);
		library4Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				bookFile = "???????????????books.txt";
				model.setNumRows(0);
				
				try {
					File file = new File(bookFile);
					
					BufferedReader bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
					String line = "";
					int i = 0;
					while((line = bufReader.readLine()) != null) {
						String[] array = line.split("/");
						if(i != 0)
							model.addRow(array);
						i++;
						
					}
					
					bufReader.close();
				}catch (FileNotFoundException e1) {
					
				}catch (IOException e1) {
					System.out.println(e1);
				}
				
			}
		});
		
		JButton library5Button = new JButton("??????");
		library5Button.setBounds(270, 10, 60, 23);
		searchPanel.add(library5Button);
		library5Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				bookFile = "???????????????books.txt";
				model.setNumRows(0);
				
				try {
					File file = new File(bookFile);
					
					BufferedReader bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
					String line = "";
					int i = 0;
					while((line = bufReader.readLine()) != null) {
						String[] array = line.split("/");
						if(i != 0)
							model.addRow(array);
						i++;
						
					}
					
					bufReader.close();
				}catch (FileNotFoundException e1) {
					
				}catch (IOException e1) {
					System.out.println(e1);
				}
				
			}
		});
		
		searchPanel.setVisible(false);
		
		
		// ???????????? ?????? ?????? ?????? -----------------------------------------------------------------------------------
		JButton searchButton = new JButton("??????");
		searchButton.setBounds(484, 339, 88, 23);
		searchPanel.add(searchButton);
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				
				String search = searchTxt.getText();
				/* file open to search a book */
				model.setNumRows(0);
				BufferedReader inputStream = null;
				try {
					inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(bookFile), "utf-8"));
				
					String temp = "????????????"; // initialize needed except null
					String[] searchedList = new String[100];
					int cnt = -1; // if searched, count++, count the result - if count == 0, print "???????????? ??????"
					String[] splited = null;
					do {
						temp = inputStream.readLine();
					
						if (temp == null)
							break; // EOF Exception
						/* for checking */
						if (temp.contains(search)) {
							cnt++;
							
							splited = temp.split("/");
							model.addRow(splited);
							searchedList[cnt] = splited[0]; // Save book numbers in array if results contain matching values

						}

					}  while (temp != null);
				
				inputStream.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e2) {
					System.out.println(e2);
				}
				
			}
		});
		
		
		
		

		/*///////////////////////////////////////////////////////////////////
		 * ?????? ?????? ???---------------------------------------------------------
		////////////////////////////////////////////////////////////////// */
		menuPanel.setBounds(0, 0, 584, 361);
		contentPane.add(menuPanel);
		menuPanel.setLayout(null);
		menuPanel.setVisible(false);
		
		// ????????? ????????? ????????? ???????????? ?????? ????????? ???????????? ??????(????????? : ???????????????????????????)----------------------------------------
		JButton search = new JButton("??????/??????");
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bookFile = "???????????????????????????books.txt";
				try {
					File file = new File("???????????????????????????books.txt");
					
					BufferedReader bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
					String line = "";
					int i = 0;
					while((line = bufReader.readLine()) != null) {
						String[] array = line.split("/");
						if(i != 0)
							model.addRow(array);
						i++;
						
					}
					
					bufReader.close();
				}catch (FileNotFoundException e1) {
					
				}catch (IOException e1) {
					System.out.println(e1);
				}
				
				menuPanel.setVisible(false);
				searchPanel.setVisible(true);
			}
		});
		search.setBounds(350, 100, 102, 55);
		menuPanel.add(search);
		
		// ???????????? ?????? ??? ?????? -----------------------------------------------------------------------------------------
		JButton distance = new JButton("????????????");
		distance.setBounds(150, 100, 102, 55);
		menuPanel.add(distance);
		distance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuPanel.setVisible(false);
				distancePanel.setVisible(true);
			}
		});
		
		
		// ??? ???????????? ??? ??? ?????? ??????(????????? ???????????? ?????? ????????????)-----------------------------------------------------------------
		JButton myPage = new JButton("??? ??????");
		myPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuPanel.setVisible(false);
				myPagePanel.setVisible(true);
				
				userNumTxt.setText(user.no);
				userNameTxt.setText(user.name);
				birthTxt.setText(user.resident_no);
				addressTxt.setText(user.address);
				phoneNumTxt.setText(user.phone_no);
				extensionTxt.setText(user.extension);
			}
		});
		myPage.setBounds(150, 200, 102, 55);
		menuPanel.add(myPage);
		
		// ??????/?????? ?????? ??????????????? ?????? ????????? ??? ?????? ?????? ---------------------------------------------------------------------------
		JButton extension = new JButton("??????/??????");
		extension.setBounds(350, 200, 102, 55);
		menuPanel.add(extension);
		extension.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rentFile = "rent.txt";
				rentModel.setNumRows(0);
				try {
					File file = new File("rent.txt");
					
					BufferedReader bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
					String line = "";
					while((line = bufReader.readLine()) != null) {
						String[] array = line.split("\t");
						String result = array[1] + "\t" + array[2] + "\t" + array[3] + "\t" + array[4];
						array = result.split("\t");
						if(user.no.equals(array[0]))
						rentModel.addRow(array);
						
					}
					
					bufReader.close();
				}catch (FileNotFoundException e1) {
					
				}catch (IOException e1) {
					System.out.println(e1);
				}
				
				menuPanel.setVisible(false);
				extensionPanel.setVisible(true);
				
			}
		});
		
		
		
		
		
		/*///////////////////////////////////////////////////////////////////
		 * ????????? ?????? ???---------------------------------------------------------
		////////////////////////////////////////////////////////////////// */
		JPanel LogInPanel = new JPanel();
		LogInPanel.setBounds(0, 0, 584, 361);
		contentPane.add(LogInPanel);
		LogInPanel.setLayout(null);
		
		JLabel label = new JLabel("ID");
		label.setBounds(99, 70, 38, 15);
		LogInPanel.add(label);
		
		IDText = new JTextField();
		IDText.setBounds(212, 67, 116, 21);
		IDText.setColumns(10);
		LogInPanel.add(IDText);
		
		JLabel label_1 = new JLabel("Password");
		label_1.setBounds(99, 116, 80, 15);
		LogInPanel.add(label_1);
		
		PswText = new JPasswordField();
		PswText.setBounds(212, 113, 116, 21);
		PswText.setColumns(10);
		LogInPanel.add(PswText);
		
		JButton LogIn = new JButton("Log-In");
		LogIn.setBounds(235, 180, 69, 23);
		LogInPanel.add(LogIn);
		
		//????????? ??? ???????????? ?????? ??????, ??? ???????????? ????????? ??????--------------------------------------------------
		LogIn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg) {
				
				try {
					
					Scanner scan = new Scanner(System.in); 
			        // RSA ?????? ??????
			        KeyPair keyPair = CipherUtil.genRSAKeyPair();

			        PublicKey publicKey = keyPair.getPublic();
			        PrivateKey privateKey = keyPair.getPrivate();
			        
			        BufferedReader fileReader = new BufferedReader(new FileReader("user.txt"));
			        BufferedWriter writer = new BufferedWriter(new FileWriter("userEncoding.txt"));

					// a line from the file
					String line;
					String[] person=new String[50];
					//person[]?????? ??? ????????? ???????????? ????????? ?????? ????????? ??????
					int p=0;

					// read lines until we read a line that is null (i.e. no more lines)
					while((line = fileReader.readLine()) != null) {
					       // split the line, returns an array of parts
					       person[p++] =line; 
					}
					fileReader.close();
					
					person[0]+="\n";
					writer.write(person[0]);

					for(int i=1;i<p;i++) { //?????? ??? p?????? ?????? ??? 
						String[] tmp=person[i].split("/");
						
						for(int j=0;j<9;j++) {//????????????
							//tmp[0]:????????????/tmp[1]:?????????/tmp[2]:????????????/tmp[3]:?????????/tmp[4]:????????????/tmp[5]:??????/tmp[6]:????????????/tmp[7]:????????????/tmp[8]:??????
							
							if(j==2||j==4||j==6) {//????????????, ????????????, ???????????? ?????????
								//?????????
								 String encrypted = CipherUtil.encryptRSA(tmp[j], publicKey);
								 encrypted+="\t";
								 writer.write(encrypted);
							}
							else if(j==8) {
								tmp[j]+="\n";
								writer.write(tmp[j]);
							}
							else {
								tmp[j]+="\t";
								writer.write(tmp[j]);
							}
					
						}
					}		
						writer.close();
					
					
					File file = new File("userEncoding.txt");
					
					BufferedReader bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
					String line1 = "";
			        
					int correct = 0;
					int num = 0;
			        while((line1 = bufReader.readLine()) != null) {
						
			        	//attribute??? ??????
			        	if(num == 0) {
			        		line1 = bufReader.readLine();
			        		num++;
			        	}

						String[] array = line1.split("\t");
						String id = array[3];
						String pass = array[4];
						int check=0;
						
						 if(id.equals(IDText.getText())) { //????????? ???????????? ??????????????? ?????????
							 
			                 pass = CipherUtil.decryptRSA(pass, privateKey);

			                 //???????????? ?????????
			                 array[2]= CipherUtil.decryptRSA(array[2], privateKey);
								
			                 //???????????? ?????????
			                 array[6]= CipherUtil.decryptRSA(array[6], privateKey);
			                  
			                 if(pass.equals(PswText.getText())) {// ??????????????? ????????? ??????
			                	 menuPanel.setVisible(true);
			                	 LogInPanel.setVisible(false);
			                	 info = array;
			                	 userNumTxt.setText(info[0]);
			                	 userNameTxt.setText(info[1]);
			                	 birthTxt.setText(info[2]);
			                	 addressTxt.setText(info[5]);
			                	 phoneNumTxt.setText(info[6]);
			                	 extensionTxt.setText(info[7]);
									
			                	 user = new User(info[0], info[1], info[2], info[5], info[6], info[7], info[8]);
			                	 correct = 1;
			                	 break;
			                  }
			               }
			            }
			        if(correct == 0)
			        	JOptionPane.showMessageDialog(LogInPanel, "????????? ?????????/???????????? ?????????.");
					
					bufReader.close();
				}catch (FileNotFoundException e) {
					
				}catch (IOException e) {
					System.out.println(e);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (InvalidKeyException e) {
					e.printStackTrace();
				} catch (NoSuchPaddingException e) {
					e.printStackTrace();
				} catch (BadPaddingException e) {
					e.printStackTrace();
				} catch (IllegalBlockSizeException e) {
					e.printStackTrace();
				}
				
			}
		});
		
		
	}
}
