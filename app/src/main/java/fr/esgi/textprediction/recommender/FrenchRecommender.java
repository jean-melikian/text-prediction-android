package fr.esgi.textprediction.recommender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of the recommender for the French language
 *
 * @author formation
 */
public class FrenchRecommender implements IRecommender {

    @Override
    public String getLanguage() {
        return "French";
    }

    /*
    @Override
    public String[] recommend(String... words) {
        return new String[]{"je", "tu", "mon", "ma"};
    }
    */

    @Override
    public List<String> recommend(List<String> words) {
        ArrayList<String> predictions = new ArrayList<String>() {{
            add("je");
            add("tu");
            add("mon");
            add("ma");
        }};

        Collections.shuffle(predictions);
        return predictions;
    }

}
