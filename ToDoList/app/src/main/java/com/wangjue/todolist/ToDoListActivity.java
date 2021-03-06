package com.wangjue.todolist;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class ToDoListActivity extends Activity implements OnNewItemAddedListener {

    private ArrayList<String> todoItems;
    private ArrayAdapter<String> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        FragmentManager fm = getFragmentManager();
        ToDoListFragment toDoListFragment = (ToDoListFragment) fm.findFragmentById(R.id.TodoListFragment);

        todoItems = new ArrayList<String>();
        aa = new ArrayAdapter<String>(this, R.layout.todolist_item, todoItems);

        toDoListFragment.setListAdapter(aa);
    }

    @Override
    public void onNewItemAdded(String newItem) {
        todoItems.add(newItem);
        aa.notifyDataSetChanged();
    }
}
