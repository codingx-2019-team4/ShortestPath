import java.util.ArrayList;

public class AstarPractice {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Information testData = new Information();
		int[][] map = testData.testData1();
		int mapH = map.length;
		int mapW = map[0].length;
		Spot[][] spotMap = new Spot[mapH][mapW];
		Spot start = null;
		Spot goal = null;
		for(int row=0;row<mapH;row++) {
			for(int col=0;col<mapW;col++) {
				spotMap[row][col] = new Spot(row,col);
				if(map[row][col] == -1)   //°_ÂI
					start = spotMap[row][col];
				else if(map[row][col] == -2) //²×ÂI
					goal = spotMap[row][col];
			}
		}
		ArrayList a = new ArrayList();
		Astar b = new Astar();
		a = b.findPath(start, goal);
	}

}
