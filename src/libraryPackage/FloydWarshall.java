package libraryPackage;

import java.util.*;
import java.io.*;

public class FloydWarshall //Shortest distance search with Floyd-Warshall
{
   
   static int c =0; //Variables to replace library indexes
   static int passcnt =0; //path case counter
   static double distance[] = new double[5]; //the distance between users and libraries

   static int nextcnt =0; //유효 케이스 카운터    
   static int sumcnt =0; //유효 케이스의 수 카운터 
   
   static String libFile = "library.txt";
   static String userFile = "user.txt";

   static String[] libname = new String[5];//library name arrangement
   static String bookName1 = "";
   static String bookName2 = "";
   static String bookName3 = "";
   
   static String linkname[] = new String[5];//libraries passed by on the move

   rentbook rent = new rentbook();//class object 
   riblen r = new riblen();
   
   
   static class userlocation//Coordinates of logged-in users
   {
      double x;
      double y;
   }
   
   static class liblocation//Coordinates of library
   {
       double x[] = new double[5];
       double y[] = new double[5];
   }
   
   static class rentbook//Class to store information about books to rent
   {
      List<String> rentbook = new ArrayList<String>()//A list to store the name of the book to rent
      {
         {

            
            if(!bookName1.equals(""))
               add(bookName1);
      
            
            if(!bookName2.equals(""))
               add(bookName2);
         
            
            if(!bookName3.equals(""))
               add(bookName3);
         
               
         }
      };

      
      List<Integer> rentval = new ArrayList<Integer>()//A list to store whether or not user have borrowed a book to borrow.
      {
         {
            add(0);
            add(0);
            add(0);
         }
      };
      //If rented, set to 1, else 0
      
      List<Double> sum = new ArrayList<Double>()
      {
   
         {
               add((double) 0);
         
         }         
         
      };//Total travel distance for each path
      

      String path[][] = new String[100][100];//A two-dimensional array that stores the route when renting a book.
      
      Integer path1[][] = new Integer[5][5];//Storing the middle path of each library through Floyd Warshall
      
   }

   class riblen//Class to store distance information between each library. 
   {
      
      double length[][] = { { 0, 1, 1.4, 2.7, 5}, { 1, 0, 0.6, 1, 1.8}, { 1.4, 0.6, 0, 2.4, 3.1},
            { 2.7, 1, 2.4, 0, 1.1 }, { 0.5, 1.8, 3.1, 1.1, 0 } };      
   }
   
   
   
