

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class scarapingtest {
    public static void main(String[] args) {
        String url = "https://www.jalan.net/kankou/cit_271460000/";

        try {
            // URLからHTMLを取得
            Document doc = Jsoup.connect(url).get();

            // 観光スポットの名前を取得（適切なセレクタに修正してください）
            Elements spots = doc.select(".spot-name-selector");

            System.out.println("オススメ観光スポットTOP5:");
            for (int i = 0; i < Math.min(5, spots.size()); i++) {
                Element spot = spots.get(i);
                System.out.println((i + 1) + ". " + spot.text());
            }
        } catch (IOException e) {
            System.err.println("URLの読み込み中にエラーが発生しました: " + e.getMessage());
        }
    }
}
