package com.example.youtubeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.youtubeapp.interfacee.InterfaceDefaultValue;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class OnlyPlayVideo extends YouTubeBaseActivity
        implements YouTubePlayer.OnInitializedListener,
        InterfaceDefaultValue {

    private YouTubePlayerView ypPlayerView;
    private YouTubePlayer ypPlayer;
    private String id = "EGy39OMyHzw";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_only_play_video);
        mapping();

            ypPlayerView.initialize(API_KEY, this);
    }

    public void mapping(){
        ypPlayerView = findViewById(R.id.yp_only_play);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer youTubePlayer, boolean b) {
        ypPlayer = youTubePlayer;
        ypPlayer.loadVideo(id);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, youTubeInitializationResult
                + " LOI ROI CU", Toast.LENGTH_SHORT).show();
    }
}