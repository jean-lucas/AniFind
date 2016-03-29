package se3a04.anifind.Experts;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Zachary on 3/28/2016.
 *
 * Sunrise to sunset considered day
 * Sunset to sunrise considered night.
 *
 * The assumption is there are day animals and night animals. Night animals won't be out during the
 * day unless they have rabies or got killed on the 403 for example. Day animals will be out at day
 * but not at night. This assumption can only hold for wild animals. Domestic animals could be out
 * day or night but generally you don't need to identify.
 *
 * There is a library found here http://mikereedell.github.io/sunrisesunsetlib-java/ that does an
 * accurate calculation but to get things up and running this class uses a class found here..
 * https://dev.kafol.net/2012/09/java-calculating-approximate-sunset-and.html
 * that is accurate for demonstration purposes.
 *
 * Point breakdown.
 * If the animal was seen between sunrise and sunset and the animal is a day animal. -> 1 point
 * If the animal was seen between sunrise and sunset and the animal is not day -> 0 points
 *
 * The reverse also holds.
 */
public class TimeExpert extends Expert {

    private final String EXPERTISE = "Time";
    private String currentSystemTime;

    @Override
    public String getExpertise() {
        return EXPERTISE;
    }

    @Override
    public int validateAttribute(String[] animalAttributes, String[] valuesToCompare) {

        // Assuming valuesToCompare comes with latitude, date and time in the form YYYY MM DD HHmm
        // Assuming attributes comes in the form HHmm
        // as a string; That then gets parsed here to fill in the respective values.


        int matchCounter = 0;

        for (String attribute : animalAttributes) {
            for (String target : valuesToCompare) {

                // get latitude from google maps
                double latitude = 273;

                // Assuming a space between YYYY MM DD HHmm latitude
                String[] date = target.split(" ");


                // Working with system time requires manipulating into the format that you wish.

                // returns sunrise and sunset in date format
                Date vSunrise = Analemma.sunrise(latitude, Integer.parseInt(date[0]),
                        Integer.parseInt(date[1]), Integer.parseInt(date[2]));
                Date vSunset = Analemma.sunset(latitude, Integer.parseInt(date[0]),
                        Integer.parseInt(date[1]), Integer.parseInt(date[2]));


                SimpleDateFormat time = new SimpleDateFormat("HHmm");


                // Sunrise and sunset in string format
                String sunrise = time.format(vSunrise.getTime());
                String sunset = time.format(vSunset.getTime());

                // between sunrise and sunset
                if (Double.parseDouble(attribute) >= Double.parseDouble(sunrise)
                        && Double.parseDouble(attribute) <= Double.parseDouble(sunset)) {
                    if (Double.parseDouble(date[3]) >= Double.parseDouble(sunrise)
                            && Double.parseDouble(date[3]) <= Double.parseDouble(sunset)) {
                        matchCounter++;

                    }



                    //System.out.println("1--");
                }
                // before sunrise or after sunset (night animal)
                if (Double.parseDouble(attribute) < Double.parseDouble(sunrise)
                        || Double.parseDouble(attribute) > Double.parseDouble(sunset)) {
                    if (Double.parseDouble(date[3]) < Double.parseDouble(sunrise)
                            || Double.parseDouble(date[3]) > Double.parseDouble(sunset)) {
                        matchCounter++;
                    }

                    // check if target is between

                    //System.out.println("2--");
                }


            }
        }


//        Date sunrise = Analemma.sunrise(51, 2016, 2, 29);
//        Date sunset = Analemma.sunset(51, 2016, 2, 29);
//
//        Date q = Analemma.sunset(51, 2016, 2, 29);
//        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
//        String currentSystemTime = time.format(q.getTime());


        return matchCounter;
    }

    private String getCurrentTime() {

        /* In order to get the system time you need to use Java calender object to get the
        system time.The SimpleDateFormat allows the programmer to format the date/time as they
        see fit for use in a string. In this case, we only need the system time so the format
        is HH:mm. The system time is returned using the 24 hour clock, then converted to a string
        using the SimpleDateFormat <format> method. */

        /*
        The date needs to be packaged in as well for the sunrise and sunset calculation

         */

        Calendar c = Calendar.getInstance();
        SimpleDateFormat time = new SimpleDateFormat("YYYY:MM:DD:HH:mm");
        currentSystemTime = time.format(c.getTime());

        return currentSystemTime;
    }

    private String getCurrentSeason() {
        return "Spring";
    }


}
