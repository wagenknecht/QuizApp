package de.hochschulestralsund.quizapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hochschulestralsund.quizapp.Database.EndlessHighscore;
import de.hochschulestralsund.quizapp.R;

public class EndlessScoreAdapter extends RecyclerView.Adapter<EndlessScoreAdapter.ViewHolder> {
    List<EndlessHighscore> values; //todo zum gewünschten DTO ändern

    public EndlessScoreAdapter(List<EndlessHighscore> input){
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

    public void add(int position, EndlessHighscore item) {
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
        View v = inflater.inflate(R.layout.endless_score, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EndlessScoreAdapter.ViewHolder holder, int position) {
        String score = String.valueOf(values.get(position).getScore());
        holder.score.setText(score);
        holder.name.setText(values.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}
