package com.example.myapplication3;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication3.data.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UseSeat extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private String deviceToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_seat);

        SharedPreferences sharedPreferences = getSharedPreferences("token", MODE_PRIVATE);
        deviceToken = sharedPreferences.getString("inputToken", "");

        // firebase 정의
        mDatabase = FirebaseDatabase.getInstance().getReference();

        seat("1");

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
                        notSeat("1");

                        //메인화면으로 이동
                        Intent takeoffIntent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(takeoffIntent);
                    }
                });

            }
        });
    }

    private void seat(String seatNumber) {
        useSeatID(seatNumber, deviceToken);
        useSeatToken(seatNumber, deviceToken);
    }

    private void notSeat(String seatNumber) {
        useSeat(seatNumber, "");
        useSeatToken(seatNumber, "");
    }

    private void useSeat(String seatNumber, String userID) {

        final DatabaseReference seatRef = mDatabase.child("Seats").child("seat"+seatNumber).child("seatUser");

        seatRef.setValue(userID)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        //Toast.makeText(RegisterActivity.this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        Toast.makeText(UseSeat.this, "저장을 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void useSeatToken(String seatNumber, String deviceToken) {

        final DatabaseReference seatRef = mDatabase.child("Seats").child("seat"+seatNumber).child("seatToken");

        seatRef.setValue(deviceToken)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        //Toast.makeText(RegisterActivity.this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        Toast.makeText(UseSeat.this, "저장을 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void useSeatID(String seatNumber, String deviceToken) {

        final DatabaseReference userRef = mDatabase.child("Users").child(deviceToken);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    User user = snapshot.getValue(User.class);

                    useSeat(seatNumber, user.getUserID());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }


    private void showSeatStatus(String seatNumber) {

        final DatabaseReference seatRef = mDatabase.child("Seats").child("seat"+seatNumber).child("seatUser");

        setContentView(R.layout.activity_seat_view);

        Button button1_3_1 = findViewById(R.id.button1_3_1);

        seatRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String seatUser = (String) snapshot.getValue();

                    if (!seatUser.equals("")) {
                        button1_3_1.setBackgroundColor(getColor(R.color.usedSeat));
                    }
                    else {
                        button1_3_1.setBackgroundColor(getColor(R.color.emptySeat));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}