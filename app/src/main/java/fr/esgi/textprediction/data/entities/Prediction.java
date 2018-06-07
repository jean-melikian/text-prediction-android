package fr.esgi.textprediction.data.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Jean-Christophe Melikian on 06/06/2018.
 */
@Entity(tableName = "prediction")
public class Prediction {

    @PrimaryKey
    @NonNull
    private String key;

    @ColumnInfo(name = "out")
    private String prediction;

    @ColumnInfo(name = "count")
    private int count;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPrediction() {
        return prediction;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
