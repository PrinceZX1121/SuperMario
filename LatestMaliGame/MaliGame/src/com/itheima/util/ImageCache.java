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
    public static BufferedImage marioRunRightImage1;
    public static BufferedImage marioRunRightImage2;
    public static BufferedImage marioRunRightImage3;
    public static BufferedImage marioJumpRightImage;
    public static BufferedImage marioTurnRightImage;
    public static BufferedImage marioStandRightImage;
    public static BufferedImage marioRunLeftImage1;
    public static BufferedImage marioRunLeftImage2;
    public static BufferedImage marioRunLeftImage3;
    public static BufferedImage marioJumpLeftImage;
    public static BufferedImage marioTurnLeftImage;
    public static BufferedImage marioStandLeftImage;
    public static BufferedImage[] marioStandImagePair = new BufferedImage[2];
    public static BufferedImage[] marioRunImagePair1 = new BufferedImage[2];
    public static BufferedImage[] marioRunImagePair2 = new BufferedImage[2];
    public static BufferedImage[] marioRunImagePair3 = new BufferedImage[2];
    public static BufferedImage[] marioJumpImagePair = new BufferedImage[2];
    public static BufferedImage[] marioTurnImagePair = new BufferedImage[2];
    static {
        try {
            brickImage = ImageIO.read( new FileInputStream("image\\brick.png"));
            floorImage = ImageIO.read( new FileInputStream("image\\floor.png"));
            queryImage = ImageIO.read( new FileInputStream("image\\query.png"));

            marioRunRightImage1 = ImageIO.read( new FileInputStream("image\\marioRunRight1.png"));
            marioRunRightImage2 = ImageIO.read( new FileInputStream("image\\marioRunRight2.png"));
            marioRunRightImage3 = ImageIO.read( new FileInputStream("image\\marioRunRight3.png"));
            marioJumpRightImage = ImageIO.read( new FileInputStream("image\\marioJumpRight.png"));
            marioStandRightImage = ImageIO.read( new FileInputStream("image\\marioStandRight.png"));
            marioTurnRightImage = ImageIO.read( new FileInputStream("image\\marioTurnRight.png"));

            marioRunLeftImage1 = ImageIO.read( new FileInputStream("image\\marioRunLeft1.png"));
            marioRunLeftImage2 = ImageIO.read( new FileInputStream("image\\marioRunLeft2.png"));
            marioRunLeftImage3 = ImageIO.read( new FileInputStream("image\\marioRunLeft3.png"));
            marioJumpLeftImage = ImageIO.read( new FileInputStream("image\\marioJumpLeft.png"));
            marioStandLeftImage = ImageIO.read( new FileInputStream("image\\marioStandLeft.png"));
            marioTurnLeftImage = ImageIO.read( new FileInputStream("image\\marioTurnLeft.png"));

            marioStandImagePair[0] = marioStandLeftImage;
            marioStandImagePair[1] = marioStandRightImage;

            marioRunImagePair1[0] = marioRunLeftImage1;
            marioRunImagePair1[1] = marioRunRightImage1;

            marioRunImagePair2[0] = marioRunLeftImage2;
            marioRunImagePair2[1] = marioRunRightImage2;

            marioRunImagePair3[0] = marioRunLeftImage3;
            marioRunImagePair3[1] = marioRunRightImage3;

            marioJumpImagePair[0] = marioJumpLeftImage;
            marioJumpImagePair[1] = marioJumpRightImage;

            marioTurnImagePair[0] = marioTurnLeftImage;
            marioTurnImagePair[1] = marioTurnRightImage;

            mapImage = Map.mapImage;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
