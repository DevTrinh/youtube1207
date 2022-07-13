package com.example.youtubeapp;

import static com.example.youtubeapp.R.*;
import static com.example.youtubeapp.fragment.FragmentHome.formatData;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.youtubeapp.adapter.AdapterListComment;
import com.example.youtubeapp.adapter.AdapterMainVideoYoutube;
import com.example.youtubeapp.fragment.FragmentMenuItemVideoMain;
import com.example.youtubeapp.interfacee.InterfaceClickFrameVideo;
import com.example.youtubeapp.interfacee.InterfaceDefaultValue;
import com.example.youtubeapp.item.ItemComment;
import com.example.youtubeapp.item.ItemValueSearch;
import com.example.youtubeapp.item.ItemVideoMain;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityPlayVideo extends AppCompatActivity
        implements YouTubePlayer.OnInitializedListener, InterfaceDefaultValue {


//    DESCRIPTION
    private BottomSheetBehavior btDescription;
    private CardView bottomSheetDescription;
    private TextView tvTitleDes, tvLikeDes, tvViewDes, tvTimeDes, tvDescription, tvNameChannelDes;
    private CircleImageView ivAvtChannelDes;
    private String likeDes, viewerDes, timeDes, urlAvtChannel, description;

    private BottomSheetBehavior btSheetComment;
    private LinearLayout bottomSheetComment;
    private NestedScrollView nsPlay;
    public ConstraintLayout clComment;

    private AdapterMainVideoYoutube adapterListVideoYoutube;
    private AdapterListComment adapterListComment;
    private AlertDialog.Builder alertDialog;
    private ProgressBar pbLoad;
    private CircleImageView ivUserComment;
    private EditText etUserComment;
    private ImageView ivLiked, ivDisliked, ivShare, ivDownload, ivSave,
            ivAvtChannel, ivClip,ivOpenDescription;
    private YouTubePlayer ypPlayItemClick;
    private CheckBox cbNotificationChannel;
    private RecyclerView rvListComment, rvListVideoPlay;
    public static ArrayList<ItemVideoMain> listPlayRelate = new ArrayList<>();
    private ArrayList<ItemComment> listComment = new ArrayList<>();
    private YouTubePlayerFragment youTubePlayerFragment;
    private boolean numberLikeCheck = true;

    public static String id = "";
    private String idPlayListInItemVideo = "";
    private String urlPresent = "https://www.youtube.com/watch?v=";
    private AppBarLayout alPlay;
    private TextView tvTitleVideo, tvTimeUp, tvCountViews, tvCountLiked, tvNameChannel,
            tvNumberSubscriber, tvSubscribe, tvNumberComment, tvSubscribed,  tvComment;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_play_video);

        mapping();
        mappingDes();

//        PLAY YOUTUBE VIDEO
        youTubePlayerFragment = (YouTubePlayerFragment) getFragmentManager()
                .findFragmentById(R.id.fm_contains_player_view);

        youTubePlayerFragment.initialize(API_KEY, this);

//        CHECK SUBSCRIBER
        tvSubscribe();

//        CHECK DATA NOT IN MAIN IS EXIST ?
        Intent getDataHome = getIntent();
        ItemVideoMain itemData = (ItemVideoMain) getDataHome
                .getSerializableExtra(VALUE_ITEM_VIDEO);
        Intent getDataSearch = getIntent();

        //        CHECK DATA NOT IN SEARCH IS EXIST ?
        ItemValueSearch itemValueSearch = (ItemValueSearch)
                getDataSearch.getSerializableExtra(VALUE_SEARCH);

        listPlayRelate.clear();

        if (itemValueSearch != null) {
            id = itemValueSearch.getIdVideo();
            tvTimeUp.setText(itemValueSearch.getTimeUp());
            tvTitleVideo.setText(itemValueSearch.getTitleVideo());
            tvCountViews.setText(itemValueSearch.getViewCount());
//            tvCountLiked.setText(itemValueSearch.);
//            tvNumberComment.setText(itemValueSearch.getC);
            tvNameChannel.setText(itemValueSearch.getChannelTitle());
            description = itemValueSearch.getDescriptionVideo();
            urlAvtChannel = itemValueSearch.getUrlAvtChannel();
//            tvNumberSubscriber.setText(itemValueSearch.get);
            Picasso.get().load(itemValueSearch.getUrlAvtChannel()).into(ivAvtChannel);
        } else {
            id = itemData.getIdVideo();
            tvTimeUp.setText(itemData.getTvTimeUp());
            tvTitleVideo.setText(itemData.getTvTitleVideo());
            tvCountViews.setText(itemData.getTvViewCount());
            tvCountLiked.setText(itemData.getLikeCount());
            tvNumberComment.setText(itemData.getTvCommentCount());
            tvNameChannel.setText(itemData.getTvNameChannel());
            tvNumberSubscriber.setText(itemData.getNumberSubscribe());
//        Toast.makeText(this, itemData.getIvVideo()+"", Toast.LENGTH_SHORT).show();
            Picasso.get().load(itemData.getUrlAvtChannel()).into(ivAvtChannel);
            urlAvtChannel = itemData.getUrlAvtChannel();
            description = itemData.getDescription();
//            Log.d("DESCRIPTION:", itemData.getDescription()+"");
        }
