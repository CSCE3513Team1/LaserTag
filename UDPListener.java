// Java program to illustrate Server side
// Implementation using DatagramSocket
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
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
			ds = new DatagramSocket(7500);
        	byte[] receive = new byte[1024];
        	DatagramPacket DpReceive = null;
        	while (true)
        	{
            	DpReceive = new DatagramPacket(receive, receive.length);
            	ds.receive(DpReceive);
        		String message = new String(DpReceive.getData(), 0, DpReceive.getLength());
        		queue.add(message);
				udpBroadcaster.SendMessage(getId(message));
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
