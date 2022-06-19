//Imported libraries
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * The utility class is where all the manipulation of the sentence will be
 * carried out, through a utility object.
 *
 * @author mrh5
 */

public class Utility {

    /**
     * Method which checks if args array has two elements.
     *
     * @param args An array that holds the file path of the dictionary and
     *             sentence to be manipulated
     * @return True if the args array contains two elements, otherwise false
     */
    public boolean checkArgsSizeIsTwo(String[] args) {
        return (args.length == 2);
    }

    /**
     * Obtains and prints the valid sentences with omitted consonants and
     * all the words with a omitted consonant are contained in the dictionary.
     *
     * Also prints the valid sentences with omitted vowels and all the words
     * contained in the dictionary
     *
     * Also prints the valid sentences with a added consonant and the word with
     * the added consonant is contained within the dictionary
     *
     * Also prints the valid sentences with a added vowel and the word with
     * the added vowel is contained within the dictionary
     *
     * IOException to be thrown to method which called this method, if IOException
     * is thrown
     *
     * @param dictionaryFilePath File path of the dictionary where words to be
     *                           checked by.
     * @param sentence Sentence to be manipulated
     * @throws IOException If stream to file cannot be read from or closed
     */
    public void generateValidSentences(String dictionaryFilePath, String sentence) throws IOException {
        //ArrayLists, each holding a different type of group of sentences
        ArrayList<String> validSentences = findValidCandidates(dictionaryFilePath, sentence);
        ArrayList<String> validSentencesWithoutVowels = findValidCandidatesWithoutVowels(dictionaryFilePath, sentence);
        ArrayList<String> validSentencesWithAddedConsonant = findValidCandidatesWithAddedConsonant(dictionaryFilePath, sentence);
        ArrayList<String> validSentencesWithAddedVowel = findValidCandidatesWithAddedVowel(dictionaryFilePath, sentence);
        //Methods to print each ArrayList with a different heading
        printValidSentences(validSentences);
        printValidSentencesWithoutVowels(validSentencesWithoutVowels);
        printValidSentencesWithAddedConsonant(validSentencesWithAddedConsonant);
        printValidSentencesWithAddedVowel(validSentencesWithAddedVowel);
    }

    /**
     * Method which prints all the valid sentences, if no valid sentences to be
     * printed (Size of ArrayList is zero), appropriate message is outputted.
     *
     * @param validSentences ArrayList with all the sentences that are valid
     *                       and to be printed
     */
    private void printValidSentences(ArrayList<String> validSentences) {
        int numberOfAlternatives = validSentences.size();
        //If size of arrayList is zero (number of alternative sentences is zero),
        //appropriate message is outputted
        if (numberOfAlternatives == 0) {
            System.out.println("Could not find any alternatives.");
        } else {
            //If there are alternatives to print, then each element ArrayList
            //is printed through a for each loop
            for (String sentence : validSentences) {
                System.out.println(sentence);
            }
            //Number of alternatives is also printed
            System.out.println("Found " + numberOfAlternatives + " alternatives.");
        }
    }

