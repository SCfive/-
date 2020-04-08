package ̰����;
import java.awt.Color; // eclipse �� shift+ctrl+O �Զ���Ӱ����� 
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

	//����Ҫ�õı�����������һ�� ��÷��ڷ���ǰ�� �������
	int[] snakex = new int[750]; //���ߵ�����
	int[] snakey = new int[750];
	Random rand = new Random();  // �������ʳ��
	int foodx = rand.nextInt(34)*25+25;  //�����������
	int foody = rand.nextInt(24)*25+75;
	int len = 3;    // ������ĳ���
	int score = 0;  //��Ϸ����
	String fangxiang = "R";   //��ͷ�ķ���

	boolean isStarted = false;  //�ж���Ϸ�Ƿ�ʼ  ��ϵ������Ŀո���¼��Լ� Timer�Ƿ�ʼ
	boolean isFailed = false;  //�ж���Ϸ�Ƿ�ʧ�� ����ͷ�Ƿ�ײ������
	boolean isSuceed=false;

	//���ƶ���ԭ�����ǲ��ϵ��ػ� ���������paint�������� �ػ���Ƶ����timer���� ��λ�Ǻ���
	Timer timer = new Timer(100,this); //�����ߵ��ƶ����ٶ�


	public snakePanel() {  //���캯�� �������ʼ��  �����й��̺�ĳ�ʼ״̬
	    this.setFocusable(true);  //ѡ�н��� ���Ի��� �ϵİ�ť������
	    this.addKeyListener(this);  //���̼��� ���㰴���̵�ʱ�����ǵĳ����з�ӳ
	    setup();     //���� ��ʼ��
	    timer.start();   //Timer������� �൱�ڶ����� Ȼ�󹤳̿�ʼrepaint �߲����˶�

	}

	public void paint(Graphics g) {  //�˺����൱��һ֧���� ��panel�����ϻ����ֶ������� ���� ���ֵȣ�
	    this.setBackground(Color.blue); 
	    snake.paintIcon(this, g, 25, 11);  //���浼���ͼƬ������paintIcon������������ �����ڻ�����ͼƬ
	    g.fillRect(25, 75, 850, 600);  //��дָ���ľ���

	    //����ͷ
	    if(fangxiang.equals("R")) { //���̼��������ͷ������жϺ� �����ﻭ��
	        right.paintIcon(this, g, snakex[0], snakey[0]);
	    }else if(fangxiang.equals("L")) {
	        left.paintIcon(this, g, snakex[0], snakey[0]);
	    }else if(fangxiang.equals("U")) {
	        up.paintIcon(this, g, snakex[0], snakey[0]);
	    }else if(fangxiang.equals("D")) {
	        down.paintIcon(this, g, snakex[0], snakey[0]);
	    }

	    //��������
	    for(int i=1;i<len;i++) { //����Ļ������ǰ�body.pngͼƬ����������
	        body.paintIcon(this, g, snakex[i], snakey[i]);
	    }

	    if(!isStarted) {  //���ݽṹ���ж���û��ʼ  �������ﻭ������Ķ��� ������һ����ʾ��
	        g.setColor(Color.red);//ÿ�λ�������Ҫ��������ɫ ��Ҫ���� ռīˮ �����¸�����������ɫ
	        g.setFont(new Font("����",Font.BOLD,30)); //ͬ�� ���û��ʵ����� 
	        g.drawString("���ո����ʼ��Ϸ��", 300, 300);//Ȼ�󻭳����� �����300 300 ��ָ���ֳ��ֵ�λ��
	    }
	    if(isSuceed) {
	    	g.setColor(Color.yellow);
	    	 g.setFont(new Font("����",Font.BOLD,30));
	    	 g.drawString("������Ϸ��ʱ�仹�����ȥѧϰ��", 300, 300);
	    	 g.drawString("���ո�����¿�ʼ��Ϸ��", 300, 400);
	    	 g.drawString("�������������Ϸ", 300, 500);
	    }

	    if(isFailed) {  //���ݽṹ�ж�ʧ���� �������ﻭ������Ķ��� ÿ�λ��������ǻ������ݵ��ж� �����ǻ�����
	        g.setColor(Color.red);
	        g.setFont(new Font("����",Font.BOLD,30));
	        g.drawString("��Ϸʧ�ܣ�", 300, 300);
	    }

	    food.paintIcon(this, g, foodx, foody);  //��ʳ��

	    g.setColor(Color.RED);   //���û��� ��������
	    g.setFont(new Font("arial",Font.BOLD,10));
	    g.drawString("Score:"+score, 800, 100);
	    g.drawString("Length:"+len, 800, 120);

	}

	public void setup() { //���ó�ʼ������ 
	    len = 3;
	    score = 0;
	    isStarted = false;
	    isFailed = false;
	    fangxiang = "R";   //��ͷĬ�Ϸ���Ϊ��
	    snakex[0] = 100;  //�߸ճ�����λ��
	    snakey[0] = 100;
	    snakex[1] = 75;
	    snakey[1] = 100;
	    snakex[2] = 50;
	    snakey[2] = 100;
	}


	//��������������ʵ��KeyListener��ʱ���ɵĺ��� �����Ǽ������� ��������Ҫ�õڶ� �����̰���ʱ�ķ�ӳ
	@Override
	public void keyTyped(KeyEvent e) {  
	    // TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) { //���̰���ʱ ������Ҫ�Ĳ��� 
	    int keyCode = e.getKeyCode();  //��ȡ������keycodeֵ ÿ���������в�ͬ��ֵ
	    if(keyCode == KeyEvent.VK_SPACE) {   //�жϰ����Ƿ�Ϊ�ո�� VK_SPACE���ǿո��Ĭ�ϵ�ֵ
	        if(isFailed||isSuceed) {
	            setup();   //����ǿո������Ϸ������ �͵�������������ó�ʼ��
	        }else {
	            isStarted = !isStarted; //����ǿո������Ϸû������ �жϱ�������෴��
	        }


	    }else if(keyCode == KeyEvent.VK_UP && fangxiang !="D" ) { //������ǿո�����Ƿ��� ����ͷ�����Ӧ�ķ���
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
	public void actionPerformed(ActionEvent e) { //�����������ʵ��ActionListener��ʱ���ɵĹ��� ���¼�����ʱ�����¼�����ʱ����
	    //�ٶ�1������   �¼���ʼ������������ʼ �� ��ʼpaint
	    timer.start();

	    //���ƶ����� ֻ�����ݷ����仯�� ���ʲ��ܻ�����Ӧ�Ļ�
	    if(isStarted && !isFailed&&!isSuceed) {
	        for(int i=len;i>0;i--) { //��������ƶ� ���ݵı仯 �������ƶ��������repaint��ʵ��
	            snakex[i] = snakex[i-1];
	            snakey[i] = snakey[i-1];
	        }

	    if(fangxiang.equals("R")) {  //��ͷ���ƶ� ���ݵı仯 �����ı仯�������repaint �е�ʵ��
	        snakex[0] = snakex[0]+25;
	        if(snakex[0]>850) snakex[0] = 25;  //�ĸ�if��� �ж���ͷ�Ƿ񳬳����� 
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

	        if(snakex[0] == foodx && snakey[0] == foody) {  //�ж����Ƿ�Ե�ʳ��
	            len++;
	            score++;
	            if(score==50) {
	            	isSuceed=!isSuceed;
	            }
	            foodx = rand.nextInt(34)*25+25;//ʳ����ֵ� x y���� �ڻ�����
	            foody = rand.nextInt(24)*25+75;
	        }

	        for(int i=1;i<len;i++) {  //�ж���ͷ�Ƿ�ײ��������
	            if(snakex[0]==snakex[i] && snakey[0]==snakey[i]) {
	                isFailed = true; //���ײ�� �ж���Ϸʧ�ܵı�����Ϊ�� ����Ϸʧ��
	            }       
	        }
	    }
	    //�ػ�
	    repaint();  
	}
}

