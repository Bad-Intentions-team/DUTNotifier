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
    private EditText mEdtInputGrade, mEdtInputGroup;
    private Button mBtnSave;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_your_class, container, false);
        mEdtInputGrade = (EditText) root.findViewById(R.id.edt_input_grade);
        mEdtInputGroup = (EditText) root.findViewById(R.id.edt_input_group);
        mBtnSave = (Button) root.findViewById(R.id.btn_save);
        loadYourClass();
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("INFO", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String c = mEdtInputGrade.getText().toString() + ".Nh"+ mEdtInputGroup.getText().toString();
                editor.putString("GRADE",mEdtInputGrade.getText().toString());
                editor.putString("GROUP",mEdtInputGroup.getText().toString());
                editor.putString("CLASS",c);
                editor.apply();
                Toast.makeText(getContext(),"Lớp của bạn "+ c + " đã được lưu thành công",Toast.LENGTH_LONG).show();
            }
        });
        return root;
    }
    private void loadYourClass(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("INFO", Context.MODE_PRIVATE);
        if(sharedPreferences!=null){
            String grade = sharedPreferences.getString("GRADE","");
            String group = sharedPreferences.getString("GROUP","");
            mEdtInputGrade.setText(grade);
            mEdtInputGroup.setText(group);
        }
    }

}