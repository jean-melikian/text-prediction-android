package fr.esgi.textprediction;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Collection;
import java.util.List;

/**
 * Created by Jean-Christophe Melikian on 05/06/2018.
 */
public class PredictionButtonsAdapter extends RecyclerView.Adapter<PredictionButtonsAdapter.ViewHolder> {

    private Context context;
    private List<String> predictions;

    public PredictionButtonsAdapter(Context context, List<String> predictions) {
        this.context = context;
        this.predictions = predictions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prediction_button_list_element, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mButton.setText(predictions.get(position));
    }

    @Override
    public int getItemCount() {
        return predictions.size();
    }

    public String getItem(int position) {
        return predictions.get(position);
    }

    public void add(String prediction) {
        predictions.add(prediction);
        notifyItemInserted(predictions.size() - 1);
    }

    public void addAll(Collection<String> newPredictions) {
        int startIndex = predictions.size();
        predictions.addAll(newPredictions);
        notifyItemRangeInserted(startIndex, newPredictions.size());
    }

    public void clear() {
        predictions.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public Button mButton;

        public ViewHolder(View itemView) {
            super(itemView);
            mButton = itemView.findViewById(R.id.prediction_button);

        }
    }
}
