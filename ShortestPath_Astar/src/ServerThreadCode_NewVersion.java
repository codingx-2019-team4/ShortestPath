

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.json.JSONArray;

public class ServerThreadCode_NewVersion extends Thread {
	private Socket m_socket;// Server和Client之間的連線通道
	public int[][] pathMap = null;

	public ServerThreadCode_NewVersion(Socket m_socket) {
//		this.pathMap = pathMap;
//		this.m_socket = m_socket;
	}

	@Override
	public void run()// 覆寫Thread內的run()方法
	{
		System.out.println("ininin");
		Socket client2 = new Socket();

		InetSocketAddress isa2 = new InetSocketAddress("172.20.10.7", 8025);
		try {
			client2.connect(isa2, 10000);
			System.out.println("conect?");
			BufferedInputStream in = null;
			DataInputStream dout = new DataInputStream(client2.getInputStream());

			while (true) {
				System.out.println("Wait for data～");
				in = new java.io.BufferedInputStream(client2.getInputStream());
				String receive = dout.readUTF();
				System.out.print(receive);
//				receive.split(" ");
//				位置為公分
				int originX =(int) (Integer.parseInt(receive.split(" ")[0]) / 5);
				int originY =(int) (Integer.parseInt(receive.split(" ")[1]) / 5);
				
				
//				算路徑
				Path path = new Path();
				Information mapData = path.getInformation();
//				path.setMapFileName("fireHouse2.pgm");
				path.setMapFileName("fireHouse2_cut.jpg");
				System.out.println("after goal:" + mapData.goal.size());
				System.out.println(mapData.getMapHeight() + " " + mapData.getMapWidth());
				int[] start = {-originY,-originX};
				mapData.setStart(start);
				int[][] pathMap = path.findShortestPath();		
//				
////				傳給Bob
//				try {
//					// 送出端的編寫必須和接收端的接收Class相同
//					// 使用Socket的getInputStream()和getOutputStream()進行接收和發送資料
//					// 想要寫入字串可以用 PrintStream；想要把各種基本資料型態，如 int, double...等的 "值" 輸出，可以用
//					// DataOutputStream；想要把整個物件 Serialize，則可以用 ObjectOutputStream。
//					PrintStream writer;// 在此我使用PrintStream將字串進行編寫和送出
//
//					writer = new PrintStream(m_socket.getOutputStream());// 由於是將資料編寫並送出，所以是Output
//
////					int[][] mStringArray = { { 10, 20 }, { 30, 40 } };
//					while (true) {
////						JSONArray mJSONArray = new JSONArray(Arrays.asList(mStringArray));
//						JSONArray mJSONArray = new JSONArray(Arrays.asList(pathMap));
//
////		    		JSONArray mJSONArray = new JSONArray(Arrays.asList(pathMap));
//						System.out.println(mJSONArray.toString());
//
//						writer.println(mJSONArray.toString());// 將資料編寫進串流內
//						writer.flush();// 清空緩衝區並送出資料
//						try {
//							Thread.sleep(3000);
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
////					m_socket.close();// 關閉連線
//				} catch (IOException e) {
//					System.out.println(e.getMessage());// 出現例外時，捕捉並顯示例外訊息(連線成功不會出現例外)
//				}
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());// 出現例外時，捕捉並顯示例外訊息(連線成功不會出現例外)
		}
	}
}