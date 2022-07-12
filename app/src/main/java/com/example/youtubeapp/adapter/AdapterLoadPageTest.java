package com.example.youtubeapp.adapter;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubeapp.R;
import com.example.youtubeapp.interfacee.InterfaceClickFrameVideo;
import com.example.youtubeapp.item.ItemVideoMain;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterLoadPageTest extends
        RecyclerView.Adapter {

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_LOADING = 2;

    private boolean isLoadingAdd;
    private ArrayList<ItemVideoMain> listItemVideoMain;
    private InterfaceClickFrameVideo interfaceClickFrameVideo;

    public AdapterLoadPageTest(ArrayList<ItemVideoMain> listItemVideoMain,
                                   InterfaceClickFrameVideo interfaceClickFrameVideo) {
        this.listItemVideoMain = listItemVideoMain;
        this.interfaceClickFrameVideo = interfaceClickFrameVideo;
    }

    @Override
    public int getItemViewType(int position) {

        if (listItemVideoMain != null && listItemVideoMain.size()-1 == position && isLoadingAdd){
            return TYPE_LOADING;
        }
        return TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (TYPE_ITEM == viewType){
            View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_video, null);
            return new ItemVideoMainViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prgessbar, null);
            return new ItemLoadViewHolder(view) ;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_ITEM){
            ItemVideoMain itemVideoMain = listItemVideoMain.get(position);
            ItemVideoMainViewHolder itemVideoMainViewHolder = (ItemVideoMainViewHolder) holder;
            Picasso.get().load(itemVideoMain.getUrlAvtChannel()).into(itemVideoMainViewHolder.ivAvtChannel);
            Picasso.get().load(itemVideoMain.getIvVideo()).into(itemVideoMainViewHolder.youTubeThumbnailView);
            itemVideoMainViewHolder.tvTitleMainItem.setText(itemVideoMain.getTvTitleVideo());
            itemVideoMainViewHolder.tvTimeUp.setText(itemVideoMain.getTvTimeUp());
            itemVideoMainViewHolder.tvNameChannel.setText(itemVideoMain.getTvNameChannel());
            itemVideoMainViewHolder.tvViewer.setText(itemVideoMain.getTvViewCount());
        }
    }

    @Override
    public int getItemCount() {
        if (listItemVideoMain == null) {
            return 0;
        }
        return listItemVideoMain.size();
    }

    public void addFooterLoading(){
        isLoadingAdd = true;
        listItemVideoMain.add(new ItemVideoMain("", "", "", "", "", "", "", "", ""));
    }

    public void removeFooterLoading(){
        isLoadingAdd = false;
        int position = listItemVideoMain.size() - 1;
        ItemVideoMain itemVideoMain = listItemVideoMain.get(position);
        if (itemVideoMain != null){
            listItemVideoMain.remove(position);
            notifyItemRemoved(position);
        }
    }

    public class ItemVideoMainViewHolder extends RecyclerView.ViewHolder {
        YouTubeThumbnailView youTubeThumbnailView;
        ImageView ivAvtChannel, ivMenuVertical;
        TextView tvTitleMainItem, tvNameChannel, tvViewer, tvTimeUp;

        public ItemVideoMainViewHolder(@NonNull View itemView) {
            super(itemView);
            mapping(itemView);
        }

        public void mapping(@NonNull View view) {
            ivMenuVertical = view.findViewById(R.id.iv_item_main_menu_vertical);
            youTubeThumbnailView = view.findViewById(R.id.iv_item_main_video);
            ivAvtChannel = view.findViewById(R.id.iv_item_main_avt_video);
            tvNameChannel = view.findViewById(R.id.tv_item_main_name_channel);
            tvTimeUp = view.findViewById(R.id.tv_item_main_time_up);
            tvTitleMainItem = view.findViewById(R.id.tv_item_main_title_video);
            tvViewer = view.findViewById(R.id.tv_item_main_view_count);
        }
    }

    public class ItemLoadViewHolder extends RecyclerView.ViewHolder{

        private ProgressBar pbLoadPage;

        public ItemLoadViewHolder(@NonNull View itemView) {
            super(itemView);

            pbLoadPage = itemView.findViewById(R.id.pb_load_video);
        }
    }
}