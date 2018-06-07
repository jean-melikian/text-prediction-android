package fr.esgi.textprediction.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import fr.esgi.textprediction.data.entities.Prediction;

/**
 * Created by Jean-Christophe Melikian on 06/06/2018.
 */
@Dao
public interface PredictionDao {


    @Query("SELECT prediction.* FROM prediction, one_gram "
            + "WHERE one_gram.`key` = prediction.`key`"
            + "AND one_gram.`a` LIKE :a ")
    Prediction predictOneGram(String a);


    @Query("SELECT prediction.* FROM prediction, two_gram "
            + "WHERE two_gram.`key` = prediction.`key`"
            + "AND two_gram.`a` LIKE :a "
            + "AND two_gram.`b` LIKE :b ")
    Prediction predictTwoGram(String a, String b);

    @Query("SELECT prediction.* FROM prediction, three_gram "
            + "WHERE three_gram.`key` = prediction.`key`"
            + "AND three_gram.`a` LIKE :a "
            + "AND three_gram.`b` LIKE :b "
            + "AND three_gram.`c` LIKE :c")
    Prediction predictThreeGram(String a, String b, String c);
}
