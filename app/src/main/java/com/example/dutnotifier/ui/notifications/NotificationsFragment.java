package com.example.dutnotifier.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.dutnotifier.R;

public class NotificationsFragment extends Fragment {
    private EditText mEdtInput;
    private Button mBtnSearch;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        mEdtInput = (EditText) root.findViewById(R.id.edt_input);
        mBtnSearch = (Button) root.findViewById(R.id.btn_search);
        return root;
    }
}