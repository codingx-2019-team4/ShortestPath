import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
	private static ServerSocket m_serverSocket;// 伺服器端的Socket，接收Client端的連線
	private static Socket m_socket;// Server和Client之間的連線通道

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Path path = new Path();
		Information mapData = path.getInformation();
//		path.setMapFileName("fireHouse2.pgm");
		path.setMapFileName("fireHouse2_cut.jpg");
		System.out.println("after goal:" + mapData.goal.size());
		System.out.println(mapData.getMapHeight() + " " + mapData.getMapWidth());
//		int[] a = {290,430};
//		mapData.setStart(a);
//		int[] dangerPoint = {85,330};
//		mapData.setDangerZone(dangerPoint);
		int[][] pathMap = path.findShortestPath();
		connectAPP(pathMap);
	}

	public static void connectAPP(int[][] pathMap) {
		try {
			m_serverSocket = new ServerSocket(8088);// 建立伺服器端的Socket，並且設定Port
			System.out.println("等待連線......");
			while (true) {
				m_socket = m_serverSocket.accept();// 等待伺服器端的連線，若未連線則程式一直停在這裡
				System.out.println("連線成功！");
				new Thread(new ServerThreadCode(m_socket, pathMap)).start();// 建立物件，傳入Port並執行等待接受連線的動作
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());// 出現例外時，捕捉並顯示例外訊息
		}
	}
}
