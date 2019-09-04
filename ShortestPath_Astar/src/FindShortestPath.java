
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class FindShortestPath {
	private static ServerSocket m_serverSocket;// 伺服器端的Socket，接收Client端的連線
	private static Socket m_socket;// Server和Client之間的連線通道

	private static String mapFileName = "fireHouse2.pgm";
//	private static int[] startPoint;
//	private static int[] goalPoint;

	public FindShortestPath() {
	}

	public void setMapFileName(String fileName) {
		mapFileName = fileName;
	}
//	public void setStart(int[] start) {
//		startPoint = start;
//	}
//	public void setGoal(int[] goal) {
//		goalPoint = goal;
//	}

	public static void main(String[] args) throws IOException {
//
		long startTime = System.currentTimeMillis() / 1000; // 秒
		// TODO Auto-generated method stub
//		TestData1-----------------------------------

//		Information testData = new Information();
//		int[][] map = testData.testData1();
//		int mapH = map.length;
//		int mapW = map[0].length;
//		Spot[][] spotMap = new Spot[mapH][mapW];
//		Spot start = null;
//		Spot goal = null;
//		for (int row = 0; row < mapH; row++) {
//			for (int col = 0; col < mapW; col++) {
//				spotMap[row][col] = new Spot(row, col);
//				if (map[row][col] == -1) // 起點
//					start = spotMap[row][col];
//				else if (map[row][col] == -2) // 終點
//					goal = spotMap[row][col];
//			}
//		}
//		LinkedList<Spot> path = new LinkedList<Spot>();
//		Astar a = new Astar(spotMap);
//		path = a.findPath(start, goal);
//
//		System.out.println("TestData:");
//		for (int i = 0; i < path.size(); i++) {
//			System.out.println(path.get(i).getCoordinate(0) + "  " + path.get(i).getCoordinate(1));
//		}
//		testData.drawMap(path);

//		TestData2-----------------------------------

//		Information testData2 = new Information();
//		int[][] map = testData2.testData2();
//		int mapH = map.length;
//		int mapW = map[0].length;
//		Spot[][] spotMap = new Spot[mapH][mapW];
//		Spot start = null;
//		Spot goal = null;
//		for (int row = 0; row < mapH; row++) {
//			for (int col = 0; col < mapW; col++) {
//				spotMap[row][col] = new Spot(row, col);
//				if (map[row][col] == -1) // 起點
//					start = spotMap[row][col];
//				else if (map[row][col] == -2) // 終點
//					goal = spotMap[row][col];
//				else if (map[row][col] > 1000)
//					spotMap[row][col].setObstacle(true);
//			}
//		}
//		LinkedList<Spot> path = new LinkedList<Spot>();
//		
//		Astar a = new Astar(spotMap);
//		path = a.findPath(start, goal);
//
//		System.out.println("TestData:");
//		for (int i = 0; i < path.size(); i++) {
//			System.out.println(path.get(i).getCoordinate(0) + "  " + path.get(i).getCoordinate(1));
//		}

//		MapData-----------------------------------
//		一個終點
		Information mapData = new Information();
		int[][] map = mapData.createMap(mapFileName);

//		輸出地圖檔
//		try {
//			File filename = new File("new map.txt");
//			filename.createNewFile();
//			BufferedWriter out = new BufferedWriter(new FileWriter(filename));
//		
//			for(int i=0;i<map.length;i++) {
//				for(int j=0;j<map[0].length;j++) {
//					out.write(String.valueOf(map[i][j]));		
//					out.write(" ");
//				}
//				out.write("\n");
//			}
//			out.flush();
//			out.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		int mapH = map.length;
		int mapW = map[0].length;
		int stepLength = 5;
		int[][] dividedMap = new int[(mapH / stepLength) + 1][(mapW / stepLength) + 1];
		
		System.out.println(mapH + "   " + mapW);

//		將int地圖轉換為spot地圖
		Spot[][] spotMap = new Spot[mapH][mapW];
		Spot[][] dividedSpotMap = new Spot[dividedMap.length][dividedMap[0].length];
		
		System.out.println(dividedMap.length + "   " + dividedMap[0].length);
		
		boolean obstacle = false;
		for (int row = 0; row < mapH; row++) {
			for (int col = 0; col < mapW; col++) {
				spotMap[row][col] = new Spot(row, col);
				if (map[row][col] < 10) {
					spotMap[row][col].setObstacle(true);
					obstacle = true;
//					System.out.println("row: " + row + "   col: " + col);
				}
				if(row % stepLength == 0 || col % stepLength == 0) {
					dividedSpotMap[row / stepLength][col / stepLength] = new Spot(row / stepLength,col / stepLength);
					if(obstacle == true) {
						dividedSpotMap[row / stepLength][col / stepLength].setObstacle(true);
						obstacle = false;
					}
				}
			}
		}
//
//		Spot start = spotMap[mapData.start[1]][mapData.start[0]];
//		Spot goal = spotMap[mapData.goal[1]][mapData.goal[0]];

		LinkedList<Spot> path = new LinkedList<Spot>();
//		Astar a = new Astar(spotMap);
//		path = a.findPath(start, goal);
		
		Spot start = dividedSpotMap[mapData.start[1] / stepLength][mapData.start[0] / stepLength];
		Spot goal = dividedSpotMap[mapData.goal[1] / stepLength][mapData.goal[0] / stepLength];
		
		System.out.println("start:" + start.getCoordinate(0) + "," + start.getCoordinate(1) + "   goal:" + goal.getCoordinate(0) + "," + goal.getCoordinate(1));
		
		Astar b = new Astar(dividedSpotMap);
		path = b.findPath(start, goal);

		long endTime = System.currentTimeMillis() / 1000;

		int[][] pathMap = new int[path.size()][2]; // 最短路徑傳給app端
//		System.out.println("TestData:");
		
		for(int i=0;i<pathMap.length;i++) {
			for(int j=0;j<pathMap[0].length;j++) {
				pathMap[i][j] *= stepLength;
			}
		}
		
//		for (int i = 0; i < path.size(); i++) {
////			System.out.println(path.get(i).getCoordinate(0) + "  " + path.get(i).getCoordinate(1));
//			pathMap[i][0] = path.get(i).getCoordinate(1);
//			pathMap[i][1] = path.get(i).getCoordinate(0);
//		}
		
		for (int i = 0; i < path.size(); i++) {
//			System.out.println(path.get(i).getCoordinate(0) + "  " + path.get(i).getCoordinate(1));
			pathMap[i][0] = path.get(i).getCoordinate(1) * stepLength;
			pathMap[i][1] = path.get(i).getCoordinate(0) * stepLength;
			System.out.println(path.get(i).getCoordinate(1) + "  " + path.get(i).getCoordinate(0));
		}

//		try {
//		File filename = new File("Door1 Path.txt");
//		filename.createNewFile();
//		BufferedWriter out = new BufferedWriter(new FileWriter(filename));
//	
//		for(int i=0;i<pathMap.length;i++) {
//			for(int j=0;j<pathMap[0].length;j++) {
//				out.write(String.valueOf(pathMap[i][j]));		
//				out.write(" ");
//			}
//			out.write("\n");
//		}
//		out.flush();
//		out.close();
//	} catch (Exception e) {
//		e.printStackTrace();
//	}

		mapData.drawMap(path,stepLength);
		System.out.println("總共多少秒: " + (endTime - startTime));
		connectAPP(pathMap);
		
	}
	
	public static void connectAPP(int[][] pathMap) {
		try {
			m_serverSocket = new ServerSocket(8088);// 建立伺服器端的Socket，並且設定Port
			System.out.println("等待連線......");
			while (true) {
				m_socket = m_serverSocket.accept();// 等待伺服器端的連線，若未連線則程式一直停在這裡
				System.out.println("連線成功！");
				new Thread(new ServerThreadCode(m_socket,pathMap)).start();// 建立物件，傳入Port並執行等待接受連線的動作
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());// 出現例外時，捕捉並顯示例外訊息
		}
	}

}
