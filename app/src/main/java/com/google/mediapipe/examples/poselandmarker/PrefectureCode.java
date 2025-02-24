package com.google.mediapipe.examples.poselandmarker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import android.content.Context;
import android.util.Log;

public class PrefectureCode {

    // 市区町村名から市区町村コードを取得するメソッド（Contextなし）
    public String getCityCode(String prefecture, String city) {

        // アセットからCSVファイルを読み込む処理を追加
        String csvFile = "kinki.csv"; // アセット内のファイル名

        // 以下は直接ファイル読み込み処理を行わず、アセットを使う方法に変更
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/assets/" + csvFile)))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 4) {
                    String code = values[0].replaceAll("\"", "");
                    String csvCity = values[1].replaceAll("\"", "");
                    String csvPrefecture = values[3].replaceAll("\"", "");

                    //Log.v("Test Case", "Checking: Prefecture: " + csvPrefecture + ", City: " + csvCity);

                    if (csvPrefecture.equals(prefecture) && csvCity.equals(city)) {
                        Log.d("Prefecture code", code);
                        return code;
                    }
                }
            }
        } catch (IOException e) {
            Log.e("Test Case", "ファイルを読み込めませんでした: " + e.getMessage());
        }

        return null; // 見つからない場合はnullを返す
    }
}

