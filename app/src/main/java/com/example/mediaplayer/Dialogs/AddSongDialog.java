package com.example.mediaplayer.Dialogs;/* Created by Shay Mualem 29/07/2021 */

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.mediaplayer.ManagerSongs.ManagerListSongs;
import com.example.mediaplayer.R;

import java.io.File;

public class AddSongDialog extends DialogFragment {
    final int CAMERA_REQUEST = 1;
    final int WRITE_PERMISSION_REQUEST = 2;

//    ActivityResultLauncher<Void> cameraResultLauncher;
    ActivityResultLauncher<Uri> cameraFullSizeResultLauncher;

    private EditText linkText;
    private EditText nameText;
    private ImageButton takeApicBtn;
    private ImageButton addApic;

    private File file;

    private ManagerListSongs managerListSongs;

    private AddSongDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        managerListSongs = ManagerListSongs.getInstance();

        initLaunchers();

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

//        file = new File(getExternalFilesDir(), "piccc.jpg");
//
//        Uri imageUri = FileProvider.getUriForFile(
//                this.getContext(),
//                "syntax.org.il.externalstorageexample.provider", //(use your app signature + ".provider" )
//                file);
//
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        startActivityForResult(intent, CAMERA_REQUEST);


    }

    private void initLaunchers() {

        cameraFullSizeResultLauncher = registerForActivityResult(new ActivityResultContracts.TakePicture(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {//true if the image saved to the uri given in the launch function

            }
        });

    }
}
