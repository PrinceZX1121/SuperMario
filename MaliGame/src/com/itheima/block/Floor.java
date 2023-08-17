package com.itheima.block;

public class Floor extends Block{
    public String imgPath = "image/floor.png";
    public Floor (int x, int y) {
        super (x, y);
        this.setImage(this.imgPath);
    }
}
