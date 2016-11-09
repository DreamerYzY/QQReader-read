package com.yangzhiyan.qqreader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yangzhiyan.qqreader.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by YZY on 2016/10/28.
 */

public class ChoiceGridviewAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String,String>> collection;
    private boolean multichoice;

    private boolean[] selectedTag;

    public ChoiceGridviewAdapter(Context context, List<Map<String, String>> collection,
                                 boolean multichoice) {
        this.context = context;
        this.collection = collection;
        this.multichoice = multichoice;

        selectedTag = new boolean[collection.size()];
        for (int i=0;i<selectedTag.length;i++){
            selectedTag[i] = false;
        }
    }

    @Override
    public int getCount() {
        return collection.size();
    }

    @Override
    public Object getItem(int position) {
        return collection.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.filter_lv_gv_item_layout,null
            );
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        String text = collection.get(position).get("Text");
        holder.itemtextView.setText(text);
        if (selectedTag[position]){
            holder.itemtextView.setTextColor(context.getResources().
                    getColor(R.color.textselectedcolor));
            holder.itemtextView.setBackgroundResource(R.drawable.check_bg);
        }else {
            holder.itemtextView.setTextColor(context.getResources().
                    getColor(R.color.textunselectedcolor));
            holder.itemtextView.setBackgroundResource(R.drawable.uncheck_bg);
        }
        return convertView;
    }
    private class ViewHolder{
        TextView itemtextView;

        ViewHolder(View view){
            itemtextView = (TextView) view.findViewById(R.id.itemtextview);
        }
    }
    public void changeSelectedState(int position){
        if (multichoice){
            selectedTag[position] = !selectedTag[position];
        }else {
            for (int i=0;i<selectedTag.length;i++){
                selectedTag[i] = false;
            }
            selectedTag[position] = true;
        }
        notifyDataSetChanged();
    }
    public int getSelectedIndex(){
        int result = -1;
        for (int i=0;i<selectedTag.length;i++){
            if (selectedTag[i]){
                result = i;
                break;
            }
        }
        return result;
    }
    public List<Integer> getSelectedIndexes(){
        List<Integer> results = new ArrayList<>();
        for (int i=0;i<selectedTag.length;i++){
            if (selectedTag[i]){
                results.add(i);
            }
        }
        return results;
    }
    public void clearAllChoice(){
        for (int i=0;i<selectedTag.length;i++){
            selectedTag[i] = false;
        }if (multichoice == false){
            selectedTag[0] = true;
        }
        notifyDataSetChanged();
    }

    public void setState(int position,boolean checked){
        selectedTag[position] = checked;
        notifyDataSetChanged();
    }
}
