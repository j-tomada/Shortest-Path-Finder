/**
 * This class utilizes an ArrayList data structure
 * puts nodes into an arrayList
 * The Node class consists of a name and an Integer array; Integers are the distance from other nodes
 * @author Joseph Tomada
 * @redID 824031774
 * @date 12/09/2020
 */
import jdk.internal.util.xml.impl.Input;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class Graph {
    /**
     * This object essentially links a name with an
     * Arraylist<Integer>
     */
    private static class Node {
        private final String nodeName;
        private final ArrayList<Integer> travelNodes;

        /**
         * Parameter Constructor
         *
         * @param nodeName
         * @param travelNodes
         */
        private Node(String nodeName, List<Integer> travelNodes) {
            this.nodeName = nodeName;
            this.travelNodes = new ArrayList<>();
            this.travelNodes.addAll(travelNodes);
        }

        /**
         * Getter for travelNodes
         * get a Node at the specified index
         * @param index
         * @return Node
         */
        private int getTravelNode(int index) {
            return travelNodes.get(index);
        }

        /**
         * Getter for name
         * Returns the name of the Node
         * @return
         */
        private String getName() {return nodeName;}
    }

    private final ArrayList<Node> graph;

    /**
     * Default Constructor
     * Creates an empty graph
     */
    public Graph () {
        graph = new ArrayList<>();
    }

    /**
     * Creates a copy of another Graph
     * @param graph
     */
    public Graph (Graph graph) {
        this.graph = new ArrayList<>(graph.getGraph());
    }

    /**
     * Takes in a list of strings from an adjacency matrix
     * Inputs it into ArrayList<Node> graph
     * @param ArrayList<String> matrix
     */
    public Graph (List<String> matrix) throws InputMismatchException {
        graph = new ArrayList<>();
        Boolean correctFile = true;
        ArrayList<Integer> travelNodes;
        String currString;
        String[] tempArray; //Used for line.split function
        String nodeName;

        for (String s : matrix) {
            currString = s;
            tempArray = currString.split(",");
            nodeName = tempArray[0];

            travelNodes = new ArrayList<>();
            for (int j = 1; j < tempArray.length; ++j) {
                try {
                    tempArray[j] = tempArray[j].replaceAll(" ", "");
                    travelNodes.add(Integer.parseInt(tempArray[j]));

                }
                catch (NumberFormatException ignored) {

                }
            }

            if (travelNodes.size() != matrix.size()) {
                throw new InputMismatchException();
            }

            graph.add(new Node(nodeName, travelNodes));
        }
    }

    /**
     * Returns the index of the given Node
     * @param node
     * @return int
     */
    public int indexOf (Node node) {
        return graph.indexOf(node);
    }

    /**
     * Returns how many Nodes are in the ArrayList
     * @return int
     */
    public int size() {
        return graph.size();
    }

    /**
     * Returns the node at the given index
     * @param index
     * @return Node
     */
    public Node getNode (int index) {
        return graph.get(index);
    }

    /**
     * Adds an additional node to the graph
     * Also increases the size of the Graph
     * @param node
     * @return
     */
    public Node addNode (Node node) {
        graph.add(node);
        return node;
    }

    /**
     * Returns the integer from the graph
     * First int (x) points to a Node
     * Second int (y) points to a value within said Node
     * @param x
     * @param y
     * @return int
     */
    public int get(int x, int y) {
        Node currNode = graph.get(x);
        return currNode.getTravelNode(y);
    }

    /**
     * Returns the name of the node at a given index
     * @param index
     * @return
     */
    public String getName(int index) {
        return graph.get(index).getName();
    }

    /**
     * Removes a node at the given index
     * @param index
     * @return
     */
    public Node removeNode (int index) {
        Node returnNode = graph.get(index);
        graph.remove(index);
        return returnNode;
    }

    /**
     * Returns ArrayList<Node> graph
     * @return
     */
    private ArrayList<Node> getGraph () {
        return graph;
    }

    /**
     * Primarily used for the crossOver Function
     * Swaps two Node at the given indexes
     * @param indexOne
     * @param indexTwo
     */
    public void swap(int indexOne, int indexTwo) {
        Node tempNode = graph.get(indexOne);
        graph.set(indexOne , graph.get(indexTwo));
        graph.set(indexTwo , tempNode);
    }
}
