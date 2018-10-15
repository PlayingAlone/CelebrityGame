package com.example.seth.celebritygame;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {
    static public String imageUrl[] = new String[200];
    static public String names[] = new String[200];
    static public int imgCount = 0;
    static public int namesCount=0;
    int answerButton;
    int answerCeleb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUp();
        game();

    }

    public void setUp(){
        DownloadTask task = new DownloadTask();
        try {
            task.execute("http://www.posh24.se/kandisar").get();
        } catch (Exception e) {
            Log.i("info", "website not found");
            e.printStackTrace();
        }
        int i = 0;
        while (imageUrl[i] != null) {
            Log.i("imgSrc", imageUrl[i]);
            i++;
        }
        i = 0;
        while (names[i] != null) {
            Log.i("imgSrc", names[i]);
            i++;
        }
        Log.i("Imp", "Done");
    }

    public void game(){
        DownloadImage task = new DownloadImage();
        ImageView imageView = findViewById(R.id.imageView);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        String buttons[] = new String[5];
        int positions[]= new int[4];
        int min=0;
        int max=namesCount-30;
        int randomCeleb;


        for(int i=0;i<4;i++){
            positions[i]=5;
        }

        Random r = new Random();
        randomCeleb = r.nextInt(max - min+ 1) + min;

        Bitmap myImage;
        try {
            myImage = task.execute(imageUrl[randomCeleb]).get();
            imageView.setImageBitmap(myImage);
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        int randomButton = r.nextInt(4 - 1+ 1) + 1;
        buttons[randomButton]=names[randomCeleb];
        answerCeleb= randomCeleb;
        answerButton= randomButton;
        positions[0]=randomButton;
        Log.i("random","correct celeb "+randomCeleb+" position"+ randomButton);

        for(int i =1;i<4;i++){
            int test = 0;
            randomCeleb = r.nextInt(max - min+ 1) + min;
            do {
                randomButton = r.nextInt(4 - 1+ 1) + 1;
                Log.i("try",""+randomButton);
                if(test==50){
                    finish();
                }
                test++;
            } while(positions[0]==randomButton||positions[1]==randomButton||positions[2]==randomButton||positions[3]==randomButton);
            positions[i]= randomButton;
            buttons[randomButton]=names[randomCeleb];
            Log.i("random"," celeb "+randomCeleb+" position"+ randomButton);

        }
        button1.setText(buttons[1]);
        button2.setText(buttons[2]);
        button3.setText(buttons[3]);
        button4.setText(buttons[4]);
    }

    public void answer(View view){
        Log.i("test",""+view.getTag());
        Log.i("test",""+answerButton);
        if(parseInt(view.getTag().toString()) == answerButton){
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Incorrect, the answer was "+names[answerCeleb], Toast.LENGTH_SHORT).show();
        }
        game();

    }

}
