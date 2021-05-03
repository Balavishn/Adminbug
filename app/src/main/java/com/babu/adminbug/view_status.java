package com.babu.adminbug;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class view_status extends AppCompatActivity {
    TextView bug_name,bug_detail,bug_status;
    LinearLayout bug_proof;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_status);
        UserBug user=getIntent().getParcelableExtra("app");
        bug_name=findViewById(R.id.bug_type_status);
        bug_detail=findViewById(R.id.bug_detail_status);
        bug_status=findViewById(R.id.bug_status);
        bug_proof=findViewById(R.id.bug_proof_open);
        bug_name.setText(user.Bug_name);
        bug_detail.setText(user.Bug_detail);

        bug_proof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.type.equals("image")){
                    Intent intent=new Intent();
                    intent.setAction(android.content.Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(user.url), "image/*");
                    startActivity(intent);
                }
                else{
                    Intent intent=new Intent();
                    intent.setAction(android.content.Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(user.url), "video/*");
                    startActivity(intent);
                }
            }
        });

    }
}