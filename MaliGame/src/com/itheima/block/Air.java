package com.itheima.block;

public class Air extends Block {
    public Air(int x, int y) {
        super(x,y);
        this.touchable = false;
    }
    public Air() {
        super();
    }
}
