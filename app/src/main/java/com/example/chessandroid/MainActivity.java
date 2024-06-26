package com.example.chessandroid;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity{
        //implements RecyclerBoardAdapter.OnBoardListener {

    private ChessGame chessGame = new ChessGame();
    private String initial_input;
    private String final_input;
    private ArrayList<String> inputStack = new ArrayList<>();
    public String turnColor = "w";

    private TextView pieceNameText;
    private TextView chesstext;
    private Button undoButton;
    private Button AIButton;
    private Button drawButton;
    private Button resignButton;
    private Button recordedGamesButton;
    private Button restartButton;

    private ArrayList<Piece> pieceOrder;
    private RecyclerView recyclerView;

    BoardAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;


    private boolean undo = false;
    private boolean gameOver = false;

    String[] gridColor = new String[65];

    ArrayList<RecordedGameModel> records = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board);

        //recyclerView = findViewById(R.id.recyclerboard);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) GridView gridView = findViewById(R.id.chessGridView);
        pieceOrder = new ArrayList<>();
        chessGame.board.createBoard();
        transferBoards();
        //setAdapter();
        colorBoard();
        adapter = new BoardAdapter(MainActivity.this, pieceOrder, gridColor);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!gameOver){
                    if(inputStack.isEmpty()){ // first click
                        initial_input = getCoordinate(findRow(position),findCol(position));
                        inputStack.add(initial_input);
                        pieceNameText.setText(initial_input);
                    }else{ // second click
                        initial_input = getCoordinate(findRow(position),findCol(position));
                        final_input = inputStack.get(0)+" "+initial_input;
                        inputStack.clear();
                        pieceNameText.setText(final_input);
                        chessGame.playChess(final_input);
                        pieceOrder.clear();
                        transferBoards();
                        adapter.notifyDataSetChanged();
                        chesstext = findViewById(R.id.chessText);
                        turnColor = chessGame.getOtherColor();
                        if(turnColor.equals("b")){
                            chesstext.setText("White's Move");
                        }else{
                            chesstext.setText("Black's Move");
                        }
                        chessGame.checkEndgame();
                        undo = false;
                    }
                }
            }
        });

        pieceNameText = findViewById(R.id.pieceNameText);
        chesstext = findViewById(R.id.chessText);
        undoButton = (Button) findViewById(R.id.undoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!undo && !gameOver){
                    chessGame.undoMove();
                    pieceOrder.clear();
                    transferBoards();
                    adapter.notifyDataSetChanged();
                    turnColor = chessGame.getOtherColor();
                    if(turnColor.equals("b")){
                        chesstext.setText("White's Move");
                    }else{
                        chesstext.setText("Black's Move");
                    }
                    undo = true;
                    pieceNameText.setText("");
                }
            }
        });

        AIButton = (Button) findViewById(R.id.AIButton);
        AIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    aiMove();
