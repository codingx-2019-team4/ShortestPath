import java.util.ArrayList;

public class SpotSort {
	private ArrayList spots = new ArrayList();
	
	public int length() {
		return this.spots.size();
	}
	
	public boolean contains(Spot spot) {   //�P�_Spot�O�_�bArrayList��
		for(int i=0; i< spots.size();i++) {
			if(spots.get(i) == spot)
				return true;
		}
		return false;
	}
	
	public void push(Spot spot) {
		this.spots.add(spot);
		this.spots.sort(c);  //����n�Ƨ�??
	}
	
	public void remove(Spot spot) {
		this.spots.remove(spot);
		this.spots.sort(c);  //����n�Ƨ�??
	}
}
