package se3a04.anifind.Experts;

public class LocationExpert extends Expert {

    private final String EXPERTISE = "Location";



    @Override
    public String getExpertise() {
        return EXPERTISE;
    }

    @Override
    public int validateAttribute(String[] animalAttributes, String[] valuesToCompare) {

        int matchCounter = 0;

        if (valuesToCompare.length > 0 && valuesToCompare[0].equalsIgnoreCase("current")) {
            String[] valuesToCompareNew = new String[1];
            valuesToCompareNew[0] = valuesToCompare[3];

            for (String attribute : animalAttributes) {
                for (String target : valuesToCompareNew) {

                    if (attribute.equalsIgnoreCase(target)) {
                        matchCounter += 5;
                    }
                }
            }
        }


        else if(valuesToCompare.length > 0) {

            for (String attribute : animalAttributes) {
                for (String target : valuesToCompare) {

                    if (attribute.equalsIgnoreCase(target)) {
                        matchCounter += 5;
                    }
                }
            }
        }

        return matchCounter;
    }

}

