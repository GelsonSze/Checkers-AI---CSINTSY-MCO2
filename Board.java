import java.util.*;

public class Board {

    private Tile[][] matrix;
    private ArrayList<Piece> black;
    private ArrayList<Piece> red;
    private int redKings;
    private int blackKings;

    public Board(){
        matrix = new Tile[8][8];
        black = new ArrayList<Piece>();
        red = new ArrayList<Piece>();
        redKings = 0;
        blackKings = 0;
        generatePieces();
        placePieces();
    }

    public Board(Board b){
        this.matrix = new Tile[8][8];
        this.black = new ArrayList<Piece>();
        this.red = new ArrayList<Piece>();

        for(int i=0;i<b.black.size();i++){
            this.black.add(new Piece(b.black.get(i)));
        }
        for(int i=0;i<b.red.size();i++){
            this.red.add(new Piece(b.red.get(i)));
        }
        placePieces();
        this.blackKings = b.blackKings;
        this.redKings = b.redKings;

    }

    public Tile[][] getMatrix(){
        return matrix;
    }

    public ArrayList<Piece> getRedPieces(){
        return red;
    }

    public ArrayList<Piece> getBlackPieces(){
        return black;
    }

    public int getRedKings(){
        return redKings;
    }

    public int getBlackKings(){
        return blackKings;
    }

    public Piece getPiece(int x, int y){
        return matrix[x][y].getOccupiedBy();
    }

    private void generatePieces(){
        for(int i=0;i<3;i++){
            for(int j=0;j<8;j++){
                if(i%2==0){
                    if(j%2==0)
                        black.add(new Piece(i,j,"black"));
                    else
                        red.add(new Piece(7-i,j,"red"));
                }
                else{
                    if(j%2==1)
                        black.add(new Piece(i,j,"black"));
                    else
                        red.add(new Piece(7-i,j,"red"));
                }
            }
        }
    }

    private void placePieces(){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                matrix[i][j] =  new Tile(i,j);
            }
        }
        for(int i=0;i<black.size();i++){
            matrix[black.get(i).getX()][black.get(i).getY()].occupy(black.get(i));
            matrix[black.get(i).getX()][black.get(i).getY()].setOccupied(true);
        }
        for(int i=0;i<red.size();i++){
            matrix[red.get(i).getX()][red.get(i).getY()].occupy(red.get(i));
            matrix[red.get(i).getX()][red.get(i).getY()].setOccupied(true);
        }
    }

    public void printBoard(){
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        System.out.println("X");
        for(int i=0;i<8;i++){
            System.out.print(i);
            for(int j=0;j<8;j++){
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println("Y  0    1    2    3    4    5    6    7 ");
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n");
    }

    public void removePiece(int x, int y){
        Piece temp = getPiece(x, y);
        if(temp.getColor().equalsIgnoreCase("red"))
            red.remove(temp);
        else
            black.remove(temp);
        matrix[x][y].setOccupied(false);
        matrix[x][y].occupy(null);
    }
    
    public void movePiece(Piece p, int x, int y){
        matrix[p.getX()][p.getY()].setOccupied(false);
        matrix[p.getX()][p.getY()].occupy(null);
        p.move(x,y);
        matrix[x][y].occupy(p);
        matrix[x][y].setOccupied(true);

        if((x==0 || x==7) && p.getKing() == false ){
            p.setKing();
            if(p.getColor().equalsIgnoreCase("red")) 
                redKings++;
            else 
                blackKings++;
        }
    }

    public int boardHeuristic(){
        //black == maximizer
        //red == minimizer
        int value = 0;
        if(black.isEmpty()){
            value-=1000;
            return value;
        }
        else if(red.isEmpty()){
            value+=1000;
            return value;
        }

        value += (black.size()*3) + (blackKings*2) - (red.size()*3) - (redKings*2);
        for(int i=3;i<=4;i++){
            for(int j=2;j<=5;j++){
                if(matrix[i][j].getisOccupied()){
                    if(matrix[i][j].getOccupiedBy().getColor().equalsIgnoreCase("red")){
                        value--;
                    }
                    else
                        value++;
                }
            }
        }
        return value;
    }
}
