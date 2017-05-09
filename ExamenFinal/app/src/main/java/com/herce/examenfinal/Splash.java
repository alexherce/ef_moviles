package com.herce.examenfinal;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

import java.util.Random;

public class Splash extends AppCompatActivity {

    Random rn = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int range = 19 - 1 +1;
        int randomNum = rn.nextInt(range) + 1;

        String path = "http://ubiquitous.csf.itesm.mx/~raulms/ads/ad" + randomNum + ".mp4";
        Uri uri = Uri.parse(path);

        VideoView videoView = (VideoView)findViewById(R.id.videoView);
        videoView.setVideoURI(uri);
        videoView.start();

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                Intent intentoPrincipal = new Intent().setClass(Splash.this, Login.class);
                startActivity(intentoPrincipal);
            }
        });
    }
}
