package com.example.youtubeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.example.youtubeapp.interfacee.InterfaceDefaultValue;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

public class ActivityPlayVideoTest extends AppCompatActivity implements  YouTubePlayer.OnInitializedListener, InterfaceDefaultValue {
    public static final String VIDEO_ID = "B08iLAtS3AQ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video_test);
        // フラグメント起動 （v4の作法で）
        YouTubePlayerFragment youTubePlayerFragment = (YouTubePlayerFragment) getFragmentManager()
                .findFragmentById(R.id.fm_contains_player_view);
        youTubePlayerFragment.initialize(API_KEY, this);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if(!b) {
            youTubePlayer.loadVideo(VIDEO_ID);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        final int REQUEST_CODE = 1;

        if(youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this,REQUEST_CODE).show();
        } else {
            String youTubeInitializationResultMessage
                    = String.format("There was an youTubeInitializationResult initializing the YoutubePlayer (%1$s)",
                    youTubeInitializationResult.toString());
            Toast.makeText(this, youTubeInitializationResultMessage, Toast.LENGTH_LONG).show();
        }
    }
}