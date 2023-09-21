package cpsc2150.extendedConnectX;

/**
 * Holds information about the GameBoard
 *
 * Defines: Player: #players
 *          Tokens: #Tokens
 *          Win: Marks the end of a game
 *          Tie: All grids completely filled with no winner
 *          Position: <r,c> location of where a token was dropped in c and stopped in r
 *
 * Initialization Ensures: a 2 -dimensional GameBoard with position [0][0] on the bottom left of the board
 *                         and user defined specification of GameBoard size
 *
 * Constraints: 2 < players <= 10                                                                 &&
 *              2 < tokens <= 10                                                               &&
 *              tokens are char
 *              3 <= Number of tokens to win <= row <= column <= 25
 *              Win = user defined number of tokens in a row horizontally, vertically, or diagonally    &&
 *              Win < row && win < column
 *              Tie: No empty spaces in grid and no winner
 *              3 <= row <= MAX_ROW
 *              3 <= column <= MAX_COLUMN
 *
 */
public interface IGameBoard {
    /**
     * GUI Variables
     */
    public static int MAX_SIZE = 30;
    public static int MIN_SIZE = 3;
    public static int MIN_TO_WIN = 3;
    public static int BOARD_CUT_OFF = 100;

    /**
     * This method returns the number of rows in the GameBoard
     *
     * @return: the number of rows in the GameBoard
     *
     * @post: getNumRows = rows
     */
    public int getNumRows();

    /**
     * This method returns the number of columns in the GameBoard
     *
     * @return number of columns in the GameBoard
     *
     * @post: getNumColumns = columns
     */
    public int getNumColumns();

    /**
     * This method returns the number of tokens in a row needed to win the game
     *
     * @return number of tokens in row needed to win
     *
     * @post: getNumToWin = numberOfTokensToWin
     */
    public int getNumToWin();

    /**
     * This method checks if columnPosition is a valid selection for a token
     *
     * @param columnPosition: the column where the token is to be placed
     *
     * @return true if column is able to accept another token, false if otherwise
     *
     * @pre: columnPosition <= column
     *
     * @post: checkIfFree = true if token placed in [r,columnPosition] is empty
     */
    default public boolean checkIfFree(int columnPosition){
        BoardPosition pos = new BoardPosition(getNumRows()-1,columnPosition);
        if (pos.getRow()+1 >= getNumRows() && whatsAtPos(pos) != ' ') return false;
        if (whatsAtPos(pos)==' ') return true;
        else return false;
    }

    /**
     * This method checks if the last token placed is a win
     *
     * @param columnPosition: the column where the token was placed
     *
     * @return true if last token placed in columnPosition resulted in the player winning the game, false if otherwise
     *
     * @pre: columnPosition <= column
     *
     * @post: checkForWin = true if token placed in [r,columnPosition] is the 5th same token in a row
     */
    default public boolean checkForWin(int columnPosition, char player){
        BoardPosition newPos = null;
// loops through column to find correct row that token was dropped into
        for (int r = getNumRows()-1; r >= 0; r--){
            BoardPosition pos = new BoardPosition(r,columnPosition);
// if grid is not blank, it indicates correct row where token was placed
            if (isPlayerAtPos(pos,player)){
                newPos = new BoardPosition(r,columnPosition);
                break;
            }
        }
        return (checkDiagWin(newPos,player) || checkHorizWin(newPos, player) || checkVertWin(newPos, player));
    }

    /**
     * This method checks for a tie
     *
     * @return true if GameBoard resulted in a tie, false otherwise
     *
     * @post: checkTie = true if all GameBoard grids not empty and no winner
     */
    default public boolean checkTie(){
        int counter=0;
        for (int r=getNumRows()-1; r>=0; r--) {
            for (int c=0; c<getNumColumns(); c++) {
                BoardPosition pos = new BoardPosition(r, c);
                if (whatsAtPos(pos) != ' ') counter++;
            }
        }
        if (counter==getNumRows()*getNumColumns()) return true;
        else return false;
    }

    /**
     * This method places a token onto the GameBoard
     *
     * @param token: the symbol for a player [X or O]
     * @param columnPosition: the column where the token is to be placed
     *
     * @pre: columnPosition <= column && token = X or O
     *
     * @post: GameBoard = #GameBoard except token placed in [r,columnPosition]
     */
    public void placeToken(char token, int columnPosition);

    /**
     * This method checks to see if the last token placed resulted in a horizontal win
     *
     * @param pos: the position where a token was placed
     * @param player: the symbol for a player [X or O]
     *
     * @return true if the last token placed resulted in a horizontal win, false otherwise
     *
     * @post: checkHorizWin = true if pos results in 5 tokens in a row && GameBoard = #GameBoard
     */
    default public boolean checkHorizWin(BoardPosition pos, char player){
        int counter;
        int offset; // added to the column to get neighboring tokens in the same row
        int r = pos.getRow();
        int c = pos.getColumn();
        counter = 0; // counter for num of tokens found in a row
// checks moves right
        offset = 0;
        while (counter < getNumToWin()) {
            // exceeding bounds
            if (c+offset >= getNumColumns()) break;
            // new board position created to traverse to the right to find matching tokens
            BoardPosition posOne = new BoardPosition(r, c + offset);
            if (isPlayerAtPos(posOne, player) && c+offset < getNumColumns() && counter != getNumToWin()) {
                counter++;
                offset++;
            }
            if (!isPlayerAtPos(posOne, player)) break;
            if (counter == getNumToWin()) return true;
        }
// check moves left
        offset = 1;
        while (counter < getNumToWin()) {
            // exceeding bounds
            if (c-offset < 0) break;
            // new board position created to traverse to the left to find matching tokens
            BoardPosition posTwo = new BoardPosition(r, c - offset);
            if (isPlayerAtPos(posTwo, player) && c-offset >= 0 && counter != getNumToWin()) {
                counter++;
                offset++;
            }
            if (!isPlayerAtPos(posTwo, player)) break;
            if (counter == getNumToWin()) return true;
        }
// check counter for win
        if (counter >= getNumToWin()) return true;
        else return false;
    }

