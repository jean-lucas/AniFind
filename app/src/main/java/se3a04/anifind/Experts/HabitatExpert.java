package se3a04.anifind.Experts;


import java.util.ArrayList;

/**
 * Created by Zachary on 3/28/2016.
 */
public class HabitatExpert extends Expert {

    private final String EXPERTISE = "Habitat";
//    private WordBank wordBankAccess = new WordBank();

    @Override
    public String getExpertise() {
        return EXPERTISE;
    }

    @Override
    public int validateAttribute(String[] animalAttributes, String[] valuesToCompare) {

        int matchCounter = 0;

        for (String attribute : animalAttributes) {
            for (String target : valuesToCompare) {
                if (attribute.equalsIgnoreCase(target)) {
                    matchCounter += 2;
                }
//                else {
//                    for (String word : wordBankAccess.getWordBank()) {
//                        if (word.contains(attribute)) {
//                            matchCounter++;
//                        }
//
//
//                    }
//                }
            }
        }


        return matchCounter;


    }

//    private ArrayList<String> getKeywordsFromWordBank() {
//        return wordBankAccess.getWordBank();
//    }
}
