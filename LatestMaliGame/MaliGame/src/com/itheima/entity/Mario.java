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
    public double xTargetSpeed;      //目标速度，比如说什么键子也不按，那么马里奥的目标速度为0
    public boolean jumpFlag;         //跳跃标志，表明马里奥跳跃中
    public boolean jumpAllowed;      //允许跳跃，防止多段跳跃
    public double minHeight;
    public double maxHeight;
    public double jumpedHeight;
    public double xAcMaxSpeed;          //马里奥特有的最大速度，按住加速键
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
        yAcceleration = 0.5;         //重力加速度
        yMaxSpeed = 6.0;
        jumpFlag = false;
        jumpAllowed = true;
        minHeight = 16.0;
        maxHeight = 112.0;
        jumpedHeight = 0.0;
        state = State.STAND;
    }

    //向下的碰撞检测
    private boolean collisionDetectionDown()
    {
        //暂时会出现马里奥在最下面一层的问题
        if(!(Map.mapBlockInfo[(int) xInMap / 32][1 + (int) yInMap / 32] instanceof Air))
            return true;
        else return !(Map.mapBlockInfo[(int) (xInMap + 31.0) / 32][1 + (int) yInMap / 32] instanceof Air);
    }

    //更改跑步动画贴图
    private void changeRunImage()
    {
        int walkCount = (int)(MainObject.frameCount/3)%3;
        switch (walkCount) {
            case 0 -> changeImage(ImageCache.marioRunImagePair1);
            case 1 -> changeImage(ImageCache.marioRunImagePair2);
            case 2 -> changeImage(ImageCache.marioRunImagePair3);
        }
    }

    //更新马里奥状态信息
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

    //移动更新，也就是说三个物理变量，位移，速度，加速度的自然改变
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
    private void setAcceleration()       //根据现在速度和目标速度确定加速度
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
        //父类更新
        super.update();
        //状态更新
        stateUpdate();

        if(state != State.ALOFT) {
            jumpAllowed = true;
            jumpFlag = false;
            jumpedHeight = 0.0;
        }
        //按键分析，确定目标速度
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
        //跳跃键
        if (KeyListener.up)
        {
            if(jumpAllowed)         //按跳跃键只有在允许跳跃的时候才有效
            {
                if(state != State.ALOFT)       //地面上按上键
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

        //确定加速度
        setAcceleration();
        //朝向更新
        faceUpdate();
        //移动更新
        moveUpdate();
        //贴图更新
        imageUpdate();
    }
}
//自己的角色类
/*
public class Mario extends Thread {
	
	public GameFrame gf;
	
	public boolean jumpFlag=true;
	
	//马里奥的坐标
	public int x=0,y=358;
	//马里奥的速度
	public int xspeed=5 , yspeed=1;
	//马里奥的宽高
	public int width=30,height=32;
	//马里奥的图片
	public Image img = new ImageIcon("image/mari1.png").getImage();
	
	public boolean left=false,right=false,down=false,up=false;
	
	public String Dir_Up="Up",Dir_Left="Left",Dir_Right="Right",Dir_Down="Down";
	
	
	public Mario (GameFrame gf) {
		this.gf = gf;
		this.Gravity();
	}

	// 玛丽飞翔的逻辑 ；移动的逻辑都在这里。
	public void run(){
		while(true){
			//向左走
			if(left){
				//碰撞到了
				if(hit(Dir_Left)){
					this.xspeed=0;
				}
				
				if(this.x>=0){
					this.x-=this.xspeed;
					this.img=new ImageIcon("image/mari_left.gif").getImage();
				}
				
				this.xspeed=5;
			}
			
			//向右走
			if(right){
				// 右边碰撞物检测应该是往右走的时候检测
				// 进行碰撞检测：至少主角（玛丽，碰撞物）
				if(hit(Dir_Right)){
					this.xspeed=0;
				}
				//任人物向右移动
				if(this.x<400){
					this.x += this.xspeed;
					this.img=new ImageIcon("image/mari_right.gif").getImage();
				}
				
				if(this.x>=400){
					//背景向左移动
					gf.bg.x-=this.xspeed;
					//障碍物项左移动
					for (int i = 0; i <gf.eneryList.size(); i++) {
						Enemy enery = gf.eneryList.get(i);
						enery.x-=this.xspeed;
					}
					this.img= new ImageIcon("image/mari_right.gif").getImage();
				}
				this.xspeed=5;
			}
			
			//向上跳
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
	
	
	//向上跳的函数
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
		this.yspeed=1;//还原速度
	}
	
	//检测碰撞
	public boolean hit(String dir){
		// Swing技术中，人家已经提供了！！
		Rectangle myrect = new Rectangle(this.x,this.y,this.width,this.height);

		Rectangle rect =null;
		
		for (int i = 0; i < gf.eneryList.size(); i++) {
			Enemy enery = gf.eneryList.get(i);
			
			if(dir.equals("Left")){
				rect = new Rectangle(enery.x+2,enery.y,enery.width,enery.height);
			}else if(dir.equals("Right")){
				// 右侧碰撞物检测。
				rect = new Rectangle(enery.x-2,enery.y,enery.width,enery.height);
			}
			
			else if(dir.equals("Up")){
				rect = new Rectangle(enery.x,enery.y+1,enery.width,enery.height);
			}else if(dir.equals("Down")){
				rect = new Rectangle(enery.x,enery.y-2,enery.width,enery.height);
			}
			//碰撞检测
			if(myrect.intersects(rect)){
				return true;
			}
		}
		
		return false;
	}
	
	//检查是否贴地
	public boolean isGravity=false;

	// 重力线程！
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