//                for (int i = 0; i < 20; i++) {
//                }
            }
        });

        drawButton = findViewById(R.id.drawButton);
        drawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!gameOver){
                    chessGame.playChess("draw?");
                    chesstext = findViewById(R.id.chessText);
                    chesstext.setText("Draw");
                    chessGame.checkEndgame();
                    gameOver = true;
                    pieceNameText.setText("");
                    showStorageDialog();
                }
            }
        });

        resignButton = findViewById(R.id.resignButton);
        resignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!gameOver){
                    chessGame.playChess("resign");
                    chesstext = findViewById(R.id.chessText);
                    turnColor = chessGame.getOtherColor();
                    if(turnColor.equals("b")){
                        chesstext.setText("White Resigns, Black Wins");
                    }else{
                        chesstext.setText("Black Resigns, White Wins");
                    }
                    chessGame.checkEndgame();
                    gameOver = true;
                    pieceNameText.setText("");
                    showStorageDialog();
                }
            }
        });

        recordedGamesButton = findViewById(R.id.recordedGamesButton);
        recordedGamesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRecordedGames();
            }
        });

        restartButton = findViewById(R.id.restartButton);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pieceOrder.clear();
                inputStack.clear();
                chessGame = new ChessGame();
                chessGame.board.createBoard();
                transferBoards();
                gameOver = false;
                colorBoard();
                adapter.notifyDataSetChanged();
                pieceNameText.setText("");
                chesstext.setText("White's Move");
            }
        });
    }

    private void showStorageDialog() {
        final EditText inputServer = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game name").setView(inputServer)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String t = inputServer.getText().toString();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
                Date date = new Date(System.currentTimeMillis());
                String cur = simpleDateFormat.format(date);
                RecordedGameModel model = new RecordedGameModel(t, cur, chessGame.moveList);
                records.add(model);
                records.sort(Comparator.comparing(RecordedGameModel::getDateAndTitle));
                Toast.makeText(getApplicationContext(), "saved",
                        Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    private void openRecordedGames() {
        Intent intent = new Intent(this, RecordedGames.class);
        intent.putExtra("records", records);
        startActivity(intent);
    }

    private void setAdapter(){
        //adapter = new RecyclerBoardAdapter(piece_Order, this);
        //mLayoutManager = new GridLayoutManager(getApplicationContext(), 8);
        //recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.setBackgroundColor(Color.parseColor("#325ea8"));
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.setAdapter(adapter);
    }

    public void aiMove(){
        if (!gameOver) {
            String randomInput;
            Random random = new Random();
            int r1, r2;
            r1 = random.nextInt(64);
            r2 = random.nextInt(64);
            randomInput = getCoordinate(findRow(r1),findCol(r1))+" "+getCoordinate(findRow(r2),findCol(r2));
            chessGame.playChess(randomInput);

            while (!chessGame.isValidMove()) {
                r1 = random.nextInt(64);
                r2 = random.nextInt(64);
                randomInput = getCoordinate(findRow(r1),findCol(r1))+" "+getCoordinate(findRow(r2),findCol(r2));
                chessGame.playChess(randomInput);
            }

            pieceOrder.clear();
            transferBoards();
            adapter.notifyDataSetChanged();
            chesstext = findViewById(R.id.chessText);
            turnColor = chessGame.getOtherColor();
            if(turnColor.equals("b")){
                chesstext.setText("White's Move");
            }else{
                chesstext.setText("Black's Move");
            }
            pieceNameText.setText(randomInput);
            undo = false;
            chessGame.checkEndgame();
        }
    }

    public String getCoordinate(int r, int c){
        String coordinate;
        if(c==0){
            coordinate = "a"+r;
        }else if(c==1){
            coordinate = "b"+r;
        }else if(c==2){
            coordinate = "c"+r;
        }else if(c==3){
            coordinate = "d"+r;
        }else if(c==4){
            coordinate = "e"+r;
        }else if(c==5){
            coordinate = "f"+r;
        }else if(c==6){
            coordinate = "g"+r;
        }else if(c==7){
            coordinate = "h"+r;
        }
        else{
            coordinate = "z9";
        }
        return coordinate;
    }

    public int findRow(int n){
        if(n>=0 && n<=7){
            return 8;
        }else if(n>=8 && n<=15){
            return 7;
        }else if(n>=16 && n<=23){
            return 6;
        }else if(n>=24 && n<=31){
            return 5;
        }else if(n>=32 && n<=39){
            return 4;
        }else if(n>=40 && n<=47){
            return 3;
        }else if(n>=48 && n<=55){
            return 2;
        }else if(n>=56 && n<=63){
            return 1;
        }
        return 0;
    }

    public int findCol(int n){
        if(n==0||n==8||n==16||n==24||n==32||n==40||n==48||n==56){
            return 0;
        }else if(n==1||n==9||n==17||n==25||n==33||n==41||n==49||n==57){
            return 1;
        }else if(n==2||n==10||n==18||n==26||n==34||n==42||n==50||n==58){
            return 2;
        }else if(n==3||n==11||n==19||n==27||n==35||n==43||n==51||n==59){
            return 3;
        }else if(n==4||n==12||n==20||n==28||n==36||n==44||n==52||n==60){
            return 4;
        }else if(n==5||n==13||n==21||n==29||n==37||n==45||n==53||n==61){
            return 5;
        }else if(n==6||n==14||n==22||n==30||n==38||n==46||n==54||n==62){
            return 6;
        }else if(n==7||n==15||n==23||n==31||n==39||n==47||n==55||n==63){
            return 7;
        }
        return 0;
    }

    public void transferBoards() {
        Board gameBoard = chessGame.board;
        int k = 0;
        for (int i = 8; i > 0; i--) { // Starts at bottom of the double array
            for (int j = 0; j < 8; j++) {
                String val = gameBoard.getPiece(i, j);
                if (val.equals("wp")) {
                    pieceOrder.add(new Piece(R.drawable.white_pawn));
                } else if (val.equals("wR")) {
                    pieceOrder.add(new Piece(R.drawable.white_rook));
                } else if (val.equals("wN")) {
                    pieceOrder.add(new Piece(R.drawable.white_knight));
                } else if (val.equals("wB")) {
                    pieceOrder.add(new Piece(R.drawable.white_bishop));
                } else if (val.equals("wQ")) {
                    pieceOrder.add(new Piece(R.drawable.white_queen));
                } else if (val.equals("wK")) {
                    pieceOrder.add(new Piece(R.drawable.white_king));
                } else if (val.equals("bp")) {
                    pieceOrder.add(new Piece(R.drawable.black_pawn));
                } else if (val.equals("bR")) {
                    pieceOrder.add(new Piece(R.drawable.black_rook));
                } else if (val.equals("bN")) {
                    pieceOrder.add(new Piece(R.drawable.black_knight));
                } else if (val.equals("bB")) {
                    pieceOrder.add(new Piece(R.drawable.black_bishop));
                } else if (val.equals("bQ")) {
                    pieceOrder.add(new Piece(R.drawable.black_queen));
                } else if (val.equals("bK")) {
                    pieceOrder.add(new Piece(R.drawable.black_king));
                } else {
                    pieceOrder.add(new Piece(R.drawable.blank_square));
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
/*
    @Override
    public void onBoardClick(int position) {
        if(!gameOver){
            pieceNameText = findViewById(R.id.pieceNameText);
            if(inputStack.isEmpty()){ // first click
                initial_input = getCoordinate(findRow(position),findCol(position));
                inputStack.add(initial_input);
                pieceNameText.setText(initial_input);
            }else{ // second click
                initial_input = getCoordinate(findRow(position),findCol(position));
                final_input = inputStack.get(0)+" "+initial_input;
                inputStack.clear();
                pieceNameText.setText(final_input.substring(3,5));
                chessGame.playChess(final_input);
                piece_Order.clear();
                transferBoards();
                adapter.notifyDataSetChanged();
                chesstext = findViewById(R.id.chessText);
                turnColor = chessGame.getOtherColor();
                if(turnColor.equals("b")){
                    chesstext.setText("White's Move");
                }else{
                    chesstext.setText("Black's Move");
                }
                chessGame.checkEndgame();
                undo = false;
            }
        }

    }
 */

}