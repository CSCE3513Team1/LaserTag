// Java program to illustrate Client side
// Implementation using DatagramSocket
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class UDPSender
{
	int port;
	String ip_address;

	UDPSender(){
		this.port = 7501;
		this.ip_address = "127.0.0.1";
	}

	UDPSender(int port, String ip_address){
		this.port = port;
		this.ip_address = ip_address;
	}

	public void SendMessage(String info)
	{
		try{
			DatagramSocket ds = new DatagramSocket();
			InetAddress ip = InetAddress.getByName(this.ip_address);
			byte buf[] = null;
			buf = info.getBytes();
			DatagramPacket DpSend = new DatagramPacket(buf, buf.length, ip, this.port);
			ds.send(DpSend);
			ds.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
}
