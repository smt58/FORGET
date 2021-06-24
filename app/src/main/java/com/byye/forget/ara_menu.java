package com.byye.forget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ara_menu extends AppCompatActivity {
    CardView mykonum,myalarm,mynotdefteri,myseskayit,myinfo,myanamenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ara_menu);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        mykonum=findViewById(R.id.aramenukonum);
        myalarm=findViewById(R.id.aramenualarm);
        mynotdefteri=findViewById(R.id.aramenunotdefteri);
        myseskayit=findViewById(R.id.aramenumicro);
        myinfo=findViewById(R.id.aramenuinfo);
        myanamenu=findViewById(R.id.anamenu);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        mykonum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SAMEDİN YERİ
                Intent intent=new Intent(ara_menu.this,MapsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        myalarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ara_menu.this,alarm_save.class);
                startActivity(intent);
                finish();
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        mynotdefteri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ara_menu.this,not_defteri.class);
                startActivity(intent);
                finish();
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        myseskayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ara_menu.this,recording.class);
                startActivity(intent);
                finish();
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        myanamenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ara_menu.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        myinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ara_menu.this,app_info.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ara_menu.this);
        builder.setTitle("Çıkış")
                .setMessage("Çıkmak istediğinizden eminmisiniz?")
                .setCancelable(false)
                .setIcon(R.drawable.exit)
                .setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
        .setNegativeButton("HAYIR",null);
        //Creating dialog box
        AlertDialog dialog  = builder.create();
        dialog.show();
    }
}
