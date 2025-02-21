package com.google.mediapipe.examples.poselandmarker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class recom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend);

        //レイアウトからボタンを取得
        Button button = findViewById(R.id.btn_return);
        //ボタンにリスナオブジェクトを設定
        button.setOnClickListener(new ButtonClickListener3());
    }

    //ボタンを押したとき動くリスナクラスを定義
    private class ButtonClickListener3 implements View

            .OnClickListener{
        @Override
        public void onClick(View view){
            //画面を終了、画面1へ移動
            finish();
        }
    }
}