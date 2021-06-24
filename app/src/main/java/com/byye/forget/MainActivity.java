package com.byye.forget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button plus;
    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        plus=findViewById(R.id.plus);

        //databasede olana tüm verileri alıp list de ekler

        helper=DatabaseHelper.getInstance(getApplicationContext());

        final List<user> userList = helper.getAllUsers();
        //alınan verileri listviewde göstermeği sağlayan kod kısmı
        final ListView listView = (ListView) findViewById(R.id.listview);
            CustomAdapter adapter = new CustomAdapter(this, userList);
            listView.setAdapter(adapter);


            //listviewde tıklanan itemlerin gideceği activityi gösteren kod kısmı
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                helper.closeDB();
                Intent i = new Intent(MainActivity.this, update_and_delete.class);
                i.putExtra("item", userList.get(position).getID());
                startActivity(i);
                finish();

            }
        });

            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    helper.closeDB();
                    Intent i = new Intent(MainActivity.this, ara_menu.class);
                    startActivity(i);
                    finish();
                }
            });

        }

    @Override
    public void onBackPressed() {
        Intent ıntent=new Intent(MainActivity.this,ara_menu.class);
        startActivity(ıntent);
        finish();
    }


}
