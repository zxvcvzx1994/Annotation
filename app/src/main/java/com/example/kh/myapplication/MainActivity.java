package com.example.kh.myapplication;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@EActivity (R.layout.activity_main)

public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.txt)
    TextView txt;
    @ViewById(R.id.txtProgress)
    TextView txtProgress;
    @ViewById(R.id.progress)
    ProgressBar progressBar;

    public static MainActivity getIntance(){
        MainActivity mainActivity =null;
        if(mainActivity==null)
            mainActivity = new MainActivity();
        return mainActivity;
    }
    private String[] images;
    private HttpURLConnection con;
    private InputStream inputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        images  = getResources().getStringArray(R.array.image);
    }
    public void process(View v)   {
        progressBar.setVisibility(v.VISIBLE);
        txtProgress.setVisibility(v.VISIBLE);
            new MyDownLoading().execute("https://www.youtube.com/embed/TJNduj22Lw8");
        progressBar.setVisibility(v.GONE);
        txtProgress.setVisibility(v.GONE);

    }
    private String TAG = "VO CONG VINH";


    class MyDownLoading extends AsyncTask<String,Integer,String>{
        @Override
        protected String doInBackground(String... params) {
            URL downLoadURL;
            try {
                downLoadURL  = new URL(params[0]);
                con = (HttpURLConnection) downLoadURL.openConnection();
                con.connect();
                inputStream = con.getInputStream();
                int read =  -1;
                Uri uri = Uri.parse(images[0]);
                Uri uri1 = Uri.parse(params[0]);

                byte[] bytes  =new byte[1024];
               File file= new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath()+"/mp2.mp4");

                FileOutputStream fileOutputStream  =new FileOutputStream(file);
                Log.d(TAG, ""+uri.getLastPathSegment());
                Log.d(TAG, "213"+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath());
                while((read= inputStream.read(bytes))!=-1){
                    Log.d(TAG, "DownloadImage: "+read);
                    fileOutputStream.write(bytes, 0,read);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(inputStream!=null)
                    try {
                        inputStream.close();
                    } catch (IOException e) {

                    }
                if(con!=null)
                    con.disconnect();
            }
            return "";
        }
        LayoutInflater inflater  = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_main, null);
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

    }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
    }
}


