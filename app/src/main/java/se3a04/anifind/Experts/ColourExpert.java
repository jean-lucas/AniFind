package se3a04.anifind.Experts;

import android.util.Log;

/**
    Created by Zachary on 3/28/2016.

    The assumption is a 100% match is an exact one to one match. For example attribute "red"
    matches with valuesToCompare "red" perfectly, therefore full points.

    If the number of valuesToCompare is larger than the number of attributes the percentage will
    be calculated matched/valuesToCompare. This percent will then be mapped to points.
    Eg. Attributes "red" matches 50% with valuesToCompare "red, blue".

    If the number of valuesToCompare is less than the number of attributes the percentage will
    be calculated matched/attributes. This percent will then be mapped to points.
    Eg. Attributes "red, blue" matches 50% with valuesToCompare "red".

    The rationale is if the user specifies more attributes than are available to be compared
    this is not a 100% match, it should therefore be treated as a percentage of amount of
    matched to attributes.

    The percentage to points break down:
    90 - 100% -> 4 points
    65 - 89% -> 3 points
    50 - 64% -> 2  points
    30 - 49% -> 1 point
       < 30% -> 0 points

 */

public class ColourExpert extends Expert {

    private final String EXPERTISE = "Colour";


    @Override
    public String getExpertise() {

        // <this> implies an attribute or method from this object
        return EXPERTISE;
    }


    @Override
    public int validateAttribute(String[] animalAttributes, String[] valuesToCompare) {

        double percentage;
        double matchCounter = 0;

        //skip over empty cases
        if (valuesToCompare.length == 0 || animalAttributes.length == 0 ) return 0;
        if (animalAttributes[0].equals("") || animalAttributes[0].equals(" ") ) return 0;


        for (String attribute : animalAttributes) {
            for (String target : valuesToCompare) {
                if (attribute.equalsIgnoreCase(target)) {
                    matchCounter++;
                }
            }
        }


        // percentage
        if (animalAttributes.length <= valuesToCompare.length) {
            percentage = 100 * (matchCounter / valuesToCompare.length);
        } else {
            percentage = 100 * (matchCounter / animalAttributes.length);
        }

        Log.d("MC", "" + percentage);
        return assignPoints(percentage);
    }



    private int assignPoints(double percent){

        int points;

        if (percent >= 90){
            points =4;
        }

        //89% to 51%
        else if (percent > 50 && percent < 90){
            points = 3;
        }

        //50% to 26%
        else if (percent > 25 && percent <= 50){
            points = 2;
        }

        //25% to 5%
        else if (percent >=5  && percent <= 25){
            points = 1;
        }
        else{
            points = 0;
        }

        return points;
    }

}
