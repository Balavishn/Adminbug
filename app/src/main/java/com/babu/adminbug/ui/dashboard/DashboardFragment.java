package com.babu.adminbug.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.babu.adminbug.R;
import com.babu.adminbug.UserBug;
import com.babu.adminbug.View_Adapter;
import com.babu.adminbug.loading;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    List<String> applist;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        FirebaseAuth auth=FirebaseAuth.getInstance();
        String id=auth.getUid();
        List<UserBug> list=new ArrayList<UserBug>();
        applist=new ArrayList<String>();


        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        RecyclerView recyclerView=root.findViewById(R.id.view_details);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        TextView no=root.findViewById(R.id.nodata);

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Bug_Report");
        loading lode=new loading(getActivity());
        lode.startdio();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {

                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        for (DataSnapshot snapshot2:snapshot1.getChildren()){
                            if (snapshot2.getKey().toString().equals(id)){
                                for (DataSnapshot snapshot3:snapshot2.getChildren()){
                                    //Log.d("datasget",snapshot3.getValue().toString());
                                    UserBug userBug=snapshot3.getValue(UserBug.class);
                                    list.add(userBug);
                                    //Log.d("classdatasget",userBug.email);
                                }
                            }
                        }
                    }
                    if (list.isEmpty()){
                        recyclerView.setVisibility(View.GONE);
                        no.setVisibility(View.VISIBLE);
                    }
                    View_Adapter view_adapter=new View_Adapter(list);
                    recyclerView.setAdapter(view_adapter);
                    lode.dismiss();

                }
                catch (Exception e){

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //final TextView textView = root.findViewById(R.id.text_dashboard);

        return root;
    }

    public boolean check(String appname){
            if (applist.contains(appname)){
                return true;
            }
            else{
                return false;
            }
    }
}