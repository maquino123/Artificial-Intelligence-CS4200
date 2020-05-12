import java.util.ArrayList;
import java.util.Collections;

public class MiniMax {
    public static Board bestMove = null;
    long currentTime;
    long startTime;
    public static int TIME_LIMIT;

    public MiniMax(int timeLimit) {
        TIME_LIMIT = timeLimit;
    }

    //Taken from pseudocode with some changes
    public int miniMaxSearch(Board board, char player, int depth, int alpha, int beta) {
        Board currentBestMove = board;

        //Base Case
        if (depth == 0 || board.checkIfLose('X') || board.checkIfLose('O') || currentTime >= (TIME_LIMIT * 1000)) {
            return evaluationFunction(board);
        }

        if (player == 'X') {
            int maxEvaluation = -2147483648;
            ArrayList<Board> successors = board.generateChildren('X');
            Collections.shuffle(successors); //Adding randomness to the algorithm

            for (int i = 0; i < successors.size(); i++) {
                int evaluation = miniMaxSearch(successors.get(i), 'O', depth - 1, alpha, beta);
                if (evaluation > maxEvaluation) {
                    currentBestMove = successors.get(i);
                    maxEvaluation = Math.max(maxEvaluation, evaluation);
                }
                alpha = Math.max(alpha, evaluation);
                if (alpha >= beta){
                    break;
                }
            }
            bestMove = currentBestMove;
            long endTime = System.currentTimeMillis();
            currentTime = endTime - startTime;
            return maxEvaluation;

        } else{
            int minEvaluation = 2147483647;
            ArrayList<Board> successors = board.generateChildren('O');
            Collections.shuffle(successors); //Adding randomness to the algorithm

            for (int i = 0; i < successors.size(); i++) {
                int evaluation = miniMaxSearch(successors.get(i), 'X', depth - 1, alpha, beta);
                minEvaluation = Math.min(minEvaluation, evaluation);
                beta = Math.min(beta, evaluation);
                if (alpha >= beta){
                    break;
                }
            }
            long endTime = System.currentTimeMillis();
            currentTime = endTime - startTime;
            return minEvaluation;
        }
    }

    //Difference between the X player heuristic and O player heuristic
    public int evaluationFunction(Board board){
        int xHeuristic = board.heuristicEvaluation('X');
        int oHeuristic = board.heuristicEvaluation('O');
        return xHeuristic - oHeuristic;
    }
}
