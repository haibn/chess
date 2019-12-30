/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thanhlx
 */
public class Cell {
    private int column;
    private int row;
    private Chessman aChessman;

    public Cell()
    {
        column = 0;
        row = 0;
        aChessman = null;
    }

    public Cell(int _row,int _column, Chessman _chessman)
    {
        column = _column;
        row = _row;
        setAChessman(_chessman);
    }

    public void setColumn(int _column)
    {
        column = _column;
    }

    public int getColumn()
    {
        return column;
    }

    public void setRow(int _row)
    {
        row = _row;
    }

    public int getRow()
    {
        return row;
    }

    public void setAChessman(Chessman _chessman)
    {
        aChessman = _chessman;
    }

    public Chessman getAChessman()
    {
        return aChessman;
    }

    public void copyCell(Cell _newCell)
    {
        column = _newCell.column;
        row = _newCell.row;
        aChessman=_newCell.getAChessman();
    }
}
