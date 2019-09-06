
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;
import org.json.JSONArray;

import A_star_algorithm.Information;
import A_star_algorithm.Path;

public class ServerThreadCode_NewVersion extends Thread {
	private Socket m_socket;// ServerLin 和 app client的連接通道，當算完最短路徑後會透過此通道傳輸路徑
	public int[][] pathMap = null;

	public ServerThreadCode_NewVersion(Socket m_socket) {
		this.m_socket = m_socket;
	}

	@Override
	public void run()// 覆寫Thread內的run()方法
	{
//		client為接收人位置的socket

		System.out.println("ininin");
		Socket client = new Socket();
		InetSocketAddress isa = new InetSocketAddress("192.168.208.150", 8035);
		try {
			client.connect(isa, 10000);
			System.out.println("conect?");
			BufferedInputStream in = null;
			DataInputStream dout = new DataInputStream(client.getInputStream());

			while (true) {
				System.out.println("Wait for data～");
				in = new java.io.BufferedInputStream(client.getInputStream());
				String receive = dout.readUTF();
				System.out.print(receive);
//				位置為公分，解析度為一個pixel 5公分
				int originX = (int) (Integer.parseInt(receive.split(" ")[0]) / 5);
				int originY = (int) (Integer.parseInt(receive.split(" ")[1]) / 5);

//				算路徑
				Path path = new Path();
				Information mapData = path.getInformation();
//				path.setMapFileName("fireHouse2.pgm");
				path.setMapFileName("fireHouse2_cut.jpg");
				System.out.println(mapData.getMapHeight() + " " + mapData.getMapWidth());

//				由於此演算法中地圖的原點(左上角，往右x+，往下y+)與傳入的位置原點(原圖的正中央且此演算法還裁剪過，故原點位置並非在裁剪後地圖的正中央)不同，故需標記原地圖原點的位置並將傳入進來的值加上去
				int[] startOffset = { -originY, -originX };
				mapData.setStart(startOffset);
				int[][] pathMap = path.findShortestPath();

//				最短路徑pathMap傳給手機app端

				try {
					// 送出端的編寫必須和接收端的接收Class相同
					// 使用Socket的getInputStream()和getOutputStream()進行接收和發送資料
					// 想要寫入字串可以用 PrintStream；想要把各種基本資料型態，如 int, double...等的 "值" 輸出，可以用
					// DataOutputStream；想要把整個物件 Serialize，則可以用 ObjectOutputStream。
					PrintStream writer;// 在此我使用PrintStream將字串進行編寫和送出

					writer = new PrintStream(m_socket.getOutputStream());// 由於是將資料編寫並送出，所以是Output

					JSONArray mJSONArray = new JSONArray(Arrays.asList(pathMap));
					System.out.println(mJSONArray.toString());

					writer.println(mJSONArray.toString());// 將資料編寫進串流內
					writer.flush();// 清空緩衝區並送出資料
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (IOException e) {
					System.out.println(e.getMessage());// 出現例外時，捕捉並顯示例外訊息(連線成功不會出現例外)
				}
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());// 出現例外時，捕捉並顯示例外訊息(連線成功不會出現例外)
		}
	}
}