    /**
     *
     * Method which produces all the valid sentences with a word with a missing
     * consonant, and that word is contained in the dictionary.
     *
     * IOException to be thrown to method which called this method, if thrown
     * at all
     *
     * @param dictionaryFilePath File path of the dictionary to be used
     *                           when checking the words
     * @param sentence Sentence to be manipulated
     * @return An ArrayList where each element is a sentence with a word with a
     * missing consonant and that word is contained in the dictionary
     * @throws IOException If stream to file cannot be read from or closed
     */
    private ArrayList<String> findValidCandidates(String dictionaryFilePath, String sentence) throws IOException {
        ArrayList<String> validCandidates = new ArrayList<>();
        String newSentence;
        Character characterToRemove;
        //Keeps track of which word to check
        int spaceCounter = 0;
        boolean isCharacterAConsonant;
        boolean isCharacterASpace;
        //StringBuilder class contains deleteCharAt method
        StringBuilder sentenceToBeManipulated;

        //Goes through each character in the sentence, using i as the index
        for (int i = 0; i < sentence.length(); i++) {
            //Character to be removed obtained, so the character
            //can be checked
            characterToRemove = sentence.charAt(i);
            //Sentence is converted to a StringBuilder so the deleteCharAt
            //method can be used
            sentenceToBeManipulated = new StringBuilder(sentence);
            isCharacterAConsonant = checkCharacterIsConsonant(characterToRemove);
            isCharacterASpace = checkCharacterIsSpace(characterToRemove);

            //If character is a consonant, carry out relevant manipulation on
            //sentence and check to see if new sentence created is a valid one
            if (isCharacterAConsonant) {
                //Character is removed from the sentence (in the form of StringBuilder)
                //Then converted to a String
                sentenceToBeManipulated.deleteCharAt(i);
                newSentence = sentenceToBeManipulated.toString();
                //Sentence is checked to be valid, by checking the word in
                //which the consonant has been removed (word is referenced by the spaceCounter)
                if (checkSentence(newSentence, dictionaryFilePath, spaceCounter)) {
                    //If sentence is valid, it is added to the ArrayList which
                    //holds all the valid sentences
                    validCandidates.add(newSentence);
                }
            }

            //If character found is a space, increment spaceCounter by one
            //This is so the next word in the sentence is considered and checked
            //in accordance with the dictionary
            //Example 1: If the spaceCounter is 0, the first word is to be checked
            //Example 2: If the spaceCounter is 3, the fourth word is to be checked
            //as three spaces have preceding during the iteration through the sentence
            if (isCharacterASpace) {
                spaceCounter += 1;
            }
        }

        return validCandidates;
    }

    /**
     *
     * Method which produces all the valid sentences with a word with a missing
     * vowel, and that word is contained in the dictionary.
     *
     * IOException to be thrown to method which called this method, if thrown at all
     *
     * @param dictionaryFilePath File path of the dictionary to be used
     *                           when checking the words
     * @param sentence Sentence to be manipulated
     * @return An ArrayList where each element is a sentence with a word with a
     * missing vowel and that word is contained in the dictionary
     * @throws IOException If stream to file cannot be read from or closed
     */
    private ArrayList<String> findValidCandidatesWithoutVowels(String dictionaryFilePath, String sentence) throws IOException {
        //ArrayList holds all the sentences with a word with a missing
        //vowel, and that word is contained in the dictionary
        ArrayList<String> validCandidatesWithoutVowels = new ArrayList<>();
        //Holds new sentence created, when a vowel is removed
        String newSentence;
        Character characterToRemove;
        //Keeps track of which word to check
        int spaceCounter = 0;
        boolean isCharacterAVowel;
        boolean isCharacterASpace;
        //Contains deleteCharAt method
        StringBuilder sentenceToBeManipulated;

        //Goes through each character in the sentence, using i as the index
        for (int i = 0; i < sentence.length(); i++) {
            //Character to be removed obtained, so the character
            //can be checked
            characterToRemove = sentence.charAt(i);
            //Sentence is converted to a StringBuilder so the deleteCharAt
            //method can be used
            sentenceToBeManipulated = new StringBuilder(sentence);
            isCharacterAVowel = checkCharacterIsVowel(characterToRemove);
            isCharacterASpace = checkCharacterIsSpace(characterToRemove);

            //If character is a vowel, carry out relevant manipulation on
            //sentence and check to see if new sentence created is a valid one
            if (isCharacterAVowel) {
                //Character is removed from the sentence (in the form of StringBuilder)
                //Then converted to a String
            	sentenceToBeManipulated.deleteCharAt(i);
            	newSentence = sentenceToBeManipulated.toString();
                //Sentence is checked to be valid, by checking the word in
                //which the vowel has been removed (word is referenced by the spaceCounter)
            	if (checkSentence(newSentence, dictionaryFilePath, spaceCounter)) {
                    //If sentence is valid, it is added to the ArrayList which
                    //holds all the valid sentences
                    validCandidatesWithoutVowels.add(newSentence);
                }
            }

            //If character found is a space, increment spaceCounter by one
            //This is so the next word in the sentence is considered and checked
            //in accordance with the dictionary
            //Example 1: If the spaceCounter is 0, the first word is to be checked
            //Example 2: If the spaceCounter is 3, the fourth word is to be checked
            //as three spaces have preceding during the iteration through the sentence
            if (isCharacterASpace) {
                spaceCounter += 1;
            }
        }
        return validCandidatesWithoutVowels;
    }

