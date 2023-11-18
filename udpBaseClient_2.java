// Java program to illustrate Client side
// Implementation using DatagramSocket
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;


public class udpBaseClient_2
{
	
	public udpBaseClient_2(String info) throws IOException
	{
		// Step 1:Create the socket object for
		// carrying the data.
		DatagramSocket ds = new DatagramSocket();

		InetAddress ip = InetAddress.getLocalHost();
		
		byte buf[] = null;

		System.out.println("transmit");
				
		String inp = info;
		System.out.println("This is inp in Client: " + info);
		//System.out.println("This is inp: " + inp);
		// convert the String input into the byte array.
		//buf = ByteBuffer.allocate(Integer.BYTES).putInt(inp).array();
		buf = inp.getBytes();
		
		
		// Step 2 : Create the datagramPacket for sending
		// the data.
		//System.out.println("This is buf: " + buf);
		DatagramPacket DpSend = new DatagramPacket(buf, buf.length, ip, 7500);
		
		// Step 3 : invoke the send call to actually send
		// the data.
		ds.send(DpSend);
	}
	
}
