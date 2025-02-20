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

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarker
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarkerResult
import kotlin.math.max
import kotlin.math.min
import kotlin.math.atan2
import kotlin.math.PI
//import android.widget.TextView

class OverlayView(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {


    //val poseLandmarkerHelperListener: PoseLandmarkerHelper.LandmarkerListener? = null

    private var results: PoseLandmarkerResult? = null
    private var pointPaint = Paint()
    private var linePaint = Paint()

    private var scaleFactor: Float = 1f
    private var imageWidth: Int = 1
    private var imageHeight: Int = 1

    init {
        initPaints()
    }

    fun clear() {
        results = null
        pointPaint.reset()
        linePaint.reset()
        invalidate()
        initPaints()
    }

    private fun initPaints() {
        linePaint.color =
            ContextCompat.getColor(context!!, R.color.mp_color_primary)
        linePaint.strokeWidth = LANDMARK_STROKE_WIDTH
        linePaint.style = Paint.Style.STROKE

        pointPaint.color = Color.YELLOW
        pointPaint.strokeWidth = LANDMARK_STROKE_WIDTH
        pointPaint.style = Paint.Style.FILL
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        results?.let { poseLandmarkerResult ->
            for(landmark in poseLandmarkerResult.landmarks()) {
                for(normalizedLandmark in landmark) {
                    canvas.drawPoint(
                        normalizedLandmark.x() * imageWidth * scaleFactor,
                        normalizedLandmark.y() * imageHeight * scaleFactor,
                        pointPaint
                    )
                }

                PoseLandmarker.POSE_LANDMARKS.forEach {
                    canvas.drawLine(
                        poseLandmarkerResult.landmarks().get(0).get(it!!.start()).x() * imageWidth * scaleFactor,
                        poseLandmarkerResult.landmarks().get(0).get(it.start()).y() * imageHeight * scaleFactor,
                        poseLandmarkerResult.landmarks().get(0).get(it.end()).x() * imageWidth * scaleFactor,
                        poseLandmarkerResult.landmarks().get(0).get(it.end()).y() * imageHeight * scaleFactor,
                        linePaint)
                }
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun setResults(
        poseLandmarkerResults: PoseLandmarkerResult,
        imageHeight: Int,
        imageWidth: Int,
        runningMode: RunningMode = RunningMode.IMAGE
    ) {

        results = poseLandmarkerResults

        results?.let { poseLandmarkerResult ->
            for (landmark in poseLandmarkerResult.landmarks()) {
                val number12_x = landmark[12].x()
                val number12_y = landmark[12].y()
                val number12_z = landmark[12].z()
                val number14_x = landmark[14].x()
                val number14_y = landmark[14].y()
                val number14_z = landmark[14].z()
                val number16_x = landmark[16].x()
                val number16_y = landmark[16].y()
                val number16_z = landmark[16].z()
                val number11_x = landmark[11].x()
                val number11_y = landmark[11].y()
                val number11_z = landmark[11].z()
                val number13_x = landmark[13].x()
                val number13_y = landmark[13].y()
                val number13_z = landmark[13].z()
                val number15_x = landmark[15].x()
                val number15_y = landmark[15].y()
                val number15_z = landmark[15].z()
                //右手の角度
                val vectorBAx = number13_x - number11_x
                val vectorBAy = number13_y - number11_y
                val vectorBAz = number13_z - number11_z
                val vectorBCx = number13_x - number15_x
                val vectorBCy = number13_y - number15_y
                val vectorBCz = number13_z - number15_z

                val dotProduct = vectorBAx * vectorBCx + vectorBAy * vectorBCy+ vectorBAz * vectorBCz
                val magnitudeBA = kotlin.math.sqrt(vectorBAx * vectorBAx + vectorBAy * vectorBAy+ vectorBAz * vectorBAz)
                val magnitudeBC = kotlin.math.sqrt(vectorBCx * vectorBCx + vectorBCy * vectorBCy+ vectorBCz * vectorBCz)

                val cosTheta = dotProduct / (magnitudeBA * magnitudeBC)
                val thetaRadians = kotlin.math.acos(cosTheta)

                // Kotlinのatan2関数を使って符号付き角度を計算
                val thetaRadians2 = atan2(vectorBCy, vectorBCx) - atan2(vectorBAy, vectorBAx)

                // 角度を0-360度の範囲に調整
                val thetaDegrees = thetaRadians * 180 / PI //+ 360) % 360
                val dotProduct2 = vectorBAx * vectorBCx + vectorBAy * vectorBCy
                val magnitudeBA2 = kotlin.math.sqrt(vectorBAx * vectorBAx + vectorBAy * vectorBAy)
                val magnitudeBC2 = kotlin.math.sqrt(vectorBCx * vectorBCx + vectorBCy * vectorBCy)
                val cosTheta2 = dotProduct2 / (magnitudeBA2 * magnitudeBC2)
                val Radians2 = kotlin.math.acos(cosTheta2)
                val thetaDegrees2 = (Radians2 * 180 / PI + 360) % 360




                val message =
                            "右肩.x: $number11_x\n" +
                            "右肩.y: $number11_y\n" +
                            "右肩.z: $number11_z\n" +
                            "右肘.x: $number13_x\n" +
                            "右肘.y: $number13_y\n" +
                            "右肘.z: $number13_z\n" +
                            "右手首.x: $number15_x\n" +
                            "右手首.y: $number15_y\n" +
                            "右手首.z: $number15_z\n" +
                                    "vectorBAx:$vectorBAx\n" +
                                    "vectorBAy:$vectorBAy\n" +
                                    "vectorBCx:$vectorBCx\n" +
                                    "vectorBCy:$vectorBCy\n" +


                            "右手の角度: $thetaDegrees\n" +
                            "右手の角度(xy): $thetaDegrees2\n" +
                        "左肩.x: $number12_x\n" +
                        "左肩.y: $number12_y\n" +
                        "左肩.z: $number12_z\n" +
                        "左肘.x: $number14_x\n" +
                        "左肘.y: $number14_y\n" +
                        "左肘.z: $number14_z\n" +
                        "左手首.x: $number16_x\n" +
                        "左手首.y: $number16_y\n" +
                        "左手首.z: $number16_z\n"
                var status_message="準備中"
                if(thetaDegrees2<=30){
                     status_message="構え"

                }else if(thetaDegrees2<=90){
                     status_message="動作中"

                }else{
                     status_message="終了"
                }

                val textView: TextView =
                    (context as? Activity)?.findViewById(R.id.view_test) ?: return
                textView.text = message
                val textView2: TextView =
                    (context as? Activity)?.findViewById(R.id.status_text) ?: return
                textView2.text = status_message

            }}


// ここで呼び出し

                /*
                println(message);

                    // TextViewに変数の値を表示
                   //val textView: TextView = findViewById(R.id.view_test)
                     //textView.text = message
                    println(normalizedLandmark_13.x())
                    println("正規化Y")
                    println(normalizedLandmark_13.y())
            }}

 */


                this.imageHeight = imageHeight
                this.imageWidth = imageWidth

                scaleFactor = when (runningMode) {
                    RunningMode.IMAGE,
                    RunningMode.VIDEO -> {
                        min(width * 1f / imageWidth, height * 1f / imageHeight)
                    }

                    RunningMode.LIVE_STREAM -> {
                        // PreviewView is in FILL_START mode. So we need to scale up the
                        // landmarks to match with the size that the captured images will be
                        // displayed.
                        max(width * 1f / imageWidth, height * 1f / imageHeight)
                    }
                }
                invalidate()
            }

            companion object {
            private const val LANDMARK_STROKE_WIDTH = 12F
        }

        }