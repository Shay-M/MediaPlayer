package com.example.mediaplayer.SongsRecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediaplayer.ManagerSongs.ManagerListSongs;
import com.example.mediaplayer.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SongRecyclerView_UpdateUI_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SongRecyclerView_UpdateUI_Fragment extends Fragment {
    ///////
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private  static SongAdapter.RecyclerViewListener recyclerViewListener = null;

    private RecyclerView recyclerView;
    private ManagerListSongs managerListSongs;
    //    private RecyclerViewClickInterface recyclerViewClickInterface;
    //private AtomicReference<SongAdapter> songAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //////

    private SongAdapter songAdapter;

    public SongRecyclerView_UpdateUI_Fragment() {
        // Required empty public constructor
    }

    public SongRecyclerView_UpdateUI_Fragment(SongAdapter.RecyclerViewListener recyclerViewListener) {
        this.recyclerViewListener = recyclerViewListener;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the ***provided parameters.***
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SongRecyclerView_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SongRecyclerView_UpdateUI_Fragment newInstance(String param1, String param2) {
        SongRecyclerView_UpdateUI_Fragment fragment = new SongRecyclerView_UpdateUI_Fragment(recyclerViewListener);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment לנפח
        View rootView = inflater.inflate(R.layout.fragment_song_recycler_view, container, false);

        recyclerView = rootView.findViewById(R.id.recycler_view_of_songs);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        managerListSongs = ManagerListSongs.getInstance();

        songAdapter = new SongAdapter(managerListSongs.getListOfSongsItems(), getActivity());
//        songAdapter.notifyDataSetChanged();


        songAdapter.setListener(recyclerViewListener);


        recyclerView.setAdapter(songAdapter);

        managerListSongs.setListener(new ManagerListSongs.RecyclerViewUpdateUIListener() {
            @Override
            public void onUpdateListItem() {
                songAdapter.notifyDataSetChanged();
                //songAdapter.notifyItemInserted(songAdapter.getItemCount() + 1);
            }
        });

        return rootView;
    }


}