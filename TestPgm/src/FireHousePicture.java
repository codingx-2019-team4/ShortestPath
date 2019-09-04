import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class FireHousePicture {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		String filePath = "fireHouse2_cut.jpg";
//		FileInputStream fileInputStream = new FileInputStream(filePath);
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(filePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int h = img.getHeight();
		int w = img.getWidth();
//		int[][] map = new int[h][w];
//		BufferedImage img2 = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

		int stepLength = 5;
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
//				map[i][j] = img.getRGB(j, i);
//				int a = map[i][j];
//				img2.setRGB(j, i,a);
				if(i%stepLength == 0 || j%stepLength ==0) {
					img.setRGB(j,i, new Color(255,0,0).getRGB());
				}
			}
		}
//		int position[][] = { { 10, 10 }, { 50, 10 }, { 90, 10 }, { 130, 10 }, { 170, 10 }, { 210, 10 }, { 250, 10 },
//				{ 250, 50 }, { 250, 90 }, { 250, 130 }, { 250, 170 }, { 250, 210 }, { 250, 250 }, { 250, 290 } };
//
//		int pointSize = 3;
//		for (int i = 0; i < position.length; i++) {
////				map[i][j] = img.getRGB(j, i);
////				int a = map[i][j];
////				img2.setRGB(j, i,a);
//			for (int j = -pointSize; j <= pointSize; j++) {
//				for (int k = -pointSize; k <= pointSize; k++) {
//					if ((Math.pow(j, 2) + Math.pow(k, 2) <= Math.pow(pointSize, 2))) {
//						img.setRGB(position[i][0] + j, position[i][1] + k, new Color(255, 0, 0).getRGB());
//					}
//				}
//			}
//			
//		}

		JFrame frame = new JFrame();
		ImageIcon icon = new ImageIcon(img);
		int height = icon.getIconHeight();
		int width = icon.getIconWidth();
		JLabel label = new JLabel(icon);

		label.setLocation(100, 100);
		label.setSize(height + 400, width + 400);
		frame.setSize(height + 400, width + 400);
		frame.add(label);
		frame.setVisible(true);

	}

}
