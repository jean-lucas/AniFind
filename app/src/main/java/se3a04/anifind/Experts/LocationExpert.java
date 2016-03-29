package se3a04.anifind.Experts;

/**
 * Created by Zachary on 3/28/2016.
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
        return 0;
    }

    // Method used to call Google Maps to get get users location
    private String getCurrentLocation() {
        return null;
    }

}

