package com.google.mediapipe.examples.poselandmarker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_type extends AppCompatActivity {

    private String selectedCategory, prefecture, city; // 受け取るデータ

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);

        // Activity_select からデータを取得
        Intent intent = getIntent();
        selectedCategory = intent.getStringExtra("selectedCategory");
        prefecture = intent.getStringExtra("prefecture");
        city = intent.getStringExtra("city");

        Log.d("Activity_type", "Received: " + selectedCategory + ", " + prefecture + ", " + city);

        // 各ボタンの取得
        Button type1 = findViewById(R.id.type1);
        Button type2 = findViewById(R.id.type2);
        Button type3 = findViewById(R.id.type3);
        Button type4 = findViewById(R.id.type4);
        Button type5 = findViewById(R.id.type5);

        // ボタンごとのクリックリスナーを設定
        type1.setOnClickListener(v -> sendResult("20"));    //子連れ
        type2.setOnClickListener(v -> sendResult("30"));    //カップル
        type3.setOnClickListener(v -> sendResult("50"));      //友達
        type4.setOnClickListener(v -> sendResult("40")); //シニア
        type5.setOnClickListener(v -> sendResult("60")); //一人旅
    }

    // 選択された旅行タイプを MainActivity に送信して遷移
    private void sendResult(String travelType) {
        Log.d("Activity_type", "Selected Travel Type: " + travelType);
        Intent resultIntent = new Intent(Activity_type.this, recom.class);
        resultIntent.putExtra("selectedCategory", selectedCategory);
        resultIntent.putExtra("prefecture", prefecture);
        resultIntent.putExtra("city", city);
        resultIntent.putExtra("travelType", travelType);
        startActivity(resultIntent);
        finish(); // 現在のActivityを終了
    }
}
