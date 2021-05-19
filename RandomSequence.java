/**
 * This class creates a RandomSequence based off a pre existing graph
 * Specific to the assignment, class also calculates the fitness value of the class
 * Fitness value is the total Distance between each node in the randomSequence
 */
import java.util.Random;

public class RandomSequence implements Comparable<RandomSequence> {

    Random rand = new Random();
    private final Graph origGraph;
    private Graph randomSequence;
    private int fitValue;

    /**
     * Constructor takes in a graph and creates a new
     * randomized graph based on the given Graph
     * @param origGraph
     */
    public RandomSequence(Graph origGraph) {
        this.origGraph = origGraph;
        randomSequence = new Graph(createRandomSequence());
        fitValue = calculateFit(randomSequence);
    }

    /**
     * Constructor copies variables from an existing RandomSequence class
     * @param randomSequence
     */
    public RandomSequence(RandomSequence randomSequence) {
        this.origGraph =  new Graph(randomSequence.origGraph);
        this.randomSequence = new Graph(randomSequence.randomSequence);
        this.fitValue = randomSequence.getFitValue();
    }

    /**
     * Private helper method
     * Takes Nodes from the origGraph and put it into a new Graph
     * in random Order
     * @return randomizedGraph
     */
    private Graph createRandomSequence() {
        Graph randomSequence = new Graph();
        Graph tempGraph = new Graph(origGraph);

        for (int i = 0; i < origGraph.size(); ++i) {
            int randIndex = rand.nextInt(tempGraph.size());
            randomSequence.addNode(tempGraph.getNode(randIndex));
            tempGraph.removeNode(randIndex);
        }
        return randomSequence;
    }

    /**
     * Calculates the fitness value of the RandomSequence
     * Does this by using two pointers (a curr and the next int line)
     * First Pointer points two a Node; Second pointer points to the value linked to the next
     * in Sequence
     * @param graph
     * @return int
     */
    private int calculateFit(Graph graph) {
        int i;
        int fitValue = 0;
        int currNodeVal;

        int currPointer;
        int nextPointer;

        for (i = 0; i < graph.size() - 1; ++i) {
            currPointer = i;
            nextPointer = i + 1;

            currNodeVal = randomSequence.get(currPointer, origGraph.indexOf(randomSequence.getNode(nextPointer)));
            fitValue = fitValue + currNodeVal;
        }
        currPointer = i;
        nextPointer = 0;

        currNodeVal = randomSequence.get(currPointer, origGraph.indexOf(randomSequence.getNode(nextPointer)));
        fitValue = fitValue + currNodeVal;

        return fitValue;
    }

    /**
     * crossOver method combines the sequence with an existing sequence
     * Accomplished by taking two Random Nodes and swapping their locations in the Graph
     * Potential chance of Mutation where a two random Nodes are swapped (10% chance)
     * @param sequence
     */
    public void crossOver (RandomSequence sequence) {
        Graph newGraph = new Graph(sequence.getSequence());
        final int MUTATION_CHANCE = 10;
        int index;
        int randomInt;

        for (int i = 0; i < randomSequence.size() / 2; ++i) {
            randomInt = rand.nextInt(newGraph.size());
            index = newGraph.indexOf(randomSequence.getNode(randomInt));

            newGraph.swap(randomInt , index);
        }

        randomInt = rand.nextInt(100) + 1;
        if (randomInt <= MUTATION_CHANCE) {
            int firstIndex = rand.nextInt(newGraph.size());
            int secondIndex = rand.nextInt(newGraph.size());

            newGraph.swap(firstIndex , secondIndex);
        }

        randomSequence = newGraph;
        this.fitValue = calculateFit(randomSequence);
    }

    /**
     * Method prints out the randomSequence as well as its fitness value
     */
    public void print () {
        int i;

        for (i = 0; i < randomSequence.size() - 1; ++i) {
            System.out.print(randomSequence.getName(i) +", ");
        }
        System.out.print(randomSequence.getName(i) +" ");
        System.out.print(" | Fit Value: " + getFitValue());
        System.out.println();
    }

    /**
     * Returns randomSequence
     * @return Graph
     */
    public Graph getSequence () {
        return randomSequence;
    }

    /**
     * Returns the fitness Value of the Sequence
     * @return
     */
    public int getFitValue() {
        return fitValue;
    }

    /**
     * Method ensures that the RandomSequence class is compared
     * based on their fitness level
     * @param o
     * @return
     */
    @Override
    public int compareTo(RandomSequence o) {
        return this.getFitValue() - o.getFitValue();
    }
}