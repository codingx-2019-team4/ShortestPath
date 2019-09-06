package A_star_algorithm;

public class Spot {
	public int g; // 起點隨著路徑走到此點的距離
	public int h; // 此點到終點的距離(預期的，不算障礙物)
	private int[] coordinate = new int[2];
	private boolean obstacle;
	public Spot parent;

	public Spot() {
		this.g = 0;
		this.h = 0;
		this.obstacle = false;
		parent = null;
	}

	public Spot(int now_x, int now_y) {
		this.g = 0;
		this.h = 0;
		coordinate[0] = now_x;
		coordinate[1] = now_y;
		this.obstacle = false;
		parent = null;
	}

	public int[] getCoordinate() {
		return coordinate;
	}

	public int getCoordinate(int index) {
		return coordinate[index];
	}

	public void setCoordinate(int x, int y) {
		coordinate[0] = x;
		coordinate[1] = y;
	}

	public void setObstacle(boolean b) {
		this.obstacle = b;
	}

	public boolean isObstacle() {
		return obstacle;
	}

	public boolean isEmpty() {
		if (parent == null)
			return true;
		else
			return false;
	}

//	public int compareTo(Spot spot) {
//		if(this.g + this.h < spot.g + spot.h)
//			return -1;   //其他的spot值比現在的大，不選他
//		else
//			return 1;    //其他的spot值比現在的小，先選擇他運算
//	}
}
