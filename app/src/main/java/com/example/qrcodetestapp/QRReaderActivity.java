package com.example.qrcodetestapp;

import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.TextView;

import org.opencv.android.CameraActivity;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.QRCodeDetector;
import org.opencv.utils.Converters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QRReaderActivity extends CameraActivity implements CameraBridgeViewBase.CvCameraViewListener2 {
    private CameraBridgeViewBase mOpenCvCameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrreader);
        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.camera_view);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
        mOpenCvCameraView.enableView();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    protected List<? extends CameraBridgeViewBase> getCameraViewList() {
        return Collections.singletonList(mOpenCvCameraView);
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    public void onCameraViewStarted(int width, int height) {
        mMat = new Mat(height, width, CvType.CV_8UC4);
    }

    public void onCameraViewStopped() {
        mMat.release();
    }

    private Mat mMat;
    private QRCodeDetector qrCodeDetector = new QRCodeDetector();
    private List<String> detectedData = new ArrayList<>();
    private Mat points = new Mat();

    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mMat= inputFrame.rgba();    //color
        //mMat= inputFrame.gray();    //grayscale
        //Core.bitwise_not(inputFrame.rgba(), mMat); //reversed
        //Core.bitwise_not(inputFrame.gray(), mMat); //grayscale reversed
        //Imgproc.Canny(inputFrame.gray(), mMat, 100, 200); //grayscale canny filtering
        //Imgproc.threshold(inputFrame.gray(), mMat, 0.0, 255.0, Imgproc.THRESH_OTSU); //grayscale binarization with Ohtsu
        //Core.rotate(mMat, mMat, Core.ROTATE_90_CLOCKWISE);
        /*
        if(qrCodeDetector.detectAndDecodeMulti(mMat, detectedData, points)){
            //TextView output = findViewById(R.id.textView);
            for (int i = 0; i < detectedData.size(); i++){
                Log.i("QR", "Detected:" + detectedData.get(i));
                //output.append("\n" + detectedData.get(i));
            }
            Log.i("Points", "row:" + points.rows() + " col:" + points.cols() + " channels:" + points.channels());
            for (int i = 0; i < points.rows(); i++){
                double[] posX = new double[4], posY = new double[4];
                for (int j = 0; j < 4; j++){
                    posX[j] = points.get(i, j)[0];
                    posY[j] = points.get(i, j)[1];
                    Log.i("Points", j + ":" + posX + "," + posY);
                }
                List<MatOfPoint> pos = new ArrayList<>();
                pos.add(new MatOfPoint(new Point(posX[0], posY[0]), new Point(posX[1], posY[1]), new Point(posX[2], posY[2]), new Point(posX[3], posY[3])));
                Imgproc.polylines(mMat, pos, true, new Scalar(0, 255, 0), 3);
            }
            //List<MatOfPoint> pos = new ArrayList<>();
            //pos.add(new MatOfPoint(points));
            //Imgproc.polylines(mMat, pos, true, new Scalar(0, 255, 0), 3);
            points.release();
            points = new Mat();
            //finish();
        }
        */
        return mMat;
    }
}