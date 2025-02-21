package com.example.kc3_2025;

import java.io.*;
// import java.util.*; //never used
import android.util.Log;


public class PrefectureCode {
    private final String csvFile;

    // コンストラクタ
    public PrefectureCode(String csvFile) {
        this.csvFile = csvFile;
    }

    // 市区町村名から市区町村コードを取得するメソッド
    public String getCityCode(String prefecture, String city) { //名称を追加
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 4) {
                    String code = values[0].replaceAll("\"", "");
                    String csvCity = values[1].replaceAll("\"", "");
                    String csvPrefecture = values[3].replaceAll("\"", "");

                    if (csvPrefecture.equals(prefecture) && csvCity.equals(city)) {
                        Log.d("Prefecture code",code);
                        return code;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("ファイルを読み込めませんでした: " + e.getMessage());
        }
        return null; // 見つからない場合はnullを返す
    }
}
