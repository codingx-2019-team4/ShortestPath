
import java.util.LinkedList;

public class SpotSort {
	private LinkedList<Spot> spotList = new LinkedList<Spot>();

	public LinkedList<Spot> getList(){
		return spotList;
	}
	
	public int length() {
		return this.spotList.size();
	}
	public Spot getFirst() {
		return spotList.getFirst();
	}

	public boolean contains(Spot spot) { // 這個spot有沒有在列表裡
		for (int i = 0; i < spotList.size(); i++) {
			if (spotList.get(i) == spot)
				return true;
		}
		return false;
	}

	public void push(Spot spot) {
		this.spotList.addFirst(spot);
		spotListSort();
	}

	public void remove(Spot spot) {
		this.spotList.remove(spot);
		spotListSort();
	}

	private void spotListSort() {
		for (int i = 0; i < spotList.size(); i++) {
			for (int j = 0; j < spotList.size() -1- i; j++) {
				if(spotList.get(j).g + spotList.get(j).h > spotList.get(j+1).g + spotList.get(j+1).h) {
					Spot temp = spotList.get(j+1);
					spotList.remove(j+1);
					spotList.add(j,temp);
				}
			}
		}
	}
}
