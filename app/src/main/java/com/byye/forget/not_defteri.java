package com.byye.forget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class not_defteri extends AppCompatActivity {
    EditText notdefteribaslik,notdefteribildirim;
    CardView notdefterisave;

    String sbaslık, sbildirim;
    user user1 = new user();
    DatabaseHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_defteri);

        notdefteribaslik=findViewById(R.id.notdefteribaslik);
        notdefteribildirim=findViewById(R.id.notdefteribildirim);

        notdefterisave=findViewById(R.id.notdefterisave);

        helper = DatabaseHelper.getInstance(not_defteri.this);

        notdefterisave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sbaslık = notdefteribaslik.getText().toString();
                sbildirim = notdefteribildirim.getText().toString();

                user1.setBaslık(sbaslık);
                user1.setBildirim(sbildirim);

                long result = helper.createUser(user1);

                if(result>0) {
                    LayoutInflater li2 = getLayoutInflater();
                    View layout2 = li2.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.custom_toast_layout_id));
                    TextView text = (TextView) layout2.findViewById(R.id.text);
                    ImageView img=(ImageView)layout2.findViewById(R.id.imageView);
                    text.setText(R.string.toastNotunuzOlusturuldu);
                    img.setBackgroundResource(R.drawable.iconnot);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout2);//setting the view of custom toast layout
                    toast.show();
                    ///////////////////////////////////////////////////////////////////////////////////////
                    NotdefteriKayit();
                    helper.closeDB();
                    //////////////////
                    Intent i = new Intent(not_defteri.this, MainActivity.class);
                    startActivity(i);
                    finish();


                }

                }
        });



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
        Intent intent=new Intent(not_defteri.this,ara_menu.class);
        startActivity(intent);
        finish();
    }

}
