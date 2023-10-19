import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class PlayActionTable {

    ArrayList<Player> team1_players;
    ArrayList<Player> team2_players;
    JFrame frame;
    JPanel panel;
    JTable table;
    
    PlayActionTable(ArrayList<Player> team1Players, ArrayList<Player> team2Players){
        this.team1_players = new ArrayList<>(team1Players);
        this.team2_players = new ArrayList<>(team2Players);
    }
    public void display(){
        //create jtable
        frame = new JFrame();
        panel = new JPanel();
        //add players to table
        String[] columns = {"red_hit", "red_playername", "red_score", "green_hit", "green_playername", "green_score"};
        String[][] data = new String[team1_players.size() + 1][6];
        data[0][0] = "";
        data[0][1] = "Red Team";
        data[0][2] = "Score";
        data[0][3] = "";
        data[0][4] = "Green Team";
        data[0][5] = "Score";
        for(int i = 1; i < team1_players.size() + 1; i++){
            data[i][1] = team1_players.get(i-1).getCodename();
            data[i][2] = "0";
        }
        for(int i = 1; i < team2_players.size() + 1; i++) {
            data[i][4] = team2_players.get(i-1).getCodename();
            data[i][5] = "0";
        }
        table = new JTable(data, columns) {
            public boolean isCellEditable(int row, int column) {                
                return false;               
            };
        };
        //add table to panel
        panel.setLayout(new BorderLayout());
        panel.add(table, BorderLayout.CENTER);
        //add panel to frame
        frame.add(panel);
        //set frame properties
        frame.setSize(550, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public void updateScores(int team, int player, int intscore){
        //update score
        String score = String.valueOf(intscore);
        if(team == 1){
            table.setValueAt(score, player + 1, 2);
        }
        else if(team == 2){
            table.setValueAt(score, player + 1, 5);
        }
    }

    public static void main(String[] args) {
        //generate dummy player list
        ArrayList<Player> players1 = new ArrayList<Player>();
        for(int i = 0; i < 4; i++){
            Player player = new Player(i, "Player " + i);
            players1.add(player);
        }
        ArrayList<Player> players2 = new ArrayList<Player>(players1);
        PlayActionTable playActionTable = new PlayActionTable(players1, players2);
        playActionTable.display();
        //wait
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        playActionTable.updateScores(1, 0, 1);
    }
}
