public class Tile{
    
    private int x;
    private int y;
    private boolean isOccupied;
    private Piece occupiedBy;

    public Tile(int x, int y){  
        this.x = x;
        this.y = y;
        isOccupied = false;
        occupiedBy = null;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public boolean getisOccupied(){
        return isOccupied;
    }

    public Piece getOccupiedBy(){
        return occupiedBy;
    }

    public void setOccupied(boolean b){
        isOccupied = b;
    }

    public void occupy(Piece piece){
        occupiedBy = piece;
    }

    @Override
    public String toString(){
        if(isOccupied==false)
            return "  ~  ";
        else 
            return occupiedBy.toString();
    }
}
