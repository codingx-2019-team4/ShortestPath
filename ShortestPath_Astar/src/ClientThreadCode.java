//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintStream;
//import java.net.Socket;
//
//public class ClientThreadCode extends Thread
//{
//    private Socket m_socket;//和伺服器端進行連線
//    
//    public ClientThreadCode(String ip, int port)
//    {
//        try
//        {
//            m_socket = new Socket(ip, port);//建立連線。(ip為伺服器端的ip，port為伺服器端開啟的port)
//        }
//        catch (IOException e)
//        {
//            System.out.println(e.getMessage());
//        }
//    }
//    
//    @Override
//    public void run()
//    {
//        try
//        {
//            if (m_socket != null)//連線成功才繼續往下執行
//            {
//
//                //由於Server端使用 PrintStream 和 BufferedReader 來接收和寄送訊息，所以Client端也要相同
//                PrintStream writer = new PrintStream(m_socket.getOutputStream());
//            
//                writer.println();
//                writer.flush();
//               
//                m_socket.close();
//
//            }
//        }
//        catch (IOException e)
//        {
//            System.out.println(e.getMessage());
//        }
//    }
//}