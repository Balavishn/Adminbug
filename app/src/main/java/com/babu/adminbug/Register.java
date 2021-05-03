package com.babu.adminbug;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class Register extends AppCompatActivity {
    private static final int RC_SIGN_IN =100 ;
    EditText name,pass,rpass;
    FirebaseAuth auth;
    Button register;
   // TextView login;

// ...
// Initialize Firebase Auth




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=findViewById(R.id.Rusername);
        pass=findViewById(R.id.Rpass);
        rpass=findViewById(R.id.Rpass2);
        register=findViewById(R.id.register);
        auth=FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(view);
            }
        });


    }

    public boolean validate(){
        if (!name.getText().toString().equals("")){
            name.setError("Enter Name");
            return false;
        }
        else if(!pass.getText().toString().equals("")){
            pass.setError("Enter Password");
            return false;
        }
        else if(!rpass.getText().toString().equals("")){
           rpass.setError("Enter Confirm Password");
           return false;
        }
        else if(pass.getText().toString().equals(rpass.getText().toString())){
            rpass.setError("Password not match");
            return false;
        }
        return true;
    }

    public void register(View v){
        String e=name.getText().toString();
        String p=pass.getText().toString();
        if(e.isEmpty()){
            name.setError("Email is required");
            name.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(e).matches()){
            name.setError("please enter valid email");
            name.requestFocus();
            return;
        }
        if(p.isEmpty()){
            pass.setError("Password is required");
            pass.requestFocus();
            return;
        }
        if(p.length()<6){
            pass.setError("Minimum length is greater than 6 character");
            pass.requestFocus();
            return;
        }
        auth.createUserWithEmailAndPassword(e,p).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"user created successful",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getApplicationContext(),Bottom_navi.class);
                    SharedPreferences preferences = getSharedPreferences("NUMBER", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("Register", e);
                    editor.putString("Password", p);
                    //editor.putString("StaffID", auth.getCurrentUser().getUid());

                    editor.commit();
                    startActivity(i);
                }
                else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(),"you are already registered",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


}