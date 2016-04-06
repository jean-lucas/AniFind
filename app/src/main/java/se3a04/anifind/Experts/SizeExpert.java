package se3a04.anifind.Experts;

import android.util.Log;
import android.widget.RadioButton;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Zachary on 3/28/2016.
 *
 * Assigns points based on range of size values as discussed.
 *
 * It first checks to see if the size of answers and size of animal being compared to are within
 * a single range.
 *
 * Then it checks the lower bound of answer and animal being compared to. Once it finds the
 * the lower bound it finds the upper bound range in the same way and assigns points.
 *
 */
public class SizeExpert extends Expert {


    private final String EXPERTISE = "Size";

    // Specified in meters

    // very small
    private final double VERYSMALLMIN = 0;
    private final double VERYSMALLMAX = 0.1;

    // small
    private final double SMALLMIN = 0.11;
    private final double SMALLMAX = 0.5;

    // medium
    private final double MEDIUMMIN = 0.51;
    private final double MEDIUMMAX = 2;

    // large
    private final double LARGEMIN = 2.1;
    private final double LARGEMAX = 5;

    // huge
    private final double HUGEMIN = 5.1;
    private final double HUGEMAX = 500;

    // points

    // full range
    private final int MAX_POINTS = 20;

    // 2 ranges
    private final int MEDIUM_POINTS = 7;

    // 3 ranges
    private final int SMALL_POINTS = 5;

    // 4 ranges
    private final int MIN_POINTS = 2;


    //counter to avoid memoryOverflow on recursive calls.
    private int recurisveCounter = 0;


    private Set<String> target_ranges;
    private Set<String> animal_ranges;


    @Override
    public String getExpertise() {
        return EXPERTISE;
    }

    @Override
    public int validateAttribute(String[] animalAttributes, String[] valuesToCompare) {


        double attributeMin = -1;
        double attributeMax = -1;

        double valueCompareMin = -1;
        double valueCompareMax = -1;

        target_ranges = new HashSet<String>();
        animal_ranges = new HashSet<String>();


        //value checks
        try {
            if (animalAttributes.length == 0 || valuesToCompare.length == 0 ) return 0;
            if (animalAttributes[0].equals("") || animalAttributes[0].equals(" ") ) return 0;

//            else if (animalAttributes[0].equalsIgnoreCase("") || animalAttributes[0].equalsIgnoreCase(" "))
//                return 0;
//
            else if (animalAttributes.length == 1) {
                attributeMin = Double.parseDouble(animalAttributes[0]);
                attributeMax = attributeMin;
            }

            else {
                attributeMin = Double.parseDouble(animalAttributes[0]);
                attributeMax = Double.parseDouble(animalAttributes[1]);
            }

            valueCompareMin = Double.parseDouble(valuesToCompare[0]);
            valueCompareMax = Double.parseDouble(valuesToCompare[1]);
        }

        //very bad to just catch all possible Exceptions... just for debugging purposes
        catch (Exception e) {

            Double[] newTargetValues = fixValues(valuesToCompare[0]);
            valueCompareMin = newTargetValues[0];
            valueCompareMax = newTargetValues[1];
        }


        if (valueCompareMin == -1 || attributeMin == -1) return 0;


        target_ranges.add(getRange(valueCompareMin));
        target_ranges.add(getRange(valueCompareMax));

        animal_ranges.add(getRange(attributeMin));
        animal_ranges.add(getRange(attributeMax));


//        Log.d("RANGE", "set is " + target_ranges.toString() + " for animal its " + animal_ranges.toString() +
//                " for animal values " + attributeMin + " , " + attributeMax  );
//


        //full points;
        if (target_ranges.equals(animal_ranges)) {
            Log.d("POINTS", "POint given " + MAX_POINTS + " for " + target_ranges.toString() + " , " + animal_ranges.toString());
            return MAX_POINTS;
        }


        //set used to check intersects
        Set<String> intersection = new HashSet<String>(target_ranges);
        if (!intersection.retainAll(animal_ranges) && (animal_ranges.size() - target_ranges.size() == 1)) {
            Log.d("POINTS", "POint given " + MEDIUM_POINTS + " for " + target_ranges.toString() + " , " + animal_ranges.toString());
            return MEDIUM_POINTS;
        }

        intersection = new HashSet<String>(target_ranges);
        if (!intersection.retainAll(animal_ranges) && (animal_ranges.size() - target_ranges.size() == 2)) {
            Log.d("POINTS", "POint given " + SMALL_POINTS + " for " + target_ranges.toString() + " , " + animal_ranges.toString());
            return SMALL_POINTS;
        }

        intersection = new HashSet<String>(target_ranges);
        if (!intersection.retainAll(animal_ranges) && (animal_ranges.size() - target_ranges.size() == 3)) {
            Log.d("POINTS", "POint given " + MIN_POINTS + " for " + target_ranges.toString() + " , " + animal_ranges.toString());

            return MIN_POINTS;
        }


        Log.d("POINTS", "POint given " + 0 + " for " + animal_ranges.toString() + " , " + target_ranges.toString());



        return 0;
    }



    private String getRange(Double size) {


        if (size <= VERYSMALLMAX) return "VERYSMALL";

        else if (size <= SMALLMAX) return "SMALL";

        else if (size <= MEDIUMMAX) return "MEDIUM";

        else if (size <= LARGEMAX) return "LARGE";

        else return "HUGE";
    }



    //when the gui does not convert values to their respective size
    //for example Medium to 0.5m-2m. etc..
    private Double[] fixValues(String compareValue) {

        Double[] values = {-1.0, -1.0};
        String selectedAnswer = "";

        switch (compareValue) {

            case "very small":
                values = new Double[] {0.0, 0.1};
                break;

            case "small":
                values = new Double[] {0.11, 0.5};
                break;

            case "medium":
                values = new Double[] {0.51, 2.0};
                break;

            case "large":
                values = new Double[] {2.1, 5.0};
                break;

            case "very large":
                values = new Double[] {5.1, 500.0};
                break;
        }

        return values;

    }




}