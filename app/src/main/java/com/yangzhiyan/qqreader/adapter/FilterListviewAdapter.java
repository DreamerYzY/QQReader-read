package com.yangzhiyan.qqreader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yangzhiyan.qqreader.R;
import com.yangzhiyan.qqreader.bean.MyGridView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by YZY on 2016/10/28.
 */

public class FilterListviewAdapter extends BaseAdapter {
    private Context context;
    private List<String> grouptitles;
    private List<ChoiceGridviewAdapter> gridviewAdapters;
    private View[] itemviews;

    public FilterListviewAdapter(Context context, List<String> grouptitles,
                                 List<ChoiceGridviewAdapter> gridviewAdapters) {
        this.context = context;
        this.grouptitles = grouptitles;
        this.gridviewAdapters = gridviewAdapters;

        itemviews = new View[grouptitles.size()];
    }

    @Override
    public int getCount() {
        return grouptitles.size();
    }

    @Override
    public Object getItem(int position) {
        return grouptitles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (itemviews[position] == null){
            itemviews[position] = LayoutInflater.from(context).inflate(
                    R.layout.filter_listview_item_layout,null
            );
            holder = new ViewHolder();
            x.view().inject(holder,itemviews[position]);
            itemviews[position].setTag(holder);
        }else {
            holder = (ViewHolder) itemviews[position].getTag();
        }
        String title = grouptitles.get(position);
        holder.grouptitle.setText(title);

        if (position == grouptitles.size()-1){
            holder.filtergridview.setNumColumns(3);
        }else {
            holder.filtergridview.setNumColumns(4);
        }
        holder.filtergridview.setAdapter(gridviewAdapters.get(position));
        holder.filtergridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                gridviewAdapters.get(position).changeSelectedState(i);
            }
        });
        return itemviews[position];
    }
    class ViewHolder{
        @ViewInject(R.id.grouptitle)
        TextView grouptitle;

        @ViewInject(R.id.filtergridview)
        MyGridView filtergridview;
    }
}
