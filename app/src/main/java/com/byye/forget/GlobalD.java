package com.byye.forget;

import android.app.Application;

public class GlobalD extends Application {

    public Integer ID;
    public static int Kod;
    public Integer KonumID;
    @Override
    public void onCreate() {
        super.onCreate();

    }
    public void setIDD(Integer id){
        ID=id;
    }
    public Integer getIDD(){
        return ID;
    }

    public void setKod(int kod){
        Kod=kod;
    }

    public int getKod(){
        return Kod;
    }

    public void setKonumID(Integer konumID){
        KonumID=konumID;
    }

    public Integer getKonumID(){
        return KonumID;
    }


}
