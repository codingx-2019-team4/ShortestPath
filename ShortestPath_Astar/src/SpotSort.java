
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

	private int count = 0;
	public void push(Spot spot) {
//		count ++;
//		System.out.println("Count: " + count);
		this.spotList.add(spot);
//		System.out.print("Push: ");
//		System.out.print(spot.g + spot.h + "   ");
		spotListSort();
	}

	public void remove(Spot spot) {
		this.spotList.remove(spot);
//		System.out.print("Remove: ");
//		spotListSort();
	}

	private void spotListSort() {
//		System.out.println(spotList.size());

//		更久
//		int nowFCost = spotList.getFirst().g + spotList.getFirst().h;
//		for (int i = 1; i < spotList.size(); i++) {
//			if (nowFCost <= spotList.get(i).g + spotList.get(i).h) {
//				spotList.add(i, spotList.getFirst());
//				spotList.remove(0);
//				break;
//			} else if (i == spotList.size() - 1) {
//				spotList.add(i + 1, spotList.getFirst());
//				spotList.remove(0);
//				break;
//			}
//		}

//		for (int i = 0; i < spotList.size(); i++) {
//			for (int j = 0; j < spotList.size() -1- i; j++) {
//				if(spotList.get(j).g + spotList.get(j).h > spotList.get(j+1).g + spotList.get(j+1).h) {
//					Spot temp = spotList.get(j+1);
//					spotList.remove(j+1);
//					spotList.add(j,temp);
//				}
//			}
//		}

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

//		int number = spotList.size();
//		System.out.print(number + "    ");
//		if (number > 1) {
//			Spot key = spotList.get(number - 1);
//			int keyF = key.g + key.h;
//			int left = 0;
//			int right = number - 2;
//			boolean judge = true;
////			System.out.print((spotList.get(number - 2).g + spotList.get(number - 2).h)  + "      ");
//			if (keyF > (spotList.get(number - 2).g + spotList.get(number - 2).h)) {
//
//				judge = false;
//			}
////			System.out.print(keyF + "    ");
////			if (number == 3) {
////
////				System.out.print(judge + "   ");
////			}
//			while (judge != false) {
//				int middle = (int) Math.ceil((float) (left + right) / 2);
//				int middleSpotF = spotList.get(middle).g + spotList.get(middle).h;
//				if (middle == 0) {
//					spotList.remove(number - 1);
//					spotList.addFirst(key);
//					break;
//				}
//
//				int frontMiddleSpotF = spotList.get(middle - 1).g + spotList.get(middle - 1).h;
//
//				if ((frontMiddleSpotF < keyF) && (keyF <= middleSpotF)) {
//					spotList.remove(number - 1);
//					spotList.add(middle, key);
//					break;
//				} else if ((frontMiddleSpotF >= keyF) && (middleSpotF >= keyF)) {
//					right = middle - 1;
//				} else if ((frontMiddleSpotF < keyF) && (middleSpotF < keyF)) {
//					left = middle + 1;
//				}
//			}
//		}
//
//		for (int i = 0; i < spotList.size(); i++) {
//			System.out.print(spotList.get(i).getCoordinate(0) + "," + spotList.get(i).getCoordinate(1) + "  ");
//		}
//		System.out.println();

	}
}
