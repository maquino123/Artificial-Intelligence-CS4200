import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args){
        final double instances= 401;

        long simulatedAnnealingRuntime = 0;
        long geneticRuntime = 0;

        int simulatedAnnealingCounter = 0;
        int geneticCounter = 0;

        int SAAverageStepCost = 0;
        int GAAverageStepCost = 0;

        Random rand = new Random();

        System.out.println("------------SOLVING SIMULATED ANNEALING------------");
        for (int i = 0; i < instances; i++){
            SimulatedAnnealing initialCollection = new SimulatedAnnealing();
            //GeneticAlgorithm population = new GeneticAlgorithm();
                Node node = new Node();
                for (int k = 0; k < node.length(); k++){
                    int row = rand.nextInt(25);
                    node.addOrMoveQueen(k, row);
                }
                node.setFitnessScore(node.fitnessFunction());
                initialCollection.add(node);
              //  collection.add(node);
            Node queenSA = initialCollection.getNode(0);
            long simulatdAnnealingStartTime = System.currentTimeMillis();
            Node solution1 = initialCollection.SimulatedAnnealing(queenSA);
            if(solution1 != null){
                System.out.println("Simulated Annealing Solution: " + solution1.toString() + "Fitness Score: " + node.getFitnessScore());
                simulatedAnnealingCounter++;
            }

            long simulatedAnnealingEndTime = System.currentTimeMillis();
            simulatedAnnealingRuntime = simulatedAnnealingEndTime - simulatdAnnealingStartTime;
            SAAverageStepCost += initialCollection.getStepCost();

        }
        System.out.println("");
        System.out.println("Simulated Annealing Runtime: " + simulatedAnnealingRuntime + "s");
        System.out.println("Percentage of Solved Problems: " + (simulatedAnnealingCounter/instances) * 100 + "%");
        System.out.println("Average Step Cost: " +  Math.round(SAAverageStepCost / instances));

        System.out.println("");
        System.out.println("-----------SOLVING GENETIC ALGORITHM--------------");
        for (int i = 0; i < instances; i++){
            ArrayList<Node> collection = new ArrayList<>();
            Node node = new Node();
            for (int j = 0; j < 50; j++){
                for (int k = 0; k < node.length(); k++){
                    int row = rand.nextInt(25);
                    node.addOrMoveQueen(k, row);
                }
                node.setFitnessScore(node.fitnessFunction());
                collection.add(node);
            }

            GeneticAlgorithm ga = new GeneticAlgorithm();
            long geneticStartTime = System.currentTimeMillis();
            Node solution2 = ga.geneticAlgorithm(collection);

            //System.out.println("");
            if (solution2 != null) {
                System.out.println("Genetic Algorithm Solution: " + solution2.toString() + "Fitness Score: " + node.getFitnessScore());
                geneticCounter++;
            }

            long geneticEndTime = System.currentTimeMillis();
            geneticRuntime = geneticStartTime - geneticEndTime;
        }

        System.out.println("");
        System.out.println("Genetic Algorithm Runtime: " + geneticRuntime + "s");
        System.out.println("Percentage of Solved Problems: " + (geneticCounter/instances) * 100 + "%");

    }
}
