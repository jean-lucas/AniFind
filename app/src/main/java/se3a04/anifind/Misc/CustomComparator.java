package se3a04.anifind.Misc;


import java.util.Comparator;

import se3a04.anifind.DataEntities.Animal;

public class CustomComparator implements Comparator<Animal> {


    //This compareTo method is made such that the animal with the highest points
    //will come in first
    @Override
    public int compare(Animal lhs, Animal rhs) {
        if (lhs.getPoints() < rhs.getPoints()) return 1;
        else if (lhs.getPoints() == rhs.getPoints()) return  0;
        return -1;
    }
}