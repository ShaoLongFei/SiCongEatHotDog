package com.feiji.game;



import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.feiji.util.Bullet;
import com.feiji.util.GameUtil;
import com.feiji.util.Plane;

import javazoom.jl.player.Player;



public class FeiJiGame extends Frame{

	public static int width=900,height=900;

	Image bg = GameUtil.getImage("images/bg.jpg");
	Plane plane = new Plane("images/sicong.png",200,200);
	LinkedList<Bullet> bulletList = new LinkedList<>();
	Date starTime;
	Date endTime;
	PaintThread paintThread;
	MusicPlayer musicPlayer=new MusicPlayer();
	ExecutorService executorService=Executors.newCachedThreadPool();

	boolean gameState = true;

	public void loadGame(){
		starTime = new Date();
		endTime = new Date();
		launchFrame();
	}
	

	public void launchFrame(){
		setSize(width,height);
		setResizable(false);
		setLocation(200,20);
		setVisible(true);
		addKeyListener(new KeyMoniter());
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		for(int i=0;i<15;i++){
			Bullet bullet = new Bullet("images/hotdog.png");
			bulletList.add(bullet);
 		}
		paintThread=new PaintThread();
		paintThread.start();
	}

	@Override
	public void paint(Graphics graphics) {
		graphics.drawImage(bg, 0, 0, null);
		plane.draw(graphics);
		endTime = new Date();
		if(gameState){
			for(int i=0;i<bulletList.size();i++){
				Bullet bullet=bulletList.get(i);
				bullet.draw(graphics);

				Rectangle bulletRectangle = new Rectangle((int)bullet.x,(int)bullet.y,bullet.width,bullet.height);
				Rectangle planeRectangle = new Rectangle(plane.x,plane.y,plane.width,plane.height);
				boolean collide= bulletRectangle.intersects(planeRectangle);

				if(collide){
					if (bulletList.size()!=0){
						executorService.execute(musicPlayer);
						bulletList.remove(i);
						if (bulletList.size()==0){
							gameState = false;
						}
					}
				}
			}
		}else {
			endTime = new Date();
			gameOver(graphics);
			paintThread.interrupt();
		}

		int count_time = (int)(endTime.getTime()-starTime.getTime())/1000;
		printInfo(graphics,"你已经吃了"+count_time+"秒",20,750,50);
	}

	private void gameOver(Graphics graphics) {
		printInfo(graphics,"GAME OVER",80,270,300);
		int survivalTime = (int)(endTime.getTime()-starTime.getTime())/1000;
		printInfo(graphics,"吃热狗时间："+survivalTime+"秒",40,300,400);

		switch(survivalTime/10){
			case 1:
				printInfo(graphics,"独孤求败",50,350,500);
				break;
			case 2:
				printInfo(graphics,"登堂入室",50,350,500);
				break;
			case 3:
				printInfo(graphics,"小有成就",50,350,500);
				break;
			default:
				printInfo(graphics,"初入江湖",50,350,500);
				break;
		}
		paintThread.interrupt();
	}

	Image ImageBuffer = null;  
	Graphics GraImage = null;
	@Override
	public void update(Graphics g){
	    ImageBuffer = createImage(this.getWidth(), this.getHeight());
	    GraImage = ImageBuffer.getGraphics();
	    paint(GraImage);
	    GraImage.dispose();
	    g.drawImage(ImageBuffer, 0, 0, this);
	}  

	class KeyMoniter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			plane.KeyPressedControlDirection(e);
		}
		@Override
		public void keyReleased(KeyEvent e) {
			plane.KeyRelasedControlDirection(e);
		}	
	}

	public void printInfo(Graphics g,String message,int size,int x,int y){
		g.setColor(Color.white);
		Font f = new Font("宋体",Font.BOLD,size);
		g.setFont(f);
		g.drawString(message, x,y);
	}
	

	class PaintThread extends Thread {
		@Override
		public void run() {
			while(!Thread.currentThread().isInterrupted()){
				repaint();
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}	
	}

	class MusicPlayer implements Runnable{
		@Override
		public void run() {
			try {
				new Player(new FileInputStream(FeiJiGame.class.getClassLoader().getResource("raw/music.mp3").getPath().substring(1))).play();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		FeiJiGame game = new FeiJiGame();
		game.loadGame();
	}
}
