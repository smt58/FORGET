package com.byye.forget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class app_info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(app_info.this,ara_menu.class);
        startActivity(intent);
        finish();

    }
}
