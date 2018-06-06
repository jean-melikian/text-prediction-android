package fr.esgi.textprediction;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import fr.esgi.textprediction.recommender.FrenchRecommender;
import fr.esgi.textprediction.recommender.IRecommender;

public class MainActivity extends AppCompatActivity {

    public static final String PUNCTUATION_PATTERN = "[ ,;:=+&@*_`'\".!?\\-/]+";
    private RecyclerView listPredictions;
    private EditText predictableInput;
    private TextView predictedTextView;

    private PredictionButtonsAdapter predictionButtonsAdapter;
    private LinearLayoutManager mLayoutManager;

    private IRecommender recommender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listPredictions = findViewById(R.id.list_predictions);
        predictableInput = findViewById(R.id.input_text);
        predictedTextView = findViewById(R.id.result_text_view);

        listPredictions.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        listPredictions.setLayoutManager(mLayoutManager);


        predictionButtonsAdapter = new PredictionButtonsAdapter(this, new ArrayList<String>());
        listPredictions.setAdapter(predictionButtonsAdapter);

        listPredictions.addOnItemTouchListener(new RecyclerItemClickListener(this, listPredictions, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String prediction = predictionButtonsAdapter.getItem(position);
                predictableInput.append(String.format("%s ", prediction));
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));

        predictableInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                if (recommender == null) {
                    recommender = new FrenchRecommender();
                }
                recommend_predicted(input);
            }
        });

        predictableInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                        || actionId == EditorInfo.IME_ACTION_DONE) {
                    predictedTextView.append(" " + predictableInput.getText());
                    predictableInput.setText("");

                    return true;
                }
                return false;
            }
        });

        recommender = new FrenchRecommender();

        recommend_predicted("");

    }

    private void recommend_predicted(String input) {

        String lastChar = (!input.isEmpty()) ? input.substring(input.length() - 1) : "";
        if (lastChar.isEmpty() || Pattern.matches(PUNCTUATION_PATTERN, lastChar)) {

            String[] tokens = input.split(PUNCTUATION_PATTERN);
            Log.d("Prediction", String.format("Split string tokens: %s", Arrays.asList(tokens).toString()));

            List<String> predictions = recommender.recommend(Arrays.asList(tokens));
            List<String> pred = new ArrayList<>(predictions);
            Log.d("Prediction", String.format("Predictions: %s", pred.toString()));

            predictionButtonsAdapter.clear();
            predictionButtonsAdapter.addAll(pred);
        }
    }

}
