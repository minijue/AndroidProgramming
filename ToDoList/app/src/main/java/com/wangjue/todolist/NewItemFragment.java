package com.wangjue.todolist;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewItemFragment extends Fragment {
    private OnNewItemAddedListener onNewItemAddedListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onNewItemAddedListener = (OnNewItemAddedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnNewItemAddedListener.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_item, container, false);
        final EditText myEditText = view.findViewById(R.id.myEditText);

        myEditText.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    String newItem = myEditText.getText().toString();
                    onNewItemAddedListener.onNewItemAdded(newItem);
                    myEditText.setText("");
                    return true;
                }
            }
            return false;
        });
        return view;
    }

}
