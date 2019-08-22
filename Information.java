
public class Information {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
	}
	
	public static int[][] testData1(){
		int[][] map = new int[7][7];
		int start = 0;
		int end = 1;
		map[5][1] = start;
		map[2][5] = end;
		return map;
	}
	
	public static int[][] testData2(){
		int[][] map = new int[7][7];
		int start = 0;
		int end = 1;
		int obstacle = 1000000;
		map[5][1] = start;
		map[2][5] = end;
		for(int i=1; i<=5; i++) {
			map[i][3] = obstacle;
		}
		return map;
	}

}
