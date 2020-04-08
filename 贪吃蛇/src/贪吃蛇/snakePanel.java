package 贪吃蛇;
import java.awt.Color; // eclipse 用 shift+ctrl+O 自动添加包、类 
import java.awt.Font; 
import java.awt.Graphics; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.awt.event.KeyEvent; 
import java.awt.event.KeyListener; 
import java.util.Random;

import javax.swing.ImageIcon; 
import javax.swing.JPanel; 
import javax.swing.Timer;

public class snakePanel extends JPanel implements KeyListener,ActionListener{
	ImageIcon up = new ImageIcon("snake.png"); 
	ImageIcon down = new ImageIcon("snake.png"); 
	ImageIcon right = new ImageIcon("snake.png"); 
	ImageIcon left = new ImageIcon("snake.png"); 
	ImageIcon snake = new ImageIcon("background.png"); 
	ImageIcon food = new ImageIcon("egg.png"); 
	ImageIcon body = new ImageIcon("body.png");

	//所有要用的变量都定义在一起 最好放在方法前面 方便查找
	int[] snakex = new int[750]; //放蛇的数组
	int[] snakey = new int[750];
	Random rand = new Random();  // 随机产生食物
	int foodx = rand.nextInt(34)*25+25;  //放事物的数组
	int foody = rand.nextInt(24)*25+75;
	int len = 3;    // 蛇身体的长度
	int score = 0;  //游戏积分
	String fangxiang = "R";   //蛇头的方向

	boolean isStarted = false;  //判断游戏是否开始  关系到后面的空格键事件以及 Timer是否开始
	boolean isFailed = false;  //判断游戏是否失败 即蛇头是否撞到身体
	boolean isSuceed=false;

	//蛇移动的原理：就是不断的重画 即下面的热paint（）函数 重画的频率由timer控制 单位是毫秒
	Timer timer = new Timer(100,this); //控制蛇的移动的速度


	public snakePanel() {  //构造函数 即给类初始化  即运行工程后的初始状态
	    this.setFocusable(true);  //选中焦点 即对画布 上的按钮起作用
	    this.addKeyListener(this);  //键盘监听 即你按键盘的时候我们的程序有反映
	    setup();     //重置 初始化
	    timer.start();   //Timer类的启动 相当于定闹钟 然后工程开始repaint 蛇才能运动

	}

	public void paint(Graphics g) {  //此函数相当于一支画笔 在panel画布上画各种东西（蛇 事物 文字等）
	    this.setBackground(Color.blue); 
	    snake.paintIcon(this, g, 25, 11);  //上面导入的图片就是用paintIcon函数来画出来 这是在画标题图片
	    g.fillRect(25, 75, 850, 600);  //填写指定的矩形

	    //画蛇头
	    if(fangxiang.equals("R")) { //键盘监听后对蛇头方向的判断后 这这里画出
	        right.paintIcon(this, g, snakex[0], snakey[0]);
	    }else if(fangxiang.equals("L")) {
	        left.paintIcon(this, g, snakex[0], snakey[0]);
	    }else if(fangxiang.equals("U")) {
	        up.paintIcon(this, g, snakex[0], snakey[0]);
	    }else if(fangxiang.equals("D")) {
	        down.paintIcon(this, g, snakex[0], snakey[0]);
	    }

	    //画蛇身体
	    for(int i=1;i<len;i++) { //身体的画发就是把body.png图片画在数组中
	        body.paintIcon(this, g, snakex[i], snakey[i]);
	    }

	    if(!isStarted) {  //数据结构中判读出没开始  就在这里画出下面的东西 即出现一行提示字
	        g.setColor(Color.red);//每次画东西想要不懂的颜色 都要重新 占墨水 即重新给画笔设置颜色
	        g.setFont(new Font("宋体",Font.BOLD,30)); //同样 设置画笔的字体 
	        g.drawString("按空格键开始游戏！", 300, 300);//然后画出文字 后面的300 300 是指文字出现的位置
	    }
	    if(isSuceed) {
	    	g.setColor(Color.yellow);
	    	 g.setFont(new Font("宋体",Font.BOLD,30));
	    	 g.drawString("有玩游戏的时间还不如快去学习！", 300, 300);
	    	 g.drawString("按空格键重新开始游戏！", 300, 400);
	    	 g.drawString("按方向键继续游戏", 300, 500);
	    }

	    if(isFailed) {  //数据结构判断失败了 就在这里画出下面的东西 每次画东西都是基于数据的判断 即这是画表面
	        g.setColor(Color.red);
	        g.setFont(new Font("宋体",Font.BOLD,30));
	        g.drawString("游戏失败！", 300, 300);
	    }

	    food.paintIcon(this, g, foodx, foody);  //画食物

	    g.setColor(Color.RED);   //重置画笔 画分数表
	    g.setFont(new Font("arial",Font.BOLD,10));
	    g.drawString("Score:"+score, 800, 100);
	    g.drawString("Length:"+len, 800, 120);

	}

