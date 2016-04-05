package se3a04.anifind.Experts;

import android.util.Log;

public class LocationExpert extends Expert {

    private final String EXPERTISE = "Location";



    @Override
    public String getExpertise() {
        return EXPERTISE;
    }

    @Override
    public int validateAttribute(String[] animalAttributes, String[] valuesToCompare) {

        int matchCounter = 0;

        if (animalAttributes.length == 0 || valuesToCompare.length == 0) return 0;

        //chekc if we want to do currentLocation or not
        valuesToCompare = checkValueToCompare(valuesToCompare);

        for (String attribute : animalAttributes) {
            for (String target : valuesToCompare) {
                if (attribute.equalsIgnoreCase(target)) {
                    matchCounter += 5;
                }
            }
        }

        return matchCounter;
    }


    //if the first value is "current" it means user wants to use current location
    private String[] checkValueToCompare(String[] vals) {

        if (vals[0].equalsIgnoreCase("current")) {
            return new String[]{vals[3]};
        }

        return vals;
    }


}