//        Toast.makeText(this, id+"", Toast.LENGTH_SHORT).show();
        getVideoRelated(id);
//       LAYOUT LIST COMMENT
        LinearLayoutManager linearLayoutComment = new LinearLayoutManager(this);
        rvListComment.setLayoutManager(linearLayoutComment);

//         LIST VIDEO RELATED
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvListVideoPlay.setLayoutManager(linearLayoutManager);
//        Toast.makeText(this, FragmentHome.listPlayRelated.size() + "", Toast.LENGTH_SHORT).show();
        adapterListVideoYoutube =
                new AdapterMainVideoYoutube(listPlayRelate,
                        new InterfaceClickFrameVideo() {
                            @Override
                            public void onClickTitleVideo(int position) {
                                id = listPlayRelate.get(position).getIdVideo();
//                                setReloadVideo(listPlayRelate.get(position));
                                setDataPlay(position);
                            }

                            @Override
                            public void onClickImageVideo(int position) {
//                                setReloadVideo(listPlayRelate.get(position));
                                setDataPlay(position);
                            }

                            @Override
                            public void onClickMenuVideo(int position) {
                                FragmentMenuItemVideoMain fragmentMenuItemVideoMain =
                                        new FragmentMenuItemVideoMain();
                                fragmentMenuItemVideoMain.show(getSupportFragmentManager(),
                                        fragmentMenuItemVideoMain.getTag());
                            }
                            @Override
                            public void onClickChannelVideo(int position) {

                            }
                        });
        rvListVideoPlay.setAdapter(adapterListVideoYoutube);
//        COMMENT
        btSheetComment = BottomSheetBehavior.from(bottomSheetComment);
        btDescription = BottomSheetBehavior.from(bottomSheetDescription);
        clComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btSheetComment.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    btSheetComment.setState(BottomSheetBehavior.STATE_EXPANDED);
                    setDataComment(id);
                    adapterListComment = new AdapterListComment(listComment);
                    rvListComment.setAdapter(adapterListComment);
                } else {
                    btSheetComment.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        ivLiked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberLikeCheck) {
                    ivLiked.setImageResource(drawable.ic_like_on);
                    ivDisliked.setImageResource(drawable.ic_dislike);
                    numberLikeCheck = false;
                } else {
                    ivLiked.setImageResource(drawable.ic_like);
                    numberLikeCheck = true;
                }
            }
        });

        ivDisliked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDisplay(btSheetComment);
            }
        });

        tvSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSubscribe.setVisibility(View.GONE);
                tvSubscribed.setVisibility(View.VISIBLE);
                cbNotificationChannel.setVisibility(View.VISIBLE);
                cbNotificationChannel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            Snackbar snackbar = Snackbar
                                    .make(cbNotificationChannel,
                                            "Notifications turned on",
                                            Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }
                });
            }
        });

        tvSubscribed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.setMessage("Unsubscribe from pike channel ?");
                alertDialog.setPositiveButton("UNSUBSCRIBE",
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvSubscribe.setVisibility(View.VISIBLE);
                        tvSubscribed.setVisibility(View.GONE);
                        cbNotificationChannel.setVisibility(View.GONE);
                    }
                });
                alertDialog.setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
            }
        });

