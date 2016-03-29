package se3a04.anifind.Experts;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Zachary on 3/28/2016.
 */
public class TimeExpert extends Expert {

    private final String EXPERTISE = "Time";
    private String currentSystemTime;
    /* In order to get the system time you need to use Java calender object to get the
    system time.The SimpleDateFormat allows the programmer to format the date/time as they
    see fit for use in a string. In this case, we only need the system time so the format
    is HH:mm. The system time is returned using the 24 hour clock, then converted to a string
    using the SimpleDateFormat <format> method. */


    @Override
    public String getExpertise() {
        return EXPERTISE;
    }

    @Override
    public int validateAttribute(String[] animalAttributes, String[] valuesToCompare) {
        return 0;
    }

    private String getCurrentTime() {

        /* In order to get the system time you need to use Java calender object to get the
        system time.The SimpleDateFormat allows the programmer to format the date/time as they
        see fit for use in a string. In this case, we only need the system time so the format
        is HH:mm. The system time is returned using the 24 hour clock, then converted to a string
        using the SimpleDateFormat <format> method. */

        Calendar c = Calendar.getInstance();
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        currentSystemTime = time.format(c.getTime());

        return currentSystemTime;
    }

    private String getCurrentSeason(){
        return null;
    }


}
