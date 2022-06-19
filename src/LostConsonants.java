//Imported libraries
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * The class where the program starts its execution and calls the relevant
 * methods to manipulate the sentence. This class will also deal with all the
 * exceptions that are thrown in the program as all exceptions will be thrown
 * upwards to this class once thrown.
 *
 * @author mrh5
 */


public class LostConsonants {

    /**
     *
     * Creates the object from the utility class, which will be used to do
     * all the manipulation of the sentence.
     *
     * The main method will also be used to check if the correct amount of
     * parameters have been passed to the program, through the args arrays.
     *
     * It is also in try catch block as any exceptions thrown will be thrown
     * up to the main method and dealt with here.
     *
     * @param args A string array, first element contains the file path
     *             of the dictionary to be used in the sentence manipulation.
     *             The second element contains the sentence to be manipulated.
     */

    public static void main(String[] args) {
        try {
            Utility utility = new Utility();
            boolean isArgsSizeTwo;

            isArgsSizeTwo = utility.checkArgsSizeIsTwo(args);

            if (isArgsSizeTwo) {
                utility.generateValidSentences(args[0], args[1]);
            } else {
                //Appropriate error message if arg array does not have 2 elements
                System.out.println("Expected 2 command line arguments, but got " + args.length + ".");
                System.out.println("Please provide the path to the dictionary file as the first argument and a sentence as the second argument.");
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
            System.out.println("Invalid dictionary, aborting.");
        } catch (IOException e) {
            System.out.println("Couldn't close reader: " + e.getMessage());
        }
    }
}
