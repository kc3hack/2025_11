package com.google.mediapipe.examples.poselandmarker;

        import com.google.mediapipe.examples.app.DataHolder; // ← これがないとエラー

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
        import android.widget.Button;

//gif関連
        import android.widget.ImageView;
//        import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
//import androidx.fragment.app.Fragment;

// Implement OnMapReadyCallback.
public class map extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout file as the content view.
        setContentView(R.layout.map);

        // Get a handle to the fragment and register the callback.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // gifセット
//        ImageView matchImage = findViewById(R.id.ya);
////        GlideDrawableImageViewTarget target = new GlideDrawableImageViewTarget(matchImage);
//        Glide.with(this)
////                .asGif()
//                .load(R.raw.ya4)
//                .into(matchImage);

    }

    // Get a handle to the GoogleMap object and display marker.
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        googleMap.addMarker(new MarkerOptions()
//                .position(new LatLng(35.681167, 139.767052))
//                .title("Marker"));
//
//    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        String[] locality = new String[1];
        // 大阪駅の座標
//        LatLng osakaStation = new LatLng(34.702485, 135.495951);
        LatLng osakaStation = new LatLng(34.85225,135.85581);
        LatLng centerKansai = new LatLng(34.78,135.86);
        // 衛星写真モードに設定
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        // 座標指定用
        Geocoder geocoder = new Geocoder(this, Locale.JAPAN);
//        住所導出処理を新スレッドへ移行（以下）
//        List<Address> address = geocoder.getFromLocation(osakaStation.latitude, osakaStation.longitude, 1);

        new Thread(() -> {
            try {
                List<Address> addresses = geocoder.getFromLocation(osakaStation.latitude, osakaStation.longitude, 1);

                // 市区町村名取得
                if (addresses != null && !addresses.isEmpty()) {
                    Address address = addresses.get(0);
                    locality[0] = address.getAdminArea() + address.getLocality() + address.getAddressLine(0) + "に行くでー！"; // 市区町村名
                }

                // UIスレッドでボタンのテキストを更新
                runOnUiThread(() -> {
                    Button btn_goto = findViewById(R.id.goto_KANSAI);
//                    btn_goto.setText(locality[0]);  // ボタンに市区町村名をセット
                    String message = DataHolder.getMessage();
                    btn_goto.setText(message);
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


        //　初期位置
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerKansai, 8));

        // カメラ移動してズーム設定
//        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(osakaStation, 15));

        // 1枚目 クリック時の遷移
//        Button btn_throw = findViewById(R.id.btn_throw);
//        btn_throw.setOnClickListener(v -> {
//            setContentView(R.layout.activity_main);
//        });


        ImageView gif_ya = findViewById(R.id.ya);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
//            googleMap.addMarker(new MarkerOptions()
//                    .position(osak
//                    aStation)
//                    .title("大阪駅"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(osakaStation, 12), 3000, null);
            gif_ya.setVisibility(gif_ya.VISIBLE);
        }, 3000);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            googleMap.addMarker(new MarkerOptions()
                    .position(osakaStation)
                    .title("大阪駅"));

            // ボタンを表示する
            Button btn_goto = findViewById(R.id.goto_KANSAI);
//            btn_goto.setText(address.get(0).getLocality());
            btn_goto.setVisibility(btn_goto.VISIBLE);

            btn_goto.setOnClickListener(v -> {
                // 最終画面への遷移
//                setContentView(R.layout.test01);
                //画面3移動用のIntentを取得
                Intent intent = new Intent(map.this,recom.class);
                //画面3へ移動
                startActivity(intent);
                finish();

//                if (textView.getVisibility() == View.VISIBLE) {
//                    textView.setVisibility(View.GONE);
//                } else {
//                    textView.setVisibility(View.VISIBLE);
//                }
            });


//             最初の画面に戻る(test01を無理やりここで導入)
//            Button btn_return = findViewById(R.id.btn_return);
//            btn_return.setOnClickListener(v -> {
//                // 最終画面からの最遷移必要時は修正必要
//                finish();
//                setContentView(R.layout.launch);
//
////                if (textView.getVisibility() == View.VISIBLE) {
////                    textView.setVisibility(View.GONE);
////                } else {
////                    textView.setVisibility(View.VISIBLE);
////                }
//            });


        }, 5000);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
//            gif_ya.setVisibility(gif_ya.INVISIBLE);

//            setContentView(R.layout.test01);
        }, 7000);

    }



}