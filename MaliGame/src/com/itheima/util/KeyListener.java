package com.itheima.util;

import com.itheima.role.Boom;
import com.itheima.ui.GameFrame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;


//���̰��¼�����
public class KeyListener extends KeyAdapter{

	public static boolean right=false;
	public static boolean left=false;
	public static boolean up=false;
	public static boolean bullet=false;
	// ���յ��˵�ǰ�����棺��Ϸ����
	/*
	public GameFrame gf;

	public KeyListener(GameFrame gf) {
		this.gf = gf;
	}
	*/
	//���̼���
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		//Z 90 x 88
		switch (code) {
			//���Ҽ�
			case 39 -> right = true; // �ź�λ
			//�����
			case 37 -> left = true;
			//������
			case 88 -> up = true;
			//�ӵ���
			case 90 -> bullet = true;
		}
	}

	/*
	//����ӵ�
	public void addBoom() {	
		Boom b = new Boom(gf.mario.x,gf.mario.y+5,10);
		if(gf.mario.left) b.speed=-2;
		if(gf.mario.right) b.speed=2;
		gf.boomList.add(b);
	}
*/
	//�����ͷż���
	@Override
	public void keyReleased(KeyEvent e) {
		int code=e.getKeyCode();
		switch(code)
		{
			case 39 -> right = false; // �ź�λ
			//�����
			case 37 -> left = false;
			//������
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
