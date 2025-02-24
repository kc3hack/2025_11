package com.google.mediapipe.examples.poselandmarker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class Activity_select extends AppCompatActivity {

    private TextView city_name;
    private String prefecture, city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        city_name = findViewById(R.id.city_name);
        Button sightseeingButton = findViewById(R.id.sightseeing);
        Button foodButton = findViewById(R.id.food);

        // 変数の宣言（テスト用）
//        String prefecture = "大阪府";
//        String city = "大阪市東住吉区";
        prefecture = "大阪府";
        city = "大阪市東住吉区";

        Intent intent = getIntent();
        prefecture = intent.getStringExtra("pref");
        city = intent.getStringExtra("city");

        // 都道府県・市区町村を表示
        if (prefecture != null && city != null) {
            city_name.setText( prefecture + " " + city);
        } else {
            city_name.setText("場所情報が受け取れませんでした");
        }

        // 観光ボタンが押された場合
        sightseeingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 観光ボタンが押された場合にMainActivityに遷移
                sendResult("kankou", prefecture, city);
            }
        });

        // グルメボタンが押された場合
        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // グルメボタンが押された場合にMainActivityに遷移
                sendResult("gourmet", prefecture, city);
            }
        });
    }

    // 選択されたカテゴリ情報と都道府県・市区町村をMainActivityに送信してtype選択に遷移
    private void sendResult(String category, String prefecture, String city) {
        Log.d("Test case", "send data to Activity_type");
        Intent resultIntent = new Intent(Activity_select.this, Activity_type.class);
        resultIntent.putExtra("selectedCategory", category);// 送信するカテゴリ
        resultIntent.putExtra("prefecture", prefecture);// 送信する都道府県
        resultIntent.putExtra("city", city);// 送信する市区町村
        Log.d("Test case", "send data fin");
        startActivity(resultIntent);
        finish();
    }

}
