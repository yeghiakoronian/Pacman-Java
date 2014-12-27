import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.image.*;
import javax.swing.JApplet;
import java.applet.*;
import java.util.*;
import java.math.*;
import javax.swing.JOptionPane;  
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import java.io.*;
import javax.swing.*;
import sun.audio.*;
import java.net.*;

//Read more: http://mrbool.com/how-to-use-image-and-sound-in-an-java-applet/22500#ixzz2nm6f9FIG
public class Pacman extends Applet implements MouseListener, Runnable
{
		Image GreenPacMan , Apple , PacMan ;
		int   GreenPacMan_Movement,GreenPacMan_x, GreenPacMan_y, Apple_x, Apple_y,PacMan_Movement, PacMan_x, PacMan_y,Mouse_x,Mouse_y,Rect_x,Rect_y ;
		int AppleTimer,Counter;
		int   RectWidth,RectHeigth;
		Thread t=new Thread(this, "t1");
		int GreenPacMan_Score=0,PacMan_Score=0 ;
		boolean RectClicked,Apple_Exists;
		AudioClip PacManEat;
		
		
		
		
		
		
		
		
		public boolean NotTouchWallxplus(int PacMan_x)
		{
			if((PacMan_x+1)<=399 )
				return true;
				return false;	
		}
		
		public boolean NotTouchWallxminus(int PacMan_x)
		{
			if((PacMan_x-1)>=10 )
				return true;
				return false;	
		}
		
		public boolean NotTouchWallyplus(int PacMan_y)
		{
			if((PacMan_y+1)<=414 )
				return true;
				return false;	
		}
		
		public boolean NotTouchWallyminus(int PacMan_y)
		{
			if((PacMan_y-1)>=40 )
				return true;
				return false;	
		}
		
		
		public  boolean Eaten()
		{
			if( PacMan_x>=Apple_x && PacMan_x<=(Apple_x+20) && PacMan_y<=(Apple_y+20) && PacMan_y>=(Apple_y) )
				{ PacMan_Score++;Apple_Exists=false; return true;}
			if( PacMan_x>=Apple_x && PacMan_x<=(Apple_x+20) && (PacMan_y+20)<=(Apple_y+20) && (PacMan_y+20)>=(Apple_y) )
				{ PacMan_Score++;Apple_Exists=false;  return true;}
			if( (PacMan_x+20)>=Apple_x && (PacMan_x+20)<=(Apple_x+20) && PacMan_y<=(Apple_y+20) && PacMan_y>=(Apple_y) )
				{ PacMan_Score++;Apple_Exists=false;  return true;}
			if( (PacMan_x+20)>=Apple_x && (PacMan_x+20)<=(Apple_x+20) && (PacMan_y+20)<=(Apple_y+20) && (PacMan_y+20)>=(Apple_y) )
				{ PacMan_Score++;Apple_Exists=false;  return true;}
			if(PacMan_x==Apple_x && PacMan_y==Apple_y)
				{ PacMan_Score++;Apple_Exists=false;repaint();   return true;}

			if( GreenPacMan_x>=Apple_x && GreenPacMan_x<=(Apple_x+20) && GreenPacMan_y<=(Apple_y+20) && GreenPacMan_y>=(Apple_y) )
				{ GreenPacMan_Score++;Apple_Exists=false;  return true;}
			if( GreenPacMan_x>=Apple_x && GreenPacMan_x<=(Apple_x+20) && (GreenPacMan_y+20)<=(Apple_y+20) && (GreenPacMan_y+20)>=(Apple_y) )
				{ GreenPacMan_Score++; Apple_Exists=false; return true;}
			if( (GreenPacMan_x+20)>=Apple_x && (GreenPacMan_x+20)<=(Apple_x+20) && GreenPacMan_y<=(Apple_y+20) && GreenPacMan_y>=(Apple_y) )
				{ GreenPacMan_Score++;Apple_Exists=false;  return true;}
			if( (GreenPacMan_x+20)>=Apple_x && (GreenPacMan_x+20)<=(Apple_x+20) && (GreenPacMan_y+20)<=(Apple_y+20) && (GreenPacMan_y+20)>=(Apple_y) )
				{ GreenPacMan_Score++;Apple_Exists=false;  return true;}
			if(GreenPacMan_x==Apple_x && GreenPacMan_y==Apple_y)
				{ GreenPacMan_Score++;Apple_Exists=false;   return true;}

			return false;
		}
		public void init()
		{
			 System.out.println("init");
			 PacManEat= getAudioClip(getDocumentBase(),"pacman_eatfruit.au");
			
			
			PacMan = getImage(getCodeBase(), "Pacman.png");
			GreenPacMan = getImage(getCodeBase(), "GreenPacman.png");
			Apple=getImage(getCodeBase(), "Apple.png");
			resize(750,900);
			Rect_x=10;
			Rect_y=40; 
		    RectWidth=415;
			RectHeigth=400;
			PacMan_x = 10;
			PacMan_y = 40;
			PacMan_Movement=1;
			//Pacman moving right
			//PacMan_MovementR=1;
			//PacMan_MovementL=0;
			GreenPacMan_x = 400;
			GreenPacMan_y = 415;
			GreenPacMan_Score=0;
			GreenPacMan_Movement=-1;
			//GreenPacman moving right
			//GreenPacMan_MovementR=0;
			//GreenPacMan_MovementL=1;
			PacMan_Score=0 ;
			// 	Pacman  10-x-400
			//  Pacman  40-y-415
			do{ Apple_x = (int)(Math.random()*400+7);}while(Apple_x<10 || Apple_x>400);
			do{ Apple_y = (int)(Math.random()*400+37);}while(Apple_y<40 || Apple_y>400);
			Apple_Exists=true;
			Counter=0;
			//RectClicked=false;
			AppleTimer=1000;
			this.requestFocus();
			//addKeyListener(this);
			addMouseListener(this);
			
	
	
		}
 

