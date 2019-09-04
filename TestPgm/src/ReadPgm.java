import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

//https://stackoverflow.com/questions/3639198/how-to-read-pgm-images-in-java
public class ReadPgm {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String filePath = "fireHouse2.pgm";
		FileInputStream fileInputStream = new FileInputStream(filePath);
		Scanner scan = new Scanner(fileInputStream);
		// Discard the magic number
		scan.nextLine();
		// Discard the comment line
		scan.nextLine();
		// Read pic width, height and max value
		int picWidth = scan.nextInt();
		int picHeight = scan.nextInt();
//		int maxvalue = scan.nextInt();

		fileInputStream.close();

		// Now parse the file as binary data
		fileInputStream = new FileInputStream(filePath);
		DataInputStream dis = new DataInputStream(fileInputStream);

		// look for 4 lines (i.e.: the header) and discard them
		int numnewlines = 4;
		while (numnewlines > 0) {
			char c;
			do {
				c = (char) (dis.readUnsignedByte());
				System.out.println(c);
			} while (c != '\n');
			numnewlines--;
		}

		// read the image data and find the exactly map
		int[][] data2D = new int[picHeight][picWidth];
		int leftEdge = -1;
		int rightEdge = -1;
		int topEdge = -1;
		int botEdge = -1;
		int mapBGColor = 205; // 地圖的背景色
		int[] humanPosition = {  picWidth / 2 , picHeight / 2 }; // 人所在的位置
		int[] start = new int[] { humanPosition[0], humanPosition[1] }; // 起點
//		尋找地圖的上下左右邊界
		for (int row = 0; row < picHeight; row++) {
			for (int col = 0; col < picWidth; col++) {
//				讀取pgm檔的內容
				data2D[row][col] = dis.readUnsignedByte();
				if (data2D[row][col] != mapBGColor) {
					if (leftEdge <= 0 || col < leftEdge)
						leftEdge = col;
					if (rightEdge <= 0 || col > rightEdge)
						rightEdge = col;
					if (topEdge <= 0 || row < topEdge)
						topEdge = row;
					if (botEdge <= 0 || row > topEdge)
						botEdge = row;
				}
			}
		}
		System.out.println("leftEdge: " + leftEdge);
		System.out.println("rightEdge: " + rightEdge);
		System.out.println("topEdge: " + topEdge);
		System.out.println("botEdge: " + botEdge);
		
		BufferedImage image = new BufferedImage(picHeight, picWidth, BufferedImage.TYPE_INT_RGB);
		for (int row = topEdge; row <= botEdge; row++) {
			for (int col = leftEdge; col < rightEdge; col++) {
//				將圖片的每一點轉為RGB儲存在image裡
				int a = data2D[row][col];
				Color newColor = new Color(a, a, a);
				image.setRGB(col, row, newColor.getRGB());
//		         System.out.print(data2D[row][col] + " ");
			}
//		     System.out.println();
		}

//		標記人在哪以及出口位置(從原點出發，x+ 方向往右，y+ 方向往下)
//		一般二維矩陣row代表y軸，col代表x軸，所以在setRGB2的參數中需替換過來(如84行)
		int startColor = new Color(255, 0, 0).getRGB();  //紅色
		int endColor = new Color(0, 255, 0).getRGB();	 //綠色
		int[] door1 = new int[] { start[0] -  28, start[1] - 127 };
		int[] door2 = new int[] { start[0] +  93, start[1] - 126 };
		int[] door3 = new int[] { start[0] + 208, start[1] - 42 };
		int pointSize = 3;
		for (int i = -pointSize; i <= pointSize; i++) {
			for (int j = -pointSize; j <= pointSize; j++) {
				if ((Math.pow(i, 2) + Math.pow(j, 2) <= Math.pow(pointSize, 2))) {
					image.setRGB(humanPosition[1] + j, humanPosition[0] + i, startColor);
					image.setRGB(door1[0] + j, door1[1] + i, endColor);
					image.setRGB(door2[0] + j, door2[1] + i, endColor);
					image.setRGB(door3[0] + j, door3[1] + i, endColor);
				}
			}
		}

//		在地圖上畫線
//		for (int i = 900; i <= 1000; i++) {
//				int rgb = new Color(255, 0, 0).getRGB();
//				image.setRGB(i,1000, rgb);
//		}

		JFrame frame = new JFrame();
		ImageIcon icon = new ImageIcon(image);
		int height = icon.getIconHeight();
		int width = icon.getIconWidth();
		JLabel label = new JLabel(icon);

		label.setLocation(0, 0);
		label.setSize(height, width);
		frame.setSize(height, width);
		frame.add(label);
		frame.setVisible(true);
	}
}
