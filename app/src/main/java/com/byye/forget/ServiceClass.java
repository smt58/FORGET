package com.byye.forget;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import android.os.IBinder;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;


public class ServiceClass extends IntentService {

    private static final String TAG = ServiceClass.class.getSimpleName();

    DatabaseHelper helper;
    List<user> userList;
    GlobalD globalVariable;


    public ServiceClass() {
        super("ServiceClass");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        Log.i(TAG, "oncreate methodu " + Thread.currentThread().getName() + " threadi üzerinden cagrildi");
        super.onCreate();

        helper=DatabaseHelper.getInstance(getApplicationContext());

        globalVariable = (GlobalD) getApplicationContext();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

            NotificationChannel notificationChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID, "My app no sound", NotificationManager.IMPORTANCE_LOW
            );
            notificationChannel.setDescription("");
            notificationChannel.setSound(null,null);
            notificationChannel.enableLights(false);
            notificationChannel.enableVibration(false);
            notificationManager.createNotificationChannel(notificationChannel);
            Notification notification = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("").build();
            startForeground(1, notification);
        }

    }



    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.i(TAG, "onHandleIntent methodu " + Thread.currentThread().getName() + " threadi üzerinden cagrildi");
        Calendar mcurrentTime = Calendar.getInstance();//
        userList = helper.getAllUsers();

        for (int i = 0; i < userList.size(); i++) {

            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);//Güncel saati aldık
            int minute = mcurrentTime.get(Calendar.MINUTE);
            int yil = mcurrentTime.get(Calendar.YEAR);
            int ay = mcurrentTime.get(Calendar.MONTH);
            int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);

           // Log.i(TAG, "For içerisinde "+ userList.get(i).getDk() +"////" +userList.get(i).getSaat() +"///" +"path =" +userList.get(i).getPath());

            if(userList.get(i).getPath()==null) {
                if (userList.get(i).getYil() == yil && userList.get(i).getAy() == (ay + 1) && userList.get(i).getSaat() == hour && userList.get(i).getGun() == day && userList.get(i).getDk() == minute) {
                    Log.i(TAG, "if içerisinde " + userList.get(i).getDk());

                    globalVariable.setIDD(userList.get(i).getID());
                    globalVariable.setKod(0);
                    helper.closeDB();
                    Intent intent1 = new Intent(getApplicationContext(), BootReceiver.class);
                    sendBroadcast(intent1);


                }
            }else {
                if (userList.get(i).getYil() == yil && userList.get(i).getAy() == (ay + 1) && userList.get(i).getSaat() == hour && userList.get(i).getGun() == day && userList.get(i).getDk() == minute) {
                    Log.i(TAG, "if içerisinde " + userList.get(i).getDk());

                    globalVariable.setIDD(userList.get(i).getID());
                    globalVariable.setKod(1);
                    helper.closeDB();
                    Intent intent1 = new Intent(getApplicationContext(), BootReceiver.class);
                    sendBroadcast(intent1);


                }
            }
        }

    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(true);
        }
    }

}
