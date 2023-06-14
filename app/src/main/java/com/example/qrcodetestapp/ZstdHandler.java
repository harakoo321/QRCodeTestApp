package com.example.qrcodetestapp;

import android.util.Log;

import com.github.luben.zstd.Zstd;

public class ZstdHandler {
    public static byte[] zstdCompress(byte[] bytes){
        Log.i("Zstd", "Compressed!");
        return Zstd.compress(bytes);
    }

    public static byte[] zstdDecompress(byte[] bytes, int originalSize){
        Log.i("Zstd", "Decompressed!");
        return Zstd.decompress(bytes, originalSize);
    }
}
