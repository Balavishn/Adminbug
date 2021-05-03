package com.babu.adminbug;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import static android.content.Context.MODE_PRIVATE;

public class profile extends Fragment {
    GoogleSignInAccount acc;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_profile, container, false);
        if (GoogleSignIn.getLastSignedInAccount(v.getContext())!=null) {
            acc = GoogleSignIn.getLastSignedInAccount(v.getContext());
        }
        TextView name=v.findViewById(R.id.Pname);
        TextView email=v.findViewById(R.id.Pemail);
        ImageView image=v.findViewById(R.id.imageView);
        TextView logout=v.findViewById(R.id.logout123);
        logout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            GoogleSignInClient mc=GoogleSignIn.getClient(view.getContext(),GoogleSignInOptions.DEFAULT_SIGN_IN);
            mc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.d("Logoutttt","logout account");
                    SharedPreferences preferences=v.getContext().getSharedPreferences("NUMBER",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.clear();
                    editor.apply();
                    startActivity(new Intent(view.getContext(),Login.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            });

        });
        SharedPreferences preferences=v.getContext().getSharedPreferences("NUMBER",MODE_PRIVATE);
        email.setText(preferences.getString("Register","default"));
        if (acc!=null) {
            Log.d("hello","hello");
            Log.d("datagetsss",acc.getPhotoUrl().toString());
           // Picasso.get().load(acc.getPhotoUrl()).centerCrop().into(image);
            Glide.with(v.getContext()).load(acc.getPhotoUrl()).fitCenter().circleCrop().into(image);
            name.setText(acc.getDisplayName());
            email.setText(acc.getEmail());
        }
        return v;
    }
}