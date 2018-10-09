package com.example.seth.celebritygame;

import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.seth.celebritygame.MainActivity.imageUrl;
import static com.example.seth.celebritygame.MainActivity.imgCount;
import static com.example.seth.celebritygame.MainActivity.names;
import static com.example.seth.celebritygame.MainActivity.namesCount;

public class DownloadTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... urls) {
        Log.i("info", "start asyncTask");
        int dataCount = 0;
        String result = "";
        URL url;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            int data = reader.read();

            while (data != -1) {
                char current = (char) data;
                result += current;

                if (current == '"' && result.charAt(dataCount - 1) == '=' && result.charAt(dataCount - 2) == 'c' && result.charAt(dataCount - 6) == 'g') {
                    Log.i("image found", "index" + dataCount);
                    String imgSrc = "";
                    data = reader.read();

                    do {
                        current = (char) data;
                        imgSrc += current;
                        data = reader.read();

                    } while (!(data == '"' || data == -1));
                    imageUrl[imgCount] = imgSrc;
                    imgCount++;

                } else if (current == '"' && result.charAt(dataCount - 1) == '=' && result.charAt(dataCount - 2) == 't' && result.charAt(dataCount - 3) == 'l') {
                    Log.i("name found", "index" + dataCount);
                    String nameSrc = "";
                    data = reader.read();

                    do {
                        current = (char) data;
                        nameSrc += current;
                        data = reader.read();
                    } while (!(data == '"' || data == -1));
                    names[namesCount] = nameSrc;
                    namesCount++;
                }
                data = reader.read();
                dataCount++;
            }

            Log.i("info", result);
            Log.i("check", "" + result.charAt(0) + result.charAt(1) + result.charAt(2) + result.charAt(3));
            Log.i("info", "" + dataCount);
            Log.i("info", "" + imgCount);
            Log.i("info",""+namesCount);

            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}