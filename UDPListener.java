// Java program to illustrate Server side
// Implementation using DatagramSocket
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.PriorityQueue;
import java.util.Queue;
  
public class UDPListener implements Runnable
{
    private Queue<String> queue;
    private UDPSender udpBroadcaster;
	private DatagramSocket ds;

	UDPListener(){
		udpBroadcaster = new UDPSender();
		queue = new PriorityQueue<>();
		ds = null;
	}

   
    private String getId(String message)
    {
        String[] messageParts = message.trim().split(":");
        String sendID = messageParts[1];

        return sendID;
    }

	public String getNextEvent(){
		//System.out.println(queue.peek());
		return queue.poll();
	}

	public boolean noEvent(){
		return queue.isEmpty();
	}

    @Override
    public void run()
    {
        try 
        {
			// Step 1 : Create a socket to listen at port 7500 at IP 127.0.0.1
        	//DatagramSocket ds = new DatagramSocket(7501);
			//InetAddress ip = InetAddress.getByName("127.0.0.1");
			ds = new DatagramSocket(7500);
        	byte[] receive = new byte[1024];
        	DatagramPacket DpReceive = null;
        	while (true)
        	{
					//System.out.println("Waiting for message");
            		// Step 2 : create a DatgramPacket to receive the data.
            		DpReceive = new DatagramPacket(receive, receive.length);
  
            		// Step 3 : receive the data in byte buffer.
					try{
            			ds.receive(DpReceive);
					}
					catch (IOException e){
						//System.out.println("IOException");
					}

					//System.out.println("Message received");

            		//convert to string and add event to queue
            		String message = new String(DpReceive.getData(), 0, DpReceive.getLength());
            		queue.add(message);
					//System.out.println("This is message: " + message);
            
            		//Send hit player equipment ID
            		//udpBroadcaster = new udpBaseClient_2(getId(message));
					udpBroadcaster.SendMessage(getId(message));
            		////System.out.println("Client:-" + data(receive));
  
            		// Clear the buffer after every message.
            		receive = new byte[1024];
				}
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
		finally{
			if (ds != null && !ds.isClosed()) {
				ds.close();
			}
		}
    }
}
