package com.example.wangjue.basicview5;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
    String[] presidents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView lstView = getListView();
        lstView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lstView.setTextFilterEnabled(true);
        presidents = getResources().getStringArray(R.array.presidents_array);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, presidents));
    }

    public void onListItemClick(ListView parent, View v, int position, long id) {
        Toast.makeText(this, "You have selected " + presidents[position], Toast.LENGTH_SHORT).show();
    }
}
