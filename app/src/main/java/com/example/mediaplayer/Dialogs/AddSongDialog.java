package com.example.mediaplayer.Dialogs;/* Created by Shay Mualem 29/07/2021 */

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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

                        try {
                            managerListSongs.addSong(songUrl, null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        String Name = nameText.getText().toString();
                        String url = linkText.getText().toString();


                        listener.applyAddSong(Name, url, imgUri);

                    }
                });


        linkText = view.findViewById(R.id.dialog_link);
        nameText = view.findViewById(R.id.dialog_name);

        ImageView takeApicBtn = view.findViewById(R.id.take_a_pic);
        picContentView = view.findViewById(R.id.song_image);

        takeApicBtn.setOnClickListener(v -> takeApicFromCamera());

        return builder.create();
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
        Glide.with(this).load(imgUri).into(picContentView);

    }

//    private void initDialog() {
//
////        cameraFullSizeResultLauncher = registerForActivityResult(
////                new ActivityResultContracts.TakePicture(),
////                new ActivityResultCallback<Boolean>() {
////                    @Override
////                    public void onActivityResult(Boolean result) {
////                    //true if the image saved to the uri given in the launch function
////                    }
////                });
//
////        someActivityResultLauncher = registerForActivityResult(
////                new ActivityResultContracts.StartActivityForResult(),
////                new ActivityResultCallback<ActivityResult>() {
////                    @Override
////                    public void onActivityResult(ActivityResult result) {
////                        if (result.getResultCode() == Activity.RESULT_OK) {
////                            Log.d("getAbsolutePath", "onActivityResult: " + file.getAbsolutePath());
////
////                        }
////                    }
////                });
////        file = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "pic1.jpg");
//
//    }

    public interface AddSongDialogListener {
        void applyAddSong(String Name, String songUrl, Uri imgUri);
    }
}