    /**
     *
     * Method which produces all the valid sentences with a word having a extra
     * vowel, and that word is contained in the dictionary.
     *
     * IOException to be thrown to method which called this method, if thrown at all
     *
     * @param dictionaryFilePath File path of the dictionary to be used when
     *                           checking the words
     * @param sentence Sentence to be manipulated
     * @return An ArrayList where each element is a sentence with a word with a
     * added vowel and that word is contained in the dictionary
     * @throws IOException If stream to file cannot be read from or closed
     */
    private ArrayList<String> findValidCandidatesWithAddedVowel(String dictionaryFilePath, String sentence) throws IOException {
        //ArrayList holds all the sentences with a word with a added
        //vowel, and that word is contained in the dictionary
        ArrayList<String> validCandidatesWithAddedVowel = new ArrayList<>();
        ArrayList<Character> vowels = new ArrayList<>(Arrays.asList('a', 'e', 'i', 'o', 'u'));
        //Holds new sentence created, when a vowel is added
        String newSentence;
        //characterToCheck comes from original sentence
        Character characterToCheck;
        //characterToInsert comes from ArrayList of vowels
        Character characterToInsert;
        //Keeps track of which word to check
        int spaceCounter = 0;
        //StringBuilder class contains insert method
        StringBuilder sentenceToBeManipulated;
        //Booleans to represent the type of character found
        boolean isCharacterAConsonant;
        boolean isCharacterAVowel;
        boolean isCharacterASpace;

        //Goes through each character in the sentence, using i as the index
        for (int i = 0; i < sentence.length(); i++) {
            //Checks the character succeeding where the character will added
            characterToCheck = sentence.charAt(i);
            isCharacterAConsonant = checkCharacterIsConsonant(characterToCheck);
            isCharacterAVowel = checkCharacterIsVowel(characterToCheck);
            isCharacterASpace = checkCharacterIsSpace(characterToCheck);
            //Goes through each vowel in the ArrayList of vowels
            for (int j = 0; j < vowels.size(); j++) {
                characterToInsert = vowels.get(j);
                sentenceToBeManipulated = new StringBuilder(sentence);
                //Inserting the character in the relevant slot
                sentenceToBeManipulated.insert(i, characterToInsert);
                newSentence = sentenceToBeManipulated.toString();
                //Sentence is checked to be valid, by checking the word in
                //which the vowel has been added to (word is referenced by the spaceCounter)
                if (checkSentence(newSentence, dictionaryFilePath, spaceCounter)) {
                    //If sentence is valid, it is added to the ArrayList which
                    //holds all the valid sentences
                    validCandidatesWithAddedVowel.add(newSentence);
                }
            }

            //If the character is not a consonant, vowel or space, it is a comma
            //and cannot have a letter added after it, so instead for the next loop
            //i is incremented twice
            if (!(isCharacterAConsonant || isCharacterAVowel || isCharacterASpace)) {
                i++;
            }

            //If character found is a space, increment spaceCounter by one
            //This is so the next word in the sentence is considered and checked
            //in accordance with the dictionary
            //Example 1: If the spaceCounter is 0, the first word is to be checked
            //Example 2: If the spaceCounter is 3, the fourth word is to be checked
            //as three spaces have preceding during the iteration through the sentence
            if (isCharacterASpace) {
                spaceCounter += 1;
            }
        }
        return validCandidatesWithAddedVowel;
    }

