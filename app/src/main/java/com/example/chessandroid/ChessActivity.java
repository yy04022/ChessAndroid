package com.example.chessandroid;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChessActivity{
    private ArrayList<Piece> piece_Order;
    private RecyclerView recyclerView;

    //private ChessGame chessGame = new ChessGame();
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board);

        recyclerView = findViewById(R.id.recyclerboard);
        piece_Order = new ArrayList<>();
        //chessGame.board.createBoard();
        piece_Order.add(new Piece("HEY"));
        piece_Order.add(new Piece("YO"));
        //piece_Order.add(new Piece(R.drawable.black_bishop));
        //transferBoards();
        setAdapter();
    }

    private void setAdapter(){
        RecyclerBoardAdapter adapter = new RecyclerBoardAdapter(piece_Order);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 8);
        //recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

 */

    public void transferBoards() {
        /*
        Board gameBoard = chessGame.board;
        int k = 0;
        for (int i = 8; i > 0; i--) { // Starts at bottom of the double array
            for (int j = 0; j < 8; j++) {
                System.out.println("PRINTINGFSVSYUDGUBBHJ");
                String val = gameBoard.getPiece(i, j);
                if (val.equals("wp")) {
                    piece_Order.add(1);
                    //piece_Order.add(R.drawable.white_pawn);
                } else if (val.equals("wR")) {
                    piece_Order.add(R.drawable.white_rook);
                } else if (val.equals("wN")) {
                    piece_Order.add(R.drawable.white_knight);
                } else if (val.equals("wB")) {
                    piece_Order.add(R.drawable.white_bishop);
                } else if (val.equals("wQ")) {
                    piece_Order.add(R.drawable.white_queen);
                } else if (val.equals("wK")) {
                    piece_Order.add(R.drawable.white_king);
                } else if (val.equals("bp")) {
                    piece_Order.add(R.drawable.black_pawn);
                } else if (val.equals("bR")) {
                    piece_Order.add(R.drawable.black_rook);
                } else if (val.equals("bN")) {
                    piece_Order.add(R.drawable.black_knight);
                } else if (val.equals("bB")) {
                    piece_Order.add(R.drawable.black_bishop);
                } else if (val.equals("bQ")) {
                    piece_Order.add(R.drawable.black_queen);
                } else if (val.equals("bK")) {
                    piece_Order.add(R.drawable.black_king);
                } else {
                    piece_Order.add(R.drawable.blank_square);
                }
                k++;
            }
        }

         */
    }
}