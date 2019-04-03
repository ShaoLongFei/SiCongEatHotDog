package com.feiji.util;


import com.feiji.game.FeiJiGame;

import java.awt.*;
import java.util.Random;


public class Bullet {

	double speed=15;
	double degree;

	public double x,y;
	public int width,height;
	Image img;

	public Bullet(String img_path){
		img=GameUtil.getImage(img_path);
		degree = Math.random()*Math.PI*2;
		x=FeiJiGame.width/2;
		y=FeiJiGame.height/2;
		width = 10;
		height = 10;
	}

	public void draw(Graphics g){
		g.drawImage(img, (int)x, (int)y, null);
		x += speed*Math.cos(degree);
		y += speed*Math.sin(degree);

		if(x>FeiJiGame.width-width||x<width){
			degree=Math.PI-degree;
		}
		if(y>FeiJiGame.height-height||y<height){
			degree=-degree;
		}
	}

}
