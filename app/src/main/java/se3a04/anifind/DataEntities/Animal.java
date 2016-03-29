package se3a04.anifind.DataEntities;

/**
 * An ADT for an Animal object, with all attributes required for each expert
 */
public class Animal {

    private String name;
    private String lifestyle;   //for time expert
    private String location;    //for location expert
    private String mobility;    //for mobility expert
    private String habitat;     //for habitat expert
    private String[] colors;    //for color expert
    private String[] size;      //for size expert

    private String imgFileName;

    private int points;         //points assigned from experts

    private int NUMBER_OF_PROPERTIES = 7;   //number of attributes DONT THINK THIS IS NEEDED


    public Animal(String name, String lifestyle, String location, String mobility, String habitat, String[] colors, String[] size, String imgFileName) {
        this.name = name;
        this.lifestyle = lifestyle;
        this.location = location;
        this.mobility = mobility;
        this.habitat = habitat;
        this.colors = colors;
        this.size = size;

        this.imgFileName = imgFileName;

        this.points = 0;
    }


    public String getName() {
        return name;
    }

    public String getLifestyle() {
        return lifestyle;
    }

    public String getLocation() {
        return location;
    }

    public String getMobility() {
        return mobility;
    }

    public String getHabitat() {
        return habitat;
    }

    public String[] getColors() {
        return colors;
    }

    public String[] getSize() {
        return size;
    }

    public String getImgFileName() {
        return imgFileName;
    }

    public int getPoints() {
        return points;
    }



    public void updatePoint(int points) {
        this.points += points;
    }



    @Override
    public String toString() {
        return this.getName() + " : " + this.getPoints();

    }
}