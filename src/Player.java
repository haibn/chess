/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thanhlx
 */
public class Player {
    private boolean color;
    private Cell selected;
    private Cell destined;

    public Player()
    {
        color = true;   // Khởi tạo cho mặc định là người chơi quân trắng
        selected = null;
        destined = null;
    }

    public void setColor(boolean _color)
    {
        color = _color;
    }

    public boolean getColor()
    {
        return color;
    }

    public void setSelected(Cell val)
    {
        selected = val;
    }

    public Cell getSelected()
    {
        return selected;
    }

    public void setDestined(Cell val)
    {
        destined = val;
    }

    public Cell getDestined()
    {
        return destined;
    }
}
