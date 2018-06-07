package fr.esgi.textprediction.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import fr.esgi.textprediction.data.entities.Prediction;

/**
 * Created by Jean-Christophe Melikian on 06/06/2018.
 */
@Dao
public interface PredictionDao {


    @Query("SELECT prediction.* FROM prediction, one_gram "
            + "WHERE one_gram.`key` = prediction.`key`"
            + "AND one_gram.`a` LIKE :a "
            + "ORDER BY prediction.`count` DESC LIMIT 10")
    List<Prediction> predictOneGram(String a);


    @Query("SELECT prediction.* FROM prediction, two_gram "
            + "WHERE two_gram.`key` = prediction.`key`"
            + "AND two_gram.`a` LIKE :a "
            + "AND two_gram.`b` LIKE :b "
            + "ORDER BY prediction.`count` DESC LIMIT 10")
    List<Prediction> predictTwoGram(String a, String b);

    @Query("SELECT prediction.* FROM prediction, three_gram "
            + "WHERE three_gram.`key` = prediction.`key`"
            + "AND three_gram.`a` LIKE :a "
            + "AND three_gram.`b` LIKE :b "
            + "AND three_gram.`c` LIKE :c "
            + "ORDER BY prediction.`count` DESC LIMIT 10")
    List<Prediction> predictThreeGram(String a, String b, String c);
}
