/**
 * Main Driver Class
 * Used to test a Genetic Algorithm created by the author
 * Will ask to input an adjacency matrix file and figure out the most optimal route
 * User can also configure the initial population and how many times the Algorithm loops
 * @author Joseph Tomada
 * @date 12/09/2020
 * @redID 824031774
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Driver {
    private static final Random rand = new Random();

    /**
     * Private method helper
     * Creates an ArrayList of nodes to be used as a parameter in the
     * Graph class
     * Will also display the first three nodes in the adjacency Matrix
     * @param textFile
     * @return ArrayList<String>
     * @throws FileNotFoundException
     */
    private static ArrayList<String> createNodeList(File textFile) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(textFile);
        ArrayList<String> newList = new ArrayList<>();
        String currString;
        final int FIRST_THREE = 3;
        int i = 0;
        int nodeCount = 0;

        System.out.println("Here are the first three nodes: ");
        while(fileScanner.hasNextLine()) {
            currString = fileScanner.nextLine();

            if (i < FIRST_THREE) {
                System.out.println(currString);
                ++i;
            }

            newList.add(currString);
            ++nodeCount;
        }
        System.out.println("There are " +nodeCount +" nodes in this matrix");

        fileScanner.close();
        return newList;
    }

    /**
     * Private method helper used by crossOverHelper()
     * Takes the top and bottom 20% of an existing array
     * Inputs those Sequences to a new Array
     * @param randomSequences
     * @return ArrayList<RandomSequence>
     */
    private static ArrayList<RandomSequence> DNAHelper(ArrayList<RandomSequence> randomSequences) {
        ArrayList<RandomSequence> genePool = new ArrayList<>();
        final int PREV_DNA = (int) ( (int) randomSequences.size() * 0.2);

        /**
         * First few
         */
        for (int i = 0; i < PREV_DNA; ++i) {
            genePool.add(randomSequences.get(i));
        }

        /**
         * Last few
         */
        for (int i = randomSequences.size() - 1; i >= randomSequences.size() - PREV_DNA; --i) {
            genePool.add(randomSequences.get(i));
        }

        return genePool;
    }

    /**
     * Private method Helper
     * Takes in a array of RandomSequences and performs crossOver method between
     * the most fit sequences
     * Has a chance of breeding with "less-fit" sequences (20% chance)
     * @param randomSequences
     * @param numEpochs
     * @return
     */
    private static ArrayList<RandomSequence> crossoverHelper (ArrayList<RandomSequence> randomSequences, int numEpochs) {
        ArrayList<RandomSequence> currSequences = new ArrayList<>(randomSequences);
        ArrayList<RandomSequence> newList;
        int currEpoch;

        for (currEpoch = 1; currEpoch <= numEpochs; ++currEpoch) {
            Collections.sort(currSequences);
            newList = DNAHelper(currSequences);

            System.out.println("Current population (Epoch " +currEpoch +"): ");
            for (RandomSequence currSequence : currSequences) {
                currSequence.print();
            }
            System.out.println();

            for (int i = newList.size(); i < currSequences.size(); ++i) {
                int firstInt;
                int secondInt;

                int chance = rand.nextInt(100) + 1;
                if (chance <= 10) { //10% chance they mate with someone "non-fit"
                    secondInt = newList.size() - 1;
                }
                else {
                    secondInt = rand.nextInt(currSequences.size() / 2);
                }
                firstInt = rand.nextInt(currSequences.size() / 2);

                RandomSequence firstSequence = new RandomSequence(currSequences.get(firstInt));
                RandomSequence secondSequence = new RandomSequence(currSequences.get(secondInt));

                firstSequence.crossOver(secondSequence);

                newList.add(firstSequence);
            }
            currSequences = new ArrayList<>(newList);
        }
        return currSequences;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Use: java Driver file.txt");
        }
        else {
            File textFile = new File(args[0]);
            Scanner scnr = new Scanner(System.in);
            int userInput;

            try { //Try/Catch used to catch a FileNotFound Exception

                if (!textFile.exists()) {
                    throw new FileNotFoundException();
                }

                ArrayList<String> fileList = createNodeList(textFile);
                ArrayList<RandomSequence> randomSeqList = new ArrayList<>();
                Graph cityGraph = new Graph(fileList); //Can throw InputMistMatchException (Error with file)

                System.out.println("How many initial sequences would you like to create (Recommend 100): ");
                userInput = scnr.nextInt();

                int arraySize;
                if (userInput < 0) {
                    System.out.println("Setting population to default value");
                    arraySize = 10;
                }
                else {
                    System.out.println("Setting population to " + userInput);
                    arraySize = userInput;
                }

                System.out.println("Creating the initial population");
                for(int i = 0; i < arraySize; ++i) {
                    randomSeqList.add(new RandomSequence(cityGraph));
                }
                System.out.println();

                System.out.print("Initial Population created, how many epochs would you like to visit: ");
                userInput = scnr.nextInt();

                randomSeqList = crossoverHelper(randomSeqList, userInput);

                System.out.println("\nAn optimal route is: ");
                randomSeqList.get(0).print();




            } catch (FileNotFoundException e) {
               System.out.println("Error: file has not been found (Maybe spelling error)");
            }
            catch (InputMismatchException e) {
                System.out.println("Error: The given adjacency matrix has the incorrect format");
            }
        }
    }

}
