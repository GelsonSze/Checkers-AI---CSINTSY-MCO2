public class Piece{
    private int x;
    private int y;
    private String color;
    private boolean isKing;

    public Piece(int x, int y, String color){
        this.x = x;
        this.y = y;
        this.color = color;
        isKing = false;
    }

    public Piece(Piece p){
        this.x = p.getX();
        this.y = p.getY();
        this.color = p.getColor();
        this.isKing = p.getKing();
    }

    public String getColor(){
        return color;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public boolean getKing(){
        return isKing;
    }

    public void setKing(){
        this.isKing = true;
    }

    public void move(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString(){
        if(color.equalsIgnoreCase("red")){
            if(isKing)
                return "  R* ";
            else
                return "  R  ";
        }
        else{
            if(isKing)
                return "  B* ";    
            else
                return "  B  ";
        }
    }

    @Override
    public boolean equals(Object o){
        if(((Piece)o).getX() == x && ((Piece)o).getY() == y && ((Piece)o).getColor().equalsIgnoreCase(color))
            return true;
        return false;
    }

    @Override
    public Object clone() {
        try {
            return (Piece) super.clone();
        } catch (CloneNotSupportedException e) {
            return new Piece(this);
        }
    }

}
