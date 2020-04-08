package Ã∞≥‘…ﬂ;
import javax.swing.*;
public class snake2{
	public static void main(String[] args) {
		JFrame frame=new JFrame();
		frame.setBounds(10,10,900,720);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		snakePanel panel=new snakePanel();
		frame.add(panel);
		frame.setVisible(true);
	}
}