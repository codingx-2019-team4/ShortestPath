package A_star_algorithm;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Information {
	private int picWidth;
	private int picHeight;
	private static int leftEdge = -1;
	private static int rightEdge = -1;
	private static int topEdge = -1;
	private static int botEdge = -1;
	private static int[][] map = null;
	private static int mapBGColor = 205; // 地圖的背景色

//	以下三個變數都是用x軸y軸的座標方式(其他矩陣如地圖第一個括號為y軸，第二個為x軸)  
//	此項目未來須重構
	private int[] origin; // 原點
	public int[] start; // 人的位置
	public ArrayList<int[]> goal = new ArrayList<int[]>(); // 安全位置
	private ArrayList<int[]> dangerPointList = null; // sensor感測器位置，當有危險時標記

	public int[][] testData1() {
		int[][] map = new int[7][7];
		int start = -1;
		int end = -2;
		map[5][1] = start;
		map[2][5] = end;
		return map;
	}

	public int[][] testData2() {

		int[][] map = new int[7][7];
		int start = -1;
		int end = -2;
		int obstacle = 1000000;
		map[5][1] = start;
		map[2][5] = end;
		for (int i = 1; i <= 5; i++) {
			map[i][3] = obstacle;
		}
		return map;
	}

	public void setStart(int[] start) {
		System.out.println(( origin[0] + start[0]) + "  " + ( origin[1] + start[1]));
		if (map[origin[1] +start[1]][origin[0] +start[0]] > 10) {
			this.start = start;
			this.start[0] = origin[0] + start[0];
			this.start[1] = origin[1] + start[1];
		}
	}

	public void setDangerZone(int[] dangerPoint) {
		if (this.dangerPointList == null)
			this.dangerPointList = new ArrayList<int[]>();
		this.dangerPointList.add(dangerPoint);
	}

	public ArrayList<int[]> getDangerZone() {
		return dangerPointList;
	}
	
	public int getMapHeight() {
		return map.length;
	}

	public int getMapWidth() {
		return map[0].length;
	}

	public int[][] createPGMMap(String fileName) throws IOException {
		String filePath = fileName;
		FileInputStream fileInputStream = new FileInputStream(filePath);
		Scanner scan = new Scanner(fileInputStream);
		// Discard the magic number
		scan.nextLine();
		// Discard the comment line
		scan.nextLine();
		// Read pic width, height and max value
		this.picWidth = scan.nextInt();
		this.picHeight = scan.nextInt();

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
		map = new int[picHeight][picWidth];

		int[] position = { picWidth / 2, picHeight / 2 };

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
		// 地圖旋轉+90度
		mapRotate90();

		position[0] -= leftEdge;
		position[1] -= topEdge;
		int[] goalPosition1 = { (position[0] - 127), (position[1] + 28) };
		int[] goalPosition2 = { (position[0] - 126), (position[1] - 93) };
		int[] goalPosition3 = { (position[0] - 42), (position[1] - 208) };

		System.out.println("position: " + position[1] + "  " + position[0]);
		goal.add(goalPosition1);
		goal.add(goalPosition2);
		goal.add(goalPosition3);

		this.origin = position; // 原點

		System.out.println("leftEdge: " + leftEdge);
		System.out.println("rightEdge: " + rightEdge);
		System.out.println("topEdge: " + topEdge);
		System.out.println("botEdge: " + botEdge);

//		設置起點、終點		
		this.start = origin; // 人所在的位置(起點) (預設)
//		this.goal = goalPosition;// 終點

		int newWidth = rightEdge - leftEdge + 1;
		int newHeight = botEdge - topEdge + 1;
		int[][] finalMap = new int[newHeight][newWidth];
		for (int row = 0; row < newHeight; row++) {
			for (int col = 0; col < newWidth; col++) {
				finalMap[row][col] = map[topEdge + row][leftEdge + col];
			}
		}
		map = finalMap;
		picWidth = map[0].length;
		picHeight = map.length;

		dis.close();
		scan.close();
		return map;
	}

	public int[][] createJPGMap(String fileName) {
//		FileInputStream fileInputStream = new FileInputStream(filePath);
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		picHeight = img.getHeight();
		picWidth = img.getWidth();

		map = new int[picHeight][picWidth];
		for (int i = 0; i < picHeight; i++) {
			for (int j = 0; j < picWidth; j++) {
				Color color = new Color(img.getRGB(j, i));
				map[i][j] = (int) ((color.getRed() + color.getGreen() + color.getBlue()) / 3);
			}
		}

//		原點
		int[] position = new int[2];
		position[0] = 179;
		position[1] = 314;
		int[] goalPosition1 = { (position[0] - 127), (position[1] + 28) };
		int[] goalPosition2 = { (position[0] - 126), (position[1] - 93) };
		int[] goalPosition3 = { (position[0] - 42), (position[1] - 208) };

		System.out.println("position: " + position[1] + "  " + position[0]);
		this.goal.add(goalPosition1);
		this.goal.add(goalPosition2);
		this.goal.add(goalPosition3);

		this.origin = position; // 原點
		this.start = origin;

		return map;
	}

	public void drawMap(LinkedList<Spot> path, int stepLength) {
//		繪圖-------------------------------------
		BufferedImage image = new BufferedImage(picWidth, picHeight, BufferedImage.TYPE_INT_RGB);

		System.out.println("x :" + picWidth);
		System.out.println("y :" + picHeight);

		for (int row = 0; row < picHeight; row++) {
			for (int col = 0; col < picWidth; col++) {
//				將圖片的每一點轉為RGB儲存在image裡
				int a = map[row][col];
				Color newColor = new Color(a, a, a);
				image.setRGB(col, row, newColor.getRGB());
			}
		}

//		標記人在哪以及出口位置(從原點出發，x+ 方向往右，y+ 方向往下)
//		一般二維矩陣row代表y軸，col代表x軸，所以在setRGB2的參數中需替換過來(如84行)
		int startColor = new Color(255, 0, 255).getRGB(); // 紅色
		int endColor = new Color(0, 255, 0).getRGB(); // 綠色
		int[] door1 = new int[] { origin[0] - 127, origin[1] + 28 };
		int[] door2 = new int[] { origin[0] - 126, origin[1] - 93 };
		int[] door3 = new int[] { origin[0] - 42, origin[1] - 208 };

		int pointSize = 3;
		for (int i = -pointSize; i <= pointSize; i++) {
			for (int j = -pointSize; j <= pointSize; j++) {
				if ((Math.pow(i, 2) + Math.pow(j, 2) <= Math.pow(pointSize, 2))) {
					image.setRGB(start[0] + j, start[1] + i, startColor);
					image.setRGB(door1[0] + j, door1[1] + i, new Color(0, 0, 255).getRGB());
					image.setRGB(door2[0] + j, door2[1] + i, endColor);
					image.setRGB(door3[0] + j, door3[1] + i, endColor);
				}
			}
		}

		int dangerZoneSize = 35;
		if (dangerPointList != null) {
			for (int number = 0; number < dangerPointList.size(); number++) {
				for (int i = -dangerZoneSize; i <= dangerZoneSize; i++) {
					for (int j = -dangerZoneSize; j <= dangerZoneSize; j++) {
						int x = dangerPointList.get(number)[0] + j;
						int y = dangerPointList.get(number)[1] + i;
						if ((x >= 0) && (x <= picWidth) && (y >= 0) && (y <= picHeight))
							image.setRGB(x, y, new Color(0, 155, 155).getRGB());
					}
				}
			}
		}

		int number = path.size();
		for (int i = 0; i < number; i++) {
			int rgb = new Color(255, 0, 0).getRGB();
			int x = path.get(i).getCoordinate(1) * stepLength;
			int y = path.get(i).getCoordinate(0) * stepLength;
			for (int j = -pointSize; j <= pointSize; j++) {
				for (int k = -pointSize; k <= pointSize; k++) {
					if ((Math.pow(j, 2) + Math.pow(k, 2) <= Math.pow(pointSize, 2))) {
						image.setRGB(x + k, y + j, rgb);
					}
				}
			}
		}

//		儲存圖片
//		File outputFile = new File("fireHouseResult.jpg");
//		try {
//			ImageIO.write(image, "jpg", outputFile);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		JFrame frame = new JFrame();
		ImageIcon icon = new ImageIcon(image);
		int height = icon.getIconHeight();
		int width = icon.getIconWidth();
		JLabel label = new JLabel(icon);

		label.setLocation(100, 100);
		label.setSize(height + 400, width + 400);
		frame.setSize(height + 400, width + 400);
		frame.add(label);
		frame.setVisible(true);

//		System.out.println("leftEdge: " + leftEdge);
//		System.out.println("rightEdge: " + rightEdge);
//		System.out.println("topEdge: " + topEdge);
//		System.out.println("botEdge: " + botEdge);
	}

	public static void mapRotate90() {
		int originHeight = map.length;
		int originWidth = map[0].length;
		int[][] newMap = new int[originWidth][originHeight];
		for (int i = 0; i < originWidth; i++) {
			for (int j = 0; j < originHeight; j++) {
				newMap[i][j] = map[j][originWidth - i - 1];
			}
		}
		map = newMap;
		int tempLeft = leftEdge;
		int tempRight = rightEdge;
		leftEdge = topEdge;
		rightEdge = botEdge;
		topEdge = originWidth - tempRight;
		botEdge = originWidth - tempLeft;

	}

//	public static void dilation(int[][] map) {
//		int[][] newMap = new int[map.length + 2][map[0].length + 2];
//		for(int i=0;i<map.length;i++) {
//			for(int j=0;j<map[0].length;j++) {
//				newMap[i+1][j+1] = map[i][j];
//			}
//		}
//		for (int i = 0; i < newMap.length; i++) {
//			for (int j = 0; j < newMap[0].length; j++) {
//				if (newMap[i][j] == mapBGColor && 
//					(  newMap[i - 1][j]     == 255 || newMap[i - 1][j - 1] == 255 || newMap[i][j - 1]     == 255
//					|| newMap[i + 1][j - 1] == 255 || newMap[i + 1][j]     == 255 || newMap[i + 1][j + 1] == 255
//					|| newMap[i][j + 1]     == 255 || newMap[i - 1][j + 1] == 255)) {
//					map[i- 1][j- 1] = 255;
//				}
//			}
//		}
//	}

}
