package com.example.youtubeapp.fragment;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.youtubeapp.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.security.Provider;

public class FragmentYoutubeTest extends Fragment implements  YouTubePlayer.OnInitializedListener{
    // API キー
    private static final String API_KEY = "AIzaSyDx3-B9UWFemhdJIWmJ5Iy2YBF_2_fW_Wk";
    // YouTubeのビデオID
    private static String VIDEO_ID = "EGy39OMyHzw";
    private YouTubePlayer YPlayer;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_play_test, container, false);

        return rootView;
    }
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b){
            YPlayer = youTubePlayer;
            YPlayer.setFullscreen(true);
            YPlayer.loadVideo(VIDEO_ID);
            YPlayer.play();
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult youTubeInitializationResult) {

    }
}
