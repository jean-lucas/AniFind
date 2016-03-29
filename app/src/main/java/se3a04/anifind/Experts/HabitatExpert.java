package se3a04.anifind.Experts;


import java.util.ArrayList;

/**
 * Created by Zachary on 3/28/2016.
 */
public class HabitatExpert extends Expert {

    private final String EXPERTISE = "Habitat";
    private WordBank wordBankAccess = new WordBank();

    @Override
    public String getExpertise() {
        return EXPERTISE;
    }

    @Override
    public int validateAttribute(String[] animalAttributes, String[] valuesToCompare) {
        return 0;
    }

    private ArrayList<String> getKeywordsFromWordBank() {
        return wordBankAccess.getWordBank();
    }
}
