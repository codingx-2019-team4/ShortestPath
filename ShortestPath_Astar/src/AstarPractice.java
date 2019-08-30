
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AstarPractice {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		TestData1-----------------------------------
		/*
		 * Information testData = new Information(); int[][] map = testData.testData1();
		 * int mapH = map.length; int mapW = map[0].length; Spot[][] spotMap = new
		 * Spot[mapH][mapW]; Spot start = null; Spot goal = null; for (int row = 0; row
		 * < mapH; row++) { for (int col = 0; col < mapW; col++) { spotMap[row][col] =
		 * new Spot(row, col); if (map[row][col] == -1) // 起點 start = spotMap[row][col];
		 * else if (map[row][col] == -2) // 終點 goal = spotMap[row][col]; } }
		 * LinkedList<Spot> path = new LinkedList<Spot>(); Astar a = new Astar(spotMap);
		 * path = a.findPath(start, goal);
		 * 
		 * System.out.println("TestData:"); for(int i=0;i<path.size();i++) {
		 * System.out.println(path.get(i).getCoordinate(0) + "  " +
		 * path.get(i).getCoordinate(1)); }
		 */

//		TestData2-----------------------------------
		/*
		 * Information testData2 = new Information(); int[][] map =
		 * testData2.testData2(); int mapH = map.length; int mapW = map[0].length;
		 * Spot[][] spotMap = new Spot[mapH][mapW]; Spot start = null; Spot goal = null;
		 * for (int row = 0; row < mapH; row++) { for (int col = 0; col < mapW; col++) {
		 * spotMap[row][col] = new Spot(row, col); if (map[row][col] == -1) // 起點 start
		 * = spotMap[row][col]; else if (map[row][col] == -2) // 終點 goal =
		 * spotMap[row][col]; else if(map[row][col] > 1000)
		 * spotMap[row][col].setObstacle(true); } } LinkedList<Spot> path = new
		 * LinkedList<Spot>(); Astar a = new Astar(spotMap); path = a.findPath(start,
		 * goal);
		 * 
		 * System.out.println("TestData:"); for(int i=0;i<path.size();i++) {
		 * System.out.println(path.get(i).getCoordinate(0) + "  " +
		 * path.get(i).getCoordinate(1)); }
		 */

//		MapData-----------------------------------
//		一個終點
		long startTime = System.nanoTime();
		Information mapData = new Information();
		int[][] map = mapData.createMap("fireHouse2.pgm");
		int mapH = map.length;
		int mapW = map[0].length;
		Spot[][] spotMap = new Spot[mapH][mapW];

		for (int row = 0; row < mapH; row++) {
			for (int col = 0; col < mapW; col++) {
				spotMap[row][col] = new Spot(row, col);
				if (map[row][col] > 1000)
					spotMap[row][col].setObstacle(true);
			}
		}
		Spot start = spotMap[mapData.start[0]][mapData.start[1]];
		Spot goal = spotMap[mapData.goal[0]][mapData.goal[1]];

		LinkedList<Spot> path = new LinkedList<Spot>();
		Astar a = new Astar(spotMap);
		path = a.findPath(start, goal);

		System.out.println("TestData:");
		for (int i = 0; i < path.size(); i++) {
			System.out.println(path.get(i).getCoordinate(0) + "  " + path.get(i).getCoordinate(1));
		}

		mapData.drawMap();
		long endTime = System.nanoTime();
		System.out.println(endTime - startTime);

	}

}
