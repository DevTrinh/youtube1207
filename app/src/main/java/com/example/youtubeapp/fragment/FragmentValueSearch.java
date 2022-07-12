package com.example.youtubeapp.fragment;

import static com.example.youtubeapp.fragment.FragmentHome.formatData;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.youtubeapp.ActivityPlayVideo;
import com.example.youtubeapp.ActivitySearchVideo;
import com.example.youtubeapp.R;
import com.example.youtubeapp.adapter.AdapterValueSearch;
import com.example.youtubeapp.interfacee.InterfaceClickFrameVideo;
import com.example.youtubeapp.interfacee.InterfaceDefaultValue;
import com.example.youtubeapp.item.ItemValueSearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentValueSearch extends Fragment implements InterfaceDefaultValue {
    private static RecyclerView rvListValueSearch;
    private static AdapterValueSearch adapterValueSearch;
    public static ArrayList<ItemValueSearch> listValueSearch = new ArrayList<>();
    private EditText etSearch;
    private ImageView backSearch;
    private ProgressBar pbLoad;
    private String API_SEARCH = "";
    private Intent intentSearchToPlayVideo;
    public static final String EXTRA_DATA = "EXTRA_DATA";

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_value_search,
                container, false);
        intentSearchToPlayVideo = new Intent(getContext(), ActivityPlayVideo.class);
        String valueSearch = getArguments().getString(VALUE_SEARCH);
        API_SEARCH = "https://youtube.googleapis.com/youtube/v3/search?part=snippet&maxResults=50&q="
                + valueSearch + "&key=" + API_KEY + "";
        mapping(view);
        pbLoad.setVisibility(View.VISIBLE);
        listValueSearch.clear();
        etSearch.setText(valueSearch);
        getJsonValueSearch(API_SEARCH);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvListValueSearch.setLayoutManager(linearLayoutManager);
//        Toast.makeText(getContext(), listValueSearch.size()
//                + "", Toast.LENGTH_SHORT).show();
        adapterValueSearch = new AdapterValueSearch(listValueSearch,
                new InterfaceClickFrameVideo() {
                    @Override
                    public void onClickTitleVideo(int position) {
                        /*IMAGEVIEW ONCLICK*/
                        Log.d("TITLE ON CLICK: " + position,
                                listValueSearch.get(position).getTitleVideo());
                        intentSearchToPlayVideo.putExtra(VALUE_SEARCH,
                                listValueSearch.get(position));
                        startActivity(intentSearchToPlayVideo);
                    }

                    @Override
                    public void onClickImageVideo(int position) {
                        /*TITTLE ONCLICK*/
                        Log.d("IMAGE VIEW ON CLICK: " + position,
                                listValueSearch.get(position).getTitleVideo());
                        intentSearchToPlayVideo.putExtra(VALUE_SEARCH,
                                listValueSearch.get(position));
                        startActivity(intentSearchToPlayVideo);
                    }

                    @Override
                    public void onClickMenuVideo(int position) {
                        Log.d("MENU VIDEO: " + position,
                                listValueSearch.get(position).getTitleVideo());
                    }

                    @Override
                    public void onClickChannelVideo(int position) {
                        Log.d("CHANNEL VIDEO: " + position,
                                listValueSearch.get(position).getTitleVideo());
                    }
                });
        rvListValueSearch.setAdapter(adapterValueSearch);
