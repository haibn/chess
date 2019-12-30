
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import javax.swing.ImageIcon;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hai
 */
public class Chess {
    private Cell board[][];     // Khởi tạo 8x8 cell thể hiện bàn cờ
    private Player players[];   // Tạo 2 người chơi
    private int win;            // win = 1 nếu người chơi trắng thắng, = 0 Nếu ng chơi đen thắng
                                // = 2 nếu hòa cơ, = 3 nếu trò chơi vẫn chưa phân thắng bại
    private boolean turn;       // turn = true nếu là phiên chơi của người chơi trắng và ngược lại
    private int mode;           // mode = 1: người chơi với người
                                // mode = 2: người chơi với máy
                                // mode = 3: máy chơi với máy
    private Cell tmpBoard[][];        // Biến tmpBoard để lưu lại trạng thái trước đó của bàn cờ, phục vụ hàm undo
    private boolean tmpTurn;
    private boolean clicked;        // Biến clicked = true nếu đã có 1 quân cờ được chọn
                                    // clicked = fasle nếu chưa có quân cờ nào được chọn

    public Chess()
    {
        board = new Cell[8][8];
        players = new Player[2];
        win = 3;
        turn = true;
        mode = 1;
        clicked = false;
        tmpBoard = new Cell[8][8];

        players[0] = new Player();
        players[0].setColor(false); // nguoi choi 0 la quan den
        players[1] = new Player();  // nguoi choi 1 la quan trang
        
        for(int i=0;i<8;i++)
            for(int j=0;j<8;j++){
                board[i][j] =new Cell(i,j,null); // khoi tao cac o
                tmpBoard[i][j] =new Cell(i,j,null);
            }

        //khoi tao quan den
        for(int i=0;i<8;i++)
        {
            board[1][i].setAChessman(new Pawn());
            board[1][i].getAChessman().setAnIcon(false);
            board[1][i].getAChessman().setColor(false); //quan mau den
        }
        board[0][0].setAChessman(new Rook());
        board[0][7].setAChessman(new Rook());
        board[0][1].setAChessman(new Knight());
        board[0][6].setAChessman(new Knight());
        board[0][2].setAChessman(new Bishop());
        board[0][5].setAChessman(new Bishop());
        board[0][3].setAChessman(new Queen());
        board[0][4].setAChessman(new King());
        for(int i=0;i<8;i++){
            board[0][i].getAChessman().setAnIcon(false);
            board[0][i].getAChessman().setColor(false);  //quan mau den
        }

        // khoi tao quan trang
        for(int i=0;i<8;i++)
            board[6][i].setAChessman(new Pawn());
        board[7][0].setAChessman(new Rook());
        board[7][7].setAChessman(new Rook());
        board[7][1].setAChessman(new Knight());
        board[7][6].setAChessman(new Knight());
        board[7][2].setAChessman(new Bishop());
        board[7][5].setAChessman(new Bishop());
        board[7][3].setAChessman(new Queen());
        board[7][4].setAChessman(new King());

        // khoi tao gia tri hang va cot cho cac quan co
        for(int i=0;i<8;i++)
            for(int j=0;j<8;j++)
               if(board[i][j].getAChessman()!=null)
               {            
                   board[i][j].getAChessman().setRow(i);
                   board[i][j].getAChessman().setColumn(j);
               }
        backupBoard();// luu trang thai cua ban co
    }

    public void setPlayers(Player first, Player second)
    {
        players[0] = first;
        players[1] = second;
    }

    public Player getPlayer(int i)
    {
        return players[i];
    }

    public Cell getBoard(int _row, int _column)
    {
        return board[_row][_column];
    }

    public void setWin(int _win)
    {
        win = _win;
    }

    public int getWin()
    {
        return win;
    }

    public boolean checkFinish()
    {
        if(win == 0 || win == 1)
            return true;
        return false;
    }

    public void setTurn(boolean _turn)
    {
        turn = _turn;
    }

    public boolean getTurn()
    {
        return turn;
    }

    public void setMode(int _mode)
    {
        mode = _mode;
    }

    public int getMode()
    {
        return mode;
    }

