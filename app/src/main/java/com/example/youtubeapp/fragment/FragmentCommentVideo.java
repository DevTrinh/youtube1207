package com.example.youtubeapp.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubeapp.R;
import com.example.youtubeapp.interfacee.InterfaceDefaultValue;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class FragmentCommentVideo extends Fragment implements InterfaceDefaultValue {
    View getView;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_bottom_sheet_list_comment, container, false);
        getView = view;
        return view;
    }

    public View getView(){
        return getView;
    }


//    public void getJsonCommentVideo(String idVideoItem){
//        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
//                "https://youtube.googleapis.com/youtube/v3/commentThreads?part=snippet%2Creplies&maxResults=100&order=relevance&videoId="
//                        +idVideoItem+"&key="+API_KEY+"", null,
//                new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    for(int i = 0; i< response.length(); i++){
//
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//    }
}
