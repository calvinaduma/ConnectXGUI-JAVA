package cpsc2150.extendedConnectX;

import java.util.ArrayList;
import java.util.*;

/**
 * The controller class will handle communication between our View and our Model (IGameBoard)
 * <p>
 * This is where you will write code
 * <p>
 * You will need to include your IGameBoard interface
 * and both of the IGameBoard implementations from Project 4
 * If your code was correct you will not need to make any changes to your IGameBoard implementation class
 */
public class ConnectXController {

    //our current game that is being played
    private IGameBoard curGame;

    //The screen that provides our view
    private ConnectXView screen;

    public static final int MAX_PLAYERS = 10;
    //our play tokens are hard coded. We could make a screen to get those from the user, but

    private int numPlayers;

    // the available players tokens.
    private char[] playerTokens = {'X','O','R','A','M','L','P','Y','F','Z'};
    // the selected player tokens based on numPlayers
    private List<Character> players = new ArrayList<>();
    // keeps track of current player turn
    private char currentPlayer;
    private int playerCounter = 0, showPlayerCounter = 1;
    // keeps track if a game has been won
    private boolean gameWin;

    /**
     * @param model the board implementation
     * @param view  the screen that is shown
     * @post the controller will respond to actions on the view using the model.
     */
    ConnectXController(IGameBoard model, ConnectXView view, int np) {
        this.curGame = model;
        this.screen = view;
        numPlayers = np;
        for (int i=0; i<numPlayers; i++){
            players.add(playerTokens[i]);
        }
        gameWin = false;
    }

    /**
     * @param col the column of the activated button
     * @post will allow the player to place a token in the column if it is not full, otherwise it will display an error
     * and allow them to pick again. Will check for a win as well. If a player wins it will allow for them to play another
     * game hitting any button
     */
    public void processButtonClick(int col) {
        if (curGame.checkTie() || gameWin) newGame();
        // finds the row of token placed
        BoardPosition finalPos = null;
        for (int r = 0; r < curGame.getNumRows(); r++) {
            BoardPosition pos = new BoardPosition(r, col);
            if (curGame.whatsAtPos(pos) == ' ') {
                finalPos = pos;
                break;
            }
        }
        // current player
        currentPlayer = players.get(playerCounter);
        char playerToShow = players.get(showPlayerCounter);
        screen.setMessage("It is " + playerToShow + "'s turn!");
        // check if slot is free
        if (curGame.checkIfFree(col)){
            curGame.placeToken(currentPlayer,col); // place token on gameboard
            screen.setMarker(finalPos.getRow(),col,currentPlayer); // place token on GUI
            // check for win
            if (curGame.checkForWin(col,currentPlayer)){
                screen.setMessage(currentPlayer + " has won! Click any button to start a new game.");
                gameWin = true;
            }
            // check for tie
            if (curGame.checkTie()){
                screen.setMessage("It is a tie! Click any button to start a new game.");
            }
            if (playerCounter == numPlayers-1) playerCounter=0;
            else playerCounter++;
            if (showPlayerCounter+1 == numPlayers) showPlayerCounter = 0;
            else showPlayerCounter++;
        } else {
            screen.setMessage("Column is full! Choose another spot.");
        }
    }

    /**
     * This method will start a new game by returning to the setup screen and controller
     */
    private void newGame() {
        //close the current screen
        screen.dispose();
        //start back at the set up menu
        SetupView screen = new SetupView();
        SetupController controller = new SetupController(screen);
        screen.registerObserver(controller);
    }
}