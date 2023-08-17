package com.itheima.entity;

import com.itheima.MainObject;
import com.itheima.block.Air;
import com.itheima.util.ImageCache;
import com.itheima.util.KeyListener;
import com.itheima.util.Map;

import javax.swing.*;

enum State
{
    WALK, STAND, ALOFT, TURN;
}
public class Mario extends MovableEntity
{
    public State state;
    public double xTargetSpeed;      //Ŀ���ٶȣ�����˵ʲô����Ҳ��������ô����µ�Ŀ���ٶ�Ϊ0
    public boolean jumpFlag;         //��Ծ��־�������������Ծ��
    public boolean jumpAllowed;      //������Ծ����ֹ�����Ծ
    public double minHeight;
    public double maxHeight;
    public double jumpedHeight;
    public double xAcMaxSpeed;          //��������е�����ٶȣ���ס���ټ�
    public Mario()
    {
        image = ImageCache.marioStandRightImage;
        face = Direction.RIGHT;
        xInMap = 32.0;
        yInMap = 416.0;
        width = 32;
        height = 32;
        xSpeed = 0.0;
        xAcAcceleration = 0.5;
        xDeAcceleration = 0.25;
        xAcceleration = 0.0;
        xMaxSpeed = 4.0;
        xAcMaxSpeed = 6.0;
        ySpeed = 0.0;
        yAcceleration = 0.5;         //�������ٶ�
        yMaxSpeed = 6.0;
        jumpFlag = false;
        jumpAllowed = true;
        minHeight = 16.0;
        maxHeight = 112.0;
        jumpedHeight = 0.0;
        state = State.STAND;
    }

    //���µ���ײ���
    private boolean collisionDetectionDown()
    {
        //��ʱ������������������һ�������
        if(!(Map.mapBlockInfo[(int) xInMap / 32][1 + (int) yInMap / 32] instanceof Air))
            return true;
        else return !(Map.mapBlockInfo[(int) (xInMap + 31.0) / 32][1 + (int) yInMap / 32] instanceof Air);
    }

    //�����ܲ�������ͼ
    private void changeRunImage()
    {
        int walkCount = (int)(MainObject.frameCount/3)%3;
        switch (walkCount) {
            case 0 -> changeImage(ImageCache.marioRunImagePair1);
            case 1 -> changeImage(ImageCache.marioRunImagePair2);
            case 2 -> changeImage(ImageCache.marioRunImagePair3);
        }
    }

    //���������״̬��Ϣ
    private void stateUpdate()
    {
        if(!collisionDetectionDown())
            state = State.ALOFT;
        else if(xSpeed == 0.0 && xAcceleration == 0.0)
            state = State.STAND;
        else if(Math.abs(xAcceleration) == xAcAcceleration && xAcceleration * xSpeed < 0.0)
            state = State.TURN;
        else
            state = State.WALK;
    }
    private void imageUpdate()
    {
        if(jumpFlag)
            changeImage(ImageCache.marioJumpImagePair);
        switch (state) {
            case STAND -> changeImage(ImageCache.marioStandImagePair);
            case WALK -> changeRunImage();
            case TURN -> changeImage(ImageCache.marioTurnImagePair);
        }
    }
    private void changeFace()
    {
        switch(face)
        {
            case LEFT -> face = Direction.RIGHT;
            case RIGHT -> face = Direction.LEFT;
        }
    }