	public void setup() { //重置初始化函数 
	    len = 3;
	    score = 0;
	    isStarted = false;
	    isFailed = false;
	    fangxiang = "R";   //蛇头默认方向为右
	    snakex[0] = 100;  //蛇刚出发的位置
	    snakey[0] = 100;
	    snakex[1] = 75;
	    snakey[1] = 100;
	    snakex[2] = 50;
	    snakey[2] = 100;
	}


	//这三个函数是在实现KeyListener类时生成的函数 作用是监听键盘 我们是需要用第二 即键盘按下时的反映
	@Override
	public void keyTyped(KeyEvent e) {  
	    // TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) { //键盘按下时 工程需要的操作 
	    int keyCode = e.getKeyCode();  //获取按键的keycode值 每个按键都有不同的值
	    if(keyCode == KeyEvent.VK_SPACE) {   //判断按键是否为空格键 VK_SPACE就是空格键默认的值
	        if(isFailed||isSuceed) {
	            setup();   //如果是空格键且游戏结束了 就调用这个函数重置初始化
	        }else {
	            isStarted = !isStarted; //如果是空格键且游戏没结束了 判断变量变成相反量
	        }


	    }else if(keyCode == KeyEvent.VK_UP && fangxiang !="D" ) { //如果不是空格键而是方向建 则蛇头变成相应的方向
	        fangxiang = "U"; if(isSuceed) isSuceed=false;
	    }else if(keyCode == KeyEvent.VK_DOWN && fangxiang !="U") {
	        fangxiang  = "D";if(isSuceed) isSuceed=false;
	    }else if(keyCode == KeyEvent.VK_RIGHT && fangxiang !="L") {
	        fangxiang = "R";if(isSuceed) isSuceed=false;
	    }else if(keyCode == KeyEvent.VK_LEFT && fangxiang !="R") {
	        fangxiang = "L";if(isSuceed) isSuceed=false;
	    }
	}

	@Override
	public void keyReleased(KeyEvent e) {
	    // TODO Auto-generated method stub
	}

	@Override
	public void actionPerformed(ActionEvent e) { //这个函数是在实现ActionListener类时生成的功能 当事件发生时，当事件发生时调用
	    //再定1个闹钟   事件开始是以闹钟响起开始 即 开始paint
	    timer.start();

	    //先移动数据 只有数据发生变化了 画笔才能画出相应的画
	    if(isStarted && !isFailed&&!isSuceed) {
	        for(int i=len;i>0;i--) { //蛇身体的移动 数据的变化 真正的移动在下面的repaint中实现
	            snakex[i] = snakex[i-1];
	            snakey[i] = snakey[i-1];
	        }

	    if(fangxiang.equals("R")) {  //蛇头的移动 数据的变化 真正的变化在下面的repaint 中的实现
	        snakex[0] = snakex[0]+25;
	        if(snakex[0]>850) snakex[0] = 25;  //四个if语句 判断蛇头是否超出画布 
	    }else if(fangxiang.equals("L")) {
	        snakex[0] = snakex[0]-25;
	        if(snakex[0]<25) snakex[0] = 850;
	    }else if(fangxiang.equals("U")) {
	        snakey[0] = snakey[0]-25;
	        if(snakey[0]<75) snakey[0] = 650;
	    }else if(fangxiang.equals("D")) {
	        snakey[0] = snakey[0]+25;
	        if(snakey[0]>650) snakey[0] = 75;
	    }

	        if(snakex[0] == foodx && snakey[0] == foody) {  //判断蛇是否吃到食物
	            len++;
	            score++;
	            if(score==50) {
	            	isSuceed=!isSuceed;
	            }
	            foodx = rand.nextInt(34)*25+25;//食物出现的 x y坐标 在画布内
	            foody = rand.nextInt(24)*25+75;
	        }

	        for(int i=1;i<len;i++) {  //判断蛇头是否撞到蛇身体
	            if(snakex[0]==snakex[i] && snakey[0]==snakey[i]) {
	                isFailed = true; //如果撞到 判断游戏失败的变量变为真 即游戏失败
	            }       
	        }
	    }
	    //重画
	    repaint();  
	}
}

