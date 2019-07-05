package com.example.gdagt.emptor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.gdagt.emptor.adapter.DerpAdapter2;
import com.example.gdagt.emptor.model.DerpData2;
import com.example.gdagt.emptor.model.ListItem2;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class mylikesActivity extends AppCompatActivity {
    private RecyclerView recView;
    private DerpAdapter2 adapter;
    private ArrayList listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylikes);
        setTitle(R.string.pageLikes);
        listData = (ArrayList) DerpData2.getListData();
        recView = (RecyclerView) findViewById(R.id.rec_list2);
        recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DerpAdapter2(listData, this);
        recView.setAdapter(adapter);
        GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/likes",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        try {
                            JSONArray jsonArray = response.getJSONObject().getJSONArray("data");
                            for (int i = 0; i < 20; i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String names = object.getString("name");
                                ListItem2 item = new ListItem2();
                                item.setTitle(names);
                                listData.add(item);
                                adapter.notifyItemInserted(listData.indexOf(item));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        request.executeAsync();

    }
}
