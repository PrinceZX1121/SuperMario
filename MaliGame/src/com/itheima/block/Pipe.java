package com.itheima.block;

public class Pipe extends Block{
    public String imgPath = "image/pipe.png";
    private int height = 60;
    public Pipe (int x, int y) {
        super(x,y);
        this.setImage(this.imgPath);
    }
}
