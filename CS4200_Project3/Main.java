public class Main {
    public static void main(String[] args){
        long minConflictsRunTime = 0;
        final double iterations = 401;
        int minConflictsCounter = 0;
        final int maxSteps = 5000;

        System.out.println("-------SOLVING N-QUEENS USING MIN-CONFLICTS ALGORITHM-------");

        for (int i = 0; i < iterations; i++) {
            MinConflicts min = new MinConflicts();
            long minConflictsStartTime = System.currentTimeMillis();
            min.placeRandomQueen(25);
            min.minConflicts(maxSteps);
            long minConflictsEndTime = System.currentTimeMillis();
            if (min.getSolution(25) == true){
                minConflictsCounter++;
            }
            minConflictsRunTime += (minConflictsEndTime - minConflictsStartTime);
        }

        System.out.println("Average Run Time: " + Math.round(minConflictsRunTime/iterations) + "s");
        System.out.println("Percentage Solved: " + (minConflictsCounter/iterations) * 100 + "%");
        System.out.println("Sucess Ratio: " + minConflictsCounter + "/" + (int) iterations);
        //System.out.println("Fitness: "   + getFitness(queenPositions));

    }
}
