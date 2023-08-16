package com.itheima.block;

import javax.swing.*;
import java.awt.*;

public class Block {
    public int width = 30, height = 30;
    public int x, y;
    public Image img;
    public boolean touchable = true;
    public Block(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Block() {}
    public void setImage(String imgPath) {
        this.img = new ImageIcon(imgPath).getImage();
    }
}