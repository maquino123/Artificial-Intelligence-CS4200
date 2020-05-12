import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class GeneticAlgorithm {
    double mutationRate = 0.20;

    public Node geneticAlgorithm(ArrayList<Node> initialCollection){
        ArrayList<Node> collection = initialCollection;
        ArrayList<Node> newCollection = new ArrayList<>();


        //Problem with an infinite loop
        while (true){
            Node parent1 = parentSelection(collection);
            Node parent2 = parentSelection(collection);

            Node[] childrenArray = reproduce(parent1, parent2);

            for (Node i: childrenArray){
                mutate(i);
                //For some reason, the while loop won't exit due to this
                if(i.fitnessFunction() == 0){
                    return i;
                }
                newCollection.add(i);
                return i;
            }

            if(newCollection.size() == 25){
                ArrayList<Node> temp = newCollection;
                collection = temp;
                newCollection.clear();
            }
        }
    }


    //Parent Selection won't work, both parents are the same
    private Node parentSelection(ArrayList<Node> collection){
        Random random = new Random();
        Node bestFit = collection.get(random.nextInt(collection.size()));
        bestFit.setFitnessScore(bestFit.fitnessFunction());

        for (int i = 1; i < random.nextInt(collection.size()); i++){
            Node node = new Node();
            int randomInteger = random.nextInt(collection.size());
            node.setCurrentState(collection.get(randomInteger).getCurrentState());
            node.setFitnessScore(collection.get(randomInteger).fitnessFunction());

            if(node.getFitnessScore() < bestFit.getFitnessScore()){
                bestFit = new Node(node.getCurrentState());
                bestFit.setFitnessScore(node.getFitnessScore());
            }
        }
        return bestFit;
    }


    private void mutate(Node node){
        int[] currentState = node.getCurrentState();
        if(Math.random() < mutationRate){
            currentState[(int) (Math.random() * 25)] = (int)(Math.random() * 25);
            node.setFitnessScore(node.fitnessFunction());
        }
    }

    private static Node[] reproduce(Node a, Node b){
        int[] bound = new int[25];
        int[] childA = a.getCurrentState().clone();
        int[] childB = b.getCurrentState().clone();

        int crossoverPoint = (int) ((Math.random() * 24) + 1);

        System.arraycopy(childA, 0, bound, 0, crossoverPoint);
        System.arraycopy(childB, 0, childA, 0, crossoverPoint);
        System.arraycopy(bound, 0, childB, 0, crossoverPoint);

        a.setCurrentState(childA);
        a.setFitnessScore(a.fitnessFunction());
        b.setCurrentState(childB);
        b.setFitnessScore(b.getFitnessScore());

        Node child1 = new Node(childA);
        Node child2 = new Node(childB);
        return new Node[]{child1, child2};

    }
}