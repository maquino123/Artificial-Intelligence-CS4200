import java.util.ArrayList;
import java.util.Arrays;

public class Board{
    ArrayList<Board> children;
    int boardSize = 8;
    char[][] currentState;
    int[] currentXPosition;
    int[] currentOPosition;
    String[] opponentMoves = new String[64];
    String[] computerMoves = new String[64];
    int countXMoves = 0;
    int countOMoves = 0;


    //Constructors for Board
    public Board(char playing) {
        currentState = new char[boardSize][boardSize];
        currentXPosition = new int[2];
        currentOPosition = new int[2];

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                currentState[i][j] = '-';
            }
        }
        if (playing == 'X') {
            currentState[0][0] = 'X';
            currentState[boardSize - 1][boardSize - 1] = 'O';
            currentXPosition[0] = 0;
            currentXPosition[1] = 0;
            currentOPosition[0] = 7;
            currentOPosition[1] = 7;

        }else{
            currentState[0][0] = 'O';
            currentState[boardSize - 1][boardSize - 1] = 'X';
            currentXPosition[0] = 7;
            currentXPosition[1] = 7;
            currentOPosition[0] = 0;
            currentOPosition[1] = 0;
        }
    }

    public Board(char[][] state){
        currentState = state;
        currentXPosition = new int[2];
        currentOPosition = new int[2];

        for (int i = 0; i < boardSize; i++){
            for (int j = 0; j < boardSize; j++){
                if (state[i][j] == 'X'){
                    currentXPosition[0] = i;
                    currentXPosition[1] = j;
                }if (state[i][j] == 'O'){
                    currentOPosition[0] = i;
                    currentOPosition[1] = j;
                }
            }
        }
    }

    //Checking the move is within the bounds of the board
    public boolean isWithinBounds(int row, int column) {
        if (row >= 0 && row < boardSize && column >= 0 && column < boardSize) {
            return true;
        }
        return false;
    }


    //Returns number of possible moves for either player
    public boolean validMoves(char player, int rowToMove, int columnToMove) {
        int rowPosition, colPosition;
        int rowDistance, colDistance;

        //Can't move out of bounds
        if (!isWithinBounds(rowToMove, columnToMove)) {
            //System.out.println("Can't move there! This move is out of the board!");
            return false;
        }

        //Can't move if it is taken
        if (currentState[rowToMove][columnToMove] != '-') {
            //System.out.println("Can't move there! That spot is already taken!");
            return false;
        }

        if (player == 'O') {
            rowPosition = currentOPosition[0];
            colPosition = currentOPosition[1];
        } else {
            rowPosition = currentXPosition[0];
            colPosition = currentXPosition[1];
        }

        //Calculating the distance between rows and columns
        rowDistance = Math.abs(rowPosition - rowToMove);
        colDistance = Math.abs(colPosition - columnToMove);

        if (rowDistance == 0 && colDistance == 0) {
            //System.out.println("Can't move there! It's the same spot!");
            return false;
        }

        //Checking for valid moves going Up
        if ((rowToMove < rowPosition) && (colPosition == columnToMove)) {
            int i = 1;
            while (i <= rowDistance) {
                if (currentState[rowPosition - i][columnToMove] != '-') {
                    //System.out.println("Invalid move! Can't move up!");
                    return false;
                }
                i++;
            }
            return true;
        }

        //Checking for valid moves going Down
        else if ((rowToMove > rowPosition) && (columnToMove == colPosition)) {
            int i = 1;
            while (i <= rowDistance) {
                    if (currentState[rowPosition + i][columnToMove] != '-') {
                        //System.out.println("Invalid move! Can't move down!");
                        return false;
                    }
                    i++;
                }
                return true;
            }

            //Checking for valid moves going Right
        else if ((columnToMove > colPosition) && (rowPosition == rowToMove)) {
                int i = 1;
                while (i <= colDistance) {
                    if (currentState[rowToMove][colPosition + i] != '-') {
                        //System.out.println("Invalid move! Can't move Right!");
                        return false;
                    }
                    i++;
                }
                return true;
            }

            //Checking for valid moves going Left
            else if ((columnToMove < colPosition) && (rowPosition == rowToMove)) {
                int i = 1;
                while (i <= colDistance) {
                    if (currentState[rowToMove][colPosition - i] != '-') {
                        //System.out.println("Invalid move! Can't move Left!");
                        return false;
                    }
                    i++;
                }
                return true;
            }

            //Checking for valid moves with the Diagonals
            else if (rowDistance == colDistance) {

                //Checking diagonal going Up and Left
                if (rowToMove < rowPosition && columnToMove < colPosition) {
                    int i = 1;
                    while (i <= colDistance) {
                        if (currentState[rowPosition - i][colPosition - i] != '-') {
                            //System.out.println("Invalid move! Can't move diagonally!");
                            return false;
                        }
                        i++;
                    }
                    return true;
                }

                //Checking diagonal going Up and Right
                else if (rowToMove < rowPosition && columnToMove > colPosition) {
                    int i = 1;
                    while (i <= colDistance) {
                        if (currentState[rowPosition - i][colPosition + i] != '-') {
                            //System.out.println("Invalid move! Can't move diagonally!");
                            return false;
                        }
                        i++;
                    }
                    return true;
                }

                //Checking diagonal going down and right
                else if (rowToMove > rowPosition && columnToMove > colPosition) {
                    int i = 1;
                    while (i <= colDistance) {
                        if (currentState[rowPosition + i][colPosition + i] != '-') {
                            //System.out.println("Invalid move! Can't move diagonally!");
                            return false;
                        }
                        i++;
                    }
                    return true;
                }

                //Checking diagonal going down and left
                else if (rowToMove > rowPosition && columnToMove < colPosition) {
                    int i = 1;
                    while (i <= colDistance) {
                        if (currentState[rowPosition + i][colPosition - i] != '-') {
                            //System.out.println("Invalid move! Can't move diagonally!");
                            return false;
                        }
                        i++;
                    }
                    return true;
                }

            }
            return false;
        }

    //Checks if the player has lost
    public boolean checkIfLose(char player) {
        int currentRow, currentCol;
        boolean playerLost = true;
        if (player == 'O') {
            currentRow = currentOPosition[0];
            currentCol = currentOPosition[1];
        } else {
            currentRow = currentXPosition[0];
            currentCol = currentXPosition[1];
        }

        //Checks on left and right side
        for (int i = -1; i < 2; i++){
            if (isWithinBounds(currentRow + i, currentCol)){
                if(currentState[currentRow + i][currentCol] == '-'){
                    playerLost = false;
                }
            }
        }

        //Checks up and down
        for (int i = -1; i < 2; i++){
            if (isWithinBounds(currentRow, currentCol + i)){
                if(currentState[currentRow][currentCol + i] == '-'){
                    playerLost = false;
                }
            }
        }

        //Checks diagonals
        for (int i = -1; i < 2; i++){
            for (int j = -1; j < 2; j++) {
                if (isWithinBounds(currentRow + i, currentCol + j)) {
                    if (currentState[currentRow + i][currentCol + j] == '-') {
                        playerLost = false;
                    }
                }
            }
        }
        return playerLost;
    }

    //Updates moves for either X or O, checks if there are valid moves available.
    public boolean updateMove(char player, int rowToMove, int colToMove) {
        if (player == 'O' && validMoves(player, rowToMove - 1, colToMove - 1)) {
            int oRow = rowToMove - 1;
            int oCol = colToMove - 1;
            currentState[currentOPosition[0]][currentOPosition[1]] = '#';
            currentOPosition[0] = oRow;
            currentOPosition[1] = oCol;
            currentState[currentOPosition[0]][currentOPosition[1]] = 'O';
            countOMoves++;
            return true;
        } else if (player == 'X' && validMoves(player, rowToMove, colToMove)) {
            int xRow = rowToMove; //For some reason this won't work for rowToMove - 1
            int xCol = colToMove; //For some reason this won't work for colToMove - 1
            currentState[currentXPosition[0]][currentXPosition[1]] = '#';
            currentXPosition[0] = xRow;
            currentXPosition[1] = xCol;
            currentState[currentXPosition[0]][currentXPosition[1]] = 'X';
            countXMoves++;
            return true;
        }
        return false;
    }

    public char convertRow(int row){
        char r = 't'; //T for temporary value. Should never be T

        if(row == 0){
            r = 'A';
        }else if (row == 1){
            r = 'B';
        }else if (row == 2){
            r = 'C';
        }else if (row == 3){
            r = 'D';
        }else if (row == 4){
            r = 'E';
        }else if (row == 5){
            r = 'F';
        }else if (row == 6){
            r = 'G';
        }else if (row == 7){
            r = 'H';
        }
        return r;
    }

    //Records the move to be printed later
    public void recordMove(Board board, char player){
        if (player == 'O'){
            char[] oMovesArray = {convertRow(board.currentOPosition[0]), (char) (board.currentOPosition[1] + '1')};
            String oRecordMove = String.copyValueOf(oMovesArray);
            opponentMoves[countOMoves - 1] = oRecordMove;
        }else if (player == 'X'){
            char[] xMovesArray = {convertRow(board.currentXPosition[0]), (char) (board.currentXPosition[1] + '1')};
            String xRecordMove = String.copyValueOf(xMovesArray);
            computerMoves[countXMoves - 1] = xRecordMove;
        }
    }

    //Generates successors
    public ArrayList<Board> generateChildren(char player){
        int rowToMove, colToMove;
        children = new ArrayList<Board>();
        if (player == 'O'){
            rowToMove = currentOPosition[0];
            colToMove = currentOPosition[1];
        }else{
            rowToMove = currentXPosition[0];
            colToMove = currentXPosition[1];
        }

        //Successors going UP
        for (int i = rowToMove - 1; i >= 0; i--){
            if (currentState[i][colToMove] == '-'){
                if(validMoves(player, i, colToMove)) {
                    char[][] newState = deepCopyClone(currentState);
                    newState[i][colToMove] = player;
                    newState[rowToMove][colToMove] = '#';
                    Board child = new Board(newState);
                    children.add(child);
                }
            }
        }

        //Successors going DOWN
        for (int i = rowToMove + 1; i < boardSize; i++){
            if (currentState[i][colToMove] == '-'){
                if(validMoves(player, i, colToMove)) {
                    char[][] newState = deepCopyClone(currentState);
                    newState[i][colToMove] = player;
                    newState[rowToMove][colToMove] = '#';
                    Board child = new Board(newState);
                    children.add(child);
                }
            }
        }

        //Successors going LEFT
        for (int i = colToMove - 1; i >= 0; i--){
            if (currentState[rowToMove][i] == '-'){
                if(validMoves(player, rowToMove, i)) {
                    char[][] newState = deepCopyClone(currentState);
                    newState[rowToMove][i] = player;
                    newState[rowToMove][colToMove] = '#';
                    Board child = new Board(newState);
                    children.add(child);
                }
            }
        }

        //Successors going RIGHT
        for (int i = colToMove + 1; i < boardSize; i++){
            if (currentState[rowToMove][i] == '-'){
                if(validMoves(player, rowToMove, i)) {
                    char[][] newState = deepCopyClone(currentState);
                    newState[rowToMove][i] = player;
                    newState[rowToMove][colToMove] = '#';
                    Board child = new Board(newState);
                    children.add(child);
                }
            }
        }

        //Successors going UP and LEFT
        for (int i = rowToMove - 1, j = colToMove - 1; i >= 0 && j >= 0; i--, j--){
            if (currentState[i][j] == '-'){
                if(validMoves(player, i, j)) {
                    char[][] newState = deepCopyClone(currentState);
                    newState[i][j] = player;
                    newState[rowToMove][colToMove] = '#';
                    Board child = new Board(newState);
                    children.add(child);
                }
            }
        }

        //Successors going UP and RIGHT
        for (int i = rowToMove - 1, j = colToMove + 1; i >= 0 && j < boardSize; i--, j++){
            if (currentState[i][j] == '-'){
                if(validMoves(player, i, j)) {
                    char[][] newState = deepCopyClone(currentState);
                    newState[i][j] = player;
                    newState[rowToMove][colToMove] = '#';
                    Board child = new Board(newState);
                    children.add(child);
                }
            }

        }

        //Successors going DOWN and LEFT
        for (int i = rowToMove + 1, j = colToMove - 1; i < boardSize && j >= 0; i++, j--){
            if (currentState[i][j] == '-'){
                if(validMoves(player, i, j)) {
                    char[][] newState = deepCopyClone(currentState);
                    newState[i][j] = player;
                    newState[rowToMove][colToMove] = '#';
                    Board child = new Board(newState);
                    children.add(child);
                }
            }

        }

        //Successors going DOWN and RIGHT
        for (int i = rowToMove + 1, j = colToMove + 1; i < boardSize && j < boardSize; i++, j++){
            if (currentState[i][j] == '-'){
                if(validMoves(player, i, j)) {
                    char[][] newState = deepCopyClone(currentState);
                    newState[i][j] = player;
                    newState[rowToMove][colToMove] = '#';
                    Board child = new Board(newState);
                    children.add(child);
                }
            }
        }
        return children;
    }


    //Heuristic Score = Number of unused spaces around it
    public int heuristicEvaluation(char player){
        int rowToMove;
        int colToMove;
        int emptySpaceCount = 0;

        if(player == 'O'){
            rowToMove = currentOPosition[0];
            colToMove = currentOPosition[1];
        }else{
            rowToMove = currentXPosition[0];
            colToMove = currentXPosition[1];
        }
        for (int i = rowToMove - 1; i >= 0; i--){
            if (currentState[i][colToMove] == '-'){
                if(validMoves(player, i, colToMove)) {
                    emptySpaceCount++;
                }
            }
        }

        for (int i = rowToMove + 1; i < boardSize; i++){
            if (currentState[i][colToMove] == '-'){
                if(validMoves(player, i, colToMove)) {
                    emptySpaceCount++;
                }
            }
        }

        for (int i = colToMove - 1; i >= 0; i--){
            if (currentState[rowToMove][i] == '-'){
                if(validMoves(player, rowToMove, i)) {
                    emptySpaceCount++;
                }
            }
        }

        for (int i = colToMove + 1; i < boardSize; i++){
            if (currentState[rowToMove][i] == '-'){
                if(validMoves(player, rowToMove, i)) {
                    emptySpaceCount++;
                }
            }
        }

        for (int i = rowToMove - 1, j = colToMove - 1; i >= 0 && j >= 0; i--, j--){
            if (currentState[i][j] == '-'){
                if(validMoves(player, i, j)) {
                    emptySpaceCount++;
                }
            }
        }

        for (int i = rowToMove - 1, j = colToMove + 1; i >= 0 && j < boardSize; i--, j++){
            if (currentState[i][j] == '-'){
                if(validMoves(player, i, j)) {
                    emptySpaceCount++;
                }
            }

        }

        for (int i = rowToMove + 1, j = colToMove - 1; i < boardSize && j >= 0; i++, j--){
            if (currentState[i][j] == '-'){
                if(validMoves(player, i, j)) {
                    emptySpaceCount++;
                }
            }
        }

        for (int i = rowToMove + 1, j = colToMove + 1; i < boardSize && j < boardSize; i++, j++){
            if (currentState[i][j] == '-'){
                if(validMoves(player, i, j)) {
                    emptySpaceCount++;
                }
            }
        }
        return emptySpaceCount;
    }
    //Prints the board and the current state
    public void printBoard(){
        char[] rowLetters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
        System.out.println("  1  2  3  4  5  6  7  8       Computer vs. Opponent");
        for (int i = 0; i < computerMoves.length; i++){
            if (i < boardSize) {
                System.out.print(rowLetters[i]);
                for (int j = 0; j < boardSize; j++) {
                    System.out.print(" " + currentState[i][j] + " ");
                }
                if (computerMoves[i] != null){
                    System.out.print("        " + (i+1) + ". " + computerMoves[i]);
                }else{
                    System.out.print("             ");
                }
                if(opponentMoves[i] != null) {
                    System.out.print("        " + opponentMoves[i]);
                }else{
                    System.out.print("        ");
                }
                System.out.println();
            }

            if (i >= boardSize && computerMoves[i] != null){
                if (i >= 9){
                    System.out.print("                                " + (i + 1) + ". " + computerMoves[i]);
                }else {
                    System.out.print("                                 " + (i + 1) + ". " + computerMoves[i]);
                }
            }
            if(i >= boardSize && opponentMoves[i] != null) {
                System.out.print("        " + opponentMoves[i]);
                System.out.println();
            }
        }
        System.out.println();
    }


    //Method that generates a deep copy of the original char array.
    public static char[][] deepCopyClone(char[][] original) {
        if (original == null) {
            return null;
        }
        final char[][] result = new char[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }
}
