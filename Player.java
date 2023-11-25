public class Player {
    private int id;
    private String codename;
    private int score;
    private int equipID;
    boolean hitBase;

    Player(int id, String codename){
        this.id = id;
        this.codename = codename;
        this.score = 0;
    }

    Player(int id, String codename, int equipID){
        this.id = id;
        this.codename = codename;
        this.equipID = equipID;
        this.score = 0;
    }

    public int getId(){ return id; }
    public String getCodename(){ return codename; }
    public int getScore(){ return score; }
    public int getEquipID(){ return equipID; }
    public boolean getHitBase(){ return hitBase; }

    public void setId(int id){ this.id = id; }
    public void setCodename(String codename){ this.codename = codename; }
    public void setScore(int score){ this.score = score; }
    public void setEquipID(int equipID){ this.equipID = equipID; }
    public void setHitBase(boolean hitBase){ this.hitBase = hitBase; }

    public String toString(){
        return "Player: " + id + " " + codename;
    }

    public boolean equals(Object o){
        if(o instanceof Player){
            Player p = (Player) o;
            return (this.id == p.id && this.codename.equals(p.codename));
        }
        return false;
    }
}
