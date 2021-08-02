package com.example.mediaplayer.SongsRecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.mediaplayer.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SoundBigFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SoundBigFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String song_name = "param1";
    private static final String song_uri = "param2";
    private Uri imgUri;
    // TODO: Rename and change types of parameters
    private String mSongName;
    private String mSongUri;

    public SoundBigFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SoundBigFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SoundBigFragment newInstance(String songName, Uri songUri) {
        SoundBigFragment fragment = new SoundBigFragment();
        Bundle args = new Bundle(); //Contains all the settings, sorting the values in it
        args.putString(song_name, songName);
        args.putString(song_uri, String.valueOf(songUri));
        fragment.setArguments(args); //Each fragment has an internal-field where we store the information
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //get the parameters
            mSongName = getArguments().getString(song_name);
            mSongUri = getArguments().getString(song_uri);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sound_big, container, false);

        imgUri = Uri.parse(mSongUri);

        TextView songTitle = rootView.findViewById(R.id.big_name_song);
        ImageView songImg = rootView.findViewById(R.id.big_pic_song);
        songTitle.setText(mSongName);
        Glide.with(this).load(imgUri).centerCrop().into(songImg);//.thumbnail(0.10f)



        return rootView;
    }
}