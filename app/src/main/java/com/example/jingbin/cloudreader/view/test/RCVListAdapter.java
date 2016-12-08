package com.example.jingbin.cloudreader.view.test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jingbin.cloudreader.R;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author linxiao
 * @version 1.0.0
 */
public class RCVListAdapter extends RecyclerView.Adapter<RCVListAdapter.ItemHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    Context context;

    private List<String> dataSource = new ArrayList<>();

    private View header;

    public RCVListAdapter(Context context) {
        this.context = context;
    }

    public void setDataSource(List<String> dataSource){
        this.dataSource = dataSource;
    }

    public boolean isHeader(int position) {
        return position == 0;
    }

    public void setHeader(View v) {
        header = v;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("RCVListAdapter", "onCreateViewHolder");
        if (viewType == TYPE_HEADER) {
            return new ItemHolder(header);
        }
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        return isHeader(position) ? TYPE_HEADER : TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return dataSource.size() + 1;
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        public ItemHolder(View itemView) {
            super(itemView);
        }
    }
}
