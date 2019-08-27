
public class Spot {
	public int g; // 起點隨著路徑走到此點的距離
	public int h; // 此點到終點的距離(預期的，不算障礙物)
	private int now_x;   //現在的x值
	private int now_y;   //現在的y值
//	private boolean chosen;
//	private boolean nextStep;
	private boolean obstacle;
//	private int item;
	private Spot parent;

	
	public Spot() {
		this.g = 0;
		this.h = 0;
		this.obstacle = false;
		parent = null;
	}
	
	
	public Spot(int now_x, int now_y) {
		this.g = 0;
		this.h = 0;
		this.now_x = now_x;
		this.now_y = now_y;
//		this.chosen = false;
//		this.nextStep = false;
		this.obstacle = false;
		parent = null;
	}
	
	public int[] getCoordinate() {
		int[] coordinate = {now_x,now_y};
		return coordinate;
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

	public int compareTo(Spot spot) {
		if(this.g + this.h < spot.g + spot.h)
			return -1;   //其他的spot值比現在的大，不選他
		else
			return 1;    //其他的spot值比現在的小，先選擇他運算
	}

}
