import java.util.*;

class Checkers{

    private Board board;
    private boolean gameEnd;
    private boolean turn;
    private boolean mandatory;
    static Move alpha = new Move(null, -10000);
    static Move beta = new Move (null, 10000); 
    static int count=0;

    public Checkers(){
        board = new Board();
        gameEnd = false;
        mandatory = false; 
        turn  = false; //false - red's turn 
                       //true - black's turn
    }

    public Piece getPiece(int x, int y){
        return board.getPiece(x, y);
    }

    public boolean movePiece(Piece p, int x, int y){
        int tempx,tempy;

        String move = Integer.toString(p.getX()) + Integer.toString(p.getY()) + Integer.toString(x) + Integer.toString(y);
        if(x<0||x>=8 || y<0 || y>=8)
            return false;   
        if(getValidMoves(this.board).contains(move)){
            if(mandatory==true){
                tempx = x-p.getX();
                tempy = y-p.getY();
                if(tempx<0){
                    tempx++;
                    if(tempy<0) tempy++;
                    else tempy--;
                }
                else{
                    tempx--;
                    if(tempy<0) tempy++;
                    else tempy--;
                }
                board.removePiece(p.getX()+tempx, p.getY()+ tempy);
                mandatory = false;
                board.movePiece(p, x, y);
                if(checkMultiple(p,this.board))
                    mandatory=true;
            }
            else
                board.movePiece(p, x, y);
            return true;
        }
        else{
            System.out.println("Invalid Move");
            return false;
        }
    }

