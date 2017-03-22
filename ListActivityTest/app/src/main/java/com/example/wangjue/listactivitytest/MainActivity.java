package com.example.wangjue.listactivitytest;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

/**
 * Created by wangjue on 17-3-22.
 */

public class MainActivity extends ListActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] arr = {"Zhang3", "Li4", "Wang2"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, arr);

        setListAdapter(adapter);
    }
}
