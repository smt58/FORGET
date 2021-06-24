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

public class konum_baslik extends AppCompatActivity {
    EditText konumbaslik,konumbildirim;
    CardView konumsave;

    String sbaslık, sbildirim;
    user user1 = new user();
    DatabaseHelper helper;

    public double enlem,boylam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konum_baslik);

        konumbaslik=findViewById(R.id.konumbaslik);
        konumbildirim=findViewById(R.id.konumbildirim);

        konumsave=findViewById(R.id.konumsave);

        helper = DatabaseHelper.getInstance(konum_baslik.this);

        konumsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sbaslık = konumbaslik.getText().toString();
                sbildirim = konumbildirim.getText().toString();

                user1.setBaslık(sbaslık);
                user1.setBildirim(sbildirim);

                user1.setX_konum(getirenlem());
                user1.setY_konum(getirboylam());

                long result = helper.createUser(user1);

                if(result>0) {
                    LayoutInflater li2 = getLayoutInflater();
                    View layout2 = li2.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.custom_toast_layout_id));
                    TextView text = (TextView) layout2.findViewById(R.id.text);
                    ImageView img = (ImageView) layout2.findViewById(R.id.imageView);
                    text.setText(R.string.toastkonum);
                    img.setBackgroundResource(R.drawable.locationicon);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout2);//setting the view of custom toast layout
                    toast.show();
                    helper.closeDB();

                    Intent i = new Intent(konum_baslik.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }


            }
        });
    }


    private double getirenlem() {
        Intent receiveIntent = this.getIntent();
        enlem = receiveIntent.getDoubleExtra("enlem", 0.00);
        return enlem;

    }

    private double getirboylam() {
        Intent receiveIntent = this.getIntent();
        boylam = receiveIntent.getDoubleExtra("boylam", 0.00);
        return boylam;
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(konum_baslik.this,MapsActivity.class);
        startActivity(intent);
        finish();

    }
}
