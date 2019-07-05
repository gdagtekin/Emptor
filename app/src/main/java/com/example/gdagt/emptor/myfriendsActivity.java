package com.example.gdagt.emptor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.example.gdagt.emptor.adapter.DerpAdapter;
import com.example.gdagt.emptor.model.DerpData;
import com.example.gdagt.emptor.model.ListItem;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class myfriendsActivity extends AppCompatActivity {
    ImageView imageView;
    private RecyclerView recView;
    private DerpAdapter adapter;
    private ArrayList listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfriends);
        setTitle(R.string.pageFriends);
        listData = (ArrayList) DerpData.getListData();
        recView = (RecyclerView) findViewById(R.id.rec_list);
        imageView = (ImageView) findViewById(R.id.imageView2);
        recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DerpAdapter(listData, this);
        recView.setAdapter(adapter);


        GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/taggable_friends",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        Log.v("Dikkat", response.toString());

                        try {

                            JSONArray jsonArray = response.getJSONObject().getJSONArray("data");
                            for (int i = 0; i < 20; i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
                                String isimler = object.getString("name");
                                String imageUrl = object.getString("picture");
                                String id = object.getString("id");
                                final ListItem item = new ListItem();
                                item.setTitle(isimler);
                                int bas = imageUrl.indexOf("https");
                                int son = imageUrl.lastIndexOf("\"");
                                imageUrl = imageUrl.substring(bas, son);
                                imageUrl = imageUrl.replace("\\", "");
                                URL URLImage = new URL(imageUrl);
                                String url = new String("http://rs86.pbsrc.com/albums/k82/seanp_music/icons%2050px/sidebar_soundcloud-1.png~c200");
                                //Bitmap bitmap=new downloadImage().doInBackground(imageUrl);
                                //Bitmap bitmap = BitmapFactory.decodeStream(URLImage.openConnection().getInputStream());
                               /* Bitmap bitmap;
                                InputStream in=new URL(imageUrl).openStream();
                                bitmap= BitmapFactory.decodeStream(in);*/
                                //  item.setBitmap(bitmap);
                                // foo(imageUrl);
                                foo(imageUrl);

                                listData.add(item);
                                adapter.notifyItemInserted(listData.indexOf(item));
                                Log.w("Dikkat", isimler);
                                Log.v("Dikkat", imageUrl);
                                Log.v("Dikkat", id);
                                // Picasso.with(getApplicationContext()).load(imageUrl).into(imageView);

                            }
                            /// String ad="https://graph.facebook.com/"+id+"/picture?type=small";

                            //InputStream in=new URL(url).openStream();
                           /* Bitmap bitmap= BitmapFactory.decodeStream((InputStream) new URL("http://rs86.pbsrc.com/albums/k82/seanp_music/icons%2050px/sidebar_soundcloud-1.png~c200").getContent());
                            imageView.setImageBitmap(bitmap);*/

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
        request.executeAsync();

    }

    Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

            imageView.setImageBitmap(bitmap);
            Log.v("Dikkat", "Bitti");
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    void foo(String url) {
        Picasso.with(getApplicationContext()).load(url).into(target);
    }

    public class downloadImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap image = null;
            try {
                InputStream in = new URL(url).openStream();
                image = BitmapFactory.decodeStream(in);


            } catch (Exception e) {
                Log.w("HATA", "fotoğraf hata");
            }
            return image;
        }
    }


}
