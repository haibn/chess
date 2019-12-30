
import javax.swing.ImageIcon;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thanhlx
 */
// Lớp Quân Mã
public class Knight extends Chessman {

    public Knight()
    {
        super();
        setName("Knight");
        setAnIcon(true);    // Khởi tạo mặc định quân mã có màu trắng
    }

    @Override
    public void setAnIcon(boolean color) {
        if(color == true)   // Nếu là quân trắng
            anIcon = new ImageIcon("images/white_knight1.gif");
        else        // Nếu là quân đen
            anIcon = new ImageIcon("images/black_knight1.gif");
    }

    @Override
    public boolean move(Cell _selected, Cell _destined, Cell _board[][]) {
        check.setSelected(_selected);
        check.setDestined(_destined);
        check.setBoard(_board);
        return check.isMoveKnight();
    }
}
