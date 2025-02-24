package com.google.mediapipe.examples.poselandmarker;

import android.util.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;

public class ScrapingTest {
    // コールバックインターフェース
    public interface FetchCallback {
        void onSuccess(String[] topSpots, String[] topUrls, String[] topImages);
        void onError(String errorMessage);
    }

    // 観光スポットを取得するメソッド
    public void fetchTouristSpots(String cityCode, String selection,String traveltype, FetchCallback callback) {
        new Thread(() -> {
            try {
                Log.v("ScrapingTest", "Fetching URL for city code: " + cityCode);
                String url = "https://www.jalan.net/" + selection + "/cit_" + cityCode + "0000/" +"?sortKbn="+traveltype;

                Document doc = Jsoup.connect(url).get();
                Elements scriptTags = doc.select("script[type=application/ld+json]");

                if (!scriptTags.isEmpty()) {
                    String jsonData = scriptTags.first().data();
                    JSONArray jsonArray = new JSONArray(jsonData);

                    String[] topSpots = new String[5];
                    String[] topUrls = new String[5];
                    String[] topImages = new String[5];

                    for (int i = 0; i < Math.min(5, jsonArray.length()); i++) {
                        JSONObject spot = jsonArray.getJSONObject(i);
                        topSpots[i] = (i + 1) + ". " + spot.optString("name", "名称不明");
                        topUrls[i] = spot.optString("url", "");
                        topImages[i] = spot.optString("image", "");
                    }

                    callback.onSuccess(topSpots, topUrls, topImages);
                } else {
                    //callback.onError("JSON-LDデータが見つかりませんでした");
                    Log.d("Test case","JSON-LD data not found");
                }
            } catch (IOException e) {
                callback.onError("データ取得エラー: " + e.getMessage());
            } catch (org.json.JSONException e) {
                callback.onError("JSON解析エラー: " + e.getMessage());
            }
        }).start();
    }
}

