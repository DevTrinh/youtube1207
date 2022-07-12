package com.example.youtubeapp.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubeapp.R;
import com.example.youtubeapp.interfacee.InterfaceClickFrameVideo;
import com.example.youtubeapp.interfacee.InterfaceDefaultValue;
import com.example.youtubeapp.item.ItemValueSearch;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterValueSearch extends RecyclerView.Adapter<AdapterValueSearch.ItemValueSearchViewHolder> implements InterfaceDefaultValue {

    ArrayList<ItemValueSearch> listValueSearch = new ArrayList<>();
    InterfaceClickFrameVideo interfaceClickFrameVideo;

    public AdapterValueSearch(ArrayList<ItemValueSearch> listValueSearch, InterfaceClickFrameVideo interfaceClickFrameVideo) {
        this.listValueSearch = listValueSearch;
        this.interfaceClickFrameVideo = interfaceClickFrameVideo;
    }

    @NonNull
    @Override
    public ItemValueSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_video, parent, false);
        return new ItemValueSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemValueSearchViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {
        ItemValueSearch itemValueSearch = listValueSearch.get(position);
        Picasso.get().load(itemValueSearch.getUrlImage()).into(holder.ivImageAvt);
        Picasso.get().load(itemValueSearch.getUrlAvtChannel()).into(holder.ivChannel);
        holder.tvTimeUp.setText(itemValueSearch.getTimeUp());
        holder.tvViewer.setText(itemValueSearch.getViewCount());
        holder.tvNameChannel.setText(itemValueSearch.getChannelTitle());
        holder.tvTitleVideo.setText(itemValueSearch.getTitleVideo());
        holder.tvTitleVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*TITTLE ONCLICK*/
                interfaceClickFrameVideo.onClickTitleVideo(position);
            }
        });
        holder.ivImageAvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*IMAGEVIEW ONCLICK*/
                interfaceClickFrameVideo.onClickImageVideo(position);
            }
        });
        holder.ivChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceClickFrameVideo.onClickChannelVideo(position);
            }
        });

        holder.ivMenuVertical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceClickFrameVideo.onClickMenuVideo(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listValueSearch == null){
            return 0;
        }
        return listValueSearch.size();
    }

    public class ItemValueSearchViewHolder extends RecyclerView.ViewHolder{
        private YouTubeThumbnailView ivImageAvt;
        private ImageView ivChannel, ivMenuVertical;
        private TextView tvTitleVideo;
        private TextView tvNameChannel;
        private TextView tvViewer;
        private TextView tvTimeUp;

       public ItemValueSearchViewHolder(@NonNull View itemView) {
           super(itemView);
           mapping(itemView);
       }

       public void mapping(@NonNull View view){
           ivMenuVertical = view.findViewById(R.id.iv_item_main_menu_vertical);
           ivImageAvt  = view.findViewById(R.id.iv_item_main_video);
           ivChannel  = view.findViewById(R.id.iv_item_main_avt_video);
           tvTitleVideo = view.findViewById(R.id.tv_item_main_title_video);
           tvNameChannel  = view.findViewById(R.id.tv_item_main_name_channel);
           tvViewer = view.findViewById(R.id.tv_item_main_view_count);
           tvTimeUp = view.findViewById(R.id.tv_item_main_time_up);
       }
   }
}
