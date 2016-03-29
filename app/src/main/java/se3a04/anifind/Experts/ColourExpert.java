package se3a04.anifind.Experts;

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


        for (String attribute: animalAttributes) {
            for (String target: valuesToCompare) {
                if (attribute.equals(target)) {
                    matchCounter++;
                }
            }
        }

        /*
        // comparing
        for (int i = 0; i < animalAttributes.length; i++) {
            for (int j = 0; j < valuesToCompare.length; j++) {
                if (animalAttributes[i] == valuesToCompare[j]) {
                    matchCounter++;
                }
            }
        }
        */

        // percentage
        if (animalAttributes.length <= valuesToCompare.length) {
            percentage = 100 * (matchCounter / valuesToCompare.length);
        }
        else{
            percentage = 100 * (matchCounter/animalAttributes.length);
        }

        return assignPoints(percentage);


    }


    private int assignPoints(double percent){

        int points;

        if (percent >= 90){
            points =4;
        }
        else if (percent >= 65 && percent < 90){
            points = 3;
        }

        else if (percent >= 50 && percent< 65){
            points = 2;
        }
        else if (percent >= 30 && percent < 50){
            points = 1;
        }
        else{
            points = 0;
        }

        return points;
    }

}
