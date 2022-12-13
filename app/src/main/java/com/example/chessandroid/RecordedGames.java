package com.example.chessandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecordedGames extends AppCompatActivity implements RecordedGamesInterface{

    ArrayList<RecordedGameModel> gameModels;
    private Button backButton;
    private Switch title_or_date;
    RecyclerView recyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorded_games);

        recyclerView = findViewById(R.id.gameList);

        Intent intent = getIntent();
        gameModels = (ArrayList<RecordedGameModel>) intent.getSerializableExtra("records");

        RecordedGamesAdapter adapter = new RecordedGamesAdapter(this, gameModels, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        backButton = findViewById(R.id.backButton2);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        title_or_date = findViewById(R.id.switch1);
        title_or_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title_or_date.setText("(Tap To Change)");
                /*
                Sort the recorded games on either title or date from the switch
                 */
            }
        });


    }

    private void goWatchGame(int pos) {
        Intent intent = new Intent(this, WatchGames.class);
        intent.putExtra("record", gameModels.get(pos));
        startActivity(intent);
    }

    private void goBack() {
        this.finish();
    }

    @Override
    public void onGameClick(int position) {
        goWatchGame(position);
    }
}