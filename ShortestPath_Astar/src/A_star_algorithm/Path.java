package A_star_algorithm;

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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Path {
	private static String mapFileName = null;
//	private static int[] startPoint;
//	private static int[] goalPoint;
	private Information mapData = new Information();
	int[][] map;

	public Path() {
	}

	public void setMapFileName(String fileName) throws IOException {
		mapFileName = fileName;
		if (fileName.lastIndexOf(".pgm") >= 0) {
			map = mapData.createPGMMap(mapFileName);
			System.out.println("YES");
		} else if (fileName.lastIndexOf(".jpg") > 0) {
			map = mapData.createJPGMap(fileName);
			System.out.println("NO");
		}
	}

	public void setStart(int[] start) {

		mapData.start = start;
	}

	public void setGoal(int[] goal) {
		mapData.goal.add(goal);
	}

	public Information getInformation() {
		return mapData;
	}

	public int[][] findShortestPath() {
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

		int mapH = map.length;
		int mapW = map[0].length;

//		為了加快計算最短路徑的速度，將原地圖劃分為更小的地圖
		int stepLength = 5; // 原地圖 : 切割後地圖 = stepLength : 1
		int[][] dividedMap = new int[((mapH - 1) / stepLength) + 1][((mapW - 1) / stepLength) + 1];

		System.out.println(mapH + "   " + mapW);

//		將int地圖轉換為spot地圖
		Spot[][] spotMap = new Spot[mapH][mapW];
		Spot[][] dividedSpotMap = new Spot[dividedMap.length][dividedMap[0].length];

		System.out.println("dividedMap:" + dividedMap.length + "   " + dividedMap[0].length);
		
		boolean obstacle = false;
		for (int row = 0; row < mapH; row++) {
			for (int col = 0; col < mapW; col++) {
				spotMap[row][col] = new Spot(row, col);
				if (map[row][col] < 10 || map[row][col] == 205) { // 小於10的值設為障礙物
					spotMap[row][col].setObstacle(true);
					obstacle = true;
				}
				if (row % stepLength == 0 || col % stepLength == 0) {
					dividedSpotMap[row / stepLength][col / stepLength] = new Spot(row / stepLength, col / stepLength);
					if ((obstacle == true) || (spotMap[row][col].isObstacle() == true)) {
						dividedSpotMap[row / stepLength][col / stepLength].setObstacle(true);
						obstacle = false;
					}
				}
			}
		}
		
		int dangerZoneSize = 35;
		ArrayList<int[]> dangerPointList = mapData.getDangerZone();
		if (dangerPointList != null) {
			for (int number = 0; number < dangerPointList.size(); number++) {
				for (int i = -dangerZoneSize; i <= dangerZoneSize; i++) {
					for (int j = -dangerZoneSize; j <= dangerZoneSize; j++) {
						int x = dangerPointList.get(number)[0] + j;
						int y = dangerPointList.get(number)[1] + i;
						if ((x >= 0) && (x <= mapW) && (y >= 0) && (y <= mapH)) {
							spotMap[y][x].setObstacle(true);
						}
						if(x % stepLength == 0 || y % stepLength == 0)
							dividedSpotMap[y / stepLength][x / stepLength].setObstacle(true);
					}
				}
			}
		}
		
		LinkedList<Spot> path = new LinkedList<Spot>();
		LinkedList<LinkedList<Spot>> pathList = new LinkedList<LinkedList<Spot>>();
		Spot start = dividedSpotMap[mapData.start[1] / stepLength][mapData.start[0] / stepLength];

		int minFValue =(int) 1e10;
		int index = 0;
		for (int i = 0; i < mapData.goal.size(); i++) {
			int nowFValue = 0;
			Spot goal = dividedSpotMap[mapData.goal.get(i)[1] / stepLength][mapData.goal.get(i)[0] / stepLength];
			System.out.println("start:" + start.getCoordinate(0) + "," + start.getCoordinate(1) + "   goal:"
					+ goal.getCoordinate(0) + "," + goal.getCoordinate(1));
			Astar star = new Astar(dividedSpotMap);
			path = star.findPath(start, goal);
			pathList.add(i,path);
			
			nowFValue = goal.g + goal.h;
			System.out.println("F:" + nowFValue);
			if(path == null)
				continue;
			if(minFValue > nowFValue) {
				index = i;
				minFValue = nowFValue;
			}
		}
		
		LinkedList<Spot> shortestPath = pathList.get(index);

		long endTime = System.currentTimeMillis() / 1000;
		int[][] pathMap = new int[shortestPath.size()][2]; // 最短路徑傳給app端

//		將切割後地圖的最短路徑還原為原地圖的解析度
		for (int i = 0; i < pathMap.length; i++) {
			for (int j = 0; j < pathMap[0].length; j++) {
				pathMap[i][j] *= stepLength;
			}
		}

		for (int i = 0; i < shortestPath.size(); i++) {
			pathMap[i][0] = shortestPath.get(i).getCoordinate(1) * stepLength;
			pathMap[i][1] = shortestPath.get(i).getCoordinate(0) * stepLength;
			System.out.println(shortestPath.get(i).getCoordinate(1) + "  " + shortestPath.get(i).getCoordinate(0));
		}

		mapData.drawMap(shortestPath, stepLength);
		System.out.println("總共多少秒: " + (endTime - startTime));

		return pathMap;
	}

	public static void saveMap(String fileName, int[][] map) {
		try {
			File file = new File(fileName);
			file.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(file));

			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[0].length; j++) {
					out.write(String.valueOf(map[i][j]));
					out.write(" ");
				}
				out.write("\n");
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
