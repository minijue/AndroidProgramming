package com.wangjue.example.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment2 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment2, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        Button btnGetText = getActivity().findViewById(R.id.btnGetText);
        btnGetText.setOnClickListener((v) -> {
            TextView lbl = getActivity().findViewById(R.id.lblFragment1);
            Toast.makeText(getActivity(), lbl.getText(), Toast.LENGTH_SHORT).show();
        });
    }

}
