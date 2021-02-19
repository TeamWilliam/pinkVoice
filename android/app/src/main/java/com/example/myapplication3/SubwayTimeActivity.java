package com.example.myapplication3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import android.widget.EditText;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SubwayTimeActivity extends AppCompatActivity {

    String data;
    String dataStationName;
    String dataSubwayTimeUp;
    String dataCurrentTime;
    String dataSubwayTimeDown;

    TextView stationName;
    TextView currentTime;
    TextView up;
    TextView down;

    EditText edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subway_time);
        final CountDownTimer[] countDownTimer = new CountDownTimer[2];
        stationName= (TextView)findViewById(R.id.stationName);
        currentTime= (TextView)findViewById(R.id.currentTime);
        up = (TextView)findViewById(R.id.up);
        down = (TextView)findViewById(R.id.down);

        TextView textdown, textup;
        textup = (TextView) findViewById(R.id.up);
        textdown = (TextView) findViewById(R.id.down);

        textup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(getBaseContext(), SeatView.class);
                startActivity(registerIntent);
                finish();
            }
        });

        textdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(getBaseContext(), SeatView.class);
                startActivity(registerIntent);
                finish();
            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {
                //data=getXmlData();
                dataStationName = getStationNum();
                dataCurrentTime = getCurrentTime();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //text.setText(data);
                        stationName.setText(dataStationName);
                        currentTime.setText(dataCurrentTime);
                        countDownTimer[0] = new CountDownTimer(200000,1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                dataSubwayTimeUp = getSubwayTimeUp();
                                up.setText(dataSubwayTimeUp);
                            }

                            @Override
                            public void onFinish() {

                            }
                        };
                        countDownTimer[0].start();

                        countDownTimer[1] = new CountDownTimer(200000,1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                dataSubwayTimeDown = getSubwayTimeDown();
                                down.setText(dataSubwayTimeDown);
                            }

                            @Override
                            public void onFinish() {

                            }
                        };
                        countDownTimer[1].start();
                    }
                });
            }
        }).start();
    }

