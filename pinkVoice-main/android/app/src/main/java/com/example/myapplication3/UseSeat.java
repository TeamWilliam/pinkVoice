package com.example.myapplication3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
                //Cancel 버튼 핸들러
                Button noBtn = takeoff_dialog.findViewById(R.id.custom_cancel);
                noBtn.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(UseSeat.this,"아니오를 선택했습니다.",Toast.LENGTH_LONG).show();
                        takeoff_dialog.dismiss();
                    }
                });
                //OK 버튼 핸들러
                Button yesBtn = takeoff_dialog.findViewById(R.id.custom_confirm);
                yesBtn.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(UseSeat.this,"이용을 종료합니다.",Toast.LENGTH_LONG).show();
                        //이용 종료 디비에서 좌석 상태 빈좌석으로 바꾸기


                        //메인화면으로 이동
                        Intent takeoffIntent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(takeoffIntent);
                    }
                });

            }
        });
    }
}