		public void paint( Graphics g)
		{
			 System.out.println("paint");
			g.clearRect(0, 0, this.getWidth(), this.getHeight());
			this.requestFocusInWindow();
			g.setColor (Color.black);
			g.drawRect(Rect_x,Rect_y,RectWidth,RectHeigth); 
			g.drawImage(PacMan, PacMan_x, PacMan_y, 25, 25, null);  
			g.drawImage(GreenPacMan, GreenPacMan_x, GreenPacMan_y, 25, 25, null); 
			g.drawImage(Apple, Apple_x, Apple_y, 25, 25, null);
	

			Font f = new Font("serif", Font.BOLD, 18);
			g.setFont(f);
			String PacManScore = "PacMan Score: " + PacMan_Score;
			g.drawString (PacManScore, 22,500);
	
			String GreenPacManScore = "GreenPacMan Score: " + GreenPacMan_Score;
			g.drawString (GreenPacManScore, 230,500);
					if(RectClicked==false)
					{
						if(Apple_Exists==true)
						{
							// track x of apple then y
							if(PacMan_x<Apple_x ){PacMan_x=PacMan_x+1;PacMan_Movement=1;}
							else if(PacMan_x>Apple_x ){PacMan_x=PacMan_x-1;PacMan_Movement=-1;}
							else if(PacMan_y<Apple_y ){PacMan_y=PacMan_y+1;PacMan_Movement=2;}
							else if(PacMan_y>Apple_y ){PacMan_y=PacMan_y-1;PacMan_Movement=-2;}
							
								
							//if(GreenPacMan_x<Apple_x ){GreenPacMan_x=GreenPacMan_x+1;GreenPacMan_Movement=1;}
							//else if(GreenPacMan_x>Apple_x ){GreenPacMan_x=GreenPacMan_x-1;GreenPacMan_Movement=-1;}
							//else if(GreenPacMan_y<Apple_y ){GreenPacMan_y=GreenPacMan_y+1;GreenPacMan_Movement=2;}
							//else if(GreenPacMan_y>Apple_y){GreenPacMan_y=GreenPacMan_y-1;GreenPacMan_Movement=-2;}
						   
						   // track y of apple then x  , comment out  and  remove comments from above to make greenpacman track like yellow one
						   if(GreenPacMan_y<Apple_y ){GreenPacMan_y=GreenPacMan_y+1;GreenPacMan_Movement=2;}
						   else if(GreenPacMan_y>Apple_y){GreenPacMan_y=GreenPacMan_y-1;GreenPacMan_Movement=-2;}
						   else if(GreenPacMan_x<Apple_x ){GreenPacMan_x=GreenPacMan_x+1;GreenPacMan_Movement=1;}
						   else if(GreenPacMan_x>Apple_x ){GreenPacMan_x=GreenPacMan_x-1;GreenPacMan_Movement=-1;}
						   
							if (Eaten()){PacManEat.play();Apple_x=500;Apple_y=600; repaint();}
							try{ Thread.sleep(2);} catch(InterruptedException e) {}
							repaint();
						}

						else
						{
							Counter=Counter+5;
							if(Counter==AppleTimer)
							{
								Apple_Exists=true;
								do{ Apple_x = (int)(Math.random()*400+7);}while(Apple_x<10 || Apple_x>400);
								do{ Apple_y = (int)(Math.random()*400+37);}while(Apple_y<40 || Apple_y>400);
								Counter=0;
							}
							if(!NotTouchWallxplus(PacMan_x) || !NotTouchWallxminus(PacMan_x) || !NotTouchWallyplus(PacMan_y) || !NotTouchWallyminus(PacMan_y))
							   PacMan_Movement=-1*PacMan_Movement;
								
								
							if(PacMan_Movement==1)  PacMan_x=PacMan_x+1;
							if(PacMan_Movement==-1) PacMan_x=PacMan_x-1;
							if(PacMan_Movement==2)  PacMan_y=PacMan_y+1;
							if(PacMan_Movement==-2) PacMan_y=PacMan_y-1;	
								
							if(!NotTouchWallxplus(GreenPacMan_x) || !NotTouchWallxminus(GreenPacMan_x) || !NotTouchWallyplus(GreenPacMan_y) || !NotTouchWallyminus(GreenPacMan_y))
								GreenPacMan_Movement=-1*GreenPacMan_Movement;
								
							if(GreenPacMan_Movement==1)  GreenPacMan_x=GreenPacMan_x+1;
							if(GreenPacMan_Movement==-1) GreenPacMan_x=GreenPacMan_x-1;
							if(GreenPacMan_Movement==2)  GreenPacMan_y=GreenPacMan_y+1;
							if(GreenPacMan_Movement==-2) GreenPacMan_y=GreenPacMan_y-1;				
								
						
							try{ Thread.sleep(2);} catch(InterruptedException e) {}
							repaint();
						}
					}	
				else if(RectClicked==false)
				{
					
					try
					{ 
						t.wait();
					} 
					catch(InterruptedException e) {}
				}	
			
			try
			{ 
				t.sleep(2);
			} 
			catch(InterruptedException e) {}
			repaint();
		}
		
		
 public void mouseClicked(MouseEvent e)
 {
	 System.out.println("mouseclicked");
	Mouse_x = e.getX(); 
	Mouse_y = e.getY();
	if (Mouse_x > Rect_x && Mouse_x < Rect_x+RectWidth && Mouse_y >Rect_y &&  Mouse_y < Rect_y+RectHeigth)  
						
		RectClicked = false; 
	else
		RectClicked = true; 
    
 }
 public void mousePressed(MouseEvent e)
 {}
 public void mouseReleased(MouseEvent e)
 {}
 public void mouseEntered(MouseEvent e)
 {}
 public void mouseExited(MouseEvent e)
 {}
 public void mouseDragged(MouseEvent e)
 {}
 public void mouseMoved(MouseEvent e)
 {}

 public void start()
 {
	 System.out.println("start");
	AppletContext ac = getAppletContext(); 
	URL url = getCodeBase(); 
	try 
	{ 
		AudioClip PacManIntro = ac.getAudioClip(new URL(url+"pacman_beginning.wav")); 
		AudioClip PacManMove = ac.getAudioClip(new URL(url+"pacman_chomp.wav"));
		AudioClip PacManEat = ac.getAudioClip(new URL(url+"pacman_eatfruit.wav"));
		PacManIntro.play(); 
		PacManMove.loop();
		
	} catch(MalformedURLException e) { showStatus("URL Not Found"); }
 }

 public void stop(){ System.out.println("stop");}
 public void run(){ System.out.println("run");} 
 
 }
