//connection string is jdbc:postgresql://db.gkeebcixaqkelmnqriql.supabase.co:5432/postgres?user=postgres&password=UA7ashu40JCkUNHwYymL

import java.sql.*;
import java.util.Properties;
//This is a test


public class DatabaseConnector {
    private Connection Database_Connection;
    private String Database_URL;
    private Properties Database_Properties;
    private boolean isConnected;

    //The details for the database are hard-coded here
    //Technically putting these details in the code is a really bad idea, but we've been told not to worry about security
    DatabaseConnector(){
        Database_URL = "jdbc:postgresql://db.gkeebcixaqkelmnqriql.supabase.co:5432/postgres";
        isConnected = false;
        Database_Properties = new Properties();
        Database_Properties.setProperty("user","postgres");
        Database_Properties.setProperty("password","UA7ashu40JCkUNHwYymL");
    }

    //You shouldn't need to use this alternative constructor, but it's here
    DatabaseConnector(String url, String user, String password){
        Database_URL = url;
        isConnected = false;
        Database_Properties = new Properties();
        Database_Properties.setProperty("user",user);
        Database_Properties.setProperty("password",password);
    }

    //Connects to the database. Always call this before doing anything else with the database, or you'll get yelled at.
    public void connect(){
        try {
            Database_Connection = DriverManager.getConnection(Database_URL, Database_Properties);
            Statement stmt = Database_Connection.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS player (\r\n" + //
                "  id INT PRIMARY KEY,\r\n" + //
                "  codename VARCHAR(30) UNIQUE\r\n" + //
                ");");
            stmt.close();
            isConnected = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Disconnects from the database. Always call this when you're done doing database operations.
    public void disconnect(){
        try {
            Database_Connection.close();
            isConnected = false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Adds a player to the database.
    //The database is configured to not allow entries with duplicate IDs, codenames, or first+last names-some of those constraints might end up changing later
    //If you wish to know if a conflict will occur, use willConflict() first. If you try to add a player that will conflict, you'll get a PSQLException and nothing will be added.
    public void addPlayer(Player player) throws IllegalStateException{
        if (!isConnected) throw new IllegalStateException("Not connected to database");
        try {
            Statement stmt = Database_Connection.createStatement();
            stmt.executeUpdate("INSERT INTO player (id, codename) VALUES (" + player.getId() + ", '"+ player.getCodename() + "');");
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Use this to check if a player will conflict with an existing entry in the database.
    //For the moment, duplicate names or codenames aren't allowed
    public boolean willConflict(Player player) throws IllegalStateException{
        if (!isConnected) throw new IllegalStateException("Not connected to database");
        try {
            Statement stmt = Database_Connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM player WHERE id = " + player.getId() + " OR codename = '" + player.getCodename() + "';");
            boolean foundResult = rs.next();
            rs.close();
            stmt.close();
            return foundResult;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //When adding a new player to the database, use this to get a new ID.
    //Always use this when making a new player unless you have a good reason not to.
    //At the moment this just returns the highest ID in the database + 1
    public int getNewPlayerID() throws IllegalStateException{
        if (!isConnected) throw new IllegalStateException("Not connected to database");
        try {
            Statement stmt = Database_Connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(id) FROM player;");
            rs.next();
            int id = rs.getInt("max");
            rs.close();
            stmt.close();
            return id + 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    //the next three methods are for searching the database for a player based on their ID, codename, or first+last name.
    //these should always return a single 'player' result, or null if no result is found. It doesn't need to return multiple results because those shouldn't exist.
    public Player searchByCodename(String codename) throws IllegalStateException{
        if (!isConnected) throw new IllegalStateException("Not connected to database");
        try {
            Statement stmt = Database_Connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM player WHERE codename = '" + codename + "';");
            Player player = null;
            boolean foundResult = rs.next();
            if (foundResult)
                player = new Player(rs.getInt("id"), rs.getString("codename"));
            rs.close();
            stmt.close();
            if (!foundResult) return null;
            return player;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public Player searchByID(int ID) throws IllegalStateException{
        if (!isConnected) throw new IllegalStateException("Not connected to database");
        try {
            Statement stmt = Database_Connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM player WHERE id = " + ID + ";");
            Player player = null;
            boolean foundResult = rs.next();
            if (foundResult)
                player = new Player(rs.getInt("id"), rs.getString("codename"));
            rs.close();
            stmt.close();
            if (!foundResult) return null;
            return player;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    //The following methods check to see if a player with the given properties exists in a database.
    //You can either call this with a player object, in which case it will check if a player with the same ID and codename
    //Or you can call with an id or codename, in which case it will check if a player with that id or codename exists.
    //This is distinct from WillConflict, which returns false if either codename or id conflict.
    public boolean playerExists(Player player) throws IllegalStateException{
        if (!isConnected) throw new IllegalStateException("Not connected to database");
        try {
            Statement stmt = Database_Connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM player WHERE id = " + player.getId() + " AND codename = '" + player.getCodename() + "';");
            boolean foundResult = rs.next();
            rs.close();
            stmt.close();
            return foundResult;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean playerExists(int id) throws IllegalStateException{
        if (!isConnected) throw new IllegalStateException("Not connected to database");
        try {
            Statement stmt = Database_Connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM player WHERE id = " + id + " ;");
            boolean foundResult = rs.next();
            rs.close();
            stmt.close();
            return foundResult;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean playerExists(String codename) throws IllegalStateException{
        if (!isConnected) throw new IllegalStateException("Not connected to database");
        try {
            Statement stmt = Database_Connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM player WHERE codename = '" + codename + "';");
            boolean foundResult = rs.next();
            rs.close();
            stmt.close();
            return foundResult;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //You can delete players from the database based on an exact match with a player object, or by ID.
    public void deletePlayer(Player player) throws IllegalStateException{
        if (!isConnected) throw new IllegalStateException("Not connected to database");
        try {
            Statement stmt = Database_Connection.createStatement();
            stmt.executeUpdate("DELETE FROM player WHERE id = " + player.getId() + " ;");
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePlayer(int id) throws IllegalStateException{
        if (!isConnected) throw new IllegalStateException("Not connected to database");
        try {
            Statement stmt = Database_Connection.createStatement();
            stmt.executeUpdate("DELETE FROM player WHERE id = " + id + " ;");
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //This will overwrite the player in the database with the same ID as the player object with the object's data.
    //This is kept separate from addPlayer because you should really know which one you're using.
    public void updatePlayer(Player player) throws IllegalStateException{
        if (!isConnected) throw new IllegalStateException("Not connected to database");
        try {
            Statement stmt = Database_Connection.createStatement();
            stmt.executeUpdate("UPDATE player SET codename = '" + player.getCodename() + "' WHERE id = " + player.getId() + " ;");
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //This is for testing. Leave it commented out.
    /*public static void main(String[] args) {
        //test the class
        DatabaseConnector db = new DatabaseConnector();
        db.connect();
        //
        db.getNewPlayerID();
        db.willConflict(new Player(1, "test"));
        Player player = db.searchByID(2);
        if (player != null){
            System.out.println(player.toString());
            db.deletePlayer(player);
        }
        db.disconnect();
    }*/
}
