package com.example.aderelemaryidowu.recyclerviewyupdev;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

class FilmDetails extends AppCompatActivity {
    ImageView UserImageView;
    TextView UserLoginTextView;
    TextView UserIdTextView;
    public UserContact mUserContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details_layout);
        UserImageView = (ImageView)findViewById(R.id.user_image_details);
        UserLoginTextView = (TextView)findViewById(R.id.user_login_details);
        UserIdTextView = (TextView)findViewById(R.id.user_id_details);
        UserImageView.setImageResource(getIntent().getIntExtra("image", 0));
        UserLoginTextView.setText("Username: " + getIntent().getStringExtra("Username"));
        UserIdTextView.setText("Id: " + getIntent().getStringExtra("Id"));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
