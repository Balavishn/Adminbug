package com.babu.adminbug;

import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class View_Adapter extends RecyclerView.Adapter<View_Adapter.myview> {
    List<UserBug> user;

    public View_Adapter(List<UserBug> user) {
        this.user = user;
    }

    @NonNull
    @Override
    public View_Adapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_app_name2,parent,false);
        myview myview=new myview(v);
        return myview;
    }

    @Override
    public void onBindViewHolder(@NonNull View_Adapter.myview holder, int position) {
        holder.app.setText(user.get(position).Name);
        holder.bug.setText(user.get(position).Bug_name);
        holder.l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               holder.v1.getContext().startActivity(new Intent(holder.v1.getContext(),view_status.class).putExtra("app", (Parcelable) user.get(position)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return user.size();
    }

    public class myview extends RecyclerView.ViewHolder{
       TextView app,bug;
       LinearLayout l1;
       View v1;

        public myview(@NonNull View itemView) {
            super(itemView);
            v1=itemView;
            app=itemView.findViewById(R.id.app_name);
            bug=itemView.findViewById(R.id.bug_type);
            l1=itemView.findViewById(R.id.singleapp2);
            l1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

    }
}
