package com.itheima.block;

public class Query extends Block{
    public String imgPath = "image/query.png";
    public Query (int x, int y) {
        super(x, y);
        this.setImage(this.imgPath);
    }
}
