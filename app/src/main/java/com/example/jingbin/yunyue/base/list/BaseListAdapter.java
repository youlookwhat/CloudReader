package com.example.jingbin.yunyue.base.list;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangcai on 16-4-23.
 *
 * ButterKnife.bind(this, itemView); 放在ViewHolder的构造方法里
 * @BindView(R.id.xxx) 放在 ViewHolder内
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {
    protected List<T> data = new ArrayList<>();

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder;
        if (convertView == null) {
            holder = creatHolder(parent, position);
            convertView = holder.getItemView();
            convertView.setTag(holder);
        } else {
            holder = (BaseHolder) convertView.getTag();
        }

        holder.onBindView(getItem(position), position);
        return convertView;
    }

    protected abstract BaseHolder<T> creatHolder(ViewGroup parent , int position);


    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public void addAll(List<T> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void removeAll(List<T> data){
        this.data.removeAll(data);
        notifyDataSetChanged();
    }
    public void add(T t) {
        this.data.add(t);
        notifyDataSetChanged();
    }

    public void clear() {
        this.data.clear();
        notifyDataSetChanged();
    }


}
