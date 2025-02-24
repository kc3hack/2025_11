package com.google.mediapipe.examples.poselandmarker;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ScoreCalculator {

    // 地域コードごとの面積データを保持
    private static final Map<String, Double> areaData = new HashMap<>();

    // CSVファイルから面積データを読み込むメソッド
    public static void loadAreaData(Context context) {
        try {
            // CSVファイルを読み込む
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("kinki_mencho.csv"))
            );

            String line;
            // 最初の行（ヘッダー）はスキップ
            reader.readLine();

            // 各行を読み込み、地域コードと面積を取得
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length == 5) {
                    String cityCode = columns[0];
                    try {
                        double area = Double.parseDouble(columns[4]); // 面積を取得
                        areaData.put(cityCode, area); // 地域コードと面積を保存
                    } catch (NumberFormatException e) {
                        Log.e("ScoreCalculator", "Invalid area value in CSV: " + columns[4]);
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            Log.e("ScoreCalculator", "Error reading CSV file", e);
        }
    }

    // 地域コードから面積を取得し、ThrowScoreを計算（小数点第2位で四捨五入）
    public static double calculateThrowScore(String cityCode) {
        // 地域コードに対応する面積を取得
        Double area = areaData.get(cityCode);
        if (area == null) {
            // 面積データが存在しない場合は0を返す
            return 0;
        }

        // 27351.35を面積で割ってThrowScoreを計算
        double score = 27351.35 / area;

        // 小数点第2位で四捨五入
        return Math.round(score * 100.0) / 100.0;
    }
}
