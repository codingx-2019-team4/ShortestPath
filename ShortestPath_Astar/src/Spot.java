
public class Spot {
	private int g; // �_�I�H�۸��|���즹�I���Z��
	private int h; // ���I����I���Z��(�w�����A�����ê��)
	private int now_x;   //�{�b��x��
	private int now_y;   //�{�b��y��
	private boolean chosen;
	private boolean nextStep;
	private boolean isObstacle;
	private int item;
	private Spot parent = new Spot();

	
	public void Spot() {
		this.g = 0;
		this.h = 0;
		this.isObstacle = false;
		parent = null;
	}
	public void Spot(int now_x, int now_y) {
		this.g = 0;
		this.h = 0;
		this.now_x = now_x;
		this.now_y = now_y;
		this.chosen = false;
		this.nextStep = false;
		this.isObstacle = false;
		parent = null;
	}
	
	public int[] getCoordinate() {
		int[] coordinate = {now_x,now_y};
		return coordinate;
	}
	
	public void becomeObstacle() {
		this.isObstacle = true;
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
