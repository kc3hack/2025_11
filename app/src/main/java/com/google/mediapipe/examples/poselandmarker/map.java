package com.google.mediapipe.examples.poselandmarker;

        import com.google.mediapipe.examples.app.DataHolder_latitude;
        import com.google.mediapipe.examples.app.DataHolder_longitude;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.location.Geocoder;
        import android.location.Address;
        import android.os.Bundle;

        import java.io.IOException;
        import java.util.Locale;
        import java.util.List;

        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.SupportMapFragment;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.MarkerOptions;
        import com.google.android.gms.maps.CameraUpdateFactory;

//待機関連
        import android.os.Handler;
        import android.os.Looper;
        import android.util.Log;
        import android.widget.Button;

//gif関連
        import android.widget.ImageView;

public class map extends AppCompatActivity implements OnMapReadyCallback {

    // 市区町村取得用
    String pref = new String();
    String city = new String();
    String ex_city = new String();

    // 特定文字を含めて前を削除
    public static String removeBeforeIncludingRegex(String input, String delimiter) {
        return input.replaceAll("^.*?" + delimiter, ""); // delimiter の前と delimiter を削除
    }

    // 特定文字以前を削除
    public static String removeBeforeRegex(String input, String delimiter) {
        return input.replaceAll(".*?" + delimiter, delimiter); // delimiter の直前まで削除
    }

    // 特定文字以降を削除
    public static String removeAfterRegex(String input, String delimiter) {
        return input.replaceAll(delimiter + ".*", ""); // delimiter 以降を削除
    }

    // 特定文字を含めず以降を削除
    public static String removeAfterExcludingRegex(String input, String delimiter) {
        return input.replaceAll("(" + delimiter + ").*", "$1"); // delimiter の直後以降を削除
    }

    // 区の位置以降を削除
    public static String removeAfterFirstWard(String input) {
        // "区" の位置を取得
        int index = input.indexOf("区");

        // "区" が見つかった場合
        if (index != -1) {
            return input.substring(0, index + 1); // "区" を含めたそれ以前の文字列を返す
        } else {
            return input; // "区" が見つからない場合はそのまま返す
        }
    }

    // 郵便番号検出チェック（エラー補正用）
    public static boolean containsPostalMark(String input) {
        return input != null && input.contains("〒");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout file as the content view.
        setContentView(R.layout.map);

        // Get a handle to the fragment and register the callback.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        String[] locality = new String[1];
        // 大阪駅の座標
        //LatLng osakaStation = new LatLng(34.557002, 136.091722);

        String latitude = DataHolder_latitude.getMessage();
        String longitude = DataHolder_longitude.getMessage();

        LatLng osakaStation = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
        LatLng centerKansai = new LatLng(34.78,135.86);
        // 衛星写真モードに設定
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        // 座標指定用
        Geocoder geocoder = new Geocoder(this, Locale.JAPAN);
        // 住所導出処理を新スレッドへ移行（以下）
        new Thread(() -> {
            boolean dest_pos_error = false;
            try {
                List<Address> addresses = geocoder.getFromLocation(osakaStation.latitude, osakaStation.longitude, 1);
                Button btn_goto = findViewById(R.id.goto_KANSAI);

                // 市区町村名取得
                if (addresses != null && !addresses.isEmpty()) {
                    Address address = addresses.get(0);
                    locality[0] = address.getAdminArea() + address.getLocality() + address.getAddressLine(0) + "に行くでー！"; // 市区町村名

                    // AdminArea() → 都道府県・getLocality()→市区町村のみ
                    //市区町村正規取得処理
                    pref = address.getAdminArea();

                    // 関西域外のエラー処理
                    if(!(pref.equals("滋賀県") || pref.equals("京都府") || pref.equals("大阪府") || pref.equals("兵庫県") || pref.equals("奈良県") || pref.equals("和歌山県") ||pref.equals("三重県"))) {
                        // 関西外の場合
                        pref = "";
                        city = "";
                    }else{
                        // 例外処理: 区の場合
                        ex_city = address.getLocality();
                        city = removeBeforeIncludingRegex(address.getAddressLine(0), address.getAdminArea());
                        if (ex_city.equals("京都市") || ex_city.equals("大阪市") || ex_city.equals("堺市") || ex_city.equals("神戸市")) {
                            // 区の文字以後を削除
                            city = removeAfterFirstWard(city);
                        } else {
                            // 市町村名のみ保持
                            city = removeAfterExcludingRegex(city, ex_city);
                        }
                    }

                    if(containsPostalMark(pref) || containsPostalMark(city)){
                        // エラー住所の場合
                        pref = "";
                        city = "";
                    }
                }

                // UIスレッドでボタンのテキストを更新
                runOnUiThread(() -> {
                    // 都道府県以外にダーツの先が当たった場合の処理
                    if(pref.isEmpty() && city.isEmpty()){
                        btn_goto.setText("もう1回投げるで～！");
                    }else{
                        btn_goto.setText(pref+city+"\nに行ってみよか～！");
                    }
//                    String message = DataHolder.getMessage();   // データホルダーにセット
//                    btn_goto.setText(message);
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        //　初期位置
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerKansai, 8));

        ImageView gif_ya = findViewById(R.id.ya);
//        new Handler(Looper.getMainLooper()).postDelayed(() -> {
//            gif_ya.setVisibility(gif_ya.VISIBLE);
//        }, 2000);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(osakaStation, 12), 3000, null);
//            gif_ya.setVisibility(gif_ya.VISIBLE);
        }, 3000);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            googleMap.addMarker(new MarkerOptions()
                    .position(osakaStation)
                    .title("大阪駅"));

            // ボタンを表示する
            Button btn_goto = findViewById(R.id.goto_KANSAI);
            btn_goto.setVisibility(btn_goto.VISIBLE);

            btn_goto.setOnClickListener(v -> {
                if(btn_goto.getText().toString() == "もう1回投げるで～！"){
                    //ダーツ投げ画面へ遷移
                    //移動用のIntentを取得
                    Intent intent = new Intent(map.this,mediapipe.class);
                    //移動xx
                    startActivity(intent);
                    finish();
                }else{
                    //おすすめ画面へ遷移
                    //移動用のIntentを取得
                    Intent intent = new Intent(map.this,Activity_select.class);
                    intent.putExtra("pref", pref);
                    intent.putExtra("city", city);
                    //移動
                    startActivity(intent);
                    finish();
                }


            });
        }, 6000);

    }



}