    /**
     *
     * Method which produces all the valid sentences with a word having a extra
     * consonant, and that word is contained in the dictionary.
     *
     * IOException to be thrown to method which called this method, if thrown at all
     *
     * @param dictionaryFilePath File path of the dictionary to be used when
     *                           checking words
     * @param sentence Sentence to be manipulated
     * @return An ArrayList where each element is a sentence with a word with a
     * added vowel and that word is contained in the dictionary
     * @throws IOException If stream to file cannot be read from or closed
     */
    private ArrayList<String> findValidCandidatesWithAddedConsonant(String dictionaryFilePath, String sentence) throws IOException {
        //ArrayList holds all the sentences with a word with a added
        //consonant, and that word is contained in the dictionary
        ArrayList<String> validCandidatesWithAddedConsonant = new ArrayList<>();
        ArrayList<Character> consonant = new ArrayList<>(Arrays.asList('b',
                'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r',
                's', 't', 'v', 'w', 'x', 'y', 'z'));
        //Holds new sentence created, when a consonant is added
        String newSentence;
        //characterToCheck comes from original sentence
        Character characterToCheck;
        //characterToInsert comes from ArrayList of consonants
        Character characterToInsert;
        //Keeps track of which word to check
        int spaceCounter = 0;
        //StringBuilder class contains insert method
        StringBuilder sentenceToBeManipulated;
        //Booleans to represent the type of character found
        boolean isCharacterASpace;
        boolean isCharacterAConsonant;
        boolean isCharacterAVowel;

        //Goes through each character in the sentence, using i as the index
        for (int i = 0; i < sentence.length(); i++) {
            //Checks the character succeeding where the character will added
            characterToCheck = sentence.charAt(i);
            isCharacterAConsonant = checkCharacterIsConsonant(characterToCheck);
            isCharacterAVowel = checkCharacterIsVowel(characterToCheck);
            isCharacterASpace = checkCharacterIsSpace(characterToCheck);

            //Goes through each consonant in the ArrayList of consonants
            for (int j = 0; j < consonant.size(); j++) {
                characterToInsert = consonant.get(j);
                sentenceToBeManipulated = new StringBuilder(sentence);
                //Inserting the character in the relevant slot
                sentenceToBeManipulated.insert(i, characterToInsert);
                newSentence = sentenceToBeManipulated.toString();
                //Sentence is checked to be valid, by checking the word in
                //which the consonant has been added to (word is referenced by the spaceCounter)
                if (checkSentence(newSentence, dictionaryFilePath, spaceCounter)) {
                    //If sentence is valid, it is added to the ArrayList which
                    //holds all the valid sentences
                    validCandidatesWithAddedConsonant.add(newSentence);
                }
            }

            //If the character is not a consonant, vowel or space, it is a comma
            //and cannot have a letter added after it, so instead for the next loop
            //i is incremented twice
            if (!(isCharacterAConsonant || isCharacterAVowel || isCharacterASpace)) {
                i++;
            }

            //If character found is a space, increment spaceCounter by one
            //This is so the next word in the sentence is considered and checked
            //in accordance with the dictionary
            //Example 1: If the spaceCounter is 0, the first word is to be checked
            //Example 2: If the spaceCounter is 3, the fourth word is to be checked
            //as three spaces have preceding during the iteration through the sentence
            if (isCharacterASpace) {
                spaceCounter += 1;
            }
        }
        return validCandidatesWithAddedConsonant;
    }

