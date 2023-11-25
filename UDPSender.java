// Java program to illustrate Client side
// Implementation using DatagramSocket
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;


public class UDPSender
{
	int port;

	UDPSender(){
		this.port = 7501;
	}

	UDPSender(int port){
		this.port = port;
	}

	public void SendMessage(String info) throws IOException
	{
		// Step 1:Create the socket object for
		// carrying the data.
		DatagramSocket ds = new DatagramSocket();

		//InetAddress ip = InetAddress.getLocalHost();
		InetAddress ip = InetAddress.getByName("127.0.0.1");
		
		byte buf[] = null;

		//System.out.println("transmit");
				
		String inp = info;
		////System.out.println("This is inp in Client: " + info);
		////System.out.println("This is inp: " + inp);
		// convert the String input into the byte array.
		//buf = ByteBuffer.allocate(Integer.BYTES).putInt(inp).array();
		buf = inp.getBytes();
		//System.out.println("This is inp: " + inp);
		//print the contents of the byte array
		String decoded_String = new String(buf);
		//System.out.println("This is decoded_String: " + decoded_String);
		
		
		// Step 2 : Create the datagramPacket for sending
		// the data.
		////System.out.println("This is buf: " + buf);
		DatagramPacket DpSend = new DatagramPacket(buf, buf.length, ip, this.port);
		//System.out.println("Sending to ip: " + ip + " port: " + this.port);
		
		// Step 3 : invoke the send call to actually send
		// the data.
		//System.out.println("This is DpSend: " + DpSend);
		ds.send(DpSend);
	}
	
}
