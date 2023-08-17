package com.itheima.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageCache {
    public static BufferedImage brickImage;
    public static BufferedImage mapImage;
    public static BufferedImage queryImage;
    public static BufferedImage floorImage;
    public static BufferedImage marioRunImage1;
    public static BufferedImage marioRunImage2;
    public static BufferedImage marioRunImage3;
    public static BufferedImage marioJumpImage;
    public static BufferedImage marioTurnImage;
    public static BufferedImage marioStandImage;
    static {
        try {
            brickImage = ImageIO.read( new FileInputStream("image\\brick.png"));
            floorImage = ImageIO.read( new FileInputStream("image\\floor.png"));
            queryImage = ImageIO.read( new FileInputStream("image\\query.png"));
            marioRunImage1 = ImageIO.read( new FileInputStream("image\\marioRun1.png"));
            marioRunImage2 = ImageIO.read( new FileInputStream("image\\marioRun2.png"));
            marioRunImage3 = ImageIO.read( new FileInputStream("image\\marioRun3.png"));
            marioJumpImage = ImageIO.read( new FileInputStream("image\\marioJump.png"));
            marioStandImage = ImageIO.read( new FileInputStream("image\\marioStand.png"));
            marioTurnImage = ImageIO.read( new FileInputStream("image\\marioTurn.png"));
            mapImage = Map.mapImage;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
