package com.codeniro.android.gittracker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class StatsActivity extends AppCompatActivity {

    private int page= 1;
    ListView listView;
    List mList;
    User user;
    GitAdapter adapter;
    AsyncHttpClient client;
    Gson gson;
    String loca;
    String lang;

    AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
         avi=new AVLoadingIndicatorView(StatsActivity.this);

        Intent intent = getIntent();
        loca=intent.getStringExtra("loca");
        lang=intent.getStringExtra("lang");

        String url="https://api.github.com/search/users?q=location:" + loca + "+language:"+ lang+ "&page=1";
            dataInit(url);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int currentVisibleItemCount;
            private int currentScrollState;
            private int currentFirstVisibleItem;
            private int totalItem;
            private LinearLayout lBelow;

            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                this.currentScrollState = i;
                this.isScrollCompleted();

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                this.currentFirstVisibleItem = i;
                this.currentVisibleItemCount = i1;
                this.totalItem = i2;

            }

            private void isScrollCompleted() {
                if (totalItem - currentFirstVisibleItem == currentVisibleItemCount
                        && this.currentScrollState == SCROLL_STATE_IDLE) {
                    if(mList.size() == user.getTotal_count()){
                        Toast.makeText(getApplicationContext(), "No more users..",Toast.LENGTH_SHORT).show();
                        loadMoreData();
                    }else{
                        Toast.makeText(getApplicationContext(), "Loading more..",Toast.LENGTH_SHORT).show();
                        loadMoreData();
                    }

                }
            }

        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(StatsActivity.this, DisplayActivity.class);

                // getting values from selected ListItem
                String title = ((TextView) view.findViewById(R.id.title)).getText().toString();
                ImageView img = (ImageView) view.findViewById(R.id.thumbnail);

                img.buildDrawingCache();
                Bitmap bitmap = img.getDrawingCache();
                intent.putExtra("BitmapImage", bitmap);
                intent.putExtra("title", title);
                intent.putExtra("drawable", R.drawable.class);
                startActivity(intent);
            }
        });
    }

    public void dataInit(String urlParam){
        startAnim();

        listView = (ListView) findViewById(R.id.gitlist);
        client = new AsyncHttpClient();
        client.setUserAgent(System.getProperty("http.agent"));
        client.get(StatsActivity.this, urlParam, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {


                String responseString = response.toString();
                gson = new Gson();
                user= gson.fromJson(responseString, User.class);
                mList=user.getItems();
                adapter = new GitAdapter(StatsActivity.this,user.getItems() );
                listView.setAdapter(adapter);
                stopAnim();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String e, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error connecting..Try again later",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(StatsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void loadMoreData(){
        page+=1;
        String url="https://api.github.com/search/users?q=location:" + loca +"+language:"+lang+ "&page="+ Integer.toString(page);
        client = new AsyncHttpClient();
        client.setUserAgent(System.getProperty("http.agent"));
        client.get(StatsActivity.this, url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                String responseString = response.toString();
                gson = new Gson();
                user= gson.fromJson(responseString, User.class);
                List results = user.getItems();
                mList.addAll(user.getItems());

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String e, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error connecting..Try again later",Toast.LENGTH_SHORT).show();
            }
        });
    }

    void startAnim(){
        findViewById(R.id.avi).setVisibility(View.VISIBLE);
    }

    void stopAnim(){
        findViewById(R.id.avi).setVisibility(View.GONE);
    }

}
