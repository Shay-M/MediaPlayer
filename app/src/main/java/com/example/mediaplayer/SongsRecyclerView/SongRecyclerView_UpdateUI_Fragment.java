package com.example.mediaplayer.SongsRecyclerView;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediaplayer.ManagerSongs.ManagerListSongs;
import com.example.mediaplayer.R;

import java.util.Collections;

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
    private static SongAdapter.RecyclerViewListener recyclerViewListener = null;

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


        songAdapter.setListener(recyclerViewListener);


        recyclerView.setAdapter(songAdapter);

        managerListSongs.setListener(new ManagerListSongs.RecyclerViewUpdateUIListener() {
            @Override
            public void onUpdateListItem() {
//                songAdapter.notifyDataSetChanged();
                songAdapter.notifyItemInserted(songAdapter.getItemCount() + 1);
            }
        });

        // https://www.youtube.com/watch?v=uvzP8KTz4Fg
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.START | ItemTouchHelper.END) {

            // Load the animation
            final Animation scaleDownAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.animate_scale_down);
            final Animation scaleUpAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.animate_scale_up);

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();

                // update lists the new Position
                Collections.swap(managerListSongs.getListOfSongsItems(), fromPosition, toPosition);
                Collections.swap(managerListSongs.getListOfUrlSongs(), fromPosition, toPosition);

                // update ui
                songAdapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }


            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                Spanned Message = Html.fromHtml("You are going to delete the song:\n" + "<br>" + "<b>" + managerListSongs.getListOfSongsItems().get(viewHolder.getAdapterPosition()).getName() + "<br>" + "<br>" + "</b>" + "This action cannot be undone!", HtmlCompat.FROM_HTML_MODE_LEGACY);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Please confirm").setMessage(Message).setIcon(android.R.drawable.ic_menu_delete)
                        .setNegativeButton("Cancel", (dialog, which) -> {
                            songAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                        })
                        .setPositiveButton("Delete", (dialog, which) -> {
                            //managerListSongs.RemovingSongFromList(viewHolder.getAdapterPosition()); //todo
                            managerListSongs.getListOfSongsItems().remove(viewHolder.getAdapterPosition());
                            managerListSongs.getListOfUrlSongs().remove(viewHolder.getAdapterPosition());
                            songAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                            managerListSongs.SaveSongList();
                        }).show();


            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                // Log.d("onSelectedChanged", "actionState: "+ actionState);
                // Log.d("onSelectedChanged", "");

                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    scaleUpAnimation.setFillAfter(true);
                    viewHolder.itemView.startAnimation(scaleUpAnimation);
                }

            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                if (viewHolder.itemView.getAnimation() != null)
                    viewHolder.itemView.startAnimation(scaleDownAnimation);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


        return rootView;
    }


}