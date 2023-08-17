package com.itheima.block;

import javax.swing.*;
import java.awt.*;

public class Block {
    public String imgPath;
    private int width = 32, height = 32;
    private int x, y;
    private Image img;
    public boolean touchable = true;
    public Block(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getY() {
        return y;
    }
    public Block() {}

    public Image getImage(){
        return img;
    }
    public void setImage(String imgPath) {
        this.img = new ImageIcon(imgPath).getImage();
    }
}