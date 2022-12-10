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

    private final String[] gridColor = new String[65];

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_games);

        GridView gridView = findViewById(R.id.gridView);
        pieces = new ArrayList<>();
        chessGame.board.createBoard();
        transferBoards();
        colorBoard();
        BoardAdapter boardAdapter = new BoardAdapter(WatchGames.this, pieces, gridColor);
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

    private void colorBoard(){
        gridColor[0] = "#FFFFFF";
        gridColor[1] = "#ADD8E6";
        gridColor[2] = "#FFFFFF";
        gridColor[3] = "#ADD8E6";
        gridColor[4] = "#FFFFFF";
        gridColor[5] = "#ADD8E6";
        gridColor[6] = "#FFFFFF";
        gridColor[7] = "#ADD8E6";

        gridColor[8] = "#ADD8E6";
        gridColor[9] = "#FFFFFF";
        gridColor[10] = "#ADD8E6";
        gridColor[11] = "#FFFFFF";
        gridColor[12] = "#ADD8E6";
        gridColor[13] = "#FFFFFF";
        gridColor[14] = "#ADD8E6";
        gridColor[15] = "#FFFFFF";

        gridColor[16] = "#FFFFFF";
        gridColor[17] = "#ADD8E6";
        gridColor[18] = "#FFFFFF";
        gridColor[19] = "#ADD8E6";
        gridColor[20] = "#FFFFFF";
        gridColor[21] = "#ADD8E6";
        gridColor[22] = "#FFFFFF";
        gridColor[23] = "#ADD8E6";

        gridColor[24] = "#ADD8E6";
        gridColor[25] = "#FFFFFF";
        gridColor[26] = "#ADD8E6";
        gridColor[27] = "#FFFFFF";
        gridColor[28] = "#ADD8E6";
        gridColor[29] = "#FFFFFF";
        gridColor[30] = "#ADD8E6";
        gridColor[31] = "#FFFFFF";

        gridColor[32] = "#FFFFFF";
        gridColor[33] = "#ADD8E6";
        gridColor[34] = "#FFFFFF";
        gridColor[35] = "#ADD8E6";
        gridColor[36] = "#FFFFFF";
        gridColor[37] = "#ADD8E6";
        gridColor[38] = "#FFFFFF";
        gridColor[39] = "#ADD8E6";

        gridColor[40] = "#ADD8E6";
        gridColor[41] = "#FFFFFF";
        gridColor[42] = "#ADD8E6";
        gridColor[43] = "#FFFFFF";
        gridColor[44] = "#ADD8E6";
        gridColor[45] = "#FFFFFF";
        gridColor[46] = "#ADD8E6";
        gridColor[47] = "#FFFFFF";

        gridColor[48] = "#FFFFFF";
        gridColor[49] = "#ADD8E6";
        gridColor[50] = "#FFFFFF";
        gridColor[51] = "#ADD8E6";
        gridColor[52] = "#FFFFFF";
        gridColor[53] = "#ADD8E6";
        gridColor[54] = "#FFFFFF";
        gridColor[55] = "#ADD8E6";

        gridColor[56] = "#ADD8E6";
        gridColor[57] = "#FFFFFF";
        gridColor[58] = "#ADD8E6";
        gridColor[59] = "#FFFFFF";
        gridColor[60] = "#ADD8E6";
        gridColor[61] = "#FFFFFF";
        gridColor[62] = "#ADD8E6";
        gridColor[63] = "#FFFFFF";
    }
}