    /**
     * This method checks to see if the last token placed resulted in a vertical win
     *
     * @param pos: the position where a token was placed
     * @param player: the symbol for a player [X or O]
     *
     * @return true if the last token placed resulted in a vertical win, false otherwise
     *
     * @post: checkVertWin = true if pos resulted in 5 tokens in a row && GameBoard = #GameBoard
     */
    default public boolean checkVertWin(BoardPosition pos, char player){
        int counter;
        int offset;
        int r = pos.getRow();
        int c = pos.getColumn();
        counter = 0;
// check moves up
        offset = 0;
        while (counter < getNumToWin()) {
            // exceeding bounds
            if (r + offset >= getNumRows()) break;
            BoardPosition posOne = new BoardPosition(r + offset, c);
            if (isPlayerAtPos(posOne, player) && r + offset < getNumRows() && counter != getNumToWin()) {
                counter++;
                offset++;
            }
            if (!isPlayerAtPos(posOne, player)) break;
            if (counter == getNumToWin()) return true;
        }
// check moves down
        offset = 1;
        while (counter < getNumToWin()) {
            // exceeding bounds
            if (r - offset < 0) break;
            BoardPosition posTwo = new BoardPosition(r - offset, c);
            if (isPlayerAtPos(posTwo, player) && r - offset >= 0 && counter != getNumToWin()) {
                counter++;
                offset++;
            }
            if (!isPlayerAtPos(posTwo, player)) break;
            if (counter == getNumToWin()) return true;
        }
// check counter for win
        if (counter >= getNumToWin()) return true;
        else return false;
    }

    /**
     * This method checks to see if the last token placed resulted in diagonal win
     *
     * @param pos: the position where a token was placed
     * @param player: the symbol for a player [X or O]
     *
     * @return true if the last token placed in a diagonal win, false otherwise
     *
     * @post: checkDiagWin = true if pos resulted in 5 tokens in a row && GameBoard = #GameBoard
     */
    default public boolean checkDiagWin(BoardPosition pos, char player){
        int counter;
        int offset;
        int r = pos.getRow();
        int c = pos.getColumn();
        counter = 0;
        // diagonal of (+1,+1)
        offset = 0;
        while (counter < getNumToWin()) {
            // exceed bounds
            if (r+offset >= getNumRows() || c+offset >= getNumColumns()) break;
            BoardPosition posOne = new BoardPosition(r+offset, c+offset);
            if (isPlayerAtPos(posOne, player) && counter != getNumToWin()) {
                counter++;
                offset++;
            }
            if (counter == getNumToWin()) return true;
            if (!isPlayerAtPos(posOne, player)) break;
        }
        // diagonal of (-1,-1)
        offset = 1;
        while (counter < getNumToWin()) {
            // exceed bounds
            if (r-offset < 0 || c-offset < 0) break;
            BoardPosition posTwo = new BoardPosition(r-offset, c-offset);
            if (isPlayerAtPos(posTwo, player) && counter != getNumToWin()) {
                counter++;
                offset++;
            }
            if (!isPlayerAtPos(posTwo, player)) break;
            if (counter == getNumToWin()) return true;
        }
        // checking diagonal in different direction
        if (counter < getNumToWin()) {
            counter = 0;
            offset = 0;
            //diagonal of (+1,-1)
            while (counter < getNumToWin()) {
                // exceed bounds
                if (r + offset >= getNumRows() || c - offset < 0) break;
                BoardPosition posTwo = new BoardPosition(r + offset, c - offset);
                if (isPlayerAtPos(posTwo, player) && counter != getNumToWin()) {
                    counter++;
                    offset++;
                }
                if (!isPlayerAtPos(posTwo, player)) break;
                if (counter == getNumToWin()) return true;
            }
            // diagonal of (-1,+1)
            offset = 1;
            while (counter < getNumToWin()) {
                // exceed bounds
                if (r - offset < 0 || c + offset >= getNumColumns()) break;
                BoardPosition posTwo = new BoardPosition(r - offset, c + offset);
                if (isPlayerAtPos(posTwo, player) && counter != getNumToWin()) {
                    counter++;
                    offset++;
                }
                if (!isPlayerAtPos(posTwo, player)) break;
                if (counter == getNumToWin()) return true;
            }
        } else if (counter >= getNumToWin()) return true;
        return false;
    }
    /**
     * This method checks what player is at a position
     *
     * @param pos: the position where a token was placed
     *
     * @return character of player that is in pos
     *
     * @pre: (0,0) <= pos <= (user-defined row,user-defined column)
     *
     * @post: GameBoard = #GameBoard
     */
    public char whatsAtPos(BoardPosition pos);

    /**
     * This method checks to see if a player is at a position
     *
     * @param pos: the position where a token was placed
     * @param player: the symbol for a player [X or O]
     *
     * @return true if the player is at pos, false otherwise
     *
     * @pre: (0,0) <= pos <= (user-defined row,user-defined column)
     *
     * @post: isPlayerAtPos = true if player is at pos
     */
    default public boolean isPlayerAtPos(BoardPosition pos, char player){
        return (whatsAtPos(pos) == player);
    }
}
