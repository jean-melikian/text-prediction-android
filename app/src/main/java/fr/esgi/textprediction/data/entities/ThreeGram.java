package fr.esgi.textprediction.data.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Jean-Christophe Melikian on 06/06/2018.
 */
@Entity(tableName = "three_gram",
        foreignKeys = @ForeignKey(
                entity = Prediction.class,
                parentColumns = "key",
                childColumns = "key"
        )
)
public class ThreeGram {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "a")
    private String first;

    @ColumnInfo(name = "b")
    private String second;

    @ColumnInfo(name = "c")
    private String third;

    @ColumnInfo(name = "key")
    private String predictionKey;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getThird() {
        return third;
    }

    public void setThird(String third) {
        this.third = third;
    }

    public String getPredictionKey() {
        return predictionKey;
    }

    public void setPredictionKey(String predictionKey) {
        this.predictionKey = predictionKey;
    }
}
