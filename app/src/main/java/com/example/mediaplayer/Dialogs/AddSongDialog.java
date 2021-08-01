package com.example.mediaplayer.Dialogs;/* Created by Shay Mualem 29/07/2021 */

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.mediaplayer.ManagerSongs.ManagerListSongs;
import com.example.mediaplayer.R;
import com.example.mediaplayer.utils.CameraManagerUrl;

public class AddSongDialog extends DialogFragment {

    private EditText linkText;
    private EditText nameText;
    private ImageView picContentView;

    private Uri imgUri = null;

    private ManagerListSongs managerListSongs;
    private CameraManagerUrl cameraManagerUrl;

    private AddSongDialogListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        cameraManagerUrl = CameraManagerUrl.getInstance();
        managerListSongs = ManagerListSongs.getInstance();


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_song_dialog, null);

        builder.setView(view).setTitle("Add A Song").setMessage("You can attach a photo to the song from the gallery or take a photo")
                .setNegativeButton("Cancel", (dialog, which) -> {
                })
                .setPositiveButton("Add", (dialog, which) -> {
                    String songUrl = linkText.getText().toString();
                    if (!songUrl.isEmpty()) {

                        String Name = nameText.getText().toString();
                        String url = linkText.getText().toString();


                        listener.applyAddSong(Name, url, imgUri);

                    }
                });


        linkText = view.findViewById(R.id.dialog_link);
        nameText = view.findViewById(R.id.dialog_name);

        ImageView takeApicBtn = view.findViewById(R.id.take_a_pic);
        ImageView galleriapicBtn = view.findViewById(R.id.add_a_pic);

        picContentView = view.findViewById(R.id.song_image);

        //From Camera
        takeApicBtn.setOnClickListener(v -> takeApicFromCamera());
        //From Galleria
        galleriapicBtn.setOnClickListener(v -> picFromGalleria());

        return builder.create();
    }

    private void picFromGalleria() {
//        GalleryManagerUrl galleryManagerUrl = new GalleryManagerUrl();

//        galleryManagerUrl.fileFun();

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 3);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 3) {
            Log.d("TAG", "onActivityResult: " + data);
            imgUri = data.getData();
            Glide.with(this).load(data.getData()).centerCrop().thumbnail(0.10f).into(picContentView);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (AddSongDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement AddSongDialogListener , " + e.getMessage());
        }

    }

    private void takeApicFromCamera() {
        imgUri = cameraManagerUrl.dispatchTakePictureIntent();
        Glide.with(this).load(imgUri).centerCrop().into(picContentView);

    }


    public interface AddSongDialogListener {
        void applyAddSong(String Name, String songUrl, Uri imgUri);
    }
}
