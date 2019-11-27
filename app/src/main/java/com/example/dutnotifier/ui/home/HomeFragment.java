package com.example.dutnotifier.ui.home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dutnotifier.INTERNET.ConnectionReceiver;
import com.example.dutnotifier.MyDatabaseHelper;
import com.example.dutnotifier.NotiAdapter;
import com.example.dutnotifier.R;
import com.example.dutnotifier.model.modelNoti;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class HomeFragment extends Fragment {
    public static final String MY_URL = "http://sv.dut.udn.vn/G_Thongbao_LopHP.aspx";
    private RecyclerView recycler;
    private NotiAdapter notiAdapter;
    private MyDatabaseHelper db;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        db = new MyDatabaseHelper(getActivity());
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recycler = (RecyclerView) root.findViewById(R.id.recyler_category);

        configRecyclerView();
        new DownloadTask().execute(MY_URL);
        return root;
    }
    private void configRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),1);
        recycler.hasFixedSize();
        recycler.setLayoutManager(layoutManager);
    }
    //Download HTML bằng AsynTask
    private class DownloadTask extends AsyncTask<String, Void, ArrayList<modelNoti>> {

        private static final String TAG = "DownloadTask";

        @Override
        protected ArrayList<modelNoti> doInBackground(String... strings) {
            Document document = null;
            ArrayList<modelNoti> listNoti = new ArrayList<>();
            if( checkInternet() == true) {
                db.deleteTable();
                try {
                    document = (Document) Jsoup.connect(strings[0]).get();
                    if (document != null) {
                        Elements sub = document.select("body");
                        Element element = sub.first();
                        String str = element.ownText();
                        String [] arr = str.split("Thầy");
                        for(int i=1;i<arr.length;i++) {
                            arr[i] = "Thầy"+ arr[i] ;
                        }
                        int j=1;
                        Element temp = element.getElementsByClass("MsoNormal").first();
                        Element a = null;
                        Element b = element.getElementsByTag("p").first();
                        for(int i=1;i<25;i++)
                        {
                            modelNoti noti = new modelNoti();
                            a = temp;
                            if(b==a) b = b.nextElementSibling();
                            noti.setTitle(a.text());

                            temp = element.getElementsByAttributeValue("class", "MsoNormal").get(i);
                            if(b==temp) {
                                noti.setContent(arr[j]);
                                j++;
                            }
                            while(b != temp) {
                                noti.setContent(b.text());
                                b= b.nextElementSibling();
                            }
                            listNoti.add(noti);
                            db.addNoti(noti);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                listNoti = db.getAllNotis();
            }
                return listNoti;
        }

        @Override
        protected void onPostExecute(ArrayList<modelNoti> notis) {
            super.onPostExecute(notis);
            //Setup data recyclerView
            notiAdapter = new NotiAdapter(getActivity(),notis);
            recycler.setAdapter(notiAdapter);
        }
    }

    private boolean checkInternet(){
        boolean check = ConnectionReceiver.isConnected();
        return check;
    }
}