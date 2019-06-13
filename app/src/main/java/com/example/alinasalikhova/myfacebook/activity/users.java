package com.example.alinasalikhova.myfacebook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alinasalikhova.myfacebook.R;
import com.example.alinasalikhova.myfacebook.pojo.User;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;

public class users extends AppCompatActivity {
    private RecyclerView usersRecyclerView;
    private UsersAdapter usersAdapter;
    private Toolbar toolbar;
    private EditText queryEditText;
    private Button searchButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_users);
        initRecyclerView();

        toolbar = findViewById(R.id.toolbar);
        queryEditText = toolbar.findViewById(R.id.query_edit_text);
        searchButton = toolbar.findViewById(R.id.search_button);

        searchUsers();
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchUsers();
            }
        });


        queryEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchUsers();
                    return true;
                }
                return false;
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    String randomString( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }

    private void searchUsers() {
        Collection<User> users = getUsers();
        usersAdapter.clearItems();
        usersAdapter.setItems(users);
    }

    private Collection<User> getUsers() {
        return Arrays.asList(
                new User(
                        9700608,
                        "http://i.imgur.com/DvpvklR.png",
                        "Picasso",
                        "@pic",
                        42
                ),

                new User(
                        44196397,
                        "https://pbs.twimg.com/profile_images/782474226020200448/zDo-gAo0_400x400.jpg",
                        "Elon Musk",
                        "@elonmusk",
                        42456
                ),
                new User(
                        4419639,
                        "https://api.adorable.io/avatars/171/"+ randomString(8)+"@"+randomString(8)+".png",
                        "Pikabu",
                        "@pikabu",
                        10
                ),
                new User(
                        44196397,
                        "https://api.adorable.io/avatars/171/"+ randomString(8)+"@"+randomString(8)+".png",
                        "Elon",
                        "@elon",
                        4
                ),
                new User(
                        44196397,
                        "https://api.adorable.io/avatars/171/"+ randomString(8)+"@"+randomString(8)+".png",
                        "Musk",
                        "@musk",
                        32
                )
        );
    }

    private void initRecyclerView() {
        usersRecyclerView = findViewById(R.id.users_recycler_view);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        UsersAdapter.OnUserClickListener onUserClickListener = new UsersAdapter.OnUserClickListener() {
            @Override
            public void onUserClick(User user) {
                Intent intent = new Intent(users.this, home.class);
                //intent.putExtra(UserInfoActivity.USER_ID, user.getId());
                startActivity(intent);
            }
        };
        usersAdapter = new UsersAdapter(onUserClickListener);
        usersRecyclerView.setAdapter(usersAdapter);
    }
}
