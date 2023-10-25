import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class EntryScreen extends JFrame implements ActionListener
{
    DatabaseConnector database;
    JFrame frame;
    JPanel panel;
    JLabel label;
    JTextField iDText;
    JButton button;
    boolean startGame;
    int gameState;
    int iD;
    int equipID;
    String codeName;
    String team;
    boolean exists;
    Action exit;
    Action play;
    Action clearPlayers;
    static String info = "";
    ArrayList<Player> greenTeam;
    ArrayList<Player> redTeam;


    EntryScreen()
    {
        //exit = new Exit();
        play = new Play();
        clearPlayers = new Clear();
        greenTeam = new ArrayList<>();
        redTeam = new ArrayList<>();
        //submit = new Submit();
        gameState = 0;
        startGame = false;
        exists = false;
        database = new DatabaseConnector();
        frame = new JFrame();
        panel = new JPanel();
        label = new JLabel("ID");
        iDText = new JTextField();
        button = new JButton("Submit");

        this.frame.setSize(350, 200);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
        this.frame.add(this.panel);

        InputMap iMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        iMap.put(KeyStroke.getKeyStroke("F3"), "exit");
        panel.getActionMap().put("exit", exit);
        iMap.put(KeyStroke.getKeyStroke("F5"), "play");
        panel.getActionMap().put("play", play);
        iMap.put(KeyStroke.getKeyStroke("F12"), "clear");
        panel.getActionMap().put("clear", clearPlayers);

        // iMap.put(KeyStroke.getKeyStroke("\t"), "submit");
        // panel.getActionMap().put("submit", submit);

        this.panel.add(this.label);

        this.panel.add(this.iDText);

        this.button.setBounds(10,80,80,25);
        this.button.addActionListener(this);
        this.panel.add(this.button);
        
        this.panel.setLayout(null);

        this.enterID();
    }

    /*public class Exit extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("f3");
            gameState = 5;
            update();
        }
    }*/

    public class Play extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("f5");
            gameState = -1;
        }
    }

    public class Clear extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("f12");
            System.out.println(redTeam.toString());
            System.out.println(greenTeam.toString());
            redTeam.clear();
            greenTeam.clear();
            gameState = 0;
            enterID();
            System.out.println(redTeam.toString());
            System.out.println(greenTeam.toString());
        }
    }

    public void actionPerformed(ActionEvent e)
    {
        update();
    }

    public void update()
    {
        System.out.println("Button Pressed");

        this.database.connect();
        System.out.println("test");
        
        this.gameState++;
        System.out.println(this.gameState);
        if(this.gameState == 0)
        {
            this.enterID();
        }
        else if(this.gameState == 1)
        {
            try
            {
                this.iD = Integer.parseInt(this.iDText.getText());
                if(this.database.searchByID(this.iD) == null)
                {
                    this.iD = this.database.getNewPlayerID();
                    
                    System.out.println("Player not found. New ID given: " + this.iD);
                    this.enterCodeName();
                }
                else
                {
                    this.codeName = this.database.searchByID(this.iD).getCodename();
                    this.exists = true;
                    System.out.println("Player has been found. Your codename is " + this.codeName);
                    gameState++;
                    this.enterEquipID();
                }
                
            }
            catch(NumberFormatException exc)
            {
                System.out.println("Enter an integer");
                this.iDText.setText("");
                this.gameState = 0;
            }
        }
        else if(this.gameState == 2)
        {
            try
            {
                if(exists)
                {
                    
                }
                else
                {
                    this.codeName = this.iDText.getText();
                }
                this.enterEquipID();
            }
            catch(NumberFormatException exc)
            {
                System.out.println("Enter a string");
                this.gameState = 1;
            }
            
        }
        else if(this.gameState == 3)
        {
            try
            {
                this.equipID = Integer.parseInt(this.iDText.getText());
                info = this.iDText.getText();
                this.iDText.setText("");
                try
                {
                    baseClient();
                }
                catch(IOException e1)
                {
                    e1.printStackTrace();
                }
                this.enterTeam();
                //this.gameState = 0;
            }
            catch(NumberFormatException exc)
            {
                System.out.println("Enter an integer");
                this.iDText.setText("");
                this.gameState = 2;
            }
        }
        else if(this.gameState == 4)
        {
            try
            {
                this.team = this.iDText.getText();
                if(!(this.team.toLowerCase().equals("red")) && !(this.team.toLowerCase().equals("green")))
                {
                    System.out.println("Enter red or green" + this.team.toLowerCase());
                    this.gameState = 3;
                }
                else
                {
                    Player player = new Player(this.iD, this.codeName);
                    if(this.team.toLowerCase().equals("red"))
                    {
                        redTeam.add(player);
                    }
                    if(this.team.toLowerCase().equals("green"))
                    {
                        greenTeam.add(player);
                    }

                    this.gameState = 0;
                    if(exists)
                    {
                        exists = false;
                    }
                    else
                    {
                        //Player newPlayer = new Player(this.iD, this.codeName);
                        this.database.addPlayer(player);
                    }
                    
                    this.enterID();
                }
            }
            catch(NumberFormatException exc)
            {
                System.out.println("Enter red or green exception");
                this.iDText.setText("");
                this.gameState = 3;
            }
        }

        database.disconnect();

        
        System.out.println(this.gameState);

        if(this.gameState == 6)
        {
            System.exit(0);
        }
    }

    public void enterID()
    {
        this.label.setText("ID");
        this.label.setBounds(10,20,80,25);

        this.iDText.setText("");
        this.iDText.setBounds(100, 20, 165, 25);
    }
    
    public void enterCodeName()
    {   
        this.label.setText("CodeName");
        this.label.setBounds(10,20,80,25);

        this.iDText.setText("");
        this.iDText.setBounds(100, 20, 165, 25);
    }

    public void enterEquipID()
    {   
        this.label.setText("Equipment ID");
        this.label.setBounds(10,20,80,25);

        this.iDText.setText("");
        this.iDText.setBounds(100, 20, 165, 25);
    }

    public void enterTeam()
    {
        this.label.setText("Team");
        this.label.setBounds(10,20,80,25);

        this.iDText.setText("");
        this.iDText.setBounds(100, 20, 165, 25);
    }

    public static void baseClient() throws IOException
	{
		// Step 1:Create the socket object for
		// carrying the data.
		DatagramSocket ds = new DatagramSocket();

		InetAddress ip = InetAddress.getLocalHost();
		byte buf[] = null;

        System.out.println("transmit");
		
		String inp = info;
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

    public ArrayList<ArrayList<Player>> waitForPlayerEntry()
    {
        while(gameState != -1)
        {
            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        ArrayList<ArrayList<Player>> teams = new ArrayList<>();
        teams.add(redTeam);
        teams.add(greenTeam);
        return teams;
    }
}
