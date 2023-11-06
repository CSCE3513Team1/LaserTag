public class Player {
    private int id;
    private String codename;
    private int score;
    boolean hitBase;

    Player(int id, String codename){
        this.id = id;
        this.codename = codename;
        this.score = 0;
    }

    public int getId(){ return id; }
    public String getCodename(){ return codename; }
    public int getScore(){ return score; }
    public boolean getHitBase(){ return hitBase; }

    public void setId(int id){ this.id = id; }
    public void setCodename(String codename){ this.codename = codename; }
    public void setScore(int score){ this.score = score; }
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
