package se3a04.anifind.Experts;

/**
 * Created by Zachary on 3/28/2016.
 */
public class ColourExpert extends Expert{

    private int points;
    private final String EXPERTISE= "Colour";

    @Override
    public String getExpertise() {

        // <this> implies an attribute or method from this object
        return EXPERTISE;
    }




    @Override
    public int validateAttribute(String[] animalAttributes, String[] valuesToCompare) {
        return points;

        // call method for points
    }
}