    //�ƶ����£�Ҳ����˵�������������λ�ƣ��ٶȣ����ٶȵ���Ȼ�ı�
    private void moveUpdate()
    {
        xInMap += xSpeed;
        double xOldSpeed = xSpeed;
        xSpeed += xAcceleration;
        if((xOldSpeed-xTargetSpeed)*(xSpeed-xTargetSpeed)<=0.0)
            xSpeed = xTargetSpeed;

        yInMap += ySpeed;
        if(collisionDetectionDown())
            yInMap = (double)(((int)yInMap)/32)*32;
        ySpeed += yAcceleration;
        if(ySpeed >= yMaxSpeed)
            ySpeed = yMaxSpeed;
    }
    private void setAcceleration()       //���������ٶȺ�Ŀ���ٶ�ȷ�����ٶ�
    {
        if (xTargetSpeed == xAcMaxSpeed)
            if(state == State.ALOFT)
                xAcceleration = xDeAcceleration;
            else
                xAcceleration = xAcAcceleration;
        else if (xTargetSpeed == -xAcMaxSpeed)
            if(state == State.ALOFT)
                xAcceleration = -xDeAcceleration;
            else
                xAcceleration = -xAcAcceleration;
        else if(xTargetSpeed == xMaxSpeed)
            if(xSpeed > xMaxSpeed)
                xAcceleration = -xDeAcceleration;
            else
                if(state == State.ALOFT)
                    xAcceleration = xDeAcceleration;
                else
                    xAcceleration = xAcAcceleration;
        else if(xTargetSpeed == -xMaxSpeed)
            if(xSpeed < -xMaxSpeed)
                xAcceleration = xDeAcceleration;
            else
                if(state == State.ALOFT)
                    xAcceleration = -xDeAcceleration;
                else
                    xAcceleration = -xAcAcceleration;
        else if(xTargetSpeed == 0.0)
            if(xSpeed > 0.0)
                xAcceleration = -xDeAcceleration;
            else if(xSpeed < 0.0)
                xAcceleration = xDeAcceleration;
            else
                xAcceleration = 0.0;
    }
    private void faceUpdate()
    {
        switch (state) {
            case WALK, TURN -> {
                if (xAcceleration > 0.0)
                    face = Direction.RIGHT;
                else if (xAcceleration < 0.0)
                    face = Direction.LEFT;
                if (Math.abs(xAcceleration) == xDeAcceleration)
                    changeFace();
            }
        }
    }
    public void update()
    {
        //�������
        super.update();
        //״̬����
        stateUpdate();

        if(state != State.ALOFT) {
            jumpAllowed = true;
            jumpFlag = false;
            jumpedHeight = 0.0;
        }
        //����������ȷ��Ŀ���ٶ�
        if(KeyListener.right) {
            if(KeyListener.bullet)
                xTargetSpeed = xAcMaxSpeed;
            else
                xTargetSpeed = xMaxSpeed;
        }
        else if (KeyListener.left) {
            if(KeyListener.bullet)
                xTargetSpeed = -xAcMaxSpeed;
            else
                xTargetSpeed = -xMaxSpeed;
        }
        else {
            if(state!=State.ALOFT)
                xTargetSpeed = 0.0;
            else
                xTargetSpeed = xSpeed;
        }
        //��Ծ��
        if (KeyListener.up)
        {
            if(jumpAllowed)         //����Ծ��ֻ����������Ծ��ʱ�����Ч
            {
                if(state != State.ALOFT)       //�����ϰ��ϼ�
                {
                    jumpFlag = true;
                    ySpeed = -yMaxSpeed;
                }
                else
                {
                    if(jumpedHeight <= maxHeight && ySpeed <= 0.0)
                        ySpeed = -yMaxSpeed;
                }
            }
        }
        else
        {
            if(state == State.ALOFT)
                jumpAllowed = false;
            if(jumpFlag && jumpedHeight <= minHeight && ySpeed <= 0.0)
                ySpeed = -yMaxSpeed;
        }

        if(jumpFlag)
            jumpedHeight -= ySpeed;

        //ȷ�����ٶ�
        setAcceleration();
        //�������
        faceUpdate();
        //�ƶ�����
        moveUpdate();
        //��ͼ����
        imageUpdate();
    }
}
//�Լ��Ľ�ɫ��
/*
public class Mario extends Thread {
	
	public GameFrame gf;
	
	public boolean jumpFlag=true;
	
	//����µ�����
	public int x=0,y=358;
	//����µ��ٶ�
	public int xspeed=5 , yspeed=1;
	//����µĿ��
	public int width=30,height=32;
	//����µ�ͼƬ
	public Image img = new ImageIcon("image/mari1.png").getImage();
	
	public boolean left=false,right=false,down=false,up=false;
	
	public String Dir_Up="Up",Dir_Left="Left",Dir_Right="Right",Dir_Down="Down";
	
	
	public Mario (GameFrame gf) {
		this.gf = gf;
		this.Gravity();
	}

	// ����������߼� ���ƶ����߼��������
	public void run(){
		while(true){
			//������
			if(left){
				//��ײ����
				if(hit(Dir_Left)){
					this.xspeed=0;
				}
				
				if(this.x>=0){
					this.x-=this.xspeed;
					this.img=new ImageIcon("image/mari_left.gif").getImage();
				}
				
				this.xspeed=5;
			}
			
			//������
			if(right){
				// �ұ���ײ����Ӧ���������ߵ�ʱ����
				// ������ײ��⣺�������ǣ���������ײ�
				if(hit(Dir_Right)){
					this.xspeed=0;
				}
				//�����������ƶ�
				if(this.x<400){
					this.x += this.xspeed;
					this.img=new ImageIcon("image/mari_right.gif").getImage();
				}
				
				if(this.x>=400){
					//���������ƶ�
					gf.bg.x-=this.xspeed;
					//�ϰ��������ƶ�
					for (int i = 0; i <gf.eneryList.size(); i++) {
						Enemy enery = gf.eneryList.get(i);
						enery.x-=this.xspeed;
					}
					this.img= new ImageIcon("image/mari_right.gif").getImage();
				}
				this.xspeed=5;
			}
			
			//������
			if(up){

				if(jumpFlag && !isGravity){
					jumpFlag=false;
					new Thread(){
						public void run(){
							jump();
							jumpFlag=true;
						}
					}.start();
				}
			}
			
			try {
				this.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	//�������ĺ���
	public void jump(){
		int jumpHeigh=0;
		for (int i = 0; i < 150; i++) {
			gf.mario.y-=this.yspeed;
			jumpHeigh++;
			if(hit(Dir_Up)){
				break;
			}
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (int i = 0; i <jumpHeigh; i++) {
			gf.mario.y+=this.yspeed;
			if(hit(Dir_Down)){
				this.yspeed=0;
			}
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
		}
		this.yspeed=1;//��ԭ�ٶ�
	}
	
	//�����ײ
	public boolean hit(String dir){
		// Swing�����У��˼��Ѿ��ṩ�ˣ���
		Rectangle myrect = new Rectangle(this.x,this.y,this.width,this.height);

		Rectangle rect =null;
		
		for (int i = 0; i < gf.eneryList.size(); i++) {
			Enemy enery = gf.eneryList.get(i);
			
			if(dir.equals("Left")){
				rect = new Rectangle(enery.x+2,enery.y,enery.width,enery.height);
			}else if(dir.equals("Right")){
				// �Ҳ���ײ���⡣
				rect = new Rectangle(enery.x-2,enery.y,enery.width,enery.height);
			}
			
			else if(dir.equals("Up")){
				rect = new Rectangle(enery.x,enery.y+1,enery.width,enery.height);
			}else if(dir.equals("Down")){
				rect = new Rectangle(enery.x,enery.y-2,enery.width,enery.height);
			}
			//��ײ���
			if(myrect.intersects(rect)){
				return true;
			}
		}
		
		return false;
	}
	
	//����Ƿ�����
	public boolean isGravity=false;

	// �����̣߳�
	public void Gravity(){
			new Thread(){
				public void run(){
					
					while(true){
						try {
							sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						

						while(true){
							if(!jumpFlag){
								break;
							}
							
							if(hit(Dir_Down)){
								break;
							}
							
							if(y>=358){
								isGravity=false;
							}else{
								isGravity=true;
								y+=yspeed;
							}
							
							try {
								sleep(10);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
					}
				}
				}
			}.start();
	
	}
}
*/