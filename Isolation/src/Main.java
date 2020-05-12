import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        char player;
        int time;


        Scanner scanner = new Scanner(System.in);

        System.out.print("Who goes first, C for computer, O for opponent: ");
        char playsFirst = scanner.next().charAt(0);
        System.out.println();

        System.out.print("What is the time limit in seconds: ");
        time = scanner.nextInt();
        System.out.println();

        if(playsFirst == 'C' || playsFirst == 'c'){
            player = 'X';

        }else if (playsFirst == 'O' || playsFirst == 'o'){
            player = 'O';
        }else{
            player = ' ';
        }

        Board board = new Board(player);
        MiniMax miniMax = new MiniMax(time);
        board.printBoard();

        if(player == 'O'){
            int row = 0;
            int col;
            System.out.print("Enter opponent's move: ");
            long startTime = System.currentTimeMillis();
            String move = scanner.next();
            long endTime = System.currentTimeMillis();
            long timeElapsed = endTime - startTime;

            if(timeElapsed >= (time * 1000)){
                System.out.println("Opponent took too much time!");
                System.exit(0);
            }

            String rowLetter = move.substring(0, 1);

            switch(rowLetter){
                case "A": row = 1; break;
                case "B": row = 2; break;
                case "C": row = 3; break;
                case "D": row = 4; break;
                case "E": row = 5; break;
                case "F": row = 6; break;
                case "G": row = 7; break;
                case "H": row = 8; break;
            }

            col = Integer.parseInt(move.substring((1)));

            while(!board.validMoves('O', row - 1, col - 1)){
                System.out.println("Invalid move!");
                System.out.print("Enter opponent's move: ");
                move = scanner.next();
                endTime = System.currentTimeMillis();
                timeElapsed = endTime - startTime;

                if(timeElapsed >= (time * 1000)){
                    System.out.println("Opponent took too much time!");
                    System.exit(0);
                }

                rowLetter = move.substring(0, 1);

                switch(rowLetter){
                    case "A": row = 1; break;
                    case "B": row = 2; break;
                    case "C": row = 3; break;
                    case "D": row = 4; break;
                    case "E": row = 5; break;
                    case "F": row = 6; break;
                    case "G": row = 7; break;
                    case "H": row = 8; break;
                }
                col = Integer.parseInt(move.substring((1)));
            }

            board.updateMove('O', row, col);
            board.recordMove(board, 'O');
            board.printBoard();
        }
        int index = 0;

        while(true) {
            System.out.println("Computer choosing a move....");
            miniMax.startTime = System.currentTimeMillis();
            miniMax.miniMaxSearch(board, 'X', 6, -2147483648, 2147483647);
            miniMax.currentTime = 0;
            board.updateMove('X', miniMax.bestMove.currentXPosition[0], miniMax.bestMove.currentXPosition[1]);
            board.recordMove(miniMax.bestMove, 'X');
            board.printBoard();

            for (int i = index; i <= index; i++){
                if (board.computerMoves[i] != null){
                    System.out.println("Computer's move is: " + board.computerMoves[i]);
                }else{
                    break;
                }
            }
            index++;

            if(board.checkIfLose('X')){
                System.out.println("Player X Lost!");
                System.exit(0);
            }if(board.checkIfLose('O')){
                System.out.println("Player O Lost!");
                System.exit(0);
            }

            int row = 0;
            int col;
            System.out.print("Enter opponent's move: ");
            long startTime = System.currentTimeMillis();
            String move = scanner.next();
            long endTime = System.currentTimeMillis();
            long timeElapsed = endTime - startTime;

            if(timeElapsed >= (time * 1000)){
                System.out.println("Opponent took too much time!");
                System.exit(0);
            }

            String rowLetter = move.substring(0, 1);

            switch(rowLetter){
                case "A": row = 1; break;
                case "B": row = 2; break;
                case "C": row = 3; break;
                case "D": row = 4; break;
                case "E": row = 5; break;
                case "F": row = 6; break;
                case "G": row = 7; break;
                case "H": row = 8; break;
            }

            col = Integer.parseInt(move.substring((1)));

            while(!board.validMoves('O', row - 1, col - 1)){
                System.out.println("Invalid move!");
                System.out.print("Enter opponent's move: ");
                move = scanner.next();
                endTime = System.currentTimeMillis();
                timeElapsed = endTime - startTime;

                if(timeElapsed >= (time * 1000)){
                    System.out.println("Opponent took too much time!");
                    System.exit(0);
                }

                rowLetter = move.substring(0, 1);

                switch(rowLetter){
                    case "A": row = 1; break;
                    case "B": row = 2; break;
                    case "C": row = 3; break;
                    case "D": row = 4; break;
                    case "E": row = 5; break;
                    case "F": row = 6; break;
                    case "G": row = 7; break;
                    case "H": row = 8; break;
                }
                col = Integer.parseInt(move.substring((1)));
            }

            board.updateMove('O', row, col);
            board.recordMove(board, 'O');
            board.printBoard();
        }

    }

}
