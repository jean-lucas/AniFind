package se3a04.anifind.Experts;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Zachary on 3/28/2016.
 *
 * Time of day revisited.
 * To simplify time of day logic we are going to assume 4 "states" to a day. The user is going to
 * be asked what states that they saw the animal in. The user should be able to only choose 1.
 *
 * The 4 day options that should be available to the user
 *
 * NOTE This class expects spelling as such
 *
 * "Morning", "Day", "Evening", "Night", "SystemTime"
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


        // Assuming that if we are using current system time the first animalAttribute will be
        // "SystemTime"



        int matchCounter = 0;

        // Requesting system time will only have one element array
        // Call getCurrentTime that return morning, day etc. and assign it to animal attribute
        // This allows the rest of the logic to continue as normal
        if (animalAttributes[0].equals("SystemTime")){
            animalAttributes[0] = getCurrentTime();
        }


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


    // Retrieving the current system time
    private String getCurrentTime() {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat time = new SimpleDateFormat("HHmm");
        String currentSystemTime = time.format(c.getTime());

        int sysTime = Integer.parseInt(currentSystemTime);


        if (sysTime>=500 && sysTime <=900){
            currentSystemTime = "Morning";
        }
        else if (sysTime >= 901 && sysTime <=1800){
            currentSystemTime = "Day";
        }
        else if (sysTime > 601 && sysTime <=2200){
            currentSystemTime = "Evening";
        }
        else {
            currentSystemTime = "Night";
        }



        return currentSystemTime;
    }

}
