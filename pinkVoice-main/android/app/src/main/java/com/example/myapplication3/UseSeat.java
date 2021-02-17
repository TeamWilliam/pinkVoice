package com.example.myapplication3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UseSeat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_seat);

        //하차하기 버튼
        Button takeoffBtn = (Button)findViewById(R.id.takeoff);
        takeoffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog takeoff_dialog = new Dialog(UseSeat.this);
                takeoff_dialog.setContentView(R.layout.dialog_alert);
                TextView takeoff_text = (TextView)takeoff_dialog.findViewById(R.id.noti);
                takeoff_text.setText("\n이용을 완료하시겠습니까?\n");
                takeoff_dialog.show();

            }
        });
    }
}