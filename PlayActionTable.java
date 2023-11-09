import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.table.TableCellRenderer;

public class PlayActionTable {

    ArrayList<Player> team1_players;
    ArrayList<Player> team2_players;
    ArrayList<String> log;
    int table_size;
    //int log_start;
    static int LOG_SIZE = 5;
    JFrame frame;
    JPanel panel;
    JPanel panelTwo;
    JTable table;
    static int ROW_HEIGHT = 15;
    JTextArea log_text;
    private GameTimer gameTimer;
    int delay = 1000;
    int swapOut = 0;
    static int HIT_SCORE_INCREMENT = 10;
    static int BASE_SCORE_INCREMENT = 100;
    
    
    
    PlayActionTable(ArrayList<Player> team1Players, ArrayList<Player> team2Players){
        this.team1_players = new ArrayList<>(team1Players);
        this.team2_players = new ArrayList<>(team2Players);
        this.log = new ArrayList<>();
    }

    public void display(){
        //create jtable
        frame = new JFrame();
        panel = new JPanel();
        panelTwo = new JPanel();
        //add players to table

        table_size = Math.max(team1_players.size(), team2_players.size()) + 1;
        //log_start = team_size + 2;
        //table_size = team_size + 2 + LOG_SIZE;
        String[] columns = {"red_hit", "red_playername", "red_score", "green_hit", "green_playername", "green_score"};
        String[][] data = new String[table_size][6];
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
        table.setRowHeight(ROW_HEIGHT);
        //make an empty log area
        log_text = new JTextArea();
        log_text.setEditable(false);
        log_text.setLineWrap(true);
        log_text.setWrapStyleWord(true);
        log_text.setFont(new Font("Serif", Font.PLAIN, 10));
        for(int i = 0; i < LOG_SIZE; i++){
            log_text.append("\n");
        }
        
        //Reference to GameTimer
        gameTimer = new GameTimer();
        
        //Gets value of time from GameTimer
        swapOut = gameTimer.getSecondsRemaining();
        
        //add table to panel
        panel.setLayout(new BorderLayout());
        panel.add(table, BorderLayout.CENTER);
        panel.add(log_text, BorderLayout.SOUTH);
        
        //add in GameTimer to the panel
        panelTwo.add(gameTimer);
       
        //add panel to frame 
        frame.add(panelTwo);
        
        //set frame properties
        //frame.setSize(550, 200);
        //determine framze size by the number of players
        frame.setSize(550, 130 + (table_size * 15));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        //gets countdown from GameTimer and when finished swap out
        //Jpanels from countdown to actionTable
        try {
            Thread.sleep(swapOut * delay);    
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        frame.add(panel);
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

    //Use this whenever a player hits a player. Make sure to pass in player IDs
    public void playerHitPlayer(int attacker_id, int defender_id){
        //update score
        Player attacker = null;
        Player defender = null;
        for(int i = 0; i < team1_players.size(); i++){
            if(team1_players.get(i).getId() == attacker_id){
                attacker = team1_players.get(i);
            }
            if(team1_players.get(i).getId() == defender_id){
                defender = team1_players.get(i);
            }
        }
        for(int i = 0; i < team2_players.size(); i++){
            if(team2_players.get(i).getId() == attacker_id){
                attacker = team2_players.get(i);
            }
            if(team2_players.get(i).getId() == defender_id){
                defender = team2_players.get(i);
            }
        }
        if (attacker == null || defender == null) {
            return;
        }
        log.add(attacker.getCodename() + " hit " + defender.getCodename());
        int score = attacker.getScore();
        score += HIT_SCORE_INCREMENT;
        attacker.setScore(score);
    }

    //Use this whenever a player hits the base
    public void playerHitBase(int attacker_id){
        Player attacker = null;
        for(int i = 0; i < team1_players.size(); i++){
            if(team1_players.get(i).getId() == attacker_id){
                attacker = team1_players.get(i);
            }
        }
        for(int i = 0; i < team2_players.size(); i++){
            if(team2_players.get(i).getId() == attacker_id){
                attacker = team2_players.get(i);
            }
        }
        attacker.setHitBase(true);
        attacker.setScore(attacker.getScore() + BASE_SCORE_INCREMENT);
        log.add(attacker.getCodename() + " hit the base");
    }

    public void sortByScore(){
        this.team1_players.sort((p1, p2) -> p2.getScore() - p1.getScore());
        this.team2_players.sort((p1, p2) -> p2.getScore() - p1.getScore());
    }

    //Use this within a clock cycle to update the display
    public void updateDisplay(){
        //update table
        //this code should probably be cleaned to use the same table instead of making a new one, but it was easier to copy paste
        this.sortByScore();
        String[] columns = {"red_hit", "red_playername", "red_score", "green_hit", "green_playername", "green_score"};
        String[][] data = new String[table_size][6];
        data[0][0] = "";
        data[0][1] = "Red Team";
        data[0][2] = "Score";
        data[0][3] = "";
        data[0][4] = "Green Team";
        data[0][5] = "Score";
        for(int i = 1; i < team1_players.size() + 1; i++){
            if (team1_players.get(i-1).getHitBase()) {
                data[i][0] = "B";
            }
            else {
                data[i][0] = "";
            }
            data[i][1] = team1_players.get(i-1).getCodename();
            data[i][2] = String.valueOf(team1_players.get(i-1).getScore());
        }
        for(int i = 1; i < team2_players.size() + 1; i++) {
            if (team2_players.get(i-1).getHitBase()) {
                data[i][3] = "B";
            }
            else {
                data[i][3] = "";
            }
            data[i][4] = team2_players.get(i-1).getCodename();
            data[i][5] = String.valueOf(team2_players.get(i-1).getScore());
        }

        table = new JTable(data, columns) {
            public boolean isCellEditable(int row, int column) {                
                return false; 
            };
        };
        table.setRowHeight(ROW_HEIGHT);
        

        log_text.setText("");
        int log_start_index = log.size() - LOG_SIZE;
        if (log_start_index < 0) {
            log_start_index = 0;
        }
        //if less than LOG_SIZE lines, add blank lines
        for(int i = 0; i < LOG_SIZE - log.size(); i++){
            log_text.append("\n");
        }
        for(int i = log_start_index; i < log.size(); i++){
            log_text.append(log.get(i) + "\n");
        }

        panel.removeAll();
        panel.setLayout(new BorderLayout());
        panel.add(table, BorderLayout.CENTER);
        panel.add(log_text, BorderLayout.SOUTH);
        panel.revalidate();
        panel.repaint();

    }

    
    //test
    /*public static void main(String[] args){
        ArrayList<Player> team1 = new ArrayList<>();
        ArrayList<Player> team2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            team1.add(new Player(i, "Player " + i));
        }
        for (int i = 5; i < 10; i++) {
            team2.add(new Player(i, "Player " + i));
        }
        PlayActionTable playActionTable = new PlayActionTable(team1, team2);
        playActionTable.display();
        //test playerHitPlayer
        playActionTable.playerHitPlayer(4, 7);
        playActionTable.playerHitBase(2);
        playActionTable.updateDisplay();
        //wait, then have a random player hit a random player, seven times
        for(int i = 0; i < 7; i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int attacker = (int)(Math.random() * 10);
            int defender = (int)(Math.random() * 10);
            playActionTable.playerHitPlayer(attacker, defender);
            playActionTable.updateDisplay();
        }
    }*/

}