    public ArrayList<String> getValidMoves(Board b){
        ArrayList<Piece> pieces;
        ArrayList<String> validMoves = new ArrayList<String>();
        ArrayList<String> mandatoryKill = new ArrayList<String>();
        int left = -1, right = 1;
        int tempX,tempY;
        String temp;

        if(turn==false){ //red piece
            pieces = b.getRedPieces();
            
            for(int i=0;i<pieces.size();i++){
                for(int j=0;j<=1;j++){
                    int redX = -1;
                    tempX = pieces.get(i).getX();
                    tempY = pieces.get(i).getY();
                    //System.out.print(tempX + "" + tempY + " ");
                    if(j==1){
                        if(pieces.get(i).getKing())
                            redX=1;
                        else
                            break;
                    }
                    tempX+=redX;
                    if(tempX>=0 && tempX<=7){
                        if((tempY+left)>=0){ //red move left
                            temp = Integer.toString(pieces.get(i).getX()) + Integer.toString(pieces.get(i).getY()) + Integer.toString(tempX) + Integer.toString(tempY+left);
                            if(b.getPiece(tempX,tempY+left)!=null && b.getPiece(tempX,tempY+left).getColor().equalsIgnoreCase("black")){
                                if((tempX+redX)>=0 && (tempX+redX)<=7 && (tempY+(2*left))>=0 && (tempY+(2*left))<=7 && b.getPiece(tempX+redX,tempY+(2*left))==null){
                                    temp = Integer.toString(pieces.get(i).getX()) + Integer.toString(pieces.get(i).getY()) + Integer.toString(tempX+redX) + Integer.toString(tempY+(2*left));
                                    mandatoryKill.add(temp);
                                }
                            }
                            else if(b.getPiece(tempX,tempY+left)==null)
                                validMoves.add(temp);
                            
                        }
                        if((tempY+right)<=7){ //red move right
                            temp = Integer.toString(pieces.get(i).getX()) + Integer.toString(pieces.get(i).getY()) + Integer.toString(tempX) + Integer.toString(tempY+right);
                            if(b.getPiece(tempX,tempY+right)!=null && b.getPiece(tempX,tempY+right).getColor().equalsIgnoreCase("black")){
                                if((tempX+redX)>=0 && (tempX+redX)<=7 && (tempY+(2*right))>=0 && (tempY+(2*right))<=7 && b.getPiece(tempX+redX,tempY+(2*right))==null){
                                    temp = Integer.toString(pieces.get(i).getX()) + Integer.toString(pieces.get(i).getY()) + Integer.toString(tempX+redX) + Integer.toString(tempY+(2*right));
                                    mandatoryKill.add(temp);
                                }
                            }
                            else if(b.getPiece(tempX,tempY+right)==null)
                                validMoves.add(temp);
                            
                        }
                    }
                }
            
            //System.out.println();
            }
        }
        else{
            pieces = b.getBlackPieces();

            for(int i=0;i<pieces.size();i++){
                for(int j=0;j<=1;j++){
                    int blackX = 1;
                    tempX = pieces.get(i).getX();
                    tempY = pieces.get(i).getY();
                    //System.out.println(tempX + "" + tempY + " ");
                    if(j==1){
                        if(pieces.get(i).getKing())
                            blackX=-1;
                        else
                            break;
                    }
                    tempX+=blackX;
                    if(tempX>=0 && tempX<=7){
                        if((tempY+left)>=0){ //black move left
                            temp = Integer.toString(pieces.get(i).getX()) + Integer.toString(pieces.get(i).getY()) + Integer.toString(tempX) + Integer.toString(tempY+left);
                            if(b.getPiece(tempX,tempY+left)!=null && b.getPiece(tempX,tempY+left).getColor().equalsIgnoreCase("red")){
                                if((tempX+blackX)>=0 && (tempX+blackX)<=7 && (tempY+(2*left))>=0 && (tempY+(2*left))<=7 && b.getPiece(tempX+blackX,tempY+(2*left))==null){
                                    temp = Integer.toString(pieces.get(i).getX()) + Integer.toString(pieces.get(i).getY()) + Integer.toString(tempX+blackX) + Integer.toString(tempY+(2*left));
                                    mandatoryKill.add(temp);
                                }
                            }
                            else if(b.getPiece(tempX,tempY+left)==null)
                                validMoves.add(temp);
                        }
                        if((tempY+right)<=7){ //black move right
                            temp = Integer.toString(pieces.get(i).getX()) + Integer.toString(pieces.get(i).getY()) + Integer.toString(tempX) + Integer.toString(tempY+right);
                            if(b.getPiece(tempX,tempY+right)!=null && b.getPiece(tempX,tempY+right).getColor().equalsIgnoreCase("red")){
                                if((tempX+blackX)>=0 && (tempX+blackX)<=7 && (tempY+(2*right))>=0 && (tempY+(2*right))<=7 && b.getPiece(tempX+blackX,tempY+(2*right))==null){
                                    temp = Integer.toString(pieces.get(i).getX()) + Integer.toString(pieces.get(i).getY()) + Integer.toString(tempX+blackX) + Integer.toString(tempY+(2*right));
                                    mandatoryKill.add(temp);
                                }
                            }
                            else if(b.getPiece(tempX,tempY+right)==null)
                                validMoves.add(temp);
                        }
                    }
                }
            }
        }
        //System.out.println();
        if(!mandatoryKill.isEmpty()){
            mandatory=true;
            // System.out.println("mandatory");
            // System.out.println(mandatoryKill);
            return mandatoryKill;
        }

        // System.out.println("not mandatory");
        // System.out.println(validMoves);
        mandatory=false;
        return validMoves;
    }

