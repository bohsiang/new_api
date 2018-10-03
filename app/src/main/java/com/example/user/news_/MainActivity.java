package com.example.user.news_;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MainActivity extends AppCompatActivity {
    String News_Url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        News_Url="https://newsapi.org/v1/articles?source=cnn&apiKey=abae59d632e34a4fa7b61e9f14c7f88f";


        new MainActivity.AsyncHttpTask().execute(News_Url);
    }
    public class AsyncHttpTask extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... urls) {
            String result="";
            URL url;
            HttpURLConnection urlConnection=null;

            try {
                url=new URL(urls[0]);
                urlConnection=(HttpURLConnection)url.openConnection();
                String response=streamToString(urlConnection.getInputStream());
                parseResult(response);
                return result;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    String streamToString(InputStream stream)throws IOException
    {
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(stream));
        String data;
        String result="";
        while ((data=bufferedReader.readLine())!=null)
        {
            result+=data;
        }
        if(null!=stream)
        {
            stream.close();
        }

        return result;
    }
    private void parseResult(String result)
    {
        JSONObject response;
        try {
            response = new JSONObject(result);
            JSONArray articles=response.optJSONArray("articles");

            for(int i=0;i<articles.length();i++)
            {
                JSONObject article=articles.optJSONObject(i);
                String title=article.optString("title");
                Log.i("Title",title);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
