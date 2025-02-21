package com.example.kc3_2025;

import android.util.Log;

import java.io.IOException;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.json.JSONArray;
import org.json.JSONObject;

public class ScrapingTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // ユーザー入力を受け付けるためのScanner

        // コマンドラインから都道府県名と市区町村名を入力
        System.out.print("都道府県名を入力してください: ");
        String prefecture = scanner.nextLine().trim();

        System.out.print("市区町村名を入力してください: ");
        String city = scanner.nextLine().trim();

        String csvFile = "kinki.csv"; // CSVファイルのパス
        Log.v("Test Case","This is test");

        // PrefectureCodeのインスタンスを作成
        PrefectureCode codeFinder = new PrefectureCode(csvFile);

        // 市区町村コードを取得
        String cityCode = codeFinder.getCityCode(prefecture, city);
        Log.d("citycode",cityCode);

        if (cityCode == null) {
            System.out.println("市区町村コードが見つかりませんでした。");
            scanner.close();
            return;
        }

        // 取得した市区町村コードを使ってURLを構成
        String url = "https://www.jalan.net/kankou/cit_" + cityCode + "0000/";

        try {
            // URLからHTMLを取得
            Document doc = Jsoup.connect(url).get();

            // JSON-LDデータを含むscriptタグを取得
            Elements scriptTags = doc.select("script[type=application/ld+json]");

            if (!scriptTags.isEmpty()) {
                // 最初の script タグの内容を取得
                String jsonData = scriptTags.first().data();

                // JSONデータを解析
                JSONArray jsonArray = new JSONArray(jsonData);

                System.out.println("オススメ観光スポットTOP5:");
                for (int i = 0; i < Math.min(5, jsonArray.length()); i++) {
                    JSONObject spot = jsonArray.getJSONObject(i);

                    // "name" キーから観光スポット名を取得
                    String name = spot.optString("name", "名称不明");

                    System.out.println((i + 1) + ". " + name);
                }
            } else {
                System.out.println("JSON-LDデータが見つかりませんでした。");
            }

        } catch (IOException e) {
            System.err.println("URLの読み込み中にエラーが発生しました: " + e.getMessage());
        } catch (org.json.JSONException e) {
            System.err.println("JSONデータの解析中にエラーが発生しました: " + e.getMessage());
        }

        // スキャナーを閉じる
        scanner.close();
    }
}
