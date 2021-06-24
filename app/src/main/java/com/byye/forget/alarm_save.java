package com.byye.forget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class alarm_save extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    Button alarmsaveinfo;
    EditText alarmbaslik,alarmbildirim;
    CardView alarmsave,alarmtarihvesaat;
    String sbaslık, sbildirim;
    user user1 = new user();
    int day, month, year, hour, minute;
    int myday, myMonth, myYear, myHour, myMinute;
    DatabaseHelper helper;
    public static int emre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_save);

        alarmsave=findViewById(R.id.alarmsave);
        alarmtarihvesaat=findViewById(R.id.alarmtarihvesaat);
        alarmsaveinfo=findViewById(R.id.alarmsaveinfo);

        alarmbaslik=findViewById(R.id.alarmbaslik);
        alarmbildirim=findViewById(R.id.alarmbildirim);

        helper = DatabaseHelper.getInstance(alarm_save.this);


        alarmsaveinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(alarm_save.this);
                builder.setTitle("Information")
                        .setMessage("After entering the title and note information, the selected date and time will also inform you."
                                + "Sends notification to your phone with 9 seconds of notification.")
                        .setCancelable(false)
                        .setPositiveButton("OK", null );
                //Creating dialog box
                AlertDialog dialog  = builder.create();
                dialog.show();

            }
        });

        alarmtarihvesaat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(alarm_save.this, alarm_save.this, year, month, day);
                datePickerDialog.show();

            }
        });


        alarmsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sbaslık = alarmbaslik.getText().toString();
                sbildirim = alarmbildirim.getText().toString();

                user1.setBaslık(sbaslık);
                user1.setBildirim(sbildirim);

                long result = helper.createUser(user1);

                if(result>0) {
                    /////////////////////////
                    LayoutInflater li2 = getLayoutInflater();
                    View layout2 = li2.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.custom_toast_layout_id));
                    TextView text = (TextView) layout2.findViewById(R.id.text);
                    text.setText(R.string.toastalarmkuruldu);
                    ImageView img = (ImageView) layout2.findViewById(R.id.imageView);
                    img.setBackgroundResource(R.drawable.calendaricon);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout2);//setting the view of custom toast layout
                    toast.show();
                    //////////////////////////////
                    ///////////////////////////
                    helper.closeDB();
                    scheduleAlarm();

                    ////////////////////////
                    Intent i = new Intent(alarm_save.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        myYear = year;
        myday = dayOfMonth;
        myMonth = month;
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(alarm_save.this, alarm_save.this, hour, minute, true);
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        myHour = hourOfDay;
        myMinute = minute;
        user1.setYil(myYear);
        user1.setAy(myMonth + 1);
        user1.setGun(myday);
        user1.setSaat(myHour);
        user1.setDk(myMinute);

    }

    public void scheduleAlarm() {
        emre=2;
        // Construct an intent that will execute the AlarmReceiver
        Intent intent = new Intent(getApplicationContext(), myBackgroundProcess.class);
        // Create a PendingIntent to be triggered when the alarm goes off
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, myBackgroundProcess.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Setup periodic alarm every every half hour from this point onwards
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                29 * 1000, pIntent);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(alarm_save.this,ara_menu.class);
        startActivity(intent);
        finish();
    }


}
