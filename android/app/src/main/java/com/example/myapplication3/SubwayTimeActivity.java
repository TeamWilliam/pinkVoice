package com.example.myapplication3;

import android.annotation.SuppressLint;
import android.os.Bundle;
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

public class SubwayTimeActivity extends AppCompatActivity {

    String data;
    String dataStationName;
    String dataSubwayLine;
    String dataCurrentTime;

    TextView text;
    TextView stationName;
    TextView subwayLine;
    TextView stationBeforeAfter;
    TextView currentTime;

    EditText edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subway_time);

        stationName= (TextView)findViewById(R.id.stationName);
        currentTime= (TextView)findViewById(R.id.currentTime);


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
//
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
//


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
    String getCurrentTime(){
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

                        if(tag.equals("recptnDt")){
                            buffer.append("🚄 현재 열차 도착 정보 갱신 시간 : ");
                            xpp.next();
                            buffer.append(xpp.getText());//title 요소의 TEXT 읽어와서 문자열버퍼에 추가
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
    }



}