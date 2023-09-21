package cpsc2150.extendedConnectX;

import java.util.*;

public class GameBoardMem extends AbsGameBoard {
    /**
     * @invariant: row = #row && column = #column && board = #row x #column 2-dimensional array of char
     *               && numberOfTokensToWin = #numberOfTokensToWin
     */

    private int row, column, numberOfTokensToWin;

    public GameBoardMem(int newRow, int newColumn, int numToWin) {
        numberOfTokensToWin = numToWin;
        row = newRow;
        column = newColumn;
        boardMap = new HashMap<Character, List<BoardPosition>>();
    }

    public int getNumRows() {
        return row;
    }

    public int getNumColumns() {
        return column;
    }

    public int getNumToWin() {
        return numberOfTokensToWin;
    }


    public void placeToken(char token, int columnPosition) {
        BoardPosition finalPos = null;
        for (int r = 0; r < getNumRows(); r++) {
            BoardPosition pos = new BoardPosition(r, columnPosition);
// if slot is empty
            if (whatsAtPos(pos) == ' ') {
                finalPos = pos;
                break;
            }
        }
        if (boardMap.containsKey(token)) {
            for (Map.Entry<Character, List<BoardPosition>> tempBoardList : boardMap.entrySet()) {
                if (tempBoardList.getKey() == token) {
                    List<BoardPosition> tempList = new ArrayList<BoardPosition>();
                    tempList.addAll(tempBoardList.getValue());
                    tempList.add(finalPos);
                    boardMap.put(token, tempList);
                }
            }
        } else {
            List<BoardPosition> tempList = new ArrayList<BoardPosition>();
            tempList.add(finalPos);
            boardMap.put(token, tempList);
        }
    }

    public char whatsAtPos(BoardPosition pos) {
        if (boardMap.isEmpty()) return ' ';
        else {
// iterates through each list in map
            for (Map.Entry<Character, List<BoardPosition>> tempBoardList : boardMap.entrySet()) {
                List<BoardPosition> tempList = new ArrayList<BoardPosition>();
                tempList.addAll(tempBoardList.getValue());
// if pos is found in list
                for (BoardPosition tempPos : tempList){
                    if (tempPos.equals(pos)) return tempBoardList.getKey();
                }
            }
        }
        return ' ';
    }
}