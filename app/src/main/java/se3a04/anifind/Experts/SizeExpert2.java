//package se3a04.anifind.Experts;
//
//import android.util.Log;
//
///**
// * Created by Zachary on 3/28/2016.
// *
// * Assigns points based on range of size values as discussed.
// *
// * It first checks to see if the size of answers and size of animal being compared to are within
// * a single range.
// *
// * Then it checks the lower bound of answer and animal being compared to. Once it finds the
// * the lower bound it finds the upper bound range in the same way and assigns points.
// *
// */
//public class SizeExpert2 extends Expert {
//
//    private final String EXPERTISE = "Size";
//
//    // Specified in meters
//
//    // very small
//    private final double VERYSMALLMIN = 0;
//    private final double VERYSMALLMAX = 0.25;
////    private final char RANGE_GROUP = 'A';
//
//    // small
//    private final double SMALLMIN = 0.26;
//    private final double SMALLMAX = 1;
////    private final char  RANGE_GROUP = 'B';
//
//    // medium
//    private final double MEDIUMMIN = 1.1;
//    private final double MEDIUMMAX = 3;
////    private final char RANGE_GROUP = 'C';
//
//    // large
//    private final double LARGEMIN = 3.1;
//    private final double LARGEMAX = 5;
////    private final char RANGE_GROUP = 'D';
//
//    // huge
//    private final double HUGEMIN = 5.1;
//    private final double HUGEMAX = 500;
////    private final char RANGE_GROUP = 'E';
//
//    //range group identifier
//
//    private String target_range_group;
//    private String animal_range_group;
//
//    // points
//    // full range
//    private final int FULLPOINTS = 40;
//
//    // 2 ranges
//    private final int PPPOINTS = 30;
//
//    // 3 ranges
//    private final int PPOINTS = 20;
//
//    // 4 ranges
//    private final int POINTS = 10;
//
//    // > 4 ranges = 0.
//
//
//    @Override
//    public String getExpertise() {
//        return EXPERTISE;
//    }
//
//
//    /**
//     *ASSUMPTIONS: in valuesToCompare the max is never above the HUGEMAX
//     * and the min/max value in values to compare are beside each other.
//     */
//    @Override
//    public int validateAttribute(String[] animalAttributes, String[] valuesToCompare) {
//
//        int points = 0;
//        double[] range = {0, 0.25, 0.26, 1, 1.1, 3, 3.1, 5, 5.1, 500};
//
//        double animal_min = Double.parseDouble(animalAttributes[0]);
//        double animal_max = Double.parseDouble(animalAttributes[1]);
//
//        double target_min = Double.parseDouble(valuesToCompare[0]);
//        double target_max = Double.parseDouble(valuesToCompare[1]);
//
//        //get the range group for target
//        if (target_max        )
//
//        //get the range groupe for animal
//
//        //base case where nothing in range
//        if (animal_max < target_min) { points = 0;}
//        if (animal_min > target_max) { points = 0;}
//
//        //case where animal is within the range - full points
//        if (animal_min >= target_min && animal_max <= target_max) {
//            points  = FULLPOINTS;
//        }
//
//        //animal range is partially within the target range
//        if ()
//
//
//        return points;
//    }
//
//}