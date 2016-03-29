package se3a04.anifind;


import java.util.ArrayList;
import java.util.HashMap;

import se3a04.anifind.DataEntities.Animal;
import se3a04.anifind.DataEntities.QA;
import se3a04.anifind.Experts.Expert;
import se3a04.anifind.Experts.HabitatExpert;
import se3a04.anifind.Experts.LocationExpert;
import se3a04.anifind.Experts.MobilityExpert;
import se3a04.anifind.Experts.SizeExpert;
import se3a04.anifind.Experts.TimeExpert;

public class BlackBoard {


    private Expert[] experts;

    public BlackBoard() {


        experts = new Expert[] {
                new HabitatExpert(),
                new LocationExpert(),
                new MobilityExpert(),
                new SizeExpert(),
                new TimeExpert()
        };
    }



    public void consultAllExperts(ArrayList<Animal> animals, HashMap<String, QA> qas) {

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

                    case "Time":
                        pnt = e.validateAttribute(a.getLifestyle().split("##"), qas.get("Lifestyle").getAnswersGivenByUsers());
                        a.updatePoint(pnt);
                        break;

                    default:
                        a.updatePoint(0);
                        break;
                }
            }
        }
    }
}

