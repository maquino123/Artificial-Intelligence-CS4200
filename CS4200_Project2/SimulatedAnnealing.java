
import java.util.ArrayList;

//************************************************* GUIDELINES *********************************************
//Start with initial configuration
//Repeatedly search neighborhood and select a neighbor as candidate
//Evaluate some cost function (or fitness function) and accept candidate if "better"; if not, select another neighbor
//Stop if quality is sufficiently high, if no improvement can be found or after some fixed time

public class SimulatedAnnealing extends ArrayList<int[]> {
    double coolingRate = 0.999;
    double temperature = 1;
    double runtimeLimit = .000001;
    private ArrayList<Node> collection;
    int stepCost = 0;

    public SimulatedAnnealing(){
        collection = new ArrayList<>();
    }

    public Node SimulatedAnnealing(Node node){
        Node currentNode = node;

        while (true){
            if(temperature <  runtimeLimit){
                return null;
            }
            currentNode = randomSuccessor(currentNode, currentNode.fitnessFunction());
            currentNode.setFitnessScore(currentNode.fitnessFunction());
            if (currentNode.fitnessFunction() == 0){
                return currentNode;
            }
            temperature = temperature * coolingRate;
        }
    }

    private Node randomSuccessor(Node node, int currentFitnessScore){
        int deltaE;
        double probability;
        int length = node.length();
        Node parent = new Node(node.getCurrentState());
        Node child = new Node(node.getCurrentState());

        int randomQueen = (int) (Math.random() * length);
        int newPosition;
        int currentPosition = node.getCurrentState()[randomQueen];

        do {
            newPosition = (int) (Math.random() * length);
        }while (newPosition == currentPosition);
        child.addOrMoveQueen(randomQueen, newPosition);
        stepCost++;
        child.setFitnessScore(child.fitnessFunction());
        int childFitnessScore = child.getFitnessScore();

        if (childFitnessScore < currentFitnessScore){
            return child;
        }

        deltaE = currentFitnessScore - childFitnessScore;
        probability = Math.exp(deltaE / temperature);

        if (Math.random() < probability){
            return child;
        }

        return parent;

    }

    public void add(Node node){
        collection.add(node);
    }

    public Node getNode(int position){
        return collection.get(position);
    }

    public int getStepCost(){
        return stepCost;
    }
}
