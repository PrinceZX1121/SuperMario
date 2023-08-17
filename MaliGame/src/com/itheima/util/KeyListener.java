package com.itheima.util;

import com.itheima.role.Boom;
import com.itheima.ui.GameFrame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;


//键盘按下监听类
public class KeyListener extends KeyAdapter{

	public static boolean right=false;
	public static boolean left=false;
	public static boolean up=false;
	public static boolean bullet=false;
	// 接收到了当前主界面：游戏界面
	/*
	public GameFrame gf;

	public KeyListener(GameFrame gf) {
		this.gf = gf;
	}
	*/
	//键盘监听
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		//Z 90 x 88
		switch (code) {
			//按右键
			case 39 -> right = true; // 信号位
			//按左键
			case 37 -> left = true;
			//按跳键
			case 88 -> up = true;
			//子弹键
			case 90 -> bullet = true;
		}
	}

	/*
	//添加子弹
	public void addBoom() {	
		Boom b = new Boom(gf.mario.x,gf.mario.y+5,10);
		if(gf.mario.left) b.speed=-2;
		if(gf.mario.right) b.speed=2;
		gf.boomList.add(b);
	}
*/
	//键盘释放监听
	@Override
	public void keyReleased(KeyEvent e) {
		int code=e.getKeyCode();
		switch(code)
		{
			case 39 -> right = false; // 信号位
			//按左键
			case 37 -> left = false;
			//向跳键
			case 88 -> up = false;
			case 90 -> bullet = false;
		}
		/*
		if(code==39){
			right=false;
			//gf.mario.img=new ImageIcon("image/mari1.png").getImage();
		}
		if(code==37){
			left=false;
			//.mario.img=new ImageIcon("image/mari_left1.png").getImage();
		}
		if(code==38){
			up=false;
		}

		 */
	}

}
