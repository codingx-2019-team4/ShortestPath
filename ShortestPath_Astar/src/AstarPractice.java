
import java.util.LinkedList;

public class AstarPractice {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*
		Information testData = new Information();
		int[][] map = testData.testData1();
		int mapH = map.length;
		int mapW = map[0].length;
		Spot[][] spotMap = new Spot[mapH][mapW];
		Spot start = null;
		Spot goal = null;
		for (int row = 0; row < mapH; row++) {
			for (int col = 0; col < mapW; col++) {
				spotMap[row][col] = new Spot(row, col);
				if (map[row][col] == -1) // 起點
					start = spotMap[row][col];
				else if (map[row][col] == -2) // 終點
					goal = spotMap[row][col];
			}
		}
		LinkedList<Spot> path = new LinkedList<Spot>();
		Astar a = new Astar(spotMap);
		path = a.findPath(start, goal);
		
		System.out.println("TestData:");
		for(int i=0;i<path.size();i++) {
			System.out.println(path.get(i).getCoordinate(0) + "  " + path.get(i).getCoordinate(1));
		}*/
		
		Information testData2 = new Information();
		int[][] map = testData2.testData2();
		int mapH = map.length
				;
		int mapW = map[0].length;
		Spot[][] spotMap = new Spot[mapH][mapW];
		Spot start = null;
		Spot goal = null;
		for (int row = 0; row < mapH; row++) {
			for (int col = 0; col < mapW; col++) {
				spotMap[row][col] = new Spot(row, col);
				if (map[row][col] == -1) // 起點
					start = spotMap[row][col];
				else if (map[row][col] == -2) // 終點
					goal = spotMap[row][col];
				else if(map[row][col] > 1000)
					spotMap[row][col].setObstacle(true);
			}
		}
		LinkedList<Spot> path = new LinkedList<Spot>();
		Astar a = new Astar(spotMap);
		path = a.findPath(start, goal);
		
		System.out.println("TestData:");
		for(int i=0;i<path.size();i++) {
			System.out.println(path.get(i).getCoordinate(0) + "  " + path.get(i).getCoordinate(1));
		}

	}

}
