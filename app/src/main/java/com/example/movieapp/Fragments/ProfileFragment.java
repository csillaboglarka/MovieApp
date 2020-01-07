package com.example.movieapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieapp.DatabaseHelper;
import com.example.movieapp.Activities.MainActivity;
import com.example.movieapp.R;
import com.example.movieapp.User;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {
    DatabaseHelper db;
    TextView fname,lname;
    User user;
    Button logoutBtn,changePasswordBtn,chooseImageBtn,saveImageBtn;
    ImageView img;

    private static final int RESULT_LOAD_IMAGE=1;
    String path;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        SharedPreferences pref = getActivity().getPreferences(Context.MODE_PRIVATE);
        final String email = pref.getString("EMAIL", "");

       //nem kapja meg


        db =  new DatabaseHelper(getActivity());
        fname=v.findViewById(R.id.firstName);
        lname=v.findViewById(R.id.lastName);
        logoutBtn=v.findViewById(R.id.logout);
        changePasswordBtn=v.findViewById(R.id.changePassword);
        chooseImageBtn=v.findViewById(R.id.chooseImage);
        img=v.findViewById(R.id.userImage);

       ArrayList<String> datas = new ArrayList<>();
       datas = db.getDatas(email);



        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
            }
        });


        chooseImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery,RESULT_LOAD_IMAGE);
            }
        });





        return v;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data!=null)
        {
            Uri selectedImage = data.getData();
            path = selectedImage.toString();
            img.setImageURI(selectedImage);
        }
    }

}