    public void save(String _name) throws FileNotFoundException
    {
        String fileName = _name + ".chess";
        FileOutputStream outputFile = new FileOutputStream(fileName);
        OutputStreamWriter writer = new OutputStreamWriter(outputFile);
        BufferedWriter bufWriter = new BufferedWriter(writer);
        PrintWriter printWriter = new PrintWriter(bufWriter, true);

        // Ghi ra trạng thái bàn cờ ra file theo định dạng 8 hàng 8 cột
        // Quân trắng ghi tên quân cờ, quân đen ghi "x" + tên quân cờ phía sau để phân biệt
        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 8; j++)
            {
                if (board[i][j].getAChessman()==null)
                {
                    printWriter.println("null");
                    continue;
                }
                else if(board[i][j].getAChessman().getName().compareTo("Pawn") == 0)     // Nếu là quân tốt
                {
                    if(board[i][j].getAChessman().getColor())       // Nếu là tốt trắng
                        printWriter.println("Pawn");
                    else    // Nếu là tốt đen
                        printWriter.println("xPawn");
                    continue;
                }

                else if(board[i][j].getAChessman().getName().compareTo("Rook") == 0)     // Nếu là quân xe
                {
                    if(board[i][j].getAChessman().getColor())       // Nếu là xe trắng
                        printWriter.println("Rook");
                    else    // Nếu là xe đen
                        printWriter.println("xRook");
                    continue;
                }

                else if(board[i][j].getAChessman().getName().compareTo("Knight") == 0)     // Nếu là quân mã
                {
                    if(board[i][j].getAChessman().getColor())       // Nếu là mã trắng
                        printWriter.println("Knight");
                    else    // Nếu là mã đen
                        printWriter.println("xKnight");
                    continue;
                }

                else if(board[i][j].getAChessman().getName().compareTo("Bishop") == 0)     // Nếu là tượng tốt
                {
                    if(board[i][j].getAChessman().getColor())       // Nếu là tượng trắng
                        printWriter.println("Bishop");
                    else    // Nếu là tượng đen
                        printWriter.println("xBishop");
                    continue;
                }

                else if(board[i][j].getAChessman().getName().compareTo("Queen") == 0)     // Nếu là quân hậu
                {
                    if(board[i][j].getAChessman().getColor())       // Nếu là hậu trắng
                        printWriter.println("Queen");
                    else    // Nếu là hậu đen
                        printWriter.println("xQueen");
                    continue;
                }

                else if(board[i][j].getAChessman().getName().compareTo("King") == 0)     // Nếu là quân tướng
                {
                    if(board[i][j].getAChessman().getColor())       // Nếu là tướng trắng
                        printWriter.println("King");
                    else    // Nếu là tướng đen
                        printWriter.println("xKing");
                    continue;
                }
            }
        if(turn ==true)
           printWriter.println("true");
        else printWriter.println("false");
        
