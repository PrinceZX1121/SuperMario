package com.itheima.block;

public class Brick extends Block{
    public String imgPath = "image/brick.png";
    public Brick(int x, int y) {
        super(x, y);
        this.setImage(this.imgPath);
    }
    public Brick() {
        super();
    }
}