    public boolean checkMultiple(Piece p, Board b){
        
        int left = -1, right = 1;
        int redX = -1, blackX = 1;
        int tempX = p.getX();
        int tempY = p.getY();

        if(p.getColor().equalsIgnoreCase("red")){
            tempX+=redX;
            if(tempX>=0 && tempX<=7){
                if((tempY+left)>=0){ //red move left
                    if(b.getPiece(tempX,tempY+left)!=null && b.getPiece(tempX,tempY+left).getColor().equalsIgnoreCase("black")){
                        if((tempX+redX)>=0 && (tempX+redX)<=7 && (tempY+(2*left))>=0 && (tempY+(2*left))<=7 && b.getPiece(tempX+redX,tempY+(2*left))==null){
                            return true;
                        }
                    }
                }
                if((tempY+right)<=7){ //red move right
                    if(b.getPiece(tempX,tempY+right)!=null && b.getPiece(tempX,tempY+right).getColor().equalsIgnoreCase("black")){
                        if((tempX+redX)>=0 && (tempX+redX)<=7 && (tempY+(2*right))>=0 && (tempY+(2*right))<=7 && b.getPiece(tempX+redX,tempY+(2*right))==null){
                            return true;
                        }
                    }
                }
            }
        }
        else{
            tempX+=blackX;
            if(tempX>=0 && tempX<=7){
                if((tempY+left)>=0){ //black move left
                    if(b.getPiece(tempX,tempY+left)!=null && b.getPiece(tempX,tempY+left).getColor().equalsIgnoreCase("red")){
                        if((tempX+blackX)>=0 && (tempX+blackX)<=7 && (tempY+(2*left))>=0 && (tempY+(2*left))<=7 && b.getPiece(tempX+blackX,tempY+(2*left))==null){
                            return true;
                        }
                    }
                }
                if((tempY+right)<=7){ //black move right
                    if(b.getPiece(tempX,tempY+right)!=null && b.getPiece(tempX,tempY+right).getColor().equalsIgnoreCase("red")){
                        if((tempX+blackX)>=0 && (tempX+blackX)<=7 && (tempY+(2*right))>=0 && (tempY+(2*right))<=7 && b.getPiece(tempX+blackX,tempY+(2*right))==null){
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public Board getBoard(){
        return board;
    }

    public boolean getTurn(){
        return turn;
    }
    
    public void setTurn(boolean b){
        turn = b;
    }

    public boolean getGameEnd(){
        return gameEnd;
    }

    public boolean getMandatory(){
        return mandatory;
    }

    public void setGameEnd(boolean b){
        gameEnd = b;
    }

    public void setBoard(Board b){
        this.board = b;
    }

    public Move alphaBeta(int depth, Move m, Boolean maximizingPlayer, Move alpha, Move beta){
        if (depth == 6)
            return new Move(m);

        if(maximizingPlayer){
            setTurn(true);
            Move best = alpha;
            ArrayList<Move> moves = simulateMove(m,getValidMoves(m.getBoard()));
            selectionSort(moves,true);

            for(int i=0;i<moves.size();i++){
                Move val = alphaBeta(depth+1, moves.get(i), false, alpha, beta);
                count++;

                if(val.getValue()>best.getValue()){
                    best = val;
                }
                if(alpha.getValue()<best.getValue()){
                    alpha = best;
                }
                if(beta.getValue() <= alpha.getValue())
                     break;
            }
            return best;
        }
        else{
            setTurn(false);
            Move best = beta;
            ArrayList<Move> moves = simulateMove(m,getValidMoves(m.getBoard()));
            selectionSort(moves,false);
            
            for(int i=0;i<moves.size();i++){
                Move val = alphaBeta(depth+1, moves.get(i), true, alpha, beta);
                count++;

                if(val.getValue()<best.getValue()){
                    best = val;
                }
                if(beta.getValue()>best.getValue()){
                    beta = best;
                }
                if(beta.getValue() <= alpha.getValue())
                      break;
            }
            return best;
        }
        
    }

    public ArrayList<Move> simulateMove(Move m, ArrayList<String> strings){
        ArrayList<Move> moves = new ArrayList<Move>();
        String s = new String();
        int x1,x2,y1,y2;

        if(strings.isEmpty()){
            if(turn==false)
                moves.add(new Move(m.getBoard(),-1000));
            else
                moves.add(new Move(m.getBoard(),1000));

            return moves;
        }

        for(int i=0;i<strings.size();i++){
            if(mandatory==true){
                Board newBoard = new Board(m.getBoard());
                s = strings.get(i);
                x1 = s.charAt(0) - '0';
                y1 = s.charAt(1) - '0';
                x2 = s.charAt(2) - '0';
                y2 = s.charAt(3) - '0';
                Piece temp = newBoard.getPiece(x1, y1);

                int tempx, tempy;
                tempx = x2-temp.getX();
                tempy = y2-temp.getY();
                if(tempx<0){
                    tempx++;
                    if(tempy<0) tempy++;
                    else tempy--;
                }
                else{
                    tempx--;
                    if(tempy<0) tempy++;
                    else tempy--;
                }
                newBoard.removePiece(temp.getX()+tempx, temp.getY()+ tempy);
                newBoard.movePiece(temp, x2, y2);
                Move tempMove = new Move(newBoard, newBoard.boardHeuristic());
                tempMove.setParent(m);
                tempMove.setTurn(getTurn());
                if(checkMultiple(temp, newBoard)){
                   ArrayList<Move> kills = simulateMove(tempMove, getValidMoves(newBoard));
                   for(int k=0;k<kills.size();k++){
                        moves.add(kills.get(k));
                   }
                }
                else{
                    tempMove.setParent(m);
                    tempMove.setTurn(getTurn());
                    moves.add(tempMove);
                }
            }
            else{
                Board newBoard = new Board(m.getBoard());
                s = strings.get(i);
                x1 = s.charAt(0) - '0';
                y1 = s.charAt(1) - '0';
                x2 = s.charAt(2) - '0';
                y2 = s.charAt(3) - '0';
                Piece temp = newBoard.getPiece(x1, y1);
                newBoard.movePiece(temp, x2, y2);
                Move tempMove = new Move(newBoard, newBoard.boardHeuristic());
                tempMove.setParent(m);
                tempMove.setTurn(getTurn());
                moves.add(tempMove);
            }
        }
        return moves;
    }
    
    public void selectionSort(ArrayList<Move> arr, boolean reverse){
        int n = arr.size();
 
        for (int i = 0; i < n-1; i++)
        {
        
            int min = i;
            if(!reverse){
                for (int j = i+1; j < n; j++)
                    if (arr.get(j).getValue() < arr.get(min).getValue())
                        min = j;
            }
            else{
                for (int j = i+1; j < n; j++)
                    if (arr.get(j).getValue() > arr.get(min).getValue())
                        min = j;
            }

            Collections.swap(arr,min,i);
        }
    }

    public static void main(String[] args){
        Checkers checkers = new Checkers();
        Piece chosen;
        Scanner kb = new Scanner(System.in);
        int x1,y1,x2,y2;
        boolean kill=false;

        while(!checkers.getGameEnd()){
            (checkers.getBoard()).printBoard();

            if (checkers.board.getRedPieces().isEmpty() || checkers.getValidMoves(checkers.getBoard()).isEmpty()){
                System.out.println("black wins!");
                checkers.setGameEnd(true);
                break;
            }

            if(checkers.getTurn()==false){
                boolean valid = false;
                while(!valid){
                    System.out.print("Choose x (row) index of chosen piece: ");
                    x1 = kb.nextInt();
                    System.out.print("Choose y (column) index of chosen piece: ");
                    y1 = kb.nextInt();
                    
                    try{
                        chosen = checkers.getPiece(x1, y1);
                    }
                    catch(Exception e){
                        chosen = null;
                    }

                    if(chosen!=null && chosen.getColor().equalsIgnoreCase("red")){
                        System.out.print("Choose x (row) index to move: ");
                        x2 = kb.nextInt();
                        System.out.print("Choose y (column) index to move: ");
                        y2 = kb.nextInt();
                        if(checkers.movePiece(chosen, x2, y2)==true){
                            valid=true;
                            kill = checkers.getMandatory();
                            if(kill!=true)
                                checkers.setTurn(true);
                        }
                    }
                    else{
                        System.out.println("Invalid Piece");
                    }
                }
            }
            else{
                Move current,nextmove;
                ArrayList<Move> moves = new ArrayList<Move>();
                alpha.reset(-10000);
                beta.reset(10000);
                if (checkers.board.getBlackPieces().isEmpty() || checkers.getValidMoves(checkers.getBoard()).isEmpty()){
                    System.out.println("red wins!");
                    checkers.setGameEnd(true);
                }

                nextmove= new Move(checkers.getBoard(),checkers.getBoard().boardHeuristic());
            
                current = nextmove;
                nextmove = checkers.alphaBeta(0, nextmove, true, alpha, beta);

                while(nextmove.getParent() != current && nextmove.getParent() != null){
                    moves.add(nextmove);
                    nextmove = nextmove.getParent();
                }
                moves.add(nextmove);
                int i = moves.size()-2;
                while(!moves.isEmpty() && moves.get(i).getTurn()==true){
                    i--;
                    if(i<0)
                        break;
                }
                nextmove = moves.get(i+1);

                checkers.setBoard(nextmove.getBoard());
                System.out.println("*******************************************************************");
            
                checkers.setTurn(false);
                System.out.println("count: " + count);
                count = 0;
            }
        }
        kb.close();
    }
}