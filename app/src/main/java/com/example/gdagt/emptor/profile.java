package com.example.gdagt.emptor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;


public class profile extends AppCompatActivity {
    TextView tvAdSoyad, tvDogumTarihi, tvCinsiyet;
    ImageView imageView;
    public static String paket = "com.gdagt.emptor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ActionBar actionBar = getSupportActionBar();

        setTitle(R.string.profilPage);
        tvAdSoyad = (TextView) findViewById(R.id.tvAdSoyad);
        tvDogumTarihi = (TextView) findViewById(R.id.tvDogumTarihi);
        tvCinsiyet = (TextView) findViewById(R.id.tvCinsiyet);
        imageView = (ImageView) findViewById(R.id.imageView);
        getData();
    }

    public void myfriends(View view) {
        Intent intent = new Intent(this, myfriendsActivity.class);
        startActivity(intent);
    }

    public void mylikes(View view) {
        Intent intent = new Intent(this, mylikesActivity.class);
        startActivity(intent);
    }

    public void getData() {
        //Bundle b = getIntent().getExtras();
        //String name = getIntent().getStringExtra("names");
        //Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        tvAdSoyad.setText(getSharedPreferences(paket + ".maindata", MODE_PRIVATE).getString(paket + ".name", "null"));
        String cinsiyet = (getSharedPreferences(paket + ".maindata", MODE_PRIVATE).getString(paket + ".gender", "null"));
        if (cinsiyet.equalsIgnoreCase("male")) {
            tvCinsiyet.setText(getResources().getString(R.string.male));
        } else if (cinsiyet.equalsIgnoreCase("female")) {
            tvCinsiyet.setText(getResources().getString(R.string.female));
        } else {
            tvCinsiyet.setText("Null");
        }
        tvDogumTarihi.setText(getSharedPreferences(paket + ".maindata", MODE_PRIVATE).getString(paket + ".birthday", "null"));
        getPicture();
    }

    public void getPicture() {
        String preEncodedImage = getSharedPreferences(paket + ".maindata", MODE_PRIVATE).getString(paket + ".photo", "");
        if (!preEncodedImage.equalsIgnoreCase("")) {
            byte[] b = Base64.decode(preEncodedImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(R.drawable.com_facebook_button_login_background);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_exa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(profile.this, login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//
        startActivity(intent);
        finish();
        System.exit(0);
    }
}
