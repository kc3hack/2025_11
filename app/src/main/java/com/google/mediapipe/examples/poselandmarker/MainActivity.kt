/*
 * Copyright 2023 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.mediapipe.examples.poselandmarker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
//import androidx.navigation.ui.setupWithNavController
import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarker
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarkerResult

import com.google.mediapipe.examples.poselandmarker.databinding.ActivityMainBinding
//import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarkerResult

class MainActivity : AppCompatActivity(){
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var poseLandmarkerHelper: PoseLandmarkerHelper
    private var results: PoseLandmarkerResult? = null

    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        //val overlayView = findViewById<OverlayView>(R.id.OverLay)
        //overlayView.listener = this

        //val textView: TextView = findViewById(R.id.view_test)

        // TextViewに変数の値を表示

        //textView.text = "messsage2"
        //Thread.sleep(1000)
        //textView.text = "messsage3"

        //val navHostFragment =
        //    supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        //val navController = navHostFragment.navController
        //activityMainBinding.navigation.setupWithNavController(navController)
        //activityMainBinding.navigation.setOnNavigationItemReselectedListener {
            // ignore the reselection

        //}
    }



    /*
    // PoseLandmarkerHelper から結果を受け取る
    override fun onResults(resultBundle: PoseLandmarkerHelper.ResultBundle) {
        results = poseLandmarkerResults
        results?.let { poseLandmarkerResult ->
            for(landmark in poseLandmarkerResult.landmarks()) {
                val normalizedLandmark_13=landmark[13]
                println("正規化X")
                val number = normalizedLandmark_13.x()

                val message = "landmark13.x: $number"

// ここで呼び出し


                println(message);

                // TextViewに変数の値を表示
                val textView: TextView = findViewById(R.id.view_test)
                textView.text = message
                println(normalizedLandmark_13.x())
                println("正規化Y")
                println(normalizedLandmark_13.y())

    }
    */
    override fun onBackPressed() {
        finish()
    }


}