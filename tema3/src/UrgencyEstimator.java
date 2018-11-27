import entities.enums.IllnessType;
import entities.enums.Urgency;

import java.util.HashMap;
import java.util.Map;


/**
 * Estimates the urgency based on the patient's illness and how severe the illness is manifested
 */
public class UrgencyEstimator {

    private static UrgencyEstimator instance;
    private Map<Urgency, HashMap<IllnessType, Integer>> algorithm;

    private UrgencyEstimator(){
        algorithm = new HashMap<Urgency, HashMap<IllnessType, Integer>>() {
            {
                put(Urgency.IMMEDIATE,
                        new HashMap<IllnessType, Integer>() {
                            {
                                put(IllnessType.ABDOMINAL_PAIN, 60);
                                put(IllnessType.ALLERGIC_REACTION, 50);
                                put(IllnessType.BROKEN_BONES, 80);
                                put(IllnessType.BURNS, 40);
                                put(IllnessType.CAR_ACCIDENT, 30);
                                put(IllnessType.CUTS, 50);
                                put(IllnessType.FOOD_POISONING, 50);
                                put(IllnessType.HEART_ATTACK, 0);
                                put(IllnessType.HEART_DISEASE, 40);
                                put(IllnessType.HIGH_FEVER, 70);
                                put(IllnessType.PNEUMONIA, 80);
                                put(IllnessType.SPORT_INJURIES, 70);
                                put(IllnessType.STROKE, 0);

                            }
                        });

                put(Urgency.URGENT,
                        new HashMap<IllnessType, Integer>() {
                            {
                                put(IllnessType.ABDOMINAL_PAIN, 40);
                                put(IllnessType.ALLERGIC_REACTION, 30);
                                put(IllnessType.BROKEN_BONES, 50);
                                put(IllnessType.BURNS, 20);
                                put(IllnessType.CAR_ACCIDENT, 20);
                                put(IllnessType.CUTS, 30);
                                put(IllnessType.HEART_ATTACK, 0);
                                put(IllnessType.FOOD_POISONING, 20);
                                put(IllnessType.HEART_DISEASE, 20);
                                put(IllnessType.HIGH_FEVER, 40);
                                put(IllnessType.PNEUMONIA, 50);
                                put(IllnessType.SPORT_INJURIES, 50);
                                put(IllnessType.STROKE, 0);
                            }
                        });

                put(Urgency.LESS_URGENT,
                        new HashMap<IllnessType, Integer>() {
                            {
                                put(IllnessType.ABDOMINAL_PAIN, 10);
                                put(IllnessType.ALLERGIC_REACTION, 10);
                                put(IllnessType.BROKEN_BONES, 20);
                                put(IllnessType.BURNS, 10);
                                put(IllnessType.CAR_ACCIDENT, 10);
                                put(IllnessType.CUTS, 10);
                                put(IllnessType.FOOD_POISONING, 0);
                                put(IllnessType.HEART_ATTACK, 0);
                                put(IllnessType.HEART_DISEASE, 10);
                                put(IllnessType.HIGH_FEVER, 0);
                                put(IllnessType.PNEUMONIA, 10);
                                put(IllnessType.SPORT_INJURIES, 20);
                                put(IllnessType.STROKE, 0);
                            }
                        });

            }
        };
    }

    public static UrgencyEstimator getInstance() {
        if (instance == null) {
            instance = new UrgencyEstimator();
        }
        return instance;
    }

    //called by doctors and nurses
    public Urgency estimateUrgency(IllnessType illnessType, int severity) {

        if (severity >= algorithm.get(Urgency.IMMEDIATE).get(illnessType) )
            return Urgency.IMMEDIATE;
        if (severity >= algorithm.get(Urgency.URGENT).get(illnessType) )
            return Urgency.URGENT;
        if (severity >= algorithm.get(Urgency.LESS_URGENT).get(illnessType) )
            return Urgency.LESS_URGENT;
        return Urgency.NON_URGENT;
    }
}
