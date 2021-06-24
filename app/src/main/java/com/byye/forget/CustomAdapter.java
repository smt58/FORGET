package com.byye.forget;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by byye on 18.08.2020.
 */

public class CustomAdapter extends BaseAdapter {
    private LayoutInflater userInflater;
    private List<user> userList;

    public CustomAdapter(Activity activity, List<user> userList) {
        userInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View lineView;
        lineView = userInflater.inflate(R.layout.listview, null);
        TextView textViewUserName = (TextView) lineView.findViewById(R.id.baslıklist);
        TextView textViewSystemClock = (TextView) lineView.findViewById(R.id.bildirimlist);
        TextView textViewtarih = (TextView) lineView.findViewById(R.id.tarihlist);
        TextView textViewsaat = (TextView) lineView.findViewById(R.id.saatlist);
        ImageView imageView=(ImageView)lineView.findViewById(R.id.imageView1);


        user user = userList.get(i);


        if(user.getPath()==null) {
            if ((user.getGun() == 0 && user.getDk() == 0 && user.getSaat() == 0 && user.getAy() == 0 && user.getYil() == 0)) {
                if(user.getX_konum()!=0){
                    textViewUserName.setText(user.getBaslık());
                    textViewSystemClock.setText(user.getBildirim());

                    imageView.setBackgroundResource(R.drawable.locationicon);

                }
                else {
                textViewUserName.setText(user.getBaslık());
                textViewSystemClock.setText(user.getBildirim());
                textViewsaat.setText("");
                textViewtarih.setText("");
                imageView.setBackgroundResource(R.drawable.iconnot);
                }
            } else {
                textViewUserName.setText(user.getBaslık());
                textViewSystemClock.setText(user.getBildirim());
                imageView.setBackgroundResource(R.drawable.calendaricon);
                textViewtarih.setText(String.valueOf(user.getGun()) + "/" + String.valueOf(user.getAy()) + "/" + String.valueOf(user.getYil()));
                textViewsaat.setText(String.valueOf(user.getSaat()) + ":" + String.valueOf(user.getDk()));
            }
        }
        else
        {
            textViewUserName.setText("SES KAYDI");
            textViewSystemClock.setText(" ");
            imageView.setBackgroundResource(R.drawable.mikraicon);
            textViewtarih.setText(String.valueOf(user.getGun()) + "/" + String.valueOf(user.getAy()) + "/" + String.valueOf(user.getYil()));
            textViewsaat.setText(String.valueOf(user.getSaat()) + ":" + String.valueOf(user.getDk()));
        }

        return lineView;
    }
}