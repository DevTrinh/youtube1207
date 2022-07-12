package com.example.youtubeapp.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.youtubeapp.R;
import com.example.youtubeapp.interfacee.InterfaceDefaultValue;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;

public class FragmentOnlyPlay extends YouTubePlayerFragment
        implements YouTubePlayer.OnInitializedListener, InterfaceDefaultValue {
    private YouTubePlayerView ypPlayerView;
    private YouTubePlayer ypPlayer;
    private String id = "EGy39OMyHzw";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_only_play, container, false);
        ypPlayerView = view.findViewById(R.id.yp_fm_only_play);
        ypPlayerView.initialize(API_KEY, this);
        return view;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer youTubePlayer, boolean b) {
        ypPlayer = youTubePlayer;
        ypPlayer.loadVideo(id);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(getContext(), youTubeInitializationResult
                + " LOI ROI CU", Toast.LENGTH_SHORT).show();
    }
}
