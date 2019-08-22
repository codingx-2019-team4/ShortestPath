import java.util.ArrayList;

public class Astar {
	public static ArrayList<Spot> openList;
	public static ArrayList<Spot> closedList;
	
	private static int hCost(Spot curSpot, Spot goalSpot) {
		int h = Math.abs(goalSpot.getCoordinate()[0] - curSpot.getCoordinate()[0]) 
				+ Math.abs(goalSpot.getCoordinate()[1] - curSpot.getCoordinate()[1]);
		return h;
	}
	
	
}
