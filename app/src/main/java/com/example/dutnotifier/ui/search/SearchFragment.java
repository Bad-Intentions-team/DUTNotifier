package com.example.dutnotifier.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.dutnotifier.R;

public class SearchFragment extends Fragment {
    private EditText mEdtInput;
    private Button mBtnSearch;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        mEdtInput = (EditText) root.findViewById(R.id.edt_input);
        mBtnSearch = (Button) root.findViewById(R.id.btn_search);
        return root;
    }
}