//  OPEN DESCRIPTION
        tvTitleVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvTitleDes.equals(tvTitleVideo)){

                }
                else{
                    setDescription();
                }
                bottomSheetDisplay(btDescription);
            }
        });

        ivOpenDescription.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View v) {
                if (tvTitleDes.equals(tvTitleVideo)){

                }
                else{
                    setDescription();
                }
                bottomSheetDisplay(btDescription);
            }
        });

        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String sub = "Your Subject";
                myIntent.putExtra(Intent.EXTRA_SUBJECT, sub);
                myIntent.putExtra(Intent.EXTRA_TEXT, urlPresent + id);
                startActivity(Intent.createChooser(myIntent, getString(string.share_video)));
            }
        });
        Toast.makeText(this, id+"huhhuh", Toast.LENGTH_SHORT).show();
//        ypPlayItemClick.loadVideo(id);
    }

    public void clickChannel(View view) {
        finish();
        Toast.makeText(this, "Channel "+tvNameChannel.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    private void setReloadVideo(@NonNull ItemVideoMain idClick){
//        ypPlayItemClick.loadVideo(idClick.getIdVideo());
        Intent refresh = new Intent(this, ActivityPlayVideo.class);
        refresh.putExtra(VALUE_ITEM_VIDEO, idClick);
        startActivity(refresh);//Start the same Activity
        finish(); //finish Activity.

    }

    private void setDescription(){
        tvTitleDes.setText(tvTitleVideo.getText().toString());
        tvNameChannelDes.setText(tvNameChannel.getText().toString());
        Picasso.get().load(urlAvtChannel).into(ivAvtChannelDes);
        tvDescription.setText(description);
    }

//    BOTTOM SHEET
    @SuppressLint("ClickableViewAccessibility")
    private void bottomSheetDisplay(
            @NonNull BottomSheetBehavior bt){
//        alPlay.setClickable(false);
//        nsPlay.setClickable(false);
//        alPlay.setNestedScrollingEnabled(false);
//        nsPlay.setNestedScrollingEnabled(false);
        if (bt.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            bt.setState(BottomSheetBehavior.STATE_EXPANDED);
//            setDataComment(id);
        } else {
            bt.setState(BottomSheetBehavior.STATE_COLLAPSED);
            alPlay.setVisibility(View.VISIBLE);
            nsPlay.setVisibility(View.VISIBLE);
        }
    }

    private void setDataPlay(int position) {
        idPlayListInItemVideo = listPlayRelate.get(position).getIdVideo();
        tvTimeUp.setText(listPlayRelate.get(position).getTvTimeUp());
        tvTitleVideo.setText(listPlayRelate.get(position).getTvTitleVideo());
        tvCountViews.setText(listPlayRelate.get(position).getTvViewCount());
        tvCountLiked.setText(listPlayRelate.get(position).getLikeCount());
        tvNumberComment.setText(listPlayRelate.get(position).getTvCommentCount());
        tvNameChannel.setText(listPlayRelate.get(position).getTvNameChannel());
        tvCountLiked.setText(listPlayRelate.get(position).getLikeCount());
        tvNumberSubscriber.setText(listPlayRelate.get(position).getNumberSubscribe());
        description = listPlayRelate.get(position).getDescription();
        urlAvtChannel = listPlayRelate.get(position).getUrlAvtChannel();
//        Toast.makeText(this, listPlayRelate.get(position).getIvVideo()+"", Toast.LENGTH_SHORT).show();
        Picasso.get().load(listPlayRelate.get(position)
                .getUrlAvtChannel()).into(ivAvtChannel);
        ypPlayItemClick.loadVideo(idPlayListInItemVideo);
/*        Log.d("AAAAAAAAAAAAAAAA", listPlayRelate
                .get(position).getUrlAvtChannel() + "");*/

//                Toast.makeText(ActivityPlayVideo.this, idPlayListInItemVideo+"", Toast.LENGTH_SHORT).show();
    }

    private void setDataComment(String idVideo) {
        String API_LIST_COMMENT_VIDEO =
                "https://youtube.googleapis.com/youtube/v3/commentThreads?part=snippet&maxResults=100&order=relevance&textFormat=plainText&videoId="
                        + idVideo + "&key=" + API_KEY
                        +"&fbclid=IwAR3WPsV7YUhleTcSEzTMCEQKKqMokxOUqwFEO41ELUw0s7TVhUjmaSRmlAg";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                API_LIST_COMMENT_VIDEO, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String nameChannelComment = "";
                    String urlAvtChannelComment = "";
                    String timeComment = "";
                    String contentComment = "";
                    String numberLikeComment = "";
                    String totalReplyCount = "";
                    JSONArray jsonItems = response.getJSONArray(ITEMS);
                    Log.d("AAAAAAAAAAAA", jsonItems.length()+"");
                    for (int i = 0; i < jsonItems.length(); i++) {
                        JSONObject jsonItem = jsonItems.getJSONObject(i);
//                        Log.d("AGUGUGUU", jsonItem.getString(ID));
                        JSONObject jsonSnippet = jsonItem.getJSONObject(SNIPPET);
                        totalReplyCount = jsonSnippet.getString(TOTAL_REPLY_COUNT);
//                        Log.d(TOTAL_REPLY_COUNT + " "+ i, totalReplyCount+"");
                        JSONObject jsonTopLevelComment = jsonSnippet.getJSONObject(TOP_LEVEL_COMMENT);
                        JSONObject jsonSnippetSub = jsonTopLevelComment.getJSONObject(SNIPPET);
                        contentComment = jsonSnippetSub.getString(TEXT_DISPLAY);
//                        Log.d(TEXT_DISPLAY+" "+i, contentComment);
                        nameChannelComment = jsonSnippetSub.getString(AUTHOR_DISPLAY_NAME);
//                        Log.d(AUTHOR_DISPLAY_NAME+i, nameChannelComment);
                        numberLikeComment = formatData(Integer.parseInt(jsonSnippetSub.getString(LIKED_COUNT)));
//                        Log.d(LIKED_COUNT+" "+i, numberLikeComment);
                        timeComment = formatTimeUpVideo(jsonSnippetSub.getString(PUBLISHED_AT)+"");
//                        timeComment = jsonSnippetSub.getString(PUBLISHED_AT);
//                        Log.d(PUBLISHED_AT + i, timeComment);
                        urlAvtChannelComment = jsonSnippetSub.getString(AUTHOR_PROFILE_IMAGE_URL);
                        Log.d(AUTHOR_PROFILE_IMAGE_URL+" "+i, urlAvtChannelComment);
                        listComment.add(new ItemComment(nameChannelComment,
                                urlAvtChannelComment, timeComment,
                                contentComment, numberLikeComment,
                                totalReplyCount));
                        adapterListComment.notifyItemChanged(i);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ActivityPlayVideo.this, error + "LIST COMMENT", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    // GET LIST VIDEO RELATED
    private void getVideoRelated(String idRelated) {
        String API_RELATED_VIDEO = "https://youtube.googleapis.com/youtube/v3/search?part=snippet&maxResults=25&relatedToVideoId="
                + idRelated + "&type=video&key=" + API_KEY + "";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                API_RELATED_VIDEO, null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonItems = response.getJSONArray(ITEMS);
//                            Log.d("LENGTHHHHHH", jsonItems.length() + "");
                            String idVideo = "";
                            String titleVideo = "Best Relaxing Piano Studio Ghibli Complete Collection 2022 (No Mid-roll Ads)";
                            String publishedAt = "";
                            String idChannel = "";
                            String urlThumbnail = "";
                            String channelName = "";
                            String numberLiker = "";
                            String commentCount = "";
                            String description = "";
                            int size = 0;
                            for (int i = 0; i < 4; i++) {
                                JSONObject jsonItem = jsonItems.getJSONObject(i);
                                JSONObject jsonIdVideo = jsonItem.getJSONObject(ID);
                                idVideo = jsonIdVideo.getString(ID_VIDEO);
//                        Log.d("ID", idVideo+"");
                                if (jsonItem.has(SNIPPET)) {
                                    getInfoVideo(idVideo, i - size, jsonItems.length() - size);
                                    JSONObject jsonSnippet = jsonItem.getJSONObject(SNIPPET);
                                    titleVideo = jsonSnippet.getString(TITLE);
//                            Log.d("LOGGG"+i, titleVideo+"");
                                    description = jsonSnippet.getString(DESCRIPTION);
//                                    Log.d(DESCRIPTION+" :", description);
                                    idChannel = jsonSnippet.getString(CHANNEL_ID);
//                            Log.d("ID CHANNEL "+i, idChannel+"");
                                    getInfoChannel(idChannel, i - size, jsonItems.length() - size);
                                    JSONObject jsonThumbnail = jsonSnippet.getJSONObject(THUMBNAIL);
                                    JSONObject jsonHighImg = jsonThumbnail.getJSONObject(HIGH);
                                    urlThumbnail = jsonHighImg.getString(URL);
//                            Log.d("IMAGE "+i, urlThumbnail);
                                    channelName = jsonSnippet.getString(CHANNEL_TITLE);
//                                    Log.d("CHANNEL NAME: " + i, channelName + "");
                                    publishedAt = formatTimeUpVideo(jsonSnippet
                                            .getString(PUBLISHED_AT) + "");
//                                    Log.d(PUBLISHED_AT, publishedAt + "");
//                            Log.d("JSON STATICS: ", listViewer.size()+"")
                                } else {
                                    size++;
                                    continue;
                                }
                                listPlayRelate.add(new ItemVideoMain(titleVideo,
                                        urlThumbnail, idChannel,
                                        channelName, publishedAt,
                                        idVideo, commentCount,
                                        numberLiker, description));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ActivityPlayVideo.this, error + "",
                        Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void tvSubscribe() {
        alertDialog = new AlertDialog.Builder(this);
        testDisplayTvSubscribe();
    }

    //    GET INFO CHANNEL (URL avt, SubScribe, amount Comment)
    private void getInfoChannel(String ID_CHANNEL, int position, int size) {
        if (position < size) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
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
                            listPlayRelate.get(position).setUrlAvtChannel(jsonHigh.getString(URL) + "");
//                            Log.d("LINKKKKKKKK " + position, jsonHigh.getString(URL));
                        }
                        JSONObject jsonStatics = jsonItem.getJSONObject(STATISTICS);
                        if (jsonStatics.has(SUBSCRIBE_COUNT)) {
                            listPlayRelate.get(position).setNumberSubscribe(formatData
                                    (Integer.parseInt(jsonStatics.getString(SUBSCRIBE_COUNT))) + " Subscribers");
//                    Log.d("AAAAA " + position, urlChannel);
                        }
                        adapterListVideoYoutube.notifyItemChanged(position);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ActivityPlayVideo.this, "FALSE GET URL AVT CHANNEL",
                            Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
        }
    }

    private void getInfoVideo(String id, int position, int size) {
        if (position < size) {
//            Log.d("IDDDDDDDDDDD", id + "");
            String API_VIEWER = "https://youtube.googleapis.com/youtube/v3/videos?part=statistics&id="
                    + id + "&key=" + API_KEY + "";
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                    API_VIEWER, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonItems = response.getJSONArray(ITEMS);
                        JSONObject jsonItem = jsonItems.getJSONObject(0);
                        JSONObject jsonStatics = jsonItem.getJSONObject(STATISTICS);
                        listPlayRelate.get(position).setTvViewCount(formatData(
                                Integer.parseInt(jsonStatics.getString(VIEW_COUNT))) + " Views");
//                        Log.d("VIEWWWWWWWWWWW" + position, Integer.parseInt(jsonStatics.getString(VIEW_COUNT)) + "Views");
                        if (jsonStatics.has(LIKED_COUNT)) {
                            listPlayRelate.get(position).setLikeCount(
                                    formatData(Integer.parseInt(jsonStatics.getString(LIKED_COUNT))));
//                            Log.d("LIKEEEEEEEEEEE", formatData(Integer.parseInt(jsonStatics.getString(LIKED_COUNT))));
                        } else {
                            listPlayRelate.get(position).setLikeCount("");
                        }
                        if (jsonStatics.has(COMMENT_COUNT)){
                            listPlayRelate.get(position).setTvCommentCount(formatData(Integer.parseInt(jsonStatics.getString(COMMENT_COUNT))));
                        }
                        else{
                            listPlayRelate.get(position).setTvCommentCount("Video does not support comments");
                        }
                        adapterListVideoYoutube.notifyItemChanged(position);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ActivityPlayVideo.this, error + "", Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
        }
    }

    private void mappingDes(){
        tvTitleDes = findViewById(R.id.tv_title_des);
        tvNameChannelDes = findViewById(R.id.tv_name_channel_des);
        tvLikeDes = findViewById(R.id.tv_number_like_des);
        tvViewDes = findViewById(R.id.tv_number_view_des);
        ivAvtChannelDes = findViewById(R.id.iv_avt_channel_des);
        tvTimeDes = findViewById(R.id.tv_number_time_des);
        tvDescription = findViewById(R.id.tv_des_main);
        alPlay = findViewById(R.id.appbar);
        nsPlay = findViewById(R.id.nestedScrollView);
    }

    private void mapping() {
        bottomSheetDescription = findViewById(R.id.layout_view_description);
        ivShare = findViewById(R.id.iv_share_play_video);
        pbLoad = findViewById(R.id.pb_load_video_related);
        tvSubscribe = findViewById(R.id.tv_play_video_subscribe);
        tvCountLiked = findViewById(R.id.tv_like_toolbar);
        tvCountViews = findViewById(R.id.tv_play_video_count_viewer);
        tvTitleVideo = findViewById(R.id.tv_title_video_play);
        tvTimeUp = findViewById(R.id.tv_play_video_count_time);
        tvNameChannel = findViewById(R.id.tv_name_channel_play);
        tvNumberSubscriber = findViewById(R.id.tv_amount_subscriber);
        tvNumberComment = findViewById(R.id.tv_number_comment);
        tvSubscribed = findViewById(R.id.tv_play_video_subscribed);
        ivAvtChannel = findViewById(R.id.iv_avt_channel_play);
        ivLiked = findViewById(R.id.iv_ic_like_play_video);
        ivDisliked = findViewById(R.id.iv_ic_dislike_play_video);
        ivOpenDescription = findViewById(R.id.iv_icon_down_description);
        cbNotificationChannel = findViewById(R.id.cb_on_notification_channel);
        rvListVideoPlay = findViewById(R.id.rv_list_play_video);
        clComment = findViewById(R.id.cl_comment);
        bottomSheetComment = findViewById(R.id.ll_contains_comment_list);
        ivUserComment = findViewById(R.id.iv_avt_user_comments);
        etUserComment = findViewById(R.id.et_user_comment);
        rvListComment = findViewById(R.id.rv_list_comment_video);
        tvComment = findViewById(R.id.tv_sheet_comment);
        ivClip = findViewById(R.id.iv_ic_clip_play_video);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String formatTimeUpVideo(String time) {
        String timeEnd = java.time.Clock.systemUTC().instant().toString();
        String timeStart = time;
        Instant start = Instant.parse(timeStart);
        Instant end = Instant.parse(timeEnd);

        long duration = Duration.between(start, end).toMillis();
        int hour = (int) TimeUnit.MILLISECONDS.toHours(duration);
        int min = (int) (TimeUnit.MILLISECONDS.toMinutes(duration)
                - TimeUnit.MILLISECONDS.toHours(duration) * 60);
//        int second = (int) (TimeUnit.MILLISECONDS.toSeconds(duration) - minutes);
        String timeUp = "1 hour ago";
        if (hour > 8760) {
            timeUp = (hour / 8760) + " year ago";
        }
        if (hour > 720 && hour < 8760) {
            timeUp = (hour / 720) + " month ago";
        }
        if (hour > 168 && hour < 720) {
            timeUp = (hour / 168) + " week ago";
        }
        if (hour < 168 && hour > 24) {
            timeUp = (hour / 24) + " day ago";
        }
        if (hour > 1 && hour < 24) {
            timeUp = (hour) + " hour ago";
        }
        if (hour < 1) {
            timeUp = min + " min ago";
        }
        if (min< 1){
            timeUp = " few seconds ago";
        }
        return timeUp;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    public void testDisplayTvSubscribe() {
        if (tvSubscribe.getVisibility() == View.VISIBLE) {
            tvSubscribed.setVisibility(View.GONE);
            cbNotificationChannel.setVisibility(View.GONE);
        } else {
            tvSubscribed.setVisibility(View.VISIBLE);
            tvSubscribe.setVisibility(View.GONE);
            cbNotificationChannel.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        @NonNull YouTubePlayer youTubePlayer, boolean b) {
//        ypPlayItemClick = youTubePlayer;
//        ypPlayItemClick.loadVideo(id);
        if (!b) {
            ypPlayItemClick = youTubePlayer;
            ypPlayItemClick.loadVideo(id);
            Toast.makeText(this, "hehe "+id, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, youTubeInitializationResult
                + " LOI ROI CU", Toast.LENGTH_SHORT).show();
    }
}