package de.hochschulestralsund.quizapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hochschulestralsund.quizapp.Database.Bestenliste;
import de.hochschulestralsund.quizapp.R;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder> {
    List<Bestenliste> values;

    public ScoreAdapter(List<Bestenliste> input) {
        values = input;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView score;
        public TextView name;

        public ViewHolder(@NonNull View v) {
            super(v);
            score = v.findViewById(R.id.tvPosition);
            name = v.findViewById(R.id.tvUsername);
        }
    }

    public void add(int position, Bestenliste item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_score, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreAdapter.ViewHolder holder, int position) {
        String score = values.get(position).getScore() + "/10";
        holder.score.setText(score);
        holder.name.setText(values.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}
