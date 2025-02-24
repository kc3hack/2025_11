package com.google.mediapipe.examples.poselandmarker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class recom extends AppCompatActivity {
    private TextView cityCodeText, ThrowScore, rank1, rank2, rank3, rank4, rank5,Resultlabel;
    private ImageView image1, image2, image3, image4, image5;
    private String selectedCategory, prefecture, city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommended);

        // UIコンポーネントの取得
        cityCodeText = findViewById(R.id.city_name);
        ThrowScore = findViewById(R.id.score);
        rank1 = findViewById(R.id.rank_1);
        rank2 = findViewById(R.id.rank_2);
        rank3 = findViewById(R.id.rank_3);
        rank4 = findViewById(R.id.rank_4);
        rank5 = findViewById(R.id.rank_5);
        image1 = findViewById(R.id.image_1);
        image2 = findViewById(R.id.image_2);
        image3 = findViewById(R.id.image_3);
        image4 = findViewById(R.id.image_4);
        image5 = findViewById(R.id.image_5);
        Resultlabel = findViewById(R.id.result_label);
        Button changeCategoryButton = findViewById(R.id.change_category);
        Button changeTravelTypeButton = findViewById(R.id.change_travelType); // 追加
        Button throwagainButton = findViewById(R.id.throw_again);

        // Intentからデータを取得
        Intent intent = getIntent();
        selectedCategory = intent.getStringExtra("selectedCategory");
        prefecture = intent.getStringExtra("prefecture");
        city = intent.getStringExtra("city");
        String travelType = intent.getStringExtra("travelType"); // 旅行タイプを取得
        Log.d("MainActivity", "Received: " + selectedCategory + ", " + prefecture + ", " + city + ", " + travelType);

        // 都道府県・市区町村の表示
        if (prefecture != null && city != null) {
            cityCodeText.setText("命中地点: " + prefecture + " " + city);
        } else {
            cityCodeText.setText("場所情報が受け取れませんでした");
        }

        // 面積データをロード
        ScoreCalculator.loadAreaData(this);

        // 初回の観光スポット検索
        searchTouristSpots(prefecture, city, selectedCategory,travelType);
        if (selectedCategory.equals("kankou")) {
            Resultlabel.setText("オススメ観光スポットTOP5");
        } else {
            Resultlabel.setText("オススメグルメスポットTOP5");
        }

        // カテゴリー変更ボタンの設定
        changeCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 観光 ⇄ グルメ 切り替え
                selectedCategory = selectedCategory.equals("kankou") ? "gourmet" : "kankou";
                if (selectedCategory.equals("kankou")) {
                    Resultlabel.setText("オススメ観光スポットTOP5");
                } else {
                    Resultlabel.setText("オススメグルメスポットTOP5");
                }
                searchTouristSpots(prefecture, city, selectedCategory,travelType);
            }
        });

        // 「誰と行くか変更」ボタンの設定
        changeTravelTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ActivityType に戻る Intent を作成
                Intent intent = new Intent(recom.this, Activity_type.class);
                intent.putExtra("selectedCategory", selectedCategory);
                intent.putExtra("prefecture", prefecture);
                intent.putExtra("city", city);
                startActivity(intent);
                finish(); // 現在の画面を終了
            }
        });

        // 「再度投げる」ボタンの設定
        throwagainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ActivityType に戻る Intent を作成
                Intent intent = new Intent(recom.this, mediapipe.class);
                startActivity(intent);
                finish(); // 現在の画面を終了
            }
        });


    }

    private void searchTouristSpots(String prefecture, String city, String selectedCategory,String travelType) {
        if (prefecture == null || city == null || selectedCategory == null) return;

        PrefectureCode codeFinder = new PrefectureCode();
        String cityCode = codeFinder.getCityCode(prefecture, city);
        if (cityCode == null) {
            cityCodeText.setText("市区町村コードが見つかりませんでした");
            return;
        }

        // ThrowScoreの計算と表示
        double score = ScoreCalculator.calculateThrowScore(cityCode);
        Log.d("Test Case", String.valueOf(score));
        if(score < 40){
            ThrowScore.setText("得点:"  + score +" / おぬしのダーツ力はSランク！！！素晴らしい");
        }else if(score <=100){
            ThrowScore.setText("得点:"  + score +" / おぬしのダーツ力はAランク！！すごい");
        }else if(score <=300){
            ThrowScore.setText("得点:"  + score +" / おぬしのダーツ力はBランク！まあまあ");
        }else if(score <=1000){
            ThrowScore.setText("得点:"  + score +" / おぬしのダーツ力はCランク．普通");
        }else{
            ThrowScore.setText("得点:"  + score +" / おぬしのダーツ力はDランク...残念");
        }
        //ThrowScore.setText("得点: " + score);

        // スクレイピング開始
        ScrapingTest scrapingTest = new ScrapingTest();
        scrapingTest.fetchTouristSpots(cityCode, selectedCategory, travelType,new ScrapingTest.FetchCallback() {
            @Override
            public void onSuccess(String[] topSpots, String[] topUrls, String[] topImages) {
                runOnUiThread(() -> {

                    setSpot(rank1, image1, topSpots[0], topUrls[0], topImages[0]);
                    setSpot(rank2, image2, topSpots[1], topUrls[1], topImages[1]);
                    setSpot(rank3, image3, topSpots[2], topUrls[2], topImages[2]);
                    setSpot(rank4, image4, topSpots[3], topUrls[3], topImages[3]);
                    setSpot(rank5, image5, topSpots[4], topUrls[4], topImages[4]);
                });
            }

            @Override
            public void onError(String errorMessage) {
                runOnUiThread(() -> cityCodeText.setText(errorMessage));
            }
        });
    }

    private void setSpot(TextView textView, ImageView imageView, String name, String url, String imageUrl) {
        if (name != null) textView.setText(name);

        if (url != null && !url.isEmpty()) {
            // テキストをタッチしたときにブラウザを開く
            textView.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            });

            // 画像をタッチしたときにブラウザを開く
            imageView.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            });
        }

        // 画像URLが存在する場合のみ画像を表示
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(this).load(imageUrl).into(imageView);
        } else {
            // 画像URLが空の場合は画像をクリア
            imageView.setImageDrawable(null);
        }
    }

}
