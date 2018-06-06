package fr.esgi.textprediction.recommender;

/**
 * Created by Jean-Christophe Melikian on 06/06/2018.
 */
public interface OnRecommendationListener {
    void recommendation(String prediction, String input);
}
