package com.google.mediapipe.examples.poselandmarker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class launch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch);

//        //レイアウトからボタンを取得
        Button btn_throw = findViewById(R.id.btn_throw);
//        //ボタンにリスナオブジェクトを設定
        btn_throw.setOnClickListener(new ButtonClickListener());
    }

    //ボタンを押したとき動くリスナクラスを定義
    private class ButtonClickListener implements View

            .OnClickListener{
        @Override
        public void onClick(View view){
            //画面2移動用のIntentを取得
            Intent intent = new Intent(launch.this,mediapipe.class);
//            Intent intent = new Intent(MainActivity.this,MainActivity_pipe.class);
            intent.putExtra("message", "これをMPに渡すねん！");
////          //画面2へ移動
            startActivity(intent);

//            setContentView(R.layout.activity_main);
//            btn_throw.setOnClickListener(v -> {
//                setContentView(R.layout.activity_main);
//            });



            //レイアウトからボタンを取得
//            Button btn_throw = findViewById(R.id.btn_throw);
//            btn_throw.setOnClickListener(v -> {
//                Intent intent = new Intent(this, MapActivity.class);
//                startActivity(intent);

//                //画面2移動用のIntentを取得
//                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
//                //画面2へ移動
//                startActivity(intent);
//            });

        }
    }
}

