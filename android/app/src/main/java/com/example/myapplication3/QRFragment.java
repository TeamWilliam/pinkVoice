package com.example.myapplication3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class QRFragment extends Fragment {
    String QR;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(QRFragment.this);

        integrator.setCaptureActivity(CaptureForm.class); //큐알 꾸민거
        integrator.setOrientationLocked(false); //큐알 세로화면
        integrator.setPrompt("Scan QR code");
        integrator.setBeepEnabled(false); //큐알 스캔되면 효과음 나중에 필요하면 true로 바꾸기
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);

        integrator.initiateScan();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_q_r, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result!=null){
            //스캔 안된 경우
            if(result.getContents() == null){
                Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
            } else{ //스캔 된 경우
                Toast.makeText(getContext(), "Scanned : " + result.getContents(), Toast.LENGTH_LONG).show();

                //알림창 테스트
                QR = result.getContents();//큐알코드 스캔 내용 저장
                Dialog dialog;//다이얼로그 생성
                dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_alert);//커스텀다이얼로그연결
                TextView tv = (TextView)dialog.findViewById(R.id.noti);
                tv.setText("\n"+QR+"번째 좌석입니다.\n 이용하시겠습니까?\n");//큐알 스캔내용 출력
                dialog.show();
                //Cancel 버튼 핸들러
                Button noBtn = dialog.findViewById(R.id.custom_cancel);
                noBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(),"아니오를 선택했습니다.",Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
                //OK 버튼 핸들러
                dialog.findViewById(R.id.custom_confirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(),QR+"번째 좌석을 이용합니다.",Toast.LENGTH_LONG).show();
                        //좌석 이용중으로 디비 바꾸기

                        //이용중 화면으로 전환
                        Intent usingIntent = new Intent(getActivity(), UseSeat.class);
                        startActivity(usingIntent);
                    }
                });
            }
        }
    }
}