package com.itheima.entity;

import java.awt.image.BufferedImage;

enum Direction
{
    LEFT, RIGHT;
}

public class MovableEntity extends Entity{
    public double xSpeed;
    public double xAcAcceleration;    //加速时用的加速度（绝对值）
    public double xDeAcceleration;    //减速时用的加速度（绝对值）
    public double xAcceleration;
    public double ySpeed = 0.0;
    public double yAcceleration = 0.5;    //一般而言是重力加速度
    public double xMaxSpeed = 5.0;
    public double yMaxSpeed = 5.0;
    public Direction face;              //实体面朝向

    public void update()         //重写实体更新
    {
        super.update();
    }

    public void changeImage(BufferedImage newImage)    //重写更改实体图片
    {
        super.changeImage(newImage);
    }

    public void changeImage(BufferedImage[] newImagePair)    //根据朝向辨别 0：左， 1：右
    {
        switch (face) {
            case LEFT -> image = newImagePair[0];
            case RIGHT -> image = newImagePair[1];
        }
    }
}
