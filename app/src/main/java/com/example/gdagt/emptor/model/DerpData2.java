package com.example.gdagt.emptor.model;

/**
 * Created by gdagt on 6/22/2017.
 */

import java.util.ArrayList;
import java.util.List;

public class DerpData2 {


    public static List<ListItem2> getListData() {
        List<ListItem2> data = new ArrayList<>();
        /*GraphRequest request2 = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/taggable_friends",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        Log.v("Dikkat",response.toString());

                        try {
                            JSONArray jsonArray=response.getJSONObject().getJSONArray("data");
                            for (int i=0; i<10; i++){

                                JSONObject object=jsonArray.getJSONObject(i);
                                String isimler=object.getString("name");
                                ListItem item = new ListItem();
                                item.setTitle("a"+String.valueOf(i));
                                item.setImageResId(icons[0]);
                                data.add(item);
                                Log.w("Dikkat",isimler);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        request2.executeAsync();*/

        /*for (int i = 0; i  < 10; i++) {
            ListItem item = new ListItem();
            item.setImageResId(icons[0]);
            item.setTitle(arrays.get(i));
            data.add(item);
        }*/

        /*
        //Repeat process 4 times, so that we have enough data to demonstrate a scrollable
        //RecyclerView
        for (int x = 0; x  < 4; x++) {
            //create ListItem with dummy data, then add them to our List
            for (int i = 0; i  < titles.length && i  < icons.length; i++) {
                ListItem item = new ListItem();
                item.setImageResId(icons[i]);
                item.setTitle(titles[i]);
                data.add(item);
            }
        }*/
/*
        for (int x = 0; x  < 4; x++) {
            //create ListItem with dummy data, then add them to our List
            for (int i = 0; i  < titles.length && i  < icons.length; i++) {
                ListItem item = new ListItem();
                item.setImageResId(icons[i]);
                item.setTitle("a");
                data.add(item);
            }
        }*/
        return data;
    }


}

