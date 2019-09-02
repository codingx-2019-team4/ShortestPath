import java.util.LinkedList;

public class Astar {
	public SpotSort openList; // 待處理的點
	public SpotSort closedList; // 不需要再次檢查的點
	public Spot[][] map = null;

	public Astar(Spot[][] map) {
		this.map = map;
	}

	private int hCost(Spot curSpot, Spot goalSpot) {
		int xDis = Math.abs(goalSpot.getCoordinate(1) - curSpot.getCoordinate(1));
		int yDis = Math.abs(goalSpot.getCoordinate(0) - curSpot.getCoordinate(0));
		int h = xDis + yDis;
		if (xDis == 0 || yDis == 0) // 在x軸或是y軸方向
			return h * 10;
		else { // 非x軸或y軸方向
			int slash = 0; // 斜線方向
			int sLine = 0; // 直線方向
			if (xDis > yDis) {
				slash = yDis;
				sLine = xDis - yDis;

			} else {
				slash = xDis;
				sLine = yDis - xDis;
			}
			return (slash * 14 + sLine * 10);

		}
	}

	public LinkedList<Spot> findPath(Spot start, Spot goal) {
		openList = new SpotSort();
		openList.push(start);
		start.g = 0;
		start.h = hCost(start, goal);

		closedList = new SpotSort();
		Spot spot = null; // 每次f值最小的點

//		當openList裡沒有元素時代表找不到路徑
		while (openList.length() != 0) {
			spot = openList.getFirst();
			closedList.push(spot);
			openList.remove(spot);

//			到終點則計算路程
			if (spot.getCoordinate() == goal.getCoordinate())
				return calculatePath(spot);

//			還沒到終點就先找目前點的鄰居
			LinkedList<Spot> neighbours = new LinkedList<Spot>();
			neighbours = getNeighbours(spot);

			for (int i = 0; i < neighbours.size(); i++) {
				Spot neighbourNode = neighbours.get(i);

//				closedList中沒有neighbourNode，則繼續計算
				if (!closedList.contains(neighbourNode)) {
					int cost; // 上個點到目前位置的距離
					int totalCost; // 起點到目前位置的距離
					int neighbourHCost;// 鄰居到終點的預測距離(包括障礙物)

//					若鄰居點還沒排進openList，計算它的cost

//					G cost
					cost = hCost(spot, neighbourNode);
					totalCost = spot.g + cost;
					if (!openList.contains(neighbourNode)) {
//						H cost
						neighbourHCost = hCost(neighbourNode, goal);

						neighbourNode.g = totalCost;
						neighbourNode.parent = spot;
						neighbourNode.h = neighbourHCost;

						openList.push(neighbourNode);
					} else {
						if (neighbourNode.g > totalCost) {
							neighbourNode.g = totalCost;
							neighbourNode.parent = spot;
						}
					}
				}
			}
		}
		if (spot.getCoordinate() != goal.getCoordinate()) {
			System.out.println("Goal not found");
			return null;
		}
		return calculatePath(spot);
	}

	private LinkedList<Spot> calculatePath(Spot spot) {
		LinkedList<Spot> list = new LinkedList<Spot>();
		while (spot != null) {
			list.addFirst(spot);
			spot = spot.parent;
		}
		return list;
	}

//	目前只先算前後左右(沒有斜線)
	private LinkedList<Spot> getNeighbours(Spot spot) {
		LinkedList<Spot> neightbours = new LinkedList<Spot>();
		int posRow = spot.getCoordinate(0);
		int posCol = spot.getCoordinate(1);
//		System.out.println(posRow + " " + posCol);
//		System.out.println("--------------------");
//		逆時針加入鄰居
//		上鄰居
		if ((posRow > 0) && (map[posRow - 1][posCol].isObstacle() == false))
			neightbours.add(map[posRow - 1][posCol]);
//		左上鄰居
		if ((posRow > 0) && (posCol > 0) && (map[posRow - 1][posCol - 1].isObstacle() == false))
			neightbours.add(map[posRow - 1][posCol - 1]);
//		 左鄰居
		if ((posCol > 0) && (map[posRow][posCol - 1].isObstacle() == false))
			neightbours.add(map[posRow][posCol - 1]);
//		左下鄰居
		if ((posRow < map.length - 1) && (posCol > 0) && (map[posRow + 1][posCol - 1].isObstacle() == false) )
			neightbours.add(map[posRow + 1][posCol - 1]);
//		下鄰居
		if ((posRow < map.length - 1) && (map[posRow + 1][posCol].isObstacle() == false))
			neightbours.add(map[posRow + 1][posCol]);
//		右下鄰居
		if ((posRow < map.length - 1) && (posCol < map[0].length - 1) && (map[posRow + 1][posCol + 1].isObstacle() == false))
			neightbours.add(map[posRow + 1][posCol + 1]);
//		右鄰居
		if ((posCol < map[0].length - 1) && (map[posRow][posCol + 1].isObstacle() == false))
			neightbours.add(map[posRow][posCol + 1]);
//		右上鄰居
		if ((posCol < map[0].length - 1) && (map[posRow - 1][posCol + 1].isObstacle() == false) && (posRow > 0))
			neightbours.add(map[posRow - 1][posCol + 1]);

		return neightbours;
	}
}
