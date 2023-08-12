package com.itheima.util;

import com.itheima.block.*;
import org.junit.Test;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.File;

public class Map
{
    public static BufferedImage mapInfoImg;               //地图信息图片文件
    public static Block[][] mapBlockInfo;                  //地图信息
    public static int height, width;                        //方块宽高
    public static int realHeight, realWidth;
    public static BufferedImage mapImage;
    public static Graphics mapGraphics;
    public Map() throws IOException {
        int a, r, g, b;
        int argb;
        int i, j;

        mapInfoImg = ImageIO.read(new FileInputStream("image\\mapInfo.png"));
        height = mapInfoImg.getHeight();
        width = mapInfoImg.getWidth();
        realHeight = height * 32;
        realWidth = width * 32;
        mapBlockInfo = new Block[width][height];
        for (i = 0; i < width; i++) {
            for (j = 0; j < height; j++) {
                argb = mapInfoImg.getRGB(i, j);
                switch (argb) {
                    case 0xff000000 -> mapBlockInfo[i][j] = new Floor();
                    case 0xffffffff -> mapBlockInfo[i][j] = new Air();
                    case 0xffff0000 -> mapBlockInfo[i][j] = new Brick();
                    case 0xffffff00 -> mapBlockInfo[i][j] = new Query();
                }
                /*
                黑色：0xff000000   floor
                白色：0xffffffff   air
                红色：0xffff0000   brick
                黄色：0xffffff00   query
                 */
            }
        }

        mapImage = new BufferedImage(realWidth, realHeight, BufferedImage.TYPE_INT_ARGB);
        mapGraphics = mapImage.getGraphics();
        mapGraphics.setColor(new Color(92, 148, 252, 255));     //马里奥天空的颜色
        mapGraphics.fillRect(0, 0, realWidth, realHeight);

        for (i = 0; i < width; i++) {
            for (j = 0; j < height; j++) {
                switch (mapBlockInfo[i][j].getClass().getName()) {
                    case "com.itheima.block.Brick" -> mapGraphics.drawImage(ImageCache.brickImage, i * 32, j * 32, null);
                    case "com.itheima.block.Floor" -> mapGraphics.drawImage(ImageCache.floorImage, i * 32, j * 32, null);
                    case "com.itheima.block.Query" -> mapGraphics.drawImage(ImageCache.queryImage, i * 32, j * 32, null);
                }
            }
        }

        File outFile = new File("out.png");
        ImageIO.write(mapImage, "png", outFile);
    }

    @Test
    public void testResult() throws Exception {
        //Map map = new Map();
    }
}

/*public class Map {

    //��������
    public List<String> list = new ArrayList<>();

    // ��ά����Ԫ������һ��һά���飺���о���
    public int[][] map = null;

    // ��Ԫ���ԣ���֤Map���readMap()����ȷʵ�ѵ�ͼ�����ļ�map.txt
    // ���س��˶�ά����
    @Test
    public void testResult() throws Exception {
        int[][] result = readMap();
        // ��ά����������������һ���Ƿ��ǵ�ͼ��������Ϣ
        for(int i = 0 ; i < result.length ; i++ ){
            for(int j = 0 ; j < result[i].length ; j++) {
                System.out.print(result[i][j]+" ");
            }
            System.out.println();
        }
    }

    public int[][] readMap() throws Exception {
        // �����ļ�������
        FileInputStream fis = new FileInputStream("map.txt");
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        //ֱ�Ӷ�ȡһ������
        String value = br.readLine();

        while (value != null) {
            //����ȡ����һ�����ݼ��뵽������
            list.add(value);
            value = br.readLine();
        }

        br.close();

        //�õ������ж�����
        int row = list.size();
        int cloum = 0;

        for (int i = 0; i < 1; i++) {
            String str = list.get(i);
            String[] values = str.split(",");
            cloum = values.length;
        }


        map = new int[row][cloum];

        //���������ַ���ת��������������ֵ����λ����map
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            String[] values = str.split(",");
            for (int j = 0; j < values.length; j++) {
                map[i][j] = Integer.parseInt(values[j]);
            }
        }
        return map;
    }


}
*/