package se3a04.anifind.Experts;


public abstract class Expert {



    /**
     * Allows the title of the expert to be accessible
     * @return expert's name
     */
    public abstract String getExpertise();



    /**
     * Assign points based the comparison of the current animal attribute,
     * and the value it is comparing against.
     * @param animalAttributes the attribute of the animal beign compared
     * @param valuesToCompare the value given by the user from the GUI
     * @return The points given
     */
    public abstract int validateAttribute(String[] animalAttributes, String[] valuesToCompare);
}
