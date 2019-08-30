
import java.util.LinkedList;

public class SpotSort {
	private LinkedList<Spot> spotList = new LinkedList<Spot>();

	public LinkedList<Spot> getList() {
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
		if (spotList.size() > 0) {
			Spot min = spotList.get(0);
			int pos = 0;
			for (int i = 0; i < spotList.size() - 1; i++) {
				if (min.g + min.h > spotList.get(i + 1).g + spotList.get(i + 1).h) {
					min = spotList.get(i + 1);
					pos = i + 1;
				}
			}
			if (min != spotList.get(0)) {
				spotList.remove(pos);
				spotList.addFirst(min);
			}
		}
	}

}