//    String getXmlData(){
//        Button button = findViewById(R.id.Chungmuro);
//        StringBuffer buffer=new StringBuffer();
//        String location = URLEncoder.encode("충무로");
//
//        String queryUrl="http://swopenAPI.seoul.go.kr/api/subway/534c5457527373753333556c6c476f/xml/realtimeStationArrival/0/5/"+location;
//        try{
//            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
//            InputStream is= url.openStream(); //url위치로 입력스트림 연결
//
//            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();//xml파싱을 위한
//            XmlPullParser xpp= factory.newPullParser();
//            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기
//
//            String tag;
//
//            xpp.next();
//            int eventType= xpp.getEventType();
//            while( eventType != XmlPullParser.END_DOCUMENT ){
//                switch( eventType ){
//                    case XmlPullParser.START_DOCUMENT:
//                        buffer.append("파싱 시작...\n\n");
//                        break;
//
//                    case XmlPullParser.START_TAG:
//                        tag= xpp.getName();//테그 이름 얻어오기
//
//                        if(tag.equals("subwayId")){
//                            xpp.next();
//                            String str = xpp.getText();
//                            if (str.equals("1004")){
//                                buffer.append("4호선");
//                                buffer.append("\n");
//                            }
//                            else if (str.equals("1003")){
//                                buffer.append("3호선");
//                                buffer.append("\n");
//                            }
//                        }
//                        else if(tag.equals("updnLine")){
//                            buffer.append("상하행 정보 : ");
//                            xpp.next();
//                            buffer.append(xpp.getText());//title 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                            buffer.append("\n"); //줄바꿈 문자 추가
//                        }
//
//                        else if(tag.equals("bstatnNm")){
//                            xpp.next();
//                            buffer.append(xpp.getText());//category 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                            buffer.append("행 열차");
//                            buffer.append("\n");//줄바꿈 문자 추가
//                        }
//                        else if(tag.equals("arvlMsg2")){
//                            buffer.append("열차 도착정보 :");
//                            xpp.next();
//                            buffer.append(xpp.getText());//telephone 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                            buffer.append("\n");//줄바꿈 문자 추가
//                        }
//                        break;
//
//                    case XmlPullParser.TEXT:
//                        break;
//
//                    case XmlPullParser.END_TAG:
//                        tag= xpp.getName(); //테그 이름 얻어오기
//
//                        if(tag.equals("item")) buffer.append("\n");// 첫번째 검색결과종료..줄바꿈
//                        break;
//                }
//
//                eventType= xpp.next();
//            }
//
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        buffer.append("파싱 끝\n");
//        return buffer.toString();//StringBuffer 문자열 객체 반환
//    }

    //상행선 가져오기
    String getSubwayTimeUp(){
//        Button button = findViewById(R.id.Chungmuro);
//        StringBuffer buffer=new StringBuffer();
//        String location = URLEncoder.encode("충무로");
//
//        String queryUrl="http://swopenAPI.seoul.go.kr/api/subway/534c5457527373753333556c6c476f/xml/realtimeStationArrival/0/8/"+location;
//        try{
//            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
//            InputStream is= url.openStream(); //url위치로 입력스트림 연결
//
//            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();//xml파싱을 위한
//            XmlPullParser xpp= factory.newPullParser();
//            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기
//
//            String tag;
//
//            xpp.next();
//            int eventType= xpp.getEventType();
//            while( eventType != XmlPullParser.END_DOCUMENT ){
//                switch( eventType ){
//                    case XmlPullParser.START_DOCUMENT:
//                        buffer.append("파싱 시작...\n\n");
//                        break;
//
//                    case XmlPullParser.START_TAG:
//                        tag= xpp.getName();//테그 이름 얻어오기
//
//                        if(tag.equals("subwayId")){
//                            xpp.next();
//                            String str1 = xpp.getText();
//                            if (str1.equals("1003")){
//                                buffer.append("\n");
//                                buffer.delete(0,tag.length());
//                                buffer.append("상행");
//                            }
//                        }
//                        else if(tag.equals("bstatnNm")){
//                            xpp.next();
//                            String str2 = xpp.getText();
//                            if (str2.equals("오금")){
//                                buffer.append("\n");
//                                buffer.delete(2,tag.length());
//                                buffer.append(str2);
//                            }
//
//                        }
//                        break;
//
//                }
//                eventType= xpp.next();
//            }
//
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        buffer.append("지하철 없음\n");
//        return buffer.toString();//StringBuffer 문자열 객체 반환
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        StringBuffer buffer = new StringBuffer();;
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int c_hour = calendar.get(Calendar.HOUR_OF_DAY);
        int c_min = calendar.get(Calendar.MINUTE);
        int c_sec = calendar.get(Calendar.SECOND);

        Calendar baseCal = new GregorianCalendar(year,month,day,c_hour,c_min,c_sec);
        Calendar targetCal = new GregorianCalendar(year,month,day,c_hour+1,c_min+13,23);  //비교대상날짜

        Calendar baseCal2 = new GregorianCalendar(year,month,day,c_hour,c_min,c_sec);
        Calendar targetCal2 = new GregorianCalendar(year,month,day,c_hour+1,c_min+8,37);

        long diffSec = (targetCal.getTimeInMillis() - baseCal.getTimeInMillis()) / 1000;
        long diffDays = diffSec / (24*60*60);

        long diffSec2 = (targetCal2.getTimeInMillis() - baseCal2.getTimeInMillis()) / 1000;
        long diffDays2 = diffSec2 / (24*60*60);

        targetCal.add(Calendar.DAY_OF_MONTH, (int)(-diffDays));
        targetCal2.add(Calendar.DAY_OF_MONTH, (int)(-diffDays2));

        int hourTime = (int)Math.floor((double)(diffSec/3600));
        int minTime = (int)Math.floor((double)(((diffSec - (3600 * hourTime)) / 60)));
        int secTime = (int)Math.floor((double)(((diffSec - (3600 * hourTime)) - (60 * minTime))));

        int hourTime2 = (int)Math.floor((double)(diffSec2/3600));
        int minTime2 = (int)Math.floor((double)(((diffSec2 - (3600 * hourTime2)) / 60)));
        int secTime2 = (int)Math.floor((double)(((diffSec2 - (3600 * hourTime2)) - (60 * minTime2))));

        String min = String.format("%02d", minTime);
        String sec = String.format("%02d", secTime);

        String min2 = String.format("%02d", minTime2);
        String sec2 = String.format("%02d", secTime2);

        buffer.append("구파발행  "+min + " 분 "+ sec + "초\n\n");
        buffer.append("대화행     "+min2 + " 분 "+ sec2 + "초");
        return buffer.toString();
    }

    //하행선 가져오기
    String getSubwayTimeDown(){
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        StringBuffer buffer = new StringBuffer();;
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int c_hour = calendar.get(Calendar.HOUR_OF_DAY);
        int c_min = calendar.get(Calendar.MINUTE);
        int c_sec = calendar.get(Calendar.SECOND);

        Calendar baseCal = new GregorianCalendar(year,month,day,c_hour,c_min,c_sec);
        Calendar targetCal = new GregorianCalendar(year,month,day,c_hour+1,c_min+2,45);  //비교대상날짜

        Calendar baseCal2 = new GregorianCalendar(year,month,day,c_hour,c_min,c_sec);
        Calendar targetCal2 = new GregorianCalendar(year,month,day,c_hour+1,c_min+10,10);

        Calendar baseCal3 = new GregorianCalendar(year,month,day,c_hour,c_min,c_sec);
        Calendar targetCal3 = new GregorianCalendar(year,month,day,c_hour+1,c_min+6,1);

        long diffSec = (targetCal.getTimeInMillis() - baseCal.getTimeInMillis()) / 1000;
        long diffDays = diffSec / (24*60*60);

        long diffSec2 = (targetCal2.getTimeInMillis() - baseCal2.getTimeInMillis()) / 1000;
        long diffDays2 = diffSec2 / (24*60*60);

        long diffSec3 = (targetCal3.getTimeInMillis() - baseCal3.getTimeInMillis()) / 1000;
        long diffDays3 = diffSec / (24*60*60);

        targetCal.add(Calendar.DAY_OF_MONTH, (int)(-diffDays));
        targetCal2.add(Calendar.DAY_OF_MONTH, (int)(-diffDays2));
        targetCal2.add(Calendar.DAY_OF_MONTH, (int)(-diffDays3));

        int hourTime = (int)Math.floor((double)(diffSec/3600));
        int minTime = (int)Math.floor((double)(((diffSec - (3600 * hourTime)) / 60)));
        int secTime = (int)Math.floor((double)(((diffSec - (3600 * hourTime)) - (60 * minTime))));

        int hourTime2 = (int)Math.floor((double)(diffSec2/3600));
        int minTime2 = (int)Math.floor((double)(((diffSec2 - (3600 * hourTime2)) / 60)));
        int secTime2 = (int)Math.floor((double)(((diffSec2 - (3600 * hourTime2)) - (60 * minTime2))));

        int hourTime3 = (int)Math.floor((double)(diffSec/3600));
        int minTime3 = (int)Math.floor((double)(((diffSec3 - (3600 * hourTime3)) / 60)));
        int secTime3 = (int)Math.floor((double)(((diffSec3 - (3600 * hourTime3)) - (60 * minTime3))));

        String min = String.format("%02d", minTime);
        String sec = String.format("%02d", secTime);

        String min2 = String.format("%02d", minTime2);
        String sec2 = String.format("%02d", secTime2);

        String min3 = String.format("%02d", minTime3);
        String sec3 = String.format("%02d", secTime3);


        buffer.append("수서행  "+min + " 분 "+ sec + "초\n\n");
        buffer.append("수서행  "+min2 + " 분 "+ sec2 + "초\n\n");
        buffer.append("오금행  "+min3 + " 분 "+ sec3 + "초");
        return buffer.toString();
    }

    //현재 역 이름 가져오기
    String getStationNum(){
        StringBuffer buffer=new StringBuffer();
        String location = URLEncoder.encode("충무로");

        String queryUrl="http://swopenAPI.seoul.go.kr/api/subway/534c5457527373753333556c6c476f/xml/realtimeStationArrival/0/5/"+location;
        try{
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();//xml파싱을 위한
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();
            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//테그 이름 얻어오기

                        if(tag.equals("statnNm")){
                            xpp.next();
                            String str = xpp.getText();
                            //buffer.append("\n"); //줄바꿈 문자 추가
                            buffer.delete(0,tag.length());
                            buffer.append(str+"역");
                        }
                        break;
                }
                eventType= xpp.next();
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return buffer.toString();//StringBuffer 문자열 객체 반환
    }


    //현재시간 가져오기
    String getCurrentTime() {
        StringBuffer buffer = new StringBuffer();
        String location = URLEncoder.encode("충무로");

        String queryUrl = "http://swopenAPI.seoul.go.kr/api/subway/534c5457527373753333556c6c476f/xml/realtimeStationArrival/0/8/" + location;
        try {
            URL url = new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();//xml파싱을 위한
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//테그 이름 얻어오기

                        if (tag.equals("recptnDt")) {
                            xpp.next();
                            buffer.append("현재 열차 정보는 실시간 정보입니다    ");
                            buffer.append(xpp.getText());//category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n\n");//줄바꿈 문자 추가
                        }
                        break;
                }
                eventType = xpp.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();//StringBuffer 문자열 객체 반환
    }


}