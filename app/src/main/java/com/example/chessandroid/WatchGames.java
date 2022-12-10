package com.example.chessandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class WatchGames extends AppCompatActivity {

    private Button backButton;
    private Button nextButton;

    private ChessGame chessGame = new ChessGame();
    private ArrayList<Piece> pieces;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_games);

        GridView gridView = findViewById(R.id.gridView);
        pieces = new ArrayList<>();
        chessGame.board.createBoard();
        transferBoards();

        BoardAdapter boardAdapter = new BoardAdapter(WatchGames.this, pieces);
        gridView.setAdapter(boardAdapter);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextMove();
            }
        });
    }


    private void goBack() {
        Intent intent = new Intent(this, RecordedGames.class);
        startActivity(intent);
    }

    private void nextMove(){
        /*
        Read from text file and give call playChess with the written input
         */
        String example_input = " ";
        chessGame.playChess(example_input);
    }

    public void transferBoards() {
        Board gameBoard = chessGame.board;
        int k = 0;
        for (int i = 8; i > 0; i--) { // Starts at bottom of the double array
            for (int j = 0; j < 8; j++) {
                String val = gameBoard.getPiece(i, j);
                if (val.equals("wp")) {
                    pieces.add(new Piece(R.drawable.white_pawn));
                } else if (val.equals("wR")) {
                    pieces.add(new Piece(R.drawable.white_rook));
                } else if (val.equals("wN")) {
                    pieces.add(new Piece(R.drawable.white_knight));
                } else if (val.equals("wB")) {
                    pieces.add(new Piece(R.drawable.white_bishop));
                } else if (val.equals("wQ")) {
                    pieces.add(new Piece(R.drawable.white_queen));
                } else if (val.equals("wK")) {
                    pieces.add(new Piece(R.drawable.white_king));
                } else if (val.equals("bp")) {
                    pieces.add(new Piece(R.drawable.black_pawn));
                } else if (val.equals("bR")) {
                    pieces.add(new Piece(R.drawable.black_rook));
                } else if (val.equals("bN")) {
                    pieces.add(new Piece(R.drawable.black_knight));
                } else if (val.equals("bB")) {
                    pieces.add(new Piece(R.drawable.black_bishop));
                } else if (val.equals("bQ")) {
                    pieces.add(new Piece(R.drawable.black_queen));
                } else if (val.equals("bK")) {
                    pieces.add(new Piece(R.drawable.black_king));
                } else {
                    pieces.add(new Piece(R.drawable.blank_square));
                }
                k++;
            }
        }
    }
}
