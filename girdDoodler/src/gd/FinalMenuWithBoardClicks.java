package gd;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.Finishings;
import javax.swing.*;

public class FinalMenuWithBoardClicks extends Main {
	static Board board;
	static JButton btnStart;
	static JButton btnHighScores, btnInstructions;
	static Coordinate start, finish;
	static Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	static int width = (int) dimension.getWidth();
	static int height = (int) dimension.getHeight();
	private static int index = 0;
	
	/**The major method that will be used in other class to recreate main menu when need to return to main menu
	 * 
	 * @throws IOException required by eclipse
	 */
	
	public static void main(String[] args) throws IOException {
		startMenu();
	}
	public static void startMenu() throws IOException {
		 
		 
		// setup main JFrame with Menu Options
		JFrame fr = new JFrame("Grid Doodler");
		fr.setSize(dimension);
		fr.setResizable(false);
		fr.setLocationRelativeTo(null);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	    int x = (int) (width/2);
	    int y = (int) (height/2);
		final Color YEL = new Color(240, 226, 165);

		// setup buttons and image background
		JPanel panel1;
		panel1 = new JPanel();


		btnStart = new JButton("Start");
		
		btnStart.setBounds(x-150, y*8/9, 300, 60);
		
		btnStart.setBackground( new Color(202,204,206));
		btnStart.setFont(new Font("SansSerif", Font.PLAIN, 25));
		panel1.add(btnStart);

		btnHighScores = new JButton("Exit");
		btnHighScores.setBounds(x-150, y*8/9+y*2/5, 300, 60);
		btnHighScores.setBackground( new Color(202,204,206));
		btnHighScores.setFont(new Font("SansSerif", Font.PLAIN, 25));
		panel1.add(btnHighScores);
		btnInstructions = new JButton("Instructions");
		btnInstructions.setBounds(x-150, y*8/9+y/5, 300, 60); 
		btnInstructions.setBackground( new Color(202,204,206));
		btnInstructions.setFont(new Font("SansSerif", Font.PLAIN, 25));
		panel1.add(btnInstructions);
		
		fr.add(panel1);

		// setup what happens if each button is clicked
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					@Override
					public void run() {

						 JPanel level = new JPanel();
						 level.setLayout(null);
						 
						 level.setBackground(new Color(244,255,255));
						 
                        for (int i = 1; i<=10; i++) {
                        	try {
								setButton(i,fr,level);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                        }
                         fr.remove(panel1);
                         fr.add(level);

                         fr.validate();
						 
					}
				}).start();

			}
		});
		btnHighScores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					System.exit(0);
			}
		});
		btnInstructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						JPanel jp = new JPanel();
						jp.setLayout(null);
						jp.setBackground(new Color(244,255,255));
						ImageDemo(jp);
						System.out.println("Operating?");
						fr.remove(panel1);
						fr.add(jp);
						fr.validate();

						 
					}
				}).start();

			}
		});

		panel1.setSize(width,height);
		panel1.setLayout(null); 
        ImageIcon ii2 = new ImageIcon("Heading.png");
        int scaledWidth1 = width/2;
		Image n2 = ii2.getImage();
		BufferedImage bimg = ImageIO.read(new File("Heading.png"));
		int iwidth1          = bimg.getWidth();
		int iheight1         = bimg.getHeight();
		double ratio = ((double)scaledWidth1)/iwidth1;
		int scaledHeight1 = (int) (ratio*iheight1);
		n2=getScaledImage(n2, scaledWidth1, scaledHeight1);
		ii2 = new ImageIcon(n2);
		JLabel jl2 = new JLabel(ii2);
        jl2.setBounds(x-scaledWidth1/2, height/16, scaledWidth1, scaledHeight1);
        panel1.add(jl2);
		
		ImageIcon ii = new ImageIcon("back.png");
		Image n = ii.getImage();
		n=getScaledImage(n, width, height);
		ii = new ImageIcon(n);
		JLabel jl = new JLabel(ii);
        jl.setBounds(0, 0, width, height);
        panel1.add(jl);
        


		fr.setVisible(true);
	}
	
	
	/**
	 * Method that is responsible for creating and formating level buttons in level choosing frame
	 * @param num the number of level
	 * @param f the JFrame the buttons are at
	 * @param p the new panel for levels
	 * @throws IOException required by eclipse
	 */
	 public static void setButton(int num, JFrame f, JPanel p) throws IOException {

		 Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		 int width = (int) dimension.getWidth();
		 int height = (int) dimension.getHeight();
		 int x = (int) (dimension.getWidth());
		 int y = (int) (dimension.getHeight());
		int scaledWidth = x/2+150+x/2-x/4;
         JButton bu = new JButton("level "+num);
         bu.setFont(new Font("Dialog", Font.PLAIN, 20));
         int xcoordinate;
         int ycoordinate;
         int buWidth=width/6;
         int buHeight=30;
         if(num>5) {
        	if(num==10) {
        		xcoordinate =  50+((width-100)/5)*4;
        	}
        	else{
        		xcoordinate =  50+((width-100)/5)*(num%5-1);
        	}
        	xcoordinate = xcoordinate+ ((width-100)/5 - buWidth)/2;
         	ycoordinate = (height*4)/5;
         	bu.setBounds(xcoordinate, ycoordinate, buWidth, buHeight);
         	
            String name = "level"+num+".jpg";
            
            ImageIcon i = new ImageIcon(name);

            Image n = i.getImage();
            n= getScaledImage(n, buWidth, buWidth);
            i = new ImageIcon(n);
            JLabel jl = new JLabel(i);
            jl.setBounds(xcoordinate, ycoordinate-buWidth-20, buWidth, buWidth);
            p.add(jl);

         }
         if(num<=5) {
        	ycoordinate = height/3;
        	xcoordinate =  50+((width-100)/5)*(num%6-1);
        	xcoordinate = xcoordinate+ ((width-100)/5 - buWidth)/2;
        	bu.setBounds(xcoordinate, ycoordinate, buWidth, buHeight);
            
            String name = "level"+num+".jpg";
            
            ImageIcon i = new ImageIcon(name);
            Image n = i.getImage();
            n= getScaledImage(n, buWidth, buWidth);
            i = new ImageIcon(n);
            JLabel jl = new JLabel(i);
            jl.setBounds(xcoordinate, ycoordinate-buWidth-20, buWidth, buWidth);

            p.add(jl);
            
         }
         bu.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                     new Thread(new Runnable() {
                             @Override
                             public void run() {
                                     try {
										choose(num);
									} catch (InterruptedException | IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} 
                             }
                     }).start();

             }
         });
         
         p.add(bu);


         
	 }

	 
	/**
	 * From https://stackoverflow.com/questions/6714045/how-to-resize-jlabel-imageicon
	 * @param srcImg  image that will be scaled
	 * @param w the width of the image after conversion
	 * @param h the height of the image after conversion
	 * @return altered image
	 */
	private static Image getScaledImage(Image srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}
	
	/** this whole method is for instruction part of the start menu; create a panel for instruction
	 * 
	 * @param jp the JPanel the instruction will be displayed
	 */
	public static void ImageDemo (JPanel jp){
		ImageIcon[] images = { new ImageIcon("step1.jpg"), new ImageIcon("step2.jpg"),
				new ImageIcon("step3.jpg"), new ImageIcon("step4.jpg") };
		JButton jb1,jb2;
		JLabel jl;
	// index is 0 at first which is the first picture
			
			ImageIcon place = images[index];
			Image n = place.getImage();
            n= getScaledImage(n, height-350, height-350);
            place = new ImageIcon(n);
			jl = new JLabel(place);
			jl.setBounds(width/3, 100, height-350, height-350);

			int midWidth = 110;
			jb1 = new JButton("<- First step");
			jb1.setFont(new Font("SansSerif", Font.PLAIN, 20));
			jb1.setBounds(width/2-160-midWidth/2, height-200, 150, 30);
			jb2 = new JButton("Next ->");
			jb2.setFont(new Font("SansSerif", Font.PLAIN, 20));
			jb2.setBounds(width/2-midWidth/2, height-200, midWidth, 30);
			
			jp.add(jb1);
			jp.add(jb2);
			jp.add(jl);


			// when user click "previous" button this method will go back to the previous picture
			// and make sure that for the first picture, it will keep the same 

			jb1.addActionListener(new ActionListener() {


				int index;
				public void actionPerformed(ActionEvent e) {
					index--;
					if(index<=0){
						index = 0;
					}
					if(index>=images.length-1){
						index = images.length-1;
					}
					ImageIcon place = images[index];
					Image n = place.getImage();
		            n= getScaledImage(n, height-350, height-350);
		            place = new ImageIcon(n);
					jl.setIcon(place);
				}
			});
			// vice versa to the previous one , this is for teh button "next" and aim to point the next picture
			jb2.addActionListener(new ActionListener() {


				int index;
				public void actionPerformed(ActionEvent e) {
					index++;
					if(index<=0){
						index = 0;
					}
					if(index>images.length-1){
						index = 0;
					}
					ImageIcon place = images[index];
					Image n = place.getImage();
		            n= getScaledImage(n, height-350, height-350);
		            place = new ImageIcon(n);
					jl.setIcon(place);
				}
			});
			
			//Return to main menu
			JButton bu =new JButton("Return to Main Menu");
			bu.setFont(new Font("SansSerif", Font.PLAIN, 20));
			bu.setBounds(width/2+midWidth/2+10,height-200,220,30);
			bu.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					FinalMenuWithBoardClicks fm=new FinalMenuWithBoardClicks();
					try {
						fm.startMenu();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});		
			jp.add(bu);
			bu.setVisible(true);

		}

}





