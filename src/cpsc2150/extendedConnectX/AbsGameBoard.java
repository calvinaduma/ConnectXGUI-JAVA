package cpsc2150.extendedConnectX;

import java.util.List;
import java.util.Map;

public abstract class AbsGameBoard implements IGameBoard{

    protected Map<Character, List<BoardPosition>> boardMap;
    protected char[][] board;
    /**
     * This method Overrides the toString() Object method to provide a string of the current GameBoard
     *
     * @return a string representation of the current GameBoard
     *
     * @post: toString() = string representation of the GameBoard
     */
    @Override
    public String toString(){
        String str = "";
        // prints header
        for (int i=0; i<getNumColumns(); i++){
            str += " | " +i;
            if (i==getNumColumns()-1){
                str += " | \n";
            }
        }
        // prints contents
        for (int i=getNumRows()-1; i>=0; i--){
            for (int j=0; j<getNumColumns(); j++){
                // correction for board aesthetics destroying due to double digit header
                BoardPosition pos = new BoardPosition(i,j);
                char player = whatsAtPos(pos);
                if (j < 10) {
                    str += " | " + player;
                } else {
                    str += " | " + player + " ";
                }
                if (j==getNumColumns()-1){
                    str += " | \n";
                }
            }
        }
        return str;
    }
}
