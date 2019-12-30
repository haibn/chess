
import javax.swing.ImageIcon;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thanhlx
 */
// Lớp Quân Tướng
public class King extends Chessman {

    public King()
    {
        super();
        setName("King");
        setAnIcon(true);    // Khởi tạo mặc định quân tướng có màu trắng
    }

    @Override
    public void setAnIcon(boolean color) {
        if(color == true)   // Nếu là quân trắng
            anIcon = new ImageIcon("images/white_king1.gif");
        else        // Nếu là quân đen
            anIcon = new ImageIcon("images/black_king1.gif");
    }

    @Override
    public boolean move(Cell _selected, Cell _destined, Cell _board[][]) {
        check.setSelected(_selected);
        check.setDestined(_destined);
        check.setBoard(_board);
        return check.isMoveKing();
    }
}
