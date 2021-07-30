package com.example.mediaplayer.utils;/* Created by Shay Mualem 30/07/2021 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.mediaplayer.MainActivity;

import java.io.File;

public class CameraManagerUrl extends AppCompatActivity {
    private static CameraManagerUrl instead;

    File file;

    ActivityResultLauncher<Intent> someActivityResultLauncher;

    private MainActivity context;

    public CameraManagerUrl(MainActivity context) {
        this.context = context;

        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Log.d("someActivityResultLauncher", file.getAbsolutePath());

                    }
                });

        file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "pic1.jpg");


    }

    //singleton
    public static CameraManagerUrl getInstance(MainActivity context) {
        if (instead == null && context != null)
            instead = new CameraManagerUrl(context);
        return instead;
    }

    public void TakePicFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri fileUri = FileProvider.getUriForFile(this, context.getPackageName() + ".provider", file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        Toast.makeText(context, fileUri.toString(), Toast.LENGTH_LONG).show();
        Log.d("TakePicFromCamera", fileUri.toString());

        someActivityResultLauncher.launch(intent);
        //return fileUri.toString();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


}
