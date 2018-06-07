package fr.esgi.textprediction.data.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Jean-Christophe Melikian on 06/06/2018.
 */
@Entity(tableName = "one_gram",
        foreignKeys = @ForeignKey(
                entity = Prediction.class,
                parentColumns = "key",
                childColumns = "key"
        )
)
public class OneGram {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "a")
    private String first;

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

    public String getPredictionKey() {
        return predictionKey;
    }

    public void setPredictionKey(String predictionKey) {
        this.predictionKey = predictionKey;
    }
}
