package com.itheima.entity;

import java.awt.image.BufferedImage;

enum Direction
{
    LEFT, RIGHT;
}

public class MovableEntity extends Entity{
    public double xSpeed;
    public double xAcceleration;
    public double ySpeed;
    public double yAcceleration;
    public Direction face;              //实体面朝向
    public boolean changeableFace;      //面朝向可更改，用于检测比如跳跃状态的马里奥是不可更改朝向的

    public void update()         //重写实体更新
    {
        super.update();
    }

    public void changeImage(BufferedImage newImage)    //重写更改实体图片
    {
        super.changeImage(newImage);
    }

}
