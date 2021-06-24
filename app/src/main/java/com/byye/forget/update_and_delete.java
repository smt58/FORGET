package com.byye.forget;

import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Random;

public class update_and_delete extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    EditText baslikupdate,bildirimupdate;
    CardView update,delete,updateopsiyon;
    String sbaslikupdate, sbildirimupdate;
    Integer positions;
    user user1 = new user();
    DatabaseHelper helper;
    int day, month, year, hour, minute;
    static int kod;
    int myday, myMonth, myYear, myHour, myMinute;
    Button updateinfo;
    Random random ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_and_delete);

        baslikupdate =findViewById(R.id.baslikupdate);
        bildirimupdate=findViewById(R.id.bildirimupdate);
        update=findViewById(R.id.update);
        delete=findViewById(R.id.delete);
        updateopsiyon=findViewById(R.id.updateopsiyon);
        updateinfo=findViewById(R.id.updateinfo);

        positions = getIntent().getExtras().getInt("item");


        helper = DatabaseHelper.getInstance(update_and_delete.this);
        random = new Random();



      //  final List<user> userList = helper.getAllUsers();

        user1=helper.getUser(positions);

        baslikupdate.setText(user1.getBaslık());
        bildirimupdate.setText(user1.getBildirim());

        updateopsiyon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(update_and_delete.this, v);
                popup.setOnMenuItemClickListener(update_and_delete.this);
                popup.inflate(R.menu.popup_menu);
                popup.show();
            }
        });

       update.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               sbaslikupdate = baslikupdate.getText().toString();
               sbildirimupdate=bildirimupdate.getText().toString();

               user1.setBaslık(sbaslikupdate);
               user1.setBildirim(sbildirimupdate);

               int result = helper.updateUser(user1);



               if (result > 0) {

                   if(kod==0){
                       LayoutInflater li2 = getLayoutInflater();
                       View layout2 = li2.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.custom_toast_layout_id));
                       TextView text = (TextView) layout2.findViewById(R.id.text);
                       text.setText(R.string.toastguncellendi);
                       ImageView img=(ImageView)layout2.findViewById(R.id.imageView);
                       img.setBackgroundResource(R.drawable.updateicon);
                       Toast toast = new Toast(getApplicationContext());
                       toast.setDuration(Toast.LENGTH_LONG);
                       toast.setView(layout2);//setting the view of custom toast layout
                       toast.show();

                       helper.closeDB();

                       Intent i=new Intent(update_and_delete.this,MainActivity.class);
                       startActivity(i);
                       finish();
                   }
                   if(kod==2){
                       NotdefteriKayit();
                       helper.closeDB();
                       Intent i=new Intent(update_and_delete.this,MainActivity.class);
                       startActivity(i);
                       finish();
                   }

               } else {
                   Toast.makeText(update_and_delete.this,"HATA", Toast.LENGTH_LONG).show();
               }

           }
       });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SweetAlertDialog(update_and_delete.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(R.string.alertbaslik)
                        .setContentText("Başlık Silinecek")
                        .setConfirmText("EVET")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                helper.deleteUser(user1.getID());
                                Toast.makeText(update_and_delete.this,R.string.silinditoast, Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(update_and_delete.this,MainActivity.class);
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                startActivity(intent);
                                sDialog.dismissWithAnimation();
                                finish();
                            }
                        })
                        .setCancelButton("HAYIR", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();



            }
        });

        updateinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(update_and_delete.this);
                builder.setTitle("Bilgilendirme")
                        .setMessage("Yalnızca not defteri ve tarih ve saat başlıklarını güncelleyebilirsiniz. " +
                                "Tüm başlıkları silebilirsiniz .")
                        .setCancelable(false)
                        .setPositiveButton("OK", null );
                //Creating dialog box
                AlertDialog dialog  = builder.create();
                dialog.show();

            }
        });


    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.wifi_item:

                kod=2;

                return true;
            case R.id.dateandtime_item:

                kod=0;

                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(update_and_delete.this, update_and_delete.this,year, month,day);
                datePickerDialog.show();

                return true;


            default:
                return false;

        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        myYear = year;
        myday = dayOfMonth;
        myMonth = month;
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(update_and_delete.this, update_and_delete.this, hour, minute, true);
        timePickerDialog.show();
    }
    @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
             myHour = hourOfDay;
             myMinute = minute;

             user1.setYil(myYear);
             user1.setAy(myMonth+1);
             user1.setGun(myday);
             user1.setSaat(myHour);
             user1.setDk(myMinute);
    }
    private void NotdefteriKayit() {

        user1.setDk(0);
        user1.setSaat(0);
        user1.setGun(0);
        user1.setAy(0);
        user1.setYil(0);

    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(update_and_delete.this,MainActivity.class);
        startActivity(intent);
        finish();

    }

}
