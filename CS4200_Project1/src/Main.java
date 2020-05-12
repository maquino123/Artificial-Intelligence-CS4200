import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static AStarGraphSearch AStar = new AStarGraphSearch();
    private static AStarTreeSearch AStarTree = new AStarTreeSearch();
    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the 8-Puzzle Problem!");
        System.out.println("Please choose an option: ");
        System.out.println("1: Randomly generate a puzzle");
        System.out.println("2: Input your own 8-puzzle");
        System.out.println("3: Exit");

        int userChoice = scanner.nextInt();

        if (userChoice == 1){
            randomPuzzle();
        }else if (userChoice == 2){
            inputPuzzle();
        }else if (userChoice == 3){
            System.exit(0);
        }else{
            System.out.println("Invalid choice: Please enter 1, 2, or 3");
        }
    }

    private static void inputPuzzle() throws Exception{
        Scanner scanner = new Scanner(System.in);
        int[] puzzle = new int[9];
        System.out.println("Please enter your custom puzzle, with 0 as the blank tile.");

        for (int i = 0; i < puzzle.length; i++){
            System.out.print("Position " + i + ": ");
            int input = scanner.nextInt();
            puzzle[i] = input;
        }

        Board board = new Board(puzzle, 1);
        if (!board.isSolvable()){
            System.out.println("The puzzle can't be solved");
            return;
        }else{
            System.out.println("----------------------------------------------------");
            System.out.println("SOLVING USING GRAPH SEARCH WITH HAMMING DISTANCE");
            AStar.AStarSearch(board);

            System.out.println("----------------------------------------------------");

            System.out.println("SOLVING USING TREE SEARCH WITH HAMMING DISTANCE");
            AStarTree.AStarSearch(board);
        }

        board = new Board(puzzle, 2);
        if (!board.isSolvable()){
            System.out.println("The puzzle can't be solved");
        }else{
            System.out.println("----------------------------------------------------");
            System.out.println("SOLVING USING GRAPH SEARCH WITH MANHATTAN DISTANCE");
            AStar.AStarSearch(board);

            System.out.println("----------------------------------------------------");

            System.out.println("SOLVING USING TREE SEARCH WITH MANHATTAN DISTANCE");
            AStarTree.AStarSearch(board);

        }
    }

    private static void randomPuzzle() throws Exception{
        Board randomBoard = new Board();
        System.out.println("Created a random board: " + Arrays.toString(randomBoard.getBoard()));
        randomBoard.setHeuristic(1);

        System.out.println("------------------------------------------------------");
        System.out.println("SOLVING USING GRAPH SEARCH WITH HAMMING DISTANCE");
        AStar.AStarSearch(randomBoard);

        System.out.println("------------------------------------------------------");
        System.out.println("SOLVING USING TREE SEARCH WITH HAMMING DISTANCE");
        AStarTree.AStarSearch(randomBoard);

        randomBoard.setHeuristic(2);

        System.out.println("------------------------------------------------------");

        System.out.println("SOLVING USING GRAPH SEARCH WITH MANHATTAN DISTANCE");
        AStar.AStarSearch(randomBoard);
        System.out.println("------------------------------------------------------");
        System.out.println("SOLVING USING TREE SEARCH WITH MANHATTAN DISTANCE");
        AStarTree.AStarSearch(randomBoard);
    }
}
