package fr.esgi.textprediction.recommender;

import java.util.List;

/**
 * Main class to implement.
 * Given an array of words in a sentence, get the next words that would match.
 * For instance, in french given : "à","cause" we want to suggest : "de"
 *
 * @author formation
 */
public interface IRecommender {
    /**
     * Get the language for the recommander
     *
     * @return the name of the language for the recommender
     */
    String getLanguage();

    /**
     * Get the suggestions given a set of words
     *
     * @param words the words in a sentence
     * @return the options for the next word
     */
//    String[] recommend(String[] words);

    List<String> recommend(List<String> words);

}
