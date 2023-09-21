package cpsc2150.extendedConnectX;

import java.util.*;

public class GameBoard extends AbsGameBoard {
    /**
     * @invariant: row = #row && column = #column && board = #row x #column 2-dimensional array of char
     *            && numberOfTokensToWin = #numberOfTokensToWin
     *
     * @correspondence: self = board[MAX_ROW][MAX_COLUMN]
     */
    private int row;
    private int column;
    private int numberOfTokensToWin;

    public GameBoard(int newRow, int newColumn, int numToWin){
        numberOfTokensToWin = numToWin;
        row = newRow;
        column = newColumn;
        board = new char[row][column];
        // fills each grid of board with an empty space
        for (int i=row-1; i>=0; i--)
            for (int j=column-1; j>=0; j--)
                board[i][j] = ' ';
    }

    public int getNumRows(){ return row; }

    public int getNumColumns(){ return column; }

    public int getNumToWin(){ return numberOfTokensToWin; }

    public void placeToken(char token, int columnPosition){
// iterates through GameBoard starting at bottom to find empty row
        for (int i=0; i<row; i++){
            if (board[i][columnPosition]==' '){
                // places token into empty space
                board[i][columnPosition] = token;
                break;
            }
        }
    }

    public char whatsAtPos(BoardPosition pos){
       return board[pos.getRow()][pos.getColumn()];
    }
}
