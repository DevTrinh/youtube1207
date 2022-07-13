package com.example.youtubeapp;

import static com.example.youtubeapp.preferences.PrefListSearch.getArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youtubeapp.adapter.AdapterHistorySearch;
import com.example.youtubeapp.interfacee.InterfaceClickFrameVideo;
import com.example.youtubeapp.interfacee.InterfaceClickSearch;
import com.example.youtubeapp.interfacee.InterfaceDefaultValue;
import com.example.youtubeapp.item.ItemSearch;
import com.example.youtubeapp.preferences.PrefListSearch;
import com.example.youtubeapp.preferences.PrefSearch;

import java.util.ArrayList;
import java.util.Locale;

public class ActivitySearchVideo extends AppCompatActivity implements InterfaceDefaultValue {
    private RecyclerView rvHistorySearch;
    public static AdapterHistorySearch adapterHistorySearch;
    public static EditText etSearch;
    private ArrayList<ItemSearch> listItemSearch = new ArrayList<>();
    private ArrayList<ItemSearch> listRevert = new ArrayList<>();
    private ArrayList<String> listSearchString = new ArrayList<>();
    private TextView tvHistorySearch;
    private PrefSearch prefSearch;
    private ImageView ivBackHome, ivMic;

    private final int REQ_CODE_SPEECH_INPUT = 100;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_video);
        mapping();

         prefSearch = new PrefSearch(this);

        if (prefSearch.getArrayList(PREF_SEARCH) != null){
            listSearchString = prefSearch.getArrayList(PREF_SEARCH);
            for (int i = 0; i<listSearchString.size(); i++){
                listItemSearch.add(new ItemSearch(listSearchString.get(i)+""));
//                Log.d("SIZEEEEEEEEE "+i, listSearchString.get(i)+"");
            }
            for(int i = listItemSearch.size() - 1; i>0; i--){
                listRevert.add(new ItemSearch(listItemSearch.get(i).getString()));
            }
        }
//        listSearchString = prefSearch.getArrayList(PREF_SEARCH);
        //get data Preferences

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvHistorySearch.setLayoutManager(linearLayoutManager);
        adapterHistorySearch = new AdapterHistorySearch(listRevert, new InterfaceClickSearch() {
            @Override
            public void onClickTextHistory(int position) {
                etSearch.setText(listRevert.get(position).getString());
                toValueSearch(listRevert.get(position).getString());
            }

            @Override
            public void onClickIconRightHistory(int position) {
                Toast.makeText(ActivitySearchVideo.this,
                        listRevert.get(position).getString()+"",
                        Toast.LENGTH_SHORT).show();
                etSearch.setText(listRevert.get(position).getString());
            }
        });
        rvHistorySearch.setAdapter(adapterHistorySearch);
        adapterHistorySearch.notifyDataSetChanged();
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    Log.d("VALUEEEEEEEEEEEEEEEEE", etSearch.getText().toString()+"");
                    listSearchString.add(etSearch.getText().toString()+"");
                    toValueSearch(etSearch.getText().toString()+"");
                }
                return false;
            }
        });

        ivMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeechInput();
            }
        });

        ivBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    etSearch.setText(result.get(0));
                }
                break;
            }
        }
    }

    @NonNull
    private ArrayList<ItemSearch> getHistorySearch(){
        ArrayList<ItemSearch> list = new ArrayList<>();
        for(int i = 0; i<listSearchString.size(); i++){
            listItemSearch.add(new ItemSearch(listSearchString.get(i)+""));
        }
        return list;
    }

    @Override
    protected void onResume() {
        adapterHistorySearch.notifyDataSetChanged();
        super.onResume();
    }

    public void toValueSearch(String value){
        prefSearch.saveArrayList(listSearchString, PREF_SEARCH);
        Intent returnMain = new Intent(ActivitySearchVideo.this, MainActivity.class);
        returnMain.putExtra(VALUE_SEARCH, value+"");
        startActivity(returnMain);
    }

    public ArrayList<ItemSearch> listSearch(){
        ArrayList<ItemSearch> list = new ArrayList<>();
        list.add(new ItemSearch("Face Book"));
        list.add(new ItemSearch("instagram"));
        list.add(new ItemSearch("Twister"));
        list.add(new ItemSearch("Face Book"));
        list.add(new ItemSearch("Face Book"));
        list.add(new ItemSearch("Face Book"));
        list.add(new ItemSearch("Face Book"));
        list.add(new ItemSearch("Face Book"));
        list.add(new ItemSearch("Face Book"));
        return list;
    }

    public void mapping(){
        ivMic = findViewById(R.id.iv_mic);
        ivBackHome = findViewById(R.id.ic_back_search);
        rvHistorySearch = findViewById(R.id.rv_history_search);
        tvHistorySearch = findViewById(R.id.tv_history);
        etSearch = findViewById(R.id.et_search_video);
    }
}