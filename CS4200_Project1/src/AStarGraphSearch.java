import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class AStarGraphSearch {
    private int nodesGenerated; //Search cost

    public HashMap<Integer, Integer> AStarSearch(Board board) throws Exception {

        PriorityQueue<Board> frontier = new PriorityQueue<>();
        HashSet<Board> explored = new HashSet<>();
        HashMap<Integer, Integer> depthMap = new HashMap<>();
        nodesGenerated = 0;

        long startTime = System.nanoTime();
        frontier.add(board);

        while (!frontier.isEmpty()) {
            Board b = frontier.poll();

            if (b.isGoalState()) {
                long endTime = System.nanoTime() - startTime;

                b.printStates();

                int heuristic = b.getHeuristic();
                if (heuristic == 1) {
                    System.out.println("Heuristic: Hamming Distance");
                } else if (heuristic == 2) {
                    System.out.println("Heuristic: Manhattan Distance");
                }

                System.out.println("Search Cost: " + nodesGenerated);
                System.out.println("Time Spent on Search: " + endTime + " ns");
                System.out.println("Path Cost: " + b.getFn());
                depthMap.put(b.getGn(), nodesGenerated);

                return depthMap;
            }

            ArrayList<Board> successors = b.getSuccessors();
            for (Board i : successors) {
                if (!explored.contains(i)) {
                    frontier.add(i);
                    nodesGenerated++;
                }
            }

            explored.add(b);
        }

        throw new Exception("Puzzle reached end of frontier");

    }
}

