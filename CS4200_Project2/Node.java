import java.lang.reflect.Array;
import java.util.ArrayList;

public class Node {

    private ArrayList<Node> collection;
    private int fitnessScore;
    private int[] currentState = new int[25];

    public Node(){

    }

    public Node(int[] node){
        fitnessScore = this.fitnessFunction();
        this.currentState = node.clone();
    }

    public int fitnessFunction(){
        //The more queens attacking, the lower the fitness score
        //The smaller number being attacked, the higher the fitness score;
        int attackedCount = 0; //Keeps track of any attacking queens
        for (int  row = 0; row < currentState.length-1; row++){
            for (int col = row + 1; col < currentState.length; col++){
                if (row != col){
                    if(( col - row == Math.abs(currentState[col] - currentState[row])) || currentState[col] == currentState[row] ){
                        attackedCount++;
                    }
                }
            }
        }
        return attackedCount;
    }

    //Setters and Getters
    public int setFitnessScore(int fitnessScore){
        return this.fitnessScore = fitnessScore;
    }

    public int getFitnessScore(){
        return fitnessScore;
    }

    public void setCurrentState(int[] currentState){
        this.currentState = currentState;
    }

    public int[] getCurrentState(){
        return currentState;
    }

    public int length(){
        return currentState.length;
    }

    public void addOrMoveQueen(int index, int position){
        currentState[index] = position;
    }


    @Override
    public boolean equals(Object node){
        Node n = (Node) node;
        if (!(node instanceof Node)){
            return false;
        }
        return this.toString().equals(n.toString());
    }

    @Override
    public String toString(){
        String resultingState = "[";
        for (int i = 0; i < currentState.length; i++){
            resultingState = resultingState + currentState[i] + ", ";

        }
        resultingState = resultingState + "]";
        return resultingState;
    }

    public void setCollection(Node collection){
        ArrayList<Node> copy = new ArrayList<>(collection.getCollection());
    }

    public ArrayList<Node> getCollection(){
        return collection;
    }

}
