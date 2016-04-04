package se3a04.anifind.Experts;



/**
 * Created by Zachary on 3/28/2016.
 * Points
 * < 25 km 3 points
 * 26 - 50 km 2 points
 * 51 - 150 1 points
 *  > 150 0 points
 */
public class LocationExpert extends Expert {

    private final String EXPERTISE = "Location";


     /* This variable is will implement the Google Maps API
    and allow us to get the users current location.

    private locationAccessObj = GoogleMapsAPI */

    @Override
    public String getExpertise() {
        return EXPERTISE;
    }

    @Override
    public int validateAttribute(String[] animalAttributes, String[] valuesToCompare) {

        int matchCounter = 0;

        if (valuesToCompare.length == 1) {

            for (String attribute : animalAttributes) {
                for (String target : valuesToCompare) {

                    if (attribute.equalsIgnoreCase(target)) {
                        matchCounter += 30;
                    }
                }
            }
        }

        else{
            String[] animalAttributesNew = new String[1];
            animalAttributesNew[0] = animalAttributes[3];

            for (String attribute : animalAttributesNew) {
                for (String target : valuesToCompare) {

                    if (attribute.equalsIgnoreCase(target)) {
                        matchCounter += 30;
                    }
                }
            }
        }


        return matchCounter;
    }

}

