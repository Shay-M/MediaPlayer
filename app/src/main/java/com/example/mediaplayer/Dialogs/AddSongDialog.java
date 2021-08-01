package com.example.mediaplayer.Dialogs;/* Created by Shay Mualem 29/07/2021 */

import android.app.Dialog;
import android.content.Context;
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

import java.io.File;

public class AddSongDialog extends DialogFragment {


    //private  MainActivity mainActivity;
    private EditText linkText;
    private EditText nameText;
    private ImageView takeApicBtn;
    private ImageView addApic;
    private ImageView picContentView;

    private File file;


    private ManagerListSongs managerListSongs;
    private CameraManagerUrl cameraManagerUrl;

    private AddSongDialogListener listener;

//    public AddSongDialog(MainActivity mainActivity) {
//        this.mainActivity = mainActivity;
//    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        cameraManagerUrl = CameraManagerUrl.getInstance();
        managerListSongs = ManagerListSongs.getInstance();
//        cameraManagerUrl = CameraManagerUrl.getInstance();

        //initLaunchers();

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
                            managerListSongs.addSong(songUrl);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        String Name = linkText.getText().toString();
                        String imgUrl = linkText.getText().toString();

                        listener.applyAddSong(Name, songUrl, imgUrl);
                    }
                });


        linkText = view.findViewById(R.id.dialog_link);
        nameText = view.findViewById(R.id.dialog_name);

        takeApicBtn = view.findViewById(R.id.take_a_pic);
        addApic = view.findViewById(R.id.add_a_pic);
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
            // e.printStackTrace();
            throw new ClassCastException(context.toString() + "must implement AddSongDialogListener!");
        }

    }

    private void takeApicFromCamera() {
        cameraManagerUrl.dispatchTakePictureIntent();

        Glide.with(this).load(cameraManagerUrl.dispatchTakePictureIntent()).into(picContentView);

    }

    private void initLaunchers() {

//        cameraFullSizeResultLauncher = registerForActivityResult(
//                new ActivityResultContracts.TakePicture(),
//                new ActivityResultCallback<Boolean>() {
//                    @Override
//                    public void onActivityResult(Boolean result) {
//                    //true if the image saved to the uri given in the launch function
//                    }
//                });

//        someActivityResultLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult result) {
//                        if (result.getResultCode() == Activity.RESULT_OK) {
//                            Log.d("getAbsolutePath", "onActivityResult: " + file.getAbsolutePath());
//
//                        }
//                    }
//                });
//        file = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "pic1.jpg");

    }

    public interface AddSongDialogListener {
        void applyAddSong(String Name, String songUrl, String imgUrl);
    }
}
