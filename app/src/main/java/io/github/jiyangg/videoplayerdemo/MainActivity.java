package io.github.jiyangg.videoplayerdemo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;

import io.github.jiyangg.videoplayerdemo.player.PlayerView;

public class MainActivity extends AppCompatActivity {
    private String videoPath;
    private PlayerView mPlayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initData();
        Intent intent = getIntent();
        Uri uri = intent.getData();
        if(uri!=null){
            videoPath = uri.getPath();
        }else{
            initData();
        }
        initMPlayer();
    }

    private void initMPlayer() {
        mPlayView = findViewById(R.id.mPlayerView);
        mPlayView.setVideoFilePath(videoPath);
    }

    //将raw里video拷贝到文件
    private void initData() {
        File dir = getFilesDir();
        File path = new File(dir, "shape.mp4");
        final BufferedInputStream in = new BufferedInputStream(getResources().openRawResource(R.raw.shape_of_my_heart));
        final BufferedOutputStream out;
        try {
            out = new BufferedOutputStream(openFileOutput(path.getName(), Context.MODE_PRIVATE));
            byte[] buf = new byte[1024];
            int size = in.read(buf);
            while (size > 0) {
                out.write(buf, 0, size);
                size = in.read(buf);
            }
            in.close();
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        videoPath = path.toString();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPlayView != null)
            mPlayView.pause();
    }

}
