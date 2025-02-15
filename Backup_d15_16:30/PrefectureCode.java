package org.example.app;

import java.io.*;
import java.util.*;

public class PrefectureCode {
    public static void main(String[] args) {
        String csvFile = "kinki.csv"; // CSVファイルのパス

        try (Scanner scanner = new Scanner(System.in)) { // Scanner を try-with-resources に変更
            System.out.print("都道府県名を入力してください: ");
            String prefecture = scanner.nextLine().trim();

            System.out.print("市町村名を入力してください: ");
            String city = scanner.nextLine().trim();

            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(","); // CSVをカンマで分割
                    if (values.length >= 4) {
                        String code = values[0].replaceAll("\"", ""); // 市区町村コード
                        String csvCity = values[1].replaceAll("\"", ""); // 市町村名
                        String csvPrefecture = values[3].replaceAll("\"", ""); // 都道府県名

                        if (csvPrefecture.equals(prefecture) && csvCity.equals(city)) {
                            System.out.println("市区町村コード: " + code);
                            return;
                        }
                    }
                }
                System.out.println("該当するデータが見つかりませんでした。");
            } catch (IOException e) {
                System.err.println("ファイルを読み込めませんでした: " + e.getMessage());
            }
        }
    }
}
