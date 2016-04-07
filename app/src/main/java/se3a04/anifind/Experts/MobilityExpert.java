package se3a04.anifind.Experts;

public class MobilityExpert extends Expert {

    private final String EXPERTISE = "Mobility";


    @Override
    public String getExpertise() {
        return EXPERTISE;
    }

    @Override
    public int validateAttribute(String[] animalAttributes, String[] valuesToCompare) {
        int matchCounter = 0;

        if (valuesToCompare.length == 0 || animalAttributes.length == 0) return 0;
        if (animalAttributes[0].equals("") || animalAttributes[0].equals(" ") ) return 0;

        for (String attribute : animalAttributes) {
            for (String target : valuesToCompare) {
                if (attribute.equalsIgnoreCase(target)) {
                    matchCounter += 20;
                }
            }
        }

        return matchCounter;
    }

}
