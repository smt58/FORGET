package com.byye.forget;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.io.IOException;

// Ses kaydı alnındıkdan sonra , saat ve tarih uyumlu olduğuunda bildirime tıklanınca açılan activity
// açılan pencerede direk ses dinlenir
// tek button ile de kapatılır.

public class save extends AppCompatActivity {

    user user1 = new user();
    int userid;
     String path;
    MediaPlayer mediaPlayer ;
    Button seskes,sesdevam;
    DatabaseHelper helper;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        seskes=findViewById(R.id.seskes);
        sesdevam=findViewById(R.id.sesdevam);

        GlobalD globalVariable = (GlobalD) getApplicationContext();

        userid=globalVariable.getIDD();

        //databaseden tüm kullanıcıları listeye ekliyor
        helper=DatabaseHelper.getInstance(getApplicationContext());

        // alınan kullanıcılar içinde saat ve dk aynı olan kullanıcının id sini alıp user2 yi setliyor
        user1=helper.getUser(userid);

        path=user1.getPath();

        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        sesdevam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
            }
        });



        seskes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                recording a=new recording();
                a.MediaRecorderReady();
                helper.closeDB();
                Intent intent=new Intent(save.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(save.this,ara_menu.class);
        startActivity(intent);
        finish();
    }

}


