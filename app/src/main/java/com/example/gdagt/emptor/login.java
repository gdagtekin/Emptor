package com.example.gdagt.emptor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;

public class login extends AppCompatActivity {
    LoginButton loginButton;
    TextView textView;
    CallbackManager callbackManager;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static String paket = "com.gdagt.emptor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        loginButton = (LoginButton) findViewById(R.id.fb_login_bn);
        textView = (TextView) findViewById(R.id.textView);
        textView.setVisibility(View.INVISIBLE);

        callbackManager = CallbackManager.Factory.create();
        sharedPreferences = getSharedPreferences(paket + ".maindata", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "user_birthday", "user_friends", "user_likes"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());
                                try {
                                    String name = object.getString("name");
                                    String gender = object.getString("gender");
                                    String birthday = object.getString("birthday");
                                    editor.putString(paket + ".name", name);
                                    editor.putString(paket + ".gender", gender);
                                    editor.putString(paket + ".birthday", birthday);
                                    editor.apply();
                                    URL imageURL = new URL("https://graph.facebook.com/"
                                            + object.getString("id") + "/picture?type=large");
                                    new login.downloadImage().execute(String.valueOf(imageURL));
                                } catch (Exception e) {
                                    Log.e("HATA", e.getMessage());
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,gender,birthday,picture");
                request.setParameters(parameters);
                request.executeAsync();
                goProfile();
            }

            @Override
            public void onCancel() {
                //textView.setText("Login Cancelled");
                Toast.makeText(login.this, "Login Cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                //textView.setText("Login Error");
                Toast.makeText(login.this, "Login Error", Toast.LENGTH_SHORT).show();
            }
        });
        if (AccessToken.getCurrentAccessToken() != null) {
            goProfile();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void goProfile() {
        Intent intent = new Intent(this, profile.class);
        /*intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);*/ //Activity stack olmaması için bunlar kullanılabilir
        startActivity(intent);
    }


    public class downloadImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap image = null;
            try {
                InputStream in = new URL(url).openStream();
                image = BitmapFactory.decodeStream(in);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();
                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                editor.putString(paket + ".photo", encodedImage);
                editor.commit();

            } catch (Exception e) {
                Log.e("HATA", e.getMessage());
            }
            return image;
        }
    }
}
