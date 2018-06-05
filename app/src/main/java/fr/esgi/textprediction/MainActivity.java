package fr.esgi.textprediction;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView listPredictions;
    private PredictionButtonsAdapter predictionButtonsAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listPredictions = findViewById(R.id.list_predictions);
        listPredictions.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        listPredictions.setLayoutManager(mLayoutManager);
        List<String> predictions = new ArrayList<>();
        predictions.add("Meh1");
        predictions.add("Meh2");
        predictions.add("Meh3");
        predictions.add("Meh4");
        predictions.add("Meh5");
        predictions.add("Meh6");

        predictionButtonsAdapter = new PredictionButtonsAdapter(this, predictions);
        listPredictions.setAdapter(predictionButtonsAdapter);
        Log.d("PredictionAdapter", predictionButtonsAdapter.getItem(0));
        listPredictions.addOnItemTouchListener(new RecyclerItemClickListener(this, listPredictions, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String prediction = predictionButtonsAdapter.getItem(position);
                Toast.makeText(MainActivity.this, String.format("Clicked on prediction: %s", prediction), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

}
