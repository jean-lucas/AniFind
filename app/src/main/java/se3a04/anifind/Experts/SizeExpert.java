package se3a04.anifind.Experts;



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
    private static final double VERYSMALLMIN = 0;
    private static final double VERYSMALLMAX = 0.25;

    // small
    private static final double SMALLMIN = 0.26;
    private static  final double SMALLMAX = 1;

    // medium
    private static final double MEDIUMMIN = 1;
    private static final double MEDIUMMAX = 3.1;

    // large
    private static final double LARGEMIN = 3;
    private static final double LARGEMAX = 5;

    // huge
    private static final double HUGEMIN = 5.1;
    private static final double HUGEMAX = 500;

    // points

    // full range
    private static final int FULLPOINTS = 40;

    // 2 ranges
    private static final int PPPOINTS = 30;

    // 3 ranges
    private static final int PPOINTS = 20;

    // 4 ranges
    private static final int POINTS = 10;

    // > 4 ranges = 0.


    @Override
    public String getExpertise() {
        return EXPERTISE;
    }

    @Override
    public int validateAttribute(String[] animalAttributes, String[] valuesToCompare) {

        int points = 0;

        // take in animal attribute comes in as [min max]  [min max]
        // values to compare comes in is [min max] [min max]
        // with in the range full points eg. 30
        // somewhat in in range 15 points
        //

        for (int i = 0; i < animalAttributes.length; i += 2) {
            for (int j = 0; j < valuesToCompare.length; i += 2) {

                double attributeMin = Double.parseDouble(animalAttributes[i]);
                double attributeMax = Double.parseDouble(animalAttributes[i + 1]);

                double valueCompareMin = Double.parseDouble(valuesToCompare[j]);
                double valueCompareMax = Double.parseDouble(valuesToCompare[j + 1]);


                // full point scenario
                // when attribute and value to compare  are both within in single range
                // very small
                if (attributeMin > VERYSMALLMIN && attributeMin > VERYSMALLMIN && attributeMax < VERYSMALLMAX && valueCompareMax < VERYSMALLMAX) {
                    points += FULLPOINTS;
                }

                // small
                else if (attributeMin > SMALLMIN && attributeMin > SMALLMIN && attributeMax < SMALLMAX && valueCompareMax < SMALLMAX) {
                    points += FULLPOINTS;
                }

                // medium
                else if (attributeMin > MEDIUMMIN && attributeMin > MEDIUMMIN && attributeMax < MEDIUMMAX && valueCompareMax < MEDIUMMAX) {
                    points += FULLPOINTS;
                }

                // large
                else if (attributeMin > LARGEMIN && attributeMin > LARGEMIN && attributeMax < LARGEMAX && valueCompareMax < LARGEMAX) {
                    points += FULLPOINTS;
                }

                // huge
                else if (attributeMin > HUGEMIN && attributeMin > HUGEMIN && attributeMax < HUGEMAX && valueCompareMax < HUGEMAX) {
                    points += FULLPOINTS;
                }


                // half point scenario
                // partial range


                // more range it covers the less points it gets

                // first check lower bound to see range that it falls in
                // then check upper bound to see where it falls in
                // assign points

                // range from very small --> huge
                else if (attributeMin > VERYSMALLMIN && attributeMin > VERYSMALLMIN && attributeMin < VERYSMALLMAX && valueCompareMin < VERYSMALLMAX) {

                    // small
                    if (attributeMin > SMALLMIN && attributeMin > SMALLMIN && attributeMin < SMALLMAX && valueCompareMin < SMALLMAX) {
                        points += PPPOINTS;
                    }

                    // medium
                    else if (attributeMin > MEDIUMMIN && attributeMin > MEDIUMMIN && attributeMin < MEDIUMMAX && valueCompareMin < MEDIUMMAX) {
                        points += PPOINTS;
                    }

                    // large
                    else if (attributeMin > LARGEMIN && attributeMin > LARGEMIN && attributeMin < LARGEMAX && valueCompareMin < LARGEMAX) {
                        points += POINTS;
                    }

                    //huge
                    // This one is kind of useless but put her for completeness right now
                    else if (attributeMin > HUGEMIN && attributeMin > HUGEMIN && attributeMin < HUGEMAX && valueCompareMin < HUGEMAX) {
                        points += 0;
                    }

                }

                // small --> huge

                else if (attributeMin > SMALLMIN && attributeMin > SMALLMIN && attributeMin < SMALLMAX && valueCompareMin < SMALLMAX) {

                    // medium
                    if (attributeMin > MEDIUMMIN && attributeMin > MEDIUMMIN && attributeMin < MEDIUMMAX && valueCompareMin < MEDIUMMAX) {
                        points += PPPOINTS;
                    }

                    // large
                    else if (attributeMin > LARGEMIN && attributeMin > LARGEMIN && attributeMin < LARGEMAX && valueCompareMin < LARGEMAX) {
                        points += PPOINTS;
                    }

                    //huge
                    // This one is kind of useless but put her for completeness right now
                    else if (attributeMin > HUGEMIN && attributeMin > HUGEMIN && attributeMin < HUGEMAX && valueCompareMin < HUGEMAX) {
                        points += POINTS;
                    }

                }


                // medium --> huge

                else if (attributeMin > MEDIUMMIN && attributeMin > MEDIUMMIN && attributeMin < LARGEMAX && valueCompareMin < LARGEMAX) {


                    // large
                    if (attributeMin > LARGEMIN && attributeMin > LARGEMIN && attributeMin < LARGEMAX && valueCompareMin < LARGEMAX) {
                        points += PPPOINTS;
                    }

                    //huge
                    // This one is kind of useless but put her for completeness right now
                    else if (attributeMin > HUGEMIN && attributeMin > HUGEMIN && attributeMin < HUGEMAX && valueCompareMin < HUGEMAX) {
                        points += PPOINTS;
                    }

                }


                // large --> Huge

                else if (attributeMin > LARGEMIN && attributeMin > LARGEMIN && attributeMin < HUGEMAX && valueCompareMin < HUGEMAX) {


                    //huge
                    // This one is kind of useless but put her for completeness right now
                    if (attributeMin > HUGEMIN && attributeMin > HUGEMIN && attributeMin < HUGEMAX && valueCompareMin < HUGEMAX) {
                        points += PPPOINTS;
                    }

                }

            }
        }

        return points;
    }


}
