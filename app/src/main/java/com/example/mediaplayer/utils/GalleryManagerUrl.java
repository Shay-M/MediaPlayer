//package com.example.mediaplayer.utils;/* Created by Shay Mualem 01/08/2021 */
//
//import android.net.Uri;
//import android.widget.ImageView;
//
//import androidx.activity.result.ActivityResultCallback;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.appcompat.app.AppCompatActivity;
//
//public class GalleryManagerUrl extends AppCompatActivity {
//
//    public static final int PICK_IMAGE = 3;
//    Uri resultIv;
//
//
//    ActivityResultLauncher<String> pickContentResultLauncher;
//
//
//    public GalleryManagerUrl() {
//
//        pickContentResultLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
//            @Override
//            public void onActivityResult(Uri result) {
//                resultIv = result;
//            }
//        });
//    }
//
//    public void fileFun() {
//
//        pickContentResultLauncher.launch("image/*");
//    }
//
//}
//
//
//}
//
//
