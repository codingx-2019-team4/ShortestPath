import java.util.ArrayList;

public class SpotSort {
	private ArrayList spots = new ArrayList();
	
	public int length() {
		return this.spots.size();
	}
	
	public boolean contains(Spot spot) {   //判斷Spot是否在ArrayList裡
		for(int i=0; i< spots.size();i++) {
			if(spots.get(i) == spot)
				return true;
		}
		return false;
	}
	
	public void push(Spot spot) {
		this.spots.add(spot);
		this.spots.sort(c);  //為何要排序??
	}
	
	public void remove(Spot spot) {
		this.spots.remove(spot);
		this.spots.sort(c);  //為何要排序??
	}
}
