package cpsc2150.extendedConnectX;

public class BoardPosition {
    /**
     * @invariant: row = #row && column = #column
     *
     * Correspondence: self.row = row && self.column = column
     */

    private int column;
    private int row;

    /**
     * This creates a new object that contains the information for an individual grid on the board
     *
     * @param rowPosition: the row number of an individual grid
     * @param columnPosition: the column number of an individual grid
     *
     * @pre: 0 <= rowPosition <= #row && 0 <= columnPosition <= #column
     *
     * @post: row = rowPosition && column = columnPosition
     */
    public BoardPosition(int rowPosition, int columnPosition){
        column = columnPosition;
        row = rowPosition;
    }

    /**
     * This method gets the row number for the position of the grid
     *
     * @return the row position of the object
     *
     * @post: getRow = row
     */
    public int getRow(){ return row; }

    /**
     * This method gets the column number for the position of the grid
     *
     * @return the column position of the object
     *
     * @post: getColumn = column
     */
    public int getColumn(){ return column; }

    /**
     * This method Overrides the equals() Object method to return true or false
     *
     * @return true if the pos is equal to the object's pos, false otherwise
     *
     * @pre: (0,0) <= pos <= (#row,#column)
     *
     * @post: equals() = true if pos.row==this.row && pos.column==this.column
     */

    public boolean equals(BoardPosition pos){
        return (getRow() == pos.getRow() && getColumn() == pos.getColumn());
    }

    /**
     * This method Overrides the toString() Object method to print a string
     *
     * @return a string in the format "r,c"
     *
     * @pre: (0,0) <= pos <= (#row,#column)
     *
     * @post: toString() = the value of the String
     *
     */

    public String toString(BoardPosition pos){
        String str = "";
        str += pos.getRow() + "," + pos.getColumn();
        return str;
    }
}
