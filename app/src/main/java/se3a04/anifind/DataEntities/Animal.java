package se3a04.anifind.DataEntities;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;

/**
 * An ADT for an Animal object, with all attributes required for each expert
 */
public class Animal implements Serializable {

    private String name;
    private String lifestyle;   //for time expert
    private String[] habitat;     //for habitat expert
    private String[] mobility;    //for mobility expert
    private String[] locations;    //for location expert
    private String[] colors;    //for color expert
    private String[] size;      //for size expert

    //private String imgFileName;

    private int points;         //points assigned from experts

    private int NUMBER_OF_PROPERTIES = 7;   //number of attributes DONT THINK THIS IS NEEDED


    public Animal(String name, String lifestyle, String[] habitat, String[] mobility, String[] location, String[] colors, String[] size) {
        this.name = name;
        this.lifestyle = lifestyle;
        this.locations = location;
        this.mobility = mobility;
        this.habitat = habitat;
        this.colors = colors;
        this.size = size;

        //this.imgFileName = imgFileName;

        this.points = 0;
    }


    public String getName() {
        return name;
    }

    public String getLifestyle() {
        return lifestyle;
    }

    public String[] getLocation() {
        return locations;
    }

    public String[] getMobility() {
        return mobility;
    }

    public String[] getHabitat() {
        return habitat;
    }

    public String[] getColors() {
        return colors;
    }

    public String[] getSize() {
        return size;
    }

    /*public String getImgFileName() {
        return imgFileName;
    }*/

    public int getPoints() {
        return points;
    }


    public void updatePoint(int points) {
        this.points += points;
    }


    public HashMap<String, String[]> getFacts() {
        HashMap<String,String[]> facts = new HashMap<String, String[]>();

        facts.put("color",this.getColors());
        facts.put("habitat",this.getHabitat());
        facts.put("location",this.getLocation());
        facts.put("Time",this.getLifestyle().split("."));
        facts.put("mobility", this.getMobility());
        facts.put("size", this.getSize());

        return facts;

   }

    @Override
    public String toString() {
        return this.getName() + " : " + this.getPoints();

    }
}


