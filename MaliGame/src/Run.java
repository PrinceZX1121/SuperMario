import com.itheima.MainObject;
import com.itheima.entity.Mario;
import com.itheima.ui.GameFrame;
import com.itheima.util.KeyListener;
import com.itheima.util.Map;

/**
  超级玛丽启动类。
 */
public class Run {
	public static boolean endFlag = false;
	//主函数，程序入口
	public static void start() throws Exception
	{
		new Map();      //初始化Map
		MainObject.mario = new Mario();
		MainObject.gf = new GameFrame();
		MainObject.kl = new KeyListener();
		MainObject.gf.addKeyListener(MainObject.kl);
		MainObject.frameCount = 0;
	}

	public static void update(){
		MainObject.mario.update();
		MainObject.gf.repaint();
	}

	public static void end()
	{

	}

	public static void main(String[] args) throws Exception {
		start();
		while(!endFlag)
		{
			update();
			Thread.sleep(17);
			MainObject.frameCount++;
		}
		end();
	}
	/*
	public static void main(String[] args) throws Exception {
		GameFrame gf = new GameFrame();
		// 创建监听器对象
		KeyListener kl = new KeyListener(gf);
		// 给窗体添加键盘监听器
		gf.addKeyListener(kl);
	}
	*/
}
