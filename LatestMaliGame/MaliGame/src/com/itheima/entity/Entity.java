package com.itheima.entity;

import java.awt.image.BufferedImage;

public class Entity {
    public double xInMap;        //相对于地图的x像素坐标
    public double yInMap;        //相对于地图的y像素坐标
    public int width;         //像素宽度， 主要用于碰撞检测
    public int height;        //像素高度
    public BufferedImage image;           //图片，  图片和实体碰撞箱不一定是完全吻合的

    public void update()         //实体更新
    {

    }

    public void changeImage(BufferedImage newImage)    //更改实体图片
    {
        image = newImage;
    }
}
