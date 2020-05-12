import java.util.ArrayList;
import java.util.Random;

public class MinConflicts {
    static ArrayList<Integer> queenPositions;
    public MinConflicts(){
        queenPositions = new ArrayList<>(25);
    }

    //Places queens in random position and makes sure that each queen is in a different column
    public static void placeRandomQueen(int n){
        Random rand = new Random();
        int row = 0;
        int column = 0;

        while (row < n){
            column = rand.nextInt(n);
            if(!queenPositions.contains(column)){
                queenPositions.add(row, column);
                row++;
            }
        }
    }

    //Returns the number of queens attacking
    public static int attackingQueens(ArrayList<Integer> queenPositions){
        //The more queens attacking, the higher the number
        // The smaller number being attacked, the lower the number;
        int attackedCount = 0; //Keeps track of any attacking queens
        for (int row = 0; row < queenPositions.size() - 1; row++){
            for (int col = row + 1; col < queenPositions.size(); col++){
                if (row != col){
                    if(( col - row == Math.abs(queenPositions.get(col) - queenPositions.get(row))) || queenPositions.get(col) == queenPositions.get(row) ){
                        attackedCount++;
                    }
                }
            }
        }
        return attackedCount;
    }

    //This algorithm selects a a column and moves the queen in the column to minimize the number of queens attacking it
    //Repeats this until a solution is found
    public static void minConflicts(int maxSteps){
        Random rand = new Random();
        int stepCounter = 0;
        while (attackingQueens(queenPositions) > 0 && stepCounter < maxSteps){
            //Select a random column
            int randomColumn = rand.nextInt(queenPositions.size());
            int totalConflicts = attackingQueens(queenPositions);
            int row = queenPositions.get(randomColumn);

            for (int i = 0; i < queenPositions.size(); i++){
                if (i == queenPositions.get(randomColumn)){
                    continue;
                }else{
                    ArrayList<Integer> newQueenPositions = new ArrayList<>();
                    for (int j: queenPositions){
                        newQueenPositions.add(j);
                    }
                    newQueenPositions.set(randomColumn, i);
                    if (attackingQueens(newQueenPositions) <= totalConflicts){
                        totalConflicts = attackingQueens(newQueenPositions);
                        row = i;
                    }
                }
            }
            stepCounter++;
            queenPositions.set(randomColumn, row);
        }
    }

    //Get the final solution of the board
    public static boolean getSolution(int n){
        System.out.print("Min-Conflicts Solution: [ ");
        for (int i = 0; i < queenPositions.size(); i++){
            System.out.print(queenPositions.get(i) + " ");
        }
        System.out.print("]");
        System.out.println(" Attacking Queen Count: " + attackingQueens(queenPositions));
        if (attackingQueens(queenPositions) == 0){
            return true;
        }
        return false;
    }
}
