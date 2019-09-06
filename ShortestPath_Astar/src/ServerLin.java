
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import org.json.JSONArray;

public class ServerLin {
	public static void main(String[] args) throws Exception {
		new Thread(new ServerMainThread()).start();
	}
}

class ServerMainThread extends Thread {
	private ServerSocket m_serverSocket;// 伺服器端的Socket，接收Client端的連線
	private Socket m_socket;// Server和Client之間的連線通道
	public int[][] pathMap = null;

	@Override
	public void run()// 覆寫Thread內的run()方法
	{
		try {
			m_serverSocket = new ServerSocket(8081);
			System.out.println("等待連線......");
			while (true) {
				m_socket = m_serverSocket.accept();// 等待伺服器端的連線，若未連線則程式一直停在這裡
				System.out.println("連線成功！");
				new Thread(new ServerThreadCode_NewVersion(m_socket)).start();// 建立物件，傳入Port並執行等待接受連線的動作
			}
		} catch (IOException e) {
			System.out.println("ininin");
			e.printStackTrace();
		} // 建立伺服器端的Socket，並且設定Port

	}
}

