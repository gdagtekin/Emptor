package com.example.gdagt.emptor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gdagt.emptor.R;
import com.example.gdagt.emptor.model.ListItem2;

import java.util.List;

/**
 * Created by gdagt on 6/22/2017.
 */


public class DerpAdapter2 extends RecyclerView.Adapter<DerpAdapter2.DerpHolder> {

    private List<ListItem2> listData;
    private LayoutInflater inflater;

    public DerpAdapter2(List<ListItem2> listData, Context c) {
        inflater = LayoutInflater.from(c);
        this.listData = listData;
    }

    @Override
    public DerpAdapter2.DerpHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item2, parent, false);
        return new DerpHolder(view);
    }

    @Override
    public void onBindViewHolder(DerpHolder holder, int position) {
        ListItem2 item = listData.get(position);
        holder.title.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class DerpHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private View container;

        public DerpHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.lbl_item_text2);
            container = itemView.findViewById(R.id.cont_item_root2);
        }
    }
}

