package com.example.qrcodetestapp;

import org.opencv.core.Mat;

public class QRCodeHandler {
    private Mat mat = new Mat();

    public Mat getMat(){
        return this.mat;
    }

    public void dispose(){
        mat.release();
    }
}
