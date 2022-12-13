package com.example.chessandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecordedGamesAdapter extends RecyclerView.Adapter<RecordedGamesAdapter.MyViewHolder2> {

    private final RecordedGamesInterface recyclerViewInterface;

    Context context;
    ArrayList<RecordedGameModel> recordedGameModels;

    public RecordedGamesAdapter(Context context, ArrayList<RecordedGameModel> recordedGameModels, RecordedGamesInterface recyclerViewInterface){
        this.context = context;
        this.recordedGameModels = recordedGameModels;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public RecordedGamesAdapter.MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.a_recorded_game, parent, false);
        return new RecordedGamesAdapter.MyViewHolder2(v, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordedGamesAdapter.MyViewHolder2 holder, int position) {
        holder.gameTitle.setText(recordedGameModels.get(position).gameTitle);
        holder.gameDate.setText(recordedGameModels.get(position).date);
    }

    @Override
    public int getItemCount() {
        return recordedGameModels.size();
    }

    public static class MyViewHolder2 extends RecyclerView.ViewHolder{

        TextView gameTitle;
        TextView gameDate;

        public MyViewHolder2(@NonNull View itemView, RecordedGamesInterface recordedGamesInterface) {
            super(itemView);
            gameTitle = itemView.findViewById(R.id.gameTitle);
            gameDate = itemView.findViewById(R.id.gameDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recordedGamesInterface != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            recordedGamesInterface.onGameClick(position);
                        }
                    }
                }
            });
        }
    }
}
