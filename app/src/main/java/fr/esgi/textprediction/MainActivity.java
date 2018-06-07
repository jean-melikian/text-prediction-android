package fr.esgi.textprediction;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import fr.esgi.textprediction.data.DatabaseManager;
import fr.esgi.textprediction.data.PredictionDatabase;
import fr.esgi.textprediction.data.dao.PredictionDao;
import fr.esgi.textprediction.data.entities.Prediction;

public class MainActivity extends AppCompatActivity {

    public static final String PUNCTUATION_PATTERN = "[ ,;:=+&@*_`'\".!?\\-/]+";
    private RecyclerView listPredictions;
    private EditText predictableInput;
    private TextView predictedTextView;

    private PredictionButtonsAdapter predictionButtonsAdapter;
    private LinearLayoutManager mLayoutManager;

    private PredictionDatabase database;

    private RecommendTask recommendTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = DatabaseManager.getInstance().getDatabase();

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
                recommend_predicted(input);
            }
        });

        predictableInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                        || actionId == EditorInfo.IME_ACTION_DONE) {
                    predictedTextView.append(predictableInput.getText() + "\n");
                    predictableInput.setText("");
                    return true;
                }
                return false;
            }
        });

        recommend_predicted("je suis");
    }

    private void recommend_predicted(String input) {

        String lastChar = (!input.isEmpty()) ? input.substring(input.length() - 1) : "";
        if (lastChar.isEmpty() || Pattern.matches(PUNCTUATION_PATTERN, lastChar)) {

            String[] tokens = input.split(PUNCTUATION_PATTERN);

            recommendTask = new RecommendTask();
            recommendTask.execute(tokens);

        }
    }

    private class RecommendTask extends AsyncTask<String, Void, List<Prediction>> {
        private List<Prediction> predictions;
        private PredictionDao dao;

        @Override
        protected void onPreExecute() {
            if (dao == null) {
                dao = database.predictionDao();
            }
            predictions = new ArrayList<>();
        }

        @Override
        protected List<Prediction> doInBackground(String... inputWords) {
            predictions = searchForPredictions((inputWords.length > 3 ? 3 : inputWords.length), inputWords);
            return predictions;
        }

        @Override
        protected void onPostExecute(List<Prediction> predictions) {
            predictionButtonsAdapter.newPredictions(predictions);
        }

        protected List<Prediction> searchForPredictions(int gram, String... inputWords) {
            if (gram == 1) {
                predictions = dao.predictOneGram(inputWords[inputWords.length - 1]);
            } else if (gram == 2) {
                predictions = dao.predictTwoGram(inputWords[inputWords.length - 2], inputWords[inputWords.length - 1]);
            } else if (gram == 3) {
                predictions = dao.predictThreeGram(
                        inputWords[inputWords.length - 3],
                        inputWords[inputWords.length - 2],
                        inputWords[inputWords.length - 1]
                );
            }

            if (gram > 1 && (predictions == null || predictions.isEmpty())) {
                predictions = searchForPredictions(gram - 1, inputWords);
            }
            return predictions;
        }
    }

}
