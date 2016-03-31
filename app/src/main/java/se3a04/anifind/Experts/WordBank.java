package se3a04.anifind.Experts;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Zachary on 3/28/2016.
 */
public class WordBank {

    // assuming the key words are stored in the below list directory
    private static final String FILEPATHNAME = "assets/key_words";
    private ArrayList<String> wordBank = new ArrayList<String>();


    private void readFileContent() {
        File file = new File(FILEPATHNAME);
        try {
            Scanner read = new Scanner(file);
            while (read.hasNextLine()) {

                // Assuming words are separated by comma
                String [] wordLine = read.nextLine().split(",");

                // Add each of these words to arrayList
                for (int i = 0; i < wordLine.length; i++) {
                    wordBank.add(wordLine[i]);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    public ArrayList<String> getWordBank() {
        return wordBank;
    }


}
