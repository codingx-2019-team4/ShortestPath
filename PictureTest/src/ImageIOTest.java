import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ImageIOTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("123.jpg"));
		}catch(IOException e) {
			
		}
//		System.out.println(img);
		Color myWhite = new Color(255,0,0);
		int rgb = myWhite.getRGB();
		System.out.println(rgb);
		for(int i=100;i<201;i++) {
			for(int j=100;j<151;j++) {
				img.setRGB(i, j, rgb);
			}
		}
		rgb = new Color(0,255,0).getRGB();
		for(int i=150;i<201;i++) {
			for(int j=120;j<151;j++) {
				img.setRGB(i, j, rgb);
			}
		}
		
		ImageIcon icon = new ImageIcon(img);
		int height = icon.getIconHeight();
		int width = icon.getIconWidth();
		JLabel lb = new JLabel(icon);
		JFrame frame = new JFrame();
		
		lb.setLocation(0,0);
		lb.setSize(height,width);
		frame.setSize(height,width);
		frame.add(lb);
		frame.setVisible(true);
		
	}

}
