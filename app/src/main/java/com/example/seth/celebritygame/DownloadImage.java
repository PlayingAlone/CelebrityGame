package com.example.seth.celebritygame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.seth.celebritygame.MainActivity.imageUrl;
import static com.example.seth.celebritygame.MainActivity.names;

public class DownloadImage extends AsyncTask<String, Void, Bitmap> {

    @Override
    protected Bitmap doInBackground(String... urls) {

        try {
            URL url = new URL(urls[0]);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.connect();
            InputStream in = connection.getInputStream();

            Bitmap myBitmap = BitmapFactory.decodeStream(in);

            return myBitmap;

        }catch (Exception e){
            Log.i("DownloadImage","failed to access image");
        }
        return null;
    }
}