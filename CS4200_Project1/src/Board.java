import java.util.ArrayList;
import java.util.Arrays;

public class Board implements Comparable<Board> {
    private final int[] goalState = {0, 1, 2, 3, 4, 5, 6, 7, 8};
    private int[] board;
    private int gOfn;
    private int hOfn;
    private int heuristic;
    private int indexOfEmptySpace;
    private final Board initialState;

    public Board(int[] board, int heuristic) throws Exception{
        gOfn = 0;
        this.board = board;
        this.heuristic = heuristic;

        if (heuristic == 1){
            hOfn = hammingDistance();
        }else if (heuristic == 2){
            hOfn = manhattanDistance();
        }else{
            throw new Exception("Not a valid heuristic.");
        }
        this.indexOfEmptySpace = findIndexOfEmptySpace();
        initialState = null;
    }

    public Board(Board initState, int newEmptySpace, int heuristic){
        this.board = Arrays.copyOf(initState.board, initState.board.length);
        this.heuristic = heuristic;
        if (heuristic == 1){
            hOfn = hammingDistance();
        }else if (heuristic == 2){
            hOfn = manhattanDistance();
        }

        //Swap
        int temp = board[initState.indexOfEmptySpace];
        board[initState.indexOfEmptySpace] = board[newEmptySpace];
        board[newEmptySpace] = temp;
        this.indexOfEmptySpace = newEmptySpace;
        this.gOfn = initState.gOfn + 1;
        this.initialState = initState;
    }

    public Board(){
        do {
            ArrayList<Integer> numberList = new ArrayList<Integer>( Arrays.asList( 0, 1, 2, 3, 4, 5, 6, 7, 8 ) );
            board = new int[9];
            for (int i = 0; i < board.length; ++i){
                board[i] = numberList.remove((int)(Math.random() * numberList.size()));
            }
        }
        while(!isSolvable());
            gOfn = 0;
            this.indexOfEmptySpace = findIndexOfEmptySpace();
            initialState = null;
    }

    public void setHeuristic(int heuristic){
        this.heuristic = heuristic;
        if (heuristic == 1){
            hOfn = hammingDistance();
        }else if (heuristic == 2){
            hOfn = manhattanDistance();
        }
    }

    private int findIndexOfEmptySpace(){
        for (int i = 0; i < board.length; i++){
            if (board[i] == 0){
                return i;
            }
        }
        return -1;
    }

    public int getFn(){
        return this.gOfn + this.hOfn;
    }

    private int manhattanDistance(){
        int manhattanDistance = 0;
        for (int i = 0; i < board.length; i++){
            if (board[i] != goalState[i]){
                manhattanDistance++;
            }
        }

        return manhattanDistance;
    }

    private int hammingDistance(){
        int hammingDistance = 0;
        for (int i = 0; i < board.length; i++){
            int position = board[i];
            hammingDistance += (Math.abs(position % 3 - i % 3) + Math.abs(position / 3 - i / 3));
        }
        return hammingDistance;
    }

    public boolean isGoalState(){
        return Arrays.equals(this.board, goalState);
    }

    public boolean isSolvable(){
        int inversionCount = 0;
        for (int i = 0; i < board.length - 1; i++){
            for (int j = i + 1; j < board.length; j++){
                if (board[i] > board[j] && board[i] != 0 && board[j] != 0){
                    inversionCount++;
                }
            }
        }

        return inversionCount%2 == 0;
    }

    public ArrayList<Board> getSuccessors(){
        ArrayList<Board> successors = new ArrayList<>();
        int rowWithEmpty = indexOfEmptySpace / 3;
        int colWithEmpty = indexOfEmptySpace % 3;

        for (int i = 0; i < board.length; i++){
            int currRow = i / 3;
            int currCol = i % 3;
            if ((Math.abs(currRow - rowWithEmpty) + Math.abs(currCol - colWithEmpty)) == 1){
                Board board = new Board(this, i, heuristic);
                successors.add(board);
            }
        }

        return successors;
    }

    public int getHeuristic(){
        return heuristic;
    }

    public int getGn(){
        return gOfn;
    }

    public String printBoard(){
        String space = "";
        for (int i = 0; i < board.length; i++){
            space += board[i] + "   ";
            if ((i+1) % 3 == 0 && i != 0 & i != 8){
                space += "\n";
            }
        }

        return space;
    }

    public void printStates(){
        if (initialState != null){
            initialState.printStates();
        }
        System.out.println();
        System.out.println(this.toString());
    }

    public int[] getBoard(){
        return board;
    }

    @Override
    public String toString(){
        return "\n" + this.printBoard();
    }

    @Override
    public int compareTo(Board board){
        if (this.getFn() < board.getFn()){
            return -1;
        }

        if (this.getFn() > board.getFn()){
            return 1;
        }

        return 0;
    }

    @Override
    public int hashCode(){
        return Arrays.hashCode(board);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Board) {
            Board b = (Board) obj;
            return Arrays.equals(this.board, b.board);
        }
        return false;
    }
}
