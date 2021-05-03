package com.babu.adminbug;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Bug_report extends AppCompatActivity {
    ImageButton get;
    ImageView check,cancel;
    TextView upload,uploadproof;
    final Uri[] selecturl = new Uri[1];
    Uri select;
    String type;

    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    String app_name;
    EditText bug_name;
    EditText bug_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bug_report);
        app_name=getIntent().getStringExtra("app_name");
        uploadproof=findViewById(R.id.uploadproof);
        bug_name=findViewById(R.id.bug1);
        bug_detail=findViewById(R.id.bug2);
        get=findViewById(R.id.getimage);
        check=findViewById(R.id.click);
        cancel=findViewById(R.id.click2);
        upload=findViewById(R.id.uploadd);
        FirebaseAuth auth=FirebaseAuth.getInstance();
        String email=auth.getCurrentUser().getEmail().toString();
        String uid=auth.getUid();
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkval()){
                long time = System.currentTimeMillis();
                final ProgressDialog progressDialog = new ProgressDialog(Bug_report.this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();
                if (select.toString().contains("image")) {
                    Log.d("dataget", "image");
                    StorageReference mountainImagesRef = storageRef.child("images/");
                    mountainImagesRef.child(uid).child(time + ".jpg").putFile(select).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String downloadurl = taskSnapshot.getStorage().getDownloadUrl().toString();
                            Log.d("selectdownloadurl", downloadurl);
                            mountainImagesRef.child(uid).child(time + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    selecturl[0] = uri;
                                    Log.d("downloadurl", selecturl[0].toString());
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Bug_Report").child(app_name).child(uid);
                                    UserBug userBug = new UserBug(app_name,email, bug_name.getText().toString(), bug_detail.getText().toString(), selecturl[0].toString(), "image");
                                    ref.push().setValue(userBug).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Snackbar snackbar = Snackbar.make(view, "Upload Success fully", Snackbar.LENGTH_LONG);
                                            snackbar.show();
                                            Toast.makeText(getApplicationContext(), "File uploaded successfully", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    });
                                }
                            });
                            //String download=mountainImagesRef.getDownloadUrl();

                        }
                    });
                } else if (select.toString().contains("video")) {
                    Log.d("dataget", "video");
                    Log.d("dataget", "image");
                    StorageReference mountainImagesRef = storageRef.child("video/");
                    mountainImagesRef.child(uid).child(time + ".mp4").putFile(select).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mountainImagesRef.child(uid).child(time + ".mp4").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    selecturl[0] = uri;
                                    Log.d("downloadurl", selecturl[0].toString());
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Bug_Report").child(app_name).child(uid);
                                    UserBug userBug = new UserBug(app_name,email, bug_name.getText().toString(), bug_detail.getText().toString(), selecturl[0].toString(), "video");
                                    ref.push().setValue(userBug).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Notication_send notication_send=new Notication_send();
                                            notication_send.sendNotifiy("New Bug","New Bug Report Added");
                                            Snackbar snackbar = Snackbar.make(view, "Upload Success fully", Snackbar.LENGTH_LONG);
                                            snackbar.show();
                                            //Toast.makeText(getApplicationContext(), "File uploaded successfully", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    });
                                }
                            });
                            //String download=mountainImagesRef.getDownloadUrl();
                            //Log.d("downloadurl",download.toString());
                        }
                    });
                    get.setVisibility(View.GONE);
                    check.setVisibility(View.VISIBLE);
                    cancel.setVisibility(View.VISIBLE);
                }
            }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get.setVisibility(View.VISIBLE);
                check.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
             //   StorageReference mountainsRef = storageRef.child("mountains.jpg");

// Create a reference to 'images/mountains.jpg'


// While the file names are the same, the references point to different files
            //    mountainsRef.getName().equals(mountainImagesRef.getName());    // true
            //    mountainsRef.getPath().equals(mountainImagesRef.getPath());

            }
        });
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();
            }
        });
    }

    private boolean checkval() {
        if (bug_name.getText().toString().equals("")){
            bug_name.setError("Enter Bug Name");
           return false;
        }
        if (bug_detail.getText().toString().equals("")){
            bug_detail.setError("Enter bug Details");
            return false;
        }
        if (select==null){
            uploadproof.setTextColor(Color.RED);
            return false;
        }
        return true;
    }


    private void uploadFile() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        intent.setType("image/* video/*");

        /*intent.setAction(Intent.ACTION_GET_CONTENT);*/

        startActivityForResult(intent,1);

    }


    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode==1) {
            uploadproof.setTextColor(Color.RED);
             select= data.getData();

                get.setVisibility(View.GONE);
                check.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);
            }
        else{

            get.setVisibility(View.VISIBLE);
            check.setVisibility(View.GONE);
            cancel.setVisibility(View.GONE);
        }
    }
}