   public void floydalgorithm()//Function to perform Floyd Warshall.
   {   
      for (int i=0; i<5; i++)
          for (int j=0; j<5; j++)
             if (r.length[i][j] == 10000)
                rent.path1[i][j] = -1;
             else
                rent.path1[i][j] = i;

      for (int k = 0; k < r.length.length; k++) 
      {
         for (int i = 0; i < r.length.length; i++) 
         {
            for (int j = 0; j < r.length.length; j++) 
            {
               if (r.length[i][j] > r.length[i][k] + r.length[k][j])
               {
                  r.length[i][j] = r.length[i][k] + r.length[k][j];
            
                     rent.path1[i][j] = rent.path1[k][j];//storage of intermediate points         
               }
            }
         }
      }   
      
   }
   
   
   public boolean search1(String rib, String book) throws IOException//Search for book to rent
   {
      String bookFile = rib + "books.txt";
      
      BufferedReader inputStream = null;
      try {
         inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(bookFile), "utf-8"));
      } catch (FileNotFoundException e1) {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      }
      
      String temp = "tmp";//

      do {
         temp = inputStream.readLine(); // throws IOException
         if (temp == null)
            break; //Break if read to the end of the document
         /* for checking */
         if (temp.contains(book)) 
         {
            return true;
         }

      } while (temp != null);
      
      return false;
      
   }
   

   public boolean allrent()//Check if user have rented all the books
   {
      
      for(int i=0; i<rent.rentval.size(); i++)
      {
         if(rent.rentval.get(i) == 0)
         {
            return false;
         }
      }
      return true;
   }
   
   public void resetrent()//rentval initialization function
   {
      
      for(int i=0; i<rent.rentval.size(); i++)
      {
         if(rent.rentval.get(i) == 1)
            rent.rentval.set(i,0);      
      }
   }
   
   public void clear()//Initialization for reuse
   {
      rent.sum.clear();
      nextcnt =0;    
      sumcnt =0; 
      passcnt =0;
      c = 0;
      for(int i=0; i < linkname.length; i++)
      {
         linkname[i] = null;
      }
   }
   
   
   public void print() throws IOException//Result output function.
   {      

      rent.sum.remove((Double)0.0);//The initial value of 0 is a temporary value to avoid nullpointer error, so delete it.
      
      int minIndex = rent.sum.indexOf(Collections.min(rent.sum));//Obtain the minimum value and then obtain the index value.
         
      Library.textPane.append("\n");
      Library.textPane.append("최단거리 : " + String.format("%.4f", Collections.min(rent.sum)) +"km");//shortest distance output
      Library.textPane.append("\n");
      Library.textPane.append("최단 이동경로 > \n");
      boolean linkval = false;
      for(int i=0; i < rent.path[minIndex].length; i++)//shortest path output
      {
         if(rent.path[minIndex][i] == null)
            break;
         else
         {
            if(rent.path[minIndex][i] != null && rent.path[minIndex][i] !=rent.path[minIndex][i+1])
               Library.textPane.append("[" + rent.path[minIndex][i]+ "] ");
            if(i % 2 == 0 && rent.path[minIndex][i+ 1] != null )
            {
               for(int j =0; j<libname.length; j++)//intermediate point output
               {
                  if(rent.path[minIndex][i+ 1] == libname[j])
                     link(minIndex,j);
               }
               linkval = true;
            }
         }   
      }
      
      Library.textPane.append("\n");
      
      for(int i=0; i < rent.path.length; i++)
      {
         for(int b=0; b<rent.rentbook.size(); b++)//Output where user can rent a book
         {
            if(rent.path[minIndex][i] != null &&  search1(rent.path[minIndex][i], rent.rentbook.get(b)))
            {   
               Library.textPane.append("'" + rent.rentbook.get(b) + "' 책은 " + rent.path[minIndex][i] + "에서 빌리실 수 있습니다.\n");
               rent.rentval.set(b, 1);   
            }
            resetrent();
         }
      }

      if(linkval)//If a midpoint exists
      {
         for(int i=0; i < linkname.length; i++)//Print out if user can rent a book from a mid-point library.
         {
            for(int b=0; b<rent.rentbook.size(); b++)
            {
               if(linkname[i] != null &&  search1(linkname[i], rent.rentbook.get(b)))
               {   
                  Library.textPane.append("'" + rent.rentbook.get(b) + "' 책은 " + linkname[i] + "에서 빌리실 수 있습니다.\n");
                  rent.rentval.set(b, 1);   
               }
               resetrent();
            }
         }
      }
      
      Library.textPane.append("\n");
      Library.textPane.append("-그 외 다른 이동경로-\n");
      
      for(int j=0; j < rent.sum.size(); j++)//Output valid path other than shortest path
      {
         for(int i=0; i < rent.path[j].length; i++)
         {
            if(j != minIndex)
            {
               if(rent.path[j][i] != null)
                  Library.textPane.append("[" + rent.path[j][i]+ "] ");
               if(i % 2 == 0 && rent.path[j][i+ 1] != null)
                  link(j,i);   
            }
         }
         if(j != minIndex)
            Library.textPane.append("\n거리 : " + String.format("%.4f", rent.sum.get(j)) + "km\n\n");
      }
   
       clear();
   }
   
   public void link(int a, int b) throws IOException//A function that calculates the values between two points that are passed through Floyd Warshal.
   {
        
      int bidx = 0;
      int b2idx = 0;
      
      
      for(int i=0; i < libname.length; i++)
      {
         if(libname[i] == rent.path[a][b])
            bidx =  i;
         if(libname[i] == rent.path[a][b + 1])
            b2idx =  i;
      }   
          
      for (int i=0; i<5; i++)
         rent.path1[i][i] = i;
      
       String myPath = "";
       
       int i=0;
       while (rent.path1[bidx][b2idx] != bidx) {
          myPath = "(" + libname[rent.path1[bidx][b2idx]] + ") " + myPath;
          linkname[i] = libname[rent.path1[bidx][b2idx]];
          b2idx = rent.path1[bidx][b2idx];      
          i++;      
       }
       
       myPath ="" + myPath;
       Library.textPane.append(myPath);

   }

   
   public void m5(int cur) throws IOException//Function to obtain the shortest distance to borrow a book based on Floyd Washall
   {   
      
      if(c > libname.length)//c is the library index number of cur. There are 0 to 4, so if it's 5, it's over.
      {
         return;
      }
            
      
      for(int i=0; i < rent.rentbook.size(); i++)
      {   
                  
         for(int j= 0 ; j < libname.length; j++)
         {   
            if(passcnt ==0)
            {
               resetrent();   
               rent.path[sumcnt][passcnt] = libname[cur];//User have visited where user are, so save it to the path.
               rent.sum.add(sumcnt, distance[cur]);
               passcnt++;
            
            }
            
            for(int b=0; b<rent.rentbook.size(); b++)//Add all the books in the library as rented.
            {
               if(search1(libname[cur], rent.rentbook.get(b)))
               {   
                  rent.rentval.set(b, 1);      
               }
            }
            
            if(allrent() && c < libname.length- 1)// Once user have rented all the books, move on to the next library case.
            {   
            
               sumcnt++;   
               passcnt =0;
               c++;
               resetrent();      
               m5(c);
               
            }
            
            if (j != cur && search1(libname[j], rent.rentbook.get(i)) && rent.rentval.get(i) == 0 ) 
               //Except for the same library, there are books user want to find, and if user haven't borrowed them yet,
               {               
                  rent.path[sumcnt][passcnt] = libname[j];//Going there. Therefore, add to path
                                                   
                  rent.sum.set(sumcnt, rent.sum.get(sumcnt) + r.length[cur][j]);//Add path Distance                           
                     
                  for(int k=0; k < rent.path1[cur].length; k++)//User can also borrow books from the library on the path, so user add that user have borrowed all the books in the library.
                  {
                     for(int t=0; t < rent.rentbook.size(); t++)
                     {
                        if (search1(libname[rent.path1[cur][k]], rent.rentbook.get(t)) &&  rent.rentval.get(t) == 0)
                           rent.rentval.set(i,1);   
                     }
                        
                  }
                  nextcnt++;
                  m5(j);   
                                                                                             
               }
                     
            }
         }
      
   }
}




   
