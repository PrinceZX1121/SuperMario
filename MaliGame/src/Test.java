import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import javax.imageio.ImageIO;

public class Test {
    public File fl = new File("D:\\1.txt");

    public static void main(String[] args) throws FileNotFoundException {
        Test test = new Test();
        FileInputStream fis = new FileInputStream(test.fl);
        System.out.print(test.fl.getName());
    }
}
