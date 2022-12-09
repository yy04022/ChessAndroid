/**
 * Board class makes the board and holds all of the values in each cell
 */

package com.example.chessandroid;

public class Board {

    private String[][] board = new String[9][9];
    /**
     * gets the piece on the selected cell
     * @param r row of selected cell
     * @param c col of selected cell
     * @return String of the selected cell
     */
    public String getPiece(int r, int c) {
        return this.board[r][c];
    }
    /**
     * sets the piece for the selected cell
     * @param p String name of piece
     * @param r row of selectd cell
     * @param c col of selected cell
     */
    public void setPiece(String p, int r, int c) {
        this.board[r][c] = p;
    }
    /**
     * creates the board for the game
     */
    public void createBoard() {
        board[8][0] = "bR";
        board[8][1] = "bN";
        board[8][2] = "bB";
        board[8][3] = "bQ";
        board[8][4] = "bK";
        board[8][5] = "bB";
        board[8][6] = "bN";
        board[8][7] = "bR";
        board[8][8] = "8";

        board[7][0] = "bp";
        board[7][1] = "bp";
        board[7][2] = "bp";
        board[7][3] = "bp";
        board[7][4] = "bp";
        board[7][5] = "bp";
        board[7][6] = "bp";
        board[7][7] = "bp";
        board[7][8] = "7";

        board[6][0] = "  ";
        board[6][1] = "##";
        board[6][2] = "  ";
        board[6][3] = "##";
        board[6][4] = "  ";
        board[6][5] = "##";
        board[6][6] = "  ";
        board[6][7] = "##";
        board[6][8] = "6";

        board[5][0] = "##";
        board[5][1] = "  ";
        board[5][2] = "##";
        board[5][3] = "  ";
        board[5][4] = "##";
        board[5][5] = "  ";
        board[5][6] = "##";
        board[5][7] = "  ";
        board[5][8] = "5";

        board[4][0] = "  ";
        board[4][1] = "##";
        board[4][2] = "  ";
        board[4][3] = "##";
        board[4][4] = "  ";
        board[4][5] = "##";
        board[4][6] = "  ";
        board[4][7] = "##";
        board[4][8] = "4";

        board[3][0] = "##";
        board[3][1] = "  ";
        board[3][2] = "##";
        board[3][3] = "  ";
        board[3][4] = "##";
        board[3][5] = "  ";
        board[3][6] = "##";
        board[3][7] = "  ";
        board[3][8] = "3";

        board[2][0] = "wp";
        board[2][1] = "wp";
        board[2][2] = "wp";
        board[2][3] = "wp";
        board[2][4] = "wp";
        board[2][5] = "wp";
        board[2][6] = "wp";
        board[2][7] = "wp";
        board[2][8] = "2";

        board[1][0] = "wR";
        board[1][1] = "wN";
        board[1][2] = "wB";
        board[1][3] = "wQ";
        board[1][4] = "wK";
        board[1][5] = "wB";
        board[1][6] = "wN";
        board[1][7] = "wR";
        board[1][8] = "1";

        board[0][0] = " a";
        board[0][1] = " b";
        board[0][2] = " c";
        board[0][3] = " d";
        board[0][4] = " e";
        board[0][5] = " f";
        board[0][6] = " g";
        board[0][7] = " h";
        board[0][8] = " ";
    }


    /**
     * prints the board of the game
     */
    public void printBoard() {
        //try (BufferedWriter w = new BufferedWriter(new FileWriter("com.example.androidchess/pieces"))) {
        int z = 1;
        for (int i = 8; i >= 0; i--) {
            for (int j = 0; j < 9; j++) {
                if (i > 0 && z % 2 == 0 && board[i][j].equals("  ")) {
                    //System.out.print("## ");
                    //w.write("## ");
                } else {
                    //System.out.print(board[i][j] + " ");
                    //w.write(board[i][j]+" ");
                }
                z++;
            }
            //System.out.println();
            //w.newLine();
        }
        //System.out.println();
        //w.newLine();
        //w.close();
        //}catch(IOException e){
        //    e.printStackTrace();
        //}
    }

}