//        CLICK X IN etSearch
        etSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (etSearch.getRight()
                            - etSearch.getCompoundDrawables()[DRAWABLE_RIGHT]
                            .getBounds().width())) {
                        ActivitySearchVideo.etSearch.setText("");
                        backSearch();
                        return true;
                    }
                }
                return false;
            }
        });

        etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backSearch();
            }
        });

        backSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backSearch();
            }
        });

        return view;
    }

    private void getInfoChannel(String ID_CHANNEL, int position, int size) {
        if (position < size) {
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                    "https://youtube.googleapis.com/youtube/v3/channels?part=snippet%2Cstatistics&id="
                            + ID_CHANNEL + "&key=" + API_KEY + "",
                    null, new Response.Listener<JSONObject>() {
                @SuppressLint("NotifyDataSetChanged")
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonItems = response.getJSONArray(ITEMS);
                        JSONObject jsonItem = jsonItems.getJSONObject(0);
                        JSONObject jsonSnippet = jsonItem.getJSONObject(SNIPPET);
                        JSONObject jsonThumbnail = jsonSnippet.getJSONObject(THUMBNAIL);
                        JSONObject jsonHigh = jsonThumbnail.getJSONObject(HIGH);
                        if (jsonThumbnail.has(HIGH)) {
                            listValueSearch.get(position).setUrlAvtChannel(jsonHigh.getString(URL) + "");
//                            Log.d("LINKKKKKKKK " + position, jsonHigh.getString(URL));
                        }
                        JSONObject jsonStatics = jsonItem.getJSONObject(STATISTICS);
                        if (jsonStatics.has(SUBSCRIBE_COUNT)) {
                            listValueSearch.get(position).setNumberSubscribe
                                    (formatData(Integer.parseInt
                                            (jsonStatics.getString(SUBSCRIBE_COUNT)))
                                            + "Subscribers");
//                    Log.d("AAAAA " + position, urlChannel);
                        }

                        adapterValueSearch.notifyItemChanged(position);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "FALSE GET URL AVT CHANNEL",
                            Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
        }
    }

    private void backSearch() {
        ActivitySearchVideo.adapterHistorySearch.notifyItemChanged(0);
        getActivity().finish();
        Toast.makeText(getActivity(), "BACK", Toast.LENGTH_SHORT).show();
    }

    private void getJsonValueSearch(String API_SEARCH) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                API_SEARCH, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String idVideo = "";
                    String timeUp = "";
                    String channelId = "";
                    String titleVideo = "";
                    String descriptionVideo = "";
                    String urlImage = "";
                    String channelTitle = "";
                    String viewCount = "";
                    JSONArray jsonItems = response.getJSONArray(ITEMS);
                    Log.d("JSON ITEMS: ", jsonItems.length() + "");
                    int size = 0;
                    for (int i = 0; i < jsonItems.length(); i++) {
                        JSONObject jsonItem = jsonItems.getJSONObject(i);
                        JSONObject jsonId = jsonItem.getJSONObject(ID);
                        if (jsonId.has(ID_VIDEO)) {
                            idVideo = jsonId.getString(ID_VIDEO);
//                            Log.d("VIDEO ID: "+i, idVideo);
                            JSONObject jsonSnippet = jsonItem.getJSONObject(SNIPPET);
//                            Log.d("JSON SNIPPET: "+i,FragmentHome.formatTimeUpVideo(jsonSnippet.getString(PUBLISHED_AT)));
                            timeUp = FragmentHome.formatTimeUpVideo(jsonSnippet.getString(PUBLISHED_AT));
                            channelId = jsonSnippet.getString(CHANNEL_ID);
//                            Log.d("CHANNEL ID: "+i, channelId);
                            getInfoChannel(channelId, i-size, jsonItems.length() - size);
                            titleVideo = jsonSnippet.getString(TITLE);
//                            Log.d("TITLE CHANNEL: "+i, titleVideo);
                            descriptionVideo = jsonSnippet.getString(DESCRIPTION);
                            JSONObject jsonThumbnails = jsonSnippet.getJSONObject(THUMBNAIL);
                            JSONObject jsonHigh = jsonThumbnails.getJSONObject(HIGH);
                            urlImage = jsonHigh.getString(URL);
//                            Log.d("URL IMAGE: " + i, urlImage);
                            channelTitle = jsonSnippet.getString(CHANNEL_TITLE);
//                            Log.d("CHANNEL TITLE: "+i, channelTitle);
                            viewCount = "1.5M views";
                        } else {
                            size++;
                            continue;
                        }
                        listValueSearch.add(new ItemValueSearch(idVideo,
                                timeUp, channelId, titleVideo,
                                descriptionVideo, urlImage,
                                channelTitle, viewCount));
                        pbLoad.setVisibility(View.GONE);

                    }
                } catch (JSONException ignored) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error + " Co len ban manh a",
                        Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
//        Log.d("HUHUHUHUHUHUHUH", API_SEARCH);
    }

    public void mapping(@NonNull View view) {
        pbLoad = view.findViewById(R.id.pb_load_video_search);
        backSearch = view.findViewById(R.id.iv_back_after_search);
        rvListValueSearch = view.findViewById(R.id.rv_list_value_search);
        etSearch = view.findViewById(R.id.et_after_search);
    }
}
