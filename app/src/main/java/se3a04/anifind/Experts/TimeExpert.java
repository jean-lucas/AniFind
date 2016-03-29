package se3a04.anifind.Experts;

/**
 * Created by Zachary on 3/28/2016.
 * 
 * Time of day revisited.
 * To simplify time of day logic we are going to assume 4 "states" to a day. The user is going to
 * be asked what states that they saw the animal in. The user should be able to only choose 1.
 *
 * The 4 day options that should be available to the user
 *
 * Morning 5:00 AM - 9:00 AM
 * Day 9:01 AM - 6:00 PM
 * Evening 6:01 PM - 10:00 PM
 * Night 10:01 PM - 4:59 AM
 */
public class TimeExpert extends Expert {

    private final String EXPERTISE = "Time";

    @Override
    public String getExpertise() {
        return EXPERTISE;
    }

    @Override
    public int validateAttribute(String[] animalAttributes, String[] valuesToCompare) {


        int matchCounter = 0;

        // if the time of day given by the user matches time of day attribute 1 point
        // no match is 0 points.
        for (String attribute : animalAttributes) {
            for (String target : valuesToCompare) {
                if (attribute.equals(target)) {
                    matchCounter++;
                }
            }
        }

        return matchCounter;
    }

}
