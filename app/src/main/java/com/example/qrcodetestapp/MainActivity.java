package com.example.qrcodetestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {
    private Data data;
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i("OpenCV", "OpenCV loaded successfully");
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = (Data)getApplication();
        Button qrReadButton = findViewById(R.id.qrReadButton);
        Button qrCreateButton = findViewById(R.id.qrCreateButton);
        Button compressButton = findViewById(R.id.compressButton);
        Button decompressButton = findViewById(R.id.decompressButton);
        qrReadButton.setOnClickListener(new QRReadButtonClickListener());
        qrCreateButton.setOnClickListener(new QRCreateButtonClickListener());
        compressButton.setOnClickListener(new CompressButtonListener());
        decompressButton.setOnClickListener(new DecompressButtonListener());
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageBitmap(data.getBitmap());
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (!OpenCVLoader.initDebug()){
            Log.d("OpenCV", "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, mLoaderCallback);
        } else {
            Log.d("OpenCV", "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    private class QRReadButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            TextView output = findViewById(R.id.textView);
            output.setText("QRCodeRead!");
            startActivity(new Intent(MainActivity.this, QRReaderActivity.class));
        }
    }

    private class QRCreateButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            TextView output = findViewById(R.id.textView);
            output.setText("QRCodeCreated!");
        }
    }

    public byte[] compressed = null;
    public int length;
    private class CompressButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            TextView output = findViewById(R.id.textView);
            String string = output.getText().toString();
            length = string.getBytes().length;
            compressed = ZstdHandler.zstdCompress(string.getBytes());
            output.setText("Compressed!");
        }
    }

    private class DecompressButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            TextView output = findViewById(R.id.textView);
            byte[] decompressed = ZstdHandler.zstdDecompress(compressed, length);
            output.setText(new String(decompressed));
        }
    }
}