/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hai
 */
public class CheckMove {
    private Cell selected;
    private Cell destined;
    private Cell board[][];

    public CheckMove()
    {
        selected = null;
        destined = null;
        board = new Cell[8][8];
    }

    public void setSelected(Cell _selected)
    {
        selected=_selected;
    }

    public Cell getSelected()
    {
        return selected;
    }

    public void setDestined(Cell _destined)
    {
        destined=_destined;
    }

    public Cell getDestined()
    {
        return destined;
    }

    public void setBoard(Cell _board[][])
    {
        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 8; j++)
                board[i][j]=_board[i][j];
    }

    public Cell[][] getBoard()
    {
        return board;
    }

    // phuong thuc kiem tra 2 o co chua cung 1 loai quan hay ko.
    // hoac 2 o trung nhau van return true.
    public boolean Check(){
        //2 o trung nhau
        if (selected.getRow()==destined.getRow() && selected.getColumn()==destined.getColumn())
            return true;
        //2 quan cung mau
        if(selected.getAChessman()!=null&&destined.getAChessman()!=null)
            if (selected.getAChessman().getColor()==destined.getAChessman().getColor())
                 return true;
        return false;
    }

    //phuong thuc kiem tra viec di chuyen quan tot .
    public boolean isMovePawn(){
        // neu 2 o chua cung 1 loai quan hoac trung nhau thi false .
        if (Check())
            return false;
        //neu destined ko co quan thi kiem tra di thang
        if (destined.getAChessman()== null){
        // quan den tien len 1 buoc
            if (selected.getAChessman().getColor()== false && destined.getRow()-selected.getRow()==1 && destined.getColumn()==selected.getColumn())
                return true;
            // quan den nuoc dau tien co the di 2 nuoc
            if (selected.getAChessman().getColor()== false && destined.getRow()==3 && selected.getRow()==1 && destined.getColumn()==selected.getColumn() && board[2][selected.getColumn()].getAChessman()== null)
                return true;
            // quan trang tien len 1 buoc
            if (selected.getAChessman().getColor()== true && selected.getRow()-destined.getRow()==1 && destined.getColumn()==selected.getColumn())
                return true;
            // quan trang nuoc dau tien co the di 2 nuoc
            if (selected.getAChessman().getColor()== true && selected.getRow()==6 && destined.getRow()==4 && destined.getColumn()==selected.getColumn() && board[5][selected.getColumn()].getAChessman()== null)
                return true;
            return false;
        }
        else{
        //neu destined co quan thi kiem tra an cheo
        // quan den an cheo
            if (selected.getAChessman().getColor()== false && destined.getAChessman().getColor()== true && destined.getRow()-selected.getRow()==1 && Math.abs(destined.getColumn()-selected.getColumn())==1 )
                return true;
            // quan trang an cheo
            if (selected.getAChessman().getColor()== true && destined.getAChessman().getColor()== false && selected.getRow()-destined.getRow()==1 && Math.abs(destined.getColumn()-selected.getColumn())==1 )
                return true;
            return false;
        }
    }

    //phuong thuc kiem tra viec di chuyen quan xe .
    public boolean isMoveRook(){
        // neu 2 o chua cung 1 loai quan hoac trung nhau thi false .
        if (Check())
            return false;
        //Kiem tra di doc
        if (selected.getColumn()==destined.getColumn()){
            // Di tien
            if (selected.getRow()<destined.getRow())
                for ( int i=selected.getRow()+1;i<destined.getRow();i++)
                    if (board[i][selected.getColumn()].getAChessman()!= null)
                        return false;
            // Di lui
            if (selected.getRow()>destined.getRow())
                for ( int i=destined.getRow()+1;i<selected.getRow();i++)
                    if (board[i][selected.getColumn()].getAChessman()!= null)
                        return false;
            return true;
        }
        //Kiem tra di ngang
        if (selected.getRow()==destined.getRow()){
            // Di sang phai
            if (selected.getColumn()<destined.getColumn())
                for ( int i=selected.getColumn()+1;i<destined.getColumn();i++)
                    if (board[selected.getRow()][i].getAChessman()!= null)
                        return false;
            // Di sang trai
            if (selected.getColumn()>destined.getColumn())
                for ( int i=destined.getColumn()+1;i<selected.getColumn();i++)
                    if (board[selected.getRow()][i].getAChessman()!= null)
                        return false;
            return true;
        }
        return false;
    }

    //phuong thuc kiem tra viec di chuyen quan ma .
    public boolean isMoveKnight(){
        // neu 2 o chua cung 1 loai quan hoac trung nhau thi false
        if (Check())
            return false;
        if (Math.abs(selected.getRow()-destined.getRow())==1 && Math.abs(selected.getColumn()-destined.getColumn())==2)
            return true;
        if (Math.abs(selected.getRow()-destined.getRow())==2 && Math.abs(selected.getColumn()-destined.getColumn())==1)
            return true;
        return false;
    }

    //phuong thuc kiem tra viec di chuyen quan tuong .
    public boolean isMoveBishop(){
        // neu 2 o chua cung 1 loai quan hoac trung nhau thi false
        if (Check())
            return false;
        // Kiem tra di cheo
        if (Math.abs(selected.getRow()-destined.getRow()) != Math.abs(selected.getColumn()-destined.getColumn()))
            return false;
        // Kiem tra 4 huong
        if (selected.getRow()<destined.getRow() && selected.getColumn()<destined.getColumn()){
            int i=selected.getRow()+1;
            int j=selected.getColumn()+1;
            while (i<destined.getRow()){
                if (board[i][j].getAChessman()!= null)
                    return false;
                i++;
                j++;
            }
        }
        if (selected.getRow()<destined.getRow() && selected.getColumn()>destined.getColumn()){
            int i=selected.getRow()+1;
            int j=selected.getColumn()-1;
            while (i<destined.getRow()){
                if (board[i][j].getAChessman()!= null)
                    return false;
                i++;
                j--;
            }
        }
        if (selected.getRow()>destined.getRow() && selected.getColumn()>destined.getColumn()){
            int i=selected.getRow()-1;
            int j=selected.getColumn()-1;
            while (i>destined.getRow()){
                if (board[i][j].getAChessman()!= null)
                    return false;
                i--;
                j--;
            }
        }
        if (selected.getRow()>destined.getRow() && selected.getColumn()<destined.getColumn()){
            int i=selected.getRow()-1;
            int j=selected.getColumn()+1;
            while (i>destined.getRow()){
                if (board[i][j].getAChessman()!= null)
                    return false;
                i--;
                j++;
            }
        }
        return true;
    }

    //phuong thuc kiem tra viec di chuyen quan vua
    public boolean isMoveKing(){
        // neu 2 o chua cung 1 loai quan hoac trung nhau thi false
        if(Check())
            return false;
        if (Math.abs(selected.getRow()-destined.getRow())<=1 && Math.abs(selected.getColumn()-destined.getColumn())<=1)
            return true;
        return false;
    }

    // Phuong thuc kiem tra di chuyen cua quan hau la ket hop cua quan xe va quan tuong
    public boolean isMoveQueen()
    {
        if(Check())
            return false;
        if(isMoveRook() || isMoveBishop())
            return true;
        return false;
    }
}