import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Information {
	
	private int picWidth;
	private int picHeight;
	private int leftEdge = -1;
	private int rightEdge = -1;
	private int topEdge = -1;
	private int botEdge = -1;
	private int[][] map = null;
	private int[] origin;
	public int[] start;
	public int[] goal;
	
	public int[][] testData1(){
		int[][] map = new int[7][7];
		int start = -1;
		int end = -2;
		map[5][1] = start;
		map[2][5] = end;
		return map;
	}
	
	public int[][] testData2(){
		int[][] map = new int[7][7];
		int start = -1;
		int end = -2;
		int obstacle = 1000000;
		map[5][1] = start;
		map[2][5] = end;
		for(int i=1; i<=5; i++) {
			map[i][3] = obstacle;
		}
		return map;
	}
	
	public int[][] createMap(String filename) throws IOException{
		String filePath = filename;
		FileInputStream fileInputStream = new FileInputStream(filePath);
		Scanner scan = new Scanner(fileInputStream);
		// Discard the magic number
		scan.nextLine();
		// Discard the comment line
		scan.nextLine();
		// Read pic width, height and max value
		this.picWidth = scan.nextInt();
		this.picHeight = scan.nextInt();
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
		this.map = new int[picHeight][picWidth];
		
		int mapBGColor = 205; // 地圖的背景色
		int[] position = {picWidth / 2 ,picHeight / 2 }; 
		int[] goalPosition =  {(position[0] - 28),(position[1] - 127)};
		this.origin = position; // 原點
		
//		設置起點、終點		
		this.start = origin;     // 人所在的位置(起點)
		this.goal = goalPosition;//終點

		
//		尋找地圖的上下左右邊界
		for (int row = 0; row < picHeight; row++) {
			for (int col = 0; col < picWidth; col++) {
//				讀取pgm檔的內容
				map[row][col] = dis.readUnsignedByte();
				if (map[row][col] != mapBGColor) {
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

		
//		System.out.println("leftEdge: " + leftEdge);
//		System.out.println("rightEdge: " + rightEdge);
//		System.out.println("topEdge: " + topEdge);
//		System.out.println("botEdge: " + botEdge);

		
////		繪圖-------------------------------------
//		BufferedImage image = new BufferedImage(picHeight, picWidth, BufferedImage.TYPE_INT_RGB);
//		for (int row = topEdge; row <= botEdge; row++) {
//			for (int col = leftEdge; col < rightEdge; col++) {
////				將圖片的每一點轉為RGB儲存在image裡
//				int a = map[row][col];
//				Color newColor = new Color(a, a, a);
//				image.setRGB(col, row, newColor.getRGB());
////		         System.out.print(map[row][col] + " ");
//			}
////		     System.out.println();
//		}
//
////		標記人在哪以及出口位置(從原點出發，x+ 方向往右，y+ 方向往下)
////		一般二維矩陣row代表y軸，col代表x軸，所以在setRGB2的參數中需替換過來(如84行)
//		int startColor = new Color(255, 0, 0).getRGB();  //紅色
//		int endColor = new Color(0, 255, 0).getRGB();	 //綠色
//		int[] door1 = new int[] { origin[0] -  28, origin[1] - 127 };
//		int[] door2 = new int[] { origin[0] +  93, origin[1] - 126 };
//		int[] door3 = new int[] { origin[0] + 208, origin[1] - 42 };
//		int pointSize = 3;
//		for (int i = -pointSize; i <= pointSize; i++) {
//			for (int j = -pointSize; j <= pointSize; j++) {
//				if ((Math.pow(i, 2) + Math.pow(j, 2) <= Math.pow(pointSize, 2))) {
//					image.setRGB(start[1] + j, start[0] + i, startColor);
//					image.setRGB(door1[0] + j, door1[1] + i, endColor);
//					image.setRGB(door2[0] + j, door2[1] + i, endColor);
//					image.setRGB(door3[0] + j, door3[1] + i, endColor);
//				}
//			}
//		}
//
////		在地圖上畫線
////		for (int i = 900; i <= 1000; i++) {
////				int rgb = new Color(255, 0, 0).getRGB();
////				image.setRGB(i,1000, rgb);
////		}
//
//		JFrame frame = new JFrame();
//		ImageIcon icon = new ImageIcon(image);
//		int height = icon.getIconHeight();
//		int width = icon.getIconWidth();
//		JLabel label = new JLabel(icon);
//
//		label.setLocation(0, 0);
//		label.setSize(height, width);
//		frame.setSize(height, width);
//		frame.add(label);
//		frame.setVisible(true);
		
		dis.close();
		return map;
	}
	
	public void drawMap() {
//		繪圖-------------------------------------
		BufferedImage image = new BufferedImage(picHeight, picWidth, BufferedImage.TYPE_INT_RGB);
		for (int row = topEdge; row <= botEdge; row++) {
			for (int col = leftEdge; col < rightEdge; col++) {
//				將圖片的每一點轉為RGB儲存在image裡
				int a = map[row][col];
				Color newColor = new Color(a, a, a);
				image.setRGB(col, row, newColor.getRGB());
//		         System.out.print(map[row][col] + " ");
			}
//		     System.out.println();
		}

//		標記人在哪以及出口位置(從原點出發，x+ 方向往右，y+ 方向往下)
//		一般二維矩陣row代表y軸，col代表x軸，所以在setRGB2的參數中需替換過來(如84行)
		int startColor = new Color(255, 0, 0).getRGB();  //紅色
		int endColor = new Color(0, 255, 0).getRGB();	 //綠色
		int[] door1 = new int[] { origin[0] -  28, origin[1] - 127 };
		int[] door2 = new int[] { origin[0] +  93, origin[1] - 126 };
		int[] door3 = new int[] { origin[0] + 208, origin[1] - 42 };
		int pointSize = 3;
		for (int i = -pointSize; i <= pointSize; i++) {
			for (int j = -pointSize; j <= pointSize; j++) {
				if ((Math.pow(i, 2) + Math.pow(j, 2) <= Math.pow(pointSize, 2))) {
					image.setRGB(start[1] + j, start[0] + i, startColor);
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
