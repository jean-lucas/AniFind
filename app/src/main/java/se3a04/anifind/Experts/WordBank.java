package se3a04.anifind.Experts;

import java.util.ArrayList;

/**
 * Created by Zachary on 3/28/2016.
 */
public class WordBank {

    private final String FILEPATHNAME = "Path";
    private ArrayList<String> fileContents;


    // Testing stub to get things working because IO is not working
    private String [] test = {"dense grassland", "dense woodland", "flat grassland", "prairie grassland", "desert", "forest",
    "dense forest"};


    private void readFileContent() {
        // read file in
        // split on the commas
    }

    // Returns the word bank in an ArrayList
    private void parseByKeywords(String[] keywords) {

    }

    public ArrayList<String> getWordBank() {

        // for the purpose of testing until we get file IO working
        for (int i = 0; i < test.length; i++){
            fileContents.add(test[i]);
        }

        return fileContents;
    }


}
