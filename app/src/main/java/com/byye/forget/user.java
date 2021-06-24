package com.byye.forget;


public final class user {

    private String Baslık;
    private String Bildirim;
    private double X_konum;
    private double Y_konum;
    private Integer ID;
    private int Yil,Ay,Gun,Saat,Dk;
    private String Path;



    public user() {
    }

    public user(Integer id,String baslık, String bildirm,float x_konum, float y_konum, int yil, int ay,int gun,int saat,int dk, String path) {
        Baslık =baslık;
        Bildirim=bildirm;
        X_konum=x_konum;
        Y_konum=y_konum;
        ID=id;
        Yil=yil;
        Ay=ay;
        Gun=gun;
        Saat=saat;
        Dk=dk;
        Path=path;
    }


    public Integer getID() {
        return ID;
    }

    public void setID(Integer id) {
        ID = id;
    }

    public int getYil() {
        return Yil;
    }

    public void setYil(int yil) {
        Yil = yil;
    }

    public int getAy() {
        return Ay;
    }

    public void setAy(int ay) {
        Ay = ay;
    }

    public int getGun() {
        return Gun;
    }

    public void setGun(int gun) {
        Gun = gun;
    }

    public int getSaat() {
        return Saat;
    }

    public void setSaat(int saat) {
        Saat = saat;
    }

    public int getDk() {
        return Dk;
    }

    public void setDk(int dk) {
         Dk = dk;
    }

    public String getBaslık() {
        return Baslık;
    }

    public void setBaslık(String baslık) {
        Baslık = baslık;
    }

    public void setX_konum(double x_konum){
        X_konum=x_konum;
    }

    public double getX_konum() {
        return X_konum;
    }

    public void setY_konum(double y_konum) {
        Y_konum = y_konum;
    }

    public double getY_konum() {
        return Y_konum;
    }

    public String getBildirim() {
        return Bildirim;
    }

    public void setBildirim(String bildirim) {
        Bildirim= bildirim;
    }

    public void setPath(String path){
        Path=path;
    }
    public String getPath(){
        return Path;
    }

}

