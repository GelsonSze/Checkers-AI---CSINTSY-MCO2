public class Move {
    private int value;
    private Board board;
    private Move parent;
    private boolean turn;

    public Move(Board b, int v){
        this.value = v;
        this.board = b;
        this.parent = null;
    }

    public Move(Move m){
        this.value = m.getValue();
        this.board = m.getBoard();
        this.parent = m.getParent();
        this.turn = m.getTurn();
    }

    public int getValue(){
        return value;
    }

    public Board getBoard(){
        return board;
    }

    public Move getParent(){
        return parent;
    }

    public boolean getTurn(){
        return turn;
    }
    
    public void setTurn(boolean b){
        turn = b;
    }

    public void setParent(Move parent){
        this.parent = parent;
    }

    public void setValue(int v){
        this.value = v;
    }

    public void setBoard(Board b){
        this.board = b;
    }

    public void reset(int value){
        this.board = null;
        this.value = value;
        this.parent = null;

    }
}
