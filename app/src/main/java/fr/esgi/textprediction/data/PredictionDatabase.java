package fr.esgi.textprediction.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import fr.esgi.textprediction.data.dao.PredictionDao;
import fr.esgi.textprediction.data.entities.OneGram;
import fr.esgi.textprediction.data.entities.Prediction;
import fr.esgi.textprediction.data.entities.ThreeGram;
import fr.esgi.textprediction.data.entities.TwoGram;

/**
 * Created by Jean-Christophe Melikian on 06/06/2018.
 */
@Database(
        version = 2,
        entities = {Prediction.class, OneGram.class, TwoGram.class, ThreeGram.class})
public abstract class PredictionDatabase extends RoomDatabase {
    public abstract PredictionDao predictionDao();
}