        printWriter.close();
    }

    public void load(String _name) throws FileNotFoundException, IOException
    {
        FileInputStream inputFile = new FileInputStream(_name);
        InputStreamReader reader = new InputStreamReader(inputFile);
        BufferedReader bufReader = new BufferedReader(reader);
        String tmp;
        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 8; j++)
            {
                tmp = bufReader.readLine();
                if(tmp.compareTo("Pawn") == 0 || tmp.compareTo("xPawn") == 0)
                {
                    board[i][j].setAChessman(new Pawn());
                    if(tmp.compareTo("xPawn") == 0){                 // neu la quan den
                        board[i][j].getAChessman().setColor(false);
                        board[i][j].getAChessman().setAnIcon(false);
                    }
                    continue;
                }
                else if(tmp.compareTo("Rook") == 0 || tmp.compareTo("xRook") == 0)
                {
                    board[i][j].setAChessman(new Rook());
                    if(tmp.compareTo("xRook") == 0){                // neu la quan den
                        board[i][j].getAChessman().setColor(false);
                        board[i][j].getAChessman().setAnIcon(false);
                    }
                    continue;
                }

                else if(tmp.compareTo("Knight") == 0 || tmp.compareTo("xKnight") == 0)
                {
                    board[i][j].setAChessman(new Knight());
                    if(tmp.compareTo("xKnight") == 0){              // neu la quan den
                        board[i][j].getAChessman().setColor(false);
                        board[i][j].getAChessman().setAnIcon(false);
                    }
                    continue;
                }

                else if(tmp.compareTo("Bishop") == 0 || tmp.compareTo("xBishop") == 0)
                {
                    board[i][j].setAChessman(new Bishop());
                    if(tmp.compareTo("xBishop") == 0){               // neu la quan den
                        board[i][j].getAChessman().setColor(false);
                        board[i][j].getAChessman().setAnIcon(false);
                    }
                    continue;
                }

                else if(tmp.compareTo("Queen") == 0 || tmp.compareTo("xQueen") == 0)
                {
                    board[i][j].setAChessman(new Queen());
                    if(tmp.compareTo("xQueen") == 0){               // neu la quan den
                        board[i][j].getAChessman().setColor(false);
                        board[i][j].getAChessman().setAnIcon(false);
                    }
                    continue;
                }

                else if(tmp.compareTo("King") == 0 || tmp.compareTo("xKing") == 0)
                {
                    board[i][j].setAChessman(new King());
                    if(tmp.compareTo("xKing") == 0){                // neu la quan den
                        board[i][j].getAChessman().setColor(false);
                        board[i][j].getAChessman().setAnIcon(false);
                    }
                    continue;
                }
                else if(tmp.compareTo("null") == 0)
                {
                    board[i][j].setAChessman(null);
                    continue;
                }
            }
        tmp = bufReader.readLine();
        if(tmp.compareTo("true") == 0) turn=true;
        else if(tmp.compareTo("false") == 0) turn=false;
        bufReader.close();

        for(int i=0;i<8;i++)
            for(int j=0;j<8;j++)
               if(board[i][j].getAChessman()!=null)
               {
                   board[i][j].getAChessman().setRow(i);
                   board[i][j].getAChessman().setColumn(j);
               }
        backupBoard();
    }

    // Phương thức backupBoard dùng để lưu lại trạng thái trước của bàn cờ cho tmpBoard
    public void backupBoard()
    {
        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 8; j++)
                tmpBoard[i][j].copyCell(board[i][j]);
           
        tmpTurn = turn;
    }

    public void undo()
    {
        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 8; j++)
                board[i][j].copyCell(tmpBoard[i][j]);

        turn=tmpTurn;
        clicked=false;
        players[0].setSelected(null);
        players[1].setSelected(null);
    }

    Cell tmp = new Cell(); //o cell tam de luu cell da duoc click
    public void receiveMouseClicked(Cell _selected)
    {
        // Nếu chưa có Chessman nào được chọn trước đó
        if(clicked == false)
        {
            if(_selected.getAChessman() == null) // Nếu Cell này ko có Chessman nào
                return;
            else    // Nếu Cell này có Chessman
                if(turn == true)    // Nếu đang là phiên của người chơi trắng
                    if(_selected.getAChessman().getColor() == false)    // Chessman được chọn lại là quân đen
                        return;
                    else{
                        players[1].setSelected(_selected);
                        clicked = true;
                        tmp=_selected;
                    }
                else    // Nếu đang là phiên chơi của người chơi đen
                    if(_selected.getAChessman().getColor() == true)     // Chessman được chọn lại là quân trắng
                        return;
                    else{
                        players[0].setSelected(_selected);
                        clicked = true;
                        tmp=_selected;
                    }               
        }
        else    // Nếu đã có 1 Chessman được chọn trước đó
        {
            if(_selected.getAChessman() == null)    // Nếu Cell đích ko có Chessman nào
            {
                // Yêu cầu so sánh điều kiện move
                if(tmp.getAChessman().move(tmp, _selected, board))
                {
                    backupBoard();             //luu trang thai truoc khi di chuyen
                    tmp.getAChessman().setRow(_selected.getRow());
                    tmp.getAChessman().setColumn(_selected.getColumn());
                    _selected.setAChessman(tmp.getAChessman());  // di chuyen quan co
                    board[tmp.getRow()][tmp.getColumn()].setAChessman(null);
                    clicked=false;
                    tmp=null;
                    players[0].setSelected(null);
                    players[1].setSelected(null);
                    turn=!turn;
                }
                else
                    return;
            }
            else    // Nếu Cell đích có Chessman
                if(turn == true)    // Nếu đang là phiên của người chơi trắng
                    if(_selected.getAChessman().getColor() == true)     // Chessman được chọn lại là quân trắng
                    {
                        players[1].setSelected(_selected);
                        clicked = true;
                        tmp=_selected;
                        return;
                    }
                    else    // Chessman được chọn lại là quân đen
                    {
                        // Kiểm tra xem có move được ko
                        if(tmp.getAChessman().move(tmp, _selected, board))
                        {
                           backupBoard();          //luu trang thai truoc khi di chuyen
                           if(_selected.getAChessman().getName()=="King")win=1; // o dich la black king, trang thang
                           tmp.getAChessman().setRow(_selected.getRow());
                           tmp.getAChessman().setColumn(_selected.getColumn());
                           _selected.setAChessman(tmp.getAChessman());   //di chuyen quan co
                           board[tmp.getRow()][tmp.getColumn()].setAChessman(null);
                           clicked=false;
                           tmp=null;
                           players[0].setSelected(null);
                           players[1].setSelected(null);
                           turn=!turn;
                        }
                        else
                            return;
                    }
                else    // Nếu đang là phiên của người chơi đen
                    if(_selected.getAChessman().getColor() == false)    // Chessman được chọn lại là quân đen
                    {
                        players[0].setSelected(_selected);
                        tmp=_selected;
                        return;
                    }
                    else    // Chessman được chọn là quân trắng
                    {
                        // Kiểm tra xem có move được ko
                        if(tmp.getAChessman().move(tmp, _selected, board))
                        {
                           backupBoard();               //luu trang thai truoc khi di chuyen
                           if(_selected.getAChessman().getName()=="King")win=0; // o dich la white king, den thang
                           tmp.getAChessman().setRow(_selected.getRow());
                           tmp.getAChessman().setColumn(_selected.getColumn());
                           _selected.setAChessman(tmp.getAChessman());           //di chuyen quan co
                           board[tmp.getRow()][tmp.getColumn()].setAChessman(null);
                           clicked=false;
                           tmp=null;
                           players[0].setSelected(null);
                           players[1].setSelected(null);
                           turn =!turn;
                        }
                        else
                            return;
                    }
        }
    }

    // thay doi skin
    public void setSkin(int skinValue)
    {
      for(int i=0; i<8;i++)
	     for(int j=0;j<8;j++)
         {
             Chessman tmp = board[i][j].getAChessman();
		     if(tmp!=null)
		     {
                 if(tmp instanceof Pawn)
                     if(tmp.getColor()==true)
                          tmp.setIcon(new ImageIcon
                                 ("images/white_pawn"+String.valueOf(skinValue)+".gif"));
                     else tmp.setIcon(new ImageIcon
                                 ("images/black_pawn"+String.valueOf(skinValue)+".gif"));
                 //------------------------
                 if(tmp instanceof Rook)
                     if(tmp.getColor()==true)
                          tmp.setIcon(new ImageIcon
                                 ("images/white_rook"+String.valueOf(skinValue)+".gif"));
                     else tmp.setIcon(new ImageIcon
                                 ("images/black_rook"+String.valueOf(skinValue)+".gif"));
                 //------------------------
                 if(tmp instanceof Knight)
                     if(tmp.getColor()==true)
                          tmp.setIcon(new ImageIcon
                                 ("images/white_knight"+String.valueOf(skinValue)+".gif"));
                     else tmp.setIcon(new ImageIcon
                                 ("images/black_knight"+String.valueOf(skinValue)+".gif"));
                 //------------------------
                 if(tmp instanceof Bishop)
                     if(tmp.getColor()==true)
                          tmp.setIcon(new ImageIcon
                                 ("images/white_bishop"+String.valueOf(skinValue)+".gif"));
                     else tmp.setIcon(new ImageIcon
                                 ("images/black_bishop"+String.valueOf(skinValue)+".gif"));
                 //------------------------
                 if(tmp instanceof Queen)
                     if(tmp.getColor()==true)
                          tmp.setIcon(new ImageIcon
                                 ("images/white_queen"+String.valueOf(skinValue)+".gif"));
                     else tmp.setIcon(new ImageIcon
                                 ("images/black_queen"+String.valueOf(skinValue)+".gif"));
                 //------------------------
                 if(tmp instanceof King)
                     if(tmp.getColor()==true)
                          tmp.setIcon(new ImageIcon
                                 ("images/white_king"+String.valueOf(skinValue)+".gif"));
                     else tmp.setIcon(new ImageIcon
                                 ("images/black_king"+String.valueOf(skinValue)+".gif"));
		     }
         }
    }
}
