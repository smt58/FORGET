package com.byye.forget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.byye.forget.alarm_save.emre;

public class recording extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    Button recordinginfo;
    CardView  buttonStart, buttonStop, buttonPlayLastRecordAudio, alarmkurses,saveses,
            buttonStopPlayingRecording;
    TextView sayac;
    user user1 = new user();
    String AudioSavePathInDevice = null;
    MediaRecorder mediaRecorder ;
    Random random ;
    CountUpTimer timer;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    public static final int RequestPermissionCode = 1;
    MediaPlayer mediaPlayer ;
    DatabaseHelper helper;
    int day, month, year, hour, minute;
    int myday, myMonth, myYear, myHour, myMinute;
    GlobalD b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        emre=2;
        buttonStart = findViewById(R.id.cardstart);
        buttonStop =  findViewById(R.id.cardstop);
        buttonPlayLastRecordAudio = findViewById(R.id.cardplay);
        buttonStopPlayingRecording = findViewById(R.id.cardyeniden);
        alarmkurses=findViewById(R.id.cardtarihvesaat);
        saveses=findViewById(R.id.cardsave);

        recordinginfo=findViewById(R.id.recordinginfo);

        sayac=findViewById(R.id.sestextview);

        buttonStop.setEnabled(false);
        buttonPlayLastRecordAudio.setEnabled(false);
        buttonStopPlayingRecording.setEnabled(false);

        random = new Random();

        helper = DatabaseHelper.getInstance(recording.this);


        recordinginfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(recording.this);
                builder.setTitle("Bilgilendirme")
                        .setMessage("Ses kaydı maksimum 30 saniye alabilirsiniz. Seçtiğiniz " +
                                "gün ve saat de telefonunuz size bildirim olarak haber verir. " +
                                "Bildirime tıkladığınız da kaydetmiş olduğunuz sesi dinleyebilirsiniz. ")
                        .setCancelable(false)
                        .setPositiveButton("OK", null );
                //Creating dialog box
                AlertDialog dialog  = builder.create();
                dialog.show();
            }
        });

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkPermission()) {
                    counter();
                    AudioSavePathInDevice =
                            Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                                    CreateRandomAudioFileName(5) + "forget.3gp";

                    user1.setPath(AudioSavePathInDevice);



                    MediaRecorderReady();

                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    buttonStart.setEnabled(false);
                    buttonStop.setEnabled(true);

                    Toast.makeText(recording.this, R.string.sesbasladı,
                            Toast.LENGTH_LONG).show();
                } else {
                    requestPermission();
                }

            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                mediaRecorder.stop();
                buttonStop.setEnabled(false);
                buttonPlayLastRecordAudio.setEnabled(true);
                buttonStart.setEnabled(true);
                buttonStopPlayingRecording.setEnabled(false);

                Toast.makeText(recording.this, R.string.sesdurdu,
                        Toast.LENGTH_LONG).show();
            }
        });

        buttonPlayLastRecordAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) throws IllegalArgumentException,
                    SecurityException, IllegalStateException {

                buttonStop.setEnabled(false);
                buttonStart.setEnabled(false);
                buttonStopPlayingRecording.setEnabled(true);

                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(AudioSavePathInDevice);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();

            }
        });



        buttonStopPlayingRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sayac.setText(String.valueOf(0));
                Toast.makeText(recording.this, R.string.seshazır,
                        Toast.LENGTH_LONG).show();
                buttonStop.setEnabled(false);
                buttonStart.setEnabled(true);
                buttonStopPlayingRecording.setEnabled(false);
                buttonPlayLastRecordAudio.setEnabled(true);

                if(mediaPlayer != null){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    MediaRecorderReady();
                }
            }
        });

        alarmkurses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(recording.this, recording.this, year, month, day);
                datePickerDialog.show();
            }
        });

        saveses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                long result = helper.createUser(user1);

                if(result>0) {

                    LayoutInflater li2 = getLayoutInflater();
                    View layout2 = li2.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.custom_toast_layout_id));
                    TextView text = (TextView) layout2.findViewById(R.id.text);
                    text.setText(R.string.toastrecording);
                    ImageView img=(ImageView)layout2.findViewById(R.id.imageView);
                    img.setBackgroundResource(R.drawable.mikraicon);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout2);//setting the view of custom toast layout
                    toast.show();

                    //////////////////////////////
                    helper.closeDB();
                    if(emre==2){
                        Intent i = new Intent(recording.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }else
                        {

                        scheduleAlarm();

                        Intent i = new Intent(recording.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }

                    ////////////////////////

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
        TimePickerDialog timePickerDialog = new TimePickerDialog(recording.this, recording.this, hour, minute, true);
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

    public void MediaRecorderReady(){
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }

    public String CreateRandomAudioFileName(int string){
        StringBuilder stringBuilder = new StringBuilder( string );
        int i = 0 ;
        while(i < string ) {
            stringBuilder.append(RandomAudioFileName.
                    charAt(random.nextInt(RandomAudioFileName.length())));

            i++ ;
        }
        return stringBuilder.toString();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(recording.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {

                    } else {
                        Toast.makeText(recording.this,R.string.sesizin
                               ,Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }

    public void counter(){

        timer=new CountUpTimer(30000) {
            @Override
            public void onTick(int second) {
                sayac.setText(String.valueOf(second));
            }
        };

        timer.start();
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(recording.this,ara_menu.class);
        startActivity(intent);
        finish();
    }

}