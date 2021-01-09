package gd;
 
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileLockInterruptionException;
import java.util.Scanner;
import javax.annotation.processing.Filer;

import javax.imageio.ImageIO;
import javax.swing.*;


public class Main extends JPanel{	
	ImageIcon finalgif = new ImageIcon("finalgif1.gif");
	static FinalMenuWithBoardClicks fm = new FinalMenuWithBoardClicks();
	
	/**this method is for choosing the level of the game
	 * 
	 * @param num
	 * @throws InterruptedException
	 * @throws IOException
	 */
	 public static void choose(int num) throws InterruptedException, IOException {
		 
        if(num==1) {
                part1();
        }
        if(num==2) {
                part2();
        }
        if(num==4) {
                part4();
        }
        if(num==3) {
                part3();
        }
        if(num==5) {
                part5();
        }
        if(num==6) {
                part6();
        }
        if(num==7) {
                part7();
        }
        if(num==8) {
                part8();
        }
        if(num==9) {
                part9();
        }
        if(num==10) {
                try {
					part10();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
        
}
	
	 //ignore this line --------------------------String[][] sB = {{"","","","","3"},{"","","","","3"}, {"","","","2","1"},{"","","","2","1"}, {"","","","","1"}}; 

	 /**in our first level, use file reading to transfer the data of the game 
	  * 
	  * @param name of file(part2.txt)
	  * @return a 2d array
	  * @throws FileNotFoundException
	  */
	public static String[][] fileReading(String name) throws FileNotFoundException{
		File file =new File(name);
		Scanner input = new Scanner (file);
		int rows=input.nextInt();
		int cols=input.nextInt();
		String[][]sB =new String[rows][cols];
		int counter=0;
		while (input.hasNext()) {
			String line= input.next();
				for(int j=0;j<cols;j++) {
				sB[counter][j]=line.substring(j,j+1);
				}
				counter++;
		}

		for(int i = 0;i < sB.length;i++){   
			for(int j = 0;j < sB[i].length;j++){
				if(sB[i][j].equals("-")) {
					sB[i][j]="";
				}
			}
		}
		return sB;
	}
	

	/** for the next 10 methods which named as partx() is the differnet level of the game
	 * in each method there is a link to the main menu
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void part2() throws  InterruptedException, IOException{
			// import the file we needed 
			String[][] sB=fileReading("part2.txt");
			// this is another part of 
			String[][] sT = {{"","","","",""},{"","","","",""}, {"","","","",""},{"","1","","",""}, {"1","2","3","2","4"}};
			Board b = new Board(11, 11, sB, sT);  // the board size should be at least 7*7
			int max = 10000;	//max is the maximum point we can click
			int i = 0;				// i is the amount of click we did
			int hang =5;		// this is the amount of lines
			int lie =5;			// this is the amount of rows(this is the chinese meaning of row and line--i always confuse about these two words (Andy))
			boolean[][] points =new boolean[hang][lie];
			for (int a=0;a<hang;a++) {								//initialize the points  ------make sure they are false at first
				for (int c=0;c<lie;c++) {
					points[a][c]=false;
				}
			}
			
			// the following decleration is the location of bomb we set
			points[0][1]=true;
			points[0][2]=true;
			points[0][3]=true;		
			points[1][2]=true;				
			points[1][3]=true;		
			points[1][4]=true;		
			points[2][1]=true;			
			points[2][2]=true;	
			points[2][4]=true;		
			points[3][0]=true;		
			points[3][1]=true;		
			points[3][4]=true;		
			points[4][4]=true;
			// the amount is used to check whether user finish their game or not
			int amount=13;
			
			while(i<max) {
				Coordinate click = b.getClick();
				int row = click.getRow();
				int col = click.getCol();
				// put peg on the grid user clicked and record the times 
				b.putPeg("LIGHT", row, col);
				i++;
				//check whether user have a correct selection everytime they click the board
				// in this part user will have a choice if they pass the level----back to menu or keep moving to next level
				// if they make a mistake they also can have chance to restart the game 
				// -6 is in order to fit the board grid location with points array
				if (row-6<0 || col-6<0) {
					part2();
				}
				if (b.getFlag()!=points[row-6][col-6]) {
					// if user make a mistake  here  he/ she will have a choice  back to menu or restart					
					int res = JOptionPane.showConfirmDialog(null, "You Failed, do you want one more chance", "Fail", JOptionPane.YES_NO_OPTION);
					if (res == JOptionPane.YES_OPTION) {
						// restart the game
						part2();
					} else {
						// back to menu
						fm.startMenu();							 
						return;
					}
					break;
				}else if (i==amount) {
					// it is similar to the previous one back to menu or move to next level
					int res = JOptionPane.showConfirmDialog(null, "You Win, move to next level", "Congratuation", JOptionPane.YES_NO_OPTION);
					if (res == JOptionPane.YES_OPTION) {
						// next level
						part3();
					} else {
						// back to menu
						fm.startMenu();												 
						return;
					}
				// this is a code that make the grid that user already clicked change type(shouldn't told user ---can use this way to pass the level quite easy)
				}else if(b.getFlag()==points[row-6][col-6]) {
					points[row-6][col-6]=false;
				}
				// this is a code that used to make i which will related to amounts of bombs only count the times of left click
				if(!b.getFlag()) {												 
					i--;
				}
				System.out.println(i);
			}
			

		}


	/**the following 9 part is almost the same with each other-- the comment is teh same as part2
	 * PS: i make a wrong order of part2 and part1 ^o^(i am so sorry(Andy))
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void part1() throws InterruptedException, IOException {
		
		
		String[][] sB = {{"","","","","4"},{"","","","1","1"}, {"","","","","5"},{"","","","2","1"}, {"","","","","1"}}; 
		String[][] sT = {{"","","","",""},{"","","","",""}, {"","","","",""},{"","1","","1",""}, {"1","2","5","1","4"}};
		Board b = new Board(11, 11, sB, sT);  // the board size should be at least 7*7
		b.displayMessage("HI!!!!");
		int max = 10000;
		int i = 0;
		
		
		int hang =5;
		int lie =5;
		boolean[][] points =new boolean[hang][lie];
		for (int a=0;a<hang;a++) {								//initialize the points  ------make sure they are false at first
			for (int c=0;c<lie;c++) {
				points[a][c]=false;
			}
		}
		points[0][1]=true;
		points[0][2]=true;
		points[0][3]=true;
		points[0][4]=true;
		points[1][2]=true;
		points[1][4]=true;
		points[2][0]=true;
		points[2][1]=true;
		points[2][2]=true;
		points[2][3]=true;
		points[2][4]=true;
		points[3][1]=true;
		points[3][2]=true;
		points[3][4]=true;
		points[4][2]=true;
		
		int amount=15;
		while(i<max) {
			
			Coordinate click = b.getClick();
			int row = click.getRow();
			int col = click.getCol();
			b.putPeg("LIGHT", row, col);
			i++;
			if (row-6<0 || col-6<0) {
				part2();
			}
			if (b.getFlag()!=points[row-6][col-6]) {
				System.out.println("false");
				
				int res = JOptionPane.showConfirmDialog(null, "You Failed, do you want one more chance", "Fail", JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.YES_OPTION) {
					part1();
				} else {
					fm.startMenu();																 
					return;
				}
				break;
			}else if (i==amount) {
				 
				int res = JOptionPane.showConfirmDialog(null, "You Win, move to next level", "Congratuation", JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.YES_OPTION) {
					part2();
				} else {
					fm.startMenu();												 
					return;
				}

			}else if(b.getFlag()==points[row-6][col-6]) {
				points[row-6][col-6]=false;
			}
			if(!b.getFlag()) {												 
				i--;
			}
			System.out.println(i);
		}
		}
			
	public static void part3() throws InterruptedException, IOException {
		String[][] sB = {{"","","","","2"},{"","","","","4"}, {"","","","","5"},{"","","","1","1"}, {"","","","1","1"}}; 
		String[][] sT = {{"","","","",""},{"","","","",""}, {"","","","",""},{"1","","3","",""}, {"1","3","1","4","2"}};
		Board b = new Board(11, 11, sB, sT);  // the board size should be at least 7*7
		b.displayMessage("HI!!!!");
		int max = 10000;
		int i = 0;
		
		
		int hang =5;
		int lie =5;
		boolean[][] points =new boolean[hang][lie];
		for (int a=0;a<hang;a++) {								//initialize the points  ------make sure they are false at first
			for (int c=0;c<lie;c++) {
				points[a][c]=false;
			}
		}
		points[0][2]=true;
		points[0][3]=true;
		points[1][1]=true;
		points[1][2]=true;
		points[1][3]=true;
		points[1][4]=true;
		points[2][0]=true;
		points[2][1]=true;
		points[2][2]=true;
		points[2][3]=true;
		points[2][4]=true;
		points[3][1]=true;
		points[3][3]=true;
		points[4][0]=true;
		points[4][2]=true;
		
		int amount=15;
		
		while(i<max) {
			
			Coordinate click = b.getClick();
			int row = click.getRow();
			int col = click.getCol();
			b.putPeg("LIGHT", row, col);
			i++;
			if (row-6<0 || col-6<0) {
				part3();
			}
			if (b.getFlag()!=points[row-6][col-6]) {
				System.out.println("false");
				
				int res = JOptionPane.showConfirmDialog(null, "You Failed, do you want one more chance", "Fail", JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.YES_OPTION) {
					part3();
				} else {
					fm.startMenu();																	 
					return;
				}
				break;
			}else if (i==amount) {
				 
				int res = JOptionPane.showConfirmDialog(null, "You Win, move to next level", "Congratuation", JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.YES_OPTION) {
					part4();
				} else {
					fm.startMenu();												 
					return;
				}

			}else if(b.getFlag()==points[row-6][col-6]) {
				points[row-6][col-6]=false;
			}
			if(!b.getFlag()) {												 
				i--;
			}
			System.out.println(i);
		}
	}
	
	public static void part4() throws InterruptedException, IOException {
		 	String[][] sB = {{"","","","","3"},{"","","","","1"}, {"","","","1","1"},{"","","","1","1"}, {"","","","","4"}}; 
			String[][] sT = {{"","","","",""},{"","","","",""}, {"","","","",""},{"","","2","1",""}, {"3","1","1","3","1"}};
			Board b = new Board(11, 11, sB, sT);  // the board size should be at least 7*7
 
			int max = 10000;
			int i = 0;
			
			
			int hang =5;
			int lie =5;
			boolean[][] points =new boolean[hang][lie];
			for (int a=0;a<hang;a++) {								//initialize the points  ------make sure they are false at first
				for (int c=0;c<lie;c++) {
					points[a][c]=false;
				}
			}
			
			points[0][2]=true;
			points[0][3]=true;
			points[0][4]=true;
			points[1][2]=true;
			points[2][0]=true;
			points[2][3]=true;
			points[3][0]=true;
			points[3][3]=true;
			points[4][0]=true;
			points[4][1]=true;
			points[4][2]=true;
			points[4][3]=true;
			
			
			int amount=12;
			while(i<max) {
				
				Coordinate click = b.getClick();
				int row = click.getRow();
				int col = click.getCol();
				b.putPeg("LIGHT", row, col);
				i++;
				if (row-6<0 || col-6<0) {
					part4();
				}
				if (b.getFlag()!=points[row-6][col-6]) {
 
					
					int res = JOptionPane.showConfirmDialog(null, "You Failed, do you want one more chance", "Fail", JOptionPane.YES_NO_OPTION);
					if (res == JOptionPane.YES_OPTION) {
						part4();
					} else {
						fm.startMenu();																 
						return;
					}
					break;
				}else if (i==amount) {
					 
					int res = JOptionPane.showConfirmDialog(null, "You Win, move to next level", "Congratuation", JOptionPane.YES_NO_OPTION);
					if (res == JOptionPane.YES_OPTION) {
						part5();
					} else {
						fm.startMenu();												 
						return;
					}

				}else if(b.getFlag()==points[row-6][col-6]) {
					points[row-6][col-6]=false;
				}
				if(!b.getFlag()) {												 
					i--;
				}
				System.out.println(i);
			}
			}
			
	public static void part5() throws InterruptedException, IOException {
		String[][] sB = {{"","","1","1","1"},{"","","1","1","3"}, {"","","3","2","2"},{"","","1","2","1"}, {"","","","3","4"}}; 
		String[][] sT = {{"","","","","","","","","",""},{"","","","","","","","","",""}, {"","","","","","","","","",""},{"","","2","","","1","2","2","2","1"}, {"2","2","1","1","2","2","1","1","1","3"}};
		Board b = new Board(11, 16, sB, sT);  // the board size should be at least 7*7
		b.displayMessage("HI!!!!");
		int max = 10000;
		int i = 0;

		
		int hang =5;
		int lie =10;
		boolean[][] points =new boolean[hang][lie];
		for (int a=0;a<hang;a++) {								//initialize the points  ------make sure they are false at first
			for (int c=0;c<lie;c++) {
				points[a][c]=false;
			}
		}
		points[0][5]=true;
		points[0][7]=true;
		points[0][9]=true;
		points[1][0]=true;
		points[1][2]=true;
		points[1][6]=true;
		points[1][7]=true;
		points[1][8]=true;
		points[2][0]=true;
		points[2][1]=true;
		points[2][2]=true;
		points[2][5]=true;
		points[2][6]=true;
		points[2][8]=true;
		points[2][9]=true;
		points[3][1]=true;
		points[3][4]=true;
		points[3][5]=true;
		points[3][9]=true;
		points[4][2]=true;
		points[4][3]=true;
		points[4][4]=true;
		points[4][6]=true;
		points[4][7]=true;
		points[4][8]=true;
		points[4][9]=true;
		
		int amount=26;
		
		while(i<max) {
			
			Coordinate click = b.getClick();
			int row = click.getRow();
			int col = click.getCol();
			b.putPeg("LIGHT", row, col);
			i++;
			if (row-6<0 || col-6<0) {
				part5();
			}
			if (b.getFlag()!=points[row-6][col-6]) {
				System.out.println("false");
				
				int res = JOptionPane.showConfirmDialog(null, "You Failed, do you want one more chance", "Fail", JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.YES_OPTION) {
					part5();
				} else {
					fm.startMenu();															 
					return;
				}
				break;
			}else if (i==amount) {
				 
				int res = JOptionPane.showConfirmDialog(null, "You Win, move to next level", "Congratuation", JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.YES_OPTION) {
					part6();
				} else {
					fm.startMenu();												 
					return;
				}

			}else if(b.getFlag()==points[row-6][col-6]) {
				points[row-6][col-6]=false;
			}
			if(!b.getFlag()) {												 
				i--;
			}
			System.out.println(i);
		}
	}
		
	public static void part6() throws InterruptedException, IOException {
		String[][] sB = {{"","","","","3"},{"","","","","1"},{"","","","","4"},{"","","","","1"},{"","","","1","1"},{"","","","","1"},{"","","","3","1"},{"","","1","1","1"},{"","","","3","1"},{"","","","","3"}}; 
		String[][] sT = {{"","1","","",""},{"","1","","",""},{"","1","","1",""},{"1","1","3","4",""},{"3","1","4","1","4"}};
		Board b = new Board(16, 11, sB, sT);  // the board size should be at least 7*7
		b.displayMessage("HI!!!!");
		int max = 10000;
		int i = 0;
		
		int hang =10;
		int lie =5;
		boolean[][] points =new boolean[hang][lie];
		for (int a=0;a<hang;a++) {								//initialize the points  ------make sure they are false at first
			for (int c=0;c<lie;c++) {
				points[a][c]=false;
			}
		}
		points[0][1]=true;
		points[0][2]=true;
		points[0][3]=true;
		points[1][2]=true;
		points[2][0]=true;
		points[2][1]=true;
		points[2][2]=true;
		points[2][3]=true;
		points[3][3]=true;
		points[4][1]=true;
		points[4][3]=true;
		points[5][3]=true;
		points[6][0]=true;
		points[6][1]=true;
		points[6][2]=true;
		points[6][4]=true;
		points[7][0]=true;
		points[7][2]=true;
		points[7][4]=true;
		points[8][0]=true;
		points[8][1]=true;
		points[8][2]=true;
		points[8][4]=true;
		points[9][2]=true;
		points[9][3]=true;
		points[9][4]=true;
		
		int amount = 26;
		
		while(i<max) {
			
			Coordinate click = b.getClick();
			int row = click.getRow();
			int col = click.getCol();
			b.putPeg("LIGHT", row, col);
			i++;
			if (row-6<0 || col-6<0) {
				part6();
			}
			if (b.getFlag()!=points[row-6][col-6]) {
				System.out.println("false");
				
				int res = JOptionPane.showConfirmDialog(null, "You Failed, do you want one more chance", "Fail", JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.YES_OPTION) {
					part6();
				} else {
					fm.startMenu();															 
					return;
				}
				break;
			}else if (i==amount) {
				 
				int res = JOptionPane.showConfirmDialog(null, "You Win, move to next level", "Congratuation", JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.YES_OPTION) {
					part7();
				} else {
					fm.startMenu();												 
					return;
				}

			}else if(b.getFlag()==points[row-6][col-6]) {
				points[row-6][col-6]=false;
			}
			if(!b.getFlag()) {												 
				i--;
			}
			System.out.println(i);
		}
	}
	
	public static void part7() throws InterruptedException, IOException {
		String[][] sB = {{"","","","","0"},{"","","","","2"},{"","","","","3"},{"","","","","3"},{"","","","2","2"},{"","","","2","1"},{"","","","3","1"},{"","","","3","2"},{"","","","","4"},{"","","","","0"},}; 
		String[][] sT = {{"","","","","","","","","",""},{"","","","","","","","","",""},{"","","","","","","2","","",""},{"","","","","2","3","1","1","",""},{"0","0","4","6","3","1","2","3","0","0"},};
		Board b = new Board(16, 16, sB, sT);  // the board size should be at least 7*7
		b.displayMessage("HI!!!!");
		int max = 10000;
		int i = 0;		
		
		int hang =10;
		int lie =10;
		boolean[][] points =new boolean[hang][lie];
		for (int a=0;a<hang;a++) {								//initialize the points  ------make sure they are false at first
			for (int c=0;c<lie;c++) {
				points[a][c]=false;
			}
		}
		points[1][6]=true;
		points[1][7]=true;
		points[2][4]=true;
		points[2][5]=true;
		points[2][6]=true;
		points[3][3]=true;
		points[3][4]=true;
		points[3][5]=true;
		points[4][2]=true;
		points[4][3]=true;
		points[4][5]=true;
		points[4][6]=true;
		points[5][2]=true;
		points[5][3]=true;
		points[5][7]=true;
		points[6][2]=true;
		points[6][3]=true;
		points[6][4]=true;
		points[6][7]=true;
		points[7][2]=true;
		points[7][3]=true;
		points[7][4]=true;
		points[7][6]=true;
		points[7][7]=true;
		points[8][3]=true;
		points[8][4]=true;
		points[8][5]=true;
		points[8][6]=true;
		
		int amount=28;
		
		while(i<max) {
			
			Coordinate click = b.getClick();
			int row = click.getRow();
			int col = click.getCol();
			b.putPeg("LIGHT", row, col);
			i++;
			if (row-6<0 || col-6<0) {
				part7();
			}
			if (b.getFlag()!=points[row-6][col-6]) {
				System.out.println("false");
				
				int res = JOptionPane.showConfirmDialog(null, "You Failed, do you want one more chance", "Fail", JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.YES_OPTION) {
					part7();
				} else {
					fm.startMenu();																 
					return;
				}
				break;
			}else if (i==amount) {
				 
				int res = JOptionPane.showConfirmDialog(null, "You Win, move to next level", "Congratuation", JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.YES_OPTION) {
					part8();
				} else {
					fm.startMenu();												 
					return;
				}

			}else if(b.getFlag()==points[row-6][col-6]) {
				points[row-6][col-6]=false;
			}
			if(!b.getFlag()) {												 
				i--;
			}
			System.out.println(i);
		}
	}
	
	public static void part8() throws InterruptedException, IOException {
		String[][] sB = {{"","","","","0"},{"","","","","0"},{"","","1","1","2"},{"","","","1","2"},{"","","","4","1"},{"","","","2","2"},{"","2","2","1","1"},{"","","1","2","1"},{"","","1","2","1"},{"","","","6","1"},}; 
		String[][] sT = {{"","","","","","","","","",""},{"","","","1","","","","","1",""},{"","","","1","1","1","","","1",""},{"1","2","2","2","2","2","1","2","1",""},{"4","1","1","1","1","2","4","1","1","0"}};
		Board b = new Board(16, 16, sB, sT);  // the board size should be at least 7*7
		b.displayMessage("HI!!!!");
		int max = 10000;
		int i = 0;
		
		int hang =10;
		int lie =10;
		boolean[][] points =new boolean[hang][lie];
		for (int a=0;a<hang;a++) {								//initialize the points  ------make sure they are false at first
			for (int c=0;c<lie;c++) {
				points[a][c]=false;
			}
		}
		points[2][3]=true;
		points[2][5]=true;
		points[2][7]=true;
		points[2][8]=true;
		points[3][0]=true;
		points[3][6]=true;
		points[3][7]=true;
		points[4][2]=true;
		points[4][3]=true;
		points[4][4]=true;
		points[4][5]=true;
		points[4][8]=true;
		points[5][1]=true;
		points[5][2]=true;
		points[5][5]=true;
		points[5][6]=true;
		points[6][0]=true;
		points[6][1]=true;
		points[6][3]=true;
		points[6][4]=true;
		points[6][6]=true;
		points[6][8]=true;
		points[7][0]=true;
		points[7][3]=true;
		points[7][4]=true;
		points[7][6]=true;
		points[8][0]=true;
		points[8][5]=true;
		points[8][6]=true;
		points[8][8]=true;
		points[9][0]=true;
		points[9][1]=true;
		points[9][2]=true;
		points[9][3]=true;
		points[9][4]=true;
		points[9][5]=true;
		points[9][7]=true;
		
		int amount=37;
		
		while(i<max) {
			
			Coordinate click = b.getClick();
			int row = click.getRow();
			int col = click.getCol();
			b.putPeg("LIGHT", row, col);
			i++;
			if (row-6<0 || col-6<0) {
				part8();
			}
			if (b.getFlag()!=points[row-6][col-6]) {
				System.out.println("false");
				
				int res = JOptionPane.showConfirmDialog(null, "You Failed, do you want one more chance", "Fail", JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.YES_OPTION) {
					part8();
				} else {
					fm.startMenu();																 
					return;
				}
				break;
			}else if (i==amount) {
				 
				int res = JOptionPane.showConfirmDialog(null, "You Win, move to next level", "Congratuation", JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.YES_OPTION) {
					part9();
				} else {
					fm.startMenu();												 
					return;
				}

			}else if(b.getFlag()==points[row-6][col-6]) {
				points[row-6][col-6]=false;
			}
			if(!b.getFlag()) {												 
				i--;
			}
			System.out.println(i);
		}
	}
	
	public static void part9() throws InterruptedException, IOException {
		String[][] sB = {{"","","","4","2"},{"","1","1","1","1"},{"","","3","3","2"},{"","","","","7"},{"","","","","2"},{"","1","1","2","1"},{"","","2","1","1"},{"","","1","2","1"},{"","1","1","1","2"},{"","","3","2","1"},}; 
		String[][] sT = {{"","","","","","","","","",""},{"","","","","1","","","","1",""},{"","3","1","","2","","","","1",""},{"2","2","2","2","3","4","2","","1","3"},{"2","1","4","1","1","3","1","4","2","2"}};
		Board b = new Board(16, 16, sB, sT);  // the board size should be at least 7*7
		b.displayMessage("HI!!!!");
		int max = 10000;
		int i = 0;
		
		int hang =10;
		int lie =10;
		boolean[][] points =new boolean[hang][lie];
		for (int a=0;a<hang;a++) {								//initialize the points  ------make sure they are false at first
			for (int c=0;c<lie;c++) {
				points[a][c]=false;
			}
		}
		
		points[0][2]=true;
		points[0][3]=true;
		points[0][4]=true;
		points[0][5]=true;
		points[0][8]=true;
		points[0][9]=true;
		points[1][1]=true;
		points[1][3]=true;
		points[1][5]=true;
		points[1][9]=true;
		points[2][0]=true;
		points[2][1]=true;
		points[2][2]=true;
		points[2][4]=true;
		points[2][5]=true;
		points[2][6]=true;
		points[2][8]=true;
		points[2][9]=true;
		points[3][0]=true;
		points[3][1]=true;
		points[3][2]=true;
		points[3][3]=true;
		points[3][4]=true;
		points[3][5]=true;
		points[3][6]=true;
		points[4][8]=true;
		points[4][9]=true;
		points[5][1]=true;
		points[5][4]=true;
		points[5][6]=true;
		points[5][7]=true;
		points[5][9]=true;
		points[6][1]=true;
		points[6][2]=true;
		points[6][4]=true;
		points[6][7]=true;
		points[7][2]=true;
		points[7][4]=true;
		points[7][5]=true;
		points[7][7]=true;
		points[8][0]=true;
		points[8][2]=true;
		points[8][5]=true;
		points[8][7]=true;
		points[8][8]=true;
		points[9][0]=true;
		points[9][1]=true;
		points[9][2]=true;
		points[9][4]=true;
		points[9][5]=true;
		points[9][8]=true;
		
		int amount=51;
		
		while(i<max) {
			if(!b.getFlag()) {												 
				i--;
			}
			System.out.println(i);
			Coordinate click = b.getClick();
			int row = click.getRow();
			int col = click.getCol();
			b.putPeg("LIGHT", row, col);
			i++;
			if (row-6<0 || col-6<0) {
				part9();
			}
			if (b.getFlag()!=points[row-6][col-6]) {
				System.out.println("false");
				
				int res = JOptionPane.showConfirmDialog(null, "You Failed, do you want one more chance", "Fail", JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.YES_OPTION) {
					part9();
				} else {
					fm.startMenu();														 
					return;
				}
				break;
			}else if (i==amount) {
				 
				int res = JOptionPane.showConfirmDialog(null, "You Win, move to next level", "Congratuation", JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.YES_OPTION) {
					part10();
				} else {
					fm.startMenu();												 
					return;
				}

			}else if(b.getFlag()==points[row-6][col-6]) {
				points[row-6][col-6]=false;
			}
		}
	}
	
	public static void part10() throws InterruptedException, IOException {
		String[][] sB = {{"","","","","0"},{"","","","","0"},{"","","","","0"},{"","","","","0"},{"","","","","0"},{"","","","2","3"},{"","","","1","3"},{"1","1","1","1","1"},{"","1","1","1","3"},{"","1","1","1","1"},{"","","1","1","4"},{"","2","2","1","1"},{"","","3","1","1"},{"","","1","1","1"},{"","","5","1","1"},}; 
		String[][] sT = {{"","","","","","","","","",""},{"","","","","","","","","",""},{"","","","1","","","","","1",""},{"5","2","5","2","5","","1","","1","3"},{"1","1","3","1","1","1","5","6","1","5"}};
		Board b = new Board(21, 16, sB, sT);  // the board size should be at least 7*7
		b.displayMessage("HI!!!!");
		int max = 10000;
		int i = 0;	
		
		int hang =15;
		int lie =10;
		boolean[][] points =new boolean[hang][lie];
		for (int a=0;a<hang;a++) {								//initialize the points  ------make sure they are false at first
			for (int c=0;c<lie;c++) {
				points[a][c]=false;
			}
		}
		
		points[5][2]=true;
		points[5][3]=true;
		points[5][5]=true;
		points[5][6]=true;
		points[5][7]=true;
		points[6][2]=true;
		points[6][7]=true;
		points[6][8]=true;
		points[6][9]=true;
		points[7][0]=true;
		points[7][2]=true;
		points[7][4]=true;
		points[7][7]=true;
		points[7][9]=true;
		points[8][0]=true;
		points[8][2]=true;
		points[8][4]=true;
		points[8][7]=true;
		points[8][8]=true;
		points[8][9]=true;
		points[9][0]=true;
		points[9][2]=true;
		points[9][4]=true;
		points[9][7]=true;
		points[10][0]=true;
		points[10][4]=true;
		points[10][6]=true;
		points[10][7]=true;
		points[10][8]=true;
		points[10][9]=true;
		points[11][0]=true;
		points[11][1]=true;
		points[11][3]=true;
		points[11][4]=true;
		points[11][6]=true;
		points[11][9]=true;
		points[12][1]=true;
		points[12][2]=true;
		points[12][3]=true;
		points[12][6]=true;
		points[12][9]=true;
		points[13][2]=true;
		points[13][6]=true;
		points[13][9]=true;
		points[14][0]=true;
		points[14][1]=true;
		points[14][2]=true;
		points[14][3]=true;
		points[14][4]=true;
		points[14][6]=true;
		points[14][9]=true;
		
		int amount =51;
		
		while(i<max) {
			if(!b.getFlag()) {												 
				i--;
			}
			System.out.println(i);
			Coordinate click = b.getClick();
			int row = click.getRow();
			int col = click.getCol();
			b.putPeg("LIGHT", row, col);
			i++;
			if (row-6<0 || col-6<0) {
				part10();
			}
			System.out.println(row+" "+col);
			if (b.getFlag()!=points[row-6][col-6]) {
				System.out.println("false");
				
				int res = JOptionPane.showConfirmDialog(null, "You Failed, do you want one more chance", "Fail", JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.YES_OPTION) {
					part10();
				} else {
					fm.startMenu();															 
					return;
				}
				break;
			}else if (i==amount) {
				 
				int res = JOptionPane.showConfirmDialog(null, "You Win, move to next level", "Congratuation", JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.YES_OPTION) {
					b.setVisible(false);
				} else {
					finalFrame();											
					return;
				}

			}else if(b.getFlag()==points[row-6][col-6]) {
				points[row-6][col-6]=false;
			}
		}
	}
	
	/**this method is the final frame for people who pass the level 10
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void finalFrame() throws InterruptedException, IOException {
		// this is a way to congratulate the user who pass the level 10 
		// user can choose to back to the start menu or exit directly
		JFrame frame=new JFrame();
		JLabel lable=new JLabel(new Main().finalgif);
		frame.add(lable);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		Thread.sleep(3000);
		int res = JOptionPane.showConfirmDialog(null, "Congratulation, you finish all of our games, click yes: return to start menu, click no: exit this game", "Fail", JOptionPane.YES_NO_OPTION);
		if (res == JOptionPane.YES_OPTION) {
			fm.startMenu();	
		} else {
			System.exit(0);
			return;
		}
	}
}
	
	
	
	
	
