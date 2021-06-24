package com.byye.forget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.google.android.gms.location.LocationResult;

import java.util.List;

import androidx.core.app.NotificationCompat;


public class MyLocationService extends BroadcastReceiver {
    public static final String ACTION_PROCESS_UPDATE= "com.example.pc.haritalar.UPDATE_LOCATION";

    private final int minAralik=80;
    private final int maxAralik=150;

    DatabaseHelper helper;
    List<user> userList;

    GlobalD b=new GlobalD();
    public static double konumalarm=0;


    public static   double  ilkadrx,ilkadry;

    public static int rakam=0;


    @Override
    public void onReceive(Context context, Intent intent) {

        helper=DatabaseHelper.getInstance(context);

        userList=helper.getAllUsers();

        if(intent != null){



            if(true){


                LocationResult result= LocationResult.extractResult(intent);
                if(result!=null){
                    Location location=result.getLastLocation();
                    String location_string=new StringBuilder("KONUM BİLGİSİ ALINDI:"+location.getLatitude())
                            .append("/")
                            .append(location.getLongitude())
                            .toString();

                    ilkadrx=location.getLatitude();  //başlangıç konumları aldım
                    ilkadry=location.getLongitude();


                    rakam=3;
                    for(int i=0;i<userList.size();i++){
                        if(userList.get(i).getX_konum()!=0){
                            double uzaklık=distance(location.getLatitude(),location.getLongitude(),userList.get(i).getX_konum(),userList.get(i).getY_konum());

                            // distance(location.getLatitude(),location.getLongitude(),userList.get(i).getX_konum(),userList.get(i).getY_konum());
                            if(uzaklık>minAralik && uzaklık<maxAralik)
                            {
                                konumalarm=5;
                                b.setKonumID(userList.get(i).getID());
                                NotificationHelper notificationHelper = new NotificationHelper(context);
                                NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
                                notificationHelper.getManager().notify(1, nb.build());

                            }
                        }
                    }


                    try {///uygulama tamamen kapanmadan önceki durum
                        MapsActivity.getInstance().updatetextwiew(location_string);

                        MapsActivity.getInstance().textView2.setText("Konum bilgisi yüklendi ! ");
                        // System.out.println("konumlarrr"+user1.getY_konum());


                    }catch (Exception ex) {///uygulama tamamen kapandıktan sonraki durum


                        for(int i=0;i<userList.size();i++){
                            if(userList.get(i).getX_konum()!=0){
                                double uzaklık=distance(location.getLatitude(),location.getLongitude(),userList.get(i).getX_konum(),userList.get(i).getY_konum());

                                // distance(location.getLatitude(),location.getLongitude(),userList.get(i).getX_konum(),userList.get(i).getY_konum());
                                if(uzaklık>minAralik && uzaklık<maxAralik)
                                {

                                    konumalarm=5;
                                    b.setKonumID(userList.get(i).getID());

                                    NotificationHelper notificationHelper = new NotificationHelper(context);
                                    NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
                                    notificationHelper.getManager().notify(1, nb.build());

                                }
                            }
                        }


                    }


                }
            }else{}


        }








    }

    private double distance(double lat1,double long1,double lat2,double lon2){
        double longdiff=long1-lon2;

        double distance=Math.sin(deg2rad(lat1))
                *Math.sin(deg2rad(lat2))
                +Math.cos(deg2rad(lat1))
                *Math.cos(deg2rad(lat2))
                *Math.cos(deg2rad(longdiff));
        distance=Math.acos(distance);
        distance=rag2deg(distance);
        distance=distance*60*1.1515;
        distance=distance*1.609344*1000;

        //System.out.println("mesaeföö"+distance);

        return distance;



    }

    private double rag2deg(double distance) {
        return (distance*180.0/Math.PI);

    }

    private double deg2rad(double lat1) {
        return (lat1*Math.PI/180.0);
    }


}


