package com.example.alinasalikhova.myfacebook.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alinasalikhova.myfacebook.MainActivity;
import com.example.alinasalikhova.myfacebook.R;
import com.example.alinasalikhova.myfacebook.adapter.TweetAdapter;
import com.example.alinasalikhova.myfacebook.pojo.Tweet;
import com.example.alinasalikhova.myfacebook.pojo.User;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class home extends AppCompatActivity {
    private ImageView userImageView;
    private TextView nameTextView;
    private TextView nickTextView;
    private TextView friendsCountTextView;
    private RecyclerView tweetsRecyclerView;
    private TweetAdapter tweetAdapter;
    private Button searchButton;
    public static final String USER_ID = "userId";
    private Toolbar toolbar;
    private User currentUser;
    public final String TAG = this.getClass().getSimpleName();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_search) {
            Intent intent = new Intent(home.this, users.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_info);

        userImageView = findViewById(R.id.user_image_view);
        nameTextView = findViewById(R.id.user_name_text_view);
        nickTextView = findViewById(R.id.user_nick_text_view);
        friendsCountTextView = findViewById(R.id.friends_count_text_view);

        loadUserInfo();
        initRecyclerView();
        toolbar = findViewById(R.id.toolbar);

        this.searchButton = toolbar.findViewById(R.id.search_button);
        handleclick();
        setSupportActionBar(toolbar);

    }

    private void handleclick() {


    }

    private void loadTweets() {
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/feed?fields=attachments,created_time,message",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        try {
                            JSONObject obj = new JSONObject(response.getRawResponse());
                            JSONArray arr = obj.getJSONArray("data");
                            if (arr != null) {
                                int len = arr.length();
                                ArrayList<Tweet> tweets = new ArrayList<Tweet>();
                                for (int i = 0; i < len; i++) {
                                    String msg = "";
                                    String image = "https://picsum.photos/800/600";
                                    try{
                                        msg = arr.getJSONObject(i).getString("message");
                                    }
                                    catch (JSONException e){
                                        Log.d(TAG, e.getMessage());
                                    }
                                    try{
                                        image = arr
                                                .getJSONObject(i)
                                                .getJSONObject("attachments")
                                                .getJSONArray("data")
                                                .getJSONObject(0)
                                                .getJSONObject("media")
                                                .getJSONObject("image")
                                                .getString("src");
                                    }
                                    catch (JSONException e){
                                        Log.d(TAG, e.getMessage());
                                    }
                                    tweets.add(
                                            new Tweet(
                                                    home.this.currentUser,
                                                    arr.getJSONObject(i).getString("id"),
                                                    arr.getJSONObject(i).getString("created_time"),
                                                    msg,
                                                    image
                                            )
                                    );
                                }
                                tweetAdapter.setItems(tweets);

                            }
                        }
                        catch (JSONException e){
                            Log.d(TAG, e.getMessage());
                        }
                    }

                }
        ).executeAsync();
    }


    private void initRecyclerView() {
        tweetsRecyclerView = findViewById(R.id.tweets_recycler_view);
        tweetsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tweetAdapter = new TweetAdapter();
        tweetsRecyclerView.setAdapter(tweetAdapter);
    }
    private void loadUserInfo() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        User user = getData(object);
                        home.this.currentUser = user;
                        displayUserInfo(user);
                        loadTweets();
                    }
                });

        //Request Graph API
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, email, name, short_name, location, birthday, friends");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void displayUserInfo(User user) {

        Picasso.with(this).load(user.getImageUrl()).into(userImageView);
        nameTextView.setText(user.getName());
        nickTextView.setText(user.getNick());
        String followersCount = String.valueOf(user.getFriendsCount());
        friendsCountTextView.setText(followersCount);

    }
    private User getData(JSONObject object) {
        try {
            return new User(
                    object.getInt("id"),
                    "https://graph.facebook.com/"+ object.getString("id")+"/picture?width=250&height=250",
                    object.getString("name"),
                    object.getString("short_name"),
                    Integer.parseInt(object.getJSONObject("friends")
                            .getJSONObject("summary")
                            .getString("total_count"))
            );
        } catch (JSONException e) {
            e.printStackTrace();
            return new User(
                    1,
                    "http://i.imgur.com/DvpvklR.png",
                    "UserName",
                    "usernick",
                    22
            );
        }
    }
}