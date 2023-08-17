import com.itheima.MainObject;
import com.itheima.entity.Mario;
import com.itheima.ui.GameFrame;
import com.itheima.util.KeyListener;
import com.itheima.util.Map;

/**
  �������������ࡣ
 */
public class Run {
	public static boolean endFlag = false;
	//���������������
	public static void start() throws Exception
	{
		new Map();      //��ʼ��Map
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
		// ��������������
		KeyListener kl = new KeyListener(gf);
		// ��������Ӽ��̼�����
		gf.addKeyListener(kl);
	}
	*/
}
