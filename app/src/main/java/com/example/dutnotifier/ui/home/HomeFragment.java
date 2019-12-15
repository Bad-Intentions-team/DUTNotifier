package com.example.dutnotifier.ui.home;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dutnotifier.INTERNET.ConnectionReceiver;
import com.example.dutnotifier.MyDatabaseHelper;
import com.example.dutnotifier.NotiAdapter;
import com.example.dutnotifier.R;
import com.example.dutnotifier.model.ModelNoti;
import com.example.dutnotifier.Detail;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener {
    public static final String MY_URL = "http://sv.dut.udn.vn/G_Thongbao_LopHP.aspx";
    private RecyclerView recycler;
    private SearchView searchView;
    ArrayList<ModelNoti> listNoti = new ArrayList<>();
    private NotiAdapter notiAdapter;
    private MyDatabaseHelper db;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        db = new MyDatabaseHelper(getActivity());
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recycler = (RecyclerView) root.findViewById(R.id.recyler_category);
        searchView = (SearchView) root.findViewById(R.id.searchview);
 //       thongbao("đây là thông báo thử nghiệm");
        loadThongbao();
        searchView.setOnQueryTextListener(this);
        configRecyclerView();
        new DownloadTask().execute(MY_URL);
        return root;
    }
    private void configRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),1);
        recycler.hasFixedSize();
        recycler.setLayoutManager(layoutManager);
    }

    private void setAdapter(ArrayList<ModelNoti> arrContact) {
         notiAdapter = new NotiAdapter(getActivity(), listNoti);
        recycler.setAdapter(notiAdapter);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
         listNoti = db.getSearch(s.toLowerCase());
        setAdapter(listNoti);
        notiAdapter.notifyDataSetChanged();
        return true;
    }

    //Download HTML bằng AsynTask
    private class DownloadTask extends AsyncTask<String, Void, ArrayList<ModelNoti>> {

        private static final String TAG = "DownloadTask";

        @Override
        protected ArrayList<ModelNoti> doInBackground(String... strings) {
            Document document = null;

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
                            ModelNoti noti = new ModelNoti();
                            a = temp;
                            if(b==a) b = b.nextElementSibling();
                            noti.setTitle(a.text());

                            temp = element.getElementsByAttributeValue("class", "MsoNormal").get(i);
                            if(b==temp) {
                                noti.setContent(arr[j]);
                                j++;
                            }
                            String content = "";
                            while(b != temp) {
                                if(b.nextElementSibling() != temp) {
                                    content += b.text() + "\n";
                                }
                                else content += b.text();
                                b = b.nextElementSibling();
                                    noti.setContent(content);
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
        protected void onPostExecute(ArrayList<ModelNoti> listNoti) {
            super.onPostExecute(listNoti);
            //Setup data recyclerView
            notiAdapter = new NotiAdapter(getActivity(),listNoti);
            recycler.setAdapter(notiAdapter);
        }
    }

    private boolean checkInternet(){
        boolean check = ConnectionReceiver.isConnected();
        return check;
    }
    public void thongbao(String title, String content){
        Intent intent = new Intent(getActivity(), Detail.class);
        intent.putExtra("title",title);
        intent.putExtra("content",content);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(),
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel("MyNotifications", "MyNotifications", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getActivity().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder =new NotificationCompat.Builder(getActivity(),"MyNotifications")
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_school)
                .setContentText(content)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
          NotificationManagerCompat manager = NotificationManagerCompat.from(getActivity());
        manager.notify(0,builder.build());
    }
    private void loadThongbao(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("INFO", Context.MODE_PRIVATE);
        if(sharedPreferences!=null){
            String s = sharedPreferences.getString("CLASS","");
            ArrayList<ModelNoti> list = db.getSearch(s);
            for(ModelNoti modelNoti:list) {
                thongbao(modelNoti.getTitle(),modelNoti.getContent());
            }
        }
    }
}