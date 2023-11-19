package com.example.checkers;
import java.util.Vector;

public class Legal_moves {
    private int lrow;
    private int lcol;
    private String type;
    private int torow;
    private int tocol;
    private String sign;
    private Vector<Data>eats=new Vector<Data>();
    private Data a;


    public Legal_moves(int lrow, int lcol, String type, int torow, int tocol, String sign,Data a,Vector<Data>eats) {
        super();
        this.lrow = lrow;
        this.lcol = lcol;
        this.type = type;
        this.torow = torow;
        this.tocol = tocol;
        this.sign = sign;
        this.a=a;
        this.eats=eats;
    }

    public Vector<Data> getEats() {
        return this.eats;
    }


    public void setEats(Vector<Data> eats) {
        Vector<Data>eats2=new Vector<Data>();
        for (int i=0;i<eats.size();i++){
            Data a = new Data(eats.get(i).getRow(),eats.get(i).getCol());
            eats2.add(a);
        }
        this.eats=eats2;
    }
    public void addEats(int row,int col) {
        Data eat=new Data(row, col);
        this.eats.add(eat);
    }


    public Data getA() {
        return a;
    }
    public void setA(Data a) {
        this.a = a;
    }

    public int getLrow() {
        return lrow;
    }
    public void setLrow(int lrow) {
        this.lrow = lrow;
    }
    public int getLcol() {
        return lcol;
    }
    public void setLcol(int lcol) {
        this.lcol = lcol;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getTorow() {
        return torow;
    }
    public void setTorow(int torow) {
        this.torow = torow;
    }
    public int getTocol() {
        return tocol;
    }
    public void setTocol(int tocol) {
        this.tocol = tocol;
    }
    public String getSign() {
        return sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }

}
