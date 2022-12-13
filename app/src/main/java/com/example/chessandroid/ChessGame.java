/**
 * ChessGame class runs a single game of chess
 */
package com.example.chessandroid;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ChessGame {
    static Scanner scan = new Scanner(System.in);
    public Board board = new Board();
    private Board boardCopy = new Board();
    private Board undoBoard = new Board();
    public String turnColor = "w";
    private String input;
    private boolean validMove = false;

    private boolean promotion = false;
    private boolean castlingL = false;
    private boolean castlingR = false;
    private boolean castlingDone_WL = false;
    private boolean castlingDone_WR = false;
    private boolean castlingDone_BL = false;
    private boolean castlingDone_BR = false;
    private boolean resign = false;
    private boolean draw1 = false;
    private boolean draw2 = false;
    private boolean check = false;
    private boolean checkmate = false;
    private boolean winner = false;
    private boolean kingStillInCheck = false;
    ArrayList<String> moveList = new ArrayList<String>();

    /**
     * Takes turns for each player
     * When there is a winner, this method will stop, ending the program
     */
    public void checkEndgame() {
        //board.createBoard();
/*
        while(!winner) {
            //board.printBoard();
            turnColor = "w";
            if(check) {
                System.out.println("Check");
            }
            //System.out.print("White's move: ");
            //String n = MainActivity.selectMove();

            playChess();
            if(!winner) {
                //board.printBoard();
                turnColor = "b";
                if(check) {
                    System.out.println("Check");
                }
                //System.out.print("Black's move: ");
                playChess();
            }
        }
 */
        if(turnColor.equals("w") && checkmate) {
            printMoveList();
            System.out.println("Checkmate");
            System.out.println("White wins");
        }
        else if(turnColor.equals("b") && checkmate) {
            printMoveList();
            System.out.println("Checkmate");
            System.out.println("Black wins");
        }
        else if(turnColor.equals("w") && resign) { // white resigns, black wins
            printMoveList();
            System.out.println("Black wins");
        }
        else if(turnColor.equals("b") && resign) { // black resigns, white wins
            printMoveList();
            System.out.println("White wins");
        }
        else if(draw2) { // draw
            printMoveList();
        }
    }

    public void undoMove(){
        for(int i=1; i<9; i++) {
            for(int j=0; j<8; j++) {
                board.setPiece(undoBoard.getPiece(i, j), i, j);
            }
        }
        if (!moveList.isEmpty()) {
            moveList.remove(moveList.toArray().length-1);
        }
        // FIX THIS !!!
        if (turnColor.equals("w")){
            turnColor = "b";
        }else{
            turnColor = "w";
        }
    }

    public boolean isValidMove(){
        return validMove;
    }

    //public void setValidMove(boolean v){
        //validMove = v;
    //}

    /*
    public void AIMove(){
        System.out.println("AIMove");
        String randomInput;
        int move;
        for(int i=1; i<9; i++){
            for(int j=0; j<8; j++){
                if (turnColor.equals(board.getPiece(i,j).substring(0,1))){
                    move = random.nextInt(65);

                    //playChess(randomInput);
                    if(validMove){
                        break;
                    }
                }
            }
        }
    }

     */

    /**
     * This method takes in the input from the user
     * Determines what piece the user has selected and where to put it
     */
    public void playChess(String androidInput) {
        String pieceName;
        int col1=0, row1, col2=0, row2;
        String col1a, col2a;
        String promotionPiece = "x";
        input = androidInput;
        //System.out.println("CHESS INPUT: "+input);

        promotion = false;
        validMove = false;

        if(input.equals("draw?")){ // New
            draw2 = true;
        }
        if(draw2 && input.equals("draw")) {
            moveList.add(input);
            winner = true;
        }
        else if(draw2 && !input.equals("draw")) {
            //tryAgain();
        }
        else if(!draw2 && input.equals("resign")) {
            moveList.add(input);
            resign = true;
            winner = true;
        }
        else if(!draw2) {
            col1a = input.substring(0, 1);
            row1 = Integer.parseInt(input.substring(1, 2));
            col2a = input.substring(3, 4);
            row2 = Integer.parseInt(input.substring(4, 5));
            col1 = num2letter(col1a);
            col2 = num2letter(col2a);
            pieceName = board.getPiece(row1, col1);
            if(input.length()>10) {
                draw1 = true;
            }
            else if(input.length()>5) {
                promotionPiece = input.substring(6, 7);
            }
            else {
                promotionPiece = "x";
            }
            switch(pieceName.substring(1, 2)) {
                case "p": // Pawn
                    movePiece(pawn(row1, col1, row2, col2),
                            pieceName, row1, col1, row2, col2, promotionPiece);
                    //System.out.println("PAWN");
                    break;
                case "R": // Rook
                    movePiece(rook(row1, col1, row2, col2),
                            pieceName, row1, col1, row2, col2, promotionPiece);
                    //System.out.println("ROOK");
                    break;
                case "N": // Knight
                    movePiece(knight(row1, col1, row2, col2),
                            pieceName, row1, col1, row2, col2, promotionPiece);
                    //System.out.println("KNIGHT");
                    break;
                case "B": // Bishop
                    movePiece(bishop(row1, col1, row2, col2),
                            pieceName, row1, col1, row2, col2, promotionPiece);
                    //System.out.println("BISHOP");
                    break;
                case "Q": // Queen
                    movePiece(queen(row1, col1, row2, col2),
                            pieceName, row1, col1, row2, col2, promotionPiece);
                    //System.out.println("QUEEN");
                    break;
                case "K": // King
                    movePiece(king(row1, col1, row2, col2),
                            pieceName, row1, col1, row2, col2, promotionPiece);
                    //System.out.println("KING");
                    break;
            }
        }
    }
    /**
     * This method is a helper method
     * @return returns the other player's color
     */
    public String getOtherColor() {
        if(turnColor.equals("w")) {
            return "b";
        }
        else {
            return "w";
        }
    }
    /**
     * Ensures that the move selected by the user is valid
     * @param t true if the selected piece follows its rule sets, false if not
     * @param name String of the piece's name
     * @param r1 row of selected piece
     * @param c1 col of selected piece
     * @param r2 row of desired placement
     * @param c2 col of desired placement
     * @param pS name of piece of pawn to be promoted if applicable
     */
    public void movePiece(boolean t, String name, int r1, int c1, int r2, int c2, String pS) {
        //System.out.println("TURNCOLOR: "+turnColor);
        kingStillInCheck = false;
        if(t && board.getPiece(r1, c1).substring(0, 1).equals(turnColor) &&
                !(board.getPiece(r2, c2).substring(0, 1).equals(turnColor)) &&
                !board.getPiece(r2, c2).substring(1, 2).equals("K") &&
                (r1!=r2 || c1!=c2) &&
                (r1>0 && r1<=8) &&
                (r2>0 && r2<=8) &&
                (c1>=0 && c1<8) &&
                (c2>=0 && c2<8)) {
            // Set undoBoard from board before valid move is made
            for(int i=1; i<9; i++) {
                for(int j=0; j<8; j++) {
                    undoBoard.setPiece(board.getPiece(i, j), i, j);
                }
            }
            if(check) {
                for(int i=0; i<9; i++) {
                    for(int j=0; j<9; j++) {
                        boardCopy.setPiece(board.getPiece(i, j), i, j);
                    }
                }
                boardCopy.setPiece(turnColor+board.getPiece(r1, c1).substring(1,2), r2, c2);
                boardCopy.setPiece("  ", r1, c1);
                //System.out.println(turnColor+board.getPiece(r2, c2).substring(1,2));
                for(int r=1; r<9; r++) {
                    for(int c=0; c<8; c++) {
                        if(boardCopy.getPiece(r, c).substring(0, 2).equals(turnColor+"K")) {
                            if(isInCheck(boardCopy,getOtherColor(),r,c)) {
                                //tryAgain();
                                kingStillInCheck = true;
                                validMove = false;
                            }
                            else {
                                check = false;
                            }
                        }
                    }
                }
            }
            if(promotion && !kingStillInCheck) { // pawn promotion
                if(!pS.equals("x")) { // piece was given in input
                    board.setPiece(turnColor+pS, r2, c2);
                    board.setPiece("  ", r1, c1);
                }
                else { // no piece was given in input, so make it queen
                    board.setPiece(turnColor+"Q", r2, c2);
                    board.setPiece("  ", r1, c1);
                }
                promotion = false;
                kingCheck(getOtherColor(), turnColor);
                moveList.add(input);
                if(draw1) {
                    draw2 = true;
                }
            }
            else if(castlingL && !kingStillInCheck) {
                board.setPiece(turnColor+"R", r1, c1-1);
                board.setPiece(turnColor+"K", r1, c1-2);
                board.setPiece("  ", r1, c1);
                board.setPiece("  ", r1, c1-4);
                castlingL = false;
                kingCheck(getOtherColor(), turnColor);
                moveList.add(input);
                if(draw1) {
                    draw2 = true;
                }
            }
            else if(castlingR && !kingStillInCheck) {
                board.setPiece(turnColor+"R", r1, c1+1);
                board.setPiece(turnColor+"K", r1, c1+2);
                board.setPiece("  ", r1, c1);
                board.setPiece("  ", r1, c1+3);
                castlingR = false;
                kingCheck(getOtherColor(), turnColor);
                moveList.add(input);
                if(draw1) {
                    draw2 = true;
                }
            }
            else if(!kingStillInCheck){ // every other piece or no pawn promotion
                board.setPiece(name, r2, c2);
                board.setPiece("  ", r1, c1);
                kingCheck(getOtherColor(), turnColor); // checking if other king is in check
                moveList.add(input);
                if(draw1) {
                    draw2 = true;
                }
                /*
                IMPORTANT: ADD THIS TO ALL LEGAL MOVE SETS
                 */
                turnColor = getOtherColor();
                validMove = true;
            }
            kingStillInCheck = false;
            System.out.println();
        }
        else {
            //System.out.println("Illegal move, try again");
            //Toast.makeText(getApplicationContext(),"Illegal move, try again", Toast.LENGTH_SHORT).show();
            draw1 = false;
            validMove = false;
            //playChess(input, turnColor); // CHANGED
        }
    }
    /**
     * rule set for pawn piece
     * @param r1 row of selected piece
     * @param c1 col of selected piece
     * @param r2 row of desired placement
     * @param c2 col of desired placement
     * @return true if rules are followed
     */
    public boolean pawn(int r1, int c1, int r2, int c2) {
        if(turnColor.equals("w")) {
            if((r1==2 && r2>r1 && r2-r1==2 && c1==c2 && !isPiece(3,c1, board) && !isPiece(4,c1,board))) { // two moves up
                return true;
            }
            if(r2<=8) {
                if(!isPiece(r2,c2,board) && r2>r1 && r2-r1==1 && c1==c2) { // one move up
                    if(r2==8) {
                        promotion = true;
                    }
                    return true;
                }
                if(c1+1<=7 && c2>c1 && c2-c1==1) { // attack right
                    if(board.getPiece(r2, c2).substring(0, 1).equals("b") && r2>r1 && r2-r1==1) {
                        if(r2==8) {
                            promotion = true;
                        }
                        return true;
                    }
                }
                if(c1-1>=0 && c1>c2 && c1-c2==1) { // attack left
                    if(board.getPiece(r2, c2).substring(0, 1).equals("b") && r2>r1 && r2-r1==1) {
                        if(r2==8) {
                            promotion = true;
                        }
                        return true;
                    }
                }
            }
        }
        else {
            if((r1==7 && r1>r2 && r1-r2==2 && c1==c2 && !isPiece(6,c1,board) && !isPiece(5,c1,board))) { // two moves down
                return true;
            }
            if(r2>=1) {
                if(!isPiece(r2,c2,board) && r1>r2 && r1-r2==1 && c1==c2) { // one move down
                    if(r2==1) {
                        promotion = true;
                    }
                    return true;
                }
                if(c1+1<=7 && c2>c1 && c2-c1==1) { // attack right
                    if(board.getPiece(r2, c2).substring(0, 1).equals("w") && r1>r2 && r1-r2==1) {
                        if(r2==1) {
                            promotion = true;
                        }
                        return true;
                    }
                }
                if(c1-1>=0 && c1>c2 && c1-c2==1) { // attack left
                    if(board.getPiece(r2, c2).substring(0, 1).equals("w") && r1>r2 && r1-r2==1) {
                        if(r2==1) {
                            promotion = true;
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /**
     * rule set for rook piece
     * @param r1 row of selected piece
     * @param c1 col of selected piece
     * @param r2 row of desired placement
     * @param c2 col of desired placement
     * @return true if rules are followed
     */
    public boolean rook(int r1, int c1, int r2, int c2) {
        if(r1==r2) {
            if(c1 < c2) { // move right
                for(int c=c1+1; c<c2; c++) {
                    if(isPiece(r1,c,board)) {
                        return false;
                    }
                }
            }
            if(c1 > c2) { // move left
                for(int c=c1-1; c>c2; c--) {
                    if(isPiece(r1,c,board)) {
                        return false;
                    }
                }
            }
            if(turnColor.equals("w")) {
                if(r1==1 && c1==0) {
                    castlingDone_WL = true;
                }
                else if(r1==1 && c1==7) {
                    castlingDone_WR = true;
                }
            }
            else if(turnColor.equals("b")) {
                if(r1==8 && c1==0) {
                    castlingDone_BL = true;
                }
                else if(r1==8 && c1==7) {
                    castlingDone_BR = true;
                }
            }
            return true;
        }
        if(c1==c2) {
            if(r1<r2) {	// move up
                for(int r=r1+1; r<r2; r++) {
                    if(isPiece(r,c1,board)) {
                        return false;
                    }
                }
            }
            if(r1>r2) { // move down
                for(int r=r1-1; r>r2; r--) {
                    if(isPiece(r,c1,board)) {
                        return false;
                    }
                }
            }
            if(turnColor.equals("w")) {
                if(r1==1 && c1==0) {
                    castlingDone_WL = true;
                }
                else if(r1==1 && c1==7) {
                    castlingDone_WR = true;
                }
            }
            else if(turnColor.equals("b")) {
                if(r1==8 && c1==0) {
                    castlingDone_BL = true;
                }
                else if(r1==8 && c1==7) {
                    castlingDone_BR = true;
                }
            }
            return true;
        }
        return false;
    }
    /**
     * rule set for knight piece
     * @param r1 row of selected piece
     * @param c1 col of selected piece
     * @param r2 row of desired placement
     * @param c2 col of desired placement
     * @return true if rules are followed
     */
    public boolean knight(int r1, int c1, int r2, int c2) {
        if(		(r1+2==r2 && c1+1==c2) ||
                (r1+2==r2 && c1-1==c2) ||
                (r1-2==r2 && c1+1==c2) ||
                (r1-2==r2 && c1-1==c2) ||
                (c1+2==c2 && r1+1==r2) ||
                (c1+2==c2 && r1-1==r2) ||
                (c1-2==c2 && r1+1==r2) ||
                (c1-2==c2 && r1-1==r2)) {
            return true;

        }
        return false;
    }

    public boolean bishop(int r1, int c1, int r2, int c2) {
        if(r1<r2 && c2<c1 && (r2-r1 == c1-c2)) { // move up left
            for(int i=1; i<r2-r1; i++) {
                if(isPiece(r1+1,c1-1, board)) {
                    return false;
                }
            }
            return true;
        }
        if(r1<r2 && c1<c2 && (r2-r1 == c2-c1)) { // move up right
            for(int i=1; i<r2-r1; i++) {
                if(isPiece(r1+1,c1+1, board)) {
                    return false;
                }
            }
            return true;
        }
        if(r2<r1 && c2<c1 && (r1-r2 == c1-c2)) { // move down left
            for(int i=1; i<r1-r2; i++) {
                if(isPiece(r1-1,c1-1, board)) {
                    return false;
                }
            }
            return true;
        }
        if(r2<r1 && c1<c2 && (r1-r2 == c2-c1)) { // move down right
            for(int i=1; i<r1-r2; i++) {
                if(isPiece(r1-1,c1+1, board)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * rule set for queen piece
     * @param r1 row of selected piece
     * @param c1 col of selected piece
     * @param r2 row of desired placement
     * @param c2 col of desired placement
     * @return true if rules are followed
     */
    public boolean queen(int r1, int c1, int r2, int c2) {
        if(rook(r1, c1, r2, c2) || bishop(r1, c1, r2, c2)){
            return true;
        }
        return false;
    }

    /**
     * rule set for king piece
     * @param r1 row of selected piece
     * @param c1 col of selected piece
     * @param r2 row of desired placement
     * @param c2 col of desired placement
     * @return true if rules are followed
     */
    public boolean king(int r1, int c1, int r2, int c2) {
        if((r1<r2 && c1==c2 && r2-r1==1) ||
                (r2<r1 && c1==c2 && r1-r2==1) ||
                (c1<c2 && r1==r2 && c2-c1==1) ||
                (c2<c1 && r1==r2 && c1-c2==1) ||
                (r1<r2 && c2<c1 && r2-r1==1 && c1-c2==1) ||
                (r1<r2 && c1<c2 && r2-r1==1 && c2-c1==1) ||
                (r2<r1 && c2<c1 && r1-r2==1 && c1-c2==1) ||
                (r2<r1 && c1<c2 && r1-r2==1 && c2-c1==1)) {
            if(turnColor.equals("w")) {
                castlingDone_WL = true;
                castlingDone_WR = true;
            }
            else if(turnColor.equals("b")) {
                castlingDone_BL = true;
                castlingDone_BR = true;
            }
            if(isInCheck(board,getOtherColor(),r2,c2)) {
                check = false;
                return false;
            }
            return true;
        }
        else if(turnColor.equals("w")) { //white castling
            if(c1-c2==2 && r1==r2 && !castlingDone_WL) { // castling left
                if(isPiece(1,1, board) || isPiece(1,2, board) || isPiece(1,3, board)) {
                    return false;
                }
                else if(board.getPiece(1, 0).substring(0, 2).equals(turnColor+"R")) {
                    castlingL = true;
                    castlingDone_WL = true;
                    //
                    return true;
                }
            }
            else if(c2-c1==2 && r1==r2 && !castlingDone_WR){ // castling right
                if(isPiece(1,5, board) || isPiece(1,6, board)) {
                    return false;
                }
                else if(board.getPiece(1, 7).substring(0, 2).equals(turnColor+"R")) {
                    castlingR = true;
                    castlingDone_WR = true;
                    //
                    return true;
                }
            }
        }
        else if(turnColor.equals("b")) { // black castling
            if(c1-c2==2 && r1==r2 && !castlingDone_BL) { // castling left
                if(isPiece(8,1, board) || isPiece(8,2, board) || isPiece(8,3, board)) {
                    return false;
                }
                else if(board.getPiece(8, 0).substring(0, 2).equals(turnColor+"R")) {
                    castlingL = true;
                    castlingDone_BL = true;
                    //
                    return true;
                }
            }
            else if(c2-c1==2 && r1==r2 && !castlingDone_BR){ // castling right
                if(isPiece(8,5, board) || isPiece(8,6, board)) {
                    return false;
                }
                else if(board.getPiece(8, 7).substring(0, 2).equals(turnColor+"R")) {
                    castlingR = true;
                    castlingDone_BR = true;
                    //
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * finds the king piece and checks if it is in check
     * @param color color of king
     * @param otherColor color of pieces that would put king in check
     */
    public void kingCheck(String color, String otherColor) {
        for(int r=1; r<9; r++) {
            for(int c=0; c<8; c++) {
                if(board.getPiece(r, c).substring(0, 2).equals(color+"K")) {
                    if(isInCheck(board,otherColor,r,c)) {
                        check = true;
                        if(isInCheckmate(color, otherColor)) {
                            checkmate = true;
                            winner = true;
                        }
                    }
                }
            }
        }
    }
    /**
     * finds king and checks if its is in checkmate
     * @param color color of king
     * @param otherColor color of pieces that would put king in check
     * @return true if king is in checkmate, false if not
     */
    public boolean isInCheckmate(String color, String otherColor) {
        int sum = 0;
        int r = 0,c = 0;
        for(int i=1; i<9; i++) {
            for(int j=0; j<8; j++) {
                if(board.getPiece(i, j).substring(0, 2).equals(color+"K")) {
                    r = i;
                    c = j;
                }
            }
        }
        if(r<8 && !board.getPiece(r+1, c).subSequence(0, 1).equals(color)) { // up
            if(isInCheck(board,otherColor,r+1,c)) {
                sum++;
            }
        }
        else if(r==8) {
            sum++;
        }
        else if(board.getPiece(r+1, c).subSequence(0, 1).equals(color)) {
            sum++;
        }
        if(r>1 && !board.getPiece(r-1, c).subSequence(0, 1).equals(color)) { // down
            if(isInCheck(board,otherColor,r-1,c)) {
                sum++;
            }
        }
        else if(r==1) {
            sum++;
        }
        else if(board.getPiece(r-1, c).subSequence(0, 1).equals(color)) {
            sum++;
        }
        if(c>0 && !board.getPiece(r, c-1).subSequence(0, 1).equals(color)) { // left
            if(isInCheck(board,otherColor,r,c-1)) {
                sum++;
            }
        }
        else if(c==0) {
            sum++;
        }
        else if(board.getPiece(r, c-1).subSequence(0, 1).equals(color)) {
            sum++;
        }
        if(c<7 && !board.getPiece(r, c+1).subSequence(0, 1).equals(color)) { // right
            if(isInCheck(board,otherColor,r,c+1)) {
                sum++;
            }
        }
        else if(c==7) {
            sum++;
        }
        else if(board.getPiece(r, c+1).subSequence(0, 1).equals(color)) {
            sum++;
        }
        if(r<8 && c>=0 && !board.getPiece(r+1, c-1).subSequence(0, 1).equals(color)) { // up left
            if(isInCheck(board,otherColor,r+1,c-1)) {
                sum++;
            }
        }
        else if(r==8 || c==0) {
            sum++;
        }
        else if(board.getPiece(r+1, c-1).subSequence(0, 1).equals(color)) {
            sum++;
        }
        if(r<8 && c<=7 && !board.getPiece(r+1, c+1).subSequence(0, 1).equals(color)) { // up right
            if(isInCheck(board,otherColor,r+1,c+1)) {
                sum++;
            }
        }
        else if(r==8 || c==7) {
            sum++;
        }
        else if(board.getPiece(r+1, c+1).subSequence(0, 1).equals(color)) {
            sum++;
        }
        if(r>1 && c>=0 && !board.getPiece(r-1, c-1).subSequence(0, 1).equals(color)) { // down left
            if(isInCheck(board,otherColor,r-1,c-1)) {
                sum++;
            }
        }
        else if(r==1 || c==0) {
            sum++;
        }
        else if(board.getPiece(r-1, c-1).subSequence(0, 1).equals(color)) {
            sum++;
        }
        if(r>1 && c<=7 && !board.getPiece(r-1, c+1).subSequence(0, 1).equals(color)) { // down right
            if(isInCheck(board,otherColor,r-1,c+1)) {
                sum++;
            }
        }
        else if(r==1 || c==7) {
            sum++;
        }
        else if(board.getPiece(r-1, c+1).subSequence(0, 1).equals(color)) {
            sum++;
        }
        if(sum==8) {
            return true;
        }
        return false;
    }
    /**
     * determines if selected piece is in check
     * @param b board to use
     * @param color color to check other pieces with
     * @param r row of piece to be checked
     * @param c col of piece to be checked
     * @return true if selected piece is in check, false if not
     */
    public boolean isInCheck(Board b, String color, int r, int c) { //PRINT IN CHECK / CHECKMATE means winner
        // rook check
        if(r!=8) {
            for(int i = r+1; i<9; i++) {
                if(b.getPiece(i, c).substring(0, 2).equals(color+"R") ||
                        b.getPiece(i, c).substring(0, 2).equals(color+"Q")) {
                    return true;
                }
                else if(isPiece(i,c, b)) {
                    break;
                }
            }
        }
        if(r!=1) {
            for(int i = r-1; i>0; i--) {
                if(b.getPiece(i, c).substring(0, 2).equals(color+"R") ||
                        b.getPiece(i, c).substring(0, 2).equals(color+"Q")) {
                    return true;
                }
                else if(isPiece(i,c, b)) {
                    break;
                }
            }
        }
        if(c!=7) {
            for(int i = c+1; i<8; i++) {
                if(b.getPiece(r, i).substring(0, 2).equals(color+"R") ||
                        b.getPiece(r, i).substring(0, 2).equals(color+"Q")) {
                    return true;
                }
                else if(isPiece(r,i,b)) {
                    break;
                }
            }
        }
        if(c!=0) {
            for(int i =c-1; i>=0; i--) {
                if(b.getPiece(r, i).substring(0, 2).equals(color+"R") ||
                        b.getPiece(r, i).substring(0, 2).equals(color+"Q")) {
                    return true;
                }
                else if(isPiece(r,i,b)) {
                    break;
                }
            }
        }
        // bishop check
        if(r+1<=8 && c-1>=0) { // move up left
            for(int i=1; r+i<=8 && c-i>=0; i++) {
                if(b.getPiece(r+i,c-i).substring(0,2).equals(color+"B") ||
                        b.getPiece(r+i, c-i).substring(0, 2).equals(color+"Q")) {
                    return true;
                }
                else if(isPiece(r+1,c-1, b)) {
                    break;
                }
            }
        }
        if(r+1<=8 && c+1<=7) { // move up right
            for(int i=1; r+i<=8 && c+i<=7; i++) {
                if(b.getPiece(r+i,c+i).substring(0,2).equals(color+"B") ||
                        b.getPiece(r+i, c+i).substring(0, 2).equals(color+"Q")) {
                    return true;
                }
                else if(isPiece(r+1,c+1, b)) {
                    break;
                }
            }
        }
        if(r-1>=1 && c-1>=0) { // move down left
            for(int i=1; r-i>=1 && c-i>=0; i++) {
                if(b.getPiece(r-i,c-i).substring(0,2).equals(color+"B") ||
                        b.getPiece(r-i, c-i).substring(0, 2).equals(color+"Q")) {
                    return true;
                }
                else if(isPiece(r-1,c-1, b)) {
                    break;
                }
            }
        }
        if(r-1>=1 && c+1<=7) { // move down right
            for(int i=1; r-i>=1 && c+i<=7; i++) {
                if(b.getPiece(r-i,c+i).substring(0,2).equals(color+"B") ||
                        b.getPiece(r-i, c+i).substring(0, 2).equals(color+"Q")) {
                    return true;
                }
                else if(isPiece(r-1,c+1, b)) {
                    break;
                }
            }
        }
        // knight check
        if(r+2<=8 && c+1<=7) {
            if(b.getPiece(r+2, c+1).subSequence(0, 2).equals(color+"N")) {
                return true;
            }
        }
        if(r+2<=8 && c-1>=0) {
            if(b.getPiece(r+2, c-1).subSequence(0, 2).equals(color+"N")) {
                return true;
            }
        }
        if(r-2>=1 && c+1<=7) {
            if(b.getPiece(r-2, c+1).subSequence(0, 2).equals(color+"N")) {
                return true;
            }
        }
        if(r-2>=1 && c-1>=0) {
            if(b.getPiece(r-2, c-1).subSequence(0, 2).equals(color+"N")) {
                return true;
            }
        }
        if(r+1<=8 && c+2<=7) {
            if(b.getPiece(r+1, c+2).subSequence(0, 2).equals(color+"N")) {
                return true;
            }
        }
        if(r-1>=1 && c+2<=7) {
            if(b.getPiece(r-1, c+2).subSequence(0, 2).equals(color+"N")) {
                return true;
            }
        }
        if(r+1<=8 && c-2>=0) {
            if(b.getPiece(r+1, c-2).subSequence(0, 2).equals(color+"N")) {
                return true;
            }
        }
        if(r-1>=1 && c-2>=0) {
            if(b.getPiece(r-1, c-2).subSequence(0, 2).equals(color+"N")) {
                return true;
            }
        }
        // pawn check
        if(turnColor.equals("w")) {
            if(r+1<=8 && c+1<=7) {
                if(b.getPiece(r+1, c+1).subSequence(0, 2).equals(color+"p")) {
                    return true;
                }
            }
            if(r+1<=8 && c-1>=0) {
                if(b.getPiece(r+1, c-1).subSequence(0, 2).equals(color+"p")) {
                    return true;
                }
            }
            return false;
        }
        else {
            if(r-1>=1 && c+1<=7) {
                if(b.getPiece(r-1, c+1).subSequence(0, 2).equals(color+"p")) {
                    return true;
                }
            }
            if(r-1>=1 && c-1>=0) {
                if(b.getPiece(r-1, c-1).subSequence(0, 2).equals(color+"p")) {
                    return true;
                }
            }
            return false;
        }

    }
    /**
     * finds if selected cell contains a piece
     * @param r row of selected cell
     * @param c col of selected cell
     * @return true if piece is found, false if not
     */
    public boolean isPiece(int r, int c, Board b) {
        if((b.getPiece(r, c)!= "  ") && (b.getPiece(r, c)!="##")) {
            return true;
        }
        return false;
    }
    /**
     * Illegal move is found, repeats playChess();
     */
    public void tryAgain() {
        //System.out.println("Illegal move, try again");
        if(turnColor.equals("w")) {
            //System.out.print("White's move: ");
        }
        else {
            //System.out.print("Black's move: ");
        }
        System.out.println("TRY AGAIN METHOD");
        //playChess(input, turnColor); // CHANGED
    }
    /**
     * prints all moves made
     */
    public void printMoveList() {
        /*
        When the game is over, print to text file if user chooses
         */
        try {
            FileWriter w = new FileWriter("com.example.chessandroid/recordedGameLogs");
            //for(String str: moveList){
            //    w.write(str);
            //}
            w.write("WRITING IN THIS FILE");
            System.out.println("WROTE IN THE FILE");
            w.close();
        } catch (IOException e) {
            System.out.println("NOT WRITE");
            e.printStackTrace();
        }
    }
    /**
     * takes in a letter and returns its corresponding int
     * @param s String
     * @return int
     */
    public int num2letter(String s) {
        switch(s) {
            case "a":
                return 0;
            case "b":
                return 1;
            case "c":
                return 2;
            case "d":
                return 3;
            case "e":
                return 4;
            case "f":
                return 5;
            case "g":
                return 6;
            case "h":
                return 7;
            default: return 10;
        }
    }

}
