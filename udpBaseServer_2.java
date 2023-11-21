// Java program to illustrate Server side
// Implementation using DatagramSocket
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.util.PriorityQueue;
import java.util.Queue;
  
public class udpBaseServer_2 implements Runnable
{
    public static Queue<String> queue = new PriorityQueue<String>();
    public static udpBaseClient_2 udpBroadcaster;

	public static void baseServer() throws IOException
	{
		// Step 1 : Create a socket to listen at port 1234
        	DatagramSocket ds = new DatagramSocket(7501);
        	byte[] receive = new byte[1024];
  
        	DatagramPacket DpReceive = null;
        	while (true)
        	{
            		// Step 2 : create a DatgramPacket to receive the data.
            		DpReceive = new DatagramPacket(receive, receive.length);
  
            		// Step 3 : receive the data in byte buffer.
            		ds.receive(DpReceive);

            		//convert to string and add event to queue
            		String message = new String(DpReceive.getData(), 0, DpReceive.getLength());
            		queue.add(message);
            
            		//Send hit player equipment ID
            		udpBroadcaster = new udpBaseClient_2(getId(message));
            		//System.out.println("Client:-" + data(receive));
  
            		// Clear the buffer after every message.
            		receive = new byte[1024];
        	}
	}
   
    public static String getId(String message)
    {
        String[] messageParts = message.trim().split(":");
        String sendID = messageParts[1];

        return sendID;
    }

    @Override
    public void run()
    {
        try 
        {
            baseServer();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}
