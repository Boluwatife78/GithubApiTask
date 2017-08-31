package com.example.aderelemaryidowu.recyclerviewyupdev;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    UserContact mUserContact;
    @BindView(R.id.refreshImageView) ImageView mrefreshImageview;
    @BindView(R.id.progressBar)ProgressBar mProgressBar;
    RecyclerView mRecyclerView;
    RecyclerViewAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<UserContact>  arrayList = new ArrayList<UserContact>();;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mProgressBar.setVisibility(View.INVISIBLE);
        mrefreshImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserContact();
            }
        });
        getUserContact();
        Log.d(TAG, "Main UI code is running!");
        setSupportActionBar(mToolbar);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerViewAdapter(arrayList, this);
        mRecyclerView.setAdapter(mAdapter);
    }



    private void getUserContact() {


        String gitHubUrl = "https://api.github.com/search/users?q=language:java+location:lagos";

        if(isNetworkAvailable()) {
            toggleRefresh();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(gitHubUrl).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });

                    try {
                        String jsonData = response.body().string();

                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            System.out.println(response);

                            mUserContact = getCurrentDetails(jsonData);

                        } else {
                            alertUserAboutError();
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "Exception Caught: ", e);
                    }
                }
                });

        }
        else {
            Toast.makeText(this, R.string.network_unavailable_text, Toast.LENGTH_LONG).show();
        }
    }

    private void toggleRefresh() {
        if(mProgressBar.getVisibility() == View.INVISIBLE)
        {
            mProgressBar.setVisibility(View.VISIBLE);
            mrefreshImageview.setVisibility(View.INVISIBLE);
        }
        else
        {
            mProgressBar.setVisibility(View.INVISIBLE);
            mrefreshImageview.setVisibility(View.VISIBLE);
        }
    }

    private boolean isNetworkAvailable()
    {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if(networkInfo != null && networkInfo.isConnected())
        {
            isAvailable = true;
        }
        return isAvailable;
    }

    private UserContact getCurrentDetails(String jsonData) throws JSONException{
        if (jsonData != null) {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray items = jsonObject.getJSONArray("items");
            try {

                for (int i = 0; i < items.length(); i++) {
                    JSONObject contact = items.getJSONObject(i);

                    String login = contact.getString("login");
                    long id = contact.getLong("id");
                    String avartar_url = contact.getString("avatar_url");
                    UserContact userContact = new UserContact(avartar_url, id, login);
                    arrayList.add(userContact);

                }
                
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Couldn't get json from server. Check LogCat for possible errors!",
                            Toast.LENGTH_LONG)
                            .show();
                }
            });

        }

        return null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        ArrayList<UserContact> mArrayList = new ArrayList<>();
        for(UserContact userContact : arrayList)
        {
            String name = userContact.getLogin();
            if(name.contains(newText))
            {
                mArrayList.add(userContact);
            }
        }
        mAdapter.searchFilter(mArrayList);
        return true;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog  = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_message");

    }
}
