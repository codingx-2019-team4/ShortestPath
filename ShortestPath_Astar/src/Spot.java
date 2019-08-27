
public class Spot {
	public int g; // �_�I�H�۸��|���즹�I���Z��
	public int h; // ���I����I���Z��(�w�����A�����ê��)
	private int now_x;   //�{�b��x��
	private int now_y;   //�{�b��y��
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
			return -1;   //��L��spot�Ȥ�{�b���j�A����L
		else
			return 1;    //��L��spot�Ȥ�{�b���p�A����ܥL�B��
	}

}
