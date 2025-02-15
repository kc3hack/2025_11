package org.example.app;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ScrapingTest {
    public static void main(String[] args) {
        String url = "https://www.jalan.net/kankou/cit_271460000/";

        try {
            // URLからHTMLを取得
            Document doc = Jsoup.connect(url).get();

            // 観光スポットの名前を取得（適切なセレクタに変更）
            Elements spots = doc.select("h3"); // 適切なタグに変更

            System.out.println("オススメ観光スポットTOP5:");
            int count = 0;
            for (Element spot : spots) {
                if (count >= 5)
                    break; // 5件まで取得
                System.out.println((count + 1) + ". " + spot.text());
                count++;
            }

        } catch (IOException e) {
            System.err.println("URLの読み込み中にエラーが発生しました: " + e.getMessage());
        }
    }
}
