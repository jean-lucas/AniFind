package se3a04.anifind;


import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import se3a04.anifind.DataEntities.Animal;
import se3a04.anifind.DataEntities.QA;
import se3a04.anifind.Experts.ColourExpert;
import se3a04.anifind.Experts.Expert;
import se3a04.anifind.Experts.HabitatExpert;
//import se3a04.anifind.Experts.LocationExpert;
import se3a04.anifind.Experts.LocationExpert;
import se3a04.anifind.Experts.MobilityExpert;
import se3a04.anifind.Experts.SizeExpert;
import se3a04.anifind.Experts.SizeExpert2;
import se3a04.anifind.Experts.TimeExpert;


/**
 * TODO: Confirm the arguments for consultAlLExperts is valid, and do-able
 * TODO: Should BlackBoard sort the results based on points once finished?
 *
 */

public class BlackBoard {


    private Expert[] experts;

    public BlackBoard() {

        this.experts = new Expert[] {
                new HabitatExpert(),
//                new LocationExpert(),
                new MobilityExpert(),
                new SizeExpert2(),
//                new TimeExpert(),
                new ColourExpert()
        };
    }



    //Call each respective expert with each animal's attribute
    //return the updated list of animals
    public ArrayList<Animal> consultAllExperts(ArrayList<Animal> animals, HashMap<String, QA> qas) {

        int pnt = 0;

        for (Expert e: experts) {
            for (Animal a: animals) {

                switch (e.getExpertise()) {

                    case "Habitat":
                        pnt = e.validateAttribute(a.getHabitat(), qas.get("Habitat").getAnswersGivenByUsers());
                        a.updatePoint(pnt);
                        break;

                    case "Location":
                        pnt = e.validateAttribute(a.getLocation(), qas.get("Location").getAnswersGivenByUsers());
                        a.updatePoint(pnt);
                        break;

                    case "Mobility":
                        pnt = e.validateAttribute(a.getMobility(), qas.get("Mobility").getAnswersGivenByUsers());
                        a.updatePoint(pnt);
                        break;

                    case "Size":
                        pnt = e.validateAttribute(a.getSize(), qas.get("Size").getAnswersGivenByUsers());
                        a.updatePoint(pnt);
                        break;

                    case "Colour":
                        pnt = e.validateAttribute(a.getColors(), qas.get("Colour").getAnswersGivenByUsers());
                        a.updatePoint(pnt);
                        break;

                    case "Time":
                        //the split("##") is used just to turn a string into an array with 1 element.
                        //the symbol ## is used since it is safe to assume it will not conflict with the actual
                        //contents of the string
                        pnt = e.validateAttribute(a.getLifestyle().split("##"), qas.get("Time").getAnswersGivenByUsers());
                        a.updatePoint(pnt);
                        break;

                    default:
                        a.updatePoint(0);
                        break;
                }
            }
        }

        return animals;
    }




    public String getActiveExperts() {

        String activeExperts = "";

        for (Expert e: this.experts) {
            activeExperts += e.getExpertise() + ", ";
        }

        return activeExperts;
    }
}

