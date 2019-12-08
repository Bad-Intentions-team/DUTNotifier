package com.example.dutnotifier.ui.your_class;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.dutnotifier.R;

public class YourClassFragment extends Fragment {
    private EditText mEdtInput;
    private Button mBtnSave;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_your_class, container, false);
        mEdtInput = (EditText) root.findViewById(R.id.edt_input);
        mBtnSave = (Button) root.findViewById(R.id.btn_save);
        loadClass();
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("info", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("class",mEdtInput.getText().toString());
                editor.apply();
                Toast.makeText(getContext(),"Your class saved",Toast.LENGTH_LONG).show();
            }
        });
        return root;
    }
    private void loadClass(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("info", Context.MODE_PRIVATE);
        if(sharedPreferences!=null){
            String s = sharedPreferences.getString("class","17.Nh11");
            mEdtInput.setText(s);
        }
    }

}