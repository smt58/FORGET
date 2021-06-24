package com.byye.forget;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;

import static com.byye.forget.MyLocationService.konumalarm;

public class NotificationHelper extends ContextWrapper {

    public static final String channelID = "channelID";
    public  final String channelName = "Channel Name";
    int kod;
    user user2=new user();
    String a,b;
    Integer userid;
    MediaPlayer mp;

    private NotificationManager mManager;
    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();

        }
    }
    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);

    }
    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }


        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification() {

        //Sesi buradan alıyor ve   mp.start(); başlattıyor
        mp = MediaPlayer.create(getApplicationContext(), Settings.System.DEFAULT_ALARM_ALERT_URI);

        mp.start();

        new CountDownTimer(9000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                mp.stop();
                mp.release();
            }

        }.start();

        // burada bir sayıcı var 10 sn boyunca ses çalıyor ve 10 sn bittikden sonra onFinish fonk çağırıyor onu içinde de mp.stop fonk. sesi keser


        //burada global olan saat ve dk yı alıyorum
        GlobalD globalVariable = (GlobalD) getApplicationContext();

        userid = globalVariable.getIDD();
        kod = globalVariable.getKod();
        //databaseden tüm kullanıcıları listeye ekliyor
        DatabaseHelper helper = DatabaseHelper.getInstance(getApplicationContext());

        // alınan kullanıcılar içinde saat ve dk aynı olan kullanıcının id sini alıp user2 yi setliyor

/////////////////////////////////////////////////////////////////////////////////////////////////BAKILACAK
        if (konumalarm > 0) {

            Integer id = globalVariable.getKonumID();
            user2 = helper.getUser(id);

            //user2 nin başlık ve bildirimini alıor
            a = user2.getBaslık();
            b = user2.getBildirim();

            //uygulama kappalı olduğunda veri tabanı çalışmadığından gelen Stringler null ise genel String ataması yapıyor
            if (a == null || b == null) {
                a = "ALARM";
                b = "EŞYALARINI UNUTMA BİLDİRİME TIKLA VE GÖR";
            }


            //Aşağıdaki kod kısmında ise Bildirim oluışturuyor setContentTitle fonk. bildirim başlığını, setContentText fonk. yazılacak bildirimi gösterir
            //setContentIntent fonk ise Intent ile gösterilen( Intent intent = new Intent(this, MainActivity.class);) yere Bildirime tıklandığında geçiş yapar

            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, intent, 0);

            return new NotificationCompat.Builder(getApplicationContext(), channelID)
                    .setContentTitle(a)
                    .setContentText(b)
                    .setColor(Color.BLUE)
                    .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.remember))
                    .setSmallIcon(R.drawable.plus1)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent);

        } else {

            user2 = helper.getUser(userid);

            if (kod == 0) {
                //user2 nin başlık ve bildirimini alıor
                a = user2.getBaslık();
                b = user2.getBildirim();

                //uygulama kappalı olduğunda veri tabanı çalışmadığından gelen Stringler null ise genel String ataması yapıyor
                if (a == null || b == null) {
                    a = "ALARM";
                    b = "EŞYALARINI UNUTMA BİLDİRİME TIKLA VE GÖR";
                }
                helper.closeDB();
                //Aşağıdaki kod kısmında ise Bildirim oluışturuyor setContentTitle fonk. bildirim başlığını, setContentText fonk. yazılacak bildirimi gösterir
                //setContentIntent fonk ise Intent ile gösterilen( Intent intent = new Intent(this, MainActivity.class);) yere Bildirime tıklandığında geçiş yapar

                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, intent, 0);

                return new NotificationCompat.Builder(getApplicationContext(), channelID)
                        .setContentTitle(a)
                        .setContentText(b)
                        .setColor(Color.BLUE)
                        .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.remember))
                        .setSmallIcon(R.drawable.plus1)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent);
            } else {
                helper.closeDB();
                Intent intent = new Intent(this, save.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 3, intent, 0);

                return new NotificationCompat.Builder(getApplicationContext(), channelID)
                        .setContentTitle("SES KAYDI")
                        .setContentText("DİNLEMEK İÇİN TIKLA")
                        .setColor(Color.BLUE)
                        .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.remember))
                        .setSmallIcon(R.drawable.plus1)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent);
            }
            //bildirim tıklanınca silinmesi.
        }
    }


}
