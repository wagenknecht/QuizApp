package de.hochschulestralsund.quizapp.Adapter;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hochschulestralsund.quizapp.Activitys.HighscoreActivity;
import de.hochschulestralsund.quizapp.Database.Bestenliste;
import de.hochschulestralsund.quizapp.R;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder> {
    List<Bestenliste> values; //todo zum gewünschten DTO ändern


    public ScoreAdapter(List<Bestenliste> input){
        values = input;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView score;
        public TextView name;
        public TextView difficulty;

        public ViewHolder(@NonNull View v) {
            super(v);
            score= v.findViewById(R.id.firstLine);
            name = v.findViewById(R.id.secondLine);
            difficulty = v.findViewById(R.id.difficulty);
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
        View v = inflater.inflate(R.layout.score, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreAdapter.ViewHolder holder, int position) {
        holder.score.setText(values.get(position).getName());
        holder.name.setText("name");
        holder.difficulty.setText("hard");
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}
