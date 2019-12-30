
import javax.swing.ImageIcon;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hai
 */
// Lớp quân cờ
public abstract class Chessman {
    private int column;     // Vị trí hàng
    private int row;        // Vị trí cột
    private boolean color;  // Quân đen hay quân trắng: true là trắng, false là đen
    private boolean died;   // Quân cờ sống hay chết: true là chết, false là sống, nếu quân cờ chết các tham số khác ko còn giá trị
    protected ImageIcon anIcon;   // Ảnh đi kèm với quân cờ tương ứng
    private String name;
    protected CheckMove check;

    // Khởi tạo ko đối
    public Chessman()
    {
        column = 0;
        row = 0;
        color = true;
        died = false;
        check = new CheckMove();
    }

    // Khởi tạo có đối
    public Chessman(int _column, int _row, boolean _color, boolean _died, ImageIcon _anIcon)
    {
        column = _column;
        row = _row;
        color = _color;
        died = _died;
        anIcon.setImage(_anIcon.getImage());    // Hai kiểm tra dòng này nhé
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

    public void setColor(boolean _color)
    {
        color = _color;
    }

    public boolean getColor()
    {
        return color;
    }

    public void setDied(boolean _died)
    {
        died = _died;
    }

    public boolean getDied()
    {
        return died;
    }

    public ImageIcon getAnIcon()
    {
        return anIcon;
    }

    public void setName(String _name)
    {
        name = _name;
    }

    public String getName()
    {
        return name;
    }

    public void setIcon(ImageIcon icon)
    {
        anIcon=icon;
    }
    // 2 phương thức cuối cùng là setAnIcon và move sẽ được cài đặt abstract để tường minh ở các lớp con

    public abstract void setAnIcon(boolean color);  
    public abstract boolean move(Cell _selected, Cell _destined, Cell _board[][]);

    // Phương thức copy Quân cờ - Chessman
    public void copyChessman(Chessman _chessman)
    {
        if(_chessman!=null){
           column = _chessman.getColumn();
           row = _chessman.getRow();
           color = _chessman.getColor();
           died = _chessman.getDied();
           anIcon.setImage(_chessman.getAnIcon().getImage());
        }
    }
}
