package com.feiji.util;

import com.feiji.game.FeiJiGame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

public class Plane {
	
	boolean left,right,down,up;
	
	public int x,y,width,height;
	
	Image img ;

	public void draw(Graphics g){
			g.drawImage(img, x, y, null);
			move();
	}

	public Plane(String img_path,int x, int y){
		this.img = GameUtil.getImage(img_path);
		this.x = x;
		this.y = y;
		width = img.getWidth(null);
		height = img.getWidth(null);
	}

	public void move(){
		if(left&&x>=10){
			x -= 10;
		}		
		if(up&&y>=30){
			y -= 10;
		}
		if(right&&x<=FeiJiGame.width-60){
			x += 10;
		}
		if(down&&y<=FeiJiGame.height -60){
			y += 10;
		}
	}

	public void KeyPressedControlDirection(KeyEvent e){
		int key_code = e.getKeyCode();
		if(key_code == 37){
			left = true;
		}
		if(key_code == 38){
			up = true;
		}
		if(key_code == 39){
			right = true;
		}
		if(key_code == 40){
			down = true;
		}
	}

	public void KeyRelasedControlDirection(KeyEvent e){
		int key_code = e.getKeyCode();
		if(key_code == 37){
			left = false;
		}
		if(key_code == 38){
			up =false;
		}
		if(key_code == 39){
			right = false;
		}
		if(key_code == 40){
			down = false;
		}
	}

}
