package com.example.myapplication3;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);

        ScrollView scrollView;
        ImageView imageView;
        BitmapDrawable bitmap;
        ImageView iv;

        //지하철 노선도 사진 HorizontalScrollView
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        scrollView = (ScrollView)v.findViewById(R.id.scrollVieww);
        imageView = (ImageView)v.findViewById(R.id.imageView);
        scrollView.setHorizontalScrollBarEnabled(true);

        Resources res = getResources();
        bitmap = (BitmapDrawable) res.getDrawable(R.drawable.route);
        int bitmapWidth = bitmap.getIntrinsicWidth();
        int bitmapHeight = bitmap.getIntrinsicHeight();

        imageView.setImageDrawable(bitmap);
        imageView.getLayoutParams().width = bitmapWidth;
        imageView.getLayoutParams().height = bitmapHeight;

        Button button = (Button)v.findViewById(R.id.go);
        Button buttonA = (Button)v.findViewById(R.id.Chungmuro);
        iv = (ImageView)v.findViewById(R.id.place);
        
        //placeHolder 버튼 눌렀을때 페이지 이동
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent=new Intent(getActivity(), SubwayTimeActivity.class);
                    startActivity(intent);
            }


        });
        
        //충무로 투명버튼 눌렀을때 placeholder 이미지 나오게 하기
        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv.setImageResource(R.drawable.placeholder);
            }
        });
        return v;
    }

}