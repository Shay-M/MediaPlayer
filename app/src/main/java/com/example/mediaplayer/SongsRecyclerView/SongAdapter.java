package com.example.mediaplayer.SongsRecyclerView;/* Created by Shay Mualem 23/07/2021 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediaplayer.R;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongsViewHolder> {
    private List<SongItem> songItemList;

    public SongAdapter(List<SongItem> songItemList) {
        this.songItemList = songItemList;
    }

    /**
     * @param parent   recyclerview
     * @param viewType cell type
     * @return Inflated the view xml
     */
    @NonNull
    @Override
    public SongsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_of_song, parent, false);
        return new SongsViewHolder(view);
    }

    /**
     * call to load a cell
     *
     * @param holder   type
     * @param position position of cell
     */
    @Override
    public void onBindViewHolder(SongAdapter.SongsViewHolder holder, int position) {
        SongItem SongItem = songItemList.get(position);
        holder.songTitleTv.setText(SongItem.getName());
//        holder.songDetailsTv.setText(SongItem.getUrl());
        holder.songDetailsTv.setText(SongItem.getDuration());
//        holder.songImageIv.setImageResource(SongItem.getImageView());

    }

    /**
     * @return how many item have
     */
    @Override
    public int getItemCount() {
        return songItemList.size();
    }

    /**
     * when we have only one type of cell, this fun first call.
     *
     * @param position the next cell to show
     * @return type of cell
     */
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    //crete view holder, for hold reference in a cell
    public class SongsViewHolder extends RecyclerView.ViewHolder {

        TextView songTitleTv;
        TextView songDetailsTv;
        ImageView songImageIv;


        public SongsViewHolder(View itemView) {
            super(itemView);
            songTitleTv = itemView.findViewById(R.id.song_title);
            songDetailsTv = itemView.findViewById(R.id.song_details);
            songImageIv = itemView.findViewById(R.id.song_image);
        }
    }


}

