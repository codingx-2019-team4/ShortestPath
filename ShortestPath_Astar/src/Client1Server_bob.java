
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.json.JSONArray;

public class Client1Server_bob {
	public static void main(String[] args) throws Exception {
		new Thread(new ClientMainThread2()).start();
		
	}
}

class ClientMainThread2 extends Thread {
	public void run()// 覆寫Thread內的run()方法
	{
		Socket client2 = new Socket();
		InetSocketAddress isa2 = new InetSocketAddress("192.168.208.200", 8081);
		try {
			client2.connect(isa2, 10000);
			while(true) {
//				InputStream inputStream = client2.getInputStream();
//
//		        System.out.println("Reading: " + System.currentTimeMillis());
//
//		        byte[] sizeAr = new byte[4];
//		        inputStream.read(sizeAr);
//		        int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
//
//		        byte[] imageAr = new byte[size];
//		        inputStream.read(imageAr);
//
//		        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
//
//		        System.out.println("Received " + image.getHeight() + "x" + image.getWidth() + ": " + System.currentTimeMillis());
//		        ImageIO.write(image, "jpg", new File("C:\\Git\\ShortestPath\\TestPgm\\test2.jpg"));
				
		        InputStream array = client2.getInputStream();
//		        System.out.println(array.toString());
//		        break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("conect?");
	}
}
