package com.example.myapplication3;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SubwayTimeActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subway_time);

        Button btn = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    
    }


<<<<<<< Updated upstream
                textView.setText(sb.toString());
                progress.dismiss();
            }

            @Override
            protected String doInBackground(Void... params) {
                String result = "";
                try {
                    //서울시 오픈 API 제공(샘플 주소 json으로 작업)
                    result = Remote.getData("http://swopenapi.seoul.go.kr/api/subway/534c5457527373753333556c6c476f/xml/realtimeStationArrival/0/5/충무로");

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return result;
            }
        }.execute();

    }
=======
>>>>>>> Stashed changes
}
