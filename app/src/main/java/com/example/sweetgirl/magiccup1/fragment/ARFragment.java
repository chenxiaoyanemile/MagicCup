package com.example.sweetgirl.magiccup1.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.sweetgirl.magiccup1.R;
import com.example.sweetgirl.magiccup1.activity.ShowARActivity;


public class ARFragment extends Fragment {

    private Button ar_btn_scan;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_ar, container, false);
        init(view);
        return view;
    }

    public void init(View view){
        ar_btn_scan=(Button)view.findViewById(R.id.ar_btn_scan);
        ar_btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "加载中...", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getActivity(), ShowARActivity.class);
                startActivity(intent);
            }
        });

    }

}