    /**
     * This checks the sentence passed as a parameter is a valid sentence, it
     * does this by keeping track of word where a character has been removed
     * and checking the word with a missing character is present in the
     * dictionary.
     *
     * IOException to be thrown to method which called this method, if IOException is thrown
     *
     * @param sentence The sentence to be checked is valid
     * @param dictionaryFilePath The filepath where the dictionary with the valid words is held
     * @param spaceCounter Keeps track of the spaces which have been iterated through,
     *                     tells which word to check, for example if spaceCounter
     *                     is 3, the fourth word will be checked. If spaceCounter
     *                     is 0, the first word will be checked.
     *
     * @return true if the sentence is valid (word that has a character removed is in the dictionary), false otherwise
     * @throws IOException If stream to file cannot be read from or closed
     */
    private boolean checkSentence(String sentence, String dictionaryFilePath, int spaceCounter) throws IOException {
        String[] wordsInSentence;
        String wordToCheck;
        //Removes all the punctuation from the sentence
        sentence = sentence.replaceAll("[,.]", "");
        //Each word in the sentence is put into a string array, each word is
        //separated by a space. A space and the split method is used to generate
        // an array with all the words from the sentence
        wordsInSentence = sentence.split("\\s+");
        //SpaceCounter is used to retrieve the correct element from the array
        wordToCheck = wordsInSentence[spaceCounter];

        //reader object is created to read from the dictionary
        BufferedReader reader = new BufferedReader(new FileReader(dictionaryFilePath));
        //lineToCheck String will be used to store a word from the dictionary
        //when a line is read
        String lineToCheck;

        //Goes through each line in the dictionary, if the line is null,
        // the end of the file has been reached
        while ((lineToCheck = reader.readLine()) != null) {
            //Both the line read from the dictionary and word is converted to
            //lower-case so no word is missed out, even if there is a capital
            //letter in either the word being checked or the word from the
            //dictionary
            lineToCheck = lineToCheck.toLowerCase();
            wordToCheck = wordToCheck.toLowerCase();
            //If the word is the same as a word from the dictionary, the file
            //stream is closed and true is returned and the method is terminated
            //as well as the while loop
            if (lineToCheck.equals(wordToCheck.toLowerCase())) {
                reader.close();
                return true;
            }
        }
        //If word cannot be found in the dictionary, the file stream is closed
        //and false is returned
        reader.close();
        return false;
    }

    /**
     * Checks to see if the character passed as a parameter is a consonant.
     * @param characterToCheck character to be checked
     * @return true if the character is a consonant, false otherwise
     */
    private boolean checkCharacterIsConsonant(Character characterToCheck) {
        //Converts character to lowercase, doesn't matter if it is a capital or
        // lower-case
        characterToCheck = Character.toLowerCase(characterToCheck);
        ArrayList<Character> consonants = new ArrayList<>(Arrays.asList('b',
                'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r',
                's', 't', 'v', 'w', 'x', 'y', 'z'));

        //True if the character is within the array list, false otherwise
        return consonants.contains(characterToCheck);

    }

    /**
     *
     * Checks if the character passed as a parameter is a vowel.
     *
     * @param characterToCheck character to be checked
     * @return true if the character is a vowel, false otherwise
     */
    private boolean checkCharacterIsVowel(Character characterToCheck) {
        //Converts character to lowercase, doesn't matter if it is a capital or
        // lower-case, however considered different character to compiler
        characterToCheck = Character.toLowerCase(characterToCheck);
        ArrayList<Character> vowels = new ArrayList<>(Arrays.asList('a', 'e', 'i', 'o', 'u'));

        //True if the character is within the array list, false otherwise
        return vowels.contains(characterToCheck);
    }

    /**
     * Checks if the character passed as a parameter is a space.
     *
     * @param characterToCheck character to be checked
     * @return true if the character is a space, false otherwise
     */
    private boolean checkCharacterIsSpace(Character characterToCheck) {
        return characterToCheck.equals(' ');
    }


    //Methods to print different versions of groups of sentences, with different headings
    //Depending on the type of group of sentences
    private void printValidSentencesWithoutVowels(ArrayList<String> validSentencesWithoutVowels) {
        System.out.println("Lost vowels version:");
        printValidSentences(validSentencesWithoutVowels);
    }

    private void printValidSentencesWithAddedConsonant(ArrayList<String> validSentencesWithAddedConsonant) {
        System.out.println("Added consonant version:");
        printValidSentences(validSentencesWithAddedConsonant);
    }

    private void printValidSentencesWithAddedVowel(ArrayList<String> validSentencesWithAddedVowel) {
        System.out.println("Added vowel version:");
        printValidSentences(validSentencesWithAddedVowel);
    }
}
