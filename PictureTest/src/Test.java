import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Image img;
		img = Toolkit.getDefaultToolkit().getImage("123.jpg");
		ImageIcon icon = new ImageIcon(img);
		JLabel label = new JLabel(icon);
		JFrame frame = new JFrame();

		
		
		label.setSize(1000,1000);
		label.setLocation(0,0);
		frame.setSize(1000,1000);
		frame.add(label);

//		frame.setLayout(null);
		frame.setVisible(true